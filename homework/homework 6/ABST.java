import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.Function;
import tester.Tester;

// to represent a binary search tree
abstract class ABST<T> {
  Comparator<T> order;

  // constructor for ABST class
  ABST(Comparator<T> order) {
    this.order = order;
  }
  
  /* TEMPLATE
   * FIELDS:
   * ... this.order ...                          -- Comparator<T>
   * 
   * METHODS:
   * ... this.insert(T) ...                      -- boolean
   * ... this.sameABST(ABST<T>) ...              -- boolean
   * ... this.sameLeaf(Leaf<T>) ...              -- boolean
   * ... this.sameNode(Node<T>) ...              -- boolean
   * ... this.present(T) ...                     -- boolean
   * ... this.getLeftmost() ...                  -- T
   * ... this.findLeft(T) ...                    -- T
   * ... this.getRight() ...                     -- ABST<T>
   * ... this.sameTree(ABST<T>) ...              -- boolean
   * ... this.compareStructure(T) ...            -- boolean
   * ... this.sameData(ABST<T>) ...              -- boolean
   * ... this.buildList() ...                    -- IList<T>
   * 
   * METHODS ON FIELDS:
   * 
   */

  // inserts a given object of class T into this binary tree
  public abstract ABST<T> insert(T t);
  
  // compare this ABST to the given ABST
  public abstract boolean sameABST(ABST<T> other);
  
  // is this ABST the same as the given leaf?
  public abstract boolean sameLeaf(Leaf<T> other);
  
  // is this ABST the same as the given node?
  public abstract boolean sameNode(Node<T> other);

  // is the given object of class T present in this binary tree?
  public abstract boolean present(T t);

  // get the leftmost object in this binary tree
  public abstract T getLeftmost();

  // find the leftmost object in this binary tree
  public abstract T findLeft(T other);

  // get all objects excluding the rightmost in this binary tree
  public abstract ABST<T> getRight();

  // is this tree and the given tree the same?
  public abstract boolean sameTree(ABST<T> other);
  
  // compares the structure between two data
  public abstract boolean compareStructure(T other);

  // is the data in this tree and the given tree the same?
  public abstract boolean sameData(ABST<T> other);

  // builds a list of ancestry tree 
  public abstract IList<T> buildList();
}

// to represent the leaf class
class Leaf<T> extends ABST<T> {
  Leaf(Comparator<T> order) {
    super(order);
  }
  
  /* TEMPLATE
   * FIELDS:
   * ... this.order ...                          -- Comparator<T>
   * 
   * METHODS:
   * ... this.insert(T) ...                      -- boolean
   * ... this.sameABST(ABST<T>) ...              -- boolean
   * ... this.sameLeaf(Leaf<T>) ...              -- boolean
   * ... this.sameNode(Node<T>) ...              -- boolean
   * ... this.present(T) ...                     -- boolean
   * ... this.getLeftmost() ...                  -- T
   * ... this.findLeft(T) ...                    -- T
   * ... this.getRight() ...                     -- ABST<T>
   * ... this.sameTree(ABST<T>) ...              -- boolean
   * ... this.compareStructure(T) ...            -- boolean
   * ... this.sameData(ABST<T>) ...              -- boolean
   * ... this.buildList() ...                    -- IList<T>
   * 
   * METHODS ON FIELDS:
   * 
   */

  // inserts a certain node or leaf into the tree
  public ABST<T> insert(T t) {
    return new Node<T>(order, t, new Leaf<T>(order), new Leaf<T>(order));
  }

  // is this abst the same as the given abst?
  public boolean sameABST(ABST<T> other) {
    return other.sameLeaf(this);
  }
  
  /* Eerything in the Leaf template, plus
   * FIELDS:
   * 
   * METHODS ON PARAMTERS:
   * ... other.sameLeaf(Leaf<T>) ...            -- boolean
   */

  // is this leaf the same as the given leaf?
  public boolean sameLeaf(Leaf<T> other) {
    return true;
  }

  // is this node the same as the given node?
  public boolean sameNode(Node<T> other) {
    return false;
  }

  // there is no present data in a leaf
  public boolean present(T t) {
    return false;
  }

  // returns an exception because there is no left item in a empty tree
  public T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // helper function for getLeft()
  public T findLeft(T other) {
    return other;
  }

