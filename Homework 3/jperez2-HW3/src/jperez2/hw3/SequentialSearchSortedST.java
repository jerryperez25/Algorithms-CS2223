package jperez2.hw3;

/**
 * The declaration of SequentialSearchST now declares that each key can be compared
 * with each other key via the Comparable interface
 * 
 * @param <Key>
 * @param <Value>
 */
public class SequentialSearchSortedST<Key extends Comparable<Key>, Value> {
	int N;           // number of key-value pairs
	Node first;      // the linked list of key-value pairs

	// Nodes now store (key and value)
	class Node {
		Key   key;
		Value value;
		Node  next;

		public Node (Key key, Value val, Node next)  {
			this.key  = key;
			this.value  = val;
			this.next = next;
		}
	}

	public int size()                 { return N;  }
	public boolean isEmpty()          { return size() == 0; }
	public boolean contains(Key key)  { return get(key) != null; }

	
	/**
	 * Be sure to modify this method to stop once you have found a key that is
	 * larger than the key you are looking for.
	 * 
	 * @param key
	 * @return
	 */
	public Value get(Key key) 
	{
		for (Node forlooper=first;forlooper!=null;forlooper=forlooper.next)
		{
			 if (key.equals(forlooper.key))
			 {
				 return forlooper.value;
			 }
		}
		return null;
	}

	/**
	 * Be sure to modify this method to insert the key into its proper place
	 * in ascending sorted order.
	 * 
	 * @param key
	 * @return
	 */
	public void put(Key key, Value val) 
	{
		if (val == null) 
		{
			delete (key);
			return;
		}

		for (Node forlooper = first;forlooper!=null;forlooper=forlooper.next)
		{
			if (key.equals(forlooper.key)) 
			{
                forlooper.value = val;
                return;
            }
		}
		// add as new node at beginning
		first = new Node (key, val, first);
		N++;
	}
	
	/**
	 * Can short-circuit this method once you hit a key value that is larger 
	 * than the target key being deleted.
	 * 
	 * @param key
	 */
	public void delete(Key key) 
	{
		first = delete(first,key);
	}
	private Node delete(Node thisone, Key key)
	{
		if(thisone==null)
		{
			return null;
		}
		if (key.equals(thisone.key)) 
		{
            N--;
            return thisone.next;
        }
        thisone.next = delete(thisone.next, key);
        return thisone;
    }
}