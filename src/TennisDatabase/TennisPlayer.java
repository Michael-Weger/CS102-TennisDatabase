package TennisDatabase;

/**
 * A class used to encapsulate data regarding tennis players
 * 
 * @author Michael Weger
 *
 */
public class TennisPlayer implements TennisPlayerInterface {

	private String m_PlayerID;		// Unique ID of the player
	private String m_FirstName;		// First name of the player
	private String m_LastName;		// Last name
	private String m_Country;		// Country of the player
	private String m_BirthYear;		// Birth year of the player
	private int m_Wins;				// How many times the player has won a match
	private int m_Losses;			// How many times the player has lost a match
	
	/**
	 * Constructor
	 * @param p The raw string from which to create a player object
	 */
	public TennisPlayer(String p)
	{
		String[] player = new String[6];
		player = p.split("/");
		
		m_PlayerID 	= player[1];
		m_FirstName = player[2];
		m_LastName 	= player[3];
		m_BirthYear = player[4];
		m_Country 	= player[5];
	}
	
	/**
	 * Constructor
	 * @param player The array of strings from which to create a player object
	 */
	public TennisPlayer(String[] player)
	{
		m_PlayerID 	= player[0];
		m_FirstName = player[1];
		m_LastName 	= player[2];
		m_BirthYear = player[3];
		m_Country 	= player[4];
	}
	
	/**
	 * @return Returns the ID of this player.
	 */
	public String getPlayerID()
	{
		return m_PlayerID;
	}
	
	/**
	 * @return Returns first name of this player.
	 */
	public String getFirstName()
	{
		return m_FirstName;
	}
	
	/**
	 * @return Returns last name of this player.
	 */
	public String getLastName()
	{
		return m_LastName;
	}
	
	/**
	 * @return Returns the country of this player.
	 */
	public String getCountry()
	{
		return m_Country;
	}
	
	/**
	 * @return Returns birth year of this player as a string.
	 */
	public String getBirthYear()
	{
		return m_BirthYear;
	}
	
	/**
	 * @return Returns the birth year of this player as an integer.
	 */
	public int getBirthYearNumeric()
	{
		return Integer.parseInt(m_BirthYear);
	}
	
	/**
	 * @return Returns the full name of this player.
	 */
	public String getFullName()
	{
		return m_FirstName + " " + m_LastName;
	}
	
	/**
	 * @return Increments the wins stat of this player.
	 */
	public void incrementWins()
	{
		m_Wins++;
	}
	
	/**
	 * @return Increments the losses stat of this player.
	 */
	public void incrementLosses()
	{
		m_Losses++;
	}
	
	/**
	 * @return Returns the win:loss ratio.
	 */
	public String getWinLossRatio()
	{
		return m_Wins + ":" + m_Losses;
	}
	
	/**
	 * @return Returns a boolean value indicating if this players ID matches a given ID.
	 */
	public boolean equals(String playerID)
	{
		return m_PlayerID.equals(playerID);
	}
	
	/**
	 * @return Returns a boolean value indicating if this player's ID matches another given player's ID.
	 */
	public boolean equals(TennisPlayer player)
	{
		return m_PlayerID.equals(player.getPlayerID());
	}
	
	/**
	 * @return Returns this player as a string.
	 */
	public String toString()
	{
		return m_PlayerID + ", " + getFullName() + ", " +  m_BirthYear + ", "  + m_Country + ", " + getWinLossRatio();
	}
	
	/**
	 * Prints this player to console
	 */
	public void print()
	{
		System.out.println(this);
	}

	/**
	 * Compares this player to another
	 * @return Returns 1 if this player's ID is lexicographically less than the other. 0 if both players have the same lexographical value. -1 if this player has a greater lexographical value than the other.
	 */
	@Override
	public int compareTo(TennisPlayer otherPlayer) {
		return m_PlayerID.compareTo(otherPlayer.getPlayerID());
	}
}