package alphaciv.domain;

import java.util.HashMap;

public class WorldGeneratorImpl implements WorldGenerator, WorldFactory {

	private Tile[][] world;
	private HashMap<Position, UnitImpl> units;
	private HashMap<Position, CityImpl> cities; 
	private AllUnitsActionStrategy allUnitsAction;
	
	public WorldGeneratorImpl(AllUnitsActionStrategy allUnitsAction) {
		world = new Tile[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
		resetWorld();
		units = new HashMap<Position, UnitImpl>();
		cities = new HashMap<Position, CityImpl>();
		this.allUnitsAction = allUnitsAction;
		
	}
	
	@Override
	public void createCityAt(Position p, Player owner) {
		cities.put(p, new CityImpl(owner));
	}

	@Override
	public void createOceans(Position p) {
		int r = p.getRow();
		int c = p.getColumn();
		world[r][c] = new TileImpl(p, GameConstants.OCEANS);
	}

	@Override
	public void createMountains(Position p) {
		int r = p.getRow();
		int c = p.getColumn();
		world[r][c] = new TileImpl(p, GameConstants.MOUNTAINS);
	}
	
	@Override
	public void createUnit(Position p, String type, Player owner) {
		units.put(p, new UnitImpl(type, owner, allUnitsAction)); 
		
	}

	@Override
	public void createPlains(Position p) {
		int r = p.getRow();
		int c = p.getColumn();
		world[r][c] = new TileImpl(p, GameConstants.PLAINS);	
	}

	@Override
	public void createForest(Position p) {
		int r = p.getRow();
		int c = p.getColumn();
		world[r][c] = new TileImpl(p, GameConstants.FOREST);
	}

	@Override
	public void createHills(Position p) {
		int r = p.getRow();
		int c = p.getColumn();
		world[r][c] = new TileImpl(p, GameConstants.HILLS);	
		
	}

	@Override
	public void resetWorld() {
		for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
			for(int j = 0; j < GameConstants.WORLDSIZE; j++) {
				world[i][j] = new TileImpl(new Position(i,j));
			}
		}
	}

	@Override
	public Tile[][] getWorld() {
		return world;
	}

	@Override
	public HashMap<Position, UnitImpl> getUnits() {
		return units;
	}

	@Override
	public HashMap<Position, CityImpl> getCities() {
		return cities;
	}

	@Override
	public AllUnitsActionStrategy getAllUnitsActionStrategy() {
		return allUnitsAction;
	}

}
