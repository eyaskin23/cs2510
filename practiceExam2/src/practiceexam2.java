
// notes on review session 2

// Problem 1 : Roster (Mutation)

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import tester.Tester;

// represents a roster of students for a sports team
class Roster {
  // names of the students
  ArrayList<String> names;

  // create a roster from a given list of names
  Roster(ArrayList<String> names) {
    this.names = names;
  }

  // adds a name to this Roster's 
  // list of names
  void addName(String name) {
    this.names.add(name);
  }

  // returns the list of students in 
  // this Roster
  ArrayList<String> getNames() {
	 // creating a copy of list of names
    return new ArrayList<String>(this.names);
  }
}

// examples class!
class ExamplesRoster { 
  ArrayList<String> defaultRoster; 
  Roster volleyball;
  Roster tennis;
  Roster swimming;
  Roster figureSkating;
  Roster defaultr;

  void initData() {
    this.defaultRoster = new ArrayList<String>(Arrays.asList("Edward"));
    this.volleyball = new Roster(new ArrayList<String>(Arrays.asList("Edward")));
    this.tennis = new Roster(new ArrayList<String>(Arrays.asList("Edward")));
    this.swimming = new Roster(new ArrayList<String>(Arrays.asList("Edward")));
    this.figureSkating = new Roster(new ArrayList<String>(Arrays.asList("Edward")));
  }
  
  // shallow copy --> new ArrayList<String>(Arrays.asList((defaultr));
  
  // tests the addName method
  void testAddName(Tester t) {
    this.initData();
    
    t.checkExpect(this.volleyball.names.size(), 1);
    t.checkExpect(this.volleyball.names, new ArrayList<String>(Arrays.asList("Edward")));
    t.checkExpect(this.tennis.names, new ArrayList<String>(Arrays.asList("Edward")));
    
    this.volleyball.addName("Karen");

    t.checkExpect(this.volleyball.names.size(), 2); // 1 --> 2
    //t.checkExpect(this.volleyball.names, new ArrayList<String>(Arrays.asList("Edward", "Karen")));

    // why do the two tests below fail?
    
    // if you change the original, it will change the rest of these examples
    // changed default roster to be its own roster instead of this.defaultRoster
    
    
    t.checkExpect(this.tennis.names.size(), 1);
    t.checkExpect(this.tennis.names, new ArrayList<String>(Arrays.asList("Edward")));

  }
  
  // integers are primitive types and are mutable --> does not need a copy 
  // strings are immutable 

  // tests the getNames method
  void testGetNames(Tester t) {
    this.initData();

    t.checkExpect(this.figureSkating.getNames().size(), 1);
    t.checkExpect(this.figureSkating.getNames(), new ArrayList<String>(Arrays.asList("Edward")));
    t.checkExpect(this.swimming.getNames().size(), 1);
    t.checkExpect(this.swimming.getNames(), new ArrayList<String>(Arrays.asList("Edward")));

    ArrayList<String> fgList = this.figureSkating.getNames();
    fgList.add("Karen");

    t.checkExpect(fgList.size(), 2);
    t.checkExpect(fgList, new ArrayList<String>(Arrays.asList("Edward", "Karen")));

    // why do the three tests below fail?
    t.checkExpect(this.figureSkating.getNames(), new ArrayList<String>(Arrays.asList("Edward")));
    t.checkExpect(this.swimming.getNames().size(), 1);
    t.checkExpect(this.swimming.getNames(), new ArrayList<String>(Arrays.asList("Edward")));
  }
}

// Problem 2 : Cards (Mutation)

abstract class Card {
	int cnum;
	int year;
	int cvv;
	
	Card(int cnum, int year, int cvv) {
		this.cnum = cnum;
		this.year = year;
		this.cvv = cvv;
	}
	
}

class CreditCard extends Card {
	
	String name;
	
	CreditCard(int cnum, int year, int cvv, String name) {
		super(cnum, year, cvv);
		this.name = name;
	}
	
}

