package LimitedPriorityQueue;
/*
 * Katherine Tsai
 * This class represents a PriorityQueue with a max capacity and elements that are maintained in an ArrayList in order by priority.
 * A priority queue shouldn't be a subclass of ArrayList.
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class KTsai_LimitedPQ<E extends Comparable<E>> {

	private final int MAX_ELEMENTS;
	private ArrayList<E> queue = new ArrayList<E>();
	
	public KTsai_LimitedPQ(int capacity) {
		
		if(capacity <= 0)
			throw new IllegalArgumentException();
		
		MAX_ELEMENTS = capacity;
	}
	
	//returns true if queue is empty
	public boolean isEmpty() {
		
		return queue.isEmpty();
	}
	
	//adds item to its spot if possible
	public E add(E item) {
		
		//determine index to add item to
		int index = 0;
		if(!queue.isEmpty())
			while(index < MAX_ELEMENTS && item.compareTo(queue.get(index)) < 0)
				index++;
		
		queue.add(index, item);
		
		//not full
		if(queue.size()-1 != MAX_ELEMENTS)
			return null;
		
		//full
		return queue.remove(0);
	}
	
	//removes and returns item with greatest priority
	public E remove() {
		
		if(queue.isEmpty())
			throw new NoSuchElementException();
		
		return queue.remove(queue.size()-1);
	}
	
	//returns item with greatest priority
	public E peek() {
		
		if(queue.isEmpty())
			throw new NoSuchElementException();
		
		return queue.get(queue.size()-1);
	}
}
