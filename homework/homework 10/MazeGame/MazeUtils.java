import java.util.*;

import javalib.worldimages.WorldImage;

class EdgeWeightUtil {
  
  void insertEdge(ArrayList<Edge> arr, Edge edge) {
    for (int i = 0; i < arr.size(); i += 1) {
      if (edge.compareWeights(arr.get(i)) < 0) {
        arr.add(i, edge);
        return;
      }
    }
    arr.add(edge);
  }

  
  int generateWeight(Random rand, int range, int preference, boolean isVertical) {
    if (preference > 1 || preference < -1) {
      throw new IllegalArgumentException("Preference must be 1, -1, or 0");
    }
    else if ((preference == 1 && isVertical) || (preference == -1 && !isVertical)) {
      return rand.nextInt((int) Math.ceil(range / 3.0));
    }
    else {
      return rand.nextInt(range);
    }
  }
}

class UnionFind {
  HashMap<ACell, ACell> representatives;

  UnionFind(HashMap<ACell, ACell> representatives) {
    this.representatives = representatives;
  }

  ACell find(ACell start) {
    ACell reference = start;
    while (!reference.equals(representatives.get(reference))) {
      reference = representatives.get(reference);
    }
    return reference;
  }

  void union(ACell cell1, ACell cell2) {
    if (this.countLevel(cell1) < this.countLevel(cell2)) {
      representatives.put(this.find(cell1), this.find(cell2));
    }
    else {
      representatives.put(this.find(cell2), this.find(cell1));
    }
  }

  private int countLevel(ACell start) {
    ACell reference = start;
    int levelCount = 0;
    while (!reference.equals(representatives.get(reference))) {
      reference = representatives.get(reference);
      levelCount += 1;
    }
    return levelCount;
  }
}

class ImageUtil {

  WorldImage movePinhole(WorldImage image, String dir, int cellSize) {
    if (dir.equalsIgnoreCase("Up")) {
      image = image.movePinhole(0, cellSize / 2);
    }
    else if (dir.equalsIgnoreCase("Down")) {
      image = image.movePinhole(0, -cellSize / 2);
    }
    else if (dir.equalsIgnoreCase("Left")) {
      image = image.movePinhole(cellSize / 2, 0);
    }
    else if (dir.equalsIgnoreCase("Right")) {
      image = image.movePinhole(-cellSize / 2, 0);
    }
    return image;
  }
}

interface ICollection<T> {
  boolean isEmpty();

  void add(T item);

  T remove();
}

class Stack<T> implements ICollection<T> {
  Deque<T> contents;

  Stack() {
    this.contents = new ArrayDeque<T>();
  }

  public boolean isEmpty() {
    return this.contents.isEmpty();
  }

  public T remove() {
    return this.contents.removeFirst();
  }

  public void add(T item) {
    this.contents.addFirst(item);
  }
}

class Queue<T> implements ICollection<T> {
  Deque<T> contents;

  Queue() {
    this.contents = new ArrayDeque<T>();
  }

  public boolean isEmpty() {
    return this.contents.isEmpty();
  }

  public T remove() {
    return this.contents.removeFirst();
  }

  public void add(T item) {
    this.contents.addLast(item);  
  }
}