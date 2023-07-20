
import java.util.ArrayList;
import java.util.Iterator;

import tester.Tester;

class ArrayListIterator {

	ArrayList<Integer> arrayList;
	int currentElement;
	
	ArrayListIterator(ArrayList<Integer> arrayList) {
		this.arrayList = arrayList;
		this.currentElement = 0;
	}
	
	/* TEMPLATE:
	 * 
	 * FIELDS:
	 * ... this.arrayList ...          --- ArrayList
	 * ... this.currentElement ...     --- int
	 * 
	 * METHODS:
	 * ... this.hasNext() ...          --- boolean
	 * ... this.next() ...             --- int
	 */
	
	boolean hasNext(ArrayList<Integer> list) {
		return this.currentElement < this.arrayList.size();
	}

	int next() {
		Integer answer = this.arrayList.get(currentElement);
		currentElement = this.currentElement + 1;
		return answer;
	}
}

public class ListOfLists<T> implements Iterable<T> {
	
	ArrayList<ArrayList<T>> list;
	
	ListOfLists() {
		this.list = new ArrayList<ArrayList<T>>();
		
	}
	
	/* TEMPLATE:
	 * 
	 * FIELDS:
	 * ... this.list ...               --- ArrayList 
	 *  
	 * METHODS:
	 * ... this.addNewList() ...       --- void
	 * ... this.add() ...              --- void
	 * ... this.get() ...              --- ArrayList
	 * ... this.size() ...             --- int
	 * ... this.iterator() ...         --- iterator
	 */

	public void addNewList() {
		this.list.add(new ArrayList<T>());
	}
	
	public void add(int index, T object) {
		if (index < 0 || index >= this.list.size()) {
			throw new IndexOutOfBoundsException("Invalid");
		}
		this.list.get(index).add(object);
	}
	
	
	public ArrayList<T> get(int index) {
		if (index < 0 || index >= this.list.size()) {
			throw new IndexOutOfBoundsException("Invalid");
		}
		return this.list.get(index);
	}
	
	public int size() {
		return this.list.size();
		
	}
	
	void testListOfLists(Tester t) {
	    ListOfLists<Integer> lol = new ListOfLists<Integer>();
	    //add 3 lists
	    lol.addNewList();
	    lol.addNewList();
	    lol.addNewList();
	 
	    //add elements 1,2,3 in first list
	    lol.add(0, 1);
	    lol.add(0, 2);
	    lol.add(0, 3);
	 
	    //add elements 4,5,6 in second list
	    lol.add(1, 4);
	    lol.add(1, 5);
	    lol.add(1, 6);
	 
	    //add elements 7,8,9 in third list
	    lol.add(2, 7);
	    lol.add(2, 8);
	    lol.add(2, 9);
	    
	 
	    //iterator should return elements in order 1,2,3,4,5,6,7,8,9
	    int number = 1;
	    for (Integer i : lol) {
	    	t.checkExpect(i, number);
	    	number = number + 1;
	    }
	}
	
	public Iterator<T> iterator() {
		return new ListOfListsIterator(this);
	}
	
 private class ListOfListsIterator implements Iterator<T> {
		 ListOfLists<T> listOfLists;
		 int currentListIndex;
		 
		 ListOfListsIterator(ListOfLists<T> listOfLists) {
			 this.currentListIndex = 0;
			 this.listOfLists = listOfLists;
		}
		
		 /* TEMPLATE:
			 * 
			 * FIELDS:
			 * ... this.currentListIndex ...   --- int
			 * ... this.listOfLists ...        --- ListOfLists
			 * 
			 * METHODS:
			 * ... this.hasNext() ...          --- boolean
			 * ... this.remove() ...           --- void
			 * ... this.next() ...             --- T
			 */

		public boolean hasNext() {
			return this.currentListIndex < this.listOfLists.size();
		}

		
		public void remove() {
			throw new UnsupportedOperationException("Don't do this!");
		}


		public T next() {
			ArrayList<T> answer = this.listOfLists.get(currentListIndex);
			this.currentListIndex = this.currentListIndex + 1;
			return answer.get(currentListIndex);
		}
	}
    
	void testAdd(Tester t) {
		ArrayList<Integer> aList = new ArrayList<Integer>();
		aList.add(1);
		aList.add(2);
		aList.add(3);
		
		int sum = 0;
		ArrayListIterator iterator = new ArrayListIterator(aList);
		while (iterator.hasNext(aList)) {
			Integer elem = iterator.next();
			sum = sum + elem;
		}
		
		t.checkExpect(sum, 6);
	}
}























