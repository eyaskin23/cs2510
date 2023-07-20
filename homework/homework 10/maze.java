import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.*;
import java.awt.*;
import java.util.*;
import tester.Tester;

class Side {
	
	int x;
	int y;
	Side side;
	Side left;
	Side right;
	Side bottom;
	Side top;
	boolean hasRight;
	boolean hasBottom;
	Side previous;
	Side next;
	ArrayList<Wall> walls = new ArrayList<Wall>();
	
	boolean traveled;

	Side(int x, int y) {
		this.x = x;
		this.y = y;
		this.hasRight = true;
		this.left = null;
		this.right = null;
		this.top = null;
		this.bottom = null;
		this.hasBottom = true;
		this.traveled = false;
		this.previous = null;
		this.next = null;
	}

	
	void findPrevious() {
		if (this.top != null && !this.top.hasBottom && this.top.previous == null) {
			this.previous = this.top;
		} 
		 else if (this.left != null && !this.left.hasRight && this.left.previous == null) {
			this.previous = this.left;
		} 
		 else if (this.right != null && !this.hasRight && this.right.previous == null) {
		    this.previous = this.right;
		}
		 else if (this.bottom != null && !this.hasBottom && this.bottom.previous == null) {
		  this.previous = this.bottom;
		}
	}

	  WorldImage drawRight() {
	    return new LineImage(new Posn(0, IMaze.cellSize), Color.red)
	            .movePinhole(-1 * IMaze.cellSize, IMaze.cellSize / -2);
	  }

	  WorldImage drawBottom() {
	    return new LineImage(new Posn(IMaze.cellSize, 0), Color.red)
	            .movePinhole(IMaze.cellSize / -2, -1 * IMaze.cellSize);
	  }

	  WorldImage draw(int x, int y, Color color) {
	    return new RectangleImage(IMaze.cellSize - 2, IMaze.cellSize - 2,
	            OutlineMode.SOLID, color).movePinhole(-x * IMaze.cellSize / x / 2,
	            -x * IMaze.cellSize / x / 2);
	  }
}
class Wall {
	
	Side previous;
	Side next;
	int cost;
	
	Wall(Side previous, Side next, int cost) {
		this.previous = previous;
		this.next = next;
		this.cost = cost;
  }
}

class Player {
Side thisSide;

Player(Side thisSide) {
 this.thisSide = thisSide;
}

boolean validMove(String move) {
 if (move.equals("up") && this.thisSide.top != null) {
   return !this.thisSide.top.hasBottom;
 }
 else if (move.equals("down") && this.thisSide.bottom != null) {
   return !this.thisSide.hasBottom;
 }
 else if (move.equals("left") && this.thisSide.left != null) {
   return !this.thisSide.left.hasRight;
 }
 else if (move.equals("right") && this.thisSide.right != null) {
   return !this.thisSide.hasRight;
 }
 else {
   return false;
 }
}

WorldImage drawPlayer() {
 return new RectangleImage(IMaze.cellSize - 3, IMaze.cellSize - 3,
         OutlineMode.SOLID, Color.blue).movePinhole(-IMaze.cellSize / 2,
         -IMaze.cellSize / 2);
  }
}

 
class CostCompare implements Comparator<Wall> {

public int compare(Wall item1, Wall item2) {
 return item1.cost - item2.cost;
  }
}


