
import tester.*;

class RealEstateListing {
  boolean singleFamily;
  int year;
  int squareFootage;
  int price;
  String city;
    
  RealEstateListing(boolean singleFamily, int year, int squareFootage, 
      int price, String city) {
    this.singleFamily = singleFamily;
    this.year = year;
    this.squareFootage = squareFootage;
    this.price = price;
    this.city = city;
  }

  public boolean isSingleFamily() {
    return singleFamily;
  }

  public int getYear() {
    return year;
  }

  public int getSquareFootage() {
    return squareFootage;
  }

  public int getPrice() {
    return price;
  }

  public String getCity() {
    return city;
  }
}

  
class ExamplesRealEstateListings {
  ExamplesRealEstateListings() {}
    
  RealEstateListing condo = new RealEstateListing(false, 
        2010, 700, 350000, "Boston");  

  
  RealEstateListing single = new RealEstateListing(true, 
        1995, 2000, 699999, "Yarmouth");
  
  void testcondo(Tester t) {
  
    t.checkExpect(condo.isSingleFamily(), false);
    t.checkExpect(condo.getYear(), 2010);
    t.checkExpect(condo.getSquareFootage(), 700);
    t.checkExpect(condo.getPrice(), 350000);
    t.checkExpect(condo.getCity(), "Boston");
  
  }
  
  void testsingle(Tester t) {
    t.checkExpect(single.isSingleFamily(), true);
    t.checkExpect(single.getYear(), 1995);
    t.checkExpect(single.getSquareFootage(), 2000);
    t.checkExpect(single.getPrice(), 699999);
    t.checkExpect(single.getCity(), "Yarmouth");
  
  } 
}

  
  
  





