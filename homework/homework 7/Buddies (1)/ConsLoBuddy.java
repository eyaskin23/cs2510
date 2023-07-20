// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  // checks if the person has a direct buddy "that"
  public boolean contains(Person that) {
    return this.first.equals(that) || this.rest.contains(that);
  }

  // checks if this person and that person have common buddies
  public int countCommonBuddies(ILoBuddy that) {
    if (that.contains(this.first)) {
      return 1 + this.rest.countCommonBuddies(that);
    }
    else {
      return this.rest.countCommonBuddies(that);
    }
  }

  // does this person contain that extended buddy
  public boolean containsExtendedBuddy(Person that, ILoBuddy counted) {
    return this.first.containsExtendedBuddy(that, counted) || 
        this.rest.containsExtendedBuddy(that, counted);
  }

  // how many people are going to be indirectly
  // or directly invited by this person?
  public ILoBuddy addPartyMembers(ILoBuddy that) {
    if (that.contains(this.first)) {
      return this.rest.addPartyMembers(that);
    }
    else {
      return this.rest.addPartyMembers(this.first.addPartyMembers(that));
    }
  }

  // find the length of this ILoBuddy
  public int length() {
    return 1 + this.rest.length();
  }
}
