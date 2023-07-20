importer tester.Tester;

interface IGamePiece {
	//move this IGamePiece by the given dx and dy 
	move(int dx, int dy); 
}

//a class to represent a spaceship
class Spaceship implements IGamePiece {
	Location loc;
	String color;
	int speed; //in mph

	Spaceship(Location location, String color, int speed) {
		this.loc = location;
		this.color = color;
		this.speed = speed;
	
	
	 /* fields:
	 *  this.loc ... Location 
	 *  this.color ... String
	 *  this.speed ... int 
	 * methods:
	 *  this.reduceSpeed(int) ... int
	 *  this.move
	 * methods for fields:
	 *  this.loc.shiftXY(int, int) ... Location
	 *  this.loc.sameLocation(int, int)
	 * 
	 */
		
		
	}
	
	// return the speed that is reduced by the given rate
	int reduceSpeed(int rate) {
		return this.speed - this.speed * rate/100;
	}

//create a new spaceship that is moved by the given dx and dy
Spaceship move(int dx, int dy) {
	return new Spaceship(this.loc.shiftXY(dx, dy), this.color, this.speed);
  } 

// is the given spaceship in the same location as this one?
 boolean sameLocation(Spaceship that ) {
	 /* (can access the fields of the parameter because it's the
	  * same type as tis
	  * that.loc ... Location
	  * that.color ... String
	  * that.speed ... int
	  * that.reduceSpped(int) ... int
	  * that.move (int, int) ... Spaceship
	  * that.sameLocation(Spaceship) ... boolean 
	  */

    return this.x == that.x && that.x == that.y;
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
	
	/* fields:
	 *  this.x ... int
	 *  this.y ... int 
	 * methods:
	 *  this.shiftXY(int, int) ... Location 
	 *  
	 * 
	 */

	
	//create a new Location that is shifted by the given dx and dy
	Location shiftXY(int dx, int dy) {
		return new Location(this.x + dx, this.y + dy);
	}
	
}

class ExamplesSpaceship {
	Location loc30_40 = new Location(30, 40);
	Location loc80_120 = new Location(60, 80);
	Spaceship ship1 = new Spaceship(this.loc30_40, "red", 100);
	Spaceship ship2 = new Spaceship(this.loc80_120, "green", 120);
	
	//tests for reduceing speed of a spaceship
	boolean testReducedSpeed(Tester t) {
	   return t.checkExpect(this.ship1.reduceSpeed(50), 50) &&
	          t.checkExpect(this.ship1.reduceSpeed(20), 80);
}

	//test for moving spaceships
    boolean testMove(Tester t) {
    	return t.checkExpect(this.ship2.move(5, 10),
    			new Spaceship(new Location(65, 90), "green", 120)) &&
    			t.checkExpect(this.ship1.move(0,1),
    			      new Spaceship(new Location (30,41), "red", 100)) &&
    			t.checkExpect(this.loc30_40)
    			
    }
    
    // tests for some Location
    boolean testSameLocation(Tester t) {
    	return t.checkExpect(this.ship1.sameLocation(ship 2), false)
    			
    }
    
}

















