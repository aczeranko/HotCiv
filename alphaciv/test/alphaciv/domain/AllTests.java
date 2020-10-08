package alphaciv.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestAlphaCiv.class, TestBetaCiv.class, TestDeltaCiv.class, TestGammaCiv.class,
		TestWorldGeneration.class, UnitActionContextTest.class, WinnerStrategyUnitTests.class,
		WorldAgingStrategyUnitTests.class })
public class AllTests {

}
