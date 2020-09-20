package alphaciv.domain;

public class TimePeriodDependentAgingStrategy implements WorldAgingStrategy{

	@Override
	public int Aging(int currentAge) {
		int newAge;
		
		if(currentAge >= -4000 && currentAge < -100) {
			newAge = currentAge + 100; 
		}
		else if (currentAge == -100) {
			newAge = -1;
		}
		else if (currentAge == -1) {
			newAge = 1;
		}
		else if (currentAge == 1) {
			newAge = 50;
		}
		else if (currentAge >= 50 &&  currentAge < 1750) {
			newAge = currentAge + 50;  
		}
		else if (currentAge >= 1750 && currentAge < 1900) {
			newAge = currentAge + 25;
		}	
		else if (currentAge >= 1900 && currentAge < 1970) {
			newAge = currentAge + 5; 
		}
		else {
			newAge = currentAge + 1;
		}
		return newAge;
	}

}
