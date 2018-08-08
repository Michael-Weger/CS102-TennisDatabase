package TennisDatabase;

public class SortedLinkedList<T extends Comparable<T>> implements SortedLinkedListInterface<T> {

	private SortedLinkedListNode<T> m_Head;
	
	public SortedLinkedList()
	{
		m_Head = null;
	}
	
	@Override
	public void insert(T data) throws Exception 
	{
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

	@Override
	public void print() throws RuntimeException 
	{
		if(m_Head == null)
			System.out.println("No items in list!");
		else
		{
			for(SortedLinkedListNode<T> loopNode = m_Head; loopNode != null; loopNode = loopNode.next)
			{
				System.out.println(loopNode.data);
			}
		}
	}

	@SuppressWarnings("hiding")
	private class SortedLinkedListNode<T extends Comparable<T>>
	{
		public SortedLinkedListNode<T> next;
		public T data;
		
		public SortedLinkedListNode(T data)
		{
			this.next = null;
			this.data = data;
		}
		
		public SortedLinkedListNode(SortedLinkedListNode<T> next, T data)
		{
			this.next = next;
			this.data = data;
		}
	}
}
