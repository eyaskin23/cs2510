import java.util.*;
import javalib.impworld.WorldScene;

class Maze {

  private StartCell start;

  private EndCell end;

  private ArrayList<ArrayList<ACell>> maze;

  private HashMap<ACell, ACell> references;

  private final ArrayList<Edge> edges;

  private Iterator<Edge> edgeIterator;

  private final int cellSize;

  private ICollection<ACell> workList;

  private ArrayDeque<ACell> alreadySeen;

  private ACell currentManual;

  private WorldScene currentMazeScene;

  private WorldScene heatMapFromStart;

  private WorldScene heatMapFromEnd;

  private boolean heatMapsConstructed;

  public Maze(int width, int height, int cellSize, int preference, boolean skipConstruction) {
    if (width <= 1 && height <= 1) {
      throw new IllegalArgumentException("Cannot have a 1x1 maze");
    }
    int randWeightMax = (width + 1) / 2;
    this.maze = new ArrayList<ArrayList<ACell>>();
    this.cellSize = cellSize;
    this.edges = new ArrayList<Edge>();
    this.references = new HashMap<ACell, ACell>();
    this.alreadySeen = new ArrayDeque<ACell>();
    this.generateClosedMaze(width, height, cellSize);

    this.currentManual = this.start;
    ArrayList<Edge> allEdges = new ArrayList<Edge>();
    this.generateEdges(width, height, randWeightMax, new Random(), preference, allEdges);

    this.generateRandomConnections(new Random(), allEdges);

    this.edgeIterator = this.edges.iterator();

    this.currentMazeScene = this.drawMaze(width * cellSize, height * cellSize);

    boolean underConstruction = true;
    if (skipConstruction) {
      while (underConstruction) {
        underConstruction = this.constructMaze();
      }
    }
  }


  private void generateClosedMaze(int width, int height, int size) {
    ArrayList<ACell> rowList = new ArrayList<ACell>();

    for (int row = 0; row < height; row += 1) {
      for (int col = 0; col < width; col += 1) {
        if (row == 0 && col == 0) {
          StartCell start = new StartCell(size, col, row);
          this.start = start;
          rowList.add(start);
          references.put(start, start);

        }
        else if (row == height - 1 && col == width - 1) {
          EndCell end = new EndCell(size, col, row);
          this.end = end;
          rowList.add(end);
          references.put(end, end);

        }
        else {
          ACell cell = new MazeCell(size, col, row);
          rowList.add(cell);
          references.put(cell, cell);
        }
      }
      this.maze.add(rowList);
      rowList = new ArrayList<ACell>();
    }
  }

  private void generateEdges(int width, int height, int randWeightMax, Random rand, int preference,
      ArrayList<Edge> allEdges) {
    EdgeUtil forWeight = new EdgeUtil();
    for (int row = 0; row < height; row += 1) {

      for (int col = 0; col < width; col += 1) {
        if (row != height - 1 && col != width - 1) {
          Edge downEdge = new Edge(this.maze.get(row).get(col), this.maze.get(row + 1).get(col),
              true, forWeight.generateCost(rand, randWeightMax, preference, true));
          Edge rightEdge = new Edge(this.maze.get(row).get(col), this.maze.get(row).get(col + 1),
              false, forWeight.generateCost(rand, randWeightMax, preference, false));

          forWeight.insertEdges(allEdges, downEdge);
          forWeight.insertEdges(allEdges, rightEdge);
        }
        else if (row == height - 1 && col != width - 1) {
          Edge rightEdge = new Edge(this.maze.get(row).get(col), this.maze.get(row).get(col + 1),
              false, forWeight.generateCost(rand, randWeightMax, preference, false));

          forWeight.insertEdges(allEdges, rightEdge);
        }
        else if (row != height - 1 && col == width - 1) {
          Edge downEdge = new Edge(this.maze.get(row).get(col), this.maze.get(row + 1).get(col),
              true, forWeight.generateCost(rand, randWeightMax, preference, false));

          forWeight.insertEdges(allEdges, downEdge);
        }
      }
    }
  }


  private void generateRandomConnections(Random rand, ArrayList<Edge> allEdges) {
     Collections.shuffle(this.edges, rand);

    UnionUtil ufUtil = new UnionUtil(this.references);

     ArrayList<Edge> workList = new ArrayList<Edge>();
    while (this.edges.size() < this.references.size() - 1) {
      Edge edge = allEdges.get(0);
      if (ufUtil.findCell(edge.observeCell(true)).equals(ufUtil.findCell(edge.observeCell(false)))) {
        allEdges.remove(0);
      }
      else {
        ufUtil.union(edge.observeCell(true), edge.observeCell(false));
         edge.makeConnection();
        this.edges.add(allEdges.remove(0));
      }
    }
  }

  public boolean constructMaze() {
    if (this.edgeIterator.hasNext()) {
      Edge next = this.edgeIterator.next();
      next.makeConnection();
      this.updateMazeScene(next.observeCell(true));
      this.updateMazeScene(next.observeCell(false));
      return true;
    }
    else {

      return false;
    }
  }

  WorldScene drawMaze(int mazeWidth, int mazeHeight) {
    WorldScene maze = new WorldScene(mazeWidth, mazeHeight);
    for (int i = 0; i < this.maze.size(); i += 1) {

      for (int j = 0; j < this.maze.get(0).size(); j += 1) {
        int xPlace = (j * this.cellSize) + this.cellSize / 2;
        int yPlace = (i * this.cellSize) + this.cellSize / 2;
        maze.placeImageXY(this.maze.get(i).get(j).drawCell(), xPlace, yPlace);
      }
    }
    return maze;
  }


  int mazeHeight() {
    return this.maze.size();
  }


  int mazeWidth() {
    return this.maze.get(0).size();
  }


