package alphaciv.domain;

public class CityImpl implements City {
	
	private Player owner;
	private int population;
	private int productionPerRound;
	private int totalProduction;
	private String production; 
	private String workforceFocus; 
	
	public CityImpl(Player owner) {
		this.owner = owner; 
		this.population = 1; 
		this.productionPerRound = 6;
		this.totalProduction = 0;
		this.production = GameConstants.ARCHER;
		this.workforceFocus = GameConstants.productionFocus;
	}
	
	@Override
	public Player getOwner() {
		return owner;
	}

	@Override
	public int getSize() {		 
		return population;
	}

	@Override
	public String getProduction() {
		return production;
	}

	@Override
	public String getWorkforceFocus() {
		return workforceFocus;
	}
	
	
	public int getTotalProduction() {
		return totalProduction; 
	}
	
	public void produceProduction() {
		totalProduction += productionPerRound;
	}
	
	public void decreaseProductionForUnitCreation() {
		int cost;
		switch (production) {
		case GameConstants.ARCHER:
			cost = GameConstants.ARCHER_COST;
			break;
		case GameConstants.LEGION:
			cost = GameConstants.LEGION_COST; 
			break;
		case GameConstants.SETTLER:
			cost = GameConstants.SETTLER_COST;
			break; 
		default:
			cost = 0;		
		}
		
		totalProduction -= cost; 
	}

	public void refundProductionForUnitCreation() {
		int cost;
		switch (production) {
		case GameConstants.ARCHER:
			cost = GameConstants.ARCHER_COST;
			break;
		case GameConstants.LEGION:
			cost = GameConstants.LEGION_COST; 
			break;
		case GameConstants.SETTLER:
			cost = GameConstants.SETTLER_COST;
			break; 
		default:
			cost = 0;		
		}
		totalProduction += cost; 
		
	}
	
	public void setProduction(String unitType) {
		this.production = unitType; 
	}	
}
