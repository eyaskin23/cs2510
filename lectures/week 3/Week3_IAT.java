import tester.Tester;

//represents an ancestor tree
interface IAT {

}

class Unknown implements IAT {
	/* fields: none
	 * methods:
	 *   this.names() ... ILoString
	 *   this.namesAcc(ILoString) ... ILoString
	 *   this.wellFormed() ... boolean
	 * methods for fields: none
	 */
}

//represents a person in an ancestor tree
class Person implements IAT {
	String name;
	int yob;
	IAT parent1;
	IAT parent2;

	Person(String name, int yob, IAT p1, IAT p2) {
		this.name = name;
		this.yob = yob;
		this.parent1 = p1;
		this.parent2 = p2;
	}


	/* fields: 
	 *   this.name ... String
	 *   this.yob ... int
	 *   this.parent1 ... IAT
	 *   this.parent2 ... IAT
	 * methods:
	 *   this.names() ... ILoString
	 *   this.namesAcc(ILoString) ... ILoString
	 *   this.wellFormed() ... boolean
	 * methods for fields:
	 *   this.parent1.names() ... ILoString
	 *   this.parent2.names() ... ILoString
	 *   this.parent1.namesAcc(ILoString) ... ILoString
	 *   this.parent2.namesAcc(ILoString) ... ILoString
	 *   this.parent1.wellFormed() ... boolean
	 *   this.parent2.wellFormed() ... boolean
	 */

}


//represents a list of strings
interface ILoString {
	//reverse the order of the strings in this list
	ILoString reverse();
	//add the string to the end of this list
	ILoString addAsLast(String add);

}

//represents a non-empty list of strings
class ConsLoString implements ILoString {
	String first;
	ILoString rest;

	ConsLoString(String first, ILoString rest) {
		this.first = first;
		this.rest = rest;
	}

	/* fields:
	 *  this.first ... String
	 *  this.rest ... ILoString
	 * methods:
	 *  this.reverse() ... ILoString
	 *  this.addAsLast(String) ... ILoString
	 *  this.reverse2() ... ILoString
	 *  this.reverseAcc(ILoString) ... ILoString
	 * methods for fields:
	 *  this.rest.reverse() ... ILoString
	 *  this.rest.addAsLast(String) ... ILoString  
	 *  this.rest.reverse2() ... ILoString
	 *  this.rest.reverseAcc(ILoString) ... ILoString
	 * 
	 */

	//reverses this list of strings
	public ILoString reverse() {
		return this.rest.reverse().addAsLast(this.first) ;
	}

	//adds the given string to the end of this list
	public ILoString addAsLast(String add) {
		return new ConsLoString(this.first, this.rest.addAsLast(add));
	}

}

//represents an empty list of strings
class MtLoString implements ILoString {
	MtLoString() { }
	
	/* fields:none
	 * methods:
	 *  this.reverse() ... ILoString
	 *  this.addAsLast(String) ... ILoString
	 *  this.reverse2() ... ILoString
	 *  this.reverseAcc(ILoString) ... ILoString
	 * methods for fields:none
	 * 
	 */

	//returns this list as its reverse
	public ILoString reverse() {
		return this;
	}

	//put the given string at the end of this list
	public ILoString addAsLast(String add) {
		return new ConsLoString(add, this);
	}

}


class Examples {
	IAT enid = new Person("Enid", 1904, new Unknown(), new Unknown());
	IAT edward = new Person("Edward", 1902, new Unknown(), new Unknown());
	IAT emma = new Person("Emma", 1906, new Unknown(), new Unknown());
	IAT eustace = new Person("Eustace", 1907, new Unknown(), new Unknown());

	IAT david = new Person("David", 1925, new Unknown(), this.edward);
	IAT daisy = new Person("Daisy", 1927, new Unknown(), new Unknown());
	IAT dana = new Person("Dana", 1933, new Unknown(), new Unknown());
	IAT darcy = new Person("Darcy", 1930, this.emma, this.eustace);
	IAT darren = new Person("Darren", 1935, this.enid, new Unknown());
	IAT dixon = new Person("Dixon", 1936, new Unknown(), new Unknown());

	IAT clyde = new Person("Clyde", 1955, this.daisy, this.david);
	IAT candace = new Person("Candace", 1960, this.dana, this.darren);
	IAT cameron = new Person("Cameron", 1959, new Unknown(), this.dixon);
	IAT claire = new Person("Claire", 1956, this.darcy, new Unknown());

	IAT bill = new Person("Bill", 1980, this.candace, this.clyde);
	IAT bree = new Person("Bree", 1981, this.claire, this.cameron);

	IAT andrew = new Person("Andrew", 2001, this.bree, this.bill);

	ILoString mt = new MtLoString();
	ILoString list1 = new ConsLoString("hello", this.mt);
	ILoString list2 = new ConsLoString("how", this.list1);
	ILoString list3 = new ConsLoString("are", this.list2);
	ILoString list4 = new ConsLoString("you", this.list3);

	ILoString list1rev = new ConsLoString("you", this.mt);
	ILoString list2rev = new ConsLoString("are", this.list1rev);
	ILoString list3rev = new ConsLoString("how", this.list2rev);
	ILoString list4rev = new ConsLoString("hello", this.list3rev);

	boolean testReverse(Tester t) {
		return t.checkExpect(this.list4.reverse(), this.list4rev) &&
				t.checkExpect(this.mt.reverse(), this.mt) &&
				t.checkExpect(this.mt.addAsLast("hello"), this.list1) &&
				t.checkExpect(this.list1.addAsLast("world"), 
						new ConsLoString("hello", new ConsLoString("world", this.mt)));
		//add more tests
	}

	boolean testCountAnc(Tester t) {
		return true;
	}

	boolean testWellFormed(Tester t) {
		return true;

	}
/*
	boolean testAncNames(Tester t) {
		return t.checkExpect(new Unknown().names(), new MtLoString()) &&
				t.checkExpect(this.darcy.names(), new ConsLoString("Darcy", new ConsLoString("Emma",
						new ConsLoString("Eustace", new MtLoString())))) &&
				t.checkExpect(this.andrew.names(), false);
	}
	*/
}