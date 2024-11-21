package Heaps;
/*
 * Katherine Tsai
 * This class provides methods for a node based implementation of a Heap that implements the Priority Queue interface.
 */

import java.util.NoSuchElementException;

public class KTsai_NodeHeap<E extends Comparable<E>> implements PriorityQueue<E> {

	TreeNode root;
	int numElements;
	
	//returns item with greatest priority
	public E peek() {

		if(numElements == 0)
			throw new NoSuchElementException();

		return root.value;
	}

	//returns true if queue is empty
	public boolean isEmpty() {

		return numElements == 0;
	}

	//adds item to its spot in the tree
	public void add(E item) {

		numElements++;
		if(root == null) {
			root = new TreeNode(item, null, null, null);
			return;
		}
		
		//bubble down to next to last spot
		String binary = Integer.toBinaryString(numElements).substring(1);
		TreeNode current = root;
		while(binary.length() > 1) {
			current = getNext(binary, current);
			binary = binary.substring(1);
		}
		
		//adds item to last spot
		if(binary.equals("0")) {
			current.left = new TreeNode(item, null, null, current);
			current = current.left;
		}
		else {
			current.right = new TreeNode(item, null, null, current);
			current = current.right;
		}

		//reheap
		while(current.parent != null && current.value.compareTo(current.parent.value) < 1) {
			current.value = current.parent.value;
			current = current.parent;
			current.value = item;
		}
	}

	//removes and returns item with greatest priority
	public E remove() {

		if(numElements == 0)
			throw new NoSuchElementException();
		
		numElements--;
		E toReturn = root.value;
		if(numElements == 0) {
			root = null;
			return toReturn;
		}
		
		//bubble down to next to last spot
		String binary = Integer.toBinaryString(numElements+1).substring(1);
		TreeNode current = root;
		while(binary.length() > 1) {
			current = getNext(binary, current);
			binary = binary.substring(1);
		}
		
		E toMove;
		if(binary.equals("0")) {
			toMove = current.left.value;
			current.left = null;
		}
		else {
			toMove = current.right.value;
			current.right = null;
		}
		
		//reheap
		root.value = toMove;
		current = root;
		while(current.left != null) {
			
			//right child is smaller
			if(current.right != null && current.right.value.compareTo(current.value) < 0 && current.right.value.compareTo(current.left.value) < 0) {
				current.value = current.right.value;
				current = current.right;
				current.value = toMove;
			}
			
			//left child is smaller
			else if(current.left.value.compareTo(current.value) < 0) {
				current.value = current.left.value;
				current = current.left;
				current.value = toMove;
			}
			
			//sorted
			else
				return toReturn;
		}
		return toReturn;
	}
	
	private TreeNode getNext(String binary, TreeNode current) {
		
		if(binary.substring(0,1).equals("0"))
			return current.left;
		return current.right;
	}
	
	public class TreeNode {
		
		private E value;
		private TreeNode left;
		private TreeNode right;
		private TreeNode parent;
		
		public TreeNode(E v, TreeNode l, TreeNode r, TreeNode p) {
			
			value = v;
			left = l;
			right = r;
			parent = p;
		}
	}
}
