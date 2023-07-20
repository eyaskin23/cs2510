import java.util.ArrayDeque;
import java.util.ArrayList;
//import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
//import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;


abstract class ACell {
	
	ACell up;
	ACell down;
	ACell right;
	ACell left;
	int cellSize;
	int solveState;
	int X;
	int Y;
	int startSolve;
	int endSolve;
	boolean traveled;
	boolean solved;

	
	ACell(ACell up, ACell down, ACell left, ACell right, int cellSize) {
		
		this.up = up;
		this.down = down;
		this.right = right;
		this.left = left;
		this.cellSize = cellSize;
		this.solveState = 0;
		this.X = 0;
		this.Y = 0;	
	}
	
   ACell(int cellSize, int X, int Y) {
	   this.up = new Side(cellSize);
	   this.down = new Side(cellSize);
	   this.left = new Side(cellSize);
	   this.right = new Side(cellSize);
	   this.solveState = 0;
	   this.X = X;
	   this.Y = Y;
  }
  
   public void updateCell(ACell other, boolean vertical) { 
	  if (!vertical) {
		  this.down = other;
		  other.up = this;
	  }
	  
	  else {
		  this.right = other;
		  other.left = this;
	  }
  }
  
  abstract WorldImage drawCell();
  
  public void updateSolve(String prevDir, int change, boolean isFromStart) {
	    if (!prevDir.equals("Up") && !prevDir.equals("Down") && !prevDir.equals("Left")
	        && !prevDir.equals("Right") && !prevDir.equals("Start")) {
	      throw new IllegalArgumentException("A non start or end cell has to come from somewhere");
	    }

	    if (!prevDir.equalsIgnoreCase("Down")) {
	      this.up.updateSolve("Up", change + 1, isFromStart);
	    }

	    if (!prevDir.equalsIgnoreCase("Right")) {
	      this.left.updateSolve("Left", change + 1, isFromStart);
	    }

	    if (!prevDir.equalsIgnoreCase("Left")) {
	      this.right.updateSolve("Right", change + 1, isFromStart);
	    }

	    if (!prevDir.equalsIgnoreCase("Up")) {
	      this.down.updateSolve("Down", change + 1, isFromStart);
	    }

	    if (isFromStart) {
	      this.startSolve = change;
	    }
	    else {
	      this.endSolve = change;
	    }
	  }
  
  public ArrayList<ACell> getNeighbors() {
	  ArrayList<ACell> neighbors = new ArrayList<ACell>();
	  if (!(this.right instanceof Side)) {
		  neighbors.add(right);
	  }
	  if (!(this.down instanceof Side)) {
		  neighbors.add(down);
	  } if (!(this.left instanceof Side)) {
		  neighbors.add(right);
	  }
	  if (!(this.up instanceof Side)) {
		  neighbors.add(up);
	  }
	  return neighbors;
	  
  }
  
  
  int findLongestPath(String dis) {
	  if (!dis.equals("Up") && !dis.equals("Down") && !dis.equals("Left") && !dis.equals("Right")
			  && !dis.equals("Start")) {
		  throw new IllegalArgumentException("");
	  } 
	  int max = 0; 
	  if (!dis.equalsIgnoreCase("Down")) {
		 max = Math.max(max, 1 + this.up.findLongestPath("Up"));
	  } else if (!dis.equalsIgnoreCase("Right")) {
		  max = Math.max(max, 1 + this.left.findLongestPath("Left"));
	  } else if (!dis.equalsIgnoreCase("Left")) {
		  max = Math.max(max, 1 + this.right.findLongestPath("Right"));
	  } else if (!dis.equalsIgnoreCase("Up")) {
		  max = Math.max(max, 1 + this.down.findLongestPath("Down"));
	  }
	return max;
  }
  
