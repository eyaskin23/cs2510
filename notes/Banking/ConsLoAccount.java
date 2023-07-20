
// Represents a non-empty List of Accounts...
class ConsLoAccount implements ILoAccount {

  Account first;
  ILoAccount rest;

  /* TEMPLATE:
   * FIELDS:
   * ... this.first ...   -- Account
   * ... this.rest ...    --- ILoAccount
   */
  
  
  
  ConsLoAccount(Account first, ILoAccount rest) {
    this.first = first;
    this.rest = rest;
  }
}