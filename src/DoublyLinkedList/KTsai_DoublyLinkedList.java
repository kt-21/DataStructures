package DoublyLinkedList;
/*
 * Katherine Tsai
 * A listIterator method is added to the Doubly Linked List.
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class KTsai_DoublyLinkedList<E> {

	private ListNode front;
	private ListNode end;
	private int numElements;
	private enum STATUS {NONE, NEXT, PREVIOUS};
	
	public ListIterator<E> listIterator() {
		
		return new ListIterator<E>() {
			
			private ListNode iterLoc = front;
			private STATUS stat = STATUS.NONE;
			
			//returns true if the next element exists
			public boolean hasNext() {

				return iterLoc != null;
			}
			
			//returns the next element in the list
			public E next() {

				if(!hasNext())
					throw new NoSuchElementException("No next element available.");
				
				//saves iterLoc to return, then moves iterLoc to the next spot
				stat = STATUS.NEXT;
				E toReturn = iterLoc.data;
				iterLoc = iterLoc.next;
				return toReturn;
			}
			
			//returns true if the previous element exists
			public boolean hasPrevious() {

				return iterLoc != front;
			}
		
			//returns the previous element in the list
			public E previous() {

				if(!hasPrevious())
					throw new NoSuchElementException("No previous element available.");
				
				//moves iterLoc to the previous position then returns the data
				stat = STATUS.PREVIOUS;
				iterLoc = returnPrevious(iterLoc);
				return iterLoc.data;
			}
			
			//removes the element most recently returned by next/previous
			public void remove() {

				//determine which ListNode to remove
				ListNode toRemove = checkStatus(true);
				
				//update connections
				if(toRemove == front)
					front = toRemove.next;
				else
					toRemove.previous.next = toRemove.next;
				
				if(toRemove == end)
					end = toRemove.previous;
				else
					toRemove.next.previous = toRemove.previous;
				
				toRemove.next = toRemove.previous = null;
				numElements--;
				stat = STATUS.NONE;
			}
			
			//adds a new element before iterLoc
			public void add(E item) {

				//add to front
				if(iterLoc == front) {
					
					front = new ListNode(null, item, iterLoc);
					
					//list was empty
					if(end == null)
						end = front;
					else
						iterLoc.previous = front;
				}
				
				//add to end
				else if(iterLoc == null) {
					
					end.next = new ListNode(end, item, null);
					end = end.next;
				}
				
				//add to middle
				else {
					
					 iterLoc.previous.next = new ListNode(iterLoc.previous, item, iterLoc);
					 iterLoc.previous = iterLoc.previous.next;
				}
				
				stat = STATUS.NONE;
				numElements--;
			}
			
			//gives a new value to the ListNode most recently returned by next/previous
			public void set(E item) {
				
				checkStatus(false).data = item;
			}
			
			public int previousIndex() {
				
				return 0;
			}

			public int nextIndex() {

				return 0;
			}
			
			//returns the spot before iterLoc
			private ListNode returnPrevious(ListNode toMove) {
				
				//move iterLoc back on to the end of the list
				if(toMove == null)
					return end;
				
				return toMove.previous;
			}
			
			//returns the ListNode last returned by next/previous
			private ListNode checkStatus(boolean isRemove) {
				
				if(stat == STATUS.NONE)
					throw new IllegalStateException();
				
				//determine ListNode last returned
				ListNode toReturn = iterLoc;
				if(stat == STATUS.NEXT)
					return returnPrevious(toReturn);
				else if(isRemove)
					iterLoc = iterLoc.next;
				
				return toReturn;
			}
		};
	}
	
	//returns true if the list is empty
	public boolean isEmpty() {
		
		return numElements == 0;
	}
	
	//adds item to the front of the linked list
	public void addFront(E data) {
		
		front = new ListNode(null, data, front);
		
		//make the link doubly
		if(numElements != 0)
			front.next.previous = front;
		else
			end = front;
		
		numElements++;
	}
	
	//inserts data after the index
	public void addAfter(int index, E data) {
		
		if(index < 0 || index >= numElements)
			throw new IndexOutOfBoundsException();

		//index is in front half of the list
		if(index <= (numElements - 1) / 2) {
		
			int i = 0;
			ListNode current = front;
			while(i <= index) {
		
				//check index and add if found
				insideAddAfter(index, data, i, current);
				i++;
				current = current.next;
			}
		}
		
		//index is in back half of the list
		else {
		
			int i = numElements - 1;
			ListNode current = end;
			while(i >= index) {
		
				//check index and add if found
				insideAddAfter(index, data, i, current);
				i--;
				current = current.previous;
			}
		}
	}
	
	//adds data to the end of the list
	public void addLast(E data) {
		
		end = new ListNode(end, data, null);
		
		//make the link doubly
		if(numElements != 0)
			end.previous.next = end;
		else
			front = end;
		
		numElements++;
	}
	
	//removes first occurrence of data
	public void remove(E data) {
		
		ListNode current = front;
		while(current != null) {
	
			if(current.data.equals(data)) {

				//make doubly
				if(current.previous != null)
					current.previous.next = current.next;
				else
					front = current.next;
				
				if(current.next != null)
					current.next.previous = current.previous;
				else
					end = current.previous;
				
				//sever all connections
				current.previous = null;
				current.next = null;
				numElements--;
			}
	
			current = current.next;
		}
	}
	
	//removes the first element in the list
	public void removeFirst() {
		
		if(numElements == 0)
			throw new NoSuchElementException();
		else {
			
			front = front.next;
			
			//sever all connections
			if(front != null) {
				
				front.previous.next = null;
				front.previous = null;
			}
		}
		
		numElements--;
		if(numElements == 0)
			end = null;
	}

	//removes the first element in the list
	public void removeLast() {
		
		if(numElements == 0)
			throw new NoSuchElementException();
		else {
			
			end = end.previous;
			
			//sever all connections
			if(end != null) {
				
				end.next.previous = null;
				end.next = null;
			}
		}
		
		numElements--;
		if(numElements == 0)
			front = null;
	}
		
	//returns the total number of elements in the list
	public int size() {
		
		return numElements;
	}
	
	//returns an element based on an index passed in
	public E get(int index) {
		
		return getSet(index, null, false);
	}
	
	//returns the old value at position index
	public E set(int index, E value) {
		
		return getSet(index, value, true);
	}
	
	private void insideAddAfter(int index, E data, int i, ListNode current) {
		
		//find spot index and add
		if(i == index) {

			current.next = new ListNode(current, data, current.next);
			
			//make the link doubly
			if(index != numElements - 1)
				current.next.next.previous = current.next;
			else
				end = current.next;
			
			numElements++;
			return;
		}
	}
	
	//to be called by get and set
	private E getSet(int index, E value, boolean setValue) {
		
		if(index < 0 || index >= numElements)
			throw new IndexOutOfBoundsException();
		
		//index is in front half of the list
		if(index <= (numElements - 1) / 2) {
			
			int i = 0;
			ListNode current = front;
			while(i <= index) {
		
				//find index to return
				if(i == index)
					return insideGetSet(value, setValue, current);
				
				i++;
				current = current.next;
			}
		}
		
		//index is in back half of the list
		else {
			
			int i = numElements - 1;
			ListNode current = end;
			while(i >= index) {
		
				//find index to return
				if(i == index)
					return insideGetSet(value, setValue, current);
				
				i--;
				current = current.previous;
			}
		}
		
		return null;
	}
	
	//to be called by getSet
	private E insideGetSet(E value, boolean setValue, ListNode current) {
		
		E toReturn = current.data;
		
		//if called by set
		if(setValue)
			current.data = value;
		
		return toReturn;
	}
	
	public void printBackwards() {
		
		String toReturn = "[";
		ListNode current = end;
		while(current != null) {
			
			toReturn += current.data + ", ";
			current = current.previous;
		}
		
		System.out.println(toReturn.substring(0, toReturn.length()) + "]");
	}
	
	public String toString() {
		
		String toReturn = "[";
		ListNode current = front;
		while(current != null) {
			
			toReturn += current.data + ", ";
			current = current.next;
		}
		
		return toReturn.substring(0, toReturn.length()) + "]";
	}
	
	public class ListNode {
		
		private E data;
		private ListNode next;
		private ListNode previous;
		
		public ListNode(ListNode p, E d, ListNode n) {
			
			previous = p;
			data = d;
			next = n;
		}
	}
}
