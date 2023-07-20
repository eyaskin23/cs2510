import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

import tester.Tester;

class ArrayUtils {
	//EFFECT: swaps the items in the list at the given positions
	<T> void swap(ArrayList<T> alist, int index1, int index2) {

		//T oldItemAtIndex2 = alist.get(index2);

		//alist.set(index2, alist.get(index1));
		//alist.set(index1, oldItemAtIndex2);

		alist.set(index2, alist.set(index1, alist.get(index2)));
	}

	//map a function onto every member of the list, returning a list of the results
	<T, U> ArrayList<U> map(ArrayList<T> alist, Function<T, U> fun) {
		return this.mapHelp(alist, fun, 0, new ArrayList<U>());
	}

	//map a function onto every member of the list, returning a list of the results
	//keeps track of the current index
	<T, U> ArrayList<U> mapHelp(ArrayList<T> alist, Function<T, U> fun, int currIndex, ArrayList<U> result) {
		if (currIndex >= alist.size()) {
			return result;
		}
		else {
			result.add(fun.apply(alist.get(currIndex)));
			return this.mapHelp(alist, fun, currIndex + 1, result);
		}
	}

	//map a function onto every member of the list, returning a list of the results
	<T, U> ArrayList<U> map2(ArrayList<T> alist, Function<T, U> fun) {
		ArrayList<U> result = new ArrayList<U>();

		for (T t : alist) {
			result.add(fun.apply(t));
		}

		return result;
	}

	//combines the items in the list using the given function
	<T, U> U foldl(ArrayList<T> alist, BiFunction<T, U, U> fun, U base) {
		U result = base;

		for (T item : alist) {
			result = fun.apply(item, result);
		}

		return result;
	}

	//finds the index of the given item in the list, or returns -1 if not in the list
	<T> int find(ArrayList<T> alist, Comparator<T> comp, T target) {
		return this.findHelp(alist, comp, target, 0);
	}

	//finds the index of the given item in the list, or returns -1 if not in the list
	<T> int findHelp(ArrayList<T> alist, Comparator<T> comp, T target, int current) {
		if (current >= alist.size()) {
			return -1;
		}
		else if (comp.compare(target, alist.get(current)) == 0) {
			return current;
		}
		else {
			return this.findHelp(alist, comp, target, current + 1);
		}
	}
}


class ExamplesArrayList {
	ArrayList<Integer> ints;
	ArrayList<String> strings;
	ArrayList<String> suits = new ArrayList<String>(Arrays.asList("hearts", "diamonds", "spades", "clubs"));
	ArrayList<String> values = new ArrayList<String>(Arrays.asList("ace", "two", "three", "four", 
			"five", "six", "seven", "eight", "nine", "ten", "j", "q", "k"));



	void initData() {
		this.ints = new ArrayList<Integer>();
		this.ints.add(10);
		this.ints.add(20);
		this.ints.add(30);

		this.strings = new ArrayList<String>(Arrays.asList("a", "bb", "ccc"));
	}

	void testArrayList(Tester t) {
		this.initData();
		t.checkExpect(this.ints.get(0), 10);
		t.checkExpect(this.ints.get(2), 30);
		//t.checkExpect(this.ints.get(3), 10);

		this.strings.add(1, "b");

		t.checkExpect(this.strings.get(1), "b");
	}

	void testSwap(Tester t) {
		this.initData();

		t.checkExpect(this.ints.get(0), 10);
		t.checkExpect(this.ints.get(2), 30);

		new ArrayUtils().swap(this.ints, 0, 2);

		t.checkExpect(this.ints.get(0), 30);
		t.checkExpect(this.ints.get(2), 10);

	}
	
	void testFind(Tester t) {
		
	}
}


class Card {
	String suit;
	String value;

	Card(String s, String v) {
		this.suit = s;
		this.value = v;
	}
}













