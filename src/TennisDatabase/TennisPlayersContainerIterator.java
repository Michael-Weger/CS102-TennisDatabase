package TennisDatabase;

import java.util.Iterator;
import java.util.Stack;

import TennisDatabase.TennisPlayersContainer.NodeData;
import TennisDatabase.TennisPlayersContainer.TennisPlayersContainerNode;

/**
 * An iterator designed to work forward or backward.
 * 
 * @author Michael Weger
 */
class TennisPlayersContainerIterator implements Iterator<NodeData>
{
	private TennisPlayersContainerNode m_Root; 	// The root of the PlayersContainer.
	private QueueArrayBased<NodeData> m_Queue;	// The Queue which stores all items inorder.
	private Stack<NodeData> m_Stack;			// A stack which is used to simplify the reverse inorder traversal.

	TennisPlayersContainerIterator(TennisPlayersContainerNode root)
	{
		m_Root = root;
		m_Queue = new QueueArrayBased<NodeData>(20);
		m_Stack = new Stack<NodeData>();
		
		// Default to inorder sorting.
		this.setInorder();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() 
	{
		return !m_Queue.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public NodeData next() throws NullPointerException
	{
		try
		{
			return m_Queue.dequeue();
		}
		catch(NullPointerException e)
		{
			throw e;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove()
	{
		try
		{
			m_Queue.dequeue();
		}
		catch(NullPointerException e)
		{
			throw e;
		}
	}
	
	/**
	 * Sets up the queue to have all elements of the PlayersContainer sorted inorder
	 */
	public void setInorder()
	{
		m_Queue.dequeueAll();
		setInorderRecursive(m_Root);
	}
	
	/**
	 * Recursive supporting class which traverses the PlayerContainer to set the queue sorted inorder
	 * @param currentRoot The current root of the recursive function
	 */
	private void setInorderRecursive(TennisPlayersContainerNode currentRoot)
	{
		if(currentRoot == null)
			return;
	
		setInorderRecursive(currentRoot.leftChild);
		m_Queue.enqueue(currentRoot.data);
		setInorderRecursive(currentRoot.rightChild);
	}
	
	/**
	 * Sets up the queue to have all elements of the PlayersContainer sorted reverse inorder
	 */
	public void setReverseInorder()
	{
		m_Stack.clear();
		
		setReverseInorderRecursive(m_Root);
		
		m_Queue.dequeueAll();
		
		while(!m_Stack.isEmpty())
		{
			m_Queue.enqueue(m_Stack.pop());
		}
	}
	
	/**
	 * Recursive supporting class which traverses the PlayerContainer to set the queue sorted reverse inorder
	 * @param currentRoot The current root of the recursive function
	 */
	private void setReverseInorderRecursive(TennisPlayersContainerNode currentRoot)
	{
		if(currentRoot == null)
			return;
		
		setReverseInorderRecursive(currentRoot.leftChild);
		m_Stack.push(currentRoot.data);
		setReverseInorderRecursive(currentRoot.rightChild);
	}
	
	/**
	 * Sets up the queue to have all elements of the PlayersContainer sorted inorder
	 */
	public void setPreorder()
	{
		m_Queue.dequeueAll();
		setPreorderRecursive(m_Root);
	}
	
	/**
	 * Recursive supporting class which traverses the PlayerContainer to set the queue sorted preorder
	 * @param currentRoot The current root of the recursive function
	 */
	private void setPreorderRecursive(TennisPlayersContainerNode currentRoot)
	{
		if(currentRoot == null)
			return;

		m_Queue.enqueue(currentRoot.data);
		setPreorderRecursive(currentRoot.leftChild);
		setPreorderRecursive(currentRoot.rightChild);
	}
}
