import java.util.function.Predicate;
import tester.Tester;

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

//deque class used for mazegame
class Deque1<T> {
  Sentinel<T> header;

  // basic constructor
  Deque1() {
    this.header = new Sentinel<T>();
  }

  // constructor for connecting deque to sentinel
  Deque1(Sentinel<T> header) {
    this.header = header;
  }

  // returns the size of the deque
  public int size() {
    return this.sizeHelp(this.header, this.header);
  }

  // helper function for the size method
  public int sizeHelp(ANode<T> startNode, ANode<T> currNode) {
    if (currNode.next == startNode) {
      return 0;
    }
    else {
      return 1 + sizeHelp(startNode, currNode.next);
    }
  }

  // adds an item to the head of a list
  public void addAtHead(T value) {
    ANode<T> newNode = new Node<T>(value, this.header.next, this.header);
    this.header.next.prev = newNode;
    newNode.prev = this.header;
  }

  // adds an item to the tail of a list
  public void addAtTail(T value) {
    ANode<T> newNode = new Node<T>(value, this.header, this.header.prev);
    this.header.prev = newNode;
    newNode.prev.next = newNode;
    this.header.next = this.header.next;
  }

  // removes an item to the head of a list
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

  // removes an item to the tail of a list
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

  // finds a predicate within a deque
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

  // return the data variable
  public T findData() {
    return this.data;
  }
}

class ExamplesDeque {
  Deque1<String> deque1;

  Sentinel<String> s1;
  Node<String> abc;
  Node<String> bcd;
  Node<String> cde;
  Node<String> def;
  Deque1<String> deque2;

  Sentinel<String> s2;
  Node<String> abd;
  Node<String> cdf;
  Node<String> bce;
  Node<String> deg;
  Deque1<String> deque3;

  Sentinel<String> s3;
  Node<String> abc2;
  Node<String> abd2;
  Node<String> cdf2;
  Node<String> bce2;
  Node<String> deg2;
  Deque1<String> deque4;

  Sentinel<String> s4;
  Node<String> abc3;
  Node<String> abd3;
  Node<String> cdf3;
  Node<String> bce3;
  Deque1<String> deque5;

  void initData() {
    deque1 = new Deque1<String>();

    s1 = new Sentinel<String>();
    abc = new Node<String>("abc", this.s1, this.s1);
    bcd = new Node<String>("bcd", this.s1, this.abc);
    cde = new Node<String>("cde", this.s1, this.bcd);
    def = new Node<String>("def", this.s1, this.cde);
    deque2 = new Deque1<String>(this.s1);

    s2 = new Sentinel<String>();
    abd = new Node<String>("abd", this.s2, this.s2);
    cdf = new Node<String>("cdf", this.s2, this.abd);
    bce = new Node<String>("bce", this.s2, this.cdf);
    deg = new Node<String>("deg", this.s2, this.bce);
    deque3 = new Deque1<String>(this.s2);

    s3 = new Sentinel<String>();
    abc2 = new Node<String>("abc", this.s3, this.s3);
    abd2 = new Node<String>("abd", this.s3, this.abc2);
    cdf2 = new Node<String>("cdf", this.s3, this.abd2);
    bce2 = new Node<String>("bce", this.s3, this.cdf2);
    deg2 = new Node<String>("deg", this.s3, this.bce2);
    deque4 = new Deque1<String>(this.s3);

    s4 = new Sentinel<String>();
    abc3 = new Node<String>("abc", this.s4, this.s4);
    abd3 = new Node<String>("abd", this.s4, this.abc3);
    cdf3 = new Node<String>("cdf", this.s4, this.abd3);
    bce3 = new Node<String>("bce", this.s4, this.cdf3);
    deque5 = new Deque1<String>(this.s4);
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
    Deque1<String> deque6 = new Deque1<String>(s5);
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
    Deque1<String> deque6 = new Deque1<String>(s5);
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