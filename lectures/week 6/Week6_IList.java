
import java.util.ArrayList;
import tester.Tester;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// Represents a single square of the game area
class Cell {
	// the game starts at the very left corner
	int x;
	int y;
	String color;
	boolean flooded;
	Posn pos;
	Cell left;
	Cell top;
	Cell right;
	Cell bottom;
	// list of colors
	ArrayList<String> colors;

	// constructor including all fields
	Cell(int x, int y, boolean flooded, int colorNum) {
		this.x = x;
		this.y = y;
		initColors();
		int random = (int) (Math.random() * colorNum);
		this.color = colors.get(random);
		this.flooded = flooded;
		this.pos = new Posn(this.x, this.y);
	}

	// convenience constructor for testing
	Cell(int x, int y, String color, boolean flooded, Cell left, Cell top, Cell right, Cell bottom) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.flooded = flooded;
		this.pos = new Posn(this.x, this.y);
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	// draws the cell
	WorldImage image() {
		if (this.color.equals("RED")) {
			return new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED);
		}
		else if (this.color.equals("BLUE")) {
			return new RectangleImage(20, 20, OutlineMode.SOLID, Color.BLUE);
		}
		else if (this.color.equals("GREEN")) {
			return new RectangleImage(20, 20, OutlineMode.SOLID, Color.GREEN);
		}
		else if (this.color.equals("PURPLE")) {
			return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(184, 0, 245));
		}
		else if (this.color.equals("BLACK")) {
			return new RectangleImage(20, 20, OutlineMode.SOLID, Color.BLACK);
		}
		else if (this.color.equals("YELLOW")) {
			return new RectangleImage(20, 20, OutlineMode.SOLID, Color.YELLOW);
		}
		else if (this.color.equals("PINK")) {
			return new RectangleImage(20, 20, OutlineMode.SOLID, Color.PINK);
		}
		else {
			return new RectangleImage(20, 20, OutlineMode.SOLID, Color.ORANGE);
		}
	}

	// adds to the list of strings to update the color list
	void initColors() {
		colors = new ArrayList<String>();
		colors.add("BLUE");
		colors.add("PURPLE");
		colors.add("PINK");
		colors.add("RED");
		colors.add("GREEN");
		colors.add("BLACK");
		colors.add("YELLOW");
		colors.add("ORANGE");
	}

	// changes the cell color to the inputed color
	void setColor(String color) {
		this.color = color;
	}

	// updates the adjacent cells to the selected one
	void update(String color) {
		if (this.left != null && !this.left.flooded && this.left.color.equals(color)) {
			this.left.flooded = true;
		}
		if (this.top != null && !this.top.flooded && this.top.color.equals(color)) {
			this.top.flooded = true;
		}
		if (this.right != null && !this.right.flooded && this.right.color.equals(color)) {
			this.right.flooded = true;
		}
		if (this.bottom != null && !this.bottom.flooded && this.bottom.color.equals(color)) {
			this.bottom.flooded = true;
		}
	}
}

// represents the flooditworld
class FloodItWorld extends World {
	// includes the boardSize, number of colors used,
	// the amount of clicks/required clicks, time
	int boardSize = 20;
	int colorsUsed = 8;
	ArrayList<Cell> board;
	static final int testBoardSize = 6;
	int required;
	int clicks = 0;
	int time = 0;

	// constructor for FloodItWorld
	FloodItWorld(int bdSize, int colors) {
		boardSize = bdSize;
		colorsUsed = colors;
		createCells(boardSize);
		if (boardSize > 12) {
			required = boardSize + colorsUsed + 10;
		}
		else if (boardSize < 4) {
			required = boardSize + colorsUsed - 2;
		}
		else {
			required = boardSize + colorsUsed - 1;
		}
	}

	// convenience constructor for testing
	FloodItWorld() {
		boardSize = 2;
		colorsUsed = 3;
		required = 3;
	}

