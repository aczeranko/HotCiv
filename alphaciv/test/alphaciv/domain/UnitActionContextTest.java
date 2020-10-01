package alphaciv.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UnitActionContextTest {

	private UnitActionContext actionContext; 

	@Before
	public void setUp() {
		WinnerStrategy winningStrat = new RedWinsAt3000BCStrategy();
		WorldAgingStrategy worldAgingStrat = new IncreaseAgeBy100YearsStrategy();
		AllUnitsActionStrategy allUnitsAction = new AllUnitsActionStrategyImpl(new DoNothingActionStrategy(),
																			   new DoNothingActionStrategy(),
																			   new DoNothingActionStrategy());
		actionContext = new GameImpl(winningStrat, worldAgingStrat, allUnitsAction);
		
	}
	
	@Test
	public void testCreateCity() {
		Position p = new Position(5, 5); 
		assertNull("No city a position 5, 5", ((GameImpl)actionContext).getCityAt(p));
		
		actionContext.createCity(p, Player.BLUE);
		City c = ((GameImpl)actionContext).getCityAt(p); 
		assertNotNull("City at position 5, 5", c);
		assertEquals("The city belongs to blue player", Player.BLUE, c.getOwner());
	}
	
	@Test
	public void testRemoveUnit() {
		Position p = new Position(2,0);
		Unit u = actionContext.getUnitAt(p);
		assertEquals("There is a unit at position (2,0)", GameConstants.ARCHER, u.getTypeString());
		actionContext.removeUnit(p);
		assertNull("The unit at position (2,0) is removed", actionContext.getUnitAt(p));
	}
	
	
	@Test
	public void testGetOwnerOfUnitAt() {
		Position p = new Position(2,0);
		Unit u = actionContext.getUnitAt(p);
		assertEquals("There is a unit at position (2,0)", GameConstants.ARCHER, u.getTypeString());
		assertEquals("unit at position (2,0) has Red Owner", Player.RED, actionContext.getOwnerOfUnitAt(p));
	}

}
