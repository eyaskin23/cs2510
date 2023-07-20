
//represents real estate listings
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
}

//represents examples of real estate listings
class ExamplesListings {
  ExamplesListings() {}
    
  RealEstateListing bostonCondo = new RealEstateListing(false, 
        2010, 700, 350000, "Boston");  

  
  RealEstateListing beachHouse = new RealEstateListing(true, 
        1995, 2000, 699999, "Yarmouth");
  
  RealEstateListing losAngeles = new RealEstateListing(true, 
      2018, 500, 50000, "Los Angeles");
}

  
  
  