	// creates the board with cells based on the board size
	void createCells(int size) {
		board = new ArrayList<Cell>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == 0 && j == 0) {
					board.add(new Cell(0, 0, true, this.colorsUsed));
				}
				else {
					board.add(new Cell(i, j, false, this.colorsUsed));
				}
			}
		}
		// modifies each cell according to its left/right side
		for (int k = 0; k < board.size(); k++) {
			Cell modifyThis = board.get(k);
			if (board.get(k).x == 0) {
				modifyThis.left = null;
			}
			else {
				modifyThis.left = board.get(k - size);
			}
			if (board.get(k).x == size - 1) {
				modifyThis.right = null;
			}
			else {
				modifyThis.right = board.get(k + size);
			}
			if (board.get(k).y == 0) {
				modifyThis.top = null;
			}
			else {
				modifyThis.top = board.get(k - 1);
			}
			if (board.get(k).y == size - 1) {
				modifyThis.bottom = null;
			}
			else {
				modifyThis.bottom = board.get(k + 1);
			}
		}
	}

	// determines which cell was clicked on and
	// returns the position of that
	public Cell whichCell(Posn pos) {
		Cell thisCell = null;
		for (Cell c : board) {
			if ((c.x <= ((pos.x - 71) / 20)) && (((pos.x - 71) / 20) <= c.x)
					&& (c.y <= ((pos.y - 71) / 20)) && (((pos.y - 71) / 20) <= c.y)) {
				thisCell = c;
			}
		}
		return thisCell;
	}

	// changes this cell to be the color of the clicked one
	public void updateOnClick(Cell cell) {
		if (cell != null) {
			Cell modifyThis = board.get(0);
			modifyThis.color = cell.color;
			board.set(0, modifyThis);
		}
	}

	// updates the world state based on the cell clicked
	public void onMouseClicked(Posn pos) {
		if ((pos.x < 70 || pos.x > (boardSize * 20 + 70))
				|| (pos.y < 70 || pos.y > (boardSize * 20 + 70))) {
		}
		else {
			this.updateOnClick(this.whichCell(pos));
			clicks ++;
		}
	}

	// draws out the scene for the game including the board and cells
	public WorldScene makeScene() {
		WorldScene finalScene = new WorldScene(900, 800);
		finalScene.placeImageXY(new TextImage("Moves:", 25, FontStyle.BOLD, Color.black), 680, 200);
		finalScene.placeImageXY(
				new TextImage(Integer.toString(clicks) + "     ", 25, FontStyle.BOLD_ITALIC, Color.black),
				750, 200);
		finalScene.placeImageXY(
				new TextImage(" / " + Integer.toString(required), 25, FontStyle.BOLD_ITALIC, Color.black),
				800, 200);
		finalScene.placeImageXY(
				new TextImage("Welcome to Flood-IT! CS 2510", 25, FontStyle.BOLD_ITALIC, Color.BLACK), 280,
				625);
		finalScene.placeImageXY(
				new TextImage("By: Evelyn Yaskin", 25, FontStyle.BOLD, Color.black), 280, 670);
		WorldImage outline = new RectangleImage((boardSize + 2) * 20, (boardSize + 2) * 20,
				OutlineMode.SOLID, new Color(42, 64, 99));
		finalScene.placeImageXY(outline, ((boardSize + 2) * 10) + 50, ((boardSize + 2) * 10) + 50);
		finalScene.placeImageXY(
				new TextImage("Time: " + time / 10 + "s", 25, FontStyle.BOLD_ITALIC, Color.black), 750,
				250);
		if (clicks >= required && (!allFlooded())) {
			finalScene.placeImageXY(new TextImage("YOU LOSE!", 25, Color.RED), 700, 300);
		}
		else if (clicks <= required && allFlooded()) {
			finalScene.placeImageXY(new TextImage("YOU WIN!", 25, Color.GREEN), 700, 300);
		}
		if (time > 600) {
			finalScene.placeImageXY(
					new TextImage("Time: 1 min" + (time - 60) / 10, 25, FontStyle.BOLD, Color.black),
					650, 250);
		}
		for (Cell c : board) {
			finalScene.placeImageXY(c.image(), 80 + 20 * c.x, 80 + 20 * c.y);
		}
		return finalScene;
	}


	// updates the world based on the cell clicked
	public void updateWorld() {
		Cell floodingFromCell = this.board.get(0);
		String floodingTo = floodingFromCell.color;
		for (int i = 0; i < board.size(); i++) {
			Cell cell = board.get(i);
			if (cell.flooded) {
				cell.setColor(floodingTo);
				cell.update(floodingTo);
			}
			makeScene();
		}
	}

	// checks if all the cells are the same color
	boolean allFlooded() {
		boolean result = true;
		for (Cell c : board) {
			result = result && c.flooded;
		}
		return result;
	}

	// updates the world state every tick of the game
	public void onTick() {
		time++;
		updateWorld();

		if (clicks >= required && !allFlooded()) {
			time = 0;
		}
		if (clicks <= required && allFlooded()) {
			time = 0;
		}
	}

	// resets the game when the person presses "R"
	public void onKeyEvent(String key) {
		if (key.equals("r")) {
			this.board = new ArrayList<Cell>();
			clicks = 0;
			time = 0;
			createCells(boardSize);
		}
	}

	// starts the game with the right grid size and amount of colors inputed
	public void startGame(int gridSize, int numberOfColor) {
		if (numberOfColor > 8) {
			throw new IllegalArgumentException("Number of colors cannot exceed 8");
		}
		boardSize = gridSize;
		colorsUsed = numberOfColor;
		FloodItWorld w = new FloodItWorld(gridSize, numberOfColor);
		w.bigBang(900, 800, 0.1);
	}
}

