/* 
 *             +--------------------------------+
               | ILoBook                        |<----------------------+
               +--------------------------------+                       |
               +--------------------------------+                       |
               | int count()                    |                       |
               | double salePrice(int discount) |                       |
               | ILoBook allBefore(int y)       |                       |
               | ILoBook sortByPrice()          |                       |
               +--------------------------------+                       |
                                |                                       |
                               / \                                      |
                               ---                                      |
                                |                                       |
                  -----------------------------                         |
                  |                           |                         |
+--------------------------------+   +--------------------------------+ |
| MtLoBook                       |   | ConsLoBook                     | |
+--------------------------------+   +--------------------------------+ |
+--------------------------------+ +-| Book first                     | |
| int count()                    | | | ILoBook rest                   |-+
| double salePrice(int discount) | | +--------------------------------+
| ILoBook allBefore(int y)       | | | int count()                    |
| ILoBook sortByPrice()          | | | double salePrice(int discount) |
+--------------------------------+ | | ILoBook allBefore(int y)       |
                                   | | ILoBook sortByPrice()          |
                                   | +--------------------------------+
                                   v
                   +--------------------------------+
                   | Book                           |
                   +--------------------------------+
                   | String title                   |
                   | String author                  |
                   | int year                       |
                   | double price                   |
                   +--------------------------------+
                   | double salePrice(int discount) |
                   +--------------------------------+
 */



import tester.*;

class Book {
    String name;
    String author;
    double price;
    int year;
    Book(String name, String author, double price, int year) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.year = year;
    }
    
    //to return the discounted price of this book given the discount rate as a percent
    double salePrice(int discount) {
        return this.price;
    }
    // to return the discounted price of this book given the discount rate 
    double discount(double rate) {
        return this.price - (rate * this.price);
    }
    // to return a new book with the same author and name as this book,
    // but with price discounted at the given rate
    Book discountedBook(double rate) {
        return new Book(this.name, this.author, this.discount(rate), this.year);
    }

	public boolean allBooksBefore(int year2) {
		return false;
	}
}



/*
 * GOAL:
 * Represent a bunch of books, and be able to compute
 * - their total price
 * - how many books we have
 * - all the books published before the given year
 * - a sorted list of books
 */

/*
 * A list of books is one of
 * empty
 * (cons book list-of-books)
 */

// DYNAMIC DISPATCH: deciding which method definition to invoke (in which class)
// based on the information available at runtime of the object that's invoking
// the method

interface ILoBook {
	// to count how many books are in this list of books
    int count();
	//calculate the total sale price of all books in this list for a given discount
	double salePrice(int discount);
	// published before the given year
    ILoBook allBooksBefore(int year);
    // to construct a list of books that contains the same books as
    // this list of books, but sorted increasing by price
    ILoBook sortByPrice();
	ILoBook insert(Book first);
}

class MtLoBook implements ILoBook {
	// FIELDS:
    // None.
	
	// Constructor 
    MtLoBook() {
    	
    }
    
    // Methods
    

    public int count() {
        return 0;
    }
    
    
	public double salePrice(int discount) {
		return 0;
	}
	
	public ILoBook allBooksBefore(int year) {
		//return new MtLoBook();
		return this;
	}
	
	public ILoBook sortByPrice() {
		return this; // this is an empty list of books 
		
	}
	
	public ILoBook insert(Book b) {
		return new ConsLoBook(b, this);
	}

}
class ConsLoBook implements ILoBook {
    Book first;
    ILoBook rest;
    
    ConsLoBook(Book first, ILoBook rest) {
        this.first = first;
        this.rest = rest;
    }
        /* Template:
         * FIELDS:
         * this.first -- Book
         * this.rest -- ILoBook
         * METHODS:
         * this.totalPrice() -- double
         * this.count() -- int
         * this.allBooksBefore(int) -- ILoBook
         * this.sortByPrice() -- ILoBook
         * METHODS OF FIELDS:
         * this.first.getPrice() -- double ****
         * this.first.discount(double) -- double
         * this.first.discountedBook(double) -- Book
         * this.rest.totalPrice() -- double  *****
         * this.rest.count() -- int
         * this.rest.allBooksBefore(int) -- ILoBook
         * this.rest.sortByPrice() -- ILoBook
         */

    public int count() {
        return 1 + this.rest.count();
    }
    
    //to return the discounted price of this book given the discount rate as a percent
    public double salePrice(int discount) {
    	
    	return this.first.salePrice(discount) 
    			+ this.rest.salePrice(discount);
    }

    public ILoBook allBooksBefore(int year) {
    	/* CONDITIONAL:
    	 * if(<test-expr>) THEN-STMT
    	 * if (test) THEN-STMT else ELSE-STMT
    	 * can be a statement block
    	 */
    		
    	if (this.first.allBooksBefore(year)) {
    		return new ConsLoBook(this.first, this.rest.allBooksBefore(year));
    	} 
    	
       else {
    	    return this.rest.allBooksBefore(year) ;
    	    }
    }
       
	public ILoBook sortByPrice() {
		return this.rest.sortByPrice().insert(this.first);
	}
	
	public ILoBook insert(Book book) {
		if (book.price < this.first.year)
			return new ConsLoBook(book, this);
		else {
			return new ConsLoBook (this.first, this.rest.insert(book));
		}
	}
}

class ExamplesBooks {
    Book htdp = new Book("HtDP", "MF", 0.0, 2014);
    Book hp = new Book("HP & the search for more money", "JKR", 9000.00, 2015);
    Book gatsby = new Book("The Great Gatsby", "FSF", 15.99, 1930);
    ILoBook mtList = new MtLoBook();
    ILoBook twoBooks = new ConsLoBook(this.htdp, 
                           new ConsLoBook(this.hp, 
                               this.mtList));
    ILoBook threeBooks = new ConsLoBook(this.gatsby, this.twoBooks);
  }






