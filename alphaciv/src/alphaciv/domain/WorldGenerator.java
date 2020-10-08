package alphaciv.domain;

public interface WorldGenerator {
	public void createCityAt(Position p, Player owner); 
	public void createOceans(Position p);
	public void createMountains(Position p); 
	public void createPlains(Position p);
	public void createForest(Position p);
	public void createHills(Position p);
	public void createUnit(Position p,String type, Player owner);
	public void resetWorld(); 
}
