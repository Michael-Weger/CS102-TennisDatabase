package TennisDatabase;

interface QueueArrayBasedInterface<T> {
	
	// Desc.: Adds an item to the back of the queue.
	// Input: The generic datatype to add to the end of the queue.
	// Output: Throws a (Critical) NullPointerException if the input is null.
	public void enqueue(T item) throws NullPointerException;
	
	// Desc.: Removes an item from the front of the queue.
	// Output: The item removed from the front of the queue.
	// 		   Throws a (Critical) NullPointerException if the queue is empty.
	public T dequeue() throws NullPointerException;
	
	// Desc.: Returns the item at the front of the queue.
	// Output: The item at the front of the queue.
	//		   Throws a (Critical) NullPointerException if the queue is empty.
	public T peek() throws NullPointerException;
}
