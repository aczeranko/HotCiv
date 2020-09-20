package alphaciv.domain;

import java.util.HashMap;


public interface WinnerStrategy {

	public Player getWinner(int currentAge, HashMap<Position, CityImpl> cities); 
}
