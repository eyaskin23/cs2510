import java.awt.Color;
//import java.util.Random;
import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;

// Represents a game of Simon Says
class SimonWorld extends World {
  int score;
  ILoButton buttons;
  boolean acceptingClicks;
  //example of buttons 
  Button red = new Button(Color.RED, 100, 100, new Posn(200, 200), "Red");
  Button blue = new Button(Color.BLUE, 100, 100, new Posn(300, 300), "blue");
  Button green = new Button(Color.GREEN, 100, 100, new Posn(200, 200), "green");
  Button yellow = new Button(Color.YELLOW, 100, 100, new Posn(300, 200), "yellow");
  Button text = new Button(Color.BLACK, 100, 100, new Posn(0, 0), "Score:" + this.score);
  int random;
  ILoButton computerList;
  ILoButton userList;
  int computerCount;
  int userCount;

  /* TEMPLATE: 
  * FIELDS:
  * ... this.score ...                  --- int 
  * ... this.buttons ...                --- ILoButton
  * ... this.acceptingClicks ...        --- boolean
  * ... this.computerList ...           --- ILoButton
  * ... this.userList ...               --- ILoButton
  * ... this.computerCount ...          --- int
  * ... this.userCount ...              --- int
  * ... this.random ...                 --- Random 
  * 
  * METHODS
  * ... this.makeScene() ...                --- WorldScene
  * ... this.onTick() ...                   --- World
  * ... this.lastScene(String msg) ...      --- WorldScene
  * ... this.onMouseClicked(Posn posn) ...  --- SimonWorld
  * 
  * METHODS FOR FIELDS
  * ... this.computerList.checkListEquals() ...  --- boolean
  * ... this.placeImageXY(text.drawText) ...     --- WorldScene 
  */
  
  SimonWorld() {
    this.score = 0; //keeps track of the score
    this.computerList = new MtLoButton(); //keeps track of the previously flashed buttons
    this.userList = new MtLoButton(); //keeps track of the previously pressed buttons
    this.acceptingClicks = false; //if the game is accepting userclicks
    this.computerCount = 0; //how many previous times the computer has flashed 
    this.userCount = 0; //how many previous times the user has clicked
    this.random = (int)(Math.random() * 4); //represents a generated sequence between 4 colors
  }
  
  SimonWorld(int score, ILoButton computerList, ILoButton userList, 
      int computerCount, int userCount) {
    this.computerList = computerList;
    this.userList = userList;
    this.computerCount = computerCount;
    this.userCount = userCount;
    if (this.computerCount == 0 && this.userCount == 0) {
      this.score = this.computerList.checkListIndex(this.userList, this.score);
    } else {
      this.score = score;
    } this.random = (int) (Math.random() * 4);
  }
  
  // Draws the current state of the world
  public WorldScene makeScene() {
    if (this.score < 0) {
      return this.lastScene("You lost!");
    } else if (this.random == 0) {
      return new WorldScene(1000, 1000).placeImageXY(red.drawLit(), 200, 300)
          .placeImageXY(blue.drawDark(), 300, 300).placeImageXY(green.drawDark(), 200, 200)
          .placeImageXY(yellow.drawDark(), 300, 200)
          .placeImageXY(text.drawText("Score: " + this.score), 250, 100);
    } else if (this.random == 1) {
      return new WorldScene(1000, 1000).placeImageXY(red.drawDark(), 200, 300)
          .placeImageXY(blue.drawLit(), 300, 300).placeImageXY(green.drawDark(), 200, 200)
          .placeImageXY(yellow.drawDark(), 300, 200)
          .placeImageXY(text.drawText("Score: " + this.score), 250, 100);
    } else if (this.random == 2) {
      return new WorldScene(1000, 1000).placeImageXY(red.drawDark(), 200, 300)
          .placeImageXY(blue.drawDark(), 300, 300).placeImageXY(green.drawLit(), 200, 200)
          .placeImageXY(yellow.drawDark(), 300, 200)
          .placeImageXY(text.drawText("Score: " + this.score), 250, 100);
    } else {
      return new WorldScene(1000, 1000).placeImageXY(red.drawDark(), 200, 300)
          .placeImageXY(blue.drawDark(), 300, 300).placeImageXY(green.drawDark(), 200, 200)
          .placeImageXY(yellow.drawLit(), 300, 200)
          .placeImageXY(text.drawText("Score: " + this.score), 250, 100);
    }
  }

  // handles ticking of the clock and updating the world if needed
  public World onTick() {
    //if the computer is accepting user clicks, then it returns a random sequence of buttons
    if (acceptingClicks) {
      if (this.computerCount > 0 && random == 0) {
        return new SimonWorld(score, new ConsLoButton(this.red, this.computerList), 
           this.userList, this.computerCount - 1, this.userCount);
      } else if (this.computerCount > 0 && random == 1) {
        return new SimonWorld(score, new ConsLoButton(this.blue, this.computerList), 
           this.userList, this.computerCount - 1, this.userCount);
      } else if (this.computerCount > 0 && random == 2) {
        return new SimonWorld(score, new ConsLoButton(this.green, this.computerList), 
           this.userList, this.computerCount - 1, this.userCount);
      } else if (this.computerCount > 0) {
        return new SimonWorld(score, new ConsLoButton(this.yellow, this.computerList), 
           this.userList, this.computerCount - 1, this.userCount);
      }  else {
        acceptingClicks = true;
        return this;
      }
    } else {
      return this;
    }
  }

