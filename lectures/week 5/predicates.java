import ConsLoRunner.Runner;

interface IBookPredicate {
	boolean apply(Book b);
}

interface IRunnerPredicate {
	boolean apply(Runner r);
}

interface IPred<T> {
	boolean apply(T t);
}

class BookByAuthor implements IPred<Book>{
	public boolean apply(Book b) {
		return b.author.equals("JKR");
	}
}

class IsPosUnder50Runner implements IPred<Runner>{
	public boolean apply(Runner r) {
		return r.pos <= 50;
	}

	@Override
	public boolean apply(Runner t) {
		// TODO Auto-generated method stub
		return false;
	}
}

class IsStringLongerThan10 implements IPred<String>{
	public boolean apply(String s) {
		return s.length() <= 10;
	}
}

class IPredExamples {
	IPredExamples(){}
	
	IPred<Book> byAuthor = new BookByAuthor();
	IPred<Runner> posUnder50 = new IsPosUnder50Runner();
}

class ExamplesMarathon {
	
	Runner johnny = new Runner("Kelly", 100, 999, true, 30, 360);
	
	IList<Integer> resultList = marathon.map(new Runner2pos());
}

