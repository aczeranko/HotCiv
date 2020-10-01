package alphaciv.domain;

public interface AllUnitsActionStrategy {
	public UnitActionStrategy getStrategyFor(String unitType);
}
