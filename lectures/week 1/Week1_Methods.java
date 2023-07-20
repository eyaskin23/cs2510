
//a class to represent a spaceship
class Spaceship {
	Location loc;
	String color;
	int speed; //in mph

	Spaceship(Location location, String color, int speed) {
		this.loc = location;
		this.color = color;
		this.speed = speed;
	}	
}

//a class to represent a point on the Cartesian plane
class Location {
	int x;
	int y;

	Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

}

class ExamplesSpaceship {
	Location loc30_40 = new Location(30, 40);
	Location loc80_120 = new Location(60, 80);
	Spaceship ship1 = new Spaceship(this.loc30_40, "red", 100);
	Spaceship ship2 = new Spaceship(this.loc80_120, "green", 120);
}















