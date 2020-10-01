package alphaciv.domain;

public class AllUnitsActionStrategyImpl implements AllUnitsActionStrategy {
	
	private UnitActionStrategy archerStrat; 
	private UnitActionStrategy legionStrat; 
	private UnitActionStrategy settlerStrat;
	
	
	public AllUnitsActionStrategyImpl(UnitActionStrategy archerStrat, 
									  UnitActionStrategy legionStrat, 
									  UnitActionStrategy settlerStrat) {
		this.archerStrat = archerStrat;
		this.legionStrat = legionStrat;
		this.settlerStrat = settlerStrat;
	}
	@Override
	public UnitActionStrategy getStrategyFor(String unitType) {		
		switch (unitType) {
		case GameConstants.ARCHER:
			return archerStrat; 
			
		case GameConstants.LEGION:
			return legionStrat; 
			
		case GameConstants.SETTLER:
			return settlerStrat; 
		default:
			return null;
		}
	}

}
