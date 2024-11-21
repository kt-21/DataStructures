package QueueImplementations;/*
 * Katherine Tsai
 * This is the ListNode implementation of the Queue interface.
 */

import java.util.NoSuchElementException;

public class ListQueue<E> {

	private ListNode end;
	
	//returns true if queue is empty
	public boolean isEmpty() {

		return end == null;
	}

	//adds to end of queue
	public void add(E item) {
		
		if(isEmpty()) {
			
			end = new ListNode(item, end);
			end.next = end;
			return;
		}
		
		end.next = new ListNode(item, end.next);
		end = end.next;
	}

	//returns front of queue
	public E peek() {

		if(isEmpty())
			throw new NoSuchElementException("Empty queue");
		
		return end.next.data;
	}

	//removes front of queue
	public E remove() {

		if(isEmpty())
			throw new NoSuchElementException("Empty queue");
		
		ListNode toReturn = end.next;
		end.next = toReturn.next;
		toReturn.next = null;
		
		//last element in list
		if(end.next == null)
			end = null;
		
		return toReturn.data;
	}
	
	public class ListNode {
		
		private E data;
		private ListNode next;
		
		public ListNode(E d, ListNode n) {
			
			data = d;
			next = n;
		}
	}
}
