import tester.*;

import java.awt.Color;
import javalib.funworld.WorldScene;
import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.*;



// to represent a geometric shape
interface IShape {

	WorldImage draw();
	
}

// to represent a circle
class Circle implements IShape {
    CartPt center;
    int radius; //measured in centimeters
    String color;
    
    Circle(CartPt center, int radius, String color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

	@Override
	public WorldImage draw() {
		// TODO Auto-generated method stub
		return null;
	}
    
    /*
     // ** TEMPLATE ** 
    fields
     ... this.center ...              -- CartPt
     ... this.radius ...              -- int
     ... this.color ...               -- String
     methods:
     ... this.area() ...                  -- double 
     ... this.distToOrigin() ...          -- double 
     ... this.grow(int inc) ...           -- IShape
     ... this.biggerThan(IShape that) ... -- boolean
     methods for fields:
     ... this.nw.distToOrigin() ... double
     ... this.nw.distTo(CartPt) ... double
       
     */
    
    
}

// to represent a square
class Square implements IShape {
    CartPt nw;
    int size;
    String color;
    
    Square(CartPt nw, int size, String color) {
        this.nw = nw;
        this.size = size;
        this.color = color;
    }

	@Override
	public WorldImage draw() {
		// TODO Auto-generated method stub
		return null;
	}

    
    /*
     // ** TEMPLATE ** 
     fields:
     ... this.nw ...              -- CartPt
     ... this.size ...            -- int
     ... this.color ...           -- String
     methods:
     ... this.area() ...                  -- double 
     ... this.distToOrigin() ...          -- double 
     ... this.grow(int inc) ...           -- IShape
     methods for fields:
     ... this.nw.distToOrigin() ... double
     ... this.nw.distTo(CartPt) ... double
     
     */
      
}

/*
class Combo implements IShape {
	IShape top;
	IShape bottom;
	
	Combo(IShape top, IShape bottom) {
		this.top = top;
		this.bottom = bottom;
	}
	
	/* fields:
	 *  this.top ... IShape
	 *  this.bottom ... IShape
	 * methods:
	 *  this.area() ... double
	 *  this.grow(int) ... IShape
	 *  this.distToOrigin() ... double
	 *  this.biggerThan(IShape) ... boolean
	 * methods for fields:
	 *  this.top.area() ... double
	 *  this.top.grow(int) ... IShape
	 *  this.top.distToOrigin() ... double
	 *  this.top.biggerThan(IShape) ... boolean  
	 *  
	 *  this.bottom.area() ... double
	 *  this.bottom.grow(int) ... IShape
	 *  this.bottom.distToOrigin() ... double
	 *  this.bottom.biggerThan(IShape) ... boolean
	 * 
	 


}

*/

/*
 +--------+
 | CartPt |
 +--------+
 | int x  |
 | int y  |
 +--------+
 
 */

// to represent a Cartesian point
class CartPt {
    int x;
    int y;
    
    CartPt(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /* fields:
     *  this.x ... int
     *  this.y ... int
     *  
     *  methods:
     *   this.distToOrigin() ... double
     *   this.distTo(CartPt) ... double
     *   
     *  methods for fields: none 
     * 
     */
    
    // to compute the distance form this point to the origin
    public double distToOrigin(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    // to compute the distance form this point to the given point
    public double distTo(CartPt pt){
        return Math.sqrt((this.x - pt.x) * (this.x - pt.x) + 
                         (this.y - pt.y) * (this.y - pt.y));
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
    
   // IShape combo1 = new Combo(c1,s1);
   // IShape combo3 = new Combo(s3, c2);
    
   // IShape combo2 = new Combo(this.combo1, this.combo3);
    
    // test the method distToOrigin in the class CartPt
    boolean testDistToOrigin(Tester t) { 
        return
        t.checkInexact(this.pt1.distToOrigin(), 0.0, 0.001) &&
        t.checkInexact(this.pt2.distToOrigin(), 5.0, 0.001);
    }
    
    // test the method distTo in the class CartPt
    boolean testDistTo(Tester t) { 
        return
        t.checkInexact(this.pt1.distTo(this.pt2), 5.0, 0.001) &&
        t.checkInexact(this.pt2.distTo(this.pt3), 5.0, 0.001);
    }
    
 /*  
    // test the method area in the class Circle
    boolean testCircleArea(Tester t) { 
        return
        t.checkInexact(this.c1.area(), 314.15, 0.01);
    }
    
    // test the method grow in the class Circle
    boolean testSquareArea(Tester t) { 
        return
        t.checkInexact(this.s1.area(), 900.0, 0.01);
    }
    
    
    // test the method grow in the class Circle
    boolean testComboArea(Tester t) { 
        return
        t.checkInexact(this.combo.area(), 314.15 + 900.0 + 100, 0.01);
    }
    
    
    // test the method distToOrigin in the class Circle
    boolean testCircleDistToOrigin(Tester t) { 
        return
        t.checkInexact(this.c1.distToOrigin(), 60.71, 0.01) &&
        t.checkInexact(this.c3.distToOrigin(), 74.40, 0.01);
    }
    
    // test the method distTo in the class Circle
    boolean testSquareDistToOrigin(Tester t) { 
        return
        t.checkInexact(this.s1.distToOrigin(), 70.71, 0.01) &&
        t.checkInexact(this.s3.distToOrigin(), 44.72, 0.01);
    }
   
   
    
    // test the method grow in the class Circle
    boolean testCircleGrow(Tester t) { 
        return
        t.checkExpect(this.c1.grow(20), this.c2);
    }
    
    // test the method grow in the class Circle
    boolean testSquareGrow(Tester t) { 
        return
        t.checkExpect(this.s1.grow(20), this.s2);
    }
    
    
    // test the method biggerThan in the class Circle
    boolean testCircleBiggerThan(Tester t) { 
        return
        t.checkExpect(this.c1.biggerThan(this.c2), false) && 
        t.checkExpect(this.c2.biggerThan(this.c1), true) && 
        t.checkExpect(this.c1.biggerThan(this.s1), false) && 
        t.checkExpect(this.c1.biggerThan(this.s3), true);
    }
    
    // test the method biggerThan in the class Square
    boolean testSquareBiggerThan(Tester t) { 
        return
        t.checkExpect(this.s1.biggerThan(this.s2), false) && 
        t.checkExpect(this.s2.biggerThan(this.s1), true) && 
        t.checkExpect(this.s1.biggerThan(this.c1), true) && 
        t.checkExpect(this.s3.biggerThan(this.c1), false);
    }
     */
      
  	WorldCanvas testDraw(Tester t) {
  		WorldCanvas c = new WorldCanvas(300, 500);
  		WorldImage img1 = this.c1.draw();
  		WorldScene s = new WorldScene(300, 500).placeImageXY(img1, 150, 150);
  		c.show();
  		c.drawScene(s);
  		return c; 
  	}
}