class IMaze extends World {
static final int cellSize = 20;
int boardX;
int boardY;
HashMap<Side, Side> hash = new HashMap<Side, Side>();
ArrayList<Wall> los = new ArrayList<Wall>();
ArrayList<Wall> mst = new ArrayList<Wall>();
ArrayList<Side> path = new ArrayList<Side>();
Side endCell;

WorldScene scene = new WorldScene(0, 0);
ArrayList<ArrayList<Side>> board;

boolean done;

Player p;

TextImage won = new TextImage("You Won!", 30, Color.BLACK);
TextImage lost = new TextImage("You lost!", 30, Color.BLACK);
double tickRate = 0.01;
double time;
TextImage timeLeft;

IMaze(int boardSizeX, int boardSizeY) {
  this.boardX = boardSizeX;
  this.boardY = boardSizeY;
  this.board = this.createGrid(boardSizeX, boardSizeY);
  this.createEdges(this.board);
  this.createMap(board);
  this.kruskals();
  this.p = new Player(board.get(0).get(0));
  this.endCell = this.board.get(boardSizeY - 1).get(boardSizeX - 1);
  this.time = 100;
  this.timeLeft = new TextImage("Time left " + (int) this.time, 14, Color.black);
  this.renderWorld();
  this.done = false;
}

IMaze() {
  this.boardX = 5;
  this.boardY = 5;
  this.board = this.createGrid(5, 5);
  this.board.get(0).get(0).hasRight = false;
  this.board.get(0).get(1).hasRight = true;
  this.board.get(1).get(0).hasRight = true;
  this.board.get(1).get(1).hasRight = true;
  this.board.get(2).get(0).hasRight = true;
  this.board.get(2).get(1).hasRight = true;
  this.hash.put(this.board.get(0).get(0), this.board.get(0).get(0));
  this.hash.put(this.board.get(0).get(1), this.board.get(0).get(1));
  this.hash.put(this.board.get(1).get(0), this.board.get(1).get(0));
  this.hash.put(this.board.get(1).get(1), this.board.get(1).get(1));
  this.hash.put(this.board.get(2).get(0), this.board.get(2).get(0));
  this.hash.put(this.board.get(2).get(1), this.board.get(2).get(1));

  this.board.get(0).get(0).hasBottom = false;
  this.board.get(0).get(1).hasBottom = false;
  this.board.get(1).get(0).hasBottom = false;
  this.board.get(1).get(1).hasBottom = false;
  this.board.get(2).get(0).hasBottom = true;
  this.board.get(2).get(1).hasBottom = true;

  this.los = new ArrayList<Wall>(Arrays.asList(
          new Wall(new Side(0, 0), new Side(1, 0), 1),
          new Wall(new Side(0, 0), new Side(0, 1), 2),
          new Wall(new Side(1, 0), new Side(1, 1), 3),
          new Wall(new Side(0, 1), new Side(1, 1), 4),
          new Wall(new Side(0, 1), new Side(0, 2), 5),
          new Wall(new Side(1, 1), new Side(1, 2), 6),
          new Wall(new Side(0, 2), new Side(1, 2), 7)));
  
  this.mst = new ArrayList<Wall>(Arrays.asList(
          new Wall(new Side(0, 0), new Side(1, 0), 1),
          new Wall(new Side(0, 0), new Side(0, 1), 2),
          new Wall(new Side(1, 0), new Side(1, 1), 3),
          new Wall(new Side(0, 1), new Side(0, 2), 5),
          new Wall(new Side(1, 1), new Side(1, 2), 6)));

  this.p = new Player(this.board.get(0).get(0));
  this.done = false;
  this.path = new ArrayList<Side>();
  this.endCell = this.board.get(2).get(1);
  if (boardX < 10) {
    time = 100;
  }
  else {
    time = 100;
  }
  this.timeLeft = new TextImage("Time left " + (int) this.time, 14, Color.black);
  this.renderWorld();
}

WorldScene renderWorld() {
  this.scene.placeImageXY(board.get(0).get(0).draw(this.boardX, this.boardY, Color.GREEN),
          0, 0);
  this.scene.placeImageXY(board.get(this.boardY - 1).get(this.boardX - 1)
                  .draw(this.boardX, this.boardY, Color.RED),
          (boardX - 1) * cellSize, (boardY - 1) * cellSize);
  for (int i = 0; i < boardY; i++) {
    for (int j = 0; j < boardX; j++) {
      this.changeRenderBottom(this.board.get(i).get(j));
      this.changeRenderRight(this.board.get(i).get(j));
      if (this.board.get(i).get(j).traveled) {
        this.scene.placeImageXY(board.get(i).get(j).draw(this.boardX,
                this.boardY, Color.YELLOW), j * cellSize, i * cellSize);
      }
      if (board.get(i).get(j).hasRight) {
        this.scene.placeImageXY(board.get(i).get(j).drawRight(),
                (IMaze.cellSize * j),
                (IMaze.cellSize * i));
      }
      if (board.get(i).get(j).hasBottom) {
        this.scene.placeImageXY(board.get(i).get(j).drawBottom(),
                (IMaze.cellSize * j),
                (IMaze.cellSize * i));
      }
    }
  }
  this.scene.placeImageXY(p.drawPlayer(), this.p.thisSide.x * cellSize, this.p.thisSide.y * cellSize);
  this.scene.placeImageXY(this.timeLeft, cellSize + 20,
          boardY * cellSize + cellSize / 2);
  return scene;
}


public WorldScene makeScene() {
  if (path.size() > 1) {
    this.findEnd();
  }
  else if (path.size() > 0) {
    this.drawEnd();
  }
  else if (this.done && this.endCell.previous != null) {
    this.traceback();
  }
  if (this.p.thisSide != this.board.get(this.boardY - 1).get(this.boardX - 1)
          && this.p.thisSide != this.endCell) {
    this.time = this.time - this.tickRate;
    this.timeLeft.text = "Time left " + (int) this.time;
  }
  if (p.thisSide == this.board.get(boardY - 1).get(boardX - 1)) {
    this.scene.placeImageXY(won, boardX * cellSize / 2, boardY * cellSize / 2);
    time = 0;
  }
  if (this.time <= 0.0) {
    this.scene.placeImageXY(lost, boardX * cellSize / 2, boardY * cellSize / 2);
    this.time = 0.0;
  }
  return scene;
}


void changeRenderRight(Side v) {
  for (Wall w : this.mst) {
    if (w.next.y == w.previous.y) {
      w.next.hasRight = false;
    }
  }
}


void changeRenderBottom(Side v) {
  for (Wall wall : this.mst) {
    if (wall.next.x == wall.previous.x) {
      wall.next.hasBottom = false;
    }
  }
}


ArrayList<ArrayList<Side>> createGrid(int bWidth, int bHeight) {
  ArrayList<ArrayList<Side>> board = new ArrayList<ArrayList<Side>>();
  for (int i = 0; i < bHeight; i++) {
    board.add(new ArrayList<Side>());
    ArrayList<Side> r = board.get(i);
    for (int j = 0; j < bWidth; j++) {
      r.add(new Side(j, i));
    }
  }
  this.linkVertexs(board);
  this.createEdges(board);
  this.createMap(board);
  return board;
}


void linkVertexs(ArrayList<ArrayList<Side>> b) {
  for (int i = 0; i < this.boardY; i++) {
    for (int j = 0; j < this.boardX; j++) {
      if (j + 1 < this.boardX) {
        b.get(i).get(j).right = b.get(i).get(j + 1);
      }
      if (j - 1 >= 0) {
        b.get(i).get(j).left = b.get(i).get(j - 1);
      }
      if (i + 1 < this.boardY) {
        b.get(i).get(j).bottom = b.get(i + 1).get(j);
      }
      if (i - 1 >= 0) {
        b.get(i).get(j).top = b.get(i - 1).get(j);
      }
    }
  }
}


ArrayList<Wall> createEdges(ArrayList<ArrayList<Side>> n) {
  Random randomWeight = new Random();
  for (int i = 0; i < n.size(); i++) {
    for (int j = 0; j < n.get(i).size(); j++) {
      if (j < n.get(i).size() - 1) {
        los.add(new Wall(n.get(i).get(j), n.get(i).get(j).right, randomWeight.nextInt(50)));
      }
      if (i < n.size() - 1) {
        los.add(new Wall(n.get(i).get(j), n.get(i).get(j).bottom,
                (int) randomWeight.nextInt(50)));
      }
    }
  }
  Collections.sort(los, new CostCompare());
  return los;
}


HashMap<Side, Side> createMap(ArrayList<ArrayList<Side>> vertex) {
  for (int i = 0; i < vertex.size(); i++) {
    for (int j = 0; j < vertex.get(i).size(); j++) {
      this.hash.put(vertex.get(i).get(j), vertex.get(i).get(j));
    }
  }
  return hash;
}


ArrayList<Wall> kruskals() {
  int i = 0;
  while (this.mst.size() < this.los.size() && i < this.los.size()) {
    Wall s = los.get(i);
    if (this.find(this.find(s.previous)).equals(this.find(this.find(s.next)))) {
    }
    else {
      mst.add(s);
      union(this.find(s.previous), this.find(s.next));
    }
    i ++;
  }
  for (int y = 0; y < this.boardY; y ++) {
    for (int x = 0; x < this.boardX; x ++) {
      for (Wall e : this.mst) {
        if (this.board.get(y).get(x).equals(e.previous) || this.board.get(y).get(x).equals(e.next)) {
          this.board.get(y).get(x).walls.add(e);
        }
      }
    }
  }
  return this.mst; 
}

 
void union(Side item, Side newRep) {
  this.hash.put(this.find(item), this.find(newRep));
}

Side find(Side item) {
  if (item.equals(this.hash.get(item))) {
    return item;
  }
  else {
    return this.find(this.hash.get(item));
  }
}

 
public void onKeyEvent(String key) {
  if (key.equals("n")) {
    this.scene = this.getEmptyScene();
    this.board = this.createGrid(boardX, boardY);
    this.createEdges(this.board);
    this.createMap(board);
    this.kruskals();
    this.time = 100;
    this.p = new Player(board.get(0).get(0));
    this.endCell = this.board.get(this.boardY - 1).get(this.boardX - 1);
    this.renderWorld();
  }
  else if (key.equals("up") && p.validMove("up")) {
    p.thisSide.traveled = true;
    p.thisSide = p.thisSide.top;
  }
  else if (key.equals("down") && p.validMove("down")) {
    p.thisSide.traveled = true;
    p.thisSide = p.thisSide.bottom;
  }
  else if (key.equals("left") && p.validMove("left")) {
    p.thisSide.traveled = true;
    p.thisSide = p.thisSide.left;
  }
  else if (key.equals("right") && p.validMove("right")) {
    p.thisSide.traveled = true;
    p.thisSide = p.thisSide.right;
  }
  else if (key.equals("d")) {
    this.endCell = this.board.get(this.boardY - 1).get(this.boardX - 1);
    this.path = new Graph().pathDFS(this.board.get(0).get(0), this.board.get(this.boardY - 1)
            .get(this.boardX - 1));
  }
  else if (key.equals("b")) {
    this.endCell = this.board.get(this.boardY - 1).get(this.boardX - 1);
    this.path = new Graph().pathBFS(this.board.get(0).get(0), this.board.get(this.boardY - 1)
            .get(this.boardX - 1));
  }
  this.scene.placeImageXY(p.drawPlayer(), p.thisSide.x * cellSize, p.thisSide.y * cellSize);
  this.renderWorld();
}

public void onTick() {
}


void findEnd() {
  Side next = path.remove(0);
  this.scene.placeImageXY(next.draw(this.boardX, this.boardY, Color.CYAN),
          next.x * cellSize, next.y * cellSize);
}


void drawEnd() {
  Side next = path.remove(0);
  this.scene.placeImageXY(next.draw(this.boardX, this.boardY, Color.CYAN),
          next.x * cellSize, next.y * cellSize);
  if (!this.endCell.left.hasRight && this.endCell.left.previous != null) {
    this.endCell.previous = this.endCell.left;
  }
  else if (!this.endCell.top.hasBottom && this.endCell.top.previous != null) {
    this.endCell.previous = this.endCell.top;
  }
  else {
    this.endCell.previous = next;
  }
  this.done = true;
}

 
void traceback() {
  if (this.endCell.x == this.boardX - 1 && this.endCell.y == this.boardY - 1) {
    this.scene.placeImageXY(this.endCell.draw(this.boardX, this.boardY,
            Color.magenta), this.endCell.x * cellSize,
            this.endCell.y * cellSize);
  }
  this.scene.placeImageXY(this.endCell.previous.draw(this.boardX, this.boardY,
          Color.magenta), this.endCell.previous.x * cellSize,
          this.endCell.previous.y * cellSize);
  this.endCell = this.endCell.previous;
}
  }

