import tester.Tester;

//Design the isExpired() method that determines if the card is expired. Assume the
//current year is 2023.

//Design the makeTransaction(double) method that takes in a double and subtracts
//the double from the balance, if applicable. If the parameter is greater than the balance, the
//balance should be 0.

//Design the sameCard(ICard) method that takes in a Card and determines if the two
//cards are the same card. A Card is the same as another card if all fields are identical.

//an interface for Cards
interface ICard {

// determines if the given card is expired
boolean isExpired();

// makes a transaction
ICard makeTransaction(double price);

// checks if two cards are the same
boolean sameCard(ICard given);

// checks if given cards are the same
boolean checkValues(int cnum, int year, int cvv);

// checks if the credit card is the same as the given one
boolean sameCreditCard(CreditCard given);

//checks if the credit card is the same as the given one
boolean sameDebitCard(DebitCard given);

//checks if the credit card is the same as the given one
boolean sameGiftCard(GiftCard given);
}

//an abstract class for Cards
abstract class ACard implements ICard {
int cnum;
int year;
int cvv;

public ACard(int cnum, int year, int cvv) {
  if (new Utils().checkValidCnum(cnum)) {
    this.cnum = cnum;
  }
  if (new Utils().checkYear(year)) {
    this.year = year;
  }
  if (new Utils().checkCVV(cvv)) {
    this.cvv = cvv;
  }
}

// checks if a card is expired in the year '23
public boolean isExpired() {
  return this.year < 23;
}

// checks that the fields of this and given are the same
public boolean checkValues(int cnum, int year, int cvv) {
  return this.cnum == cnum
      && this.year == year
      && this.cvv == cvv;
}

// checks if the credit card is the same
public boolean sameCreditCard(CreditCard given) {
  return false;
}

//checks if the debit card is the same
public boolean sameDebitCard(DebitCard given) {
  return false;
}

//checks if the gift card is the same
public boolean sameGiftCard(GiftCard given) {
  return false;
}
}

//tools for constructor exceptions
//all exceptions thrown in this class
class Utils {

// determines if cnum is valid
boolean checkValidCnum(int cnum) {
  // must have 5 digits
  // first digit can't be 0
  if (cnum < 10000 || cnum > 99999) {
    throw new IllegalArgumentException("");
  } else {
    return true;
  }
}

// checks that the year is valid
boolean checkYear(int year) {
  if (year >= 10 && year <= 99) {
    return true;
  } else {
    throw new IllegalArgumentException("Year is out of range"); // terminally ends method, doesn't return anything
  }
}

// checks that the CVV is valid
boolean checkCVV(int cvv) {
  if (cvv >= 10 && cvv <= 99) {
    return true;
  } else {
    throw new IllegalArgumentException("");
  }
}

// checks that a balance is valid
boolean checkBalance(double balance) {
  if (balance >= 0.0) {
    return true;
  } else {
    throw new IllegalArgumentException("Balance can't be negative");
  }
}

// All check range and can either return boolean or return the int
}


//represents a credit card
class CreditCard extends ACard {
String name;

CreditCard(int cnum, int year, int cvv, String name){
  super(cnum, year, cvv);
  this.name = name;
}

//subtracts the given amount from the balance
public ICard makeTransaction(double price) {
  return this;
}

// checks if two cards are the same
public boolean sameCard(ICard given) {
  return given.sameCreditCard(this);
}

//checks that two credit cards are the same
public boolean sameCreditCard(CreditCard given) {
  return this.checkValues(given.cnum, given.year, given.cvv)
      && this.name.equals(given.name);
  // fields of parameter works because we know its the same type, AND we're in the same class
}
}

//represents a debit card
class DebitCard extends ACard {
String name;
double balance;

DebitCard(int cnum, int year, int cvv, String name, double balance){
  super(cnum, year, cvv);
  this.name = name;

  if (new Utils().checkBalance(balance)) {
    this.balance = balance; 
  }
}

// subtracts the given amount from the balance
public ICard makeTransaction(double price) {
  if (this.balance - price < 0.0) {
    return new DebitCard(this.cnum, this.year, this.cvv, this.name, 0.0);
  } else {
    return new DebitCard(this.cnum, this.year, this.cvv, this.name, this.balance - price);
  }
}

// checks if two cards are the same
public boolean sameCard(ICard given) {
  return given.sameDebitCard(this);
}

// checks that two debit cards are the same
public boolean sameDebitCard(DebitCard given) {
  return this.checkValues(given.cnum, given.year, given.cvv)
      && Math.abs(this.balance - given.balance) < 0.001
      && this.name.equals(given.name);
}
}

