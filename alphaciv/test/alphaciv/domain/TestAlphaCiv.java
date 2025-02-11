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
		WinnerStrategy winningStrat = new RedWinsAt3000BCStrategy();
		WorldAgingStrategy worldAgingStrat = new IncreaseAgeBy100YearsStrategy();
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
	public void shouldHavePlainsAtOtherRandomUnassignedSpots() {
		Tile  t1 = game.getTileAt(new Position(13, 2)); 
		assertEquals("Should have plains at (13, 2)", GameConstants.PLAINS, t1.getTypeString());
		Tile  t2 = game.getTileAt(new Position(8, 6)); 
		assertEquals("Should have plains at (8, 6)", GameConstants.PLAINS, t2.getTypeString());
	}

	@Test 
	public void unitsCannotMoveOverMountain() {
		game.endOfTurn(); // sets it to Blue's turn
		assertEquals("Should be Blue's Turn",Player.BLUE, game.getPlayerInTurn());
		assertFalse("Units cannot move over mountains", game.moveUnit(new Position (3,2), new Position(2,2)));
	}
	
	@Test 
	public void unitsCannotMoveOverOcean() {
		assertFalse("Units cannot move over oceans", game.moveUnit(new Position (2,0), new Position(1,0)));
	}

	@Test
	public void redCannotMoveBlue() {
		assertFalse("Red should not be able to move blue", game.moveUnit(new Position(3,2), new Position(3,3)));
	}
	
	@Test
	public void blueCannotMoveRed() {
		game.endOfTurn();
		assertFalse("Blue should not be able to move red", game.moveUnit(new Position(4,3), new Position(5,3))); 
	}
	
	@Test
	public void citiesHaveNoProductionYet() {
		City c = game.getCityAt(new Position(1,1));
		assertEquals("City should have no production yet", 0, ((CityImpl) c).getTotalProduction());	
		game.endOfTurn();
		assertEquals("City should have no production yet", 0, ((CityImpl) c).getTotalProduction());	
	}

	@Test
	public void citiesProduce6ProductionAfterEndOfRoundOne() {
		game.endOfTurn();
		game.endOfTurn();
		City c = game.getCityAt(new Position(1,1));
		assertEquals("City should have 6 production after round 1", 6, ((CityImpl) c).getTotalProduction());
	}
	
	@Test
	public void citiesHave2ProductionAfterRound2() {
		game.endOfTurn();
		game.endOfTurn();
		game.endOfTurn();
		game.endOfTurn();
		City c = game.getCityAt(new Position(1,1));
		assertEquals("City should have 2 production after round 2", 2, ((CityImpl) c).getTotalProduction());
	}


	@Test
	public void populationSizeAlwaysOne() {
		City c = game.getCityAt(new Position (1,1)); 
		assertEquals("Population size is always 1", 1, c.getSize());
	}
	
	@Test
	public void startWithRedTurn() {
		assertEquals("Red Turn!", Player.RED, game.getPlayerInTurn());
	}

	@Test 
	public void blueTurnAfterRed() {
		game.endOfTurn();
		assertEquals("Blue Turn!",  Player.BLUE, game.getPlayerInTurn());
	}
	
	@Test
	public void redTurnAfterBlue() {
		game.endOfTurn();
		game.endOfTurn();
		assertEquals("Red Turn!", Player.RED, game.getPlayerInTurn());
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
		assertNull("No winner yet at 3100BC", game.getWinner());
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
	
	//archer costs 10
	@Test
	public void redBuyArcher() {
		game.endOfTurn(); 
		game.endOfTurn(); //6
		
		Unit u = game.getUnitAt(new Position(1,1));
		assertNull("Should not be unit at (1,1)", u); 
		
		game.endOfTurn();
		game.endOfTurn(); //12
		u = game.getUnitAt(new Position(1,1));
		assertEquals("Should be archer at (1,1)",GameConstants.ARCHER, u.getTypeString()); 
		assertEquals("Should be Red archer",Player.RED, u.getOwner()); 
	}
	
	//legion costs 15
	@Test
	public void redBuyLegion() {
		Position p = new Position(1,1);
		game.changeProductionInCityAt(p, GameConstants.LEGION);
		game.endOfTurn(); 
		game.endOfTurn(); //6
		
		game.endOfTurn();
		game.endOfTurn(); //12
		
		Unit u = game.getUnitAt(p);
		assertNull("Should not be unit at (1,1)", u); 

		
		game.endOfTurn();
		game.endOfTurn(); //18	
		
		u = game.getUnitAt(p);
		assertEquals("Should be Legion at (1,1)",GameConstants.LEGION, u.getTypeString()); 
		assertEquals("Should be Red legion", Player.RED, u.getOwner()); 
	}
	
	//settler costs 30
	@Test
	public void redBuySettler() {
		Position p = new Position(1,1);
		game.changeProductionInCityAt(p, GameConstants.SETTLER);
		
		game.endOfTurn(); 
		game.endOfTurn(); //6
		
		game.endOfTurn();
		game.endOfTurn(); //12
		
		Unit u = game.getUnitAt(p);
		assertNull("Should not be a unit at (1,1)", u); 
		
		game.endOfTurn();
		game.endOfTurn(); //18
		
		game.endOfTurn();
		game.endOfTurn(); //24
		
		u = game.getUnitAt(p);
		assertNull("Should not be a unit at (1,1)", u); 
		
		game.endOfTurn();
		game.endOfTurn(); //30	
		
		u = game.getUnitAt(p);
		assertEquals("Should be Settler at (1,1)",GameConstants.SETTLER, u.getTypeString());
		assertEquals("Should be Red Settler", Player.RED, u.getOwner()); 
	}
	
	@Test
	public void placeUnitsAllAroundCity() {
		game.endOfTurn();
		game.endOfTurn();	// 6
		game.endOfTurn();
		game.endOfTurn();   // 12 - 10 = 2
		Unit u = game.getUnitAt(new Position(1,1));
		assertEquals("Should be Archer at (1,1)",GameConstants.ARCHER, u.getTypeString()); 
		assertEquals("Should be Red Archer", Player.RED, u.getOwner()); 
		
		game.endOfTurn();
		game.endOfTurn();	// 8
		game.endOfTurn();
		game.endOfTurn();   // 14 - 10 = 4
		u = game.getUnitAt(new Position(0,1));
		assertEquals("Should be Archer at (0,1)",GameConstants.ARCHER, u.getTypeString()); 
		assertEquals("Should be Red Archer", Player.RED, u.getOwner()); 
		
		game.endOfTurn();
		game.endOfTurn();   // 10 - 10 = 0
		u = game.getUnitAt(new Position(0,2));
		assertEquals("Should be Archer at (0,2)",GameConstants.ARCHER, u.getTypeString()); 
		assertEquals("Should be Red Archer", Player.RED, u.getOwner()); 
		
		game.endOfTurn();
		game.endOfTurn();   // 6
		game.endOfTurn();
		game.endOfTurn();   // 12 - 10 = 2
		u = game.getUnitAt(new Position(1,2));
		assertEquals("Should be Archer at (1,2)",GameConstants.ARCHER, u.getTypeString()); 
		assertEquals("Should be Red Archer", Player.RED, u.getOwner()); 
		
		game.endOfTurn();
		game.endOfTurn();   // 8
		game.endOfTurn();
		game.endOfTurn();   // 14 - 10 = 4
		
		// skips (2,2) because there is a mountain
		u = game.getUnitAt(new Position(2,2));
		assertNull("Should not be any unit at (2,2)",u); 
		
		u = game.getUnitAt(new Position(2,1));
		assertEquals("Should be Archer at (2,1)",GameConstants.ARCHER, u.getTypeString()); 
		assertEquals("Should be Red Archer", Player.RED, u.getOwner()); 
		assertEquals("Should be 4 Production", 4, ((CityImpl)game.getCityAt(new Position(1,1))).getTotalProduction());
		
		// skips (2,0) because unit already exists.
		u = game.getUnitAt(new Position(2,0));
		assertEquals("Should be Archer already at (2,0)",GameConstants.ARCHER, u.getTypeString()); 
		assertEquals("Should be Red Archer", Player.RED, u.getOwner()); 
		
		game.endOfTurn();
		game.endOfTurn();   // 10 - 10 = 0
		
		// skips (1,0) because there is an ocean
		u = game.getUnitAt(new Position(1,0));
		assertNull("Should not be any unit at (1,0)",u); 
		
		u = game.getUnitAt(new Position(0,0));	
		assertEquals("Should be Archer at (0,0)",GameConstants.ARCHER, u.getTypeString()); 
		assertEquals("Should be Red Archer", Player.RED, u.getOwner()); 
		
		
		game.endOfTurn();
		game.endOfTurn();   // 6
		assertEquals("Should be 6 Production", 6 , ((CityImpl)game.getCityAt(new Position(1,1))).getTotalProduction());
		game.endOfTurn();
		game.endOfTurn();   // 12 
		
		assertEquals("Should refund Production since there's no room for unit", 12 , ((CityImpl)game.getCityAt(new Position(1,1))).getTotalProduction());
		
		game.endOfTurn();
		game.endOfTurn();   // 18
		assertEquals("Should refund Production since there's no room for unit", 18 , ((CityImpl)game.getCityAt(new Position(1,1))).getTotalProduction());
	}
	
	@Test
	public void hasNotBeenMoved() {
		assertFalse(((UnitImpl)game.getUnitAt(new Position(4,3))).hasItBeenMovedOrIsFortified());
	}
	
	@Test
	public void hasBeenMoved() {
		game.moveUnit(new Position(4,3), new Position (5,3));
		assertTrue(((UnitImpl)game.getUnitAt(new Position(5,3))).hasItBeenMovedOrIsFortified());
	}
	
	@Test
	public void ResetMovementAfterRoundEnds() {
		game.moveUnit(new Position(4,3), new Position (5,3));
		assertTrue(((UnitImpl)game.getUnitAt(new Position(5,3))).hasItBeenMovedOrIsFortified());
		
		game.endOfTurn();
		game.endOfTurn();
		assertFalse(((UnitImpl)game.getUnitAt(new Position(5,3))).hasItBeenMovedOrIsFortified());
	}
	
	@Test 
	public void CapturingCityWithoutDefendingUnit() {
		// red's turn
		game.endOfTurn(); 
		// blue's turn		
		game.moveUnit(new Position(3,2), (new Position(2,1)));
		game.endOfTurn();
		// red's turn
		game.endOfTurn();
		// blue's turn
		City c = game.getCityAt(new Position (1,1 ));
		assertEquals("City should still be owned by Red", Player.RED, c.getOwner());
		game.moveUnit(new Position(2,1), (new Position(1,1)));
		assertEquals("Blue Captures City at (1,1)", Player.BLUE, c.getOwner());
	}
	
	@Test
	public void CapturingCityWithDefendingUnit() {
		// red's turn
		game.moveUnit(new Position(4,3), new Position(4,2));
		game.endOfTurn();
		// blue's turn
		game.moveUnit(new Position(3,2), new Position(4,1));
		game.endOfTurn();
		// red's turn
		City c = game.getCityAt(new Position (4,1));
		assertEquals("City should still be owned by Blue", Player.BLUE, c.getOwner());
		game.moveUnit(new Position(4,2), new Position(4,1));
		assertEquals("Red Captures Blue City at (4,1)", Player.RED, c.getOwner());
	}	
}