//An ICollection is one of
//- A Queue
//- A Stack
interface ICollection<T> {
// Adds an item to this ICollection
void add(T item);

// Removes an item from this ICollection
T remove();

// Returns the size of this ICollection
int size();
}

//Describes a Queue
//Used in Breadth-first Search
class Queue<T> implements ICollection<T> {
Deque<T> items;

Queue() {
 this.items = new Deque<T>();
}

// Adds an item to this Queue
public void add(T item) {
 this.items.addAtTail(item);
}

// Removes an item from this Queue
public T remove() {
 return this.items.removeFromHead();
}

// Returns the size of this Queue
public int size() {
 return this.items.size();
}
}

//Describes a Stack
//Used in Depth-first Search
class Stack<T> implements ICollection<T> {
Deque<T> items;

Stack() {
 this.items = new Deque<T>();
}

// Adds an item to a Stack
public void add(T item) {
 this.items.addAtHead(item);
}

// Removes and item to a Stack
public T remove() {
 return this.items.removeFromHead();
}

// Returns the size of this Stack
public int size() {
 return this.items.size();
  }
}

class Graph {

ArrayList<Side> allVertices;

Graph() {
}

ArrayList<Side> pathDFS(Side previous, Side next) {
  return this.createPath(previous, next, new Deque<Side>());
}

ArrayList<Side> pathBFS(Side previous, Side next) {
  return this.createPath(previous, next, new Deque<Side>());
}

ArrayList<Side> createPath(Side from, Side to, ICollection<Side> worklist) {
  ArrayList<Side> path = new ArrayList<Side>();

  worklist.add(from);
  while (worklist.size() > 0) {
	  Side next = worklist.remove();
    if (next == to) {
      return path;
    }
    else if (path.contains(next)) {
    }
    else {
      for (Wall w : next.walls) {
        worklist.add(w.previous);
        worklist.add(w.next);
        if (path.contains(w.previous)) {
          next.previous = w.previous;
        }
        else if (path.contains(w.next)) {
          next.previous = w.next;
        }
      }
      path.add(next);
    }
  }
  return path;
  }
}



class ExamplesMazeGame {
	IMaze RunGame = new IMaze(20, 20);

Graph g = new Graph();

void testMakeGrid(Tester t) {
	IMaze world1 = new IMaze();
    t.checkExpect(world1.board, new ArrayList<ArrayList<Side>>(Arrays.asList(
          new ArrayList<Side>(Arrays.asList(world1.board.get(0).get(0), world1.board.get(0).get(1))),
          new ArrayList<Side>(Arrays.asList(world1.board.get(1).get(0), world1.board.get(1).get(1))),
          new ArrayList<Side>(Arrays.asList(world1.board.get(2).get(0), world1.board.get(2).get(1))))));
}

void testLinkVertexs(Tester t) {
	IMaze world1 = new IMaze();
  t.checkExpect(world1.board.get(0).get(0).right, world1.board.get(0).get(1));
  t.checkExpect(world1.board.get(0).get(0).bottom, world1.board.get(1).get(0));
  t.checkExpect(world1.board.get(0).get(0).top, null);
  t.checkExpect(world1.board.get(0).get(0).left, null);
}

void testCreateEdges(Tester t) {

	IMaze world1 = new IMaze();
  t.checkExpect(world1.los.get(0),
          new Wall(new Side(world1.board.get(0).get(0).x, world1.board.get(0).get(0).y),
                  new Side(world1.board.get(0).get(1).x, world1.board.get(0).get(1).y), 1));
  t.checkExpect(world1.los.get(1),
          new Wall(new Side(world1.board.get(0).get(0).x, world1.board.get(0).get(0).y),
                  new Side(world1.board.get(1).get(0).x, world1.board.get(1).get(0).y), 2));
  t.checkExpect(world1.los.get(2),
          new Wall(new Side(world1.board.get(0).get(1).x, world1.board.get(0).get(1).y),
                  new Side(world1.board.get(1).get(1).x, world1.board.get(1).get(1).y), 3));
  t.checkExpect(world1.los.get(3),
          new Wall(new Side(world1.board.get(1).get(0).x, world1.board.get(1).get(0).y),
                  new Side(world1.board.get(1).get(1).x, world1.board.get(1).get(1).y), 4));
  t.checkExpect(world1.los.get(4),
          new Wall(new Side(world1.board.get(1).get(0).x, world1.board.get(1).get(0).y),
                  new Side(world1.board.get(2).get(0).x, world1.board.get(2).get(0).y), 5));
  t.checkExpect(world1.los.get(5),
          new Wall(new Side(world1.board.get(1).get(1).x, world1.board.get(1).get(1).y),
                  new Side(world1.board.get(2).get(1).x, world1.board.get(2).get(1).y), 6));
  t.checkExpect(world1.los.get(6),
          new Wall(new Side(world1.board.get(2).get(0).x, world1.board.get(2).get(0).y),
                  new Side(world1.board.get(2).get(1).x, world1.board.get(2).get(1).y), 7));
}

void testCreateMap(Tester t) {
	IMaze world1 = new IMaze();
  t.checkExpect(world1.hash.get(world1.board.get(0).get(0)), world1.board.get(0).get(0));
  t.checkExpect(world1.hash.get(world1.board.get(0).get(1)), world1.board.get(0).get(1));
  t.checkExpect(world1.hash.get(world1.board.get(1).get(0)), world1.board.get(1).get(0));
  t.checkExpect(world1.hash.get(world1.board.get(1).get(1)), world1.board.get(1).get(1));
  t.checkExpect(world1.hash.get(world1.board.get(2).get(0)), world1.board.get(2).get(0));
  t.checkExpect(world1.hash.get(world1.board.get(2).get(1)), world1.board.get(2).get(1));
}

void testKruskals(Tester t) {
	IMaze world1 = new IMaze();

	world1.createGrid(world1.boardX, world1. boardY);
  t.checkExpect(world1.mst.get(0), new Wall(world1.mst.get(0).previous, world1.mst.get(0).next, 1));
  t.checkExpect(world1.mst.get(1), new Wall(world1.mst.get(1).previous, world1.mst.get(1).next, 2));
  t.checkExpect(world1.mst.get(2), new Wall(world1.mst.get(2).previous, world1.mst.get(2).next, 3));
  t.checkExpect(world1.mst.get(3), new Wall(world1.mst.get(3).previous, world1.mst.get(3).next, 5));
  t.checkExpect(world1.mst.get(4), new Wall(world1.mst.get(4).previous, world1.mst.get(4).next, 6));
}

void testUnion(Tester t) {
	IMaze world1 = new IMaze();

  world1.union(world1.board.get(0).get(0), world1.board.get(0).get(1));
  t.checkExpect(world1.find(world1.board.get(0).get(0)), world1.board.get(0).get(1));
  world1.union(world1.board.get(0).get(1), world1.board.get(1).get(1));
  t.checkExpect(world1.find(world1.board.get(0).get(1)), world1.board.get(1).get(1));
  world1.union(world1.board.get(2).get(0), world1.board.get(0).get(1));
  t.checkExpect(world1.find(world1.board.get(0).get(0)), world1.board.get(1).get(1));
}

void testFind(Tester t) {
	IMaze world1 = new IMaze();
  t.checkExpect(world1.find(world1.board.get(0).get(0)), world1.board.get(0).get(0));
  t.checkExpect(world1.find(world1.board.get(2).get(0)), world1.board.get(2).get(0));
}

void testOnKeyEvent(Tester t) {

	IMaze world1 = new IMaze();
	world1.onKeyEvent("right");
  t.checkExpect(world1.p.thisSide, world1.board.get(0).get(1));
  world1.onKeyEvent("down");
  t.checkExpect(world1.p.thisSide, world1.board.get(1).get(1));
  world1.onKeyEvent("up");
  t.checkExpect(world1.p.thisSide, world1.board.get(0).get(1));
  world1.onKeyEvent("left");
  t.checkExpect(world1.p.thisSide, world1.board.get(0).get(0));
  world1.onKeyEvent("d");
  t.checkExpect(world1.path, new ArrayList<Side>(Arrays.asList(world1.board.get(0).get(0))));
  world1.onKeyEvent("b");
  t.checkExpect(world1.path, new ArrayList<Side>(Arrays.asList(world1.board.get(0).get(0))));
}

void testValidMove(Tester t) {
	IMaze world1 = new IMaze();

  t.checkExpect(world1.p.validMove("up"), false);
  t.checkExpect(world1.p.validMove("left"), false);
  t.checkExpect(world1.p.validMove("down"), true);
  t.checkExpect(world1.p.validMove("right"), true);
}

void testChangeRenderRight(Tester t) {
	IMaze world1 = new IMaze();

  world1.changeRenderRight(world1.board.get(0).get(0));
  t.checkExpect(world1.board.get(0).get(0).hasRight, false);

  world1.changeRenderRight(world1.board.get(2).get(0));
  t.checkExpect(world1.board.get(2).get(0).hasRight, true);
}

void testChangeRenderBottom(Tester t) {
	IMaze world1 = new IMaze();
  world1.changeRenderBottom(world1.board.get(0).get(0));
  t.checkExpect(world1.board.get(0).get(0).hasBottom, false);

  world1.changeRenderBottom(world1.board.get(0).get(1));
  t.checkExpect(world1.board.get(0).get(1).hasBottom, false);

  world1.changeRenderBottom(world1.board.get(2).get(0));
  t.checkExpect(world1.board.get(2).get(0).hasBottom, true);

  world1.changeRenderBottom(world1.board.get(1).get(0));
  t.checkExpect(world1.board.get(1).get(0).hasBottom, false);

  world1.changeRenderBottom(world1.board.get(1).get(1));
  t.checkExpect(world1.board.get(1).get(1).hasBottom, false);

  world1.changeRenderBottom(world1.board.get(2).get(1));
  t.checkExpect(world1.board.get(2).get(1).hasBottom, true);
}

void testAddAtTail(Tester t) {
	Deque<Side> q = new Deque<Side>();

  t.checkExpect(q.size(), 0);
  q.add(new Side(0, 0));
  t.checkExpect(q.size(), 1);
}

void testSize(Tester t) {
	Deque<Side> q = new Deque<Side>();

  t.checkExpect(q.size(), 0);
  q.add(new Side(1, 0));
  t.checkExpect(q.size(), 1);

  t.checkExpect(q.size(), 0);
  q.add(new Side(0, 0));
  t.checkExpect(q.size(), 1);
}

void testRemoveFromHead(Tester t) {
	Deque<Side> q = new Deque<Side>();
  q.add(new Side(0, 0));

  t.checkExpect(q.remove(), new Side(0, 0));
}

void testAddToHead(Tester t) {
  Deque<Side> s = new Deque<Side>();
  t.checkExpect(s.size(), 0);
  s.add(new Side(0, 0));

  t.checkExpect(s.size(), 1);
}

void testAddToTail(Tester t) {
	Deque<Side> s = new Deque<Side>();
  t.checkExpect(s.size(), 0);
  s.add(new Side(0, 0));

  t.checkExpect(s.size(), 1);
}

void testPathDFS(Tester t) {
   IMaze world1 = new IMaze();

  t.checkExpect(g.pathDFS(world1.board.get(0).get(0), world1.board.get(2).get(1)),
          new ArrayList<Side>(Arrays.asList(world1.board.get(0).get(0))));
}

void testPathBFS(Tester t) {
	IMaze world1 = new IMaze();

  t.checkExpect(g.pathBFS(world1.board.get(0).get(0), world1.board.get(2).get(1)),
          new ArrayList<Side>(Arrays.asList(world1.board.get(0).get(0))));
}

void testHasPath(Tester t) {
	IMaze world1 = new IMaze();

  t.checkExpect(g.createPath(world1.board.get(0).get(0), world1.board.get(2).get(1), new Deque<Side>()),
          new ArrayList<Side>(Arrays.asList(world1.board.get(0).get(0))));
}

void testTimer(Tester t) {
	IMaze world1 = new IMaze();
  world1.onTick();

  t.checkInexact(world1.time, 100.0, 0.001);
}

void testBigBang(Tester t) {
  /*MazeWorld world1 = new MazeWorld();
  world1.bigBang(100, 100, .1);*/

  this.RunGame.bigBang(this.RunGame.boardX * IMaze.cellSize,
          this.RunGame.boardY * IMaze.cellSize + IMaze.cellSize,
          this.RunGame.tickRate);
  }
}