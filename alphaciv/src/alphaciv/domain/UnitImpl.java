package alphaciv.domain;

public class UnitImpl implements Unit {
	
	private String unitType; 
	private Player owner; 
	private int moveCount; 
	private int defensiveStrength; 
	private int attackingStrength; 
	
	public UnitImpl(String unitType, Player owner) {
		this.unitType = unitType; 
		this.owner = owner; 
		this.moveCount = 0; 
		this.defensiveStrength = 0; 
		this.attackingStrength = 0; 
	}
	
	public UnitImpl(String unitType, Player owner, int moveCount, int defensiveStrength, int attackingStrength) {
		this.unitType = unitType; 
		this.owner = owner; 
		this.moveCount = moveCount; 
		this.defensiveStrength = defensiveStrength; 
		this.attackingStrength = attackingStrength; 
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

}
