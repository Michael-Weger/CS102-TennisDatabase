


package TennisDatabase;

import java.io.IOException;
import java.lang.String;

// Interface (package-private) providing the specifications for the TennisDatabase class.
interface TennisDatabaseInterface {

   // Desc.: Loads data from file following the format described in the specifications.
   // Output: Throws an unchecked (non-critical) exception if the loading is not fully successful.
   // 		  Throws a checked (critical) exception if the file is empty.
   public void readFromFile( String fileName ) throws TennisDatabaseRuntimeException, TennisDatabaseException, Exception;
   
   // Desc.: Writes all data from the database to a text file overwriting any existing data.
   // Output: Throws an unchecked (non-critical) exception if there was an issue exporting the database.
   //		  Throws a checked (critical) exception if the database is empty.
   public void writeToFile( String filename) throws TennisDatabaseRuntimeException, TennisDatabaseException, IOException;
   
   // Desc.: Prints all tennis players in the database to the console (sorted by id, alphabetically).
   // Output: Throws an unchecked (non-critical) exception if there are no players in the database.
   public void printAllPlayers() throws TennisDatabaseRuntimeException;
   
   // Desc.: Prints all tennis matches of input tennis player (id) to the console (sorted by date, most recent first).
   // Input: The id of the tennis player.
   // Output: Throws a checked (critical) exception if the tennis player (id) does not exists.
   //         Throws an uncheckedor (non-critical) exception if there are no tennis matches (but the player id exists).
   public void printMatchesOfPlayer( String playerId ) throws TennisDatabaseException, TennisDatabaseRuntimeException;
   
   // Desc.: Prints all tennis matches in the database to the console (sorted by date, most recent first).
   // Output: Throws an unchecked (non-critical) exception if there are no tennis matches in the database.
   public void printAllMatches() throws TennisDatabaseRuntimeException;
   
   // Desc.: Insert a tennis player into the database.
   // Input: All the data required for a tennis player.
   // Output: Throws a checked (critical) exception if player id is already in this container.
   public void insertPlayer( String player ) throws TennisDatabaseException;

   // Desc.: Insert a tennis match into the database.
   // Input: All the data required for a tennis match.
   // Output: Throws a checked (critical) exception if a player does not exist in the database.
   //         Throws a checked (critical) exception if the match score is not valid.
   public void insertMatch( String match ) throws TennisDatabaseException, Exception;
   
}


