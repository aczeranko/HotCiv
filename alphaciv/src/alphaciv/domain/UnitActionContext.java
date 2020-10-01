package alphaciv.domain;

public interface UnitActionContext {
	public void createCity(Position p, Player owner);
	public void removeUnit(Position p);
	public Unit getUnitAt(Position p);
	public Player getOwnerOfUnitAt(Position p);
	
}
