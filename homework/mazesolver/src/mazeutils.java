import java.util.*;
import javalib.worldimages.*;

class EdgeUtil {
	
	public void insertEdges(ArrayList<Edge> loe, Edge edge) {
		for (int i = 0; i < loe.size(); i ++) {
			if (edge.compareCosts(loe.get(i)) < 0) {
				loe.add(i, edge);
			}
		}
		  loe.add(edge);
	}

	public int generateCost(Random rand, int bounds, int preference, boolean vertical) {
		if (preference > 1 || preference < -1) {
			throw new IllegalArgumentException("Preference must be 1, -1, or 0");
		} if ((preference == 1 && vertical)|| (preference == -1 && !vertical)) {
			return rand.nextInt((int) Math.ceil(bounds / 3.0));
		} else {
			return rand.nextInt(bounds);
		}
	}
	
}

class UnionUtil {
	HashMap<ACell, ACell> representatives;
	
	UnionUtil(HashMap<ACell, ACell> representatives) {
		this.representatives = representatives;
	}
	
	ACell findCell(ACell first) {
		ACell ref = first;
		while (!ref.equals(representatives.get(ref))) {
			ref = representatives.get(ref);
		}
		return ref;
	}
	
	int countLevel(ACell first) {
		ACell ref = first;
		int count = 0;
		while (!ref.equals(representatives.get(ref))) {
			ref = representatives.get(ref);
			count ++;
		}
		return count;
	}
	
	
	void union(ACell cell1, ACell cell2) {
		if (this.countLevel(cell1) < this.countLevel(cell2)) {
			representatives.put(this.findCell(cell1), this.findCell(cell2));
		} else {
			representatives.put(this.findCell(cell2), this.findCell(cell1));
		}
	}
}

class ImageUtil {
	
	 WorldImage movePinhole(WorldImage bg, String dis, int cellSize) {
		    if (dis.equalsIgnoreCase("Up")) {
		    	bg = bg.movePinhole(0, cellSize / 2);
		    }
		    else if (dis.equalsIgnoreCase("Down")) {
		    	bg = bg.movePinhole(0, -cellSize / 2);
		    }
		    else if (dis.equalsIgnoreCase("Left")) {
		    	bg = bg.movePinhole(cellSize / 2, 0);
		    }
		    else if (dis.equalsIgnoreCase("Right")) {
		    	bg = bg.movePinhole(-cellSize / 2, 0);
		    }
		    return bg;
	}
}

interface ICollection<T> {
	 // Is this collection empty?
	  boolean isEmpty();

	  // EFFECT: adds the item to the collection
	  void add(T item);

	  // Returns the first item of the collection
	  // EFFECT: removes that first item
	  T remove();
	
}

class Stack<T> implements ICollection<T> {
	 Deque<T> contents;

	  // Initializes an empty Stack
	  Stack() {
	    this.contents = new ArrayDeque<T>();
	  }

	  // Returns whether or not the stack is empty
	  public boolean isEmpty() {
	    return this.contents.isEmpty();
	  }

	  // Removes the first item in a Stack
	  public T remove() {
	    return this.contents.removeFirst();
	  }

	  // Adds an item on the top of a Stack
	  public void add(T item) {
	    this.contents.addFirst(item);
	  }
	
}

class Queue<T> implements ICollection<T> {
	Deque<T> contents;

	  // Initializes an empty Queue
	  Queue() {
	    this.contents = new ArrayDeque<T>();
	  }

	  // Returns whether or not the Queue is empty
	  public boolean isEmpty() {
	    return this.contents.isEmpty();
	  }

	  // Removes the first item of the Queue
	  public T remove() {
	    return this.contents.removeFirst();
	  }

	  // Adds an item at the end of the Queue
	  public void add(T item) {
	    this.contents.addLast(item); // NOTE: Different from Stack!
	  }
	
}