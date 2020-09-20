package alphaciv.domain;

import java.util.HashMap;

public class PlayerWinsIfConquersAllCitiesInWorldStrategy implements WinnerStrategy {

	@Override
	public Player getWinner(int currentAge, HashMap<Position, CityImpl> cities) {
		
		Position p  = (Position) cities.keySet().toArray()[0];
		
		Player possibleWinner = cities.get(p).getOwner(); 
		for(Position pos: cities.keySet()) {
			if (!cities.get(pos).getOwner().equals(possibleWinner)) {
				return null;
			}
		}	
		return possibleWinner; 
	}

}
