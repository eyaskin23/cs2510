import tester.Tester;


// sameness: 
// t.checkexpect (4 = 4, true)
// t.checkExpect(true == false, false)
// t.checkExpect(17.9, 17.9, true)
// t.checkExpect(19.0 / 10.0 == 1.9, false) //bad idea
// better: 
// t.checkExpect(19.0 / 10.0 - 1.9 < 0.001) // 

// t.checkExpect("hello" == "hel".concat ("lo"), false)
// Using String.equals() is better



// to represent a geometric shape
interface IShape {
	
   Boolean sameShape(IShape that);
   
   Boolean sameCircle(Circle that);
   
   Boolean sameSquare(Square that);
   
   Boolean sameRect(Rect that);
    
}

//represents an abstract shape
abstract class AShape implements IShape {
	CartPt location;
	String color;
	
	AShape(CartPt loc, String color) {
		this.location = loc;
		this.color = color;
	}
	
	public boolean isCircle() {
		return false;
	}
	
	public boolean isRect() {
		return false;
	}
	
}

// to represent a circle
class Circle implements IShape { 
	int x, y;
	int radius;
	private int height;
	private int width;
	
	Circle(int x, int y, int radius ) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	

    public Circle(CartPt pt1, int y2, String string) {
   
	}


	/*  TEMPLATE 
     Fields:
     ... this.location ...        -- CartPt
     ... this.radius ...             -- int
     ... this.color ...           -- String
     Methods:
     ... this.sameCircle(Circle) ... boolean
     Methods for fields:
     ... this.location.distTo0() ...           -- double 
     ... this.location.distTo(CartPt) ...      -- double 
     ... this.location.sameLocation(Location) ... boolean

     */
    
    //is this Circle the same as the given one?
	
	public Boolean sameShape(IShape that) {
		return that.sameCircle(this);
	}
}

// to represent a square
class Square extends Rect {
    
    Square(CartPt nw, int size, String color) {
    	super(nw, size, size, color);
    }
    
    /*  TEMPLATE 
     Fields:
     ... this.location ...              -- CartPt
     ... this.width ...            -- int
     ... this.height ...           -- int
     ... this.color ...           -- String
     Methods:
     ... this.sameSquare(Square) ... boolean
     Methods for fields:
     ... this.location.distTo0() ...            -- double 
     ... this.location.distTo(CartPt) ...       -- double 
     ... this.location.sameLocation(Location) ... boolean
     */
    
    //is this Square the same as the given one?
    public Boolean sameSquare(Square s) {
    	return this.location.sameLocation(s.location) &&
    			this.color.equals(s.color) &&
    			this.height == s.height;
    }
}

// to represent a rectangle
class Rect extends AShape {
    public int x;
	public int y;
	int width;
    int height;
	private int radius;
    
    Rect(CartPt nw, int width, int height, String color) {
    	super(nw, color);
        this.width = width;
        this.height = height;
    }
    
     /* TEMPLATE
     FIELDS
     ... this.location ...              -- CartPt
     ... this.width ...           -- int
     ... this.height ...          -- int
     ... this.color ...           -- String
     METHODS
     ... this.sameRect(Rect) ... boolean
     METHODS FOR FIELDS:
     ... this.location.distTo0() ...        -- double
     ... this.location.distTo(CartPt) ...   -- double
     ... this.location.sameLocation(Location) ... boolean
     */
    
  //is this Rectangle the same as the given one?
    
    public boolean sameRect(IShape that) {
    	return that.sameRect(this);
    }
	

	public Boolean sameCircle(Circle that) {
		return false;
	}

	public Boolean sameSquare(Square that) {
		return false;
	}

	public Boolean sameShape(IShape that) {
		return false;
	}

	public Boolean sameRect(Rect that) {
		return false;
	}
}


class Combo implements IShape {
	IShape top;
	IShape bottom;
	
	Combo(IShape top, IShape bottom) {
		this.top = top;
		this.bottom = bottom;
	}
	
	/* fields:
	 *   this.top ... IShape
	 *   this.bottom ... IShape
	 * methods:
	 * 
	 * methods for fields:  
	 * 
	 */
	
	public Boolean sameSquare(Square that) {
		return that.sameSquare(that);
	}

	public Boolean sameShape(IShape that) {
		return false;
	}