  // returns an exception because there is no right item in a empty tree
  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  // is this abst the same as the given abst?
  public boolean sameTree(ABST<T> other) {
    return other.sameABST(this);
  }
  
  /* Eerything in the Leaf template, plus
   * FIELDS:
   * 
   * METHODS ON PARAMTERS:
   * ... other.sameABST(Leaf<T>) ...            -- boolean
   */

  // is this structure the same as the given T?
  public boolean compareStructure(T other) {
    return false;
  }

  // is this abst's data the same as that of the given abst?
  public boolean sameData(ABST<T> other) {
    return other.sameABST(this);
  }
  
  /* Eerything in the Leaf template, plus
   * FIELDS:
   * 
   * METHODS ON PARAMTERS:
   * ... other.sameABST(Leaf<T>) ...            -- boolean
   */

  // builds an empty list of data
  public IList<T> buildList() {
    return new MtList<T>();

  }
}

// to represent the node class
class Node<T> extends ABST<T> {
  T data;

  ABST<T> left;
  ABST<T> right;

  // node class constructor
  public Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }
  
  /* TEMPLATE
   * FIELDS:
   * ... this.order ...                          -- Comparator<T>
   * 
   * METHODS:
   * ... this.insert(T) ...                      -- boolean
   * ... this.sameABST(ABST<T>) ...              -- boolean
   * ... this.sameLeaf(Leaf<T>) ...              -- boolean
   * ... this.sameNode(Node<T>) ...              -- boolean
   * ... this.present(T) ...                     -- boolean
   * ... this.getLeftmost() ...                  -- T
   * ... this.findLeft(T) ...                    -- T
   * ... this.getRight() ...                     -- ABST<T>
   * ... this.sameTree(ABST<T>) ...              -- boolean
   * ... this.compareStructure(T) ...            -- boolean
   * ... this.sameData(ABST<T>) ...              -- boolean
   * ... this.buildList() ...                    -- IList<T>
   * 
   * METHODS ON FIELDS:
   * ... this.order.compare(T, T) ...            -- boolean
   * ... this.left.present(T) ...                -- boolean
   * ... this.right.present(T) ...               -- boolean
   * ... this.left.findLeft(T) ...               -- T
   * ... this.left.getRight() ...                -- ABST<T>
   * ... this.left.insert(T) ...                 -- ABST<T>
   * ... this.right.insert(T) ...                -- ABST<T>
   */

  // is this abst the same as the given abst?
  public boolean sameABST(ABST<T> other) {
    return other.sameNode(this);
  }

  // is this leaf the same as the given leaf?
  public boolean sameLeaf(Leaf<T> other) {
    return false;
  }

  // is this node the same as the given node?
  public boolean sameNode(Node<T> other) {
    return this.order.compare(this.data, other.data) == 0;
  }

  // sees if data is present in a node
  public boolean present(T t) {
    if (this.order.compare(t, this.data) < 0) {
      return this.left.present(t);
    }
    else if (this.order.compare(t, data) > 0) {
      return this.right.present(t);
    }
    else {
      return true;
    }
  }

  // returns the left side of data in this node
  public T getLeftmost() {
    return this.left.findLeft(this.data);
  }

  // helper function for getLeft()
  public T findLeft(T other) {
    return this.left.findLeft(this.data);
  }

  // gets the right side of data in the node
  public ABST<T> getRight() {
    if (this.order.compare(this.data, this.getLeftmost()) == 0) {
      return this.right;
    }
    else {
      return new Node<T>(this.order, this.data, this.left.getRight(), this.right);
    }
  }

  // inserts data into the node
  public ABST<T> insert(T t) {
    if (order.compare(t, this.data) < 0) {
      return new Node<T>(this.order, this.data, this.left.insert(t), this.right);
    }
    else {
      return new Node<T>(this.order, this.data, left, this.right.insert(t));
    }
  }

  public boolean sameTree(ABST<T> other) {
    return this.sameData(other) && other.compareStructure(this.data);
  }
  
  /* Eerything in the Leaf template, plus
   * FIELDS:
   * 
   * METHODS ON PARAMTERS:
   * ... other.compareStructure(T) ...            -- boolean
   */

  public boolean compareStructure(T other) {
    return this.order.compare(this.data, other) == 0;
  }

  public boolean sameData(ABST<T> other) {
    return this.order.compare(other.getLeftmost(), this.getLeftmost()) == 0
        && this.getRight().sameData(other.getRight());
  }
  
  /* Eerything in the Node template, plus
   * FIELDS:
   * 
   * METHODS ON PARAMTERS:
   * ... other.getLeftmost() ...            -- ABST<T>
   * ... other.getRight() ...               -- ABST<T>
   */

  // returns a list with the data inside of a node
  public IList<T> buildList() {
    return new ConsList<T>(this.getLeftmost(), this.getRight().buildList());
  }
}

