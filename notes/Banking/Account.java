
// Represents a bank account
abstract class Account {

  int accountNum;  // Must be unique
  int balance;     // Must remain above zero (others Accts have more restrictions)
  String name;     // Name on the account
  
  /* TEMPLATE:
   * FIELDS:
   * ... this.accountNum ...        --- int
   * ... this.balance ...           --- int
   * ... this.name ...              --- String
   *    
   * METHODS:
   * ... this.withdraw() ...        --- int
   * ... this.deposit() ...         --- int
   */

  Account(int accountNum, int balance, String name) {
    this.accountNum = accountNum;
    this.balance = balance;
    this.name = name;
  }
  
  //withdraws a certain amount of money from an account
  abstract int withdraw(int amount);
  
  //deposits a certain amount of money into an account
  abstract int deposit(int funds);
  
}
