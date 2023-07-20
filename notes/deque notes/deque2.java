//class Deque<T> {
//	
//	Sentinel<T> header;
//	
//	
//}
//
//abstract class ANode<T> {
//	ANode<T> prev;
//	ANode<T> next;
//	
//	ANode (ANode<T> prev, ANode<T> next) {
//		this.next = next;
//		this.prev = prev;
//	}
//}
//
//class Sentinel<T> extends ANode<T> {
//	
//	Sentinel() {
//		super(new Sentinel<T>(), new Sentinel<T>());
//	}
//}
//
//class Node<T> extends ANode<T> {
//	T data;
//	
//	Node(T data) {
//		super (null, null);
//		this.data = data;
//	}
//	
//	Node (T data, ANode<T> next, ANode<T> prev) {
//		super(next, prev);
//		this.data = data;
//	}
//}