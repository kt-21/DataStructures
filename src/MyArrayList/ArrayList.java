package MyArrayList;/*
 * Katherine Tsai
 * This class provides methods to give clients an easier way to use arrays. For example, the client will not need to make a new array every time the size needs to be changed.
 * Result must be the Object data type because you cannot convert result back to its original data type without undisguising it first.
 * You can only call methods in the Object class on result because result is disguised as an Object object.
 */

public class ArrayList {

	private Object[] data;
	private int numElements;
	
	public ArrayList() {
		
		data = new Object[10];
	}
	
	public ArrayList(int size) {
		
		if(size < 0)
			throw new IllegalArgumentException();
		
		data = new Object[size];
	}
	
	public ArrayList(ArrayList other) {
		
		numElements = other.numElements;
		
		data = new Object[other.data.length];
		for(int i = 0; i < numElements; i++)
			data[i] = other.data[i];
	}
	
	//appends the element to the end of the list
	public void add(Object element) {
		
		//check if the array is full
		if(numElements == data.length)
			data = resize(data);
		
		//add element to next new spot and update number of elements
		data[numElements] = element;
		numElements++;
	}
	
	//inserts element at index
	public void add(int index, Object element) {
		
		//check if index is valid
		if(index > numElements || index < 0)
			throw new IndexOutOfBoundsException("Index out of bounds.");
		
		//check if the array is full
		if(numElements == data.length)
			data = resize(data);
		
		//slides all elements from index on to the right by one
		for(int i = numElements; i > index; i--)
			data[i] = data[i - 1];
		
		//add element to next new spot and update number of elements
		data[index] = element;
		numElements++;
	}
	
	//returns the element located at index
	public Object get(int index) {
		
		//check if index is valid
		if(index > numElements - 1 || index < 0)
			throw new IndexOutOfBoundsException("Index out of bounds.");
		
		return data[index];
	}
	
	//returns index of first location of element
	public int indexOf(Object element) {
		
		//loop through array and return index when element is found
		for(int i = 0; i < numElements; i++) {
			
			if(data[i].equals(element))
				return i;
		}
		
		return -1;
	}
	
	//returns true if list is currently empty
	public boolean isEmpty() {
		
		//no elements in array
		if(numElements == 0)
			return true;
		
		return false;
	}
	
	//removes value at index and returns the removed element
	public Object removeIndex(int index) {
		
		//check if index is valid
		if(index > numElements - 1 || index < 0)
			throw new IndexOutOfBoundsException("Index out of bounds.");
		
		Object removed = data[index];
		numElements--;
		
		//slides all elements from index on to the left by one
		for(int i = index; i < numElements; i++)
			data[i] = data[i + 1];
		
		return removed;
	}
	
	//removes the first instance of element in the array and returns true if done
	public boolean removeElement(Object element) {
		
		//get index of first instance of element
		int index = indexOf(element);
		
		//check if index is valid
		if(index < 0)
			return false;
		
		//remove the element at index
		removeIndex(index);
		
		return true;
	}
	
	//changes the element at the given position and returns the original value
	public Object set(int index, Object element) {
		
		//check if index is valid
		if(index > numElements - 1 || index < 0)
			throw new IndexOutOfBoundsException("Index out of bounds.");
		
		//save the element change its value
		Object original = data[index];
		data[index] = element;
		
		return original;
	}
	
	//returns the total number of active elements
	public int size() {
		
		return numElements;
	}
	
	//doubles the size of the array and maintains the original values
	private Object[] resize(Object[] data) {
		
		Object[] newData = new Object[data.length*2];
		
		//copy over elements from old array to new array
		for(int i = 0; i < data.length; i++)
			newData[i] = data[i];
		
		return newData;
	}
}
