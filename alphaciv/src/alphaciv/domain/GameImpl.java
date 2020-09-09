package alphaciv.domain;

import java.util.HashMap;

/** Skeleton implementation of HotCiv.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University

   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
 */

public class GameImpl implements Game {
	private Tile[][] world;
	private HashMap<Position, Unit> units; 
	private HashMap<Position, City> cities;
	private Player currentPlayer;
	private int currentAge;


	public GameImpl() {

		//setting up 16x16 tiles 
		//ocean at (1,0) and mountains at (2,2)
		world = new Tile[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];

		for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
			for(int j = 0; j < GameConstants.WORLDSIZE; j++) {
				world[i][j] = new TileImpl(new Position(i,j));
			}
		}

		world[1][0] = new TileImpl(new Position(1, 0), GameConstants.OCEANS);
		world[2][2] = new TileImpl(new Position(2, 2), GameConstants.MOUNTAINS);	

		//initialize and set up units
		//red archer at (2,0), blue legion at (3,2), and red settler at (4,3)
		units = new HashMap<Position, Unit>();
		units.put(new Position(2, 0), new UnitImpl(GameConstants.ARCHER, Player.RED)); 
		units.put(new Position (3, 2), new UnitImpl(GameConstants.LEGION, Player.BLUE));
		units.put(new Position(4, 3), new UnitImpl(GameConstants.SETTLER, Player.RED));

		//initialize and set up cities 
		//Red city at (1, 1); 
		cities = new HashMap<Position, City>();
		cities.put(new Position(1,1), new CityImpl(Player.RED));
		cities.put(new Position(4, 1), new CityImpl(Player.BLUE));

		currentPlayer = Player.RED;
		currentAge = -4000;
	}

	public Tile getTileAt( Position p ) { 
		return world[p.getRow()][p.getColumn()];
	}

	public Unit getUnitAt( Position p ) { 	
		return units.get(p); 	
	}

	public City getCityAt( Position p ) { 		
		return cities.get(p);
	}


	public Player getPlayerInTurn() {
		return currentPlayer; 
	}

	public Player getWinner() { 
		if(currentAge >= -3000) {
			return Player.RED;
		}
		else {
			return null; 
		}
	}

	public int getAge() { return currentAge; }

	public boolean moveUnit( Position from, Position to ) {		
		if (isValidMove(from, to)) {  	
			units.put(to, units.get(from));
			units.remove(from);
			((UnitImpl)units.get(to)).beenMoved();
			return true;
		}
		else {
			return false; 
		}
	}

	/**
	 * returns true if the piece being moved is player's piece
	 * 			and if the tile being moved to is not a mountain
	 * 			and	if the 'to' destination is null (empty space)
	 * 				or if the 'to' destination holds a different player's unit
	 * @param from
	 * @param to
	 */
	private boolean isValidMove( Position from, Position to ) {
		UnitImpl movingUnit = (UnitImpl)units.get(from);
		Unit unitInToPos = units.get(to);
		return  !movingUnit.hasItBeenMoved()
				&& movingUnit.getOwner() == currentPlayer 
				&& getTileAt(to).getTypeString() != GameConstants.MOUNTAINS
				&& (unitInToPos == null || unitInToPos.getOwner() != currentPlayer)
				&& isValidMoveLength(from,to);	
	}

	private boolean isValidMoveLength(Position from, Position to ) {
		int rowDist = from.getRow() - to.getRow();
		int colDist = from.getColumn() - to.getColumn();
		int moveCount = units.get(from).getMoveCount();
		return (rowDist >= -moveCount && rowDist <= moveCount) && (colDist >= -moveCount && colDist <= moveCount);
	}
	
	public void endOfTurn() {
		if(getPlayerInTurn().equals(Player.RED)) { currentPlayer = Player.BLUE; }
		else if (getPlayerInTurn().equals(Player.BLUE)) { 
			currentPlayer = Player.RED; 
			endOfRound();
		}
	}

	private void endOfRound() {
		currentAge += 100;
		for (Position p : cities.keySet()) {
			((CityImpl) cities.get(p)).produceProduction();
			produceNewUnits(p);
		}
		for (Position p : units.keySet()) {
			((UnitImpl)units.get(p)).resetMove();
		}
	}

	/**
	 * costs: 
	 * 	Archer  - 10
	 * 	Legion  - 15
	 * 	Settler - 30 
	 */
	private void produceNewUnits(Position p) {
		CityImpl c = (CityImpl)cities.get(p);
		if (c.getTotalProduction() >= convertUnitToCost(c.getProduction())) {
			c.decreaseProductionForUnitCreation();
			placeProducedUnit(p);
		}
		
	}

	private int convertUnitToCost (String production) {
		int cost;
		switch (production) {
		case GameConstants.ARCHER:
			cost = GameConstants.ARCHER_COST;
			break;
		case GameConstants.LEGION:
			cost = GameConstants.LEGION_COST; 
			break;
		case GameConstants.SETTLER:
			cost = GameConstants.SETTLER_COST;
			break; 
		default:
			cost = 0;		
		}
		return cost;
	}

	private void placeProducedUnit(Position posOfCity) {
		int row = posOfCity.getRow();
		int col = posOfCity.getColumn();
		CityImpl c = (CityImpl)cities.get(posOfCity);
		Unit u = new UnitImpl(c.getProduction(),c.getOwner());

		if(units.get(new Position(row,col)) == null) {
			units.put(new Position(row, col), u); 
		}
		else if (units.get(new Position(row-1,col)) == null) {
			units.put(new Position(row-1,col), u); 
		}
		else if (units.get(new Position(row-1,col+1)) == null) {
			units.put(new Position(row-1,col+1), u);
		}
		else if (units.get(new Position(row,col+1)) == null) {
			units.put(new Position(row,col+1), u);
		}
		else if (units.get(new Position(row+1,col+1)) == null) {
			units.put(new Position(row+1,col+1), u);
		}
		else if (units.get(new Position(row+1,col)) == null) {
			units.put(new Position(row+1,col), u);
		}
		else if (units.get(new Position(row+1,col-1)) == null) {
			units.put(new Position(row+1,col-1), u);
		}
		else if (units.get(new Position(row,col-1)) == null) {
			units.put(new Position(row, col-1), u);			
		}
		else if (units.get(new Position(row-1,col-1)) == null) {
			units.put(new Position(row-1, col-1), u);
		}
		else {
			c.refundProductionForUnitCreation();
		}
	}

	public void changeWorkForceFocusInCityAt( Position p, String balance ) {
		//does nothing for right now
	}
	
	public void changeProductionInCityAt( Position p, String unitType ) {
		CityImpl c = (CityImpl)cities.get(p);
		if (currentPlayer.equals(c.getOwner())) {
			c.setProduction(unitType);
		}	
	}
	
	public void performUnitActionAt( Position p ) {
		//does nothing for right now
		
	}
	
}
