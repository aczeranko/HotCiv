package alphaciv.domain;

public interface UnitActionStrategy {
	public void performAction(Position posOfUnit, UnitActionContext actionContext); 
}
