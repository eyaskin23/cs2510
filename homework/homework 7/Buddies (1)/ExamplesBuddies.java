import tester.*;

//runs tests for the buddies problem
class ExamplesBuddies {

  Person ann;
  Person bob;
  Person cole;
  Person ed;
  Person hank;
  Person dan;
  Person fay;
  Person gabi;
  Person kim;
  Person len;
  Person jan;
  Person ann2;
  Person bob2;

  ExamplesBuddies() {
    this.initPerson();
    this.initBuddies();
  }

  // creates a list of example persons
  void initPerson() {
    ann = new Person("Ann");
    bob = new Person("Bob");
    cole = new Person("Cole");
    ed = new Person("Ed");
    hank = new Person("Hank");
    dan = new Person("Dan");
    fay = new Person("Fay");
    gabi = new Person("Gabi");
    kim = new Person("Kim");
    len = new Person("Len");
    jan = new Person("Jan");

    ann2 = new Person("Ann",
        new ConsLoBuddy(this.cole, new ConsLoBuddy(this.bob, new MTLoBuddy())));
    bob2 = new Person("Bob", new ConsLoBuddy(this.hank,
        new ConsLoBuddy(this.ed, new ConsLoBuddy(this.ann, new MTLoBuddy()))));
  }

  // modifies the list to include buddies
  void initBuddies() {
    ann.addBuddy(bob);
    ann.addBuddy(cole);
    bob.addBuddy(ann);
    bob.addBuddy(ed);
    bob.addBuddy(hank);
    cole.addBuddy(dan);
    dan.addBuddy(cole);
    ed.addBuddy(fay);
    fay.addBuddy(ed);
    fay.addBuddy(gabi);
    gabi.addBuddy(ed);
    gabi.addBuddy(fay);
    jan.addBuddy(kim);
    jan.addBuddy(len);
    kim.addBuddy(jan);
    kim.addBuddy(len);
    len.addBuddy(jan);
    len.addBuddy(kim);
  }

  // tests the addBuddy method
  void testaddBuddy(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(this.ann, this.ann2);
    t.checkExpect(this.bob, this.bob2);
  }

  // tests the hasDirectBuddy method in Person class
  void testHasDirectBuddy(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ann.hasDirectBuddy(bob), true);
    t.checkExpect(jan.hasDirectBuddy(kim), true);
    t.checkExpect(ann.hasDirectBuddy(len), false);
  }

  // test the hasDirectBuddy method in ILoBuddy
  void testContains(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ann.buddies.contains(bob), true);
    t.checkExpect(jan.buddies.contains(kim), true);
    t.checkExpect(ann.buddies.contains(len), false);
    t.checkExpect(new MTLoBuddy().contains(ann), false);
  }

  // tests the commonBuddies method in Person class
  void testCountCommonBuddies(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ann.countCommonBuddies(ed), 0);
    t.checkExpect(ann.countCommonBuddies(bob), 0);
    t.checkExpect(ed.countCommonBuddies(gabi), 1);
    t.checkExpect(fay.countCommonBuddies(gabi), 1);
  }

  // tests the commonBuddies method in ILoBuddy
  void testCountCommonBuddies2(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(new MTLoBuddy().countCommonBuddies(ed.buddies), 0);
    t.checkExpect(ann.buddies.countCommonBuddies(ed.buddies), 0);
    t.checkExpect(ann.buddies.countCommonBuddies(bob.buddies), 0);
    t.checkExpect(ed.buddies.countCommonBuddies(gabi.buddies), 1);
    t.checkExpect(len.buddies.countCommonBuddies(kim.buddies), 1);
  }

  // tests the extendedBuddy method in Person class
  void testHasExtendedBuddy(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ann.hasExtendedBuddy(fay), true);
    t.checkExpect(bob.hasExtendedBuddy(fay), true);
    t.checkExpect(ann.hasExtendedBuddy(bob), true);
    t.checkExpect(jan.hasExtendedBuddy(kim), true);
    t.checkExpect(fay.hasExtendedBuddy(ann), false);
  }

  // tests the extendedBuddy method in ILoBuddy
  void testcontainsExtendedBuddy2(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ann.buddies.containsExtendedBuddy(dan, new MTLoBuddy()), true);
    t.checkExpect(bob.buddies.containsExtendedBuddy(fay, new MTLoBuddy()), true);
    t.checkExpect(bob.buddies.containsExtendedBuddy(ann, new ConsLoBuddy(bob, new MTLoBuddy())),
        true);
    t.checkExpect(fay.buddies.containsExtendedBuddy(ann, new MTLoBuddy()), false);
    t.checkExpect(new MTLoBuddy().containsExtendedBuddy(ann, new MTLoBuddy()), false);
  }

  // tests the extendedBuddy method in ILoBuddy
  void testcontainsExtendedBuddy(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ann.containsExtendedBuddy(dan, new MTLoBuddy()), true);
    t.checkExpect(bob.containsExtendedBuddy(fay, new MTLoBuddy()), true);
    t.checkExpect(bob.containsExtendedBuddy(ann, new ConsLoBuddy(bob, new MTLoBuddy())), false);
    t.checkExpect(fay.containsExtendedBuddy(ann, new MTLoBuddy()), false);
  }

  // tests the partyCount method in Person class
  void testPartyCount(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ann.partyCount(), 8);
    t.checkExpect(bob.partyCount(), 8);
    t.checkExpect(kim.partyCount(), 3);
    t.checkExpect(ed.partyCount(), 3);
    t.checkExpect(hank.partyCount(), 1);
  }

  // tests the addPartyMembers method in Person class
  void testAddPartyMembers(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ed.addPartyMembers(new MTLoBuddy()),
        new ConsLoBuddy(gabi, new ConsLoBuddy(fay, new ConsLoBuddy(ed, new MTLoBuddy()))));
    t.checkExpect(kim.addPartyMembers(new ConsLoBuddy(len, new MTLoBuddy())),
        new ConsLoBuddy(jan, new ConsLoBuddy(kim, new ConsLoBuddy(len, new MTLoBuddy()))));
    t.checkExpect(hank.addPartyMembers(new MTLoBuddy()), new ConsLoBuddy(hank, new MTLoBuddy()));
  }

  // tests the add PartyMembers method in ILoBuddy
  void testAddPartMembers2(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(ed.buddies.addPartyMembers(new MTLoBuddy()),
        new ConsLoBuddy(ed, new ConsLoBuddy(gabi, new ConsLoBuddy(fay, new MTLoBuddy()))));
    t.checkExpect(kim.buddies.addPartyMembers(new ConsLoBuddy(kim, new MTLoBuddy())),
        new ConsLoBuddy(jan, new ConsLoBuddy(len, new ConsLoBuddy(kim, new MTLoBuddy()))));
    t.checkExpect(kim.buddies.addPartyMembers(new ConsLoBuddy(jan, new MTLoBuddy())),
        new ConsLoBuddy(kim, new ConsLoBuddy(len, new ConsLoBuddy(jan, new MTLoBuddy()))));
    t.checkExpect(hank.buddies.addPartyMembers(new MTLoBuddy()), new MTLoBuddy());
    t.checkExpect(new MTLoBuddy().addPartyMembers(new MTLoBuddy()), new MTLoBuddy());
  }

  // test the length function in ILoBuddy
  void testLength(Tester t) {
    initPerson();
    initBuddies();

    t.checkExpect(bob.buddies.length(), 3);
    t.checkExpect(ann.buddies.length(), 2);
    t.checkExpect(ed.buddies.length(), 1);
    t.checkExpect(new MTLoBuddy().length(), 0);
  }
}