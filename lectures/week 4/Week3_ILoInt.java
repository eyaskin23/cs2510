import tester.Tester;

//represents a list of integers
interface ILoInt {
	//does this list satisfy the 3 requirements in problem 2, variant A of Lec. 8?
	boolean satisfies();
	//does this list satisfy the 3 requirements in problem 2, variant A of Lec. 8?
	//accumulators: 3 booleans to keep track of whether each requirement was satisfied
	boolean satisfiesAcc(boolean even, boolean posAndOdd, boolean bet5And10);
}

//represents an empty list of integers
class MtLoInt implements ILoInt {

	//does this empty list satisfy the 3 requirements in problem 2, variant A of Lec. 8?
	public boolean satisfies() {
		return false;
	}

	//does this list satisfy the 3 requirements in problem 2, variant A of Lec. 8?
	//accumulators: 3 booleans to keep track of whether each requirement was satisfied
	public boolean satisfiesAcc(boolean even, boolean posAndOdd, boolean bet5And10) {
		return even && posAndOdd && bet5And10;
	}
	
}

//represents a non-empty list of integers
class ConsLoInt implements ILoInt {
	int first;
	ILoInt rest;
	
	ConsLoInt(int first, ILoInt rest) {
		this.first = first;
		this.rest = rest;
	}
	
	/* fields:
	 *  this.first ... int
	 *  this.rest ... ILoInt
	 * methods: 
	 *  this.satisfies() ... boolean
	 *  this.satisfiesAcc(boolean, boolean, boolean) ... boolean
	 * methods for fields:
	 *  this.rest.satisfies()  
	 *  this.rest.satisfiesAcc(boolean, boolean, boolean) ... boolean
	 */

	//does this list non-empty satisfy the 3 requirements in problem 2, variant A of Lec. 8?
	public boolean satisfies() {
		return this.satisfiesAcc(false, false, false);
	}

	//does this list satisfy the 3 requirements in problem 2, variant A of Lec. 8?
	//accumulators: 3 booleans to keep track of whether each requirement was satisfied
	public boolean satisfiesAcc(boolean even, boolean posAndOdd, boolean bet5And10) {
		return  (even && posAndOdd && bet5And10) ||
				this.rest.satisfiesAcc(even || this.first % 2 == 0, 
				                      posAndOdd || this.first % 2 == 1 && this.first > 0, 
				                      bet5And10 || this.first >= 5 && this.first <= 10);
	}
}

class ExamplesLoInt {
	ILoInt mt = new MtLoInt();
	ILoInt list1 = new ConsLoInt(3, new ConsLoInt(6, this.mt));
	ILoInt list2 = new ConsLoInt(3, new ConsLoInt(4, this.mt));
	ILoInt list3 = new ConsLoInt(3, new ConsLoInt(4, new ConsLoInt(-2, this.mt)));

	
	boolean testSat(Tester t) {
		return t.checkExpect(this.mt.satisfies(), false)
				&& t.checkExpect(this.list1.satisfies(), true)
				&& t.checkExpect(this.list3.satisfies(), false);
	}
	
}