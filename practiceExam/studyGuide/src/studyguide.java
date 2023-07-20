import tester.Tester;

// Study Guide for Fundies2 Exam 1:

/* Union Data : includes an interface and instances of that interface
 * this also comes with examples
 * 
 */


interface IStation {
	
}

class TStop implements IStation {
	String name; 
	String line;
	double price;
	
	TStop(String name, String line, double price) {
		this.name = name;
		this.line = line;
		this.price = price;
	}
}

class ExampleStops {
	IStation harvard = new TStop("Harvard", "red", 1.25);
}
/* Trees: 
 * list of variables, following after each other
 */

interface IAT {}

class Unknown implements IAT {
	Unknown() {}
}

class Person implements IAT {
	String name;
	IAT mom;
	IAT dad;
	
	Person(String name, IAT mom, IAT dad) {
		this.name = name;
		this.mom = mom;
		this.dad = dad;
	}
}

class ExamplesAncestors {
	ExamplesAncestors() {}

IAT unknown = new Unknown();
IAT mary = new Person("Mary", this.unknown, this.unknown);
IAT robert = new Person("Robert", this.unknown, this.unknown);
IAT john = new Person("John", this.unknown, this.unknown);

IAT jane = new Person("Jane", this.mary, this.robert);

IAT dan = new Person("Dan", this.jane, this.john);
}

/*
 *           Dan
          /       \
      Jane        John
    /      \      /  \
  Mary   Robert  ?    ?
 /   \    /   \
?     ?  ?     ?
 */

//Templates need to go in every class/method/method on field 




class Book { 
	String title;
	String author;
	int price; //in dollars
	
	//the constructor
	Book(String title, String author, int price) {
		this.title = title;
		this.author = author;
		this.price = price;
		
		/* TEMPLATE:
		Fields:
		... this.title ...         -- String
		... this.author ...        -- String
		... this.price ...         -- int

		Methods:
		... this.salePrice(int) ... -- int
		... this.reducePrice() ...  -- Book
		*/
		
	}
}


       //////////////SHAPES/////////////////////////

//to represent a geometric shape
interface IShape {
	//to compute the area of this shape
	double area();
}

class Circle implements IShape {
	int x;
	int y;
	int radius;
	String color;
	
	Circle(int x, int y, int radius, String color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}
	
	/* TEMPLATE
    FIELDS:
    ... this.x ...                   -- int
    ... this.y ...                   -- int
    ... this.radius ...              -- int
    ... this.color ...               -- String
    METHODS
    ... this.area() ...              -- double
 */

 // to compute the area of this shape
 public double area() {
   return Math.PI * this.radius * this.radius;
 }
}

class ExamplesShapes {
	ExamplesShapes() {}
	
	IShape c1 = new Circle(50, 50, 10, "red");
	
	boolean testIShapeArea(Tester t) {
		return t.checkInexact(this.c1.area(), 314.15, 0.01);
	}
}
