  WorldImage drawSide(Color color) {
	  WorldImage sides = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.OUTLINE, color);
	  WorldImage newSide;
	  if (this.up.up == null) {
		  newSide = new LineImage(new Posn(this.cellSize, 0), Color.gray);
		  sides = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP, newSide, 0, 0, sides);
		  
	  }
	  else if (this.down.down == null) {
		  newSide = new LineImage(new Posn(this.cellSize, 0), Color.gray);
		  sides = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.BOTTOM, newSide, 0, 0, sides);
		  
	  }
	  else if (this.left.left == null) {
		  newSide = new LineImage(new Posn(this.cellSize, 0), Color.gray);
		  sides = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, newSide, 0, 0, sides);
		  
	  }
	  else if (this.right.right == null) {
		  newSide = new LineImage(new Posn(this.cellSize, 0), Color.gray);
		  sides = new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.MIDDLE, newSide, 0, 0, sides);
		  
	  }
	  return sides;
  }
  
  boolean hasPath(ACell other, boolean vertical) {
	  return (vertical && (this.up.equals(other) && other.down.equals(this))
	    || (this.up.equals(other) && other.down.equals(this)))
			  || (this.left.equals(other) && other.right.equals(this))
                || (this.right.equals(other) && other.left.equals(this));
  }
  
  WorldImage drawSolveCell(boolean beginning, int longestPath) {
	  Color c;
	  int max;
	  if (beginning) {
		  max = (int) (((1 * this.startSolve) / longestPath) * 200);
	  } else {
		  max = (int) (((1 * this.endSolve) / longestPath) * 200);
	  }
	  c = new Color(200 - max, 0, max);
	  WorldImage cell = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, c);
	  cell = new OverlayImage(this.drawSide(c), cell);
	  return cell;
  }
  
  //makes sure that each cell has existing neighbors 
  public int neighbors() {
	  int count = 0;
	  if (!(this.up instanceof Side)) {
		  count ++;
	  }
	  else if (!(this.down instanceof Side)) {
		  count ++;
	  }
	  else if (!(this.left instanceof Side)) {
		  count ++;
	  }
	  else if (!(this.right instanceof Side)) {
		  count ++;
	  }
	  return count;
  }
  
  public ArrayList<ACell> returnNeighbors() {
	  ArrayList<ACell> neighbors = new ArrayList<ACell>();
	  if (!(this.up instanceof Side)) {
		  neighbors.add(this.up);
	  }
	  else if (!(this.left instanceof Side)) {
		  neighbors.add(this.left);
	  }
	  else if (!(this.down instanceof Side)) {
		  neighbors.add(this.down);
	  }
	  else if (!(this.right instanceof Side)) {
		  neighbors.add(this.right);
	  }
	  return neighbors;
  }
  
  public void traveled(boolean correct) {
	  if (!correct) {
		  this.solveState = 1;
	  } else {
		  this.solveState = 2;
	  }
  }
  
  public ACell moveTo(String dir) {
	  ACell move = this;
	  this.solveState = 1;
	  
	  if (dir.equals("up") && !(this.up instanceof Side)) {
		  move = this.up;
	  } else if (dir.equals("left") && !(this.left instanceof Side)) {
		  move = this.left;
	  } else if (dir.equals("right") && !(this.left instanceof Side)) {
		  move = this.right;
	  }  else if (dir.equals("down") && !(this.left instanceof Side)) {
		  move = this.down;
	  }
	  
	move.solveState = 3;
	return move;
  }
  
  boolean connectCell(ACell other, boolean vertical) {
	  return (vertical && (this.up.equals(other) && other.down.equals(this)) 
			 || (this.down.equals(other) && other.up.equals(this)))
			  || (this.left.equals(other) && other.right.equals(this))
			  || (this.right.equals(other) && other.left.equals(this));
  }
}

class StartCell extends ACell {
	  
	  public StartCell(int cellSize, int X, int Y) {
		  super (cellSize, X, Y);
	  }

	WorldImage drawCell() {
		Color color;
		if (this.solveState == 2) {
			color = Color.red;
		}
		else if (this.solveState == 3) {
			color = Color.pink;
		}
		else {
			color = Color.green;
		}
		
		WorldImage cellDraw = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, color);
		cellDraw = new OverlayImage(this.drawSide(color), cellDraw);
		return cellDraw;
	} 
	
	boolean connected(ACell other, boolean vertical) {
		return (vertical && (this.up.equals(other)) && other.down.equals(this))
				|| (this.down.equals(other) && other.up.equals(this)) 
 				|| ((this.left.equals(other) && other.right.equals(this))
 				|| (this.right.equals(other) && other.left.equals(this)));
	}	  
}


class EndCell extends ACell {
	  public EndCell(int cellSize, int X, int Y) {
		  super(cellSize, X, Y);
	  }

	WorldImage drawCell() {
		Color color;
		if (this.solveState == 2) {
			color = Color.blue;
		}
		else if (this.solveState == 3) {
			color = Color.magenta;
		}
		else {
			color = Color.CYAN;
		}
		
		WorldImage cellDraw = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, color);
		cellDraw = new OverlayImage(this.drawSide(color), cellDraw);
		return cellDraw;
	} 

}

class MazeCell extends ACell {
	
	MazeCell(int cellSize, int X, int Y) {
		super(cellSize, X, Y);
	}
	
	WorldImage drawCell() {
		Color color = Color.black;
		if (this.solveState == 2) {
			color = Color.cyan;
		if (this.solveState == 1) {
			color = Color.BLUE;
		}
		if (this.solveState == 3) {
			color = Color.LIGHT_GRAY;
		  }
		}
		WorldImage cell = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, color);
		cell = new OverlayImage(this.drawSide(color), cell);
		return cell;
	}
}


class Side extends ACell {
	
	public Side(int cellSize) {
		super(null, null, null, null, cellSize);
		
	}
	
	int findLongestPath(String dis) {
		return 0;
	}

	WorldImage drawCell() {
		return null;
	}
 }

class Edge {
	boolean vertical;
	ACell cell1;
	ACell cell2;
	int cost;
	
	Edge(ACell cell1, ACell cell2, boolean vertical, int cost) {
		this.cell1 = cell1;
		this.cell2 = cell2;
		this.vertical = vertical;
		this.cost = cost;
	}
	
	ACell checkCell(boolean isCell1) {
		if (isCell1) {
			return this.cell1;
		} else {
			return this.cell2;
		}
	}
	
	void connect() {
		this.cell1.updateCell(this.cell2, this.vertical);
	}
	
	public int compareCosts(Edge other) {
		return this.cost - other.cost;
	}
	
	boolean hasPath() {
		return this.cell1.hasPath(this.cell2, this.vertical);
	}
}















