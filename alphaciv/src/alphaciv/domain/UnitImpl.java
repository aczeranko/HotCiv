package alphaciv.domain;

public class UnitImpl implements Unit {

	private String unitType; 
	private Player owner; 
	private int moveCount; 
	private boolean hasBeenMoved;
	private int defensiveStrength; 
	private int attackingStrength; 
	private boolean isFortified;
	private boolean actionPerformedYet;


	private UnitActionStrategy currentActionStrategy;


	public UnitImpl(String unitType, Player owner, AllUnitsActionStrategy allUnitsStrategy) {
		this.unitType = unitType; 
		this.owner = owner; 
		this.moveCount = 1; 
		this.hasBeenMoved = false; 
		this.currentActionStrategy = allUnitsStrategy.getStrategyFor(unitType);
		isFortified = false; 
		actionPerformedYet = false; 

		switch (unitType) {
		case GameConstants.ARCHER:
			this.defensiveStrength = 3; 
			this.attackingStrength = 2;
			break;
		case GameConstants.LEGION:
			this.defensiveStrength = 2; 
			this.attackingStrength = 4; 
			break;
		case GameConstants.SETTLER:
			this.defensiveStrength = 3; 
			this.attackingStrength = 0;
			break; 		
		}
	}


	@Override
	public String getTypeString() {
		return unitType; 
	}

	@Override
	public Player getOwner() {
		return owner; 
	}

	@Override
	public int getMoveCount() {
		return moveCount; 
	}

	@Override
	public int getDefensiveStrength() {
		return defensiveStrength; 

	} 

	@Override
	public int getAttackingStrength() {
		return attackingStrength;
	}

	public void beenMoved() {
		hasBeenMoved = true; 
	}

	public void resetUnitMoveAndAction() {
		hasBeenMoved = false;
		actionPerformedYet = false;
	}

	public boolean hasItBeenMovedOrIsFortified() {
		return hasBeenMoved || isFortified;
	}
	
	public boolean hasActionBeenPerformed() {
		return actionPerformedYet;
	}

	
	public void fortify() {
		if (unitType.equals(GameConstants.ARCHER)) {
			if (isFortified) {
				// defortify
				isFortified = false; 
				defensiveStrength = defensiveStrength /2; 
			}
			else {
				isFortified = true; 
				defensiveStrength = defensiveStrength * 2; 
			}
		}

	}

	public void doAction(Position posOfUnit, UnitActionContext actionContext) {
		currentActionStrategy.performAction(posOfUnit, actionContext);
		actionPerformedYet = true; 
	}

	public boolean IsFortified() {
		return isFortified;
	}



}