// represents the properties of a book
class Book {
  String title;
  String author;
  int price;

  // constructor for Book
  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

  // get the title of a book
  public String getTitle() {
    return this.title;
  }

  // get the author of a book
  public String getAuthor() {
    return this.author;
  }

  // get the price of a book
  public int getPrice() {
    return this.price;
  }
}

// compare books by their title
class BooksByTitle implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.getTitle().compareTo(b2.getTitle());
  }
}

// compare books by their author
class BooksByAuthor implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.getAuthor().compareTo(b2.getAuthor());
  }
}

// compare books by their price
class BooksByPrice implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.getPrice() - b2.getPrice();
  }
}

//represents a generic list
interface IList<T> {
  // filter this list by the given predicate
  IList<T> filter(Predicate<T> pred);

  // map a function onto every member of this list, producing a list of the
  // results
  <U> IList<U> map(Function<T, U> fun);

  // combine the items in this list using the given function from right to left
  <U> U foldr(BiFunction<T, U, U> fun, U base);

  // combine the items in this list using the given function and base case from
  // left to right
  <U> U foldl(BiFunction<T, U, U> fun, U base);

}

//represents an empty generic list
class MtList<T> implements IList<T> {
  // filter this list by the given predicate
  public IList<T> filter(Predicate<T> pred) {
    return this;
  }

  // map a function onto every member of this list, producing a list of the
  // results
  public <U> IList<U> map(Function<T, U> fun) {
    return new MtList<U>();
  }

  // combine the items in this list using the given function
  public <U> U foldr(BiFunction<T, U, U> fun, U base) {
    return base;
  }

  // combine the items in this list using the given function and base case from
  // left to right
  public <U> U foldl(BiFunction<T, U, U> fun, U base) {
    return base;
  }
}

//represents a non-empty generic list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // filter this list by the given predicate
  public IList<T> filter(Predicate<T> pred) {
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(null));
    }
    else {
      return this.rest.filter(null);
    }
  }

  // map a function onto every member of this list, producing a list of the
  // results
  public <U> IList<U> map(Function<T, U> fun) {
    return new ConsList<U>(fun.apply(this.first), this.rest.map(fun));
  }

  // combine the items in this list using the given function
  public <U> U foldr(BiFunction<T, U, U> fun, U base) {
    return fun.apply(this.first, this.rest.foldr(fun, base));
  }

  // combine the items in this list using the given function and base case from
  // left to right
  public <U> U foldl(BiFunction<T, U, U> fun, U base) {
    return this.rest.foldl(fun, fun.apply(this.first, base));
  }
}

// examples class
class ExamplesBooks {

