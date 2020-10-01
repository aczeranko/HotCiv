package alphaciv.domain;

public class ArcherActionStrategy implements UnitActionStrategy {

	@Override
	public void performAction(Position posOfUnit, UnitActionContext actionContext ) {
		UnitImpl u = (UnitImpl) actionContext.getUnitAt(posOfUnit);
		u.fortify();
	}
}
