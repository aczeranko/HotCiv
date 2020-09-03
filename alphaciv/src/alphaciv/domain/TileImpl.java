package alphaciv.domain;

public class TileImpl implements Tile {

	private Position position;
	private String tileType;
	
	public TileImpl(Position position) {
		this.position = position;
		this.tileType = GameConstants.PLAINS; 
	}
	
	public TileImpl(Position position, String tileType) {
		this.position = position;
		this.tileType = tileType; 
	}
	
	@Override
	public Position getPosition() {
		return position; 
	}

	@Override
	public String getTypeString() {
		return tileType; 
	}
}
