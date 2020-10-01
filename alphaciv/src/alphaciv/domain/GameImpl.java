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

public class GameImpl implements Game, UnitActionContext {
	private Tile[][] world;
	private HashMap<Position, UnitImpl> units;
	private HashMap<Position, CityImpl> cities; 
	private Player currentPlayer;
	private int currentAge;

	private WinnerStrategy winCond; 
	private WorldAgingStrategy agingStrategy;
	private AllUnitsActionStrategy allUnitsAction;

	public GameImpl(WinnerStrategy winningCondition, WorldAgingStrategy agingStrategy, AllUnitsActionStrategy allUnitsAction) {

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

		this.allUnitsAction = allUnitsAction;

		//initialize and set up units
		//red archer at (2,0), blue legion at (3,2), and red settler at (4,3)
		units = new HashMap<Position, UnitImpl>();
		units.put(new Position(2, 0), new UnitImpl(GameConstants.ARCHER, Player.RED, allUnitsAction)); 
		units.put(new Position (3, 2), new UnitImpl(GameConstants.LEGION, Player.BLUE, allUnitsAction));
		units.put(new Position(4, 3), new UnitImpl(GameConstants.SETTLER, Player.RED, allUnitsAction));

		//initialize and set up cities 
		//Red city at (1, 1); 
		cities = new HashMap<Position, CityImpl>(); 

		cities.put(new Position(1,1), new CityImpl(Player.RED));
		cities.put(new Position(4, 1), new CityImpl(Player.BLUE));

		currentPlayer = Player.RED;
		currentAge = -4000;

		winCond = winningCondition; 
		this.agingStrategy = agingStrategy;
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
		return winCond.getWinner(currentAge, cities);
	}

	public int getAge() { return currentAge; }

	public boolean moveUnit( Position from, Position to ) {		
		if (isValidMove(from, to)) {  	
			UnitImpl movingUnit = units.get(from);
			units.put(to, movingUnit);
			units.remove(from);
			units.get(to).beenMoved();
			CityImpl c = cities.get(to);
			if (c != null) {
				c.setOwner(movingUnit.getOwner());
			}	
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
		UnitImpl movingUnit = units.get(from);
		Unit unitInToPos = units.get(to);
		return  !movingUnit.hasItBeenMovedOrIsFortified()
				&& movingUnit.getOwner() == currentPlayer 
				&& isTileMoveableOnto(to)
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
		currentAge = agingStrategy.Aging(currentAge);
		for (Position p : cities.keySet()) {
			cities.get(p).produceProduction();
			produceNewUnits(p);
		}
		for (Position p : units.keySet()) {
			units.get(p).resetUnitMoveAndAction();
		}
	}

	/**
	 * costs: 
	 * 	Archer  - 10
	 * 	Legion  - 15
	 * 	Settler - 30 
	 */
	private void produceNewUnits(Position p) {
		CityImpl c = cities.get(p);
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
		CityImpl c = cities.get(posOfCity);
		UnitImpl u = new UnitImpl(c.getProduction(),c.getOwner(),allUnitsAction);

		if(isLegalPlacementOfCreatedUnit(new Position(row,col))) {
			units.put(new Position(row, col), u); 
		}
		else if (isLegalPlacementOfCreatedUnit(new Position(row-1,col))) {
			units.put(new Position(row-1,col), u); 
		}
		else if (isLegalPlacementOfCreatedUnit(new Position(row-1,col+1))) {
			units.put(new Position(row-1,col+1), u);
		}
		else if (isLegalPlacementOfCreatedUnit(new Position(row,col+1))) {
			units.put(new Position(row,col+1), u);
		}
		else if (isLegalPlacementOfCreatedUnit(new Position(row+1,col+1))) {
			units.put(new Position(row+1,col+1), u);
		}
		else if (isLegalPlacementOfCreatedUnit(new Position(row+1,col))) {
			units.put(new Position(row+1,col), u);
		}
		else if (isLegalPlacementOfCreatedUnit(new Position(row+1,col-1))) {
			units.put(new Position(row+1,col-1), u);
		}
		else if (isLegalPlacementOfCreatedUnit(new Position(row,col-1))) {
			units.put(new Position(row, col-1), u);			
		}
		else if (isLegalPlacementOfCreatedUnit(new Position(row-1,col-1))) {
			units.put(new Position(row-1, col-1), u);
		}
		else {
			c.refundProductionForUnitCreation();
		}
	}

	private boolean isTileMoveableOnto(Position p) {
		return (!GameConstants.MOUNTAINS.equals(getTileAt(p).getTypeString())
				&& !GameConstants.OCEANS.equals(getTileAt(p).getTypeString()));
	}

	public boolean isLegalPlacementOfCreatedUnit(Position p) {
		return units.get(p) == null && isTileMoveableOnto(p);
	}

	public void changeWorkForceFocusInCityAt( Position p, String balance ) {
		//does nothing for right now
	}

	public void changeProductionInCityAt( Position p, String unitType ) {
		CityImpl c = cities.get(p);
		if (currentPlayer.equals(c.getOwner())) {
			c.setProduction(unitType);
		}	
	}

	public void performUnitActionAt( Position p ) {
		UnitImpl u = units.get(p);
		if (u.getOwner() == currentPlayer && !u.hasActionBeenPerformed()) {
			u.doAction(p, this);
		}
	}

	@Override
	public void createCity(Position p, Player owner) {
			cities.put(p, new CityImpl(owner));			
	}

	@Override
	public void removeUnit(Position p) {
			units.remove(p);
	}

	@Override
	public Player getOwnerOfUnitAt(Position p) {
		return units.get(p).getOwner();
	}
}
	