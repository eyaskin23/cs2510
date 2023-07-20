// represents a list of Person's buddies
interface ILoBuddy {

  // checks if the person has a direct buddy
  boolean contains(Person that);

  // counts the amount of common direct buddies
  int countCommonBuddies(ILoBuddy that);

  // checks if the person will be invited
  boolean containsExtendedBuddy(Person that, ILoBuddy alreadyCounted);

  // returns the list of people that will show up to the party
  ILoBuddy addPartyMembers(ILoBuddy that);

  // finds the length of this list of buddies
  public int length();
}