class DebitCard extends Card {
	
	String name;
	int balance;
	
	DebitCard(int cnum, int year, int cvv, String name , int balance) {
		super(cnum, year, cvv);
		this.name = name;
		this.balance = balance;
	}
	
	void makeTransaction(int withdrawal) {
		 this.balance -= withdrawal;
	}
	
	void addName(String name) {
		this.name = name;
	}
}

class GiftCard extends Card {
	
	int balance;
	
	GiftCard(int cnum, int year, int cvv, int balance) {
		super (cnum, year, cvv);
		this.balance = balance;
	}
	
	void makeTransaction(int withdrawal) {
		this.balance -= withdrawal;
	}
}

class ExampleCards {
	
	CreditCard cc;
	GiftCard gc;
	DebitCard dc;
	
	
	void initCards() {
	    this.cc = new CreditCard(123456789, 2025, 123, "");
	    this.gc = new GiftCard(987654321, 2026, 456, 100);
	    this.dc = new DebitCard(5555555, 2024,7890, "", 500);
	    
	}
	
	
	void addName(Tester t) {
		
		initCards();
		
		this.dc.addName("evelyn");
		t.checkExpect(dc.name, "evelyn");
	}
	
	void testMakeTransaction(Tester t) {
		initCards();
		this.dc.makeTransaction(50);
		t.checkExpect(dc.balance, 450);
		this.gc.makeTransaction(25);
		t.checkExpect(gc.balance, 75);
	}
}

//Problem 3: (iterators)

//to represent a person with a name 
//and their list of children (for a family tree)
class Person implements Iterable<Person> {
String name;
ArrayList<Person> listOfChildren;
int currentIndex;

// standard constructor
Person(String name, ArrayList<Person> children, int currentIndex) {
 this.name = name;
 this.listOfChildren = children;
 this.currentIndex = currentIndex;
}

// convenience constructor
Person(String name) {
 this.name = name;
 this.listOfChildren = new ArrayList<Person>();
}

void addChild(Person child) {
	this.listOfChildren.add(child);
}

public Iterator<Person> iterator() {
	return new FamilyIterator(this);
 }
}

class FamilyIterator implements Iterator<Person> {
	
	
	ArrayList<Person> toVisit;
	int currIndex;
	
	FamilyIterator(Person parent) {
		this.toVisit = new ArrayList<Person>();
		this.toVisit.add(parent);
		this.currIndex = 0;
	}
	
	// determines if the workList is not empty 
	public boolean hasNext() {
		return this.currIndex < this.toVisit.size();
	}
	
	public Person next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more people in this family tree");
		}
		
		Person nextP = toVisit.remove(0);
		this.toVisit.addAll(nextP.listOfChildren);
		return nextP;
	}
}


