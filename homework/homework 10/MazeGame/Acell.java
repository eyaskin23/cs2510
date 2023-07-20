import java.util.*;
import javalib.worldimages.*;
import java.awt.Color;

//represents a maze cell within the game
abstract class ACell {
  ACell up;
  ACell down;
  ACell left;
  ACell right;
  final int cellSize;
  int solveState;
  final int X;
  final int Y;
  private int startHeat;
  private int endHeat;

  // constructor with up, down, left, right cells
  // also has a size, used for constructing walls
  public ACell(ACell up, ACell down, ACell left, ACell right, int cellSize) {
    this.up = up;
    this.down = down;
    this.left = left;
    this.right = right;
    this.cellSize = cellSize;
    this.solveState = 0;
    this.X = 0;
    this.Y = 0;
  }

  // default maze cell with 4 walls as its neighbors
  ACell(int cellSize, int xCoord, int yCoord) {
    this.up = new Wall(cellSize);
    this.down = new Wall(cellSize);
    this.left = new Wall(cellSize);
    this.right = new Wall(cellSize);
    this.cellSize = cellSize;
    this.solveState = 0;
    this.X = xCoord;
    this.Y = yCoord;
  }

  // given a cell and a direction, it sets the neighbors of the cells
  void updateCell(ACell other, boolean isVertical) {
    if (isVertical) {
      this.down = other;
      other.up = this;
    }
    else {
      this.right = other;
      other.left = this;
    }
  }

  // draws the cell with a specific color
  abstract WorldImage drawCell();

  // recurses through the maze and then updates the distance from the start to the
  // end
  public void updateHeats(String prevDir, int change, boolean isFromStart) {
    if (!prevDir.equals("Up") && !prevDir.equals("Down") && !prevDir.equals("Left")
        && !prevDir.equals("Right") && !prevDir.equals("Start")) {
      throw new IllegalArgumentException("A non start or end cell has to come from somewhere");
    }

    if (!prevDir.equalsIgnoreCase("Down")) {
      this.up.updateHeats("Up", change + 1, isFromStart);
    }

    if (!prevDir.equalsIgnoreCase("Right")) {
      this.left.updateHeats("Left", change + 1, isFromStart);
    }

    if (!prevDir.equalsIgnoreCase("Left")) {
      this.right.updateHeats("Right", change + 1, isFromStart);
    }

    if (!prevDir.equalsIgnoreCase("Up")) {
      this.down.updateHeats("Down", change + 1, isFromStart);
    }

    if (isFromStart) {
      this.startHeat = change;
    }
    else {
      this.endHeat = change;
    }
  }

  // finds the longestpath between cells without going backawards
  int longestPath(String dir) {
    if (!dir.equals("Up") && !dir.equals("Down") && !dir.equals("Left") && !dir.equals("Right")
        && !dir.equals("Start")) {
      throw new IllegalArgumentException("A non start or end cell has to come from somewhere");
    }
    int max = 0;
    if (!dir.equalsIgnoreCase("Down")) {
      max = Math.max(max, 1 + this.up.longestPath("Up"));
    }

    if (!dir.equalsIgnoreCase("Right")) {
      max = Math.max(max, 1 + this.left.longestPath("Left"));
    }

    if (!dir.equalsIgnoreCase("Left")) {
      max = Math.max(max, 1 + this.right.longestPath("Right"));
    }

    if (!dir.equalsIgnoreCase("Up")) {
      max = Math.max(max, 1 + this.down.longestPath("Down"));
    }

    return max;
  }

