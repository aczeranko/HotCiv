package alphaciv.domain;

public class IncreaseAgeBy100YearsStrategy implements WorldAgingStrategy {

	@Override
	public int Aging(int currentAge) {
		return currentAge += 100; 
	}
}
