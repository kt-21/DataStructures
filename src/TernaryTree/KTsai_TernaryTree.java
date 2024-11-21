package TernaryTree;
/*
 * Katherine Tsai
 * This class provides methods for a Ternary Tree data structure. It is for storing Strings that link to a value and makes searching for words more efficient.
 */

public class KTsai_TernaryTree<E extends Comparable<E>> {

	private TreeNode root;
	
	public void insert(String key, Integer val) {
		root = insert(root, key, val);
	}
	
	//recursively searches for location to start inserting nodes
	private TreeNode insert(TreeNode r, String key, Integer val) {
		
		if(key.length() == 0)
			return r;
		
		if(r == null)
			return insertNode(key, val);
		
		//keep moving down the tree until spot is reached
		if(r.letter.equals(key.substring(0,1))) {
			
			//check if word is finished
			if(key.length() == 1)
				r.value = val;
			else
				r.equalsChild = insert(r.equalsChild, key.substring(1), val);
		}
		else if(key.compareTo(r.letter) < 0)
			r.leftChild = insert(r.leftChild, key, val);
		else
			r.rightChild = insert(r.rightChild, key, val);
		
		return r;
	}
	
	//adds a linked list of the rest of the key
	private TreeNode insertNode(String key, Integer val) {
		
		TreeNode toReturn = new TreeNode(key.substring(0,1), -1, null, null, null);
		key = key.substring(1);
		TreeNode current = toReturn;
		
		//add a node for each letter of key
		while(key.length() != 0) {
			
			current.equalsChild = new TreeNode(key.substring(0,1), -1, null, null, null);
			key = key.substring(1);
			current = current.equalsChild;
		}
		
		current.value = val;
		return toReturn;
	}
	
	//returns the value associated with the key parameter
	public Integer get(String key) {
		return get(root, key);
	}
	
	private Integer get(TreeNode r, String key) {
		
		if(r == null || key.length() == 0)
			return null;

		//word found
		if(key.equals(r.letter) && r.value != -1)
			return r.value;
		
		//search further based on alphabetical order
		TreeNode next;
		if(r.letter.equals(key.substring(0,1)))
			return get(r.equalsChild, key.substring(1));
		else if(key.compareTo(r.letter) < 0)
			next = r.leftChild;
		else
			next = r.rightChild;

		return get(next, key);
	}
	
	//prints all keys in tree in alphabetical order
	public void inOrder() {
		inOrder(root, "");
	}
	
	private void inOrder(TreeNode r, String previous) {
		
		if(r != null) {
			
			inOrder(r.leftChild, previous);
			//print if value exists (word is complete)
			if(r.value != -1)
				System.out.println(previous + r.letter);
			inOrder(r.equalsChild, previous + r.letter);
			inOrder(r.rightChild, previous);
		}
	}
	
	//prints all keys with prefix 
	public void prefixMatch(String pre) {
		prefixMatch(root, pre, "");
	}
	
	private void prefixMatch(TreeNode r, String pre, String soFar) {
		
		if(r == null)
			return;
		
		prefixMatch(r.leftChild, pre, soFar);
		
		//check if matches so far
		int cutOff = Math.min((soFar + r.letter).length(), pre.length());
		if(!(soFar + r.letter).substring(0, cutOff).equals(pre.substring(0, cutOff))) {
			
			prefixMatch(r.rightChild, pre, soFar);
			return;
		}
		
		//print if matches
		if(r.value != -1 && (soFar + r.letter).length() >= pre.length())
			System.out.println(soFar + r.letter);
		
		prefixMatch(r.equalsChild, pre, soFar + r.letter);
		prefixMatch(r.rightChild, pre, soFar);
	}
	
	public class TreeNode {
		
		private String letter;
		private Integer value;
		private TreeNode leftChild;
		private TreeNode rightChild;
		private TreeNode equalsChild;
		
		public TreeNode(String l, int v, TreeNode lc, TreeNode rc, TreeNode ec) {
			
			letter = l;
			value = v;
			leftChild = lc;
			rightChild = rc;
			equalsChild = ec;
		}
	}
}
