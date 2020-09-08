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
		Unit movingUnit = units.get(from);
		Unit unitInToPos = units.get(to);
		return movingUnit.getOwner() == currentPlayer 
				&& getTileAt(to).getTypeString() != GameConstants.MOUNTAINS
				&& (unitInToPos == null || unitInToPos.getOwner() != currentPlayer);	
	}

	public void endOfTurn() {
		if(getPlayerInTurn().equals(Player.RED)) { currentPlayer = Player.BLUE; }
		else if (getPlayerInTurn().equals(Player.BLUE)) { 
			
			currentAge += 100;
			currentPlayer = Player.RED; 
		}
	}
	
	private void endOfRound() {
		
	}
	
	public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
	public void changeProductionInCityAt( Position p, String unitType ) {}
	public void performUnitActionAt( Position p ) {}
}
