package TennisDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Manages tennis matches, tennis players, and their respective data structures
 * 
 * @author Michael Weger
 *
 */
public class TennisDatabase implements TennisDatabaseInterface {
	
	private TennisMatchesContainer m_MatchContainer;	// The match container which manipulates matches in the database
	private TennisPlayersContainer m_PlayerContainer;	// The player container which manipulates players in the database
	
	public TennisDatabase()
	{
		m_MatchContainer = new TennisMatchesContainer();
		m_PlayerContainer = new TennisPlayersContainer();
	}
	
	/**
	 * Loads the file from a string with which the program was started
	 * @param fileName the path at which the text file is located
	 */
	public void loadFromFile(String fileName) throws TennisDatabaseRuntimeException
	{

		Scanner fileScanner = null;
		File dataFile = new File(fileName);
		
		// Attempt to load the data file 
		if(!dataFile.exists())
		{
			throw new TennisDatabaseRuntimeException("File not found, , continuining without loading any preexisting data.");
		}
		else if(!dataFile.canRead() || !fileName.contains(".txt"))
		{
			throw new TennisDatabaseRuntimeException("File is of an invalid format, continuining without loading any preexisting data.");
		}
		else
		{
			try
			{
				fileScanner = new Scanner(dataFile);
			}
			catch(FileNotFoundException e)
			{
				throw new TennisDatabaseRuntimeException("File not found, , continuining without loading any preexisting data.");
			}
		}
		
		// Import all valid data from the file
		
		while(fileScanner.hasNextLine())
		{
			String importedData = fileScanner.nextLine();
			
			// Make sure the line is long enough to determine if its a player before continuing the validity test
			if(importedData.length() > 7 && importedData.substring(0, 7).equals("PLAYER/"))
			{
				try
				{
					this.insertPlayer(importedData, false);
				}
				catch(TennisDatabaseException e)
				{
					
				}
			}
		}
		
		// Resets the position of the file scanner to the top of the file by creating a new instance
		try 
		{
			fileScanner.close();
			fileScanner = new Scanner(dataFile);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		// Add matches now that players have been added to the database
		while(fileScanner.hasNext())
		{
			String importedData = fileScanner.nextLine();
			
			// Make sure the line is long enough to determine if its a match before continuing the validity test
			if(importedData.length() > 6 && importedData.substring(0, 6).equals("MATCH/"))
			{
				try
				{
					this.insertMatch(importedData, false);
				}
				catch(TennisDatabaseException e)
				{
					
				}
			}
		}
		
		fileScanner.close();
	}
	
	/**
	 * Adds a player to the database after validating the string
	 * @param p The string from which a player will be created
	 * @param userFeedback a boolean value which determines whether or not to provide system prints to a user.
	 */
	public void insertPlayer(String p, boolean userFeedback) throws TennisDatabaseException
	{
		String[] importedPlayer = p.split("/");
		
		// Test for all conditions where p would be an invalid player
		
		// Invalid format
		if(importedPlayer.length != 6)
		{
			throw new TennisDatabaseException("Invalid player format. Make sure there are no missing or extraneous fields.");
		}
		// Player ID is not 5 characters long
		else if(importedPlayer[1].length() != 5)
		{
			throw new TennisDatabaseException("Invalid player ID, player IDs must be 5 characters.");
		}
		// Player ID contains non alphanumeric characters
		else if(!isAlphaNumeric(importedPlayer[1]))
		{
			throw new TennisDatabaseException("Invalid player ID, player IDs must be alphanumeric.");
		}
		// Birth year contains non numeric characters
		else if(!isNumeric(importedPlayer[4]))
		{
			throw new TennisDatabaseException("A birth year must contain only numeric characters.");
		}
		// Birth year is less than 4 digits
		else if(importedPlayer[4].length() != 4)
		{
			throw new TennisDatabaseException("A valid birth year must have four digits.");
		}
		// Player is already in database
		else if(m_PlayerContainer.containsPlayer(importedPlayer[1]))
		{
			throw new TennisDatabaseException("Player already exists in database.");
		}
		// Validation successful
		else
		{	
			try 
			{
				m_PlayerContainer.insertPlayer(new TennisPlayer(p));
				if(userFeedback)
					System.out.println("Successfully added player!");
			} 
			catch (TennisDatabaseException e) 
			{
				if(userFeedback)
					System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Prints all players in the database
	 */
	public void printAllPlayers() throws TennisDatabaseRuntimeException
	{
		try
		{
			m_PlayerContainer.printAllPlayers();
		}
		catch(TennisDatabaseRuntimeException e)
		{
			throw e;
		}
	}
	
	/**
	 * Prints all matches of a single player from the TennisMatchesContainer sorted by most recent.
	 * 
	 * @param playerID The unique player ID of the player whose matches the user wants to print.
	 */
	public void printMatchesOfPlayer(String playerID)  throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		try 
		{
			m_PlayerContainer.printMatchesOfPlayer(playerID);
		} 
		catch (TennisDatabaseException e) 
		{
			throw e;
		}
	}
	
	/**
	 * Ensures the input string for a match is valid before adding it to the database.
	 * 
	 * @param m the match as a string PLAYER ID/PLAYER ID/DATE/TOURNAMENT/SET SCORE,SET SCORE...
	 * @param userFeedback a boolean value which determines whether or not to provide system prints to a user.
	 */
	public void insertMatch(String m, boolean userFeedback) throws TennisDatabaseException
	{
		String[] importedMatch = m.split("/");
		
		// Test for "all conditions" where this would be an invalid match
		
		// Invalid format
		if(importedMatch.length != 6)
		{
			throw new TennisDatabaseException("Invalid match format. Make sure there are no missing or extraneous fields.");
		}
		// Invalid unique player ID
		else if(importedMatch[1].length() != 5 || importedMatch[2].length() !=5)
		{
			throw new TennisDatabaseException("Invalid player ID, player IDs must be 5 characters.");
		}
		// Player ID isn't alphanumeric
		else if(!isAlphaNumeric(importedMatch[1]) || !isAlphaNumeric(importedMatch[2]))
		{
			throw new TennisDatabaseException("Invalid player ID, player IDs must contain only alphanumeric characters.");
		}
		// Identical unique player IDs
		else if(importedMatch[1].equals(importedMatch[2]))
		{
			throw new TennisDatabaseException("Invalid player IDs, a match cannot have two identical player IDs.");
		}
		// Players aren't in the database
		else if(!m_PlayerContainer.containsPlayer(importedMatch[1]) || !m_PlayerContainer.containsPlayer(importedMatch[2]))
		{
			throw new TennisDatabaseException("Invalid player IDs, a match must have both it's players already in the database.");
		}
		// Date isnt numeric
		if(!isNumeric(importedMatch[3]))
		{
			throw new TennisDatabaseException("Invalid date, a date must contain only numeric characters.");
		}
		// Improper date format
		else if(importedMatch[3].length() != 8)
		{
			throw new TennisDatabaseException("Invalid date, a date must have 8 digits: YYYYMMDD");
		}
		// Too small of a year
		else if(Integer.parseInt(importedMatch[3].substring(0, 4)) < 1100)
		{
			throw new TennisDatabaseException("Invalid date, tennis didn't exist before the 12th century. Tell a historian about your match!");
		}
		// Too large of a month
		else if(Integer.parseInt(importedMatch[3].substring(4, 6)) > 12)
		{
			throw new TennisDatabaseException("Invalid date, there are only 12 months in a year.");
		}
		// Too large of a date
		else if(Integer.parseInt(importedMatch[3].substring(6, 8)) > 31)
		{
			throw new TennisDatabaseException("Invalid date, there are no more than 31 days in a month");
		}
		// Too many or too few sets in a match
		else if(importedMatch[5].split(",").length < 2)
		{
			throw new TennisDatabaseException("Invalid set scores, a match must have at least two sets.");
		}
		// Actually adding the match
		else
		{
			TennisPlayer player1 = m_PlayerContainer.getPlayer(importedMatch[1]);
			TennisPlayer player2 = m_PlayerContainer.getPlayer(importedMatch[2]);
			
			TennisMatch match = new TennisMatch(m, player1, player2);
			m_MatchContainer.insertMatch(match);
			try 
			{
				m_PlayerContainer.insertMatch(match);
				
				if(userFeedback)
					System.out.println("Match successfully added.");
			} 
			catch (TennisDatabaseException e) 
			{
				if(userFeedback)
					System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Prints all matches from the TennisMatchesContainer sorted by most recent .
	 */
	public void printAllMatches() throws TennisDatabaseRuntimeException
	{
		try
		{
			m_MatchContainer.printAllMatches();
		}
		catch(TennisDatabaseRuntimeException e)
		{
			throw e;
		}
	}
	
	/**
	 * Determines if a given string contains only numeric characters
	 * @param s The string to examine
	 * @return The boolean result of the operation
	 */
	private boolean isNumeric(String s)
	{
		char[] charArr = s.toCharArray();
		
		for(char c : charArr)
			if(!Character.isDigit(c))
				return false;
		
		return true;
	}
	
	/**
	 * Determines if a given string contains only alphanumeric characters
	 * @param s The string to examine
	 * @return The boolean result of the operation
	 */
	private boolean isAlphaNumeric(String s)
	{
		char[] charArr = s.toCharArray();
				
		for(char c : charArr)
			if(!Character.isDigit(c) && !Character.isLetter(c))
				return false;
		
		return true;
	}
	
	/**
	 * A class which represents a circular doubly linked linked list to store and manipulate TennisPlayers
	 * @author Michael Weger
	 *
	 */
	private class TennisPlayersContainer implements TennisPlayersContainerInterface {
			
		private TennisPlayerContainerNode m_EntryPoint; // The start of the linked list
		private int m_NumNodes;							// Number of nodes in the linked list
		
		/**
		 * Constructor which sets default values
		 */
		public TennisPlayersContainer()
		{
			m_EntryPoint = null;	// When the linked list is empty the entry is null
			m_NumNodes = 0;			// When empty we have no nodes
		}

		/**
		 * Inserts a TennisPlayer to the linked list by their player ID
		 * @param p The player to insert
		 */
		@Override
		public void insertPlayer(TennisPlayer p) throws TennisDatabaseException {
			
			if(p == null)
				throw new TennisDatabaseException("Attempted to insert null player into the database.");
			
			// No nodes in list
			if(m_NumNodes == 0)
			{
				m_EntryPoint = new TennisPlayerContainerNode(p);
			}
			// Insert node at front of list
			else if(p.compareTo(m_EntryPoint.player) <= 0)
			{
				TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p, m_EntryPoint, m_EntryPoint.previous);
				m_EntryPoint.previous.next = newNode;
				m_EntryPoint.previous = newNode;
				m_EntryPoint = newNode;
			}
			else
			{
				// Search list for where to insert a new node
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					if(p.compareTo(loopNode.player) <= 0)
					{
						TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p, loopNode, loopNode.previous);
						loopNode.previous.next = newNode;
						loopNode.previous = newNode;
						m_NumNodes++;
						return;	
					}
				}
				
				// Node must be inserted at the end of the list
				TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p, m_EntryPoint, m_EntryPoint.previous);
				m_EntryPoint.previous.next = newNode;
				m_EntryPoint.previous = newNode;
			}
			m_NumNodes++;
		}
		
		/**
		 * Checks the PlayerContainer to see if it contains a given player by their ID
		 * @param playerId The ID of the player to search for
		 * @return The boolean result of the operation
		 */
		public boolean containsPlayer(String playerId)
		{
			// Empty list
			if(m_NumNodes == 0)
				return false;
			// Traverse list and check each player's ID to see if it matches
			else
			{
				if(m_EntryPoint.player.equals(playerId))
					return true;
				
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					if(loopNode.player.equals(playerId))
						return true;
				}	
			}
			return false;
				
		}
		
		/**
		 * Checks the PlayerContainer for a given player by their ID and returns it
		 * @param playerId The ID of the player to search for
		 * @return The player if found. If the PlayerContainer does not have the player this operation will return null.
		 */
		public TennisPlayer getPlayer(String playerId)
		{
			// Player not in list
			if(!containsPlayer(playerId))
				return null;
			else
			{
				if(m_EntryPoint.player.equals(playerId))
					return m_EntryPoint.player;
				
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					// Check if the player at the current node is the player we are looking for
					if(loopNode.player.equals(playerId))
						return loopNode.player;
				}
			}
			// Player is not in list
			return null;
		}

		/**
		 * Inserts a match to the lower level sorted linked list in each node.
		 * @param m The match to insert
		 */
		@Override
		public void insertMatch(TennisMatch m) throws TennisDatabaseException
		{
			
			boolean hasInserted = false;
			
			// Match is null
			if(m == null)
				throw new TennisDatabaseException("Attempted to insert null match into the database.");
			
			// No nodes in list
			if(m_NumNodes == 0)
				throw new TennisDatabaseException("There are currently no players in the database.");
			else
			{
				// Check the entrypoint to see if it has any players in the given match
				if(m_EntryPoint.player.equals(m.getPlayer1()) || m_EntryPoint.player.equals(m.getPlayer2()))
				{
					try 
					{
						m_EntryPoint.tennisMatches.insert(m);
						hasInserted = true;
					} 
					catch(Exception e) 
					{
						System.out.println(e.getMessage());
					}
				}
				
				// Search the remainder of the nodes
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					if(loopNode.player.equals(m.getPlayer1()) || loopNode.player.equals(m.getPlayer2()))
					{
						try 
						{
							loopNode.tennisMatches.insert(m);
							hasInserted = true;
						} 
						catch (Exception e) 
						{
							System.out.println(e.getMessage());
						}
					}
				}
				
				if(!hasInserted)
					throw new TennisDatabaseException("Unable to find a player in the database who has played in this match."); 
			}
		}

		/**
		 * Prints all players in the PlayerContainer
		 */
		@Override
		public void printAllPlayers() throws TennisDatabaseRuntimeException {
			
			// No players
			if(m_NumNodes == 0)
				throw new TennisDatabaseRuntimeException("There are currently no players in the database.");
			// Traverse the list and print all players
			else
			{
				System.out.println(m_EntryPoint.player);
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					System.out.println(loopNode.player);
				}
			}
			
		}

		/**
		 * Prints the matches of a specific player
		 * @param playerId The player ID of the player whose matches will be printed
		 */
		@Override
		public void printMatchesOfPlayer(String playerId) throws TennisDatabaseException {
			
			// No players
			if(m_NumNodes == 0)
				throw new TennisDatabaseException("There are currently no players in the database.");
			
			// No player of ID found
			else if(!this.containsPlayer(playerId))
				throw new TennisDatabaseException("There are currently no players of ID " + playerId + " in the database.");
			
			else
			{
				if(m_EntryPoint.player.equals(playerId))
				{
					try
					{
						m_EntryPoint.tennisMatches.print();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
				}
				
				// Traverse list and print players
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					if(loopNode.player.equals(playerId))
					{
						try
						{
							loopNode.tennisMatches.print();
						}
						catch(Exception e)
						{
							System.out.println(e.getMessage());
						}
					}
				}
			}
		}
		
		/**
		 * Nodes for the TennisPlayersContainer linked list
		 * 
		 * @author Michael Weger
		 *
		 */
		private class TennisPlayerContainerNode {
			
			public TennisPlayerContainerNode next;				// The next node
			public TennisPlayerContainerNode previous;			// The previous node
			public TennisPlayer player;							// The player which this node contains
			public SortedLinkedList<TennisMatch> tennisMatches;	// The linked list of matches in which this nodes player participated in
			
			/**
			 * Constructor
			 * @param player The player which this node contains
			 * @param next The next node in the linked list
			 * @param previous The previous node in the linked list
			 */
			public TennisPlayerContainerNode(TennisPlayer player, TennisPlayerContainerNode next, TennisPlayerContainerNode previous)
			{
				this.player = player;
				this.next = next;
				this.previous = previous;
				this.tennisMatches = new SortedLinkedList<TennisMatch>();
			}
			
			/**
			 * Constructor
			 * @param player The player which this node contains
			 */
			public TennisPlayerContainerNode(TennisPlayer player)
			{
				this.player = player;
				this.next = this; 		// As a circular linked list a node with no next or previous nodes must point to itself
				this.previous = this;
				this.tennisMatches = new SortedLinkedList<TennisMatch>();
			}
		}
	}
	
	/**
	 * Stores tennis matches in a dynamically sized array
	 * 
	 * @author Michael Weger
	 *
	 */
	private class TennisMatchesContainer implements TennisMatchesContainerInterface {
		
		// The array which will be manipulated
		private TennisMatch[] m_Array;
		
		// Tracks number of items in the array
		private int m_PhysicalSize;
		
		/**
		 * Constructor which sets the array to default allocated size 20
		 */
		public TennisMatchesContainer()
		{
			// Create an array and a variable to track how many items are in the array at any given time
			m_PhysicalSize = 0;
			m_Array = new TennisMatch[20];
		}
		
		/**
		 * Adds a tennis match to the database
		 * 
		 * @param match A valid tennis match after passing validity testing from the method in the TennisDatabase class
		 */
		public void insertMatch(TennisMatch match) throws TennisDatabaseException
		{	
			
			if(match == null)
				throw new TennisDatabaseException("Attempted to insert null match into the database.");
			
			int index = -1;
			
			for(int i = 0; i < m_PhysicalSize; i++)
			{
				// Our match date is greater or equal to the one at index i
				if(match.compareTo(m_Array[i]) >= 1)
				{
					index = i;
					break;
				}
			}
			
			// Resize the array if there is no room
			if(m_Array.length < m_PhysicalSize + 1)
				resizeArray();
			
			// No index was found its the new last item
			if(index == -1)
				m_Array[m_PhysicalSize] = match;
			// Otherwise shift the array to the right at the index of insertion
			else
				rightShift(index, match);
			
			// Increment our array size variable since we have added another match to the array
			m_PhysicalSize++;
		}
		
		/**
		 * Prints all matches currently in the database
		 */
		public void printAllMatches() throws TennisDatabaseRuntimeException
		{
			// Provide feedback to the user if there aren't any matches
			if(m_PhysicalSize == 0)
			{
				throw new TennisDatabaseRuntimeException("There are currently no matches in the database.");
			}
			// Print all matches
			for(TennisMatch m : m_Array)
			{
				if(m != null)
					m.print();
			}
		}
		
		/**
		 * Performs a right shift on the array for a match insertion operation
		 * @param indexOfInsertion The index at which to insert the match
		 * @param match The match to insert
		 */
		private void rightShift(int indexOfInsertion, TennisMatch match)
		{
			// Shift all items right of the index of insertion one index to the right
			for(int i = m_PhysicalSize; i > indexOfInsertion; i--)
			{
				m_Array[i] = m_Array[i-1];
			}
			
			// Insert the new item
			m_Array[indexOfInsertion] = match;
		}
		
		/**
		 * Allocates twice the amount of memory for the array
		 */
		private void resizeArray()
		{
			// Create a new array with size n+1 to fit the new item
			TennisMatch[] resizedArray = new TennisMatch[m_Array.length * 2];
			
			// Move all items from the previous array to the new array
			for(int i = 0; i < m_PhysicalSize; i++)
			{
				resizedArray[i] = m_Array[i];
			}
			
			// Set the member variable to the new array for use in other functions
			m_Array = resizedArray;
		}
	}
}