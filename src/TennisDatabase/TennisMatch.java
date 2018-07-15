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
		
		m_Winner = winnerStringResult(determineMatchWinnerRecursive(m_Results.split(",")));
	}
	
	public TennisMatch(String[] match)
	{
		m_Player1 		= match[0];
		m_Player2 		= match[1];
		m_Date 			= match[2];
		m_Tournament 	= match[3];
		m_Results 		= match[4];
		
		m_Winner = winnerStringResult(determineMatchWinnerRecursive(m_Results.split(",")));
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
	
	private String winnerStringResult(int i)
	{
		if(i > 0)
			return m_Player1;
		else
			return m_Player2;
	}
	
	private int determineMatchWinnerRecursive(String[] sets)
	{
		// Base case. If there's nothing left in the array then there's nothing to process.
		if(sets.length == 0)
			return 0;
		
		// Unpack our sets to make them easier to process and have this function slightly cleaner
		int[] scores = unpackSet(sets);
		
		// Shrink the initial array to move toward the base case
		String[] smallSets = new String[sets.length-1];
		
		// Move the content of the old array into the new one
		for(int i = 0; i < sets.length - 1; i++)
			smallSets[i] = sets[i];
		
		// P1 wins = +1 | P2 wins = -1 | Draw = 0
		// If the final integer value is positive P1 won the match, if its negative P2 won.
		if(scores[0] > scores[1])
			return determineMatchWinnerRecursive(smallSets) + 1;
		else if(scores[0] < scores[1])
			return determineMatchWinnerRecursive(smallSets) - 1;
		else
			return determineMatchWinnerRecursive(smallSets);
	}
	
	// Returns the score of the last set of the match as an integer array
	private int[] unpackSet(String[] sets)
	{
		String[] lastSet = sets[sets.length-1].split("-");
		
		int[] setScores = new int[2];
		setScores[0] = Integer.parseInt(lastSet[0]);
		setScores[1] = Integer.parseInt(lastSet[1]);
		
		return setScores;
	}
}