// represents examples of the flooditworld
class ExamplesFloodIt {
	Cell blue;
	Cell red;
	Cell green;
	Cell yellow;
	Cell orange;
	Cell black;
	Cell pink;
	Cell purple;
	ArrayList<String> loColors;
	ArrayList<Cell> testBoard;
	FloodItWorld exWorld;
	FloodItWorld exWorld2;
	ArrayList<Cell> exBoard;

	// starts the initial condition
	void initWorld() {
		blue = new Cell(0, 0, "BLUE", true, null, null, null, null);
		red = new Cell(1, 0, "RED", false, blue, null, null, null);
		green = new Cell(0, 1, "GREEN", false, null, blue, null, null);
		yellow = new Cell(1, 1, "YELLOW", false, green, red, null, null);
		orange = new Cell(0, 2, "ORANGE", false, null, green, null, null);
		black = new Cell(1, 2, "BLACK", false, orange, yellow, null, null);
		pink = new Cell(0, 3, "PINK", false, null, orange, null, null);
		purple = new Cell(1, 3, "PURPLE", false, pink, black, null, null);
		blue.right = red;
		blue.bottom = green;
		red.bottom = yellow;
		green.right = yellow;
		green.bottom = orange;
		yellow.bottom = black;
		orange.bottom = pink;
		orange.right = black;
		black.bottom = purple;
		pink.right = purple;

		// list of available colors
		loColors = new ArrayList<String>();
		loColors.add("BLUE");
		loColors.add("RED");
		loColors.add("GREEN");
		loColors.add("PURPLE");
		loColors.add("BLACK");
		loColors.add("YELLOW");
		loColors.add("PINK");
		loColors.add("ORANGE");

		ArrayList<Cell> testBoard = new ArrayList<Cell>();
		testBoard.add(blue);
		testBoard.add(green);
		testBoard.add(orange);
		testBoard.add(pink);
		testBoard.add(red);
		testBoard.add(yellow);
		testBoard.add(black);
		testBoard.add(purple);

		// examples of flooditworlds
		exWorld = new FloodItWorld();
		exWorld2 = new FloodItWorld();
		exWorld.createCells(FloodItWorld.testBoardSize);

		// example board used for testing
		exBoard = exWorld.board;
		exWorld2.board = new ArrayList<Cell>();
		exWorld2.board.add(blue);
		exWorld2.board.add(green);
		exWorld2.board.add(red);
		exWorld2.board.add(yellow);
	}

	// tests image method
	void testImage(Tester t) {
		initWorld();
		t.checkExpect(this.blue.image(), new RectangleImage(20, 20, OutlineMode.SOLID, Color.blue));
		t.checkExpect(this.red.image(), new RectangleImage(20, 20, OutlineMode.SOLID, Color.red));
		t.checkExpect(this.green.image(), new RectangleImage(20, 20, OutlineMode.SOLID, Color.green));
		t.checkExpect(this.yellow.image(),
				new RectangleImage(20, 20, OutlineMode.SOLID, Color.yellow));
		t.checkExpect(this.orange.image(),
				new RectangleImage(20, 20, OutlineMode.SOLID, Color.orange));
		t.checkExpect(this.black.image(), new RectangleImage(20, 20, OutlineMode.SOLID, Color.black));
		t.checkExpect(this.pink.image(), new RectangleImage(20, 20, OutlineMode.SOLID, Color.pink));
		t.checkExpect(this.purple.image(),
				new RectangleImage(20, 20, OutlineMode.SOLID, new Color(184, 0, 245)));
	}

