
// represents a Person with a user name and a list of buddies
class Person {

  String username;
  ILoBuddy buddies;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
  }

  Person(String username, ILoBuddy buddies) {
    this.username = username;
    this.buddies = buddies;
  }

  // adds a buddy to this person's list
  public void addBuddy(Person buddy) {
    this.buddies = new ConsLoBuddy(buddy, this.buddies);
  }

  // checks if this person has a direct buddy with another person
  public boolean hasDirectBuddy(Person that) {
    return this.username.equals(that.username) || this.buddies.contains(that);
  }

  // counts common buddies between two people
  public int countCommonBuddies(Person that) {
    return this.buddies.countCommonBuddies(that.buddies);
  }

  // checks to see if a person contains an extended buddy(indirectly/directly)
  public boolean hasExtendedBuddy(Person that) {
    return this.containsExtendedBuddy(that, new MTLoBuddy());
  }

  // checks to see is a person contains this extended issue, given a list of
  // already checked buddies
  public boolean containsExtendedBuddy(Person that, ILoBuddy checked) {
    if (checked.contains(this)) {
      return false;
    }
    else {
      return this.hasDirectBuddy(that)
          || this.buddies.containsExtendedBuddy(that, new ConsLoBuddy(this, checked));
    }
  }

  // counts the number of people that will be invited by this
  // person(indirectly/directly)
  public int partyCount() {
    return this.addPartyMembers(new MTLoBuddy()).length();
  }

  // finds the list of the people that will be invited by this
  // person(indirectly/directly)
  public ILoBuddy addPartyMembers(ILoBuddy that) {
    return this.buddies.addPartyMembers(new ConsLoBuddy(this, that));
  }
}
