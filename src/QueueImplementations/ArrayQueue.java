package QueueImplementations;
/*
 * Katherine Tsai
 * This is the Array implementation of the Queue interface.
 */

import java.util.NoSuchElementException;

public class ArrayQueue<E> {

	private E[] data = (E[]) new Object[10];
	private int frontLoc;
	private int addLoc;
	private int numElements;
	
	//returns true if queue is empty
	public boolean isEmpty() {
		
		return numElements == 0;
	}
	
	//adds to end of queue
	public void add(E item) {
		
		//resize
		if(numElements == data.length) {
			
			E[] newData = (E[]) new Object[data.length*2];
			
			for(int i = 0; i < numElements; i++)
				newData[i] = data[(i + frontLoc) % data.length];
			
			data = newData;
			addLoc = numElements;
			frontLoc = 0;
		}
		
		data[addLoc] = item;
		numElements++;
		addLoc = (addLoc + 1) % data.length;
	}
	
	//returns front of queue
	public E peek() {
		
		if(isEmpty())
			throw new NoSuchElementException("Empty queue");
		
		return data[frontLoc];
	}
	
	//removes front of queue
	public E remove() {
		
		E toReturn = peek();
		numElements--;
		frontLoc = (frontLoc + 1) % data.length;
		return toReturn;
	}
}
