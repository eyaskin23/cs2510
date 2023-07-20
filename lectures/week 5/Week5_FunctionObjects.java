import tester.Tester;

interface IPaintingPred{

	boolean apply(Painting p); 
	
	ILoPainting byArtist(String artist);
	
	ILoPainting beforeYear(int year);
}
	


//represents a list of paintings
interface ILoPainting {
	//get all of the paintings in this list that are by the artist with the given name
	ILoPainting getByArtistName(String name);
	//sort this list of painting alphabetically by title
	ILoPainting sortByTitle();
	//inserts the given painting into this *sorted* list
	ILoPainting insertByTitle(Painting p);
	
	ILoPainting sortByValue();
	
	ILoPainting insertbyValue(Painting p);
	
	ILoPainting compareByValue(Painting p);

	ILoPainting checkValue(Painting p);
	
	ILoPainting sort(IPaintingCompare comp);
	
	ILoPainting insert(Painting p, IPaintingCompare comp);
	
	boolean ormap(IPaintingPred pred);
	//gets the best painting so far
	Painting getBest(IPaintingCompare comp);
	//accumulator: keeps track of the best painting so far
	Painting getBestAcc(IPaintingCompare comp, Painting acc);
}

//represents an empty list of painting
class MtLoPainting implements ILoPainting {

	//get all of the paintings in this empty list that are by the 
	//artist with the given name
	public ILoPainting getByArtistName(String name) {
		return this;
	}

	//sort this empty list of painting alphabetically by title
	public ILoPainting sortByTitle() {
		return this;
	}

	//inserts the given painting into this *sorted* list
	public ILoPainting insertByTitle(Painting p) {
		return new ConsLoPainting(p, this);
	}

	@Override
	public ILoPainting sort(IPaintingCompare comp) {
		return this;
	}

