import tester.Tester;

class Date {
	int month;
	int day;
	int year;
	
	Date(int m, int d, int y) {
		if (m >= 1 && m <= 12) {
			this.month = m;
		} else {
			throw new IllegalArgumentException("Month must be between 1 and 12 inclusive."); 		
		} 
		if (d >=1 && d <= 31) {
			this.day = d;
		} else {
			throw new IllegalArgumentException("Day must be between 1 and 31 inclusive.");
			}
		if (y >= 2000 && y <= 2100) {
			this.year = y;
		} else {
			throw new IllegalArgumentException("Year must be in this century.");
		}
	}
	
	/* fields:
	 * this.month ... int
	 * this.day ... int
	 * this.year ... int
	 * methods:
	 * 
	 */

}

class Utils {
	//checks if the given number is within the range 
	int checkRange(int n, int low, int high, String message) {
		if (n >= low && n<= high) {
			return n;
		} else {
			throw new IllegalArgumentException(message);
		}
	}
}

class ExamplesDates {
	Date today = new Date(2, 1, 2023);
	//Date badDate = new Date(-2, 55, 5000);
	
	//test Date Constructor
	boolean testDateConstructor(Tester t) {
		return t.checkConstructorException(
				new IllegalArgumentException("Day must be between 1 and 31 inclusive."),
										"Date",
										2, 4, 2023);
	}
	//test checkRange
	boolean testCheckRange(Tester t) {
		return t.checkExpect(new Utils().checkRange(3, 0, 5, "it's good"), 3) 
				&& t.checkException(new IllegalArgumentException(""), null, null, null)
	}
	
}