  // draws the walls of a cell based on its neighbors, draws the maze row by row
  protected WorldImage drawWall(Color color) {
    WorldImage walls = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.OUTLINE, color);
    WorldImage newWall;
    if (this.up.up == null) {
      newWall = new LineImage(new Posn(this.cellSize, 0), Color.DARK_GRAY);
      walls = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP, newWall, 0, 0, walls);
    }
    if (this.down.down == null) {
      newWall = new LineImage(new Posn(this.cellSize, 0), Color.DARK_GRAY);
      walls = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.BOTTOM, newWall, 0, 0, walls);
    }
    if (this.left.left == null) {
      newWall = new LineImage(new Posn(0, this.cellSize), Color.DARK_GRAY);
      walls = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, newWall, 0, 0, walls);
    }
    if (this.right.right == null) {
      newWall = new LineImage(new Posn(0, this.cellSize), Color.DARK_GRAY);
      walls = new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.MIDDLE, newWall, 0, 0, walls);
    }

    return walls;
  }

  // draws the maze cell with the correct color
  WorldImage drawHeatCell(boolean fromStart, int longestPath) {
    Color color;
    int delta;
    if (fromStart) {
      delta = (int) (((1.0 * this.startHeat) / longestPath) * 255);
    }
    else {
      delta = (int) (((1.0 * this.endHeat) / longestPath) * 255);
    }
    color = new Color(255 - delta, 0, delta);
    WorldImage cellDrawing = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID,
        color);
    cellDrawing = new OverlayImage(this.drawWall(color), cellDrawing);
    return cellDrawing;
  }

  // counts the numbers a cell has connections to, is used for testing
  public int countNeighbors() {
    int count = 0;
    if (!(this.up instanceof Wall)) {
      count += 1;
    }
    if (!(this.down instanceof Wall)) {
      count += 1;
    }
    if (!(this.left instanceof Wall)) {
      count += 1;
    }
    if (!(this.right instanceof Wall)) {
      count += 1;
    }
    return count;
  }

  // returns the neighbors in a list, used for finding a path
  public ArrayList<ACell> getNeighbors() {
    ArrayList<ACell> neighbors = new ArrayList<ACell>();
    if (!(this.right instanceof Wall)) {
      neighbors.add(right);
    }
    if (!(this.down instanceof Wall)) {
      neighbors.add(this.down);
    }
    if (!(this.left instanceof Wall)) {
      neighbors.add(this.left);
    }
    if (!(this.up instanceof Wall)) {
      neighbors.add(this.up);
    }
    return neighbors;
  }

  // checks if the cell's neighbors was checked, based on whether or not
  // the cell is part of the correct path
  public void wasChecked(boolean isCorrect) {
    if (!isCorrect) {
      this.solveState = 1;
    }
    else {
      this.solveState = 2;
    }
  }

  // returns the neighbor in a given direction
  // returns the correct cell, if it is a wall

  public ACell move(String dir) {
    ACell moveTo = this;
    this.solveState = 1;

    if (dir.equals("up") && !(this.up instanceof Wall)) {
      moveTo = this.up;
    }
    else if (dir.equals("left") && !(this.left instanceof Wall)) {
      moveTo = this.left;
    }
    else if (dir.equals("down") && !(this.down instanceof Wall)) {
      moveTo = this.down;
    }
    else if (dir.equals("right") && !(this.right instanceof Wall)) {
      moveTo = this.right;
    }

    moveTo.solveState = 3;
    return moveTo;
  }

  // methods for testing :

  // checks if these cells are connected
  boolean connectCell(ACell other, boolean isVertical) {
    return (isVertical && (this.up.equals(other) && other.down.equals(this))
        || (this.down.equals(other) && other.up.equals(this)))
        || ((this.left.equals(other) && other.right.equals(this))
            || (this.right.equals(other) && other.left.equals(this)));

  }
}

// represents the start cell of a maze

class StartCell extends ACell {

  // construct a starting cell with a given size and four wall neighbors
  public StartCell(int cellSize, int xCoord, int yCoord) {
    super(cellSize, xCoord, yCoord);
  }

  WorldImage drawCell() {
    Color color;
    if (this.solveState == 2) {
      color = Color.GREEN;
    }
    else if (this.solveState == 3) {
      color = Color.RED;
    }
    else {
      color = Color.YELLOW;
    }
    WorldImage cellDrawing = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID,
        color);
    cellDrawing = new OverlayImage(this.drawWall(color), cellDrawing);
    return cellDrawing;
  }
}

class EndCell extends ACell {

  public EndCell(int cellSize, int xCoord, int yCoord) {
    super(cellSize, xCoord, yCoord);
  }

  WorldImage drawCell() {
    Color color;
    if (this.solveState == 2) {
      color = Color.YELLOW;
    }
    else if (this.solveState == 3) {
      color = Color.RED;
    }
    else {
      color = Color.MAGENTA;
    }
    WorldImage cellDrawing = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID,
        color);
    cellDrawing = new OverlayImage(this.drawWall(color), cellDrawing);
    return cellDrawing;
  }
}

class MazeCell extends ACell {

  public MazeCell(int cellSize, int xCoord, int yCoord) {
    super(cellSize, xCoord, yCoord);
  }

  WorldImage drawCell() {
    Color color;
    if (this.solveState == 2) {
      color = Color.YELLOW;
    }
    else if (this.solveState == 1) {
      color = Color.CYAN;
    }
    else if (this.solveState == 3) {
      color = Color.RED;
    }
    else {
      color = Color.GRAY;
    }
    WorldImage drawing = new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, color);
    drawing = new OverlayImage(this.drawWall(color), drawing);
    return drawing;
  }
}

class Wall extends ACell {

  public Wall(int cellSize) {
    super(null, null, null, null, cellSize);
  }

  WorldImage drawCell() {
    throw new IllegalStateException("Should not call to draw a wall cell");
  }

  WorldImage drawWalls() {
    throw new IllegalStateException("Should not call to draw a wall cell's walls");
  }

  int longestPath(String dir) {
    return 0;
  }

  public void updateHeats(String prevDir, int change, boolean isFromStart) {
    // does nothing is used for testing
  }
}