  ABST<Book> bstA = new Node<>(new BooksByTitle(), new Book("b3", "author", 10),
      new Node<>(new BooksByTitle(), new Book("b2", "author", 10),
          new Node<>(new BooksByTitle(), new Book("b1", "author", 10),
              new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())),
          new Leaf<>(new BooksByTitle())),
      new Node<>(new BooksByTitle(), new Book("b4", "author", 10), new Leaf<>(new BooksByTitle()),
          new Leaf<>(new BooksByTitle())));

  ABST<Book> bstB = new Node<>(new BooksByTitle(), new Book("b3", "author", 10),
      new Node<>(new BooksByTitle(), new Book("b2", "author", 10),
          new Node<>(new BooksByTitle(), new Book("b1", "author", 10),
              new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())),
          new Leaf<>(new BooksByTitle())),
      new Node<>(new BooksByTitle(), new Book("b4", "author", 10), new Leaf<>(new BooksByTitle()),
          new Leaf<>(new BooksByTitle())));

  ABST<Book> bstC = new Node<>(new BooksByTitle(), new Book("b2", "author", 10),
      new Node<>(new BooksByTitle(), new Book("b1", "author", 10), new Leaf<>(new BooksByTitle()),
          new Leaf<>(new BooksByTitle())),
      new Node<>(new BooksByTitle(), new Book("b4", "author", 10),
          new Node<>(new BooksByTitle(), new Book("b3", "author", 10),
              new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())),
          new Leaf<>(new BooksByTitle())));

  ABST<Book> bstD = new Node<>(new BooksByTitle(), new Book("b3", "author", 10),
      new Node<>(new BooksByTitle(), new Book("b1", "author", 10), new Leaf<>(new BooksByTitle()),
          new Leaf<>(new BooksByTitle())),
      new Node<>(new BooksByTitle(), new Book("b4", "author", 10), new Leaf<>(new BooksByTitle()),
          new Node<>(new BooksByTitle(), new Book("b5", "author", 10),
              new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle()))));

  ABST<Book> bstE = new Node<>(new BooksByTitle(), new Book("b3", "author", 10),
      new Node<>(new BooksByTitle(), new Book("b2", "author", 10),
          new Node<>(new BooksByTitle(), new Book("b1", "author", 10),
              new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())),
          new Leaf<>(new BooksByTitle())),
      new Node<>(new BooksByTitle(), new Book("b4", "author", 10), new Leaf<>(new BooksByTitle()),
          new Node<>(new BooksByTitle(), new Book("b5", "author", 10),
              new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle()))));

  ABST<Book> bstF = new Node<>(new BooksByTitle(), new Book("b3", "author", 10),
      new Node<>(new BooksByTitle(), new Book("b2", "author", 10), new Leaf<>(new BooksByTitle()),
          new Leaf<>(new BooksByTitle())),
      new Node<>(new BooksByTitle(), new Book("b4", "author", 10), new Leaf<>(new BooksByTitle()),
          new Node<>(new BooksByTitle(), new Book("b5", "author", 10),
              new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle()))));

  IList<Book> b4 = new ConsList<>(new Book("b4", "author", 10), new MtList<>());
  IList<Book> b3 = new ConsList<>(new Book("b3", "author", 10), this.b4);
  IList<Book> b2 = new ConsList<>(new Book("b2", "author", 10), this.b3);
  IList<Book> b1 = new ConsList<>(new Book("b1", "author", 10), this.b2);

  // test the compare method in the BooksByAuthor class
  boolean testBooksByTitle(Tester t) {
    return t.checkExpect(
        new BooksByTitle().compare(new Book("b4", "author", 10), new Book("b4", "author", 10)), 0)
        && t.checkExpect(
            new BooksByTitle().compare(new Book("b4", "author", 10), new Book("d4", "author", 10)),
            -2);
  }

  // test the compare method in the BooksByAuthor class
  boolean testBooksByAuthor(Tester t) {
    return t.checkExpect(
        new BooksByAuthor().compare(new Book("b4", "author", 10), new Book("b4", "author", 10)), 0)
        && t.checkExpect(
            new BooksByAuthor().compare(new Book("b4", "author", 10), new Book("b4", "lowry", 10)),
            -11);
  }

  // test the compare method in the BooksByAuthor class
  boolean testBooksByPrice(Tester t) {
    return t.checkExpect(
        new BooksByPrice().compare(new Book("b4", "author", 10), new Book("b4", "author", 10)), 0)
        && t.checkExpect(
            new BooksByPrice().compare(new Book("b4", "author", 8), new Book("b4", "lowry", 10)),
            -2);
  }

  // test the insert method
  boolean testInsert(Tester t) {
    return t.checkExpect(this.bstA.insert(new Book("b5", "author", 10)), this.bstE)
        && t.checkExpect(new Leaf<>(new BooksByTitle()).insert(new Book("b5", "author", 10)),
            new Node<>(new BooksByTitle(), new Book("b5", "author", 10),
                new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())));
  }

  // test the present method
  boolean testPresent(Tester t) {
    return t.checkExpect(this.bstA.present(new Book("b4", "author", 10)), true)
        && t.checkExpect(this.bstA.present(new Book("b5", "author", 10)), false) && t.checkExpect(
            new Leaf<>(new BooksByTitle()).present(new Book("b4", "author", 10)), false);
  }
  
  // test the sameABST method
  boolean testSameABST(Tester t) {
    return t.checkExpect(this.bstA.sameABST(this.bstB), true)
        && t.checkExpect(this.bstA.sameABST(this.bstC), false)
        && t.checkExpect(new Leaf<>(new BooksByTitle()).sameABST(new Leaf<>(new BooksByTitle())),
            true)
        && t.checkExpect(new Leaf<>(new BooksByTitle()).sameABST(this.bstA), false);
 }

  // test the sameLeaf method
  boolean testSameLeaf(Tester t) {
    return t.checkExpect(new Leaf<>(new BooksByTitle()).sameLeaf(new Leaf<>(new BooksByTitle())),
       true) && t.checkExpect(this.bstA.sameABST(this.bstC), false)
       && t.checkExpect(new Leaf<>(new BooksByTitle()).sameABST(this.bstA), false)
       && t.checkExpect(this.bstA.sameLeaf(new Leaf<>(new BooksByTitle())), false);
  }

  // test the sameNode method
  boolean testSameNode(Tester t) {
    return t
       .checkExpect(this.bstA.sameNode(new Node<>(new BooksByTitle(), new Book("b3", "author", 10),
           new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle()))), true)
       && t.checkExpect(
           this.bstA.sameNode(new Node<>(new BooksByTitle(), new Book("b2", "author", 10),
               new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle()))),
           false)
       && t.checkExpect(new Leaf<>(new BooksByTitle())
           .sameNode(new Node<>(new BooksByTitle(), new Book("b2", "author", 10),
               new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle()))),
           false);
 }

  // test the getLeftmost method
  boolean testGetLeftmost(Tester t) {
    return t.checkException(new RuntimeException("No leftmost item of an empty tree"),
        new Leaf<>(new BooksByTitle()), "getLeftmost")
        && t.checkExpect(this.bstA.getLeftmost(), new Book("b1", "author", 10));
  }

  // test the findLeft method
  boolean testFindLeft(Tester t) {
    return t.checkExpect(
        new Node<>(new BooksByTitle(), new Book("b2", "author", 10),
            new Node<>(new BooksByTitle(), new Book("b1", "author", 10),
                new Leaf<>(new BooksByTitle()), new Leaf<>(new BooksByTitle())),
            new Leaf<>(new BooksByTitle())).findLeft(new Book("b3", "author", 10)),
        new Book("b1", "author", 10))
        && t.checkExpect(new Leaf<>(new BooksByTitle()).findLeft(new Book("b1", "author", 10)),
            new Book("b1", "author", 10));
  }

  // test the getRight method
  boolean testGetRight(Tester t) {
    return t.checkExpect(this.bstE.getRight(), this.bstF)
        && t.checkException(new RuntimeException("No right of an empty tree"),
            new Leaf<>(new BooksByTitle()), "getRight");
  }

  // test the sameTree method
  boolean testSameTree(Tester t) {
    return t.checkExpect(this.bstA.sameTree(this.bstB), true)
        && t.checkExpect(this.bstA.sameTree(this.bstC), false)
        && t.checkExpect(this.bstD.sameTree(this.bstC), false)
        && t.checkExpect(new Leaf<>(new BooksByTitle()).sameTree(this.bstA), false)
        && t.checkExpect(new Leaf<>(new BooksByTitle()).sameTree(new Leaf<>(new BooksByTitle())),
            true);
  }

  // test the compareStructure method
  boolean testCompareStructure(Tester t) {
    return t.checkExpect(this.bstA.compareStructure(new Book("b3", "author", 10)), true)
        && t.checkExpect(this.bstA.compareStructure(new Book("b2", "author", 10)), false);
  }

  // test the sameData method
  boolean testSameData(Tester t) {
    return t.checkExpect(this.bstA.sameData(this.bstB), true)
        && t.checkExpect(this.bstA.sameData(this.bstC), true)
        && t.checkExpect(this.bstD.sameData(this.bstC), false)
        && t.checkExpect(new Leaf<>(new BooksByTitle()).sameData(this.bstA), false)
        && t.checkExpect(new Leaf<>(new BooksByTitle()).sameData(new Leaf<>(new BooksByTitle())),
            true);
  }

  // test the buildList method
  boolean testBuildList(Tester t) {
    return t.checkExpect(new Leaf<>(new BooksByTitle()).buildList(), new MtList<>())
        && t.checkExpect(this.bstA.buildList(), this.b1)
        && t.checkExpect(this.bstC.buildList(), this.b1);
  }
}