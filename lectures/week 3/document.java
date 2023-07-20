import tester.Tester;

interface IDocument {
	IDocument unCitedBibliography();
	ILoString sources();
}

class Book implements IDocument {
	String author;
	String title;
	String publishers;
	ILoDocument bibliography;
	
	Book(String author, String title, String publishers,
			ILoDocument bibliography) {
		this.author = author;
		this.title = title;
		this.publishers = publishers;
		this.bibliography = bibliography;
	}

	public IDocument unCitedBibliography() {
		return new Book(this.author, this.title, null,
				null);
	}

	public ILoString sources() {
		return new ConsLoString (this.author, null);
	}
	
}

class wikipediaArticles implements IDocument {
	String author;
	String title;
	String url;
	ILoDocument bibliography;
	
	wikipediaArticles(String author, String title,
			String url, ILoDocument bibliography) {
		this.author = author;
		this.title = title;
		this.url = url;
		this.bibliography = bibliography;
  }

	public IDocument unCitedBibliography() {
		return new wikipediaArticles
		(this.author, this.title, null, null);
	}
}

interface ILoDocument {
	ILoDocument unCitedBibliography();
}

class MtLoDocument implements ILoDocument {
	MtLoDocument () {}
	
	public ILoDocument unCitedBibliography () {
		return null;
	}
}

class ConsLoDocument implements ILoDocument {
	String first; 
	String rest;
	
	ConsLoDocument(String first, String rest) {
		this.first = first;
		this.rest = rest;
	}

	public ILoDocument unCitedBibliography() {
		return new ConsLoDocument(this.first, null);
	}
}


















//////////////////////////////////////////////////////////////////


interface ILoInt {
	//does this list satisfy the 3 requirements in 
	// problem 2, variant A in lec.8?
	
	boolean satisfies();
	//accumulator:
	boolean satisfiesAcc(boolean even, boolean posAndOdd,
			boolean bet5and10);
	
	
}

class MtLoInt implements ILoInt {

	public boolean satisfies() {
		return false;
	}

	public boolean satisfiesAcc(boolean even, boolean posAndOdd, boolean bet5and10) {
		return false;
	}
}


class ConsLoInt implements ILoInt {
	int first;
	ILoInt rest;
	
	ConsLoInt(int first, ILoInt rest) {
		this.first = first;
		this.rest = rest;
	}
	
    public boolean satisfies() {
    	return this.satisfiesAcc(false, false, false);
    }

	public boolean satisfiesAcc(boolean even, boolean posAndOdd, boolean bet5and10) {
		return (even && posAndOdd && bet5and10) || 
		 this.rest.satisfiesAcc(even || this.first % 2 == 0,
				posAndOdd || this.first % 2 == 1 && this.first > 0,
				bet5and10 || this.first >=5 && this.first <= 10);
	}
}

class ExamplesLoInt {
	ILoInt mt = new MtLoInt();
	ILoInt list1 = new ConsLoInt(3, new ConsLoInt(6, this.mt));
	ILoInt list2 = new ConsLoInt(3, new ConsLoInt(6, this.mt));

boolean testSat(Tester t) {
	return t.checkExpect(this.list1.satisfies(), true) &&
	t.checkExpect(this.list2.satisfies(), false);
	
  }
}

























