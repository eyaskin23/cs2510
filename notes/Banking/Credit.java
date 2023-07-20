
//Represents a credit line account
class Credit extends Account {

  int creditLine;  // Maximum amount accessible
  double interest; // The interest rate charged
  
  /* TEMPLATE:
   * FIELDS:
   * ... this.accountNum() ...          --- int
   * ... this.balance() ...             --- int
   * ... this.name() ...                --- String
   * ... this.creditLine() ...          --- int
   * ... this.interest() ...            --- double
   * METHODS:
   * ... this.withdraw() ...            --- int 
   * ... this.deposit() ...             --- int
   */
    
  Credit(int accountNum, int balance, String name, int creditLine, double interest) {
    super(accountNum, balance, name);
    this.creditLine = creditLine;
    this.interest = interest;
  }
  
  //withdraws a certain amount of money from a credit account
  int withdraw(int amount) {
    if (this.balance <= 0) {
      throw new RuntimeException("" + amount + " is not available");
    } else if (this.balance - amount <= 0) {
      throw new RuntimeException("" + amount + " is not available");
    } else {
      return this.balance - amount;
    }
  }
  
  //deposits a certain amount of money into a credit card
  int deposit(int funds) {
    if (funds <= 0) {
      return this.balance;
    } else if (this.balance <= 0) {
      return this.balance;
    } else {
      return this.balance + funds;
    }
  }
}