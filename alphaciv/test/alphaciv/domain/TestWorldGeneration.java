package alphaciv.domain;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TestWorldGeneration {
	private WorldGeneratorImpl worldGen;
	@Before
	public void setUp() {
		AllUnitsActionStrategy allUnitsAction = new AllUnitsActionStrategyImpl(new DoNothingActionStrategy(),
				   new DoNothingActionStrategy(),
				   new DoNothingActionStrategy());
		worldGen = new WorldGeneratorImpl(allUnitsAction);
	}	
	
	@Test
	public void allTilesArePlainsByDefault() {
		Tile[][] world = worldGen.getWorld();
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world.length; j++) {
				assertEquals("Tile at (" + i + "," + j + ") should be Plains", 
						GameConstants.PLAINS , world[i][j].getTypeString());
			}
		}
	}
	
	@Test
	public void correctTilesArePlacedCorrectly() {
		worldGen.createForest(new Position(0,0));
		worldGen.createOceans(new Position(4,4));
		worldGen.createHills(new Position(7,8));
		worldGen.createMountains(new Position(3,5));
		worldGen.createPlains(new Position(3,2));
		Tile[][] world = worldGen.getWorld();
		assertEquals("Tile at (0,0) should be Forest", 
				GameConstants.FOREST , world[0][0].getTypeString());
		assertEquals("Tile at (4,4) should be Oceans", 
				GameConstants.OCEANS , world[4][4].getTypeString());
		assertEquals("Tile at (7,8) should be Hills", 
				GameConstants.HILLS , world[7][8].getTypeString());		
		assertEquals("Tile at (3,5) should be Hills", 
				GameConstants.MOUNTAINS , world[3][5].getTypeString());
		assertEquals("Tile at (3,2) should be Plains", 
				GameConstants.PLAINS , world[3][2].getTypeString());	
	}
	
	@Test
	public void correctUnitsArePlacedCorrectly() {
		Position p1 = new Position(0,0);
		Position p2 = new Position(1,1);
		Position p3 = new Position(1,2);
		worldGen.createUnit(p1, GameConstants.ARCHER, Player.RED);
		worldGen.createUnit(p2, GameConstants.LEGION, Player.BLUE);
		worldGen.createUnit(p3, GameConstants.SETTLER, Player.RED);
		HashMap<Position,UnitImpl> units = worldGen.getUnits();
		assertEquals("Unit at (0,0) should be Archer", 
				GameConstants.ARCHER , units.get(p1).getTypeString());
		assertEquals("Unit at (0,0) should be Red", 
				Player.RED , units.get(p1).getOwner());
		assertEquals("Unit at (1,1) should be Legion", 
				GameConstants.LEGION , units.get(p2).getTypeString());
		assertEquals("Unit at (1,1) should be Blue", 
				Player.BLUE , units.get(p2).getOwner());	
		assertEquals("Unit at (1,2) should be Legion", 
				GameConstants.SETTLER , units.get(p3).getTypeString());
		assertEquals("Unit at (1,2) should be Blue", 
				Player.RED , units.get(p3).getOwner());
	}

	
}
