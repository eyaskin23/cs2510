import tester.*;

// Bank Account Examples and Tests
class Examples {

  Account check1;
  Account check2;
  Account savings1;
  Account savings2;
  Bank checking1;
  Bank checking2;
  Bank saving1;
  Bank saving2;
  
 
  /* METHODS: 
   * ... this.reset() ...            --- void
   * ... this.testExceptions() ...   --- void
   * ... this.testWithdraw() ...     --- void
   */
  
  Examples() { 
    reset();
  }

  // Initializes accounts to use for testing with effects.
  // We place inside reset() so we can "reuse" the accounts
  void reset() {

    // Initialize the account examples
    check1 = new Checking(1, 100, "First Checking Account", 20);
    savings1 = new Savings(4, 200, "First Savings Account", 2.5);
    check2 = new Checking(20, 2000, "Second Checking Account", 40);
    savings2 = new Savings(400, 200, "Second Savings Account", 100);
    checking1 = new Bank("checking 1");
  }

  // Tests the exceptions we expect to be thrown when
  //   performing an "illegal" action.
  void testExceptions(Tester t) {
    reset();
    
    t.checkException("Test for invalid Checking withdraw",
        new RuntimeException("1000 is not available"),
        this.check1,
        "withdraw",
        1000);
  }
  
  //tests the withdraw method for an account
  void testWithdraw(Tester t) {
    reset();

    t.checkExpect(check1.withdraw(2), 98);
    t.checkExpect(savings1.withdraw(5), 195);
    t.checkExpect(savings2.withdraw(10), 190);
  }
  
  //tests the deposit method for an account
  void testDeposit(Tester t) {
    reset();

    t.checkExpect(check1.deposit(10), 110);
    t.checkExpect(check2.deposit(10), 2010);
    t.checkExpect(savings1.deposit(100), 300);
    t.checkExpect(savings2.deposit(100), 300);
 
  }
}
