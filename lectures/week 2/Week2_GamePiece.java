import tester.Tester;

interface IGamePiece {
	
}


//a class to represent a spaceship
class Spaceship implements IGamePiece {
	Location loc;
	String color;
	double speed; //in mph

	Spaceship(Location location, String color, double speed) {
		this.loc = location;
		this.color = color;
		this.speed = speed;
	}	
	
	/* fields:
	 *  this.loc ... Location
	 *  this.color ... String
	 *  this.speed ... double
	 * methods:
	 *  
	 * methods for fields:
	 *  this.loc.shiftXY(int, int)
	 *  this.loc.sameLocation(int, int)  
	 */
}

//class to represent an invader
class Invader implements IGamePiece {
	Location loc;
	String color;
	int bullets;
	
	Invader(Location loc, String color, int bullets) {
		this.loc = loc;
		this.color = color;
		this.bullets = bullets;
	}
	
	/* fields:
	 *  this.loc ... Location
	 *  this.color ... String
	 *  this.bullets ... int
	 * methods:
	 *  
	 * methods for fields:
	 *  this.loc.shiftXY(int, int)
	 *  this.loc.sameLocation(int, int)  
	 */
}

//a class to represent a point on the Cartesian plane
class Location {
	int x;
	int y;

	Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/* fields: 
	 *  this.x ... int
	 *  this.y ... int
	 * methods:
	 *  this.shiftXY(int, int) ... Location 
	 *  this.sameLocation(Location) .. boolean
	 */

}

class ExamplesGame {
	
	Location loc30_40 = new Location(30, 40);
	Location loc80_120 = new Location(80, 120);
	IGamePiece ship1 = new Spaceship(this.loc30_40, "red", 67.0);
	IGamePiece ship2 = new Spaceship(this.loc80_120, "green", 100.75);
	IGamePiece invader1 = new Invader(new Location(50, 800), "black", 60);
	IGamePiece invader2 = new Invader(new Location(50, 800), "yellow", 60);

	
	ExamplesGame() {}

}




