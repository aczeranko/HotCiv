package alphaciv.domain;

public class UnitImpl implements Unit {
	
	private String unitType; 
	private Player owner; 
	private int moveCount; 
	private boolean hasBeenMoved;
	private int defensiveStrength; 
	private int attackingStrength; 
	
	public UnitImpl(String unitType, Player owner) {
		this.unitType = unitType; 
		this.owner = owner; 
		this.moveCount = 1; 
		this.hasBeenMoved = false; 
		
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
	
	public void resetMove() {
		hasBeenMoved = false;
	}

	public boolean hasItBeenMoved() {
		return hasBeenMoved;
	}

}
