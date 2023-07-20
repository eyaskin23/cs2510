
// Represents a Bank with list of accounts
class Bank {
    
  String name;
  ILoAccount accounts;
  
  /* TEMPLATE:
   * FIELDS:
   * ... this.name ...              --- String
   * ... this.accounts ...          --- ILoAccount 
   * METHODS:
   * ... this.add() ...             --- void
   * ... this.removeAccount() ...   --- void
   * ... this.withdraw2() ...       --- void
   * ... this.deposit2() ...        --- void
   */
    
  Bank(String name) {
    this.name = name;

    // Each bank starts with no accounts
    this.accounts = new MtLoAccount();
  }
  
  //adds a new account to a previous one
  void add(Account acct) {
    this.accounts = new ConsLoAccount(acct, this.accounts);
  }
  
  //removes an account from a previous one
  void removeAccount(Account acct) {
    this.accounts = new ConsLoAccount(acct, new MtLoAccount());
  }
  
  //deposits money into a bank
  public void deposit2(int amount, int accountNum) {
    if (amount <= 0) {
      throw new RuntimeException("");
    } else if (accountNum == 0) {
      throw new RuntimeException("");
    } else {
      return;
    }
  }
 
  //withdraws a certain amount of money from a bank
  public void withdraw2(int amount, int accountNum) {
    if (amount <= 0) {
      throw new RuntimeException("");
    } else if (accountNum == 0) {
      throw new RuntimeException("");
    } else {
      return;
    }
  }
}
  
 