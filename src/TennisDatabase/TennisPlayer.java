package TennisDatabase;

public class TennisPlayer {

	private String m_PlayerID;
	private String m_FirstName;
	private String m_LastName;
	private String m_Nationality;
	private String m_BirthYear;
	private int m_Wins;
	private int m_Losses;
	
	public TennisPlayer(String p)
	{
		String[] player = new String[6];
		player = p.split("/");
		
		m_PlayerID = player[1];
		m_FirstName = player[2];
		m_LastName = player[3];
		m_Nationality = player[4];
		m_BirthYear = player[5];
	}
	
	public TennisPlayer(String[] importedPlayer)
	{
		m_PlayerID = importedPlayer[0];
		m_FirstName = importedPlayer[1];
		m_LastName = importedPlayer[2];
		m_Nationality = importedPlayer[3];
		m_BirthYear = importedPlayer[4];
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
	
	public String getNationality()
	{
		return m_Nationality;
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
	
	public double getWinLossRaito()
	{
		return m_Wins/m_Losses;
	}
	
	public boolean equals(String playerID)
	{
		return m_PlayerID.equals(playerID);
	}
}
