package TennisDatabase;

import java.util.jar.Pack200;

/**
 * A class used to encapsulate data regarding tennis matches
 * 
 * @author Michael Weger
 *
 */
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
		
		m_Winner = determineWinner();
	}
	
	public TennisMatch(String[] match)
	{
		m_Player1 		= match[0];
		m_Player2 		= match[1];
		m_Date 			= match[2];
		m_Tournament 	= match[3];
		m_Results 		= match[4];
		
		m_Winner = determineWinner();
	}
	
	/**
	 * Determines if a given player is in this match
	 * 
	 * @param UPID the unique ID of a player
	 * @return a boolean indicating if the player is player 1 or player 2.
	 */
	public boolean hasPlayer(String UPID)
	{
		return m_Player1.equals(UPID) || m_Player2.equals(UPID);
	}
	
	/**
	 * 
	 * @return The date of the match as YYYYMMDD
	 */
	public int getDate()
	{
		return Integer.parseInt(m_Date);
	}
	
	/**
	 * Returns this match as a string
	 */
	public String toString()
	{	
		// Returns PID, PID, DATE, TOURNAMENT NAME, SET SCORE,SET SCORE...
		return m_Player1 + ", " + m_Player2 + ", " + m_Date + ", " + m_Tournament + ", " + m_Results; 
	}
	
	/**
	 * Takes the resulting value of the determineMatchWinnerRecursive method and returns the player ID of the winner.
	 * 
	 * @return returns the unique player ID of the winner
	 */
	private String determineWinner()
	{
		// Get the numeric "winner" value
		int i = determineMatchWinnerRecursive(m_Results.split(","));
		
		if(i > 0)
			return m_Player1;
		else
			return m_Player2;
	}
	
	/**
	 * Takes the sets of the match and determines the winner of each set. Each time P1 wins the result is incremented by 1 and for every P2 win the
	 * result is decreased by 1. If the final result is positive P1 won the set, if negative P2 won.
	 * 
	 * @param sets takes an array of the set scores of the match: SET SCORE,SET SCORE
	 * @return
	 */
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
	
	/**
	 * Unpacks a single set for analysis by determineMatchWinnerRecursive
	 * 
	 * @param sets A string array of the set scores split at the comma.
	 * @return A size 2 array with the two digits of the set being analyzed
	 */
	private int[] unpackSet(String[] sets)
	{
		// Take the last set
		String[] lastSet = sets[sets.length-1].split("-");
		
		// Put its individual numeric values into a size two array
		int[] setScores = new int[2];
		setScores[0] = Integer.parseInt(lastSet[0]);
		setScores[1] = Integer.parseInt(lastSet[1]);
		
		return setScores;
	}
}
