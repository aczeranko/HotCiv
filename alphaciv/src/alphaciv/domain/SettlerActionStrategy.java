package alphaciv.domain;

public class SettlerActionStrategy implements UnitActionStrategy {

	//needs to be able to remove Settler and create City 
	@Override
	public void performAction(Position posOfUnit, UnitActionContext actionContext) {
		// TODO Auto-generated method stub
		Player owner = actionContext.getOwnerOfUnitAt(posOfUnit);
		actionContext.removeUnit(posOfUnit);	
		actionContext.createCity(posOfUnit,owner);
	}

}
