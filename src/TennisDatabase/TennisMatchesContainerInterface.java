


package TennisDatabase;

import java.lang.Object;

// Interface (package-private) providing the specifications for the TennisMatchesContainer class.
interface TennisMatchesContainerInterface {

   // Desc.: Insert a tennis match into this container.
   // Input: A tennis match.
   // Output: Throws a checked (critical) exception if the container is full.
   public void insertMatch( TennisMatch m ) throws TennisDatabaseException;
   
   // Desc.: Prints all tennis matches to the console.
   // Output: Throws an exception if there are no matches in this container.
   public void printAllMatches() throws TennisDatabaseRuntimeException;
   
}