	// tests initColors method
	void testInitColors(Tester t) {
		this.loColors = null;
		t.checkExpect(loColors, null);
		initWorld();
		t.checkExpect(loColors.contains("BLUE"), true);
		t.checkExpect(loColors.contains("RED"), true);
		t.checkExpect(loColors.contains("GREEN"), true);
		t.checkExpect(loColors.contains("BLACK"), true);
		t.checkExpect(loColors.contains("PURPLE"), true);
		t.checkExpect(loColors.contains("YELLOW"), true);
		t.checkExpect(loColors.contains("PINK"), true);
		t.checkExpect(loColors.contains("ORANGE"), true);
	}

	// tests the setColor method.
	void testSetColor(Tester t) {
		initWorld();
		t.checkExpect(blue.color, "BLUE");
		t.checkExpect(red.color, "RED");
		t.checkExpect(green.color, "GREEN");
		green.setColor("PINK");
		blue.setColor("RED");
		red.setColor("BLUE");
		t.checkExpect(blue.color, "RED");
		t.checkExpect(red.color, "BLUE");
		t.checkExpect(green.color, "PINK");
	}

	// tests the update method
	void testUpdate(Tester t) {
		initWorld();
		t.checkExpect(blue.right.flooded, false);
		t.checkExpect(blue.bottom.flooded, false);
		blue.update("BLUE");
		t.checkExpect(blue.right.flooded, false);
		t.checkExpect(blue.bottom.flooded, false);
		blue.color = "RED";
		blue.update("RED");
		t.checkExpect(blue.right.flooded, true);
		t.checkExpect(blue.bottom.flooded, false);
		blue.update("GREEN");
		t.checkExpect(blue.right.flooded, true);
		t.checkExpect(blue.bottom.flooded, true);
	}

	// tests for whichCell method
	void testWhichCell(Tester t) {
		initWorld();
		t.checkExpect(exWorld.whichCell(new Posn(70, 70)), exBoard.get(0));
		t.checkExpect(exWorld.whichCell(new Posn(125, 125)), exBoard.get(14));
		t.checkExpect(exWorld.whichCell(new Posn(190, 190)), exBoard.get(35));
	}

	// tests createCells method
	void testCreateCells(Tester t) {
		initWorld();
		t.checkExpect(exBoard.get(0).flooded, true);
		for (int i = 0; i < exWorld.board.size(); i++) {
			Cell temp = exWorld.board.get(i);
			int bdSize = 6;
			t.checkRange(temp.x, 0, bdSize);
			t.checkRange(temp.y, 0, bdSize);
			t.checkExpect(loColors.contains(temp.color), true);
			if (temp.x == 0) {
				t.checkExpect(temp.left, null);
			}
			else {
				t.checkExpect(temp.left, exWorld.board.get(i - bdSize));
			}
			if (temp.y == 0) {
				t.checkExpect(temp.top, null);
			}
			else {
				t.checkExpect(temp.top, exWorld.board.get(i - 1));
			}
			if (temp.x == (bdSize - 1)) {
				t.checkExpect(temp.right, null);
			}
			else {
				t.checkExpect(temp.right, exWorld.board.get(i + bdSize));
			}
			if (temp.y == (bdSize - 1)) {
				t.checkExpect(temp.bottom, null);
			}
			else {
				t.checkExpect(temp.bottom, exWorld.board.get(i + 1));
			}
		}
		for (int i = 1; i < exBoard.size(); i++) {
			t.checkExpect(exBoard.get(i).flooded, false);
		}
	}

	// tests makeScene Method
	void testMakeScene(Tester t) {
		initWorld();
		WorldScene finalScene = new WorldScene(900, 800);
		WorldImage outline = new RectangleImage(80, 80, OutlineMode.SOLID, new Color(42, 64, 99));
		finalScene.placeImageXY(new TextImage("Moves:", 25, FontStyle.BOLD, Color.BLACK), 580, 200);
		finalScene.placeImageXY(new TextImage(0 + "     ", 25, FontStyle.BOLD_ITALIC, Color.BLACK), 650,
				200);
		finalScene.placeImageXY(new TextImage(" / 3", 25, FontStyle.BOLD_ITALIC, Color.BLACK), 700,
				200);
		finalScene.placeImageXY(
				new TextImage("Welcome to Flood-IT! CS 2510", 25, FontStyle.BOLD_ITALIC, Color.BLACK), 240,
				550);
		finalScene.placeImageXY(outline, 90, 90);
		finalScene.placeImageXY(
				new TextImage("Time: " + exWorld2.time / 10 + "s", 25, FontStyle.BOLD_ITALIC, Color.BLACK),
				650, 250);
		if (exWorld2.time > 60) {
			finalScene.placeImageXY(
					new TextImage("Time: 1 min" + (exWorld2.time - 60) / 10, 25, FontStyle.BOLD, Color.black),
					650, 250);
		}
		finalScene.placeImageXY(blue.image(), 80, 80);
		finalScene.placeImageXY(green.image(), 80, 100);
		finalScene.placeImageXY(red.image(), 100, 80);
		finalScene.placeImageXY(yellow.image(), 100, 100);
		t.checkExpect(exWorld2.makeScene(), finalScene);
	}

