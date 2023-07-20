import javalib.impworld.World;
import javalib.impworld.WorldScene;

class MazeSolver extends World {
	
	Maze maze;
	boolean creating;
	int width;
	int height;
	int prefs;
	int cellSize;
	boolean solved;
	boolean skip;
	int view;
	int solveMode;
	
	MazeSolver(int height, int width, int cellSize, int prefs, boolean skip) {
		if (height < 2 && width < 2) {
			throw new IllegalArgumentException("Can't have a 1 x 1 maze");
		} else {
			this.maze = new Maze(height, width, cellSize, prefs, skip);
			this.skip = skip;
			this.width = width;
			this.height = height;
			this.prefs = prefs;
			this.creating = !skip;
			this.cellSize = cellSize;
		}
	}

	public WorldScene makeScene() {
	    return this.maze.observeMaze(this.view);
	}

	
	 public void onKeyEvent(String key) {
		    if (key.equals("r")) {
		      this.maze = new Maze(this.width, this.height, this.cellSize, this.prefs,
		          this.skip);
		      this.view = 0;
		      this.solveMode = 0;
		      this.creating = true;
		      this.solved = false;
		    }
		    if (!this.creating) {
		      if (key.equals("s")) {
		        this.view = 1;
		        this.maze.calculateLongestPath(true);
		      }
		      else if (key.equals("e")) {
		        this.view = -1;
		        this.maze.calculateLongestPath(false);
		      }
		      else if (key.equals("n")) {
		        this.view = 0;
		      }
		      else if (key.equals("d") && this.solveMode == 0) {
		        this.solveMode = 1;
		        this.maze.initializeSearch(true);
		      }
		      else if (key.equals("b") && this.solveMode == 0) {
		        this.solveMode = 2;
		        this.maze.initializeSearch(false);
		      }
		      else if (key.equals("m") && this.solveMode == 0) {
		        this.solveMode = 3;
		      }
		      else if ((key.equals("up") || key.equals("left") || key.equals("down") || key.equals("right"))
		          && this.solveMode == 3 && !this.solved) {
		        this.maze.moveManually(key);
		      }
		    }
		  }
	
	public void onTick() {
	    if (creating) {
	        creating = maze.constructMaze();
	    } else if (!solved && (solveMode == 1 || solveMode == 2)) {
	        solved = maze.findPath();
	    } else if (!solved && solveMode == 3) {
	        solved = maze.solvedManually();
	    } else if (solved) {
	        return; // exit if solved is already true
	    }
	}

	
	int worldHeight() {
		return this.cellSize * this.maze.mazeHeight();
		
	}
	
	public int viewMode() {
		return this.view;
	}
	
	public int solveMode() {
		return this.solveMode;
	}
	
	boolean stillCreating() {
		return this.creating;
	}
	
	boolean isSolved() {
		return this.solved;
	}
	
	int worldWidth() {
		return this.cellSize * this.maze.mazeWidth();
	}
}



















