package TennisDatabase;

public class TennisPlayer {

	private String m_UUID;
	private String m_FirstName;
	private String m_LastName;
	private String m_Nationality;
	private String m_BirthYear;
	private int m_Wins;
	private int m_Losses;
	
	public TennisPlayer(String player)
	{
		String[] importedPlayer = new String[6];
		importedPlayer = player.split("/");
		
		m_UUID = importedPlayer[1];
		m_FirstName = importedPlayer[2];
		m_LastName = importedPlayer[3];
		m_Nationality = importedPlayer[4];
		m_BirthYear = importedPlayer[5];
	}
	
	public TennisPlayer(String[] importedPlayer)
	{
		m_UUID = importedPlayer[0];
		m_FirstName = importedPlayer[1];
		m_LastName = importedPlayer[2];
		m_Nationality = importedPlayer[3];
		m_BirthYear = importedPlayer[4];
	}
	
	public String getFullName()
	{
		return m_FirstName + " " + m_LastName;
	}
	
	public boolean hasUUID(String UUID)
	{
		return m_UUID.equals(UUID);
	}
}
