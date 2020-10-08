package alphaciv.domain;

import java.util.HashMap;

public interface WorldFactory {
	public Tile[][] getWorld();
	public HashMap<Position, UnitImpl> getUnits();
	public HashMap<Position, CityImpl> getCities();
	public AllUnitsActionStrategy getAllUnitsActionStrategy();
}

