
// Represents a checking account
class Checking extends Account {

  int minimum; // The minimum account balance allowed

  Checking(int accountNum, int balance, String name, int minimum) {
    super(accountNum, balance, name);
    this.minimum = minimum;
  }
  
  //withdraws a certain amount from a checking account
  int withdraw(int amount) {
    if (this.balance <= 0) {
      throw new RuntimeException("" + amount + " is not available");
    } else if (this.balance - amount <= 0) {
      throw new RuntimeException("" + amount + " is not available");
    } else {
      return this.balance - amount;
    }
  }

  //deposits a certain amount from a checking account
  int deposit(int funds) {
    if (funds <= 0) {
      return this.balance;
    } else if (this.balance <= 0) {
      return 0;
    } else {
      return this.balance + funds;
    }
  }
}