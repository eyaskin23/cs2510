import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import java.awt.Color;

interface IMobile {
  int totalWeight();

  int totalHeight();

  boolean isBalanced();

  IMobile buildMobile(IMobile m, int string, int strut);

  int getStrutLength(IMobile m, int strut, int guess);

  int curWidth();

  int leftWidth();

  int rightWidth();

  WorldImage drawMobile();
}

class Simple implements IMobile {
  int length;
  int weight;
  Color color;

  Simple(int length, int weight, Color color) {
    this.length = length;
    this.weight = weight;
    this.color = color;
  }

  /*
  TEMPLATE: 
  Fields: 
  ... this.length ...                              -- int 
  ... this.weight ...                              -- int 
  ... this.color ...                               -- Color
    
  Methods: 
  ... this.totalWeight() ...                       -- int 
  ... this.totalHeight() ...                       -- int
  ... this.isBalanced() ...                        -- boolean 
  ... this.buildMobile(IMobile, int, int) ...      -- IMobile 
  ... this.getStrutLength(IMobile, int, int) ...   -- int 
  ... this.curWidth() ...                          -- int 
  ... this.leftWidth() ...                         -- int 
  ... this.rightWidth() ...                        -- int 
  ... this.drawMobile() ...                        -- WorldImage
    
  Methods on Fields:
   
  */

  // returns the total weight of this IMobile
  public int totalWeight() {
    return this.weight;
  }

  // returns the total height of this IMobile
  public int totalHeight() {
    return this.length + (this.weight / 10);
  }

  // returns whether this IMobile is balanced
  public boolean isBalanced() {
    return true;
  }

  // returns a new balanced mobile with this mobile, given mobile, given string
  // length, and given strut length
  public IMobile buildMobile(IMobile m, int string, int strut) {
    return new Complex(string, this.getStrutLength(m, strut, 0), m.getStrutLength(this, strut, 0),
        this, m);
  }

  /*
  TEMPLATE FIELDS OF PARAMETERS 
  ... m.length ...                                -- int 
  ... m.leftside ...                              -- int 
  ... m.rightside ...                             -- int 
  ... m.left ...                                  -- IMobile 
  ... m.right ...                                 -- IMobile
    
  METHODS ON PARAMETERS 
  ... m.getStrutLength(IMobile, int, int) ...     -- int
    
  */

  // get strut length given this mobile, given mobile, given total strut length,
  // and given guess
  public int getStrutLength(IMobile m, int strut, int guess) {
    if (new Complex(1, guess, strut - guess, this, m).isBalanced()) {
      return guess;
    }
    return this.getStrutLength(m, strut, guess + 1);
  }

  /*
  TEMPLATE 
  FIELDS OF PARAMETERS 
  ... m.length ...                 -- int 
  ... m.leftside ...               -- int  
  ... m.rightside ...              -- int 
  ... m.left ...                   -- IMobile 
  ... m.right ...                  -- IMobile
    
  METHODS ON PARAMETERS
    
  */

  // returns the current width of this IMobile
  public int curWidth() {
    if (this.weight % 10 == 0) {
      return this.weight / 10;
    }
    return this.weight / 10 + 1;
  }

  // returns the width of the right side of this IMobile
  public int leftWidth() {
    if (this.weight % 10 == 0) {
      return this.weight / 20;
    }
    return this.weight / 20 + 1;
  }

  // returns the width of the left side of this IMobile
  public int rightWidth() {
    if (this.weight % 10 == 0) {
      return this.weight / 20;
    }
    return this.weight / 20 + 1;
  }

  // draws this IMobile
  public WorldImage drawMobile() {
    WorldImage vert = new LineImage(new Posn(0, -this.length * 20), Color.BLACK).movePinhole(0,
        this.length * 10);
    WorldImage box = new RectangleImage(this.curWidth() * 20,
        (this.totalHeight() - this.length) * 20, OutlineMode.SOLID, this.color)
        .movePinhole(0, (this.length - this.totalHeight()) * 10);

    return new OverlayImage(vert, box).movePinhole(0, - this.length * 20);
  }
}

