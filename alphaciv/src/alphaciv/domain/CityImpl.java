package alphaciv.domain;

public class CityImpl implements City {
	
	private Player owner;
	private int population;
	private int productionPerRound;
	private String production; 
	private String workforceFocus; 
	
	public CityImpl(Player owner) {
		this.owner = owner; 
		this.population = 1; 
		this.productionPerRound = 6;
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

}