  boolean checkValidMinSpanningTree() {
    return this.mazeHeight() * this.mazeWidth() - 1 == this.edges.size()
        && this.edges.size() == this.checkNumConnectedEdges();
  }

  void initializeSearch(boolean isDepthFirst) {
    this.references = new HashMap<ACell, ACell>();
    this.references.put(start, start);
    if (isDepthFirst) {
      this.workList = new Stack<ACell>();
      this.workList.add(this.start);
    }
    else {
      this.workList = new Queue<ACell>();
      this.workList.add(this.start);
    }
  }

  boolean findPath() {
    if (this.workList.isEmpty()) {
      throw new IllegalStateException("Maze should be solvable, if this is thrown, it's not");
    }
    else {
      ACell next = this.workList.remove();
      next.traveled(false);
      this.updateMazeScene(next);
      if (next.equals(this.end)) {
        this.showCorrectPath();
        return true;  
      }
      else if (!this.alreadySeen.contains(next)) {
        for (ACell cell : next.getNeighbors()) {
          if (!this.alreadySeen.contains(cell)) {
            this.workList.add(cell);
            this.references.put(cell, next);
          }
        }
        this.alreadySeen.add(next);
      }
    }
    return false;
  }


  private void showCorrectPath() {
    this.start.traveled(true);
    this.updateMazeScene(this.start);
    ACell nextCell = this.end;
    while (!nextCell.equals(this.start)) {
      nextCell.traveled(true);
      this.updateMazeScene(nextCell);
      nextCell = this.references.get(nextCell);
    }
  }

  public int calculateLongestPath(boolean fromStart) {
    if (fromStart) {
      return this.start.findLongestPath("Start");
    }
    else {
      return this.end.findLongestPath("Start");
    }
  }

  public boolean solvedManually() {
    if (this.end.equals(this.currentManual)) {
      this.showCorrectPath();
      return true;
    }
    else {
      return false;
    }
  }

  public void moveManually(String dir) {
    ACell nextCell;
    if (this.currentManual.equals(this.start) && !this.alreadySeen.contains(this.start)) {
      this.alreadySeen.add(this.start);
    }
    nextCell = this.currentManual.moveTo(dir);
    this.updateMazeScene(this.currentManual);
    if (!nextCell.equals(this.currentManual) && !this.currentManual.equals(nextCell)) {
      if (!this.alreadySeen.contains(nextCell)) {
        this.references.put(nextCell, this.currentManual);
        this.alreadySeen.add(nextCell);
      }
      this.currentManual = nextCell;
    }
    this.updateMazeScene(nextCell);
  }
  
  private void updateMazeScene(ACell cell) {
    this.currentMazeScene.placeImageXY(cell.drawCell(),
        (cell.X * this.cellSize) + this.cellSize / 2,
        (cell.Y * this.cellSize) + this.cellSize / 2);
  }

  private void constructHeatMaps() {
    int startLen = this.calculateLongestPath(true);
    int endLen = this.calculateLongestPath(false);

    this.start.updateSolve("Start", 0, true);
    this.end.updateSolve("Start", 0, false);

    this.drawHeatMap(true, startLen);
    this.drawHeatMap(false, endLen);
  }


  private void drawHeatMap(Boolean fromStart, int longestPath) {
    WorldScene maze = new WorldScene(this.mazeWidth() * this.cellSize,
        this.mazeHeight() * this.cellSize);
    for (int i = 0; i < this.maze.size(); i += 1) {
      for (int j = 0; j < this.maze.get(0).size(); j += 1) {
        int xPlace = (j * this.cellSize) + this.cellSize / 2;
        int yPlace = (i * this.cellSize) + this.cellSize / 2;
        maze.placeImageXY(this.maze.get(i).get(j).drawSolveCell(fromStart, longestPath), xPlace,
            yPlace);
      }
    }

    if (fromStart) {
      this.heatMapFromStart = maze;
    }
    else {
      this.heatMapFromEnd = maze;
    }
  }

  public WorldScene observeMaze(int mode) {
    if (!heatMapsConstructed) {
      this.constructHeatMaps();
    }
    if (mode == 0) {
      return this.currentMazeScene;
    }
    else if (mode == 1) {
      return this.heatMapFromStart;
    }
    else if (mode == -1) {
      return this.heatMapFromEnd;
    }
    else {
      throw new IllegalArgumentException("Invalid Viewing Mode");
    }
  }


  int checkNumConnectedEdges() {
    int i = 0;
    for (Edge edge : this.edges) {
      if (edge.isConnected()) {
        i += 1;
      }
    }
    return i;
  }


  boolean searchInitilized(int solveMode) {
    return (solveMode == 3 && this.workList == null)
        || (solveMode == 1 && this.workList instanceof Stack<?>)
        || (solveMode == 2 && this.workList instanceof Queue<?>);
  }


  int checkNumSeen() {
    return this.alreadySeen.size();
  }

  public int numOfEdges() {
    return this.edges.size();
  }
}

class Edge {

  private final boolean isVertical;


  private final ACell cell1;

  private final ACell cell2;
  private final int weight;

  Edge(ACell cell1, ACell cell2, boolean isVertical, int weight) {
    this.cell1 = cell1;
    this.cell2 = cell2;
    this.isVertical = isVertical;
    this.weight = weight;
  }

  ACell observeCell(boolean isCell1) {
    if (isCell1) {
      return this.cell1;
    }
    else {
      return this.cell2;
    }
  }

  void makeConnection() {
    this.cell1.updateCell(this.cell2, this.isVertical);
  }


  int compareWeights(Edge other) {
    return this.weight - other.weight;
  }

  boolean isConnected() {
    return this.cell1.connectCell(this.cell2, this.isVertical);
  }
}