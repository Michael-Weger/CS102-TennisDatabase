package TennisDatabase;

import java.util.ArrayList;
import java.util.List;

class SortedLinkedList<T extends Comparable<T>> implements SortedLinkedListInterface<T> {

	private SortedLinkedListNode<T> m_Head; // The head of the linked list
	
	/**
	 * Constructor
	 */
	public SortedLinkedList()
	{
		m_Head = null; // By default the head is null
	}
	
	/**
	 * Inserts a comparable data type into the linked list.
	 * @param data The comparable data type to insert.
	 */
	@Override
	public void insert(T data) throws Exception 
	{
		if(data == null)
			throw new Exception("Attempted to insert null data.");
		
		// No items in list
		if(m_Head == null)
		{
			m_Head = new SortedLinkedListNode<T>(data);
			return;
		}
		// Our item is greater than the first in the list
		else if(data.compareTo(m_Head.data) == 1)
		{
			SortedLinkedListNode<T> newNode = new SortedLinkedListNode<T>(m_Head, data);
			m_Head = newNode;
			return;
		}
		
		SortedLinkedListNode<T> previousNode = m_Head;
		
		// Search for where our new item should be inserted
		// Start looping at the second item in the list as we already checked to see if our new item should go first
		for(SortedLinkedListNode<T> loopNode = m_Head.next; loopNode != null ; loopNode = loopNode.next)
		{
			if(data.compareTo(loopNode.data) == 1)
			{
				SortedLinkedListNode<T> newNode = new SortedLinkedListNode<T>(loopNode, data);
				
				previousNode.next = newNode;
				return;
			}
			
			previousNode = loopNode;
		}
		
		// If this has been reached then our item should be inserted at the end of the list
		SortedLinkedListNode<T> newNode = new SortedLinkedListNode<T>(data);
		previousNode.next = newNode;
	}

	/**
	 * Prints the data of all nodes in the list.
	 */
	@Override
	public void print() throws RuntimeException 
	{
		// Empty list
		if(m_Head == null)
		{
			throw new RuntimeException("No items in list!");
		}
		// Traverse list and print data
		else
		{
			for(SortedLinkedListNode<T> loopNode = m_Head; loopNode != null; loopNode = loopNode.next)
			{
				System.out.println(loopNode.data);
			}
		}
	}
	
	/**
	 * Removes a node of specified data from the linked list.
	 * @param data The specified data.
	 */
	public void removeItem(T data)
	{
		// Empty list
		if(m_Head == null)
		{
		throw new RuntimeException("No items in list!");
		}
		// Traverse list and find the node with the specified data
		else
		{
			SortedLinkedListNode<T> lastNode = m_Head;
			
			if(m_Head.data.equals(data))
				m_Head = m_Head.next;
			
			else
				for(SortedLinkedListNode<T> loopNode = m_Head; loopNode != null; loopNode = loopNode.next)
				{
					if(loopNode.data.equals(data))
					{
						lastNode.next = loopNode.next;
						break;
					}
					
					lastNode = loopNode;
				}
		}
	}
	
	/**
	 * Returns this linked list as a list in order
	 * @return A list of this linked lists node's data
	 */
	public List<T> getAllAsList()
	{
		List<T> returnList = new ArrayList<T>();
		
		// Empty list
		if(m_Head == null)
		{
			throw new RuntimeException("No items in list!");
		}
		// Traverse list and find the node with the specified data
		else
		{
			for(SortedLinkedListNode<T> loopNode = m_Head; loopNode != null; loopNode = loopNode.next)
			{
				returnList.add(loopNode.data);
			}
		}
		
		return returnList;
	}

	/**
	 * 
	 * @author Michael Weger
	 *
	 * @param <T> The data type of the node
	 */
	@SuppressWarnings("hiding")
	private class SortedLinkedListNode<T extends Comparable<T>>
	{
		public SortedLinkedListNode<T> next; // The preceding node in the linked list
		public T data;	// The comparable data type of the ndoe
		
		/**
		 * Constructor
		 * @param data The comparable data which the node holds.
		 */
		public SortedLinkedListNode(T data)
		{
			this.next = null; // By default the next node is null
			this.data = data;
		}
		
		/**
		 * Constructor
		 * @param next The preceding node in the linked list.
		 * @param data The comparable data which the node holds.
		 */
		public SortedLinkedListNode(SortedLinkedListNode<T> next, T data)
		{
			this.next = next;
			this.data = data;
		}
	}
}
