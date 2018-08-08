


package TennisDatabase;

import java.lang.Comparable;
import java.lang.Exception;
import java.lang.RuntimeException;

// Interface (package-private) providing the specifications for the SortedLinkedList generic class.
interface SortedLinkedListInterface< T extends Comparable<T> > {

   // Desc.: Insert an object of type T this container.
   // Input: An object (reference) of type T.
   // Output: Throws a checked (critical) exception if the insertion is not successful.
   public void insert( T m ) throws Exception;
   
   // Desc.: Prints all the items in this container to the console.
   // Output: Throws an unchecked (non-critical) exception if there are no items in this container.
   public void print() throws RuntimeException;
   
}


