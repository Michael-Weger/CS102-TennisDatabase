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
	
	public TennisPlayer(String p)
	{
		String[] player = new String[6];
		player = p.split("/");
		
		m_PlayerID = player[1];
		m_FirstName = player[2];
		m_LastName = player[3];
		m_Country = player[4];
		m_BirthYear = player[5];
		System.out.println("Player constructed.");
	}
	
	public TennisPlayer(String[] player)
	{
		m_PlayerID 		= player[0];
		m_FirstName 	= player[1];
		m_LastName 		= player[2];
		m_Country 	= player[3];
		m_BirthYear 	= player[4];
	}
	
	public String getPlayerID()
	{
		return m_PlayerID;
	}
	
	public String getFirstName()
	{
		return m_FirstName;
	}
	
	public String getLastName()
	{
		return m_LastName;
	}
	
	public String getCountry()
	{
		return m_Country;
	}
	
	public String getBirthYear()
	{
		return m_BirthYear;
	}
	
	public int getBirthYearNumeric()
	{
		return Integer.parseInt(m_BirthYear);
	}
	
	public String getFullName()
	{
		return m_FirstName + " " + m_LastName;
	}
	
	public void incrementWins()
	{
		m_Wins++;
	}
	
	public void incrementLosses()
	{
		m_Losses++;
	}
	
	public String getWinLossRaito()
	{
		return m_Wins + ":" + m_Losses;
	}
	
	public boolean equals(String playerID)
	{
		return m_PlayerID.equals(playerID);
	}
	
	public boolean equals(TennisPlayer player)
	{
		return m_PlayerID.equals(player.getPlayerID());
	}
	
	public String toString()
	{
		return m_PlayerID + ", " + getFullName() + ", " +  m_Country + ", " +  m_BirthYear + ", " + getWinLossRaito();
	}
	
	public void print()
	{
		System.out.println(this);
	}

	@Override
	public int compareTo(TennisPlayer otherPlayer) {
		return m_PlayerID.compareTo(otherPlayer.getPlayerID());
	}
}