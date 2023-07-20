
interface IShape {
	
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
}

interface IFunction<A, R> {
	R apply(A t);
}

class ShapeArea implements IFunction<IShape, Double> {
	public Double apply(IShape shape) {
		if (shape instanceof Circle) {
			return Math.PI * ((Circle)shape).radius
			* ((Circle)shape).radius;		
		} else {
			return ((Rect)shape).w * ((Rect)shape).h * 1.0;
		}
	}
}

interface IVisitorShape {

	double visitCircle(Circle circle);
	double visitRect(Rect rect);
}