class Complex implements IMobile {
  int length;
  int leftside;
  int rightside;
  IMobile left;
  IMobile right;

  Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right;
  }
  /*
  TEMPLATE: 
  Fields: 
  ... this.length ...                                -- int 
  ... this.leftside ...                              -- int
  ... this.rightside ...                             -- int 
  ... this.left ...                                  -- IMobile 
  ... this.right ...                                 -- IMobile
    
  Methods: 
  ... this.totalWeight() ...                         -- int 
  ... this.totalHeight() ...                         -- int
  ... this.isBalanced() ...                          -- boolean 
  ... this.buildMobile(IMobile, int, int) ...        -- IMobile 
  ... this.getStrutLength(IMobile, int, int) ...     -- int 
  ... this.curWidth() ...                            -- int 
  ... this.leftWidth() ...                           -- int 
  ... this.rightWidth() ...                          -- int 
  ... this.drawMobile() ...                          -- WorldImage
    
  Methods on Fields: 
  ... this.left.totalWeight() ...                    -- int 
  ... this.right.totalWeight() ...                   -- int 
  ... this.left.totalHeight() ...                    -- int
  ... this.right.totalHeight() ...                   -- int 
  ... this.left.leftWidth() ...                      -- int
  ... this.right.leftWidth() ...                     -- int 
  ... this.left.rightWidth() ...                     -- int
  ... this.right.rightWidth() ...                    -- int 
  ... this.left.drawMobile() ...                     -- WorldImage 
  ... this.right.drawMobile() ...                    -- WorldImage
  */

  // returns the total weight of this IMobile
  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }

  // returns the total height of this IMobile
  public int totalHeight() {
    if (this.left.totalHeight() > this.right.totalHeight()) {
      return this.length + this.left.totalHeight();
    }
    return this.length + this.right.totalHeight();
  }

  // returns whether this IMobile is balanced
  public boolean isBalanced() {
    return this.leftside * this.left.totalWeight() == this.rightside * this.right.totalWeight();
  }

  // returns a new balanced mobile with this mobile, given mobile,
  // given string length, and given strut length
  public IMobile buildMobile(IMobile m, int string, int strut) {
    return new Complex(string, this.getStrutLength(m, strut, 0), m.getStrutLength(this, strut, 0),
        this, m);
  }

  /*
  TEMPLATE FIELDS OF PARAMETERS 
  ... m.length ...                                -- int 
  ... m.leftside ...                              -- int 
  ... m.rightside ...                             -- int 
  ... m.left ...                                  -- IMobile 
  ... m.right ...                                 -- IMobile
   
  METHODS ON PARAMETERS 
  ... m.getStrutLength(IMobile, int, int) ...     -- int
   
  */

  // get strut length given this mobile, given mobile, given total
  // strut length, and given guess
  public int getStrutLength(IMobile m, int strut, int guess) {
    if (new Complex(1, guess, strut - guess, this, m).isBalanced()) {
      return guess;
    }
    return this.getStrutLength(m, strut, guess + 1);
  }

  /*
  TEMPLATE FIELDS OF PARAMETERS 
  ... m.length ...                                -- int 
  ... m.leftside ...                              -- int 
  ... m.rightside ...                             -- int 
  ... m.left ...                                  -- IMobile 
  ... m.right ...                                 -- IMobile
   
  METHODS ON PARAMETERS 
   
  */

  // gets the current width of this IMobile
  public int curWidth() {
    return this.leftWidth() + this.rightWidth();
  }

  // returns the left width of this IMobile
  public int leftWidth() {
    return Math.max(this.left.leftWidth() + this.leftside, this.right.leftWidth() - this.rightside);
  }

  // returns the right width of this IMobile
  public int rightWidth() {
    return Math.max(this.right.rightWidth() + this.rightside,
        this.left.rightWidth() - this.leftside);
  }

  // draws this IMobile
  public WorldImage drawMobile() {

    WorldImage vert = new LineImage(new Posn(0, -this.length * 20), Color.BLACK).movePinhole(0,
        this.length * 10);
    WorldImage hor1 = new LineImage(new Posn(-this.leftside * 20, 0), Color.BLACK)
        .movePinhole(this.leftside * 10, 0);
    WorldImage hor2 = new LineImage(new Posn(this.rightside * 20, 0), Color.BLACK)
        .movePinhole(-this.rightside * 10, 0);

    WorldImage complexLeft = new OverlayImage(
        new OverlayImage(new OverlayImage(vert, hor1), hor2).movePinhole(-this.leftside * 20, 0),
        this.left.drawMobile());
    WorldImage complexRight = new OverlayImage(
        complexLeft.movePinhole((this.leftside + this.rightside) * 20, 0),
        this.right.drawMobile());
    return complexRight.movePinhole(- this.rightside * 20, - this.length * 20);
  }
}