	@Override
	public ILoPainting sortByValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILoPainting insertbyValue(Painting p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILoPainting checkValue(Painting p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILoPainting insert(Painting p, IPaintingCompare comp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ormap(IPaintingPred pred) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Painting getBest(IPaintingCompare comp) {
		throw new RuntimeException("no painting to return");
	}

	@Override
	public Painting getBestAcc(IPaintingCompare comp, Painting acc) {
		return acc;
	}

	
}

//represents a non-empty list of painting
class ConsLoPainting implements ILoPainting {
	Painting first;
	ILoPainting rest;
	
	ConsLoPainting(Painting first, ILoPainting rest) {
		this.first = first;
		this.rest = rest;
	}

	
	/* fields:
	 *  this.first ... Painting
	 *  this.rest ... ILoPainting
	 * methods:
	 *  this.getByArtistName(String) ... ILoPainting
	 * methods for fields:
	 *  this.first.samePainting(Painting) ... boolean
	 *  this.first.checkArtistName(String) ... boolean
	 *  this.first.titleComesBefore(Painting) ... boolean  
	 *  this.rest.getByArtistName(String) ... ILoPainting
	 */

	//get all of the paintings in this non-empty list that are by 
	//the artist with the given name
	public ILoPainting getByArtistName(String name) {
		if (this.first.checkArtistName(name)) {
			return new ConsLoPainting(this.first, this.rest.getByArtistName(name));
		}
		else {
			return this.rest.getByArtistName(name);
		}
	}



	//sort this list by title
	public ILoPainting sortByTitle() {
		return this;
	}
	



	//inserts the given painting into this *sorted* list
	public ILoPainting insertByTitle(Painting p) {
		if (this.first.titleComesBefore(p)) {
			return new ConsLoPainting(this.first, this.rest.insertByTitle(p));
		}
		else {
			return new ConsLoPainting(p, this);
		}
	}


	@Override
	public ILoPainting sortByValue() {
		return this.rest.sortByValue().insertbyValue(first);
	}


	@Override
	public ILoPainting insertbyValue(Painting p) {
		if (this.first.checkValue(p)) {
			return new ConsLoPainting(this.first, this.rest.insertbyValue(p));
		} else {
			return this.rest.insertbyValue(p);
		}
	}
	
	@Override
	public ILoPainting insert(Painting p, IPaintingCompare comp) {
		// TODO Auto-generated method stub
		if (comp.compare(this.first, p) < 0) {
			return new ConsLoPainting(this.first, this.rest.insert(p, comp));
		} else {
			return this.rest.insert(p, comp);
		}
	}


	@Override
	public ILoPainting checkValue(Painting p) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ILoPainting sort(IPaintingCompare comp) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean ormap(IPaintingPred pred) {
		// TODO Auto-generated method stub
		return false;
	}


	public Painting getBest(IPaintingCompare comp) {
		return null;
	}

	public Painting getBestAcc(IPaintingCompare comp, Painting acc) {
		if (comp.compare(this.first, acc) < 0) {
			return this.rest.getBestAcc(comp, this.first);
		} else {
			return this.rest.getBestAcc(comp, acc);
		}
	}
}


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

	public boolean checkValue(Painting p) {
		// TODO Auto-generated method stub
		return false;
	}

	/* fields:
	 *  this.artist ... Artist
	 *  this.title ... String
	 *  this.value ... double
	 *  this.year ... int
	 * methods:
	 *   this.samePainting(Painting) ... boolean
	 *   this.checkArtistName(String) ... boolean
	 *   this.titleComesBefore(Painting) ... boolean
	 * methods for fields:
	 *   this.artist.checkName(String) ... boolean
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
	
	//does the artist of this painting have the given name?
	boolean checkArtistName(String name) {
		return this.artist.checkName(name);
	}
	

	//does the title of this painting come before the title of the given one 
	boolean titleComesBefore(Painting p) {
		/* fields of p:
		 *   p.artist
		 *   p.title
		 *   p.value
		 *   p.year
		 *  methods of p: 
		 *   p.titleComesBefore(Painting)
		 *   p.samePainting(Painting)
		 *   p.checkArtistName(String)
		 *  methods of fields of p:
		 *   p.artist.sameArtist(Artist) ... boolean
		 *   p.artist.checkName(String) ... boolean
		 */
		
		return this.title.compareTo(p.title) < 0;

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
	 *  this.checkName(String) ... boolean 
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
	
	//is the name of this artist the same as the given name
	boolean checkName(String name) {
		return this.name.equals(name);
	}

}

class Flip implements IPaintingPred {
	IPaintingPred pred;
	
	Flip(IPaintingPred pred) {
		this.pred = pred;
	}

  public boolean apply(Painting p) {
	
	/* template for this method: 
	 * relaxing the restriction on accessing fields of parameters.
	 * wrt function objects, that are not the same as this
	 */
	return (this.pred.apply(p));
  }

@Override
public ILoPainting byArtist(String artist) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ILoPainting beforeYear(int year) {
	// TODO Auto-generated method stub
	return null;
}
}

class AndPredicate implements IPaintingPred {
	IPaintingPred left;
	IPaintingPred right;
	
	AndPredicate(IPaintingPred left, IPaintingPred right) {
		this.left = left;
		this.right = right;
	}

	public boolean apply(Painting p) {
        return this.left.apply(p) && this.right.apply(p);
	}

	@Override
	public ILoPainting byArtist(String artist) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILoPainting beforeYear(int year) {
		// TODO Auto-generated method stub
		return null;
	}
}

interface IPaintingCompare {
	//determine which painting comes first
	//return a negative int if p1 comes before p2
	//return a positive int if p2 comes before p1
	//return a zero if they are the same
	int compare(Painting p1, Painting p2);
}

class CompareByValue implements IPaintingCompare {
	public int compare(Painting p1, Painting p2) {
		return (int)p1.value - (int)p2.value;
		
		
	}
}

class CompareByTitle implements IPaintingCompare {
	
	//does p1's title come before p2's title alphabetically?
	public int compare(Painting p1, Painting p2) {
		return 0;
	}
}

class FlipCompare implements IPaintingCompare {
	IPaintingCompare comp;
	
	FlipCompare(IPaintingCompare comp) {
		this.comp = comp;
	}
	
	public int compare(Painting p1, Painting p2) {
		return this.comp.compare(p1, p2) * - 1;
	}
}

class ExamplesPaintings {
	Artist daVinci = new Artist("Da Vinci", 1452);
	Artist banksy = new Artist("Banksy", 1975); //estimated yob
	Artist monet = new Artist("Monet", 1840);
	Painting mona = new Painting(this.daVinci, "Mona Lisa", 10, 1503);
	Painting last = new Painting(this.daVinci, "The Last Supper", 11, 1480);
	Painting sunflowers = new Painting(new Artist("Van Gogh", 1853), 
			"sunflowers", 9, 1889);
	Painting waterlilies = new Painting(this.monet, "Water Lilies", 20, 1915);
	Painting showMe = new Painting(this.banksy, "Show me the Monet", 6, 2005);
	Painting balloon = new Painting(this.banksy, "Balloon Girl", 25, 2002);
	
	ILoPainting mt = new MtLoPainting();
	ILoPainting list1 = new ConsLoPainting(this.mona, this.mt);
	ILoPainting list2 = new ConsLoPainting(this.sunflowers, this.list1);
	ILoPainting list3 = new ConsLoPainting(this.waterlilies, this.list2);
	ILoPainting list4 = new ConsLoPainting(this.last, this.list3);
	
	IPaintingPred byYearAndArtist = new AndPredicate(new beforeYear(1800), new byArtist("Da Vinci"));
	
	boolean testGetBest(Tester t) {
		return t.checkExpect(this.list4.getBest(new CompareByValue()), this.waterlilies);
	}
}
	
	
	
	// tests in shorthand: 
	//this.daVinci.checkName("Da Vinci") --> true
	//this.daVinci.checkName("Banksy") --> false
	//this.daVinci.sameArtist(this.monet) --> false
	//this.daVinci.sameArtist(this.daVinci) --> true
	
	//this.mona.samePainting(this.last) --> false
	//this.mona.samePainting(this.mona) --> true
	//this.mona.checkArtistName("Da Vinci") --> true
	//this.mona.checkArtistName("Monet") --> false
	//this.balloon.titleComesBefore(this.waterlilies) --> true
	//this.waterlilies.titleComesBefore(this.balloon) --> false
	
	//this.mt.count() --> 0
	//this.list2.count() --> 2
	//this.list3.count() --> 3
	
	//this.mt.getByArtistName("Da Vinci") --> this.mt
	//this.list4.getByArtistName("Banksy") --> this.mt
	//this.list3.getByArtistName("Da Vinci") --> this.list1
	//this.list4.getByArtistName("Da Vinci") --> new ConsLoPainting(this.last,
	//                                             new ConsLoPainting(this.mona, this.mt))        
	










