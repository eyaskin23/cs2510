// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
  MTLoBuddy() {
  }

  // an empty person cannot have a direct buddy
  public boolean contains(Person that) {
    return false;
  }

  // there are zero common buddies between empty persons
  public int countCommonBuddies(ILoBuddy that) {
    return 0;
  }

  // an empty person cannot have extended buddies
  public boolean containsExtendedBuddy(Person that, ILoBuddy counted) {
    return false;
  }

  // an empty person will not have a party count
  public ILoBuddy addPartyMembers(ILoBuddy that) {
    return that;
  }

  // find the length of this ILoBuddy
  public int length() {
    return 0;
  }
}
