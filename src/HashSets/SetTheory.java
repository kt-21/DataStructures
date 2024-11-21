package HashSets;

import java.util.HashSet;

public class SetTheory {

	//takes two Sets and returns a new Set that contains all elements from the first Set and all elements from the second Set
	public static HashSet<Object> union(HashSet<Object> s1, HashSet<Object> s2) {
		
		HashSet<Object> toReturn = new HashSet<Object>();
		
		for(Object toAdd : s1)
			toReturn.add(toAdd);
		for(Object toAdd : s2)
			toReturn.add(toAdd);
		
		return toReturn;
	}
	
	//takes two Sets and returns a third Set that contains the elements that both sets share
	public static HashSet<Object> intersection(HashSet<Object> s1, HashSet<Object> s2) {
		
		HashSet<Object> toReturn = new HashSet<Object>();
		
		for(Object toAdd : s1)
			if(s2.contains(toAdd))
				toReturn.add(toAdd);
		
		return toReturn;
	}
	
	//takes two Set references and returns a third Set that contains the elements that occur in one set or the other, but not both
	public static HashSet<Object> xor(HashSet<Object> s1, HashSet<Object> s2) {
		
		HashSet<Object> toReturn = new HashSet<Object>();
		
		for(Object toAdd : s1)
			if(!s2.contains(toAdd))
				toReturn.add(toAdd);
		
		for(Object toAdd : s2)
			if(!s1.contains(toAdd))
				toReturn.add(toAdd);
		
		return toReturn;
	}
}
