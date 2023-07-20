import tester.Tester;

//a class represent a painting
class Painting {
	Artist artist;
	String title;
	double value; // in millions of dollars
	int year;

	Painting(Artist artist, String title, double value, int year) {
		this.artist = artist;
		this.title = title;
		this.value = value;
		this.year = year;
	}

	/* fields:
	 *  this.artist ... Artist
	 *  this.title ... String
	 *  this.value ... double
	 *  this.year ... int
	 * methods:
	 *   this.samePainting(Painting) ... boolean
	 * methods for fields:
	 *   this.artist.sameArtist(Artist) ... boolean
	 */

	//is this Painting the same as the given one?
	boolean samePainting(Painting p) {
		/* fields of p:
		 *   p.artist ... Artist
		 *   p.title ... String
		 *   p.value ... int
		 *   p.year ... int
		 *  methods of p: 
		 *   p.titleComesBefore(Painting)
		 *   p.samePainting(Painting)
		 *   p.checkArtistName(String)
		 *  methods of fields of p:
		 *   p.artist.sameArtist(Artist) ... boolean
		 *   p.artist.checkName(String) ... boolean
		 */
		return this.artist.sameArtist(p.artist) &&
				this.title.equals(p.title) &&
				this.value == p.value &&
				this.year == p.year;

	}	
}

class Artist { 
	String name;
	int yob;

	Artist(String n, int yob) {
		this.name = n;
		this.yob = yob;
	}

	/* fields:
	 *  this.name ... String
	 *  this.yob ... int
	 * methods:
	 *  this.sameArtist(Artist) ... boolean
	 */

	//is this artist the same as the given one?
	boolean sameArtist(Artist that) {
		/* fields of that:
		 *  that.name ... String
		 *  that.yob ... int
		 * methods of that:
		 *  that.sameArtist(Artist) ... boolean 
		 *  that.checkName(String) ... boolean
		 */
		return this.name.equals(that.name) &&
				this.yob == that.yob;
	}
	
}

class ExamplesPaintings {
	
}










































class Counter {
	int val;

	Counter() {
		this(0);
	}

	Counter(int initialVal) {
		this.val = initialVal;
	}

	int get() {
		int ans = this.val;
		this.val = this.val + 1;
		return ans;
	}
}

class ExamplesCounter {
	boolean testCounter(Tester t) {
		Counter c1 = new Counter();
		Counter c2 = new Counter(2);
		return true;
		/* What should these tests be?
		return t.checkExpect(c1.get(), ???)              // Test 1
				&& t.checkExpect(c2.get(), ???)              // Test 2
				&& t.checkExpect(c1.get() == c1.get(), ???)  // Test 3
				&& t.checkExpect(c2.get() == c1.get(), ???)  // Test 4
				&& t.checkExpect(c2.get() == c1.get(), ???)  // Test 5
				&& t.checkExpect(c1.get() == c1.get(), ???)  // Test 6
				&& t.checkExpect(c2.get() == c1.get(), ???); // Test 7
				*/
	}
}