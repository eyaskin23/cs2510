
import tester.Tester;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import java.awt.Color;
import javalib.funworld.*; 


//an interface to represent a game piece in the game
interface IGamePiece {
	//produce a new game piece that is shifted by x an y from this IGamePiece
	IGamePiece move(int x, int y);
	//draws this GamePiece onto the canvas
	WorldImage draw();
}

//a class to represent an invader in the game
class Invader implements IGamePiece {
	Location loc;
	Color color;
	int bullets;
	int size;

	Invader(Location loc, Color color, int bullets, int size) {
		this.loc = loc;
		this.color = color;
		this.bullets = bullets;
		this.size = size;
	}

	/* fields:
	 *  this.loc ... Location
	 *  this.color ... Color
	 *  this.bullets ... int
	 *  this.size ... int
	 * methods:
	 *  this.move(int, int) ... IGamePiece
	 *  this.draw() ... WorldImage
	 * methods for fields:
	 *  this.loc.move(int, int)  
	 * 
	 */

	//produce an Invader that is shifted by x and y from this Invader
	public IGamePiece move(int x, int y) {
		return new Invader(this.loc.moveLocation(x, y), this.color, this.bullets, this.size);
	}

	//draw this spaceship as a circle
	public WorldImage draw() {
		return new EllipseImage(50, 60, "solid", this.color);
	}
}


//a class to represent a spaceship in the game 
class Spaceship  implements IGamePiece {
	Location loc;
	Color color;
	int speed; // in miles per hour

	Spaceship(Location loc, Color color, int speed) {
		this.loc = loc;
		this.color = color;
		this.speed = speed;
	}

	/* fields:
	 *  this.loc ... Location
	 *  this.color ... Color
	 *  this.speed ... int
	 * methods:
	 *  this.move(int, int) ... IGamePiece
	 *  this.draw() ... WorldImage
	 * methods for fields:
	 *  this.loc.move(int, int)  
	 * 
	 */
	
	//produce an Spaceship that is shifted by x and y from this Spaceship
	public IGamePiece move(int x, int y) {
		return new Spaceship(this.loc.moveLocation(x, y), this.color, this.speed);
	}

	//draw this spaceship as a circle
	public WorldImage draw() {
		return new CircleImage(50, "solid", this.color);
	}

}



//a class to represent a location on the Cartesian plane
class Location {
	int x;
	int y;

	Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/* fields:
	 *   this.x ... int
	 *   this.y ... int
	 * methods:  
	 *   this.moveLocation(int, int) ... Location
	 */

	//produces a new Location that is shifted by x and y from this Location
	Location moveLocation(int x, int y) {
		return new Location(this.x + x, this.y + y);
	}
}




class ExamplesGamePieces {
	Location loc1 = new Location(30, 90);
	Location loc2 = new Location(260,180);

	IGamePiece ship1 = new Spaceship(this.loc1, Color.BLUE, 55);
	IGamePiece invader1 = new Invader(this.loc2, Color.PINK, 30, 3);
	Spaceship ship2 = new Spaceship(this.loc1, Color.BLUE, 55);


	//tests for move
	boolean testMove(Tester t) {
		return t.checkExpect(this.ship1.move(3, 2), new Spaceship(new Location(33, 92), Color.BLUE, 55)) &&
				t.checkExpect(this.invader1.move(3, 2), new Invader(new Location(263, 182), Color.PINK, 30, 3));
	}

	//test for drawing a spaceship
	boolean testDraw(Tester t) {
		return t.checkExpect(this.ship1.draw(), new CircleImage(50, "solid", Color.BLUE));
	}

	//draw the spaceship
	boolean testDrawing(Tester t) {
		WorldCanvas c = new WorldCanvas(300, 500);
		WorldImage img = this.ship2.draw();
		WorldScene s1 = new WorldScene(300, 500).placeImageXY(img, this.loc1.x, this.loc1.y);
		WorldScene s2 = s1.placeImageXY(this.invader1.draw(), this.loc2.x, this.loc2.y);
		c.show();
		c.drawScene(s2);
		return true;
	}


}