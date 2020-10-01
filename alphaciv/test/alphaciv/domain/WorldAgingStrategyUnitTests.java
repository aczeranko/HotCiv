package alphaciv.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WorldAgingStrategyUnitTests {

	private WorldAgingStrategy worldAging; 
	private int age; 
	
	@Before
	public void setUp() {
		age = -4000; 
	}
	
	@Test
	public void testIncreaseAgeBy100YearsStrategy() {
		worldAging = new IncreaseAgeBy100YearsStrategy(); 
		assertEquals("Age is 4000BC", age, age);
		
		
		assertEquals("Age is 39000BC", age+100, worldAging.Aging(age));
	}
	
	@Test
	public void testTimePeriodDependenAgingStrategy() {
		worldAging = new TimePeriodDependentAgingStrategy(); 
		
		    // 4000BC
			assertEquals("Should be 4000BC", -4000, age);
			assertEquals("Should be 3900BC", age+100, worldAging.Aging(age));
			
			age = -200;
			assertEquals("Should be 200BC", -200, age);
			assertEquals("Should be 100BC", age+100, worldAging.Aging(age));
			
			age = -100;
			assertEquals("Should be 1BC", -1, worldAging.Aging(age));	
			age = -1;
			assertEquals("Should be 1AD", 1, worldAging.Aging(age));	
			
			age = 1;
			assertEquals("Should be 50AD", 50, worldAging.Aging(age));	
			
			age = 50;
			assertEquals("Should be 50AD", 50, age);
			assertEquals("Should be 100AD", age+50, worldAging.Aging(age));	
			
			age = 1700;
			assertEquals("Should be 1700AD", 1700, age);
			assertEquals("Should be 1750AD", age+50, worldAging.Aging(age));
			
			age = 1750;
			assertEquals("Should be 1750AD", 1750, age);
			assertEquals("Should be 1775AD", age + 25, worldAging.Aging(age));

			age = 1875;
			assertEquals("Should be 1875AD", 1875, age);
			assertEquals("Should be 1900AD", age + 25, worldAging.Aging(age));
			
			age = 1900;
			assertEquals("Should be 1900AD", 1900, age);
			assertEquals("Should be 1905AD", age + 5, worldAging.Aging(age));

			age = 1965; 
			assertEquals("Should be 1965AD", 1965, age);
			assertEquals("Should be 1970AD", age + 5, worldAging.Aging(age));
			
			age = 1970;
			assertEquals("Should be 1970AD", 1970, age);
			assertEquals("Should be 1971AD", age + 1, worldAging.Aging(age));
	}

}
