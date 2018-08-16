package TennisDatabase;

import TennisDatabase.QueueArrayBased;
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
	 * Inserts a given player into the BST based on their unique ID.
	 * @param p The given player to insert.
	 */
	public void insertPlayer(TennisPlayer p) throws TennisDatabaseException 
	{
		if(m_Root == null)
			m_Root = new TennisPlayersContainerNode(p);
		else if(this.containsPlayer(p.getPlayerID()))
			throw new TennisDatabaseException("The given player already exists in the database");
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
		{
			return new TennisPlayersContainerNode(p);
		}
		
		int comparison = p.compareTo(currentRoot.data.player);
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
		{
			currentRoot.leftChild = insertPlayerRecursive(currentRoot.leftChild, p);
			return currentRoot;
		}
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
		{
			currentRoot.rightChild = insertPlayerRecursive(currentRoot.rightChild, p);
			return currentRoot;
		}
		// Value is already in the tree
		else
			throw new TennisDatabaseRuntimeException("The given player already exists in the database.");
	}

	/**
	 * Adds a given tennis match to the sortedlinkedlists of all participating players.
	 * @param m The given tennis match.
	 * @exception TennisDatabaseException
	 * @exception Exception
	 */
	public void insertMatch(TennisMatch m) throws TennisDatabaseException, Exception
	{
		if(m_Root == null)
			throw new TennisDatabaseException("No players are currently in the database.");
		
		try
		{
			insertMatchRecursive(m_Root, m, 0);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * Supporting recursive method which searches for the players then adds the matches.
	 * @param currentRoot The current root of the recursive function.
	 * @param m The given match.
	 * @param result How many times a match has been added. If result == 2 then all participating players have had their matches added.
	 * @throws Exception
	 */
	private void insertMatchRecursive(TennisPlayersContainerNode currentRoot, TennisMatch m, int result) throws Exception
	{
		if(result == 2)
			return;
		
		if(currentRoot != null)
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
		
		printAllPlayersRecursive(m_Root);
		
	}
	
	/**
	 * Supporting recursive method which traverses the tree to print players.
	 * @param currentRoot The current root of the recursive function.
	 */
	private void printAllPlayersRecursive(TennisPlayersContainerNode currentRoot)
	{
		if(currentRoot != null)
		{
			printAllPlayersRecursive(currentRoot.leftChild);
			currentRoot.data.player.print();
			printAllPlayersRecursive(currentRoot.rightChild);
		}
	}

	/**
	 * Prints all matches of a given player.
	 * @param playerId The ID of the given player.
	 * @exception TennisDatabaseException
	 */
	public void printMatchesOfPlayer(String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("No players are currently in the database.");
		
		try
		{
			printMatchesOfPlayerRecursive(m_Root, playerId);
		}
		catch (TennisDatabaseException e)
		{
			throw e;
		}
	}
	
	/**
	 * Supporting recursive method which uses a binary search to locate the given player then print their matches.
	 * @param currentRoot The current root of the recursive function.
	 * @param playerId The ID of the given player.
	 * @throws TennisDatabaseException
	 */
	private void printMatchesOfPlayerRecursive(TennisPlayersContainerNode currentRoot, String playerId) throws TennisDatabaseException
	{
		if(currentRoot == null)
			throw new TennisDatabaseException("The specified player does not exist in the database.");
		
		int comparison = playerId.compareTo(currentRoot.data.player.getPlayerID());
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
			printMatchesOfPlayerRecursive(currentRoot.leftChild, playerId);
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
			printMatchesOfPlayerRecursive(currentRoot.rightChild, playerId);
		// The current node is the player we are searching for
		else
			currentRoot.data.tennisMatches.print();
	}
	
	/**
	 * Removes a given player from the BST.
	 * @param playerId The given player.
	 * @exception TennisDatabaseException
	 * @exception TennisDatabaseRuntimeException
	 */
	public void removePlayer(String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("There are currently no players in the database.");
		
		try
		{
			m_Root = removePlayerRecursive(m_Root, playerId);
		}
		catch(TennisDatabaseRuntimeException e)
		{
			throw e;
		}
	}
	
	/**
	 * Supporting recursive function which uses a binary search to locate the given player then removes it from
	 * the BST replacing it with the lowest lexicographically valued node from the right subtree.
	 * @param currentRoot The current root of the recursive function.
	 * @param playerId The ID of the given player.
	 * @return The current root of the recursive function in order to update the previous root.
	 * @throws TennisDatabaseRuntimeException
	 */
	private TennisPlayersContainerNode removePlayerRecursive(TennisPlayersContainerNode currentRoot, String playerId) throws TennisDatabaseRuntimeException
	{
		if(currentRoot == null)
			throw new TennisDatabaseRuntimeException("The specified player could not be found in the database.");
		
		int comparison = playerId.compareTo(currentRoot.data.player.getPlayerID());
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
		{
			currentRoot.leftChild = removePlayerRecursive(currentRoot.leftChild, playerId);
			return currentRoot;
		}
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
		{
			currentRoot.rightChild = removePlayerRecursive(currentRoot.rightChild, playerId);
			return currentRoot;
		}
		// This is the given player
		else
		{
			// No children
			if(currentRoot.leftChild == null && currentRoot.rightChild == null)
			{
				currentRoot = null;
			}
			// No right children
			else if(currentRoot.rightChild == null)
			{
				currentRoot = currentRoot.leftChild;
			}
			// No left children
			else if(currentRoot.leftChild == null)
			{
				currentRoot = currentRoot.rightChild;
			}
			// Restructure the tree
			else
			{
				// Find the lowest value in the right subtree
				currentRoot.data = findLowestValue(currentRoot.rightChild);
				
				removePlayerRecursive(currentRoot.rightChild, currentRoot.data.player.getPlayerID());
			}
			
			return currentRoot;
		}
	}
	
	/**
	 * Locates and returns the lowest value function in a given BST.
	 * @param currentRoot The root of the BST.
	 * @return The nodedata which was determined to be lowest in the BST.
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
	 * Determines if a given player is in the BST.
	 * @param playerId The ID of the given player.
	 * @return A boolean indicating if the player is in the BST.
	 */
	public boolean containsPlayer(String playerId)
	{
		if(m_Root == null)
			return false;
		else
		{
			return containsPlayerRecursive(m_Root, playerId);
		}
	}
	
	/**
	 * Supporting recursive function which uses a binary search to find the given player and determine whether
	 * or not they are in the BST.
	 * @param currentRoot The current root of the recursive function.
	 * @param playerId The ID of the given player.
	 * @return A boolean indicating if the player is in the BST.
	 */
	private boolean containsPlayerRecursive(TennisPlayersContainerNode currentRoot, String playerId)
	{
		if(currentRoot == null)
			return false;
		
		int comparison = playerId.compareTo(currentRoot.data.player.getPlayerID());
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
			return containsPlayerRecursive(currentRoot.leftChild, playerId);
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
			return containsPlayerRecursive(currentRoot.rightChild, playerId);
		// The current node is the player we are searching for
		else
			return true;
	}
	
	/**
	 * Returns a given tennis player.
	 * @param playerId The ID of the given tennis player.
	 * @return The tennis player which was searched for.
	 * @exception TennisDatabaseException
	 */
	public TennisPlayer getPlayer(String playerId) throws TennisDatabaseException
	{
		if(!this.containsPlayer(playerId))
			throw new TennisDatabaseException("The given player does not exist in the database.");
		else
			return getPlayerRecursive(m_Root, playerId);
	}
	
	/**
	 * Supporting recursive function which uses a binary search to locate the tennis player and returns the value.
	 * @param currentRoot The current root of the recursive function/
	 * @param playerId The ID of the given player.
	 * @return The located player.
	 */
	private TennisPlayer getPlayerRecursive(TennisPlayersContainerNode currentRoot, String playerId)
	{
		int comparison = playerId.compareTo(currentRoot.data.player.getPlayerID());
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
			return getPlayerRecursive(currentRoot.leftChild, playerId);
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
			return getPlayerRecursive(currentRoot.rightChild, playerId);
		// The current node is the player we are searching for
		else
			return currentRoot.data.player;
	}
	
	/**
	 * Exports all tennis players in the tree (preorder) as a condensed string.
	 * @return The condensed string.
	 */
	public String exportTennisPlayers() throws TennisDatabaseRuntimeException
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("There are no players to export.");
		
		return exportTennisPlayersRecursive(m_Root, "");
	}
	
	/**
	 * Supporting recursive function which traveres the tree (breadth-first) to create and return a condensed string.
	 * @param currentRoot The current root of the recursive function.
	 * @param s The condensed string.
	 * @return The condensed string.
	 */
	private String exportTennisPlayersRecursive(TennisPlayersContainerNode currentRoot, String s)
	{
		if(currentRoot != null)
		{
			TennisPlayer p = currentRoot.data.player;
			String appendString = "";
			
			appendString = "PLAYER/" + p.getPlayerID() + "/" + p.getFirstName() + "/" + p.getLastName() + "/" 
							+ p.getBirthYear() + "/" + p.getCountry() + System.lineSeparator();
			
			appendString += exportTennisPlayersRecursive(currentRoot.leftChild,s);
			return appendString += exportTennisPlayersRecursive(currentRoot.rightChild, s);
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * Clears all tennis players from the BST.
	 */
	public void clear()
	{
		m_Root = null;
	}
	
	/**
	 * Creates an iterator for this object with either inorder or preorder sorting.
	 * @param inorder A boolean which determines how the players are sorted in the underlying queue.
	 * @return The iterator.
	 */
	public TennisPlayersContainerIterator<TennisPlayersContainerNode> iterator(boolean inorder)
	{
		QueueArrayBased<TennisPlayersContainerNode> queue = new QueueArrayBased<TennisPlayersContainerNode>(20);
		iteratorRecursive(m_Root, queue, inorder);
		return new TennisPlayersContainerIterator<TennisPlayersContainerNode>(queue);
	}
	
	/**
	 * Supporting class which adds all players inorder or preorder depending on the input boolean.
	 * @param currentRoot The current root of the recursive function.
	 * @param queue The queue which is having items added to it.
	 * @param inorder A boolean which determines how to insert players.
	 */
	private void iteratorRecursive(TennisPlayersContainerNode currentRoot, QueueArrayBased<TennisPlayersContainerNode> queue, boolean inorder)
	{
		if(currentRoot == null)
			return;
		
		if(inorder)
		{
			iteratorRecursive(currentRoot.leftChild, queue, inorder);
			queue.enqueue(currentRoot);
			iteratorRecursive(currentRoot.rightChild, queue, inorder);
		}
		else
		{
			queue.enqueue(currentRoot);
			iteratorRecursive(currentRoot.leftChild, queue, inorder);
			iteratorRecursive(currentRoot.rightChild, queue, inorder);
		}
	}
	
	/**
	 * Nodes for the TennisPlayersContainer linked list
	 * 
	 * @author Michael Weger
	 *
	 */
	private class TennisPlayersContainerNode {
		
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
	private class NodeData {
		
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