  // Returns the final scene with the given message displayed
  public WorldScene lastScene(String msg) {
    WorldScene background = new WorldScene(1000, 1000)
        .placeImageXY(text.drawText("Game Over!"), 250, 100);
    return background;
  }

  // handles mouse clicks and is given the mouse position
  public SimonWorld onMouseClicked(Posn pos) {
    if (!acceptingClicks) {
      if ((pos.x < 200 && pos.x > 100) && (pos.y < 300 && pos.y > 200)) {
        this.userList = new ConsLoButton(this.red, this.userList);
      } else if ((pos.x < 300 && pos.x > 250) && (pos.y < 300 && pos.y > 250)) {
        this.userList = new ConsLoButton(this.blue, this.userList);
      } else if ((pos.x < 200 && pos.x > 150) && (pos.y < 200 && pos.y > 150)) {
        this.userList = new ConsLoButton(this.green, this.userList);
      } else if ((pos.x < 300 && pos.x > 250) && (pos.y < 200 && pos.y > 150)) {
        this.userList = new ConsLoButton(this.yellow, this.userList);
      }
      if (this.userCount > 0) {
        return new SimonWorld(score, this.computerList, this.userList, this.computerCount, 
        this.userCount - 1);
      } else if (this.userCount == 0 && this.computerList.checkListIndex(
          this.userList, score) > 0) {
        return new SimonWorld(this.score + 1, this.computerList, this.userList, 
        this.score, this.score);
      } else {
        acceptingClicks = true;
        return new SimonWorld(this.computerList.checkListIndex(this.userList, score),
        this.computerList,
        this.userList, this.computerCount, this.userCount);
      }
    } else {
      return this;
    }
  }
}

//Represents the four buttons in the game
class Button {
  Color color;
  int height;
  int width;
  String s;

  /* TEMPLATE:
  * FIELDS:
  * ... this.color ... --- Color
  * ... this.height ... --- int
  * ... this.width ... --- int
  * ... this.posn ... --- Posn
  * ... this.s ... --- String
  * METHODS:
  * ... this.drawButton(Color c) --- WorldImage
  * ... this.drawText(String s) --- WorldImage
  * ... this.drawDark() --- WorldImage
  * ... this.drawLit() --- WorldImage
  * METHODS ON FIELDS:
  * ... this.color.brighter() --- WorldImage
  * ... this.color.darker() --- WorldImage
  */

  Button(Color color, int height, int width) {
    this.color = color;
    this.height = height;
    this.width = width;
  }

  Button(Color color, int height, int width, Posn posn, String s) {
    this.color = color;
    this.height = height;
    this.width = width;
    this.s = s;
  }
  
  //draws the image
  WorldImage drawButton(Color c) {
    return new RectangleImage(this.height, this.width, OutlineMode.SOLID, c);
  }
  
  //draws the text
  WorldImage drawText(String s) {
    return new TextImage(s, Color.BLACK);
  }
  
  //draws this button dark
  WorldImage drawDark() {
    return this.drawButton(this.color.darker().darker());
  }
  
  //draws this button lighter
  WorldImage drawLit() {
    return this.drawButton(this.color.brighter().brighter());
  }
}

// Represents a list of buttons
interface ILoButton {

  //checks if the list is equal to the computerCount
  int checkListIndex(ILoButton that, int score);
  
  //checks if the button is equal to the user click
  boolean checkButtonEquals(Button b);
 
}

// Represents an empty list of buttons
class MtLoButton implements ILoButton {

  /* TEMPLATE:
  * METHODS:
  * ... this.checkListIndex(ILoButton, int) --- int
  * ... this.checkButtonEquals(Button) --- boolean 
  */

  //checks the list of buttons index
  public int checkListIndex(ILoButton that, int score) {
    return score + 1;
  }
 
  //checks if the button flashed is equal to the user clicked
  public boolean checkButtonEquals(Button b) {
    return false;
  }
}

// Represents a non-empty list of buttons
class ConsLoButton implements ILoButton {
  Button first;
  ILoButton rest;

  ConsLoButton(Button first, ILoButton rest) {
    this.first = first;
    this.rest = rest;
  }

  /* TEMPLATE:
  * FIELDS:
  * ...this.first... --- Button
  * ...this.rest... --- ILoButton
  * 
  * METHODS:
  * ... this.checkListIndex(ILoButton, int) ...  --- int
  * ... this.checkButtonEquals(Button) ...  --- boolean
  * 
  * METHODS ON FIELDS:
  * ... this.color.equals(Button) ... --- boolean
  * ... that.checkButtonEquals(Button) ... --- boolean
  */