//to represent examples of Person
class ExamplesPerson {

ExamplesPerson() {
}

// simple example
Person c;
Person b;
Person a;

// complex example
Person len;
Person kim;
Person jan;
Person hank;
Person gabi;
Person fay;
Person ed;
Person dan;
Person cole;
Person bob;
Person ann;

// initializes the data and the family trees
void initData() {
 // simple example

 /*
  *      A
  *      |
  *     (B C)
  *      
  */

 this.c = new Person("C");
 this.b = new Person("B");
 this.a = new Person("A", new ArrayList<Person>(Arrays.asList(this.b, this.c)), 0);


 // complex example

 /*
  *            Ann
  *            |
  *           (Bob         Cole         Dan)
  *            |           |            |
  *   (Ed Fay Gabi Hank)  (Jan Kim)     ()
  *       |
  *      (Len)
  * 
  * 
  * Ann's children: Bob, Cole, and Dan
  * Bob's children: Ed, Fay, Gabi, and Hank
  * Cole's children: Jan and Kim
  * Fay's children: Len
  * 
  */

 this.len = new Person("Len");
 this.kim = new Person("Kim");
 this.jan = new Person("Jan");
 this.hank = new Person("Hank");
 this.gabi = new Person("Gabi");
 this.fay = new Person("Fay");
 this.fay.listOfChildren.add(this.len);
 this.ed = new Person("Ed");
 this.dan = new Person("Dan");
 this.cole = new Person("Cole");
 this.cole.listOfChildren.add(jan);
 this.cole.listOfChildren.add(kim);
 this.bob = new Person("Bob");
 this.bob.listOfChildren.add(ed);
 this.bob.listOfChildren.add(fay);
 this.bob.listOfChildren.add(gabi);
 this.bob.listOfChildren.add(hank);
 this.ann = new Person("Ann");
 this.ann.listOfChildren.add(bob);
 this.ann.listOfChildren.add(cole);
 this.ann.listOfChildren.add(dan);
 
}

void testForEachLoopForSimpleExample(Tester t) {
 this.initData();

 ArrayList<Person> aPeople = new ArrayList<Person>(
     Arrays.asList(this.a, this.b, this.c));
 ArrayList<Person> aTest = new ArrayList<Person>();

 for (Person p : aPeople) {
   System.out.println(p.name);
   aTest.add(p);
   // System.out.println(p.name);
 }

 // order should be A -> B -> C
 t.checkExpect(aTest, aPeople);
}

void testForEachLoopForComplexExample(Tester t) {
 this.initData();

 ArrayList<Person> annPeople = new ArrayList<Person>(
     Arrays.asList(this.ann, this.bob, this.cole,
         this.dan, this.ed, this.fay, this.gabi, this.hank,
         this.jan, this.kim, this.len));
 ArrayList<Person> annTest = new ArrayList<Person>();

 for (Person p : annPeople) {
   annTest.add(p);
   System.out.println(p.name);
 }

 // order should be Ann -> Bob -> Cole -> Dan -> Ed -> Fay
 //              -> Gabi -> Hank -> Jan -> Kim -> Len
 
 t.checkExpect(annTest, annPeople);
}

void testHasNextAndNextForComplexExample(Tester t) {
 this.initData();

 Iterator<Person> famIter = this.ann.iterator();

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.ann);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.bob);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.cole);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.dan);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.ed);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.fay);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.gabi);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.hank);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.jan);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.kim);

 t.checkExpect(famIter.hasNext(), true);
 t.checkExpect(famIter.next(), this.len);

 t.checkExpect(famIter.hasNext(), false);
 t.checkException(new NoSuchElementException("No more people in this family tree"), 
     famIter, "next");
  }
}



//Problem 5 Matrix (Iterators) 

class Matrix<T> implements Iterator<T> {
	
	ArrayList<ArrayList<T>> matrix;
	int row;
	int col;
	
	Matrix(ArrayList<ArrayList<T>> matrix) {
		this.matrix = matrix;
		this.row = 0;
		this.col = 0;
	}
	
	public boolean hasNext() {
		return (row < matrix.size() && col < matrix.get(row).size());
	}
	
	public T next() {
		T result = matrix.get(row).get(col);
		if (col == matrix.get(row).size() - 1) {
			row ++;
			col = 0;
		} else {
			col ++;
		}
		return result;
	}
}

class ArrayUtils {
	ArrayUtils(){}
	
	// create a nested for-loop
	
	
	// finds the largest numbers in each row
	int findValue(ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<Integer> largestNumberInEachRow = new ArrayList<Integer>();
		
		
		for (int row = 0; row < matrix.size(); row += 1) {
			int sizeOfRow = matrix.get(row).size();
			
			int maxSoFar = matrix.get(row).get(0);
			
			
			for (int col = 0; col < sizeOfRow; col += 1) {
				int curNum = matrix.get(row).get(col);
				
				if (curNum > maxSoFar) {
					maxSoFar = curNum;
				}
			}
			
			largestNumberInEachRow.add(maxSoFar);
		}
		
		int numCols = matrix.get(0).size();
		int numRows = matrix.size();
		int colIndexFoundMin = 0;
		
		
		for (int col = 0; col < numCols; col += 1) {
			
			int minSoFar = matrix.get(0).get(col);
			
			for (int row = 0; row < numRows; row += 1) {
				colIndexFoundMin = row;
				int curNum = matrix.get(row).get(col);
				
				if (curNum < minSoFar) {
					minSoFar = curNum;
				}
			}
			
 			if (largestNumberInEachRow.contains(minSoFar)) {
			  return minSoFar;	
			}
		}
		
		return -1;
	}
	