	public Boolean sameCircle(Circle that) {
		return false;
	}

	@Override
	public Boolean sameRect(Rect that) {
		return false;
	}
}



// to represent a Cartesian point
class CartPt {
    int x;
    int y;
    
    CartPt(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /* TEMPLATE
     FIELDS
     ... this.x ...          -- int
     ... this.y ...          -- int
     METHODS
     ... this.distTo0() ...        -- double
     ... this.distTo(CartPt) ...   -- double
     ... this.sameLocation(Location) ... boolean
     */
    
    // to compute the distance form this point to the origin
    public double distTo0(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    // to compute the distance form this point to the given point
    public double distTo(CartPt pt){
    	/* Everything in the class template plus:
        FIELDS of pt
        ... pt.x ...          -- int
        ... pt.y ...          -- int
        METHODS of pt
        ... pt.distTo0() ...        -- double
        ... pt.distTo(CartPt) ...   -- double
        ... pt.sameLocation(Location) ... boolean
        */
        return Math.sqrt((this.x - pt.x) * (this.x - pt.x) + 
                         (this.y - pt.y) * (this.y - pt.y));
    }
    
    //is this location the same as the given one?
    boolean sameLocation(CartPt that) {
    	/* Everything in the class template plus:
        FIELDS of pt
        ... pt.x ...          -- int
        ... pt.y ...          -- int
        METHODS of pt
        ... pt.distTo0() ...        -- double
        ... pt.distTo(CartPt) ...   -- double
        ... pt.sameLocation(Location) ... boolean
        */
    	return this.x == that.x && this.y == that.y;
    }
}

class ExamplesShapes {
    ExamplesShapes() {}
    
    CartPt pt1 = new CartPt(0, 0);
    CartPt pt2 = new CartPt(3, 4);
    CartPt pt3 = new CartPt(7, 1);
    
    IShape c1 = new Circle(new CartPt(50, 50), 10, "red");
    IShape c2 = new Circle(new CartPt(50, 50), 30, "red");
    IShape c3 = new Circle(new CartPt(30, 100), 30, "blue");
    
    IShape s1 = new Square(new CartPt(50, 50), 30, "red");
    IShape s2 = new Square(new CartPt(50, 50), 50, "red");
    IShape s3 = new Square(new CartPt(20, 40), 10, "green");
    
    IShape r1 = new Rect(new CartPt(50, 50), 30, 20, "red");
    IShape r2 = new Rect(new CartPt(50, 50), 50, 40, "red");
    IShape r3 = new Rect(new CartPt(20, 40), 10, 20, "green");
    
    Circle c4 = new Circle(this.pt1, 4, "pink");
    Circle c5 = new Circle(this.pt1, 5, "yellow");
    Circle c6 = new Circle(this.pt1, 4, "pink");
    Rect r4 = new Rect(this.pt2, 4, 5, "black");
    Rect r5 = new Rect(this.pt1, 5, 6, "red");
    Rect r6 = new Rect(this.pt2, 4, 5, "black");
    
    Square s4 = new Square(this.pt1, 5, "red");
    Square s5 = new Square(this.pt2, 4, "black");
    Square s6 = new Square(this.pt1, 5, "red");

     
    boolean testSameCircRectSquare(Tester t) {
    	return
    			t.checkExpect(c4.sameCircle(c5), false)
    			&& t.checkExpect(c4.sameCircle(c6), true)
     
    			&& t.checkExpect(r4.sameRect(r5), false)
    			&& t.checkExpect(r4.sameRect(r6), false)
    			
    			&& t.checkExpect(s4.sameSquare(s5), false)
    			&& t.checkExpect(s4.sameSquare(s6), true);
    }
    
    // test the method distTo0 in the class CartPt
    boolean testDistTo0(Tester t) { 
        return
        t.checkInexact(this.pt1.distTo0(), 0.0, 0.001) &&
        t.checkInexact(this.pt2.distTo0(), 5.0, 0.001);
    }
    
    // test the method distTo in the class CartPt
    boolean testDistTo(Tester t) { 
        return
        t.checkInexact(this.pt1.distTo(this.pt2), 5.0, 0.001) &&
        t.checkInexact(this.pt2.distTo(this.pt3), 5.0, 0.001);
    }
    
  
}
