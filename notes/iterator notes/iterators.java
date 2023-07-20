import java.util.ArrayList;
import tester.Tester;



interface Iterator<T> {
	
	boolean hasNext();
	
	T next();
	
	void remove();
	
	
}


interface IList<T> extends Iterable<T> {
	
}

class MyArrayList<T> {
	ArrayListIterator<T> iterator() {
		return new ArrayListIterator<T>(this);
	}
}

class ArrayListIterator<T> implements Iterator<T>{
   ArrayList<T> arrayList;
   
   int currentElement;
   
   ArrayListIterator(ArrayList<T> arrayList) {
	   this.arrayList = arrayList;
	   this.currentElement = 0;
	   
   }
   
   //checks whether the arraylist has a next element 
   public boolean hasNext() {
	   return this.currentElement < this.arrayList.size();
   }
   
   //returns the value of the next element 
   public T next() {
	   
	   T answer = this.arrayList.get(currentElement);
	   this.currentElement = this.currentElement + 1;
	   return answer;
	   
   }

  public void remove() {	
	  throw new UnsupportedOperationException("remove is not implemented.");
  }
  
  
}

class Util  {
   Util(){}
   
   void forEach() {
	   ArrayList<Integer> aList = new ArrayList<Integer>();
	   aList.add(1);
	   aList.add(2);
	   aList.add(3);
	   
	   int sum = 0;
	   ArrayListIterator<Integer> iterator = new ArrayListIterator<Integer>(aList);
	   while (iterator.hasNext()) {
		   Integer elem = iterator.next();
	   }
	   
	   for (Integer i : aList) {
		   sum = sum + i;
	   }
   }
}

class IteratorExamples {
	IteratorExamples(){}
	
	void testSelfMadeForEachString(Tester t) {
		ArrayList<String> aList = new ArrayList<String>();
		   aList.add("1");
		   aList.add("2");
		   aList.add("3");
		   
		   String sum = "";
		   
		   MyArrayList<Integer> myList = new MyArrayList();
		   for (Integer i : myList) {
			   sum = sum + i;
		   }
		   
		   ArrayListIterator<String> iterator = new ArrayListIterator<String>(aList);
		   while (iterator.hasNext()) {
			   String elem = iterator.next();
			   sum = sum + elem;
		   }
		   
		   t.checkExpect(sum, "123");
		   t.checkExpect(aList.size(), 3);
		   
	}
	
	void testForEach(Tester t) {
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("1");
		aList.add("2");
		aList.add("3");
		aList.add("4");

		String sum = "";
		
		MyArrayList myList = new MyArrayList();
		
		int sum1 = 0;
		for (Integer i : myList) {
			sum1 = sum1 + i;
		}
		
		for (String s : aList) {
			Iterator<String> iterator = aList.iterator();
			while (iterator.hasNext()) {
				String elem = iterator.next();
				sum = sum + elem;
			}
		}
	}
	
	void testSelfMadeForEachInt(Tester t) {
		ArrayList<Integer> aList = new ArrayList<Integer>();
		   aList.add(1);
		   aList.add(2);
		   aList.add(3);
		   
		   int sum = 0;
		   ArrayListIterator<Integer> iterator = new ArrayListIterator<Integer>(aList);
		   while (iterator.hasNext()) {
			   int elem = iterator.next();
			   sum = sum + elem;
		   }
		 t.checkExpect(sum, 6);
		 t.checkExpect(aList.size(), 3);
	}
 	
}

