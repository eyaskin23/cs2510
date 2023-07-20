import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import tester.Tester;


 class CombineLists {
	 List<String> combineLists(ArrayList<String> list1, ArrayList<String> list2) {
		 List<String> result = new ArrayList<>();
		 
		 int i = 0; int j = 0;
		 while (i < list1.size() && j < list2.size()) {
			 result.add(list1.get(i++));
			 result.add(list2.get(j++));
		 }
		 
		 while (i < list1.size()) {
			 result.add(list1.get(i));
		 }
		 
		 while (j < list2.size()) {
			 result.add(list2.get(j++));
		 }
		 
		 return result;
	 }
	 
	 public void testCombine(Tester t) {
		 CombineLists c = new CombineLists();
		 
		 ArrayList<String> list1 = new ArrayList<>(Arrays.asList("apple", "banana", "cherry"));
		 ArrayList<String> list2 = new ArrayList<>(Arrays.asList("orange", "kiwi", "grape"));
		 List<String> expected1 = new ArrayList<>(Arrays.asList("apple", "orange", "banana","kiwi", "cherry", "grape"));
		 List<String> actual1 = c.combineLists(list1, list2);
		 t.checkExpect(actual1, expected1);
		 
 	 }
 }
 
class IntRangeIterator implements Iterator<Integer> {
	int nextValue;
	int max;
	
	IntRangeIterator(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("min must be less than or equal to max");
		}
		
		this.nextValue = min;
		this.max = max;
	}
	
	public boolean hasNext() {
		return nextValue <= max;
	}
	
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		
		return nextValue++;
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
}

class Examples{
	Examples() {}
	
	 public void testRangeIterator(Tester t) {
	        // Test case 1
	        List<Integer> expected1 = new ArrayList<>(Arrays.asList(2, 3, 4));
	        List<Integer> actual1 = new ArrayList<>();
	        IntRangeIterator it1 = new IntRangeIterator(2, 4);
	        while (it1.hasNext()) {
	            actual1.add(it1.next());
	        }
	        t.checkExpect(actual1, expected1);
	    }
}
























