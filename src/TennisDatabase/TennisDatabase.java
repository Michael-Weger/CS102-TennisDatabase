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
	
	private TennisMatchesContainer m_MatchContainer;
	private TennisPlayersContainer m_PlayerContainer;
	
	public TennisDatabase()
	{
		m_MatchContainer = new TennisMatchesContainer();
		m_PlayerContainer = new TennisPlayersContainer();
	}
	
	/**
	 * Loads the file from a string with which the program was started
	 * @param fileName the path at which the text file is located
	 */
	public void loadFromFile(String fileName)
	{

		Scanner fileScanner = null;
		File dataFile = new File(fileName);
		
		// Attempt to load the data file 
		if(!dataFile.exists())
		{
			System.out.println("File not found, terminating program.");
			System.exit(1);
		}
		else if(!dataFile.canRead() || !fileName.contains(".txt"))
		{
			System.out.println("File is of an invalid format, terminating program.");
			System.exit(1);
		}
		else
		{
			try
			{
				fileScanner = new Scanner(dataFile);
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File not found, terminating program.");
				System.out.println("");
				System.exit(1);
			}
		}
		
		// Import all valid data from the file
		
		while(fileScanner.hasNextLine())
		{
			String importedData = fileScanner.nextLine();
			
			// Make sure the line is long enough to determine if its a match or a player before continuing the validity test
			if(importedData.length() > 7)
			{
				// Add only players to the database first
				if(importedData.substring(0, 7).equals("PLAYER/"))
				{
					System.out.println("Adding player...");
					this.insertPlayer(importedData, true);
				}
			}
		}
		
		// Resets the position of the file scanner to the top of the file by creating a new instance
		try 
		{
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
			
			if(importedData.length() > 6)
				if(importedData.substring(0, 6).equals("MATCH/"))
					this.insertMatch(importedData, true);
		}
	}
	
	/**
	 * Adds a player to the database after validating the string
	 * @param p The string from which a player will be created
	 * @param userFeedback a boolean value which determines whether or not to provide system prints to a user.
	 */
	public void insertPlayer(String p, boolean userFeedback)
	{
		String[] importedPlayer = p.split("/");
		
		// Test for all conditions where p would be an invalid player
		
		// Invalid format
		if(importedPlayer.length != 6)
		{
			if(userFeedback)
				System.out.println("Invalid player format. Make sure there are no missing or extraneous fields.");
				return;
		}
		else if(importedPlayer[1].length() != 5)
		{
			if(userFeedback)
				System.out.println("Invalid player ID, player IDs must be 5 digits and contain only alphanumeric characters.");
			return;
		}
		else if(importedPlayer[4].length() != 4)
		{
			if(userFeedback)
				System.out.println("A valid birth year must have four digits.");
			return;
		}
		// Player is already in database
		else if(m_PlayerContainer.containsPlayer(importedPlayer[1]))
		{
			if(userFeedback)
				System.out.println("Player already exists in database.");
			return;
		}
		else
		{
			if(userFeedback)
				System.out.println("Successfully validated player!");
			
			try 
			{
				m_PlayerContainer.insertPlayer(new TennisPlayer(p));
			} 
			catch (TennisDatabaseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prints all players in the database
	 */
	public void printAllPlayers()
	{
		m_PlayerContainer.printAllPlayers();
	}
	
	/**
	 * Prints all matches of a single player from the TennisMatchesContainer sorted by most recent.
	 * 
	 * @param playerID The unique player ID of the player whose matches the user wants to print.
	 */
	public void printMatchesOfPlayer(String playerID)
	{
		if(m_PlayerContainer.containsPlayer(playerID))
			try {
				m_PlayerContainer.printMatchesOfPlayer(playerID);
			} catch (TennisDatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * Ensures the input string for a match is valid before adding it to the database.
	 * 
	 * @param m the match as a string PLAYER ID/PLAYER ID/DATE/TOURNAMENT/SET SCORE,SET SCORE...
	 * @param userFeedback a boolean value which determines whether or not to provide system prints to a user.
	 */
	public void insertMatch(String m, boolean userFeedback)
	{
		String[] importedMatch = m.split("/");
		
		// Test for "all conditions" where this would be an invalid match
		
		// Invalid format
		if(importedMatch.length != 6)
		{
			if(userFeedback)
				System.out.println("Invalid match format. Make sure there are no missing or extraneous fields.");
			return;
		}
		// Invalid unique player ID
		else if(importedMatch[1].length() != 5 || importedMatch[2].length() !=5)
		{
			if(userFeedback)
				System.out.println("Invalid player ID, player IDs must be 5 digits and contain only alphanumeric characters.");
			return;
		}
		// Identical unique player IDs
		else if(importedMatch[1].equals(importedMatch[2]))
		{
			if(userFeedback)
				System.out.println("Invalid player IDs, a match cannot have two identical player IDs.");
			return;
		}
		// Players aren't in the database
		else if(!m_PlayerContainer.containsPlayer(importedMatch[1]) || !m_PlayerContainer.containsPlayer(importedMatch[2]))
		{
			System.out.println(m_PlayerContainer.containsPlayer(importedMatch[1]));
			System.out.println(m_PlayerContainer.containsPlayer(importedMatch[2]));
			if(userFeedback)
				System.out.println("Invalid player IDs, a match must have both it's players already in the database.");
			return;
		}
		// Improper date format
		else if(importedMatch[3].length() != 8)
		{
			if(userFeedback)
				System.out.println("Invalid date, a date must have 8 digits: YYYYMMDD");
			return;
		}
		// Too small of a year
		else if(Integer.parseInt(importedMatch[3].substring(0, 4)) < 1100)
		{
			if(userFeedback)
				System.out.println("Invalid date, tennis didn't exist before the 12th century. Tell a historian about your match!");
			return;
		}
		// Too large of a month
		else if(Integer.parseInt(importedMatch[3].substring(4, 6)) > 12)
		{
			if(userFeedback)
				System.out.println("Invalid date, there are only 12 months in a year.");
			return;
		}
		// Too large of a date
		else if(Integer.parseInt(importedMatch[3].substring(6, 8)) > 31)
		{
			if(userFeedback)
				System.out.println("Invalid date, there are no more than 31 days in a month");
			return;
		}
		// Too many or too few sets in a match
		else if(importedMatch[5].split(",").length < 2)
		{
			if(userFeedback)
				System.out.println("Invalid set scores, a match must have at least two sets.");
			return;
		}
		// Actually adding the match
		else
		{
			TennisPlayer player1 = m_PlayerContainer.getPlayer(importedMatch[1]);
			TennisPlayer player2 = m_PlayerContainer.getPlayer(importedMatch[2]);
			
			TennisMatch match = new TennisMatch(m, player1, player2);
			m_MatchContainer.insertMatch(match);
			try {
				m_PlayerContainer.insertMatch(match);
			} catch (TennisDatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(userFeedback)
				System.out.println("Match successfully added.");
		}
	}
	
	/**
	 * Prints all matches from the TennisMatchesContainer sorted by most recent .
	 */
	public void printAllMatches()
	{
		m_MatchContainer.printAllMatches();
	}
	
	private class TennisPlayersContainer implements TennisPlayersContainerInterface {
			
		private TennisPlayerContainerNode m_EntryPoint; // The start of the linked list
		private int m_NumNodes;							// Number of nodes in the linked list
		
		/**
		 * Constructor which sets default values
		 */
		public TennisPlayersContainer()
		{
			m_EntryPoint = null;
			m_NumNodes = 0;
		}

		//TODO deletes some nodes
		@Override
		public void insertPlayer(TennisPlayer p) throws TennisDatabaseException {
			
			// No nodes in list
			if(m_NumNodes == 0)
			{
				m_EntryPoint = new TennisPlayerContainerNode(p);
				System.out.println(m_EntryPoint.player.getPlayerID());
			}
			// Insert node at front of list
			else if(p.compareTo(m_EntryPoint.player) <= 0)
			{
				TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p, m_EntryPoint, m_EntryPoint.previous);
				m_EntryPoint.next = newNode;
				m_EntryPoint.previous = newNode;
				m_EntryPoint = newNode;
				System.out.println(m_EntryPoint.player.getPlayerID());
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
						System.out.println("Successfully added player to the database.");
						System.out.println(newNode.player.getPlayerID());
						return;	
					}
				}
				
				// Node must be inserted at the end of the list
				TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p, m_EntryPoint, m_EntryPoint.previous);
				m_EntryPoint.previous.next = newNode;
				m_EntryPoint.previous = newNode;
				System.out.println(newNode.player.getPlayerID());
			}
			m_NumNodes++;
		}
		
		public boolean containsPlayer(String playerId)
		{
			if(m_NumNodes == 0)
				return false;
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

		@Override
		public void insertMatch(TennisMatch m) throws TennisDatabaseException 
		{
			if(m_NumNodes == 0)
				System.out.println("There are currently no players in the database.");
			else
			{
				if(m_EntryPoint.player.equals(m.getPlayer1()) || m_EntryPoint.player.equals(m.getPlayer2()))
				{
					try 
					{
						m_EntryPoint.tennisMatches.insert(m);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					if(loopNode.player.equals(m.getPlayer1()) || loopNode.player.equals(m.getPlayer2()))
					{
						try 
						{
							loopNode.tennisMatches.insert(m);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		}

		@Override
		public void printAllPlayers() throws TennisDatabaseRuntimeException {
			
			if(m_NumNodes == 0)
				System.out.println("There are currently no players in the database.");
			else
			{
				System.out.println(m_EntryPoint.player);
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					System.out.println(loopNode.player);
				}
			}
			
		}

		@Override
		public void printMatchesOfPlayer(String playerId) throws TennisDatabaseException {
			
			if(m_NumNodes == 0)
				System.out.println("There are currently no players in the database.");
			else
			{
				for(TennisPlayerContainerNode loopNode = m_EntryPoint.next; !loopNode.equals(m_EntryPoint); loopNode = loopNode.next)
				{
					if(loopNode.player.equals(playerId))
					{
						loopNode.tennisMatches.print();
					}
				}
			}
		}
		
		private class TennisPlayerContainerNode {
			
			public TennisPlayerContainerNode next;
			public TennisPlayerContainerNode previous;
			public TennisPlayer player;
			public SortedLinkedList<TennisMatch> tennisMatches;
			
			public TennisPlayerContainerNode(TennisPlayer player, TennisPlayerContainerNode next, TennisPlayerContainerNode previous)
			{
				this.player = player;
				this.next = next;
				this.previous = previous;
				this.tennisMatches = new SortedLinkedList<TennisMatch>();
			}
			public TennisPlayerContainerNode(TennisPlayer player)
			{
				this.player = player;
				this.next = this;
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
		public void insertMatch(TennisMatch match)
		{	
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
			if(m_Array.length < m_PhysicalSize)
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
		public void printAllMatches()
		{
			// Provide feedback to the user if there aren't any matches
			if(m_PhysicalSize == 0)
			{
				System.out.println("There are currently no matches in the database.");
				return;
			}
			// Print all matches
			for(TennisMatch m : m_Array)
			{
				if(m != null)
					System.out.println(m);
			}
		}
		
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
		 * Allocates memory for an additional index in the array
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