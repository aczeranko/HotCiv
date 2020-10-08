package alphaciv.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestDeltaCiv {
	
	private Game game;

	@Before
	public void setUp() {
		WinnerStrategy winningStrat = new RedWinsAt3000BCStrategy();
		WorldAgingStrategy worldAgingStrat = new IncreaseAgeBy100YearsStrategy();
		AllUnitsActionStrategy allUnitsAction = new AllUnitsActionStrategyImpl(new DoNothingActionStrategy(),
																			   new DoNothingActionStrategy(),
																			   new DoNothingActionStrategy());
		WorldGeneratorImpl worldGen = new WorldGeneratorImpl(allUnitsAction);
		//plains everywhere else
			
		//mountain at: (0, 5), (2, 6) (3, 3), (3, 4), (3, 5), (7, 13), (11, 3), (11, 4) and (11,5)
		worldGen.createMountains(new Position(0, 5));
		worldGen.createMountains(new Position(2, 6));
		worldGen.createMountains(new Position(3, 3));
		worldGen.createMountains(new Position(3, 4));
		worldGen.createMountains(new Position(3, 5));
		worldGen.createMountains(new Position(7, 13));
		worldGen.createMountains(new Position(11, 3));
		worldGen.createMountains(new Position(11, 4));
		worldGen.createMountains(new Position(11, 5));


		//hills at: (1, 3), (1,4), (4, 8), (4. 9), (5, 11), (5, 12), (7, 10), (8, 9), (14, 5), (14,6)
		worldGen.createHills(new Position(1,3));
		worldGen.createHills(new Position(1,4));
		worldGen.createHills(new Position(4,8));
		worldGen.createHills(new Position(4,9));
		worldGen.createHills(new Position(5,11));
		worldGen.createHills(new Position(5,12));
		worldGen.createHills(new Position(7,10));
		worldGen.createHills(new Position(8,9));
		worldGen.createHills(new Position(14,5));
		worldGen.createHills(new Position(14,6));
		
		// forest at: (1, 9), (1, 10), (1, 11) (5, 2), (8, 13), (9, 1), (9, 2), (9, 3), (9, 10), (9, 11), (12, 8), (12, 9)
		worldGen.createForest(new Position(1,9));
		worldGen.createForest(new Position(1,10));
		worldGen.createForest(new Position(1,11));
		worldGen.createForest(new Position(5,2));
		worldGen.createForest(new Position(8,13));
		worldGen.createForest(new Position(9,1));
		worldGen.createForest(new Position(9,2));
		worldGen.createForest(new Position(9,3));
		worldGen.createForest(new Position(9,10));
		worldGen.createForest(new Position(9,11));
		worldGen.createForest(new Position(12,8));
		worldGen.createForest(new Position(12,9));
		
		//oceans at: (0, 0), (0, 1), (0, 2), (0, 11) (0, 12), (0, 13), (0, 14), (0, 15)
		// 			 (1, 0), (1, 1), (1, 14), (1, 15)
		// 			 (2, 0), (2, 10), (2, 11), (2, 12), (2, 15)
		//           (3, 0), (3, 10), (3, 11) 
		//			 (4, 0), (4, 1), (4, 2), (4, 14), (4, 15)
		//           (5, 0), (5, 15)
		//  		 (6, 0), (6, 1), (6, 2), (6, 6), (6, 7) ... (6.15)
		//			 (7, 0), (7, 6), (7, 14), (7, 15)
		//   		 (8, 0), (8, 6), (8, 14), (8, 15)
		//			 (9, 8)
		//           (10, 8), (10, 9), (10, 10)
		//			 (11, 0), (11, 10) ... (11, 15)
		// 			 (12, 0), (12, 1), (12, 14) (12, 15)
		//			 (13, 0), (13, 1), (13, 2), (13, 3), (13, 13) (13, 14) (13,15)
		//			 (14, 0), (14, 1), (14, 9)... (14, 15)
		//			 (15, 0), (15, 1), (15, 2), (15, 3), (15,4), (15,14)(15,15)
		worldGen.createOceans(new Position(0, 0));
		worldGen.createOceans(new Position(0, 1));
		worldGen.createOceans(new Position(0, 2));
		worldGen.createOceans(new Position(0, 11));
		worldGen.createOceans(new Position(0, 12));
		worldGen.createOceans(new Position(0, 13));
		worldGen.createOceans(new Position(0, 14));
		worldGen.createOceans(new Position(0, 15));
		
		worldGen.createOceans(new Position(1, 0));
		worldGen.createOceans(new Position(1, 1));
		worldGen.createOceans(new Position(1, 14));
		worldGen.createOceans(new Position(1, 15));
		
		worldGen.createOceans(new Position(2, 0));
		worldGen.createOceans(new Position(2, 10));
		worldGen.createOceans(new Position(2, 11));
		worldGen.createOceans(new Position(2, 12));
		worldGen.createOceans(new Position(2, 15));
		
		worldGen.createOceans(new Position(3, 0));
		worldGen.createOceans(new Position(3, 10));
		worldGen.createOceans(new Position(3, 11));
		
		worldGen.createOceans(new Position(4, 0));
		worldGen.createOceans(new Position(4, 1));
		worldGen.createOceans(new Position(4, 2));
		worldGen.createOceans(new Position(4, 14));
		worldGen.createOceans(new Position(4, 15));
		
		worldGen.createOceans(new Position(5, 0));
		worldGen.createOceans(new Position(5, 15));
		
		worldGen.createOceans(new Position(6, 0));
		worldGen.createOceans(new Position(6, 1));
		worldGen.createOceans(new Position(6, 2));
		worldGen.createOceans(new Position(6, 6));
		worldGen.createOceans(new Position(6, 7));
		worldGen.createOceans(new Position(6, 8));
		worldGen.createOceans(new Position(6, 9));
		worldGen.createOceans(new Position(6, 10));
		worldGen.createOceans(new Position(6, 11));
		worldGen.createOceans(new Position(6, 12));
		worldGen.createOceans(new Position(6, 13));
		worldGen.createOceans(new Position(6, 14));
		worldGen.createOceans(new Position(6, 15));
		
		worldGen.createOceans(new Position(7, 0));
		worldGen.createOceans(new Position(7, 6));
		worldGen.createOceans(new Position(7, 14));
		worldGen.createOceans(new Position(7, 15));
		
		worldGen.createOceans(new Position(8, 0));
		worldGen.createOceans(new Position(8, 6));
		worldGen.createOceans(new Position(8, 14));
		worldGen.createOceans(new Position(8, 15));
		
		worldGen.createOceans(new Position(9, 8));
		
		worldGen.createOceans(new Position(10, 8));
		worldGen.createOceans(new Position(10, 9));
		worldGen.createOceans(new Position(10, 10));
		
		worldGen.createOceans(new Position(11, 0));
		worldGen.createOceans(new Position(11, 10));
		worldGen.createOceans(new Position(11, 11));
		worldGen.createOceans(new Position(11, 12));
		worldGen.createOceans(new Position(11, 13));
		worldGen.createOceans(new Position(11, 14));
		worldGen.createOceans(new Position(11, 15));

		worldGen.createOceans(new Position(12, 0));
		worldGen.createOceans(new Position(12, 1));
		worldGen.createOceans(new Position(12, 14));
		worldGen.createOceans(new Position(12, 15));

		worldGen.createOceans(new Position(13, 0));
		worldGen.createOceans(new Position(13, 1));
		worldGen.createOceans(new Position(13, 2));
		worldGen.createOceans(new Position(13, 3));
		worldGen.createOceans(new Position(13, 13));
		worldGen.createOceans(new Position(13, 14));
		worldGen.createOceans(new Position(13, 15));

		worldGen.createOceans(new Position(14, 0));
		worldGen.createOceans(new Position(14, 1));
		worldGen.createOceans(new Position(14, 9));
		worldGen.createOceans(new Position(14, 10));
		worldGen.createOceans(new Position(14, 11));
		worldGen.createOceans(new Position(14, 12));
		worldGen.createOceans(new Position(14, 13));
		worldGen.createOceans(new Position(14, 14));
		worldGen.createOceans(new Position(14, 15));

		worldGen.createOceans(new Position(15, 0));
		worldGen.createOceans(new Position(15, 1));
		worldGen.createOceans(new Position(15, 2));
		worldGen.createOceans(new Position(15, 3));
		worldGen.createOceans(new Position(15, 4));
		worldGen.createOceans(new Position(15, 14));
		worldGen.createOceans(new Position(15, 15));

		worldGen.createCityAt(new Position(8, 12), Player.RED);
		worldGen.createCityAt(new Position(4, 5), Player.BLUE);
		
		worldGen.createUnit(new Position(3, 8), GameConstants.ARCHER, Player.RED);
		worldGen.createUnit(new Position(4, 4), GameConstants.LEGION, Player.BLUE);
		worldGen.createUnit(new Position(5, 5), GameConstants.SETTLER, Player.RED);
		
		game = new GameImpl(winningStrat, worldAgingStrat, worldGen);
	}
	@Test
	public void correctWorldLayout() {
		int plains = 0, oceans = 0, mountains = 0, hills = 0, forest = 0;
		for(int i = 0; i < GameConstants.WORLDSIZE; i++) {
			for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
				switch (game.getTileAt(new Position(i,j)).getTypeString()) {
				case GameConstants.PLAINS:
					plains++;
					break;
				case GameConstants.OCEANS:
					oceans++;
					break;
				case GameConstants.MOUNTAINS:
					mountains++;
					break;				
				case GameConstants.HILLS:
					hills++;
					break;
				case GameConstants.FOREST:
					forest++;
					break;
				}
			}
		}
		assertEquals("Number of Plains should be correct", 256 - (oceans + mountains + hills + forest), plains);
		assertEquals("Number of Oceans should be correct", 256 - (plains + mountains + hills + forest), oceans);
		assertEquals("Number of Mountains should be correct", 9, mountains);
		assertEquals("Number of Hills should be correct", 10, hills);
		assertEquals("Number of Forest should be correct", 12, forest);
		
		Unit u = game.getUnitAt(new Position (3, 8));
		assertEquals("Archer at 3, 8", GameConstants.ARCHER, u.getTypeString());
		assertEquals("Archer at 3, 8 belongs to Red", Player.RED, u.getOwner());
		
		u = game.getUnitAt(new Position (4, 4));
		assertEquals("Legion at 4, 4", GameConstants.LEGION, u.getTypeString());
		assertEquals("Legion at 4, 4 belongs to Blue", Player.BLUE, u.getOwner());	
		
		u = game.getUnitAt(new Position (5, 5));
		assertEquals("Settler at 5, 5", GameConstants.SETTLER, u.getTypeString());
		assertEquals("Settler at 5, 5 belongs to Red", Player.RED, u.getOwner());
		
		City redCity = game.getCityAt(new Position(8, 12));
		assertNotNull("Should have city at (8, 12)", redCity);
		assertEquals("City at (8, 12) should belong to red", Player.RED, redCity.getOwner());
		
		City blueCity = game.getCityAt(new Position(4, 5));
		assertNotNull("Should have city at (4, 5)", blueCity);
		assertEquals("City at (4,5) should belong to blue", Player.BLUE, blueCity.getOwner());
		
	}
	
}