	// tests for updateOnClick
	void testUpdateOnClick(Tester t) {
		initWorld();
		exWorld2.board = new ArrayList<Cell>();
		exWorld2.board.add(blue);
		t.checkExpect(exWorld2.board.get(0), blue);
		exWorld2.updateOnClick(red);
		t.checkExpect(exWorld2.board.get(0).color, "RED");
	}

	// tests for onMouseClicked method
	void testOnMouseClicked(Tester t) {
		initWorld();
		exWorld2.makeScene();
		ArrayList<Cell> test = exWorld2.board;
		t.checkExpect(test.get(0), blue);
		t.checkExpect(exWorld2.clicks, 0);
		exWorld2.onMouseClicked(new Posn(0, 0));
		t.checkExpect(test.get(0), blue);
		t.checkExpect(exWorld2.clicks, 0);
		exWorld2.onMouseClicked(new Posn(100, 100));
		t.checkExpect(test.get(0).color, "YELLOW");
		t.checkExpect(exWorld2.clicks, 1);
		exWorld2.onMouseClicked(new Posn(100, 100));
		t.checkExpect(exWorld2.clicks, 2);
	}

	// tests on key event
	void testOnKeyEvent(Tester t) {
		initWorld();
		exWorld2.createCells(FloodItWorld.testBoardSize);
		ArrayList<Cell> tester = new ArrayList<Cell>();
		tester = exWorld2.board;
		int testBdSize = FloodItWorld.testBoardSize;
		t.checkExpect(exWorld2.boardSize == 2, true);
		t.checkExpect(exWorld2.colorsUsed == 3, true);
		t.checkExpect(testBdSize == 6, true);
		t.checkExpect(exWorld2.board.equals(tester), true);
		exWorld2.onKeyEvent("a");
		t.checkExpect(exWorld2.boardSize == 2, true);
		t.checkExpect(exWorld2.colorsUsed == 3, true);
		t.checkExpect(testBdSize == 6, true);
		t.checkExpect(exWorld2.board.equals(tester), true);
		exWorld2.onKeyEvent("r");
		t.checkExpect(exWorld2.boardSize == 2, true);
		t.checkExpect(exWorld2.colorsUsed == 3, true);
		t.checkExpect(testBdSize == 6, true);
		t.checkExpect(exWorld2.board.equals(tester), false);
	}

	// Tests for allFlooded method
	void testAllFlooded(Tester t) {
		initWorld();
		t.checkExpect(exWorld2.allFlooded(), false);
		for (Cell c : exWorld2.board) {
			c.flooded = true;
		}
		t.checkExpect(exWorld2.allFlooded(), true);
	}

	// tests the update move world
	void testUpmovesdateWorld(Tester t) {
		initWorld();
		t.checkExpect(exWorld2.board.get(0), blue);
		exWorld2.board.get(1).flooded = true;
		exWorld2.board.get(2).flooded = true;
		exWorld2.updateWorld();
		t.checkExpect(exWorld2.board.get(1).color, "BLUE");
		t.checkExpect(exWorld2.board.get(2).color, "BLUE");
	}

	// runs the game, the person is allowed to put in the grid size and number of colors
	void testGame(Tester t) {
		FloodItWorld w = new FloodItWorld(20, 6);
		t.checkException(new IllegalArgumentException("Number of colors cannot exceed 8"), w,
				"startGame", 20, 9);
		w.startGame(26, 8);
		t.checkExpect(w.boardSize, 20);
		t.checkExpect(w.colorsUsed, 6);
		t.checkExpect(w.clicks, 0);
		t.checkExpect(w.required, 36);
	}
}