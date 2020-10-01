package alphaciv.domain;

public class DoNothingActionStrategy implements UnitActionStrategy {

	@Override
	public void performAction(Position posOfUnit, UnitActionContext actionContext) {
		// Do nothing
	}

}