  //checks the index of the ConsLoButton
  public int checkListIndex(ILoButton that, int score) {
    if (that.checkButtonEquals(this.first)) {
      return score + 1;
    } else {
      return -1;
    }
  }
 
  //checks if the clicked color is equal to the button color
  public boolean checkButtonEquals(Button b) {
    return (this.first.color.equals(b.color));
  }
}

// Examples Class
class ExamplesSimon {
  int score;
  Button red = new Button(Color.RED, 100, 100);
  Button blue = new Button(Color.BLUE, 100, 100);
  Button green = new Button(Color.GREEN, 100, 100);
  Button yellow = new Button(Color.YELLOW, 100, 100);
  Button text = new Button(Color.BLACK, 100, 100, new Posn(0, 0), "Score:" + this.score);
  WorldScene finalScene = new WorldScene(1000, 1000)
      .placeImageXY(text.drawText("Game Over!"), 250, 100);
  
  SimonWorld starterWorld = new SimonWorld();
  
  // runs the game by creating a world and calling bigBang
  boolean testSimonSays(Tester t) {
    int sceneSize = 500;
    return starterWorld.bigBang(sceneSize, sceneSize, 0.5);
  }
  
  // test the makeScene method
  void testMakeScene(Tester t) {
    starterWorld.makeScene();
  }
  
  // test the onTick method
  void testOnTick(Tester t) {
    starterWorld.onTick();
  }
  
  // test the lastScene method
  void testLastScene(Tester t) {
    starterWorld.lastScene("You lost! ");
  }
  
  // test the onMouseClicked method
  void testOnMouseClicked(Tester t) {
    starterWorld.onMouseClicked(new Posn(250, 250));
  }

  // test the final scene method
  boolean testFinalScene(Tester t) {
    return t.checkExpect(this.finalScene, new WorldScene(1000, 1000)
     .placeImageXY(this.text.drawText("Game Over!"), 250, 100));
  }

  // test buttons
  boolean testButtons(Tester t) {
    return t.checkExpect(this.red, new Button(Color.RED, 100, 100, new Posn(200, 300), null))
      && t.checkExpect(this.blue, new Button(Color.blue, 100, 100, new Posn(200, 300), null))
      && t.checkExpect(this.green, new Button(Color.green, 100, 100, new Posn(200, 200), null))
      && t.checkExpect(this.yellow, new Button(Color.yellow, 100, 100, new Posn(200, 300), null));
  }
 
  // test drawButtons method
  boolean testDrawButtons(Tester t) {
    return t.checkExpect(this.red.drawButton(Color.RED), 
        new RectangleImage(100, 100, OutlineMode.SOLID, Color.red))
        && t.checkExpect(this.blue.drawButton(Color.BLUE), 
            new RectangleImage(100, 100, OutlineMode.SOLID, Color.BLUE));
  }
  
  // test drawText method
  boolean testDrawText(Tester t) {
    return t.checkExpect(this.red.drawText("Hello!"), new TextImage("Hello!", Color.BLACK))
        && t.checkExpect(this.red.drawText("Hi!"), new TextImage("Hi!", Color.BLACK));
  }
  
  // test drawDark method
  boolean testDrawDark(Tester t) {
    return t.checkExpect(this.red.drawDark(), this.red.drawButton(Color.RED.darker().darker()))
        && t.checkExpect(this.blue.drawDark(), this.blue.drawButton(Color.BLUE.darker().darker()));
  }
  
  // test drawLit method
  boolean testDrawLit(Tester t) {
    return t.checkExpect(this.red.drawLit(), this.red.drawButton(Color.RED.brighter().brighter()))
        && t.checkExpect(this.blue.drawLit(), 
            this.blue.drawButton(Color.BLUE.brighter().brighter()));
  }
  
  // test the checkListIndex method
  boolean testCheckListIndex(Tester t) {
    return t.checkExpect(new ConsLoButton(this.red, new MtLoButton()).checkListIndex(
        new ConsLoButton(this.red, new MtLoButton()), 5), 6)
        && t.checkExpect(new ConsLoButton(this.red, new MtLoButton()).checkListIndex(
            new ConsLoButton(this.blue, new MtLoButton()), 5), -1)
        && t.checkExpect(new MtLoButton().checkListIndex(new MtLoButton(), 5), 6);
  }
  
  // test the checkButtonEquals method
  boolean testCheckButtonEquals(Tester t) {
    return t.checkExpect(new ConsLoButton(this.red, 
        new MtLoButton()).checkButtonEquals(this.red), true)
        && t.checkExpect(new ConsLoButton(this.red, 
            new MtLoButton()).checkButtonEquals(this.blue), false)
        && t.checkExpect(new MtLoButton().checkButtonEquals(this.blue), false);
  }
}

