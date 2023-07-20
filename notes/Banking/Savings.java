
// Represents a savings account
class Savings extends Account {

  double interest; // The interest rate
  
  /* TEMPLATE:
   * FIELDS:
   * ... this.accountNum ...       --- int
   * ... this.balance ...          --- int
   * ... this.name ...             --- String
   * ... this.interest             --- double
   * METHODS: 
   * ... this.withdraw() ...       --- int
   * ... this.deposit() ...        --- int
   */

  Savings(int accountNum, int balance, String name, double interest) {
    super(accountNum, balance, name);
    this.interest = interest;
  }
  
  //withdraws a certain amount of money into a savings account
  int withdraw(int amount) {
    if (this.balance < 0) {
      throw new RuntimeException("" + amount + " is not available");
    } else if (this.balance - amount < 0) {
      throw new RuntimeException("" + amount + " is not available");
    } else {
      return this.balance - amount;
    }
  }
  
  //deposits a certain amount of money into a savings account
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
