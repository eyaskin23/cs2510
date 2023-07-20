import java.util.ArrayList;
import java.util.Iterator;
import tester.Tester;

//represents arraylist iterators
class ArrayListIterator<T> implements Iterator<T> {

  ArrayList<T> items;
  int next;

  // constructor for items
  ArrayListIterator(ArrayList<T> items) {
    this.items = items;
    this.next = 0; 
  }

  // convenience constructor with items and the next index
  ArrayListIterator(ArrayList<T> items, int next) {
    this.items = items;
    this.next = next;
  }

  // determines if there is a next data in the list
  public boolean hasNext() {
    return this.next < this.items.size();
  }

  // returns the next value in a list
  public T next() {
    T answer = this.items.get(this.next);
    this.next = this.next + 1;
    return answer;
  }
  
  // finds the next arrayList
  public ArrayList<T> findNextList() {
    T end = this.items.get(0);
    this.items.subList(1, this.items.size()).add(end);
    return (ArrayList<T>) this.items;
  }
}

// represents the vigenere class
class Vigenere {
  ArrayList<Character> alphabet;
  ArrayList<ArrayList<Character>> table;
  
  //constructor using the alphabet
  Vigenere() {
    this.alphabet = new ArrayList<Character>(Arrays.asList('a', 'b', 'c'));
    this.table = this.initVigenere();
    
  }

  // creates an initvigenere method, used for printing the vigenere
  ArrayList<ArrayList<Character>> initVigenere() {
    ArrayList<ArrayList<Character>> arrayList2 = new ArrayList<ArrayList<Character>>();
    int length = this.alphabet.size();

    for (int i = 0; i < length; i++) {
      ArrayList<Character> arrayList1 = new ArrayList<Character>();

      for (int j = 0; j < length; j++) {
        arrayList1.add(alphabet.get(j));

      }
      arrayList2.add(arrayList1);
      Character first = alphabet.get(0);
      alphabet.remove(0);
      alphabet.add(first);
    }
    return arrayList2;
  }

  // produces a decoded String from the given String
  String decode(String message, String keyword) {
    String result = "";
    int count = 0;
    int initialLength = keyword.length();
    
    while (keyword.length() < message.length()) {
      int index = count % initialLength;
      keyword = keyword + keyword.substring(index, index + 1);
      count++;
    }
    
    for (int i = 0; i < this.alphabet.size(); i++) {
      char char1 = keyword.charAt(i);
      int index1 = alphabet.indexOf(char1);
      char char2 = message.charAt(i);
      int index2 = alphabet.indexOf(char2);
      result += table.get(index1).get(index2);
    }
    return result;
  }

  // produces an encoded String from the given String
  String encode(String string, String code) {
    String result = "";
    int count = 0;
    int initial = code.length();
    
    while (code.length() < string.length()) {
      int index = count % initial;
      code = code + code.substring(index, index + 1);
      count++;
    }

    for (int i = 0; i < string.length(); i++) {
      char char1 = code.charAt(i);
      int index1 = alphabet.indexOf(char1);
      char char2 = string.charAt(i);
      int index2 = table.get(index1).indexOf(char2);
      result += alphabet.get(index2);
    }
    return result;
  }
}

class ExamplesVigenere {
  ArrayList<Character> arrayList1;
  ArrayListIterator<Character> iter;
  Vigenere alphabet;
  ArrayList<ArrayList<Character>> table;

  ArrayList<Character> arrayList2;
  ArrayList<Character> arrayList3;

  void initData() {
    arrayList1 = new ArrayList<Character>();
    arrayList1.add('a');
    arrayList1.add('b');
    arrayList1.add('c');
    alphabet = new Vigenere();

    arrayList2 = new ArrayList<Character>();
    arrayList2.add('b');
    arrayList2.add('c');
    arrayList2.add('a');

    arrayList3 = new ArrayList<Character>();
    arrayList3.add('c');
    arrayList3.add('a');
    arrayList3.add('b');

    table = new ArrayList<ArrayList<Character>>();
    table.add(arrayList3);
    table.add(arrayList3);
    table.add(arrayList3);
  }

  void testInitVigenere(Tester t) {
    initData();
    t.checkExpect(this.alphabet.table, this.table);
  }
  
  void testDecode(Tester t) {
    initData();
    t.checkExpect(this.alphabet.decode("abc", "a"), "abc");
    t.checkExpect(this.alphabet.decode("cab", "cab"), "bac");
  }
  
  void testEncode(Tester t) {
    initData();
    t.checkExpect(this.alphabet.encode("abc", "abc"), "aaa");
    t.checkExpect(this.alphabet.encode("cab", "c"), "abc");
  }
}