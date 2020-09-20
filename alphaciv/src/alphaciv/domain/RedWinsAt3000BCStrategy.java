package alphaciv.domain;
import java.util.HashMap;

public class RedWinsAt3000BCStrategy implements WinnerStrategy {

	@Override
	public Player getWinner(int currentAge, HashMap<Position, CityImpl> cities) {
		if (currentAge >= -3000) {
			return Player.RED;
		}
		return null; 
	}
}
