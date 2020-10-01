package alphaciv.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestGammaCiv {

	private Game game;
	/** Fixture for GammaCiv testing. */
	@Before
	public void setUp() {
		WinnerStrategy winningStrat = new RedWinsAt3000BCStrategy(); 
		WorldAgingStrategy worldAgingStrat = new IncreaseAgeBy100YearsStrategy();
		AllUnitsActionStrategy allUnitsAction = new AllUnitsActionStrategyImpl(new ArcherActionStrategy(),
				   new DoNothingActionStrategy(),
				   new SettlerActionStrategy());
		game = new GameImpl(winningStrat, worldAgingStrat, allUnitsAction);
	}
	
	
	@Test
	public void redSettlerCreateCityAt43() {
		Position p = new Position(4,3);
		assertEquals("Position (4, 3) has a settler", GameConstants.SETTLER, game.getUnitAt(p).getTypeString());
		assertNull("Position (4, 3) has no city", game.getCityAt(p));
		
		game.performUnitActionAt(p);
		
		City c = game.getCityAt(p);
		assertNotNull("Position 4, 3) has a city", c);
		assertEquals("Red player owns the city", Player.RED, c.getOwner());
		assertNull("The settler is removed", game.getUnitAt(p));
		assertEquals("City has population size 1", 1, c.getSize());
		
	}
	
	@Test
	public void redArcherFortify() {
		Position p = new Position (2, 0);
		Unit u = game.getUnitAt(p); 
		assertEquals("Position (2,0) has an archer", GameConstants.ARCHER, u.getTypeString());
		assertEquals("Defensive strength of Archer initially 3", 3, u.getDefensiveStrength());
				
		game.performUnitActionAt(p);
		assertEquals("The defensive strength is doubled", 6, u.getDefensiveStrength());
		
		assertFalse("Archer cannot move after being fortified ", game.moveUnit(p, new Position(2, 1)));
		game.performUnitActionAt(p);
		
		// Should only perform action once
		assertEquals("The defensive strength is still doubled", 6, u.getDefensiveStrength());
		assertFalse("Archer still cannot move", game.moveUnit(p, new Position(2, 1)));
		
		game.endOfTurn();
		game.endOfTurn();
		
		assertFalse("Archer still cannot move", game.moveUnit(p, new Position(2, 1)));
		
		game.performUnitActionAt(p);
		assertTrue("Archer can move after being defortified", game.moveUnit(p, new Position(2, 1)));
		assertEquals("The defensive strength is back to 3", 3, u.getDefensiveStrength());
	}
	
	@Test
	public void blueCannotPerformRedAction() {
		game.endOfTurn();
		
		Position p = new Position (4, 3); 
		assertEquals("There is a red Settler position (4, 3)", GameConstants.SETTLER, game.getUnitAt(p).getTypeString());
		
	    game.performUnitActionAt(p);
		assertEquals("Blue cannot perform red action; red settler still exist", GameConstants.SETTLER, game.getUnitAt(p).getTypeString());
	}
}