class ExamplesMobiles {
  IMobile exampleSimple = new Simple(2, 20, Color.blue);
  IMobile s1 = new Simple(2, 36, Color.red);
  IMobile s2 = new Simple(1, 60, Color.green);
  IMobile c1 = new Complex(2, 5, 3, this.s1, this.s2);
  IMobile s3 = new Simple(1, 12, Color.red);
  IMobile c2 = new Complex(2, 8, 1, this.s3, this.c1);
  IMobile s4 = new Simple(1, 36, Color.blue);
  IMobile exampleComplex = new Complex(1, 9, 3, this.s4, this.c2);

  IMobile s5 = new Simple(3, 15, Color.green);
  IMobile example3 = new Complex(1, 7, 2, this.s5, this.exampleComplex);

  IMobile e4 = new Complex(1, 0, 3, this.s4, this.c2);

  IMobile c3 = new Complex(2, 9, 3, this.s1, this.s2);
  IMobile c4 = new Complex(2, 8, 1, this.s3, this.c3);
  IMobile e5 = new Complex(1, 0, 3, this.s4, this.c4);

  // test the totalWeight method
  boolean testTotalWeight(Tester t) {
    return t.checkExpect(this.exampleSimple.totalWeight(), 20)
        && t.checkExpect(this.exampleComplex.totalWeight(), 144)
        && t.checkExpect(this.example3.totalWeight(), 159);
  }

  // test the totalHeight method
  boolean testTotalHeight(Tester t) {
    return t.checkExpect(this.exampleSimple.totalHeight(), 4)
        && t.checkExpect(this.exampleComplex.totalHeight(), 12)
        && t.checkExpect(this.example3.totalHeight(), 13);
  }

  // test the isBalanced method
  boolean testIsBalanced(Tester t) {
    return t.checkExpect(this.exampleSimple.isBalanced(), true)
        && t.checkExpect(this.exampleComplex.isBalanced(), true)
        && t.checkExpect(this.example3.isBalanced(), false);
  }

  // test the buildMobile method
  boolean testBuildMobile(Tester t) {
    return t.checkExpect(this.exampleSimple.buildMobile(exampleSimple, 2, 10),
        new Complex(2, 5, 5, exampleSimple, exampleSimple));
  }

  // test the curWidth method
  boolean testCurWidth(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidth(), 21)
        && t.checkExpect(this.exampleSimple.curWidth(), 2) && t.checkExpect(this.e4.curWidth(), 16)
        && t.checkExpect(this.e5.curWidth(), 17);
  }
  
  //test the drawMobile method
  boolean testDrawMobile(Tester t) {
    return t.checkExpect(this.exampleSimple.drawMobile(), 
      new Simple(2, 20, Color.blue).drawMobile())
        && t.checkExpect(this.s1.drawMobile(), new Simple(2, 36, Color.red).drawMobile())
        && t.checkExpect(this.s2.drawMobile(), this.s2.drawMobile())
       
        // assuming s1 and s2 are correct
        && t.checkExpect(this.c1.drawMobile(), new Complex(2, 5, 3, this.s1, this.s2).drawMobile()) 
        && t.checkExpect(this.s3.drawMobile(), new Simple(1, 12, Color.red).drawMobile())
       
        // assuming s3 and c1 are correct
        && t.checkExpect(this.c2.drawMobile(), new Complex(2, 8, 1, this.s3, this.c1).drawMobile()) 
        && t.checkExpect(this.s4.drawMobile(), new Simple(1, 36, Color.blue).drawMobile())
       
        // assuming s4 and c2 are correct
        && t.checkExpect(this.exampleComplex.drawMobile(), 
            new Complex(1, 9, 3, this.s4, this.c2).drawMobile());
  }

  // shows image at the center of an equally-sized canvas,
  // and the text at the top of the frame is given by description
  boolean showImage(WorldImage image, String description, IMobile m) {
    int width = (int) Math.ceil(m.curWidth() * 25);
    int height = (int) Math.ceil(m.totalHeight() * 25);
    WorldCanvas canvas = new WorldCanvas(width, height, description);
    WorldScene scene = new WorldScene(width, height);
    return canvas.drawScene(scene.placeImageXY(image, m.leftWidth() * 25, 15)) && canvas.show();
  }
  
  // test showImage
  boolean testShowImage(Tester t) {
    return showImage(this.exampleComplex.drawMobile(), "Example Complex", this.exampleComplex);
  }
}