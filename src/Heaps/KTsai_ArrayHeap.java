package Heaps;
/*
 * Katherine Tsai
 * This class provides methods for an array based implementation of a Heap that implements the Priority Queue interface.
 */

import java.util.NoSuchElementException;

public class KTsai_ArrayHeap<E extends Comparable<E>> implements PriorityQueue<E> {

	private E[] data;
	private int numElements;
	
	public KTsai_ArrayHeap() {
		
		data = (E[])new Comparable[10];
		numElements = 0;
	}
	
	//returns item with greatest priority
	public E peek() {

		//throw if empty
		if(numElements == 0)
			throw new NoSuchElementException();
		
		return data[0];
	}

	//returns true if array is empty
	public boolean isEmpty() {

		return numElements == 0;
	}

	//adds item to its spot in the array
	public void add(E item) {

		//resize if necessary
		if(numElements == data.length) {

			E[] resizedArray = (E[])new Comparable[data.length];
			for(int i = 0; i < data.length; i++)
				resizedArray[i] = data[i];
			
			data = resizedArray;
		}
		
		int current = numElements;
		data[current] = item;
		
		//swap until item is in the right spot
		int parent = getParent(current);
		while(parent >= 0 && item.compareTo(data[parent]) < 0) {
			
			data[current] = data[parent];
			data[parent] = item;
			
			current = parent;
			parent = getParent(current);
		}
		
		numElements++;
	}

	//removes and returns item with greatest priority
	public E remove() {

		//throw if empty
		if(numElements == 0)
			throw new NoSuchElementException();
		
		//replace root with new root
		int current = 0;
		E toReturn = data[current];
		data[current] = data[numElements-1];
		
		//swap until the new root is swapped to its right spot
		int child = getChild(current);
		while(child != -1) {
			System.out.println("hello" + child);
			E toSwap = data[child];
			data[child] = data[current];
			data[current] = toSwap;
			
			current = child;
			child = getChild(current);
		}
		
		numElements--;
		return toReturn;
	}
	
	//returns the index of the current child's parent
	private int getParent(int current) {
		
		int parent = (current-1) / 2;
		if(current % 2 == 0)
			parent = (current-2) / 2;
		
		return parent;
	}
	
	//returns the index of the smaller of the parent's children
	private int getChild(int current) {
		
		int left = 2*current + 1;
		int child = -1;
		
		//return smaller existing child
		if(left < numElements) {
			child = left;
			if(left + 1 < numElements) {
				
				if(data[left + 1].compareTo(data[left]) < 0)
					return left + 1;

				return left;
			}
		}
		if(left + 1 < numElements)
			return left + 1;
		
		return child;
	}
}
