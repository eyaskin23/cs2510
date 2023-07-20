import tester.Tester;

// represents the ingredients of a bagel
class BagelRecipe {
  
  double flour;
  double water;
  double yeast;
  double salt;
  double malt;

  BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    Utils utils = new Utils();
    this.flour = flour;
    this.water = water;
    this.yeast = yeast;
    this.salt = salt;
    this.malt = malt;
    utils.checkSaltAndYeastRatio(flour, yeast, salt, "Salt and yeast must = 1/20flour");
    utils.checkEqualToWater(flour, water, "Flour must be equal to water");
    utils.checkEqualToMalt(yeast, malt, "Yeast must be equal to malt");
  }

  BagelRecipe(double flour, double yeast) {
    Utils utils = new Utils();
    double salt = (flour * 1 / 20) - yeast;
    this.flour = flour;
    this.water = flour;
    this.yeast = yeast;
    this.salt = salt;
    this.malt = yeast;
    utils.checkSaltAndYeastRatio(flour, yeast, salt, "Salt and yeast must = 1/20flour");
    utils.checkEqualToWater(flour, water, "Flour must be equal to water");
    utils.checkEqualToMalt(yeast, malt, "Yeast must be equal to malt");
  }

  BagelRecipe(double flourVolume, double yeastVolume, double saltVolume) {
    Utils utils = new Utils();
    double flour = (double) flourVolume * 4.25;
    double yeast = (double) yeastVolume * 1 / 48 * 5;
    double salt = (double) saltVolume * 1 / 48 * 10;
    this.flour = flour;
    this.water = flour;
    this.yeast = yeast;
    this.salt = salt;
    this.malt = yeast;
    utils.checkSaltAndYeastRatio(flour, yeast, salt, "Salt and yeast must = 1/20flour");
    utils.checkEqualToWater(flour, water, "Flour must be equal to water");
    utils.checkEqualToMalt(yeast, malt, "Yeast must be equal to malt");
  }
  
  /*
  TEMPLATE
  FIELDS:
  ... this.flour ...                                    -- double
  ... this.water ...                                    -- double
  ... this.yeast ...                                    -- double
  ... this.salt ...                                     -- double
  ... this.malt ...                                     -- double
  
  METHODS:
  ... this.sameRecipe(BagelRecipe) ...                  -- boolean
  
  METHODS ON FIELDS:
  
  */
  
  // is this bagel made with the same recipe as the given bagel?
  public boolean sameRecipe(BagelRecipe other) {
    return Math.abs(this.flour - other.flour) < 0.001
        && Math.abs(this.water - other.water) < 0.001
        && Math.abs(this.yeast - other.yeast) < 0.001
        && Math.abs(this.salt - other.salt) < 0.001
        && Math.abs(this.malt - other.malt) < 0.001;
  }
}

// represents the functions to ensure a perfect bagel
class Utils {
  
  /*
  TEMPLATE
  FIELDS:
  
  METHODS:
  ... this.checkSaltAndYeastRatio(double, double, double, String) ...           -- boolean
  ... this.checkEqualToWater(double, double, String) ...                        -- boolean
  ... this.checkEqualToMalt(double, double, String) ...                         -- boolean
  
  METHODS ON FIELDS:
  
  */
  
  // checks if the salt + yeast are 1/20th the weight of the flour
  boolean checkSaltAndYeastRatio(double flour, double yeast, double salt, String message) {
    if (Math.abs(yeast + salt - flour / 20) < 0.001) {
      return true;
    }
    else {
      throw new IllegalArgumentException(message);

    }
  }

  // checks if the weight of the flour is equal to the weight of the water
  boolean checkEqualToWater(double f, double w, String message) {
    if (Math.abs(f - w) < 0.001) {
      return true;
    }
    else {
      throw new IllegalArgumentException(message);
    }
  }

  // checks if the weight of the yeast is equal the weight of the malt
  boolean checkEqualToMalt(double y, double m, String message) {
    if (Math.abs(y - m) < 0.001) {
      return true;
    }
    else {
      throw new IllegalArgumentException(message);
    }
  }
}

class ExamplesBagels {
  BagelRecipe one = new BagelRecipe(5.0, 2.0, 4.1);
  BagelRecipe two = new BagelRecipe(5.0, 1.0, 4.6);
  BagelRecipe simple = new BagelRecipe(100, 100);
  BagelRecipe bagel1 = new BagelRecipe(6.0, 6.0, 0.15, 0.15, 0.15);
  BagelRecipe correct1 = new BagelRecipe(5.0, 5.0, 0.125, 0.125, 0.125);

  // check if the bagel is a perfect bagel
  boolean testCheckCorrect(Tester t) {
    return t.checkExpect(simple, new BagelRecipe(100, 100))
        && t.checkExpect(correct1, new BagelRecipe(5.0, 5.0, 0.125, 0.125, 0.125));
  }

  // check if the flour and the water are the same weight
  boolean testCheckFlourandWater(Tester t) {
    return t.checkConstructorException(new IllegalArgumentException("Flour must be equal to water"),
        "BagelRecipe", 5.0, 6.0, 0.2, 0.05, 0.2);
  }

  // check if the yeast and the malt are the same weight
  boolean testCheckYeastandMalt(Tester t) {
    return t.checkConstructorException(new IllegalArgumentException("Yeast must be equal to malt"),
        "BagelRecipe", 5.0, 5.0, 0.2, 0.05, 0.3);
  }

  // check if the salt and the yeast are the same weight
  boolean testCheckSaltandYeastRatio(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Salt and yeast must = 1/20flour"), "BagelRecipe", 5.0, 5.0,
        0.05, 0.05, 0.3);
  }

  // check the sameRecipe method
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(bagel1.sameRecipe(correct1), false)
        && t.checkExpect(bagel1.sameRecipe(
            new BagelRecipe(6.0, 6.0, 0.15, 0.15, 0.15)), true);
  }
}