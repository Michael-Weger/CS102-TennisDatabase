


package TennisDatabase;

import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisMatch;
import TennisDatabase.TennisPlayer;
import TennisDatabase.TennisPlayersContainerIterator;

// Interface (package-private) providing the specifications for the TennisPlayersContainer class.
interface TennisPlayersContainerInterface {

   // Desc.: Insert a tennis player into this container.
   // Input: A tennis player.
   // Output: Throws a checked (critical) exception if player id is already in this container.
   public void insertPlayer( TennisPlayer p ) throws TennisDatabaseException;
   
   // Desc.: Insert a tennis match into the lists of both tennis players of the input match.
   // Input: A tennis match.
   // Output: Throws a checked (critical) exception if the insertion is not fully successful.
   //		  Throws an unchecked (non-critical) exception if there are no players in this container.
   public void insertMatch( TennisMatch m ) throws TennisDatabaseException, Exception;
   
   // Desc.: Prints all tennis players to the console.
   // Output: Throws an unchecked (non-critical) exception if there are no players in this container.
   public void printAllPlayers() throws TennisDatabaseRuntimeException;
   
   // Desc.: Prints all tennis matches of input tennis player (id).
   // Input: The id of the tennis player.
   // Output: Throws a checked (critical) exception if the tennis player (id) does not exists.
   //         Throws an unchecked (non-critical) exception if there are no tennis matches in the list (but the player id exists).
   public void printMatchesOfPlayer( String playerId ) throws TennisDatabaseException;
   
   // Desc.: Deletes a given player from the tree.
   // Input: The id of the tennis player.
   // Output: Throws a checked (critical) exception if the tennis player (id) does not exist.
   //		  Throws an unchecked (non-critical) exception if there are no tennis players in the tree.
   public void removePlayer( String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException;
   
   // Desc.: Removes all players from the tree.
   // Output: Throws a checked (critical) exception if the container is already empty.
   public void clear() throws TennisDatabaseException;
   
   // Desc.: Creates a TennisPlayersContainerIterator object.
   // Output: The TennisPlayersContainerIterator.
   public TennisPlayersContainerIterator iterator();
   
   // Desc.: Exports all players from the tree (preorder) to a condensed string.
   // Output: The condensed string.
   // 		  Throws a checked (critical) exception if there are no tennis players in the tree.
   public String exportTennisPlayers() throws TennisDatabaseException;
   
}


