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
		actionContext = new GameImpl(winningStrat, worldAgingStrat, worldGen);
		
		
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
