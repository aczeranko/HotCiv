package alphaciv.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestBetaCiv {

	private Game game;
	/** Fixture for Betaciv testing. */
	@Before
	public void setUp() {
		WinnerStrategy winningStrat = new PlayerWinsIfConquersAllCitiesInWorldStrategy(); 
		WorldAgingStrategy worldAgingStrat = new TimePeriodDependentAgingStrategy();
		AllUnitsActionStrategy allUnitsAction = new AllUnitsActionStrategyImpl(new DoNothingActionStrategy(),
				   new DoNothingActionStrategy(),
				   new DoNothingActionStrategy());
		WorldGeneratorImpl worldGen = new WorldGeneratorImpl(allUnitsAction);
		//setting up 16x16 tiles 
		//ocean at (1,0) and mountains at (2,2)
		worldGen.createOceans(new Position(1, 0));
		worldGen.createMountains(new Position(2, 2));
		//red archer at (2,0), blue legion at (3,2), and red settler at (4,3)
		worldGen.createUnit(new Position(2,0), GameConstants.ARCHER, Player.RED);
		worldGen.createUnit(new Position(3,2), GameConstants.LEGION, Player.BLUE);
		worldGen.createUnit(new Position(4, 3), GameConstants.SETTLER, Player.RED);
		
		//Red city at (1, 1), Blue city at (4,1);
		worldGen.createCityAt(new Position(1,1), Player.RED);
		worldGen.createCityAt(new Position(4, 1), Player.BLUE);
		game = new GameImpl(winningStrat, worldAgingStrat, worldGen);
	}
	
	@Test 
	public void TestAging() {
	// 4000BC
		assertEquals("Should be 4000BC", -4000, game.getAge());
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 3900BC", -3900, game.getAge());
	
	helpAgingTo200BC();
	assertEquals("Should be 200BC", -200, game.getAge());
	
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 100BC", -100, game.getAge());
	
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 1BC", -1, game.getAge());	
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 1AD", 1, game.getAge());	
	
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 50AD", 50, game.getAge());	
	
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 100AD", 100, game.getAge());	
	
	helpAgingTo1700AD();
	assertEquals("Should be 1700AD", 1700, game.getAge());

	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 1750AD", 1750, game.getAge());

	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 1775AD", 1775, game.getAge());

	helpAgingto1875();
	assertEquals("Should be 1875AD", 1875, game.getAge());
	
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 1900AD", 1900, game.getAge());
	
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 1905AD", 1905, game.getAge());

	helpAgingto1965(); 
	assertEquals("Should be 1965AD", 1965, game.getAge());

	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 1970AD", 1970, game.getAge());
	
	game.endOfTurn();
	game.endOfTurn();
	assertEquals("Should be 1971AD", 1971, game.getAge());

	} 
	
	@Test
	public void RedWinsWhenCapturesAllCities() {
		// red's turn
		game.moveUnit(new Position(4,3), new Position(4,2));
		game.endOfTurn();
		// blue's turn
		game.moveUnit(new Position(3,2), new Position(4,1));
		game.endOfTurn();
		// red's turn
		assertNull("No Winner Yet", game.getWinner());
		game.moveUnit(new Position(4,2), new Position(4,1));
		assertEquals("Red Captures Blue City at (4,1) and becomes winner", Player.RED, game.getWinner());
	}
	
	
	@Test 
	public void BlueWinsWhenCapturesAllCIties() {
		// red's turn
		game.endOfTurn(); 
		// blue's turn		
		game.moveUnit(new Position(3,2), (new Position(2,1)));
		game.endOfTurn();
		// red's turn
		game.endOfTurn();
		// blue's turn
		assertNull("No Winner Yet", game.getWinner());
		game.moveUnit(new Position(2,1), (new Position(1,1)));
		assertEquals("Blue Captures City at (1,1) and becomes winner", Player.BLUE, game.getWinner());
	}
	
	private void helpAgingTo200BC() {
		// Notice that 37 = (3900-200)/100		
		for(int i = 0; i < 37; i++) {
			game.endOfTurn();
			game.endOfTurn();
		}
	}
	
	private void helpAgingTo1700AD() {
		//notice that 32 = (1700 - 100) / 50
		for (int i = 0; i < 32; i++ ) {
			game.endOfTurn();
			game.endOfTurn();
		}
	}
	
	private void helpAgingto1875() {
		//notice that 4 = (1875 - 1775) / 25
		for(int i = 0; i < 4; i++) {
			game.endOfTurn();
			game.endOfTurn();
		}
	}
	
	private void helpAgingto1965() {
		//notice that 12 = (1965 - 1905) / 5
		for(int i = 0; i < 12; i++) {
			game.endOfTurn();
			game.endOfTurn();
		}
	}
	
	
	

}
