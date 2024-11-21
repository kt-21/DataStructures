package Heaps;

public interface PriorityQueue<E> {
	E peek();
	boolean isEmpty();
	void add(E item);
	E remove();
}
