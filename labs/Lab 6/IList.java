import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;

// a list of class T
interface IList<T> {
  IList<T> filter(Predicate<T> pred);
  
  <U> IList<U> map(Function<T,U> converter);
  
  <U> U fold(BiFunction<T,U,U> converter,U initial);  
}

// an empty list of class T
class MtList<T> implements IList<T> {
  
  MtList() {}
  
  //filters a certain list based on a prompt
  public IList<T> filter(Predicate<T> pred) {
    return new MtList<T>();
  }

  //returns a certain instance of a string 
  public <U> IList<U> map(Function<T, U> converter) {
    return new MtList<U>();
  }
  
  //adds one to the list if the string matches the given parameter
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    return initial;
  }
}

// a non-empty list of class T
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  //returns a list of filtered strings
  
  public IList<T> filter(Predicate<T> pred) {
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first,this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }
  
  //returns a certain instance of a string
  
  public <U> IList<U> map(Function<T, U> converter) {
    return new ConsList<U>(converter.apply(this.first),this.rest.map(converter));
  }

  //adds one to the list if the string matches the parameter
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    return converter.apply(this.first, this.rest.fold(converter,initial));
  }
}

//tests if the month starts with a "j"
class FirstLetterJ implements Predicate<String> { 
  
  //tests if the first string starts with a j
  public boolean test(String t) {
    return t.substring(0, 1).equals("J");
  } 
} 

//tests if the month ends in an "er"
class EndWithEr implements BiFunction<String, Integer, Integer> {

  //if the months ends in an er, it adds one to the list, otherwise returns the previous number
  public Integer apply(String t, Integer u) {
    if (t.substring(t.length() - 2, t.length()).equals("er")) {
      return u + 1;
    }
    else {
      return u;
    }
  }
}

//abbreviates the month into three letters
class Abbrev implements Function<String, String> {

  //returns the first three letters of the string
  public String apply(String t) {
    return t.substring(0, 3);
  }
}

//examples of lists of months
class ExamplesLists {
  ExamplesLists() {}
  
  IList<String> emptyList = new MtList<String>(); //empty list
  IList<String> january = new ConsList<String>("January", this.emptyList); //january
  IList<String> february = new ConsList<String>("February", this.january); //february
  IList<String> march = new ConsList<String>("March", this.february); //march
  IList<String> april = new ConsList<String>("April", this.march); //april
  IList<String> may = new ConsList<String>("May", this.april); //may
  IList<String> june = new ConsList<String>("June", this.may); //june
  IList<String> july = new ConsList<String>("July", this.june); //july
  IList<String> august = new ConsList<String>("August", this.july); //august
  IList<String> september = new ConsList<String>("September", this.august); //september
  IList<String> october = new ConsList<String>("October", this.september); //october
  IList<String> november = new ConsList<String>("November", this.october); //november
  IList<String> december = new ConsList<String>("December", this.november); //december
  
  //checks which months begin with "J"
  boolean testFirstLetterJ(Tester t) {
    return t.checkExpect(this.december.filter(new FirstLetterJ()), 
        new ConsList<String>("July", new ConsList<String>("June",
            new ConsList<String>("January", new MtList<String>()))));
  }

  //checks the number of months that end in "er"
  
  boolean testEndWithEr(Tester t) {
    return t.checkExpect(this.december.fold(new EndWithEr(), 0), 4);
  }
  
  //checks the abbreviated versions of the months 
  
  boolean testAbbrev(Tester t) { 
    return t.checkExpect(this.december.map(new Abbrev()), 
        new ConsList<String>("Dec", new ConsList<String>("Nov",
            new ConsList<String>("Oct", new ConsList<String>("Sep",
                new ConsList<String>("Aug", new ConsList<String>("Jul",
                    new ConsList<String>("Jun", new ConsList<String>("May",
                        new ConsList<String>("Apr", new ConsList<String>("Mar",
                            new ConsList<String>("Feb", new ConsList<String>("Jan", 
                                new MtList<String>())))))))))))));
  }
}