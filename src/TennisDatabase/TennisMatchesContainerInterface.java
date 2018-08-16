


package TennisDatabase;

// Interface (package-private) providing the specifications for the TennisMatchesContainer class.
interface TennisMatchesContainerInterface {

   // Desc.: Insert a tennis match into this container.
   // Input: A tennis match.
   // Output: Throws a checked (critical) exception if the match is null.
   public void insertMatch( TennisMatch m ) throws TennisDatabaseException;
   
   // Desc.: Prints all tennis matches to the console.
   // Output: Throws an exception if there are no matches in this container.
   public void printAllMatches() throws TennisDatabaseRuntimeException;
   
   // Desc.: Deletes all matches of a given player from the ArrayList.
   // Input: The id of the tennis player in the match.
   public void removeAllMatchesOfPlayer( String playerId);
   
   // Desc.: Removes all matches from the ArrayList.
   public void clear();
   
   // Desc.: Exports all players from the ArrayList to a condensed string
   // Output: The condensed string
   public String exportTennisMatches();
   
}


