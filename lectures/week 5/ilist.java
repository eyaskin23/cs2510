import java.awt.print.Book;
import java.util.ArrayList;

interface ILoBook {
	ILoBook find(IBookPredicate predicate);
}

interface ILoRunner {
	boolean find(Runner r);
}

class MtLoBook implements ILoBook {
	MtLoBook() {}
	public ILoBook find(IBookPredicate predicate) {
	return new MtLoBook();
  }
}

class ConsLoBook implements ILoBook {
	Book first; 
	ILoBook rest;
	
	ConsLoBook(Book first, ILoBook rest) {
		this.first = first;
		this.rest = rest;
	}

	public ILoBook find(IBookPredicate predicate) {
		return null;
	}
}

class ConsLoRunner implements ILoRunner {
	Runner first;
	ILoRunner rest;
	
	ConsLoRunner(Runner first, ILoRunner rest) {
		this.first = first;
		this.rest = rest;
	}
	
	public ILoRunner find(IRunnerPredicate predicate) {
		if (predicate.apply(this.first)) {
			return new ConsLoRunner(this.first)
	} else {
		return rest.find(predicate);
	}
}
	
class Runner {
	
}






interface IList<T> {
	
}

class MtList<T> implements IList<T> {
	
}

class ConsList<T> implements IList<T>{
	T first;
	IList<T> rest;
	
	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
  }
}

class IListExamples {
	IListExamples() {}
	
	Runner frank = new Runner("Shorter", 32, 888, true, 245, 130);
	Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);

	
	
	IList<Runner> runnerList = new ConsList<Runner>();
}

  void capitalizeTitles_bad(ArrayList<Book> books) {
	 for (Book b : books) {
		 b = new Book(b.title.toUpperCase(), b.author);
	 }
}
}