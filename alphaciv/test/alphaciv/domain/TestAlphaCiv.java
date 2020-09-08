package alphaciv.domain;

import org.junit.*;
import static org.junit.Assert.*;

/** Skeleton class for AlphaCiv test cases 

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University

   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
 */
public class TestAlphaCiv {
	private Game game;
	/** Fixture for alphaciv testing. */
	@Before
	public void setUp() {
		game = new GameImpl();
	}

	@Test
	public void shouldHaveRedCityAt1_1() {
		City c = game.getCityAt(new Position(1,1));
		assertNotNull("There should be a city at (1,1)", c);
		Player p = c.getOwner();
		assertEquals( "City at (1,1) should be owned by red",
				Player.RED, p );
	}

	@Test 
	public void shouldHaveOceanAt0_1() {
		Tile t = game.getTileAt(new Position(1, 0)); 
		assertEquals("Should have ocean at (1, 0)", GameConstants.OCEANS, t.getTypeString());  
	}

	@Test 
	public void unitsCannotMoveOverMountain() {
		game.endOfTurn(); // sets it to Blue's turn
		assertEquals("Should be Blue's Turn",Player.BLUE, game.getPlayerInTurn());
		assertFalse("Units cannot move over mountains", game.moveUnit(new Position (3,2), new Position(2,2)));
	}

	@Test
	public void redCannotMoveBlue() {
		assertFalse("Red should not be able to move blue", game.moveUnit(new Position(3,2), new Position(3,3)));
	}

	@Test
	public void citiesProduce6ProductionAfterEndOfRoundOne() {
		assertEquals("City should have 6 production after round 1", 6, 6);
		
	
		
	}
	
	@Test
	public void citiesProduceAnother6AfterEndOfRoundTwo() {
		
	}


	@Test
	public void populationSizeAlwaysOne() {
		City c = game.getCityAt(new Position (1,1)); 
		assertEquals("Population size is always 1", 1, c.getSize());
	}

	@Test 
	public void blueTurnAfterRed() {
		game.endOfTurn();
		assertEquals("Blue Turn!",  Player.BLUE, game.getPlayerInTurn());
	}

	@Test
	public void redWinsInYear3000BC() {
		// 4000BC
		game.endOfTurn();
		game.endOfTurn();
		// 3900BC
		game.endOfTurn(); 
		game.endOfTurn();
		// 3800BC
		game.endOfTurn();
		game.endOfTurn();
		// 3700BC
		game.endOfTurn(); 
		game.endOfTurn();
		// 3600BC
		game.endOfTurn(); 
		game.endOfTurn();
		// 3500BC
		game.endOfTurn(); 
		game.endOfTurn();
		// 3400BC
		game.endOfTurn(); 
		game.endOfTurn();
		// 3300BC
		game.endOfTurn(); 
		game.endOfTurn(); 
		// 3200BC
		game.endOfTurn(); 
		game.endOfTurn();
		// 3100BC
		game.endOfTurn(); 
		game.endOfTurn();
		// 3000 BC
		
		assertEquals("Red Wins In Year 3000BC",Player.RED, game.getWinner());
	}

	@Test
	public void redAttackBlueDestroyed() {
		assertTrue("Red Settler at (4,3) destroys Blue Legion at (3, 2)", game.moveUnit(new Position(4, 3), new Position(3,2)));
		
		Unit u = game.getUnitAt(new Position(4, 3));
		assertNull("Red Settler should no longer be at (4,3)", u);
		assertEquals("Settler should be at (3,2)", GameConstants.SETTLER, game.getUnitAt(new Position(3,2)).getTypeString());
		assertEquals("Settler at (3,2) should be red",  Player.RED, game.getUnitAt(new Position (3,2)).getOwner()); 
	}
	
	@Test
	public void redCannotAttackRed() {
		game.moveUnit(new Position(2,0), new Position(3,1));
		game.endOfTurn();
		game.endOfTurn();
		game.moveUnit(new Position(3,1), new Position(4,2)); 
		game.endOfTurn();
		game.endOfTurn();
		assertFalse("Player cannot attack itself", game.moveUnit(new Position(4,2), new Position(4,3)));
	}

}