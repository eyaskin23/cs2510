import tester.Tester;

interface IShape { 
	double accept(IVisitorShape visitor);
	
	double beAppliedToBy(IShape2DoubleFunc func);

	<R> R accept (IShapeVisitor<R> visitor);
}

class Circle implements IShape {
  int x, y;
  int radius;
  String color;
  Circle(int x, int y, int r, String color) {
	  this.x = x;
	  this.y = y;
	  this.radius = r;
	  this.color = color;
  }
  
  public double accept(IVisitorShape visitor) {
	  return visitor.visitCircle(this);
  }

@Override
public double beAppliedToBy(IShape2DoubleFunc func) {
	// TODO Auto-generated method stub
	return 0;
}

public <R> R accept (IShapeVisitor<R> visitor) {
	return visitor.visitCircle(this);
  }  
}

class Rect implements IShape {
  int x, y, w, h;
  String color;
  
  Rect(int x, int y, int w, int h, String color) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = color;
  }

public double accept(IVisitorShape visitor) {
	return visitor.visitRect(this);
  }
}

class Square implements IShape {
	int x;
	String color;
	
	Square(int x, String color) {
		this.x = x;
		this.color = color;
	}
}

interface IFunction<A,R> {
	R apply(A t);
}

class ShapeArea implements IFunction<IShape, Double> {
	public Double apply(IShape shape) {
		if (shape instanceof Circle) {
			return Math.PI * ((Circle)shape).radius * ((Circle)shape).radius;
		} else {
			return ((Rect)shape).w * ((Rect)shape).h * 1.0;
		}
	}
}

interface IVisitorShape{
	double visitCircle(Circle circle);
	double visitRect(Rect rect);
}

class VisitorShape implements IVisitorShape {
	public double visitCircle(Circle circle) {
		return Math.PI * circle.radius * circle.radius;
	}

	public double visitRect(Rect rect) {
		return rect.w * rect.h;
	}
} 

class ExamplesShapes implements IVisitorShape {
	ExamplesShapes() {}
	IShape c1 = new Circle(10, 10, 10, "red");
	IShape c2 = new Circle(50, 50, 30, "red");
	IShape c3 = new Circle(30, 100, 30, "blue");

	IShape r1 = new Rect(50, 50, 30, 30, "red");
	IShape r2 = new Rect(50, 50, 50, 40, "red");
	IShape r3 = new Rect(20, 40, 10, 20, "green");

	IList<IShape> shapes = new ConsList<IShape>( new Circle(0,0,10,"red"),
			new ConsList<IShape>( new Rect(0,0,10,10,"red"),
					new MtList<IShape>()));
	
	IList<Double> expectedList = new ConsList<Double>( 314.15, 
								new ConsList<Double>( 100.00, 
										new MtList<Double>()));
	
	
boolean testShapes(Tester t) {
	return t.checkExpect(c1.accept(this), 0.0);
  }


public double visitCircle(Circle circle) {
	return 0;
}


public double visitRect(Rect rect) {
	return 0;
  }
}

interface IShape2DoubleFunc {
	
	double applyToCircle(Shape2Area shape2Area);
	double applyToRect(Rect rect);
}




// a function object that computes the area of iSHapes 

class Shape2Area implements IShape2DoubleFunc {
	public double applyToCircle(Circle circle) {
			return Math.PI * circle.radius * circle.radius;
	}
			
	public double applyToRect(Rect rect) {
		return rect.w * rect.h;
	}
	
	public double beAppliedToBy(IShape2DoubleFunc func) {
		return func.applyToCircle(this);
	}

	public double applyToCircle(Shape2Area shape2Area) {
		return 0;
	}
}

interface IShapeVisitor<R> {
	R visitCircle(Circle x);
	R visitRect(Rect y);
	R visitSquare(Square z);
}








