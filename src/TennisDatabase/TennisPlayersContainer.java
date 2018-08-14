package TennisDatabase;

/**
 * A class which represents a binary search tree
 * @author Michael Weger
 *
 */
class TennisPlayersContainer implements TennisPlayersContainerInterface {
		
	private TennisPlayerContainerNode m_Root; 		// The start of the binary search tree
	
	/**
	 * Constructor which sets default values
	 */
	public TennisPlayersContainer()
	{
		m_Root = null;	// When the linked list is empty the entry is null
	}
	
	/**
	 * Inserts a given player into the BST based on their unique ID
	 * @param p The given player to insert
	 */
	public void insertPlayer(TennisPlayer p) throws TennisDatabaseException 
	{
		if(m_Root == null)
			m_Root = new TennisPlayerContainerNode(p);
		else if(this.containsPlayer(p.getPlayerID()))
			throw new TennisDatabaseException("The given player already exists in the database");
		else
			insertPlayerRecursive(this.m_Root, p);
		
	}
	
	private TennisPlayerContainerNode insertPlayerRecursive(TennisPlayerContainerNode currentNode, TennisPlayer p) throws TennisDatabaseRuntimeException
	{
		if(currentNode == null)
		{
			return new TennisPlayerContainerNode(p);
		}
		
		int comparison = p.compareTo(currentNode.player);
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
		{
			currentNode.leftChild = insertPlayerRecursive(currentNode.leftChild, p);
			return currentNode;
		}
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
		{
			currentNode.rightChild = insertPlayerRecursive(currentNode.rightChild, p);
			return currentNode;
		}
		// Value is already in the tree
		else
			throw new TennisDatabaseRuntimeException("The given player already exists in the database.");
	}

	public void insertMatch(TennisMatch m) throws TennisDatabaseException 
	{
		if(m_Root == null)
			throw new TennisDatabaseException("No players are currently in the database.");
		
		insertMatchRecursive(m_Root, m, 0);
	}
	
	private void insertMatchRecursive(TennisPlayerContainerNode currentNode, TennisMatch m, int result)
	{
		if(result == 2)
			return;
		
		if(currentNode != null)
		{
			insertMatchRecursive(currentNode.leftChild, m, result);
			
			String playerID = currentNode.player.getPlayerID();
			
			if(playerID.equals(m.getPlayer1().getPlayerID()) || playerID.equals(m.getPlayer2().getPlayerID()))
			{
				try 
				{
					currentNode.tennisMatches.insert(m);
				} 
				catch (Exception e) 
				{
					System.out.println(e.getMessage());
				}
				
				result++;
			}
			
			insertMatchRecursive(currentNode.rightChild, m, result);
		}
	}

	public void printAllPlayers() throws TennisDatabaseRuntimeException 
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("No players are currently in the database.");
		
		printAllPlayersRecursive(m_Root);
		
	}
	
	private void printAllPlayersRecursive(TennisPlayerContainerNode currentNode)
	{
		if(currentNode != null)
		{
			printAllPlayersRecursive(currentNode.leftChild);
			currentNode.player.print();
			printAllPlayersRecursive(currentNode.rightChild);
		}
	}

	public void printMatchesOfPlayer(String playerId) throws TennisDatabaseException 
	{
		if(m_Root == null)
			throw new TennisDatabaseRuntimeException("No players are currently in the database.");
		
		try
		{
			printMatchesOfPlayerRecursive(m_Root, playerId);
		}
		catch(TennisDatabaseException e)
		{
			throw e;
		}
		
	}
	
	private void printMatchesOfPlayerRecursive(TennisPlayerContainerNode currentNode, String playerId) throws TennisDatabaseException
	{
		
		if(currentNode == null)
			throw new TennisDatabaseException("The given player is not in the database.");
		
		int comparison = playerId.compareTo(currentNode.player.getPlayerID());
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
			printMatchesOfPlayerRecursive(currentNode.leftChild, playerId);
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
			printMatchesOfPlayerRecursive(currentNode.rightChild, playerId);
		// The current node is the player we are searching for
		else
			currentNode.tennisMatches.print();
	}
	
	public void removePlayer(String playerId)
	{
		// TODO
	}
	
	private void removePlayerRecursive(TennisPlayerContainerNode currentNode, String playerId)
	{
		// TODO
	}
	
	public boolean containsPlayer(String playerId)
	{
		if(m_Root == null)
			return false;
		else
		{
			return containsPlayerRecursive(m_Root, playerId);
		}
	}
	
	private boolean containsPlayerRecursive(TennisPlayerContainerNode currentNode, String playerId)
	{
		if(currentNode == null)
			return false;
		
		int comparison = playerId.compareTo(currentNode.player.getPlayerID());
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
			return containsPlayerRecursive(currentNode.leftChild, playerId);
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
			return containsPlayerRecursive(currentNode.rightChild, playerId);
		// The current node is the player we are searching for
		else
			return true;
	}
	
	public TennisPlayer getPlayer(String playerId)
	{
		return getPlayerRecursive(m_Root, playerId);
	}
	
	private TennisPlayer getPlayerRecursive(TennisPlayerContainerNode currentNode, String playerId)
	{
		int comparison = playerId.compareTo(currentNode.player.getPlayerID());
		
		// The given player is lexicographically less than the player at the node
		if(comparison < 0)
			return getPlayerRecursive(currentNode.leftChild, playerId);
		// The given player is lexicographically greater than the player at the node
		else if(comparison > 0)
			return getPlayerRecursive(currentNode.rightChild, playerId);
		// The current node is the player we are searching for
		else
			return currentNode.player;
	}
	
	public String exportTennisPlayers()
	{
		return exportTennisPlayersRecursive(m_Root, "");
	}
	
	public String exportTennisPlayersRecursive(TennisPlayerContainerNode currentNode, String s)
	{
		if(currentNode != null)
		{
			String appendString = exportTennisPlayersRecursive(currentNode.leftChild,s);
			
			TennisPlayer p = currentNode.player;
			appendString += p.getPlayerID() + "/" + p.getFirstName() + "/" + p.getLastName() + "/" + p.getBirthYear() + "/" + p.getCountry() + "\\n";
			
			return appendString += exportTennisPlayersRecursive(currentNode.rightChild, s);
		}
		else
		{
			return "";
		}
	}
	
	public void clear()
	{
		m_Root = null;
	}
	
	/**
	 * Nodes for the TennisPlayersContainer linked list
	 * 
	 * @author Michael Weger
	 *
	 */
	private class TennisPlayerContainerNode {
		
		public TennisPlayerContainerNode rightChild;		// The right node
		public TennisPlayerContainerNode leftChild;			// The left node
		public TennisPlayer player;							// The player which this node contains
		public SortedLinkedList<TennisMatch> tennisMatches;	// The linked list of matches in which this nodes player participated in
		
		/**
		 * Constructor
		 * @param player The player which this node contains
		 */
		public TennisPlayerContainerNode(TennisPlayer player)
		{
			this.player = player;
			this.rightChild = null;
			this.leftChild = null;
			this.tennisMatches = new SortedLinkedList<TennisMatch>();
		}
	}
}
