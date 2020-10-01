package alphaciv.domain;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class WinnerStrategyUnitTests {
	private WinnerStrategy winStrat;
	private int age; 
	private HashMap<Position, CityImpl> cities;
	
	@Before
	public void setUp() {
		age = -4000; 
		cities = new HashMap<Position, CityImpl>();
		cities.put(new Position(0,0), new CityImpl(Player.RED));
		cities.put(new Position(1,0), new CityImpl(Player.BLUE));
	}
	
	@Test
	public void testRedWinsAt3000BCStrategy() {
		winStrat = new RedWinsAt3000BCStrategy(); 
		assertNull("There should not be any winner yet", winStrat.getWinner(age, cities));
		
		age = -3100; 
		assertNull("There should not be any winner yet", winStrat.getWinner(age, cities));		
		
		age = -3000; 
		assertEquals("Red Wins at 3000BC", Player.RED, winStrat.getWinner(age, cities)); 
	}

	
	
	@Test 
	public void testPlayerWinsIfConquersAllCitiesInWorldStrategy() {
		winStrat = new PlayerWinsIfConquersAllCitiesInWorldStrategy(); 
		assertNull("There should not be any winner yet", winStrat.getWinner(age, cities));
		
		cities.get(new Position(1,0)).setOwner(Player.RED);
		assertEquals("Red has all cities, so red wins", Player.RED, winStrat.getWinner(age, cities));
		
		
	}
}