//represents a gift card
class GiftCard extends ACard {
double balance;

GiftCard(int cnum, int year, int cvv, double balance){
  super(cnum, year, cvv);
  if (new Utils().checkBalance(balance)) {
    this.balance = balance;
  }
}

//subtracts the given amount from the balance
public ICard makeTransaction(double price) {
  if (this.balance - price < 0.0) {
    return new GiftCard(this.cnum, this.year, this.cvv, 0.0);
  } else {
    return new GiftCard(this.cnum, this.year, this.cvv, this.balance - price);
  }
}

@Override
//checks if two cards are the same
public boolean sameCard(ICard given) {
  return given.sameGiftCard(this);
}

@Override
// checks that two debit cards are the same
public boolean sameGiftCard(GiftCard given) {
  return this.checkValues(given.cnum, given.year, given.cvv)
      && Math.abs(this.balance - given.balance) < 0.001;
}
}


interface ILoString {
	  
	  boolean contains(String s);
	  
	  boolean isSubsetOf(ILoString that);
	  
	  boolean hasSameContentAs(ILoString that);
	  
	  ILoString removeDuplicatesNoOrder();
	  
	  ILoString removeDuplicates();
	  
	  ILoString remove(String s);
	}

	class MtLoString implements ILoString {
	  MtLoString() {}
	  
	  public boolean contains(String s) {
	    return false;
	  }
	  
	  public boolean isSubsetOf(ILoString that) {
	    return true;
	  }
	  
	  public boolean hasSameContentAs(ILoString that) {
	    return that.isSubsetOf(this);
	  }
	  
	  public ILoString removeDuplicatesNoOrder() {
	    return new MtLoString();
	  }
	  
	  public ILoString removeDuplicates() {
	    return new MtLoString();
	  }
	  
	  public ILoString remove(String s) {
	    return new MtLoString();
	  }
	}

	class ConsLoString implements ILoString {
	  String first;
	  ILoString rest;
	  
	  ConsLoString(String first, ILoString rest) {
	    this.first = first;
	    this.rest = rest;
	  }
	  
	  public boolean contains(String s) {
	    return this.first.equals(s) || this.rest.contains(s);
	  }
	  
	  public boolean isSubsetOf(ILoString that) {
	    return that.contains(this.first) && this.rest.isSubsetOf(that);
	  }
	  
	  public boolean hasSameContentAs(ILoString that) {
	    return this.isSubsetOf(that) && that.isSubsetOf(this);
	  }
	  
	  public ILoString removeDuplicatesNoOrder() {
	    if (this.rest.contains(this.first)) {
	      return this.rest.removeDuplicatesNoOrder();
	    }
	    else {
	      return new ConsLoString(this.first, this.rest.removeDuplicatesNoOrder());
	    }
	  }
	  
	  public ILoString removeDuplicates() {
	    return new ConsLoString(this.first, this.rest.remove(this.first).removeDuplicates());
	  }
	  
	  public ILoString remove(String s) {
	    if (this.first.equals(s)) {
	      return this.rest.remove(s);
	    }
	    else {
	      return new ConsLoString(this.first, this.rest.remove(s));
	    }
	  }
	}

	class ExamplesStrings {
	  ExamplesStrings() {}
	  
	  ILoString mt = new MtLoString();
	  ILoString list1 = new ConsLoString("a", new ConsLoString("b", 
	      new ConsLoString("c", new MtLoString())));
	  ILoString list2 = new ConsLoString("b", new ConsLoString("a", 
	      new ConsLoString("c", new MtLoString())));
	  ILoString list1capitalized = new ConsLoString("a", new ConsLoString("B", 
	      new ConsLoString("c", new MtLoString())));
	  
	  ILoString list1duplicate1 = new ConsLoString("a", (new ConsLoString("a", new ConsLoString("b", 
	      new ConsLoString("c", new MtLoString())))));
	  ILoString list1duplicate2 = new ConsLoString("a", (new ConsLoString("b", new ConsLoString("c", 
	      new ConsLoString("a", new MtLoString())))));
	  ILoString list1duplicate3 = new ConsLoString("a", (new ConsLoString("a", new ConsLoString("a", 
	      new ConsLoString("a", new MtLoString())))));
	  ILoString list1duplicate2result = new ConsLoString("b", new ConsLoString("c", 
	      new ConsLoString("a", new MtLoString())));
	  
	  boolean testHasSameContent(Tester t) {
	    return t.checkExpect(list1.hasSameContentAs(list2), true)
	        && t.checkExpect(list2.hasSameContentAs(list1), true)
	        && t.checkExpect(list1.hasSameContentAs(list1capitalized), false);
	  }
	  
	  boolean testRemoveDuplicates(Tester t) {
	    return t.checkExpect(list1duplicate1.removeDuplicates(), list1)
	        && t.checkExpect(list1duplicate2.removeDuplicatesNoOrder(), list1duplicate2result)
	        && t.checkExpect(list1duplicate3.removeDuplicates(), new ConsLoString("a", mt));
	  }
	}
	
	
	
	