	//[3, 2, 6, 10, 1] -> 8
	int maxDiff(ArrayList<Integer> nums) {
		int minSoFar = nums.get(0);
		int maxDifference = 0;

		for (int current : nums) { // current 1 minSoFar 1 maxDifference 8
			int currDifference = current - minSoFar;
			
			if (minSoFar > current) {
				minSoFar = current;
			}
			
			if (currDifference > maxDifference) {
				maxDifference = currDifference;
			}
		}

		return maxDifference;
	}
	
	
	void testFindValue(Tester t) {
		
		ArrayUtils u = new ArrayUtils();
		
		ArrayList<Integer> row1 = new ArrayList<Integer>(Arrays.asList(2, 2, 3));
		ArrayList<Integer> row2 = new ArrayList<Integer>(Arrays.asList(1, 2, 2));
		ArrayList<Integer> row3 = new ArrayList<Integer>(Arrays.asList(3, 4, 1));

		
		ArrayList<ArrayList<Integer>> exampleMatrix = new ArrayList<ArrayList<Integer>>(
				Arrays.asList(row1, row2, row3));
			
		t.checkExpect(u.findValue(exampleMatrix), 2);
		
		}
	}


// Problem 6 (ArrayLists)







// Problem 7 maxDifference (ArrayList)

class ExamplesDifferences {
	
	ExamplesDifferences() {}
	
	
	
	ArrayList<Integer> ex1 = new ArrayList<Integer>(
			Arrays.asList(11, 2, 6, 4, 10, 7));
	ArrayList<Integer> ex2 = new ArrayList<Integer>(
			Arrays.asList(9, 8, 7, 6, 5));
	

	public int calculateDifference(ArrayList<Integer> list) {
		if (list == null || list.size() < 2) {
			return 0;
		}
		int min = list.get(0);
		int maxDiff = 0;
		for (int i = 1; i < list.size(); i++) {
			int diff = list.get(i) - min;
			if (diff > maxDiff) {
				maxDiff = diff;
			}
			if (list.get(i) < min) {
				min = list.get(i);
			}
		}
		return maxDiff;
	}	
	
	boolean testDifferences(Tester t) {
		return t.checkExpect(calculateDifference(ex1), 8)
				&& t.checkExpect(calculateDifference(ex2), 0);
		
	}
}



//Problem 8 (overriding .equals) 

class Student {
	String name;
	int age;
	
	Student(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	// when overriding the equals method you need to also 
	// override hashCode
	
	@Override
	public int hashCode() {
		return this.name.hashCode() * 100 + this.age;
		
	}
	
	//overriding the equals method so that it returns false
	//if the student does not exist 
	// and true if it's an example of a new student
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Student)) {
			return false;
		}
		
		Student that = (Student) obj;
		return this.name.equals(that.name) && this.age == that.age;
	}
}

class ExampleStudents {
	
	Student kelly = new Student("Kelly", 20);
	Student adam = new Student("Adam", 21);

	
	ArrayList<Student> students = 
			new ArrayList<Student>(Arrays.asList(this.kelly, this.adam));
	
	
	boolean testContains(Tester t) {
		return t.checkExpect(this.students.contains(this.kelly), true)
		&& t.checkExpect(this.students.contains(new Student("Kelly", 20)), true);
	}
	
	boolean testHash(Tester t) {
		return t.checkExpect(this.kelly.hashCode(), -1351912272)
				&& t.checkExpect(this.kelly.name.hashCode(), 72380223);
	}
}







































