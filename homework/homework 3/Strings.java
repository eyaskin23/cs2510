// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  ILoString sort();

  ILoString insert(String s);

  boolean isSorted();

  boolean compareString(String s);

  ILoString interleave(ILoString that);

  ILoString merge(ILoString that);

  ILoString reverse();

  ILoString reversedList(ILoString l);

  boolean isDoubledList();

  boolean stringEquals(String s);

  boolean isPalindromeList();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  /*
  TEMPLATE 
  FIELDS:
   
  METHODS: 
  ... this.combine() ...                    -- String 
  ... this.sort() ...                       -- ILoString
  ... this.insert(String) ...               -- ILoString 
  ... this.isSorted() ...                   -- boolean
  ... this.compareString(String) ...        -- boolean 
  ... this.interleave(ILoString) ...        -- ILoString 
  ... this.merge(ILoString) ...             -- ILoString 
  ... this.reverse() ...                    -- ILoString 
  ... this.reversedList(ILoString) ...      -- ILoString 
  ... this.isDoubledList() ...              -- boolean
  ... this.stringEquals(String) ...         -- boolean 
  ... this.isPalindromeList() ...           -- boolean
   
  METHODS FOR FIELDS:
    
  */

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // sorts this ILoString in alphabetical order, converting all strings to
  // lowercase
  public ILoString sort() {
    return this;
  }

  // in MtLoString insert the given String into this empty list of Strings
  // already sorted alphabetically
  public ILoString insert(String s) {
    return new ConsLoString(s.toLowerCase(), this);
  }

  // determines if this ILoString is sorted in alphabetical order,
  // case-insensitively
  public boolean isSorted() {
    return true;
  }

  // checks if this String comes alphabetically after the given String
  public boolean compareString(String s) {
    return true;
  }

  // takes this list of Strings and a given list of Strings, and produces a
  // list where the first, third, fifth... elements are from this list, and the
  // second, fourth, sixth... elements are from the given list. Any “leftover”
  // elements will be left at the end
  public ILoString interleave(ILoString that) {
    return that;
  }

  // produces a sorted list of Strings that contains all items in both lists
  public ILoString merge(ILoString that) {
    return that;
  }

  // reverses this ILoString
  public ILoString reverse() {
    return this;
  }

  // produces the reversed list of this ILoString so far
  public ILoString reversedList(ILoString l) {
    return l;
  }

  // checks if this ILoString contains pairs of identical strings
  public boolean isDoubledList() {
    return true;
  }

  // checks if the first String in this ILoString equals the given String
  public boolean stringEquals(String s) {
    return false;
  }

  // checks if whether this list is a palindrome
  public boolean isPalindromeList() {
    return true;
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
  TEMPLATE 
  FIELDS 
  ... this.first ...                       -- String 
  ... this.rest ...                        -- ILoString
   
  METHODS 
  ... this.combine() ...                   -- String 
  ... this.sort() ...                      -- ILoString
  ... this.insert(String) ...              -- ILoString 
  ... this.isSorted() ...                  -- boolean
  ... this.compareString(String) ...       -- boolean 
  ... this.interleave(ILoString) ...       -- ILoString 
  ... this.merge(ILoString) ...            -- ILoString 
  ... this.reverse() ...                   -- ILoString 
  ... this.reversedList(ILoString) ...     -- ILoString 
  ... this.isDoubledList() ...             -- boolean 
  ... this.stringEquals(String) ...        -- boolean 
  ... this.isPalindromeList() ...          -- boolean
   
  METHODS FOR FIELDS 
  ... this.first.concat(String) ...                -- String
  ... this.first.compareTo(String) ...              -- int 
  ... this.rest.combine() ...                      -- String
  ... this.rest.sort() ...                         -- ILoString 
  ... this.first.toLowerCase() ...                 -- String 
  ... this.rest.insert(String) ...                 -- ILoString
  ... this.rest.compareString(String) ...          -- boolean 
  ... this.rest.isSorted() ...                     -- boolean 
  ... this.first.compareToIgnoreCase(String) ...   -- int
  ... this.rest.merge(ILoString) ...               -- ILoString
  ... this.rest.reversedList(ILoString) ...        -- ILoString
  ... this.rest.stringEquals(String) ...           -- boolean 
  ... this.rest.isDoubledList() ...                -- boolean 
  ... this.rest.isPalindromeList() ...             -- boolean
    
  */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // sorts this ILoString in alphabetical order, converting all strings to
  // lowercase
  public ILoString sort() {
    return this.rest.sort().insert(this.first);
  }

  // in ConsLoString insert the given String into this list of Strings
  // already sorted alphabetically
  public ILoString insert(String s) {
    if (this.first.compareToIgnoreCase(s) < 0) {
      return new ConsLoString(this.first.toLowerCase(), this.rest.insert(s));
    }
    else {
      return new ConsLoString(s.toLowerCase(),
          new ConsLoString(this.first.toLowerCase(), this.rest));
    }
  }

  // determines if this ILoString is sorted in alphabetical order,
  // case-insensitively
  public boolean isSorted() {
    if (this.rest.compareString(this.first)) {
      return this.rest.isSorted();
    }
    else {
      return false;
    }
  }

  // checks if this String comes alphabetically after the given String
  public boolean compareString(String s) {
    return this.first.compareToIgnoreCase(s) >= 0;
  }

  // takes this list of Strings and a given list of Strings, and produces a
  // list where the first, third, fifth... elements are from this list, and the
  // second, fourth, sixth... elements are from the given list. Any “leftover”
  // elements will be left at the end
  public ILoString interleave(ILoString that) {
    return new ConsLoString(this.first, that.interleave(this.rest));
  }

  /*
  TEMPLATE: everything in the template for ConsLoString plus 
  FIELDS OF PARAMETERS: 
  ... that.first ...                     -- String 
  ... that.rest ...                      -- ILoString
   
  METHODS ON PARAMETERS: 
  ... that.interleave(ILoString) ...     -- ILoString
    
  */

  // produces a sorted list of Strings that contains all items in both lists
  public ILoString merge(ILoString that) {
    return this.rest.merge(that.insert(this.first));
  }

  /*
  TEMPLATE: everything in the template for ConsLoString plus 
  FIELDS OF PARAMETERS: 
  ... that.first ...                     -- String 
  ... that.rest ...                      -- ILoString
   
  METHODS ON PARAMETERS: 
  ... that.insert(String) ...            -- ILoString
   
  */

  // reverses this ILoString
  public ILoString reverse() {
    return this.reversedList(new MtLoString());
  }

  // produces the reversed list of this ILoString so far
  public ILoString reversedList(ILoString l) {
    return this.rest.reversedList(new ConsLoString(this.first, l));
  }

  /*
  TEMPLATE: everything in the template for ConsLoString plus FIELDS OF
  PARAMETERS: 
  ... l.first ...           -- String 
  ... l.rest ...            -- ILoString
    
  METHODS ON PARAMETERS:
    
  */

  // checks if this ILoString contains pairs of identical strings
  public boolean isDoubledList() {
    return this.rest.stringEquals(this.first);
  }

  // checks if the first String in this ILoString equals the given String
  public boolean stringEquals(String s) {
    if (this.first.equals(s)) {
      return this.rest.isDoubledList();
    }
    return false;
  }

  // returns if whether this list is a palindrome
  public boolean isPalindromeList() {
    return this.interleave(this.reverse()).isDoubledList();
  }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.")
        && t.checkExpect(this.sortedMary.combine(), "a had lamb.little mary ");
  }

  ILoString sortedMary = new ConsLoString("a ", new ConsLoString("had ", new ConsLoString("lamb.",
      new ConsLoString("little ", new ConsLoString("mary ", new MtLoString())))));
  ILoString unorderedList = new ConsLoString("a", new ConsLoString("b",
      new ConsLoString("c", new ConsLoString("d", new ConsLoString("e",
          new ConsLoString("d", new ConsLoString("c", new ConsLoString("b",
              new ConsLoString("a", new MtLoString())))))))));
  ILoString sortedUnorderedList = new ConsLoString("a", new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("b", new ConsLoString("c",
          new ConsLoString("c", new ConsLoString("d", new ConsLoString("d",
              new ConsLoString("e", new MtLoString())))))))));

  // test the method sort
  boolean testSort(Tester t) {
    return t.checkExpect(this.mary.sort(), this.sortedMary)
        && t.checkExpect(this.sortedMary.sort(), this.sortedMary)
        && t.checkExpect(this.unorderedList.sort(), this.sortedUnorderedList);
  }

  // test the method insert
  boolean testInsert(Tester t) {
    return t.checkExpect(this.mary.insert("hi"),
        new ConsLoString("hi",
            new ConsLoString("mary ", new ConsLoString("had ",
                new ConsLoString("a ",
                    new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))))))
        && t.checkExpect(this.sortedMary.insert("hi"),
            new ConsLoString("a ",
                new ConsLoString("had ", new ConsLoString("hi", new ConsLoString("lamb.",
                    new ConsLoString("little ", new ConsLoString("mary ", new MtLoString())))))));
  }
  
  ILoString aaa = new ConsLoString("a", new ConsLoString("a", new ConsLoString("a", 
      new MtLoString())));

  // test the method isSorted
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.sortedMary.isSorted(), true)
        && t.checkExpect(this.aaa.isSorted(), true)
        && t.checkExpect(this.mary.isSorted(), false)
        && t.checkExpect(this.unorderedList.isSorted(), false);
  }

  // test the compareString method
  boolean testCompareString(Tester t) {
    return t.checkExpect(this.mary.compareString("hi"), true)
        && t.checkExpect(this.sortedMary.compareString("hi"), false);
  }

  ILoString shortList = new ConsLoString("Hello", new ConsLoString("World", new MtLoString()));

  // test the interleave method
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.mary.interleave(this.sortedMary), new ConsLoString("Mary ",
        new ConsLoString("a ", new ConsLoString("had ", new ConsLoString("had ",
            new ConsLoString("a ", new ConsLoString("lamb.", new ConsLoString("little ",
                new ConsLoString("little ",
                    new ConsLoString("lamb.", new ConsLoString("mary ", new MtLoString())))))))))))
        && t.checkExpect(this.sortedMary.interleave(this.sortedMary),
            new ConsLoString("a ",
                new ConsLoString("a ",
                    new ConsLoString("had ",
                        new ConsLoString("had ",
                            new ConsLoString("lamb.",
                                new ConsLoString("lamb.",
                                    new ConsLoString("little ", new ConsLoString("little ",
                                        new ConsLoString("mary ",
                                            new ConsLoString("mary ", new MtLoString())))))))))))
        && t.checkExpect(this.sortedMary.interleave(this.shortList),
            new ConsLoString("a ", new ConsLoString("Hello",
                new ConsLoString("had ", new ConsLoString("World", new ConsLoString("lamb.",
                    new ConsLoString("little ", new ConsLoString("mary ", new MtLoString()))))))));
  }

  // test the merge method
  boolean testMerge(Tester t) {
    return t.checkExpect(this.sortedMary.merge(this.sortedMary), new ConsLoString("a ",
        new ConsLoString("a ", new ConsLoString("had ", new ConsLoString("had ",
            new ConsLoString("lamb.", new ConsLoString("lamb.", new ConsLoString("little ",
                new ConsLoString("little ",
                    new ConsLoString("mary ", new ConsLoString("mary ", new MtLoString())))))))))))
        && t.checkExpect(this.sortedMary.merge(this.shortList),
            new ConsLoString("a ", new ConsLoString("had ",
                new ConsLoString("hello", new ConsLoString("lamb.", new ConsLoString("little ",
                    new ConsLoString("mary ", new ConsLoString("world", new MtLoString()))))))));
  }

  // test the reverse method
  boolean testReverse(Tester t) {
    return t.checkExpect(this.mary.reverse(),
        new ConsLoString("lamb.", new ConsLoString("little ", new ConsLoString("a ",
            new ConsLoString("had ", new ConsLoString("Mary ", new MtLoString()))))))
        && t.checkExpect(this.unorderedList.reverse(), this.unorderedList);
  }

  // test the reversedList method
  boolean testReversedList(Tester t) {
    return t.checkExpect(this.mary.reversedList(new MtLoString()),
        new ConsLoString("lamb.", new ConsLoString("little ", new ConsLoString("a ",
            new ConsLoString("had ", new ConsLoString("Mary ", new MtLoString()))))))
        && t.checkExpect(this.unorderedList.reversedList(new MtLoString()),
            this.unorderedList);
  }

  ILoString doubledList = new ConsLoString("a ",
      new ConsLoString("a ",
          new ConsLoString("had ", new ConsLoString("had ", new ConsLoString("lamb.",
              new ConsLoString("lamb.", new ConsLoString("little ", new ConsLoString("little ",
                  new ConsLoString("mary ", new ConsLoString("mary ", new MtLoString()))))))))));

  ILoString nonDoubleList = new ConsLoString("a ",
      new ConsLoString("a ",
          new ConsLoString("had ", new ConsLoString("had ",
              new ConsLoString("lamb.", new ConsLoString("lamb.", new ConsLoString("little ",
                  new ConsLoString("little ", new ConsLoString("mary ", new MtLoString())))))))));

  // test the isDoubledList method
  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(this.mary.isDoubledList(), false)
        && t.checkExpect(this.nonDoubleList.isDoubledList(), false)
        && t.checkExpect(this.doubledList.isDoubledList(), true);
  }

  ILoString nonDoubleListTwo = new ConsLoString("a ",
      new ConsLoString("had ",
          new ConsLoString("had ",
              new ConsLoString("lamb.",
                  new ConsLoString("lamb.", new ConsLoString("little ", new ConsLoString("little ",
                      new ConsLoString("mary ", new ConsLoString("mary ", new MtLoString())))))))));

  // test the stringEquals method
  boolean testStringEquals(Tester t) {
    return t.checkExpect(this.nonDoubleListTwo.stringEquals("a "), true)
        && t.checkExpect(this.sortedMary.stringEquals("a "), false)
        && t.checkExpect(this.shortList.stringEquals("World"), false);
  }

  // test the isPalindromeList method
  boolean testIsPalindromeList(Tester t) {
    return t.checkExpect(this.mary.isPalindromeList(), false)
        && t.checkExpect(new MtLoString().isPalindromeList(), true)
        && t.checkExpect(this.unorderedList.isPalindromeList(), true);
  }
}