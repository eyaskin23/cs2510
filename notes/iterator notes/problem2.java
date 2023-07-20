
///////// ======= Fundies Exam 2 Notes ======= /////////////


// big box :0 

import java.util.ArrayList;
import java.util.Arrays;
import tester.Tester;
import java.util.function.Predicate;
import java.util.Iterator;

class Utils {
	boolean containsSequence(ArrayList<Integer> source, 
			ArrayList<Integer> sequence) {
		for (int h = 0; h <= source.size() - sequence.size(); h++) {
			boolean found = true;
			
			// 1 2 3 4 5 6 7 8 9
			//           6 7 8 
			//           ^ ^ ^
			//         n 0 1 2
			//         h = 5, where 6 is in source
			
			for (int n = 0; n < sequence.size(); n += 1) {
				if (!source.get(h + n).equals(sequence.get(n))) 
					found = false; // this never happens when h = 5
			}
			
			if (found)
				return true; 
	   }
	return false; // prevents an infinite loop 
  } 
}

class ExamplesList {
	ArrayList<Integer> fullSource = new ArrayList<> (
			Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
	ArrayList<Integer> smallSource = new ArrayList<>(Arrays.asList(1, 2));
	ArrayList<Integer> emptySource = new ArrayList<>(Arrays.asList());
	
	ArrayList<Integer> correctSequence = new ArrayList<>(Arrays.asList(1, 2));
	ArrayList<Integer> sourceLengthSequence = new ArrayList<>(Arrays.asList(
			1, 2, 3, 4, 5, 6, 7, 8, 9));
	ArrayList<Integer> smallCorrectSequence = new ArrayList<>(Arrays.asList(1, 2));
	ArrayList<Integer> endCorrectSequence = new ArrayList<>(Arrays.asList(6, 7, 8));
	ArrayList<Integer> incorrectSequence = new ArrayList<>(Arrays.asList(1, 5, 3));
	ArrayList<Integer> emptySequence = new ArrayList<>(Arrays.asList(1, 2));

   void testContainsSequence(Tester t) {
	   Utils util = new Utils();
	   
	   t.checkExpect(util.containsSequence(emptySource, correctSequence), false);
	   t.checkExpect(util.containsSequence(smallSource, correctSequence), true);
	   t.checkExpect(util.containsSequence(emptySource, correctSequence), false);
	   t.checkExpect(util.containsSequence(correctSequence, smallCorrectSequence), true);

   }
}

interface IList<T> {
	void append(IList<T> that);
	
	IList<T> appendHelp(IList<T> that);
	
	int length();
	
}
 

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;
	
	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
		
		
	}
	
	public void append(IList<T> that) {
		this.rest = this.rest.appendHelp(that);
	}
	
	public IList<T> appendHelp(IList<T> that) {
		this.rest = this.rest.appendHelp(that);
		return this;
	}
	
	public int length() {
		return 1 + this.rest.length();
	}
}

class MtList<T> implements IList<T> {
	public void append(IList<T> that) {
		return;
	}
	
	public IList<T> appendHelp(IList<T> that) {
		return that;
	}
	
	public int length() {
		return 0;
	}
}

class Examples {
	IList<Integer> mt = new MtList<Integer>();
	IList<Integer> ints1 = new ConsList<Integer>(1,
			mt);
	IList<Integer> ints2 = new ConsList<Integer>(2,
			new ConsList<Integer>(3, mt));
	IList<Integer> ints3 = new ConsList<Integer>(4,
			new ConsList<Integer>(5, mt));
	IList<Integer> ints4 = new ConsList<Integer>(6,
			new ConsList<Integer>(7, mt));
	
	void test(Tester t) {
		
		ints1.append(ints2);
		t.checkExpect(ints1.length(), 3);
		ints2.append(ints3);
		t.checkExpect(ints1.length(), 5);
		ints2.append(ints4);
		t.checkExpect(ints2.length(), 6);
		t.checkExpect(ints3.length(), 4);
		ints4.append(ints4);
		// this test will never work  
		// t.checkExpect(ints4.length(), null);	
  }
}



class Equals<T> implements Predicate<T> {
  T data;

  Equals(T data) {
    this.data = data;
  }

  public boolean test(T t) {
    if (t == null) {
      throw new IllegalArgumentException("cannot set argument to null");
    }
    else {
      return t.equals(this.data);
    }
  }
}

class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  public int size() {
    return this.sizeHelp(this.header, this.header);
  }
  
  void append(Deque<T> other) {
	  // if aliasing, get the elements to an array list, and
	  // then put it at the end of this
	  // if this
	  
	  if (this == other) {
		  ArrayList<T> toReAdd = new ArrayList<T>();
	  
	  
	  while (other.size() > 0) {
		  toReAdd.add(this.removeFromHead());
	    }
	  
	
	  
	  for (T elementToAdd : toReAdd) {
		  this.addAtTail(elementToAdd);
	  }
	  
	  for (T elementToAdd : toReAdd) {
		  this.addAtTail(elementToAdd);
	  }
	 }
  
  while (other.size() > 0) {
	  T nextValue = other.removeFromHead();
	  this.addAtTail(nextValue);
    }
  }

  public int sizeHelp(ANode<T> startNode, ANode<T> currNode) {
    if (currNode.next == startNode) {
      return 0;
    }
    else {
      return 1 + sizeHelp(startNode, currNode.next);
    }
  }

  public void addAtHead(T value) {
    ANode<T> newNode = new Node<T>(value, this.header.next, this.header);
    this.header.next.prev = newNode;
    newNode.prev = this.header;
  }

  public void addAtTail(T value) {
    ANode<T> newNode = new Node<T>(value, this.header, this.header.prev);
    this.header.prev = newNode;
    newNode.prev.next = newNode;
    this.header.next = this.header.next;
  }

  public T removeFromHead() {
    if (this.header == this.header.next) {
      throw new RuntimeException("cannot remove node from empty deque");
    }
    else {
      ANode<T> deleted = this.header.next;
      this.header.next = this.header.next.next;
      this.header.next.prev = this.header;
      return deleted.findData();
    }
  }

  public T removeFromTail() {
    if (this.header == this.header.next) {
      throw new RuntimeException("cannot remove node from empty deque");
    }
    else {
      ANode<T> deleted = this.header.prev;
      this.header.prev = this.header.prev.prev;
      this.header.prev.next = this.header;
      return deleted.findData();
    }
  }

  public ANode<T> find(Predicate<T> p) {
    ANode<T> curr = this.header.next;
    while (curr != this.header) {
      if (p.test(curr.findData())) {
        return curr;
      }
      curr = curr.next;
    }
    return this.header;
  }
}

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }

  // updates this ANode
  void updateNode(ANode<T> that) {
    if (that.next == null) {
      throw new IllegalArgumentException("next is null");
    }
    else if (that.prev == null) {
      throw new IllegalArgumentException("prev is null");
    }
    else {
      this.next = that.next;
      this.prev = that.prev;
    }
  }

  // find the data variable in this ANode
  public abstract T findData();
}

class Sentinel<T> extends ANode<T> {

  Sentinel() {
    super(null, null);
    this.prev = this;
    this.next = this;

  }

  // returns null for the data variable, since it is a Sentinel
  public T findData() {
    return null;
  }
}

class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    super(null, null);
    this.next.prev = this;
    this.prev.next = this;
    this.data = data;
  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    super(next, prev);
    this.updateNode(this);
    this.next.prev = this;
    this.prev.next = this;
    this.data = data;
  }

  // returns the data variable
  public T findData() {
    return this.data;
  }
}

// if (this == that)



class ExamplesDeque {
  Deque<String> deque1;

  Sentinel<String> s1;
  Node<String> abc;
  Node<String> bcd;
  Node<String> cde;
  Node<String> def;
  Deque<String> deque2;

  Sentinel<String> s2;
  Node<String> abd;
  Node<String> cdf;
  Node<String> bce;
  Node<String> deg;
  Deque<String> deque3;

  Sentinel<String> s3;
  Node<String> abc2;
  Node<String> abd2;
  Node<String> cdf2;
  Node<String> bce2;
  Node<String> deg2;
  Deque<String> deque4;

  Sentinel<String> s4;
  Node<String> abc3;
  Node<String> abd3;
  Node<String> cdf3;
  Node<String> bce3;
  Deque<String> deque5;

  void initData() {
    deque1 = new Deque<String>();

    s1 = new Sentinel<String>();
    abc = new Node<String>("abc", this.s1, this.s1);
    bcd = new Node<String>("bcd", this.s1, this.abc);
    cde = new Node<String>("cde", this.s1, this.bcd);
    def = new Node<String>("def", this.s1, this.cde);
    deque2 = new Deque<String>(this.s1);

    s2 = new Sentinel<String>();
    abd = new Node<String>("abd", this.s2, this.s2);
    cdf = new Node<String>("cdf", this.s2, this.abd);
    bce = new Node<String>("bce", this.s2, this.cdf);
    deg = new Node<String>("deg", this.s2, this.bce);
    deque3 = new Deque<String>(this.s2);

    s3 = new Sentinel<String>();
    abc2 = new Node<String>("abc", this.s3, this.s3);
    abd2 = new Node<String>("abd", this.s3, this.abc2);
    cdf2 = new Node<String>("cdf", this.s3, this.abd2);
    bce2 = new Node<String>("bce", this.s3, this.cdf2);
    deg2 = new Node<String>("deg", this.s3, this.bce2);
    deque4 = new Deque<String>(this.s3);

    s4 = new Sentinel<String>();
    abc3 = new Node<String>("abc", this.s4, this.s4);
    abd3 = new Node<String>("abd", this.s4, this.abc3);
    cdf3 = new Node<String>("cdf", this.s4, this.abd3);
    bce3 = new Node<String>("bce", this.s4, this.cdf3);
    deque5 = new Deque<String>(this.s4);
  }

  // test the size method
  void testSize(Tester t) {
    initData();

    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque3.size(), 4);
  }

  // test the sizeHelp method
  void testSizeHelp(Tester t) {
    initData();

    t.checkExpect(this.deque2.sizeHelp(this.s1, this.s1), 4);
    t.checkExpect(this.deque2.sizeHelp(this.s1, this.bcd), 2);
    t.checkExpect(this.deque3.sizeHelp(this.s2, this.s2), 4);

    Sentinel<String> s5 = new Sentinel<String>();
    t.checkExpect(this.deque1.sizeHelp(s5, s5), 0);
  }

  // test the addAtHead method
  void testAddAtHead(Tester t) {
    initData();

    this.deque3.addAtHead("abc");
    t.checkExpect(this.deque3, this.deque4);

    Sentinel<String> s5 = new Sentinel<String>();
    abc = new Node<String>("abc", s5, s5);
    Deque<String> deque6 = new Deque<String>(s5);
    this.deque1.addAtHead("abc");
    t.checkExpect(this.deque1, deque6);
  }

  // test the addAtTail method
  void testAddAtTail(Tester t) {
    initData();

    this.deque5.addAtTail("deg");
    t.checkExpect(this.deque5, this.deque4);

    Sentinel<String> s5 = new Sentinel<String>();
    abc = new Node<String>("deg", s5, s5);
    Deque<String> deque6 = new Deque<String>(s5);
    this.deque1.addAtTail("deg");
    t.checkExpect(this.deque1, deque6);
  }

  // test the removeFromHead method
  void testRemoveFromHead(Tester t) {
    initData();

    t.checkExpect(this.deque4.removeFromHead(), "abc");
    t.checkException(new RuntimeException("cannot remove node from empty deque"), this.deque1,
        "removeFromHead");
  }

  // test the removeFromTail method
  void testRemoveFromTail(Tester t) {
    initData();

    t.checkExpect(this.deque4.removeFromTail(), "deg");
    t.checkException(new RuntimeException("cannot remove node from empty deque"), this.deque1,
        "removeFromTail");
  }

  // test the findData method
  void testFindData(Tester t) {
    initData();

    t.checkExpect(this.abc.findData(), "abc");
    t.checkExpect(this.s1.findData(), null);
  }

  // test the find method
  void testFind(Tester t) {
    initData();

    t.checkExpect(this.deque1.find(new Equals<String>("abc")), new Sentinel<String>());
    t.checkExpect(this.deque2.find(new Equals<String>("abc")), this.abc);
    t.checkExpect(this.deque2.find(new Equals<String>("abd")), this.s1);
  }

  // test the test method in Equals class
  void testTest(Tester t) {
    initData();

    t.checkExpect(new Equals<String>("abc").test("abc"), true);
    t.checkExpect(new Equals<String>("abc").test("abd"), false);
    t.checkException(new IllegalArgumentException("cannot set argument to null"),
        new Equals<String>("abc"), "test", (String) null);
  }
}

class EveryOther<T> implements Iterator<T> {

	Iterator<T> iter;
	
	EveryOther(Iterator<T> iter) {
		this.iter = iter;
	}
	
	// iterable vs iterator
	
	// transverable vs traveler 
	
	/* Iterator goes through the Iterable, just like a traveler goes through 
	 * a traversable
	 * 
	 */
	
	//checks whether this arrayList has a next value
	public boolean hasNext() {
		return this.iter.hasNext() && iter.hasNext();
	}
    //returns the next value (whatever it's assigned)
	public T next() {
		
		if (!this.hasNext()) {
			throw new IllegalStateException();
		}
		
		T result = this.iter.next();
		
		// 1 2 3 4 5 
		// result = 1
		// this.iter.next() = 2
		// result = 3
		// this.iter.next() = 4 
		// result = 5
		
		// what if there is no other for every other 
		
		if (this.iter.hasNext()) {
			this.iter.next();
		}
		return result;
	}
    // removes a value based on the given index
	public void remove() {
		this.iter.remove();
	}
}

class ExampleIterator {

	
		
	void testIterator(Tester t) {
		
		Iterator<Integer> arrayList = new ArrayList<Integer>(
				Arrays.asList(1, 2, 3, 4, 5)).iterator();
		
		t.checkExpect(arrayList.hasNext(), true);
		t.checkExpect(arrayList.next(), 1);
		t.checkExpect(arrayList.hasNext(), true);
		t.checkExpect(arrayList.next(), 2);
		t.checkExpect(arrayList.hasNext(), true);
		t.checkExpect(arrayList.next(), 3);
		t.checkExpect(arrayList.hasNext(), true);
		t.checkExpect(arrayList.next(), 4);
		t.checkExpect(arrayList.hasNext(), true);
		t.checkExpect(arrayList.next(), 5);
		t.checkExpect(arrayList.hasNext(), false);
	}
	
	
	void testEveryOther(Tester t) {
		Iterator<Integer> arrayList = new ArrayList<Integer>(
				Arrays.asList(1, 2, 3, 4, 5)).iterator();
		EveryOther<Integer> e = new EveryOther<Integer>(arrayList);
	
		t.checkExpect(e.hasNext(), true);
		t.checkExpect(e.next(), 1);
		t.checkExpect(e.hasNext(), true);
		t.checkExpect(e.next(), 3);
		t.checkExpect(e.hasNext(), true);
		t.checkExpect(e.next(), 5);
		t.checkExpect(e.hasNext(), false);
		
	}
} 





























