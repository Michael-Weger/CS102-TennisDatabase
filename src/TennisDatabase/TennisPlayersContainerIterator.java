package TennisDatabase;

import java.util.Iterator;

class TennisPlayersContainerIterator<T> implements Iterator<T>
{
	private QueueArrayBased<T> m_Queue;

	public TennisPlayersContainerIterator(QueueArrayBased<T> queue)
	{
		m_Queue = queue;
	}
	
	public boolean hasNext() 
	{
		return m_Queue.size() > 0;
	}

	public T next() 
	{
		return m_Queue.dequeue();
	}
	
	public void remove()
	{
		m_Queue.dequeue();
	}

}
