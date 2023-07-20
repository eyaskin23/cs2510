import javalib.impworld.*;

class MazeSolver extends World {
  private MazeGame maze;
  private boolean underConstruction;

  private int viewMode;
  private int solveMode;
  private int width;
  private int height;

  private int preference;

  private int cellSize;

  private boolean isSolved;

  private final boolean skipConstruction;

  MazeSolver(int width, int height, int cellSize, int preference, boolean skipConstruction) {
    if (width == 1 && height == 1) {
      throw new IllegalArgumentException("Cannot have a 1x1 maze");
    }
    this.maze = new MazeGame(width, height, cellSize, preference, skipConstruction);
    this.skipConstruction = skipConstruction;
    this.width = width;
    this.height = height;
    this.preference = preference;
    this.underConstruction = !skipConstruction;
    this.viewMode = 0;
    this.cellSize = cellSize;
  }

  public void onTick() {
    if (underConstruction) {
      this.underConstruction = this.maze.constructMaze();
    }
    else if (!this.isSolved && (this.solveMode == 1 || this.solveMode == 2)) {
      this.isSolved = this.maze.findPath();
    }
    else if (!this.isSolved && this.solveMode == 3) {
      this.isSolved = this.maze.solvedManually();
    }
  }

  public WorldScene makeScene() {
    return this.maze.observeMaze(this.viewMode);
  }

  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      this.maze = new MazeGame(this.width, this.height, this.cellSize, this.preference,
          this.skipConstruction);
      this.viewMode = 0;
      this.solveMode = 0;
      this.underConstruction = true;
      this.isSolved = false;
    }
    if (!this.underConstruction) {
      if (key.equals("s")) {
        this.viewMode = 1;
        this.maze.calculateLongestPath(true);
      }
      else if (key.equals("e")) {
        this.viewMode = -1;
        this.maze.calculateLongestPath(false);
      }
      else if (key.equals("n")) {
        this.viewMode = 0;
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
          && this.solveMode == 3 && !this.isSolved) {
        this.maze.moveManually(key);
      }
    }
  }

  public int worldHeight() {
    return this.cellSize * this.maze.height();
  }

  public int worldWidth() {
    return this.cellSize * this.maze.width();
  }

  public int getSolveMode() {
    return this.solveMode;
  }

  public int getViewMode() {
    return this.viewMode;
  }

  public boolean solvedYet() {
    return this.isSolved;
  }

  public boolean stillConstructing() {
    return this.underConstruction;
  }

}