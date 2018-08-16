package TennisDatabase;

/**
 * A generic Queue implementation using a circular array
 * @author Michael Weger
 *
 * @param <T> The type which this generic QueueArray will use
 */
class QueueArrayBased<T> implements QueueArrayBasedInterface<T>
{
	private T[] m_Array;			// The circular array which stores the items
	private int m_QueueFront;		// The index of the front of the queue
	private int m_InsertionOffset;	// The offset from the front at which new items will be inserted
	private int m_PhysicalSize;		// The number of items in the array
	
	public QueueArrayBased(int size)
	{
		m_Array = (T[]) new Object[size];
		m_QueueFront = 0;
		m_InsertionOffset = 0;
		m_PhysicalSize = 0;
	}
	
	/**
	 * Adds a given item to the queue at the index of insertion. Resizes the array to size 2n if the array is full.
	 * @param data The given item to insert.
	 */
	public void enqueue(Object data) throws NullPointerException
	{
		if(data == null)
			throw new NullPointerException("The given data is null and cannot be inserted.");
		
		if(m_Array.length < m_PhysicalSize + 1)
			resizeArray();
		
		// Sends our insertion point back to the front of the array
		if(m_QueueFront + m_InsertionOffset > m_Array.length - 1)
			m_InsertionOffset = 0 - m_QueueFront;
		
		m_Array[m_QueueFront + m_InsertionOffset] = (T) data;
		
		m_InsertionOffset++;
		m_PhysicalSize++;
	}
	
	/**
	 * Removes the item at the front of the queue and updates the insertion point and index where the first item in the queue is located.
	 * @return The item at the front of the queue.
	 */
	public T dequeue() throws NullPointerException
	{
		if(m_PhysicalSize == 0)
			throw new NullPointerException("The queue is currently empty.");
		else
		{
			T dequeuedItem = m_Array[m_QueueFront];
			m_Array[m_QueueFront] = null;
			
			// Update variables to keep track of where to enqueue and dequeue from
			m_PhysicalSize--;
			m_InsertionOffset--;
			m_QueueFront++;
			
			if(m_QueueFront > m_Array.length - 1)
				m_QueueFront = 0;
			
			return dequeuedItem;
		}
	}
	
	/**
	 * Returns the item at the front of the queue. Will return null if the queue is empty.
	 * @return The item at the front of the queue.
	 */
	public T peek() throws NullPointerException
	{
		if(m_Array[m_QueueFront] == null)
			throw new NullPointerException("The queue is currently empty.");
			
		return m_Array[m_QueueFront];
	}
	
	/**
	 * Returns the number of items in the queue.
	 * @return The number of items in the queue.
	 */
	public int size()
	{
		return m_PhysicalSize;
	}
	
	/**
	 * Resizes the array to be size 2n.
	 */
	private void resizeArray()
	{
		T[] resizedArray = (T[]) new Object[m_Array.length * 2];
		
		for(int i = 0; i < m_Array.length; i++)
			resizedArray[i] = m_Array[i];
		
		m_Array = resizedArray;
	}
}
