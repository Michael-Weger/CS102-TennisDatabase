package TennisDatabase;

public class TennisMatch {

	private String m_Player1;
	private String m_Player2;
	private String m_Date;
	private String m_Tournament;
	private String m_Results;
	private String m_Winner;
	
	public TennisMatch(String match)
	{
		String[] importedMatch = new String[6];
		importedMatch = match.split("/");
		
		m_Player1 		= importedMatch[1];
		m_Player2 		= importedMatch[2];
		m_Date 			= importedMatch[3];
		m_Tournament 	= importedMatch[4];
		m_Results 		= importedMatch[5];
	}
	
	public TennisMatch(String[] match)
	{
		m_Player1 		= match[0];
		m_Player2 		= match[1];
		m_Date 			= match[2];
		m_Tournament 	= match[3];
		m_Results 		= match[4];
	}
	
	public boolean hasPlayer(String UUID)
	{
		return m_Player1.equals(UUID) || m_Player2.equals(UUID);
	}
	
	public int getDate()
	{
		return Integer.parseInt(m_Date);
	}
	
	public String toString()
	{	
		return m_Player1 + ", " + m_Player2 + ", " + m_Date + ", " + m_Tournament + ", " + m_Results; 
	}
	
	// To determine a winner they must win a Bo3 or a Bo5 
	// Why the hell something this simple must be recursive is beyond me, the assignment should have required
	// an implementation of merge sort as it is efficient, challenging, and recursive...
	public void determineMatchWinner()
	{
		
	}
	
	// Returns an array with each individual score from each set. Even indices mark the start of a new set.
	private int[] getScores()
	{
		// Create a new array to store the result of each set
		// Since tennis matches can be played in a best of 5 format we must allocate at least 5 indices of memory to handle the 5 possible sets
		String[] setArray = new String[5];
		setArray = m_Results.split(",");
		
		// Likewise 
		int[] scoreArray = new int[10];
		
		int i = 0;
		for(String s : setArray)
		{
			// Split at the hyphen to get the individual 
			String[] scores = s.split("-");
			
			// scores[0] is the first score of the set result, likewise scores[1] is the second score.
			// In scoreArray[] every even number now begins a new set
			// Convert all the individual numerical characters to primitive type int to make our recursive function cleaner
			scoreArray[i] = Integer.parseInt(scores[0]);
			scoreArray[i+1] = Integer.parseInt(scores[1]);
			
			// Increment by 2 to move onto the next set
			i+=2;
		}
		
		return scoreArray;
	}
}
