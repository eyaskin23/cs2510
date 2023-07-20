class Date {
	int month;
	int day;
	int year;
	
	//constructor for data integrity
	Date(int m, int d, int y) {
		Utils utils = new Utils();
		this.month = utils.checkRange(m, 1, 12, "Month must be between 1 and 12 inclusive.");
		this.day = utils.checkRange(d, 1, 31, "Day must be between 1 and 31 inclusive.");
		this.year = utils.checkRange(y, 2000, 2100, "Year must be in this century");
	

  }
	//convenience constructor 
	Date(int m, int d) {
		this(m, d, 2023);
	}
	
	Date(int d) {
		this(2, d, 2023);
	}
	
	Date(int m, int y, boolean isFirstOfMonth) {
		this(m, 1, y); 
		if(isFirstOfMonth) {
			this.day = 1;
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
	int checkRange(int n, int low, int high, String message) {
		if (n >= low && n <= high ) {
			return n;
		} else {
			throw new IllegalArgumentException(message);
		}
}
	}

class ExamplesDates {
	Date today = new Date(2, 1, 2023);
	Date badDate = new Date(-2, 55, 5000);
}