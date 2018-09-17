package TennisDatabase;

import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisMatch;
import TennisDatabase.TennisPlayer;
import TennisDatabase.TennisPlayersContainerIterator;

/**
 * A class which represents a binary search tree.
 * @author Michael Weger
 *
 */
class TennisPlayersContainer implements TennisPlayersContainerInterface {
		
	private TennisPlayersContainerNode m_Root; 		// The start of the binary search tree
	
	/**
	 * Constructor which sets default values.
	 */
	public TennisPlayersContainer()
	{
		m_Root = null;	// When the linked list is empty the entry is null
	}
	
	/**
	 * Inserts a specified player into the BST based on their unique ID.
	 * @param p The specified player to insert.
	 */
	public void insertPlayer(TennisPlayer p) throws TennisDatabaseException 
	{
		if(m_Root == null)
			m_Root = new TennisPlayersContainerNode(p);
		
		else if(this.containsPlayer(p.getPlayerID()))
			throw new TennisDatabaseException("The specified player already exists in the database");
		
		else
			insertPlayerRecursive(this.m_Root, p);
		
	}
	
	/**
	 * Recursive method which uses a binary search to find the proper place to insert the new node then 
	 * inserts the player into the BST by their lexicographical value.
	 * @param currentRoot The current root of the recursive function.
	 * @param p The player to insert.
	 * @return The current root of the recursive function in order to update the previous root.
	 * @throws TennisDatabaseRuntimeException
	 */
	private TennisPlayersContainerNode insertPlayerRecursive(TennisPlayersContainerNode currentRoot, TennisPlayer p) throws TennisDatabaseRuntimeException
	{
		if(currentRoot == null)
			return new TennisPlayersContainerNode(p);
		
		int comparison = p.compareTo(currentRoot.data.player);
		
		// The specified player is lexicographically less than the player at the node
		if(comparison < 0)
		{
			currentRoot.leftChild = insertPlayerRecursive(currentRoot.leftChild, p);
			return currentRoot;
		}
		// The specified player is lexicographically greater than the player at the node
		else if(comparison > 0)
		{
			currentRoot.rightChild = insertPlayerRecursive(currentRoot.rightChild, p);
			return currentRoot;
		}
		// Value is already in the tree
		else
			throw new TennisDatabaseRuntimeException("The specified player already exists in the database.");
	}

	/**
	 * Adds a specified tennis match to the SortedLinkedList of all participating players.
	 * @param m The specified tennis match.
	 * @exception TennisDatabaseException
	 * @exception Exception
	 */
	public void insertMatch(TennisMatch m) throws TennisDatabaseException, Exception
	{
		if(m_Root == null)
			throw new TennisDatabaseException("No players are currently in the database.");
		else
		{
			try
			{
				insertMatchRecursive(m_Root, m, 0);
			}
			catch(Exception e)
			{
				throw e;
			}
		}
	}
	
	/**
	 * Supporting recursive method which searches for the players then adds the matches.
	 * @param currentRoot The current root of the recursive function.
	 * @param m The specified match.
	 * @param result How many times a match has been added. If result == 2 then all participating players have had their matches added.
	 * @throws Exception
	 */
	private void insertMatchRecursive(TennisPlayersContainerNode currentRoot, TennisMatch m, int result) throws Exception
	{
		if(result == 2)
			return;
		
		else if(currentRoot != null)
		{
			insertMatchRecursive(currentRoot.leftChild, m, result);
			
			String playerID = currentRoot.data.player.getPlayerID();
			
			if(playerID.equals(m.getPlayer1().getPlayerID()) || playerID.equals(m.getPlayer2().getPlayerID()))
			{
				try 
				{
					currentRoot.data.tennisMatches.insert(m);
				} 
				catch (Exception e) 
				{
					throw e;
				}
				
				result++;
			}
			
			insertMatchRecursive(currentRoot.rightChild, m, result);
		}
	}

	/**
	 * Prints all players currently in the BST
	 * @exception TennisDatabaseRuntimeException
	 */
	public void printAllPlayers() throws TennisDatabaseRuntimeException
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("No players are currently in the database.");
		
		TennisPlayersContainerIterator iterator = this.iterator();
		iterator.setInorder();
		
		// Print a blank line for a clean look in the UI.
		// This is printed here to avoid a blank line when an exception is thrown.
		System.out.println("");
		
		while(iterator.hasNext())
		{
			iterator.next().player.print();
		}
	}

	/**
	 * Prints all matches of a specified player.
	 * @param playerId The ID of the specified player.
	 * @exception TennisDatabaseException
	 */
	public void printMatchesOfPlayer(String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("There are currently no players in the database.");
		
		else
		{
			try
			{
				try
				{
					// Blank line is printed here to avoid a blank line when an exception is thrown.
					System.out.println("");
					binarySearch(m_Root, playerId).data.tennisMatches.print();
				}
				catch(RuntimeException e)
				{
					throw new TennisDatabaseRuntimeException("The specified player exists but has not played any matches in the database.");
				}
			}
			catch(TennisDatabaseRuntimeException e)
			{
				throw new TennisDatabaseRuntimeException("The specified player ID is invalid or has no matches in the database.");
			}
			catch(TennisDatabaseException e)
			{
				throw new TennisDatabaseException("The specified player does not exist in the database.");
			}
		}
	}
	
	/**
	 * Removes a list of matches from the database.
	 * @param matches The list of matches to remove
	 * @throws TennisDatabaseRuntimeException
	 * @throws TennisDatabaseException
	 */
	private void removeMatches(SortedLinkedList<TennisMatch> matches) throws TennisDatabaseRuntimeException, TennisDatabaseException
	{
		for(TennisMatch m : matches.getAllAsList())
		{
			try 
			{
				String player1Id = m.getPlayer1().getPlayerID();
				String player2Id = m.getPlayer2().getPlayerID();
				
				try 
				{
					this.binarySearch(m_Root, player1Id).data.tennisMatches.removeItem(m);
					this.binarySearch(m_Root, player2Id).data.tennisMatches.removeItem(m);
				} 
				catch (TennisDatabaseRuntimeException | TennisDatabaseException e) 
				{
					throw e;
				}
			} 
			catch (TennisDatabaseException | TennisDatabaseRuntimeException e) 
			{
				throw e;
			}
		}
		
	}
	
	/**
	 * Removes a specified player from the BST.
	 * @param playerId The specified player.
	 * @exception TennisDatabaseException
	 * @exception TennisDatabaseRuntimeException
	 */
	public void removePlayer(String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("There are currently no players in the database.");
		else
		{
			try
			{
				m_Root = removePlayerRecursive(m_Root, playerId);
			}
			catch(TennisDatabaseRuntimeException e)
			{
				throw e;
			}
		}
	}
	
	/**
	 * Supporting recursive function which uses a binary search to locate the specified player then removes it from
	 * the BST replacing it with the lowest lexicographically valued node from the right subtree.
	 * @param currentRoot The current root of the recursive function.
	 * @param playerId The ID of the specified player.
	 * @return The current root of the recursive function in order to update the previous root.
	 * @throws TennisDatabaseRuntimeException
	 */
	private TennisPlayersContainerNode removePlayerRecursive(TennisPlayersContainerNode currentRoot, String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		if(currentRoot == null)
			throw new TennisDatabaseRuntimeException("The specified player could not be found in the database.");
		
		int comparison = playerId.compareTo(currentRoot.data.player.getPlayerID());
		
		// The specified player is lexicographically less than the player at the node
		if(comparison < 0)
		{
			currentRoot.leftChild = removePlayerRecursive(currentRoot.leftChild, playerId);
			return currentRoot;
		}
		// The specified player is lexicographically greater than the player at the node
		else if(comparison > 0)
		{
			currentRoot.rightChild = removePlayerRecursive(currentRoot.rightChild, playerId);
			return currentRoot;
		}
		// This is the specified player
		else
		{
			// No children
			if(currentRoot.leftChild == null && currentRoot.rightChild == null)
			{
				removeMatches(currentRoot.data.tennisMatches);
				currentRoot = null;
			}
			// No right children
			else if(currentRoot.rightChild == null)
			{
				removeMatches(currentRoot.data.tennisMatches);
				currentRoot = currentRoot.leftChild;
			}
			// No left children
			else if(currentRoot.leftChild == null)
			{
				removeMatches(currentRoot.data.tennisMatches);
				currentRoot = currentRoot.rightChild;
			}
			// Restructure the tree
			else
			{
				removeMatches(currentRoot.data.tennisMatches);
				
				// Find the lowest value in the right subtree
				currentRoot.data = findLowestValue(currentRoot.rightChild);
				
				removePlayerRecursive(currentRoot.rightChild, currentRoot.data.player.getPlayerID());
			}
			
			return currentRoot;
		}
	}
	
	/**
	 * Locates and returns the lowest value function in a specified BST.
	 * @param currentRoot The root of the BST.
	 * @return The NodeData which was determined to be lowest in the BST.
	 */
	private NodeData findLowestValue(TennisPlayersContainerNode root)
	{
		NodeData minData = null;
		
		while(root != null)
		{
			minData = root.data;
			root = root.leftChild;
		}
		
		return minData;
	}
	
	/**
	 * Determines if a specified player is in the BST.
	 * @param playerId The ID of the specified player.
	 * @return A boolean indicating if the player is in the BST.
	 */
	public boolean containsPlayer(String playerId)
	{
		if(m_Root == null)
			return false;
		else
		{
			try
			{
				return binarySearch(m_Root, playerId).data.player != null;
			}
			catch(TennisDatabaseRuntimeException e)
			{
				return false;
			}
			catch(TennisDatabaseException e)
			{
				return false;
			}
		}
	}
	
	/**
	 * Returns a specified tennis player.
	 * @param playerId The ID of the specified tennis player.
	 * @return The tennis player which was searched for.
	 * @exception TennisDatabaseException
	 */
	public TennisPlayer getPlayer(String playerId) throws TennisDatabaseException
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("There are currently no players in the database.");
		
		else
		{
			try
			{
				return binarySearch(m_Root, playerId).data.player;
			}
			catch(TennisDatabaseException e)
			{
				throw new TennisDatabaseException("The specified player does not exist in the database.");
			}
			catch(TennisDatabaseRuntimeException e)
			{
				throw new TennisDatabaseRuntimeException("The specified ID is invalid.");
			}
		}
	}
	
	/**
	 * Exports all tennis players in the tree (preorder) as a condensed string.
	 * @return The condensed string.
	 */
	public String exportTennisPlayers() throws TennisDatabaseException
	{
		TennisPlayersContainerIterator iterator = this.iterator();
		iterator.setPreorder();
		
		String appendString = "";
		TennisPlayer p;
		
		while(iterator.hasNext())
		{
			p = iterator.next().player;
				
			appendString += "PLAYER/" + p.getPlayerID() + "/" + p.getFirstName() + "/" + p.getLastName() + "/" 
					+ p.getBirthYear() + "/" + p.getCountry() + System.lineSeparator();
		}
		
		return appendString;
	}
	
	/**
	 * Clears all tennis players from the BST.
	 */
	public void clear() throws TennisDatabaseException
	{
		if(m_Root == null)
			throw new TennisDatabaseException("The database is already empty.");
		
		else
			m_Root = null;
	}
	
	/**
	 * Creates an iterator for this container capable of inorder or reverse inorder traversal.
	 * @return The iterator.
	 */
	public TennisPlayersContainerIterator iterator()
	{
		return new TennisPlayersContainerIterator(m_Root);
	}
	
	/**
	 * A binary search to reduce code reuse throughout the class.
	 * @param key The key which is being searched for.
	 * @return The node which was being searched for.
	 * @exception TennisDatabaseException The specified key does not exist in the tree.
	 * @exception TennisDatabaseRuntimeException The specified key is null.
	 */
	private TennisPlayersContainerNode binarySearch(TennisPlayersContainerNode currentRoot, String key) throws TennisDatabaseRuntimeException, TennisDatabaseException
	{
		if(currentRoot == null)
			throw new TennisDatabaseException("The specified key does not exist in the tree.");
		
		else if(key == null)
			throw new TennisDatabaseRuntimeException("Invalid key.");
		
		int comparison = key.compareTo(currentRoot.data.player.getPlayerID());
		
		// The specified key is lexicographically less than the key at the node
		if(comparison < 0)
			return binarySearch(currentRoot.leftChild, key);
		// The specified key is lexicographically greater than the key at the node
		else if(comparison > 0)
			return binarySearch(currentRoot.rightChild, key);
		// The current node is the key we are searching for
		else
			return currentRoot;
	}
	
	/**
	 * Nodes for the TennisPlayersContainer linked list
	 * 
	 * @author Michael Weger
	 *
	 */
	class TennisPlayersContainerNode {
		
		public TennisPlayersContainerNode rightChild;	// The right node
		public TennisPlayersContainerNode leftChild;		// The left node
		public NodeData data;							// The data of the node
		
		/**
		 * Constructor
		 * @param player The player which this node contains
		 */
		public TennisPlayersContainerNode(TennisPlayer player)
		{
			this.data = new NodeData(player, new SortedLinkedList<TennisMatch>());
			this.rightChild = null;
			this.leftChild = null;
		}
	}
	
	/**
	 * Encapsulates data for a TennisPlayerContainerNode
	 * 
	 * @author Michael Weger
	 *
	 */
	class NodeData {
		
		public TennisPlayer player;
		public SortedLinkedList<TennisMatch> tennisMatches;
		
		/**
		 * Constructor
		 * @param p The player
		 * @param m The matches
		 */
		
		public NodeData(TennisPlayer player, SortedLinkedList<TennisMatch> matches)
		{
			this.player = player;
			this.tennisMatches = matches;
		}
	}
}
