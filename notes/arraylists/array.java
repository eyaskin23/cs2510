import tester.Tester;
import java.util.ArrayList;

interface IFunc<R, A> {
	R apply(A arg);
}


class CountCharacters implements IFunc<Integer, String> {
	public Integer apply(String value) {
		return value.length();
	}


void foo() {
	int x;
	int i;
	
	for (i = 0; i < 10; i = i + 1) {
		int r = i + 10 - x;
	}
  }
}


class Utils<T> {
	Utils(){};
	
	<T> void swap(ArrayList<T> arrayList, int index1, int index2) {
       T oldValueAtIndex1 = arrayList.get(index1);
       T oldValueAtIndex2 = arrayList.get(index2);
       
       arrayList.set(index2, oldValueAtIndex1);
       arrayList.set(index1, oldValueAtIndex2);
  }
	
	<T, U> ArrayList<U> map(ArrayList<T> arrayList, IFunc<U, T> func) {
		ArrayList<U> result = new ArrayList<U>();
		return this.mapHelper(arrayList, func, result, 0);
	}
	
	<T, U> ArrayList<U> mapHelper(ArrayList<T> arrayList, IFunc<U, T> func, ArrayList<U> result, int currentIndex) {
		if (currentIndex >= arrayList.size()) {
			return result;
		}
		result.add(func.apply(arrayList.get(currentIndex)));
		currentIndex = currentIndex + 1;
		return this.mapHelper(arrayList, func, result, currentIndex);
	}
	
	<T, U> ArrayList<U> map2(ArrayList<T> arrayList, IFunc<U, T> func) {
		ArrayList<U> result = new ArrayList<U>();
		
		//["1st" "2nd", "3rd"]
		for ( T elem : arrayList ) {
			result.add(func.apply(elem));
		}
		return result;
	}
}

class ArrayListExamples {
	ArrayListExamples() {}
	
	void testArrayListAddGet(Tester t) {
	ArrayList<String> stringList = new ArrayList<String>();
	
	t.checkException(new IndexOutOfBoundsException("Index 0 out of bounds for length 0"),
			stringList, "get", 0);
			
	t.checkExpect(stringList.size(), 0);
		
	stringList.add("1st item");
	t.checkExpect(stringList.size(), 1);
	
	stringList.add("2nd item");
	t.checkExpect(stringList.size(), 2);
	
	stringList.remove(1);
	t.checkExpect(stringList.size(), 1);
	
	t.checkExpect(stringList.get(0), "1st item");
	
	stringList.add(0, "0th item");
	t.checkExpect(stringList.size(), 2);
	
	t.checkException(new IndexOutOfBoundsException("Index 3 out of bounds for length 2")
			, stringList, "get", 3);
	}
	
	void testArrayListSwap(Tester t) {
	  ArrayList<String> stringList = new ArrayList<String>();
	  Utils utils = new Utils();
	  stringList.add("1st");
	  stringList.add("2nd");
	  
	  t.checkExpect(stringList.get(0), "1st");
	  t.checkExpect(stringList.get(1), "2nd");
	  utils.swap(stringList, 0, 1);
	  t.checkExpect(stringList.get(0), "2nd");
	  t.checkExpect(stringList.get(1), "1st");
	 }
	
	 void testArrayListMap(Tester t) {
		 ArrayList<String> stringList = new ArrayList<String>();
		 
		 IFunc<Integer, String> funcObj = new CountCharacters();
		 
		  stringList.add("1st");
		  stringList.add("2nd "); 
		  
		  Utils utils = new Utils();
		  
		  ArrayList<Integer> result = utils.map(stringList, funcObj);
  
	 
		  t.checkExpect(result.size(), 2);
		  t.checkExpect(result.get(0), 3);
		  t.checkExpect(result.get(1), 4);
		  
		  
		  
	 
	 }
	 
	 void capitalizeTitles(ArrayList<Book> books) {
		 for (Book b : books) {
			 b.capitalizeTitle();
		 }
	 }
	 
	 
	 
}





