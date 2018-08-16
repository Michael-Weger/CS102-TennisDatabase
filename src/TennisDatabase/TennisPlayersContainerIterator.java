package TennisDatabase;

import java.util.Iterator;
import java.util.Stack;

/**
 * An iterator designed to work forward or backward.
 * 
 * @author Michael Weger
 *
 * @param <T> The type this iterator will use
 */
class TennisPlayersContainerIterator<T> implements Iterator<T>
{
	private QueueArrayBased<T> m_Queue;	// The Queue which stores all items in forward order.
	private Stack<T> m_Stack;			// The Stack which stores all items in reverse order.

	public TennisPlayersContainerIterator(QueueArrayBased<T> queue)
	{
		T frontItem = queue.peek();
		m_Stack = new Stack<T>();
		
		// Move all items from the queue to the stack starting with the first one.
		// Keep re-adding the items to preserve the queue once a full iteration is complete.
		queue.enqueue(queue.peek());
		m_Stack.push(queue.dequeue());
		
		while(queue.peek() != frontItem)
		{
			queue.enqueue(queue.peek());
			m_Stack.push(queue.dequeue());
		}
		
		m_Queue = queue;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() 
	{
		return m_Queue.size() > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public T next() 
	{
		return m_Queue.dequeue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove()
	{
		m_Queue.dequeue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNextReverse()
	{
		return m_Stack.size() > 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public T nextReverse()
	{
		return m_Stack.pop();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void removeReverse()
	{
		m_Stack.pop();
	}

}
