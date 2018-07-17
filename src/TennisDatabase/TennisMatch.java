package TennisDatabase;

import TennisDatabase.TennisPlayer;
import CS102.Date;

/**
 * A class used to encapsulate data regarding tennis matches
 * 
 * @author Michael Weger
 *
 */
public class TennisMatch implements TennisMatchInterface {

	// TODO Store player reference rather than unique ID
	
	private TennisPlayer m_Player1;	// The first player of the match
	private TennisPlayer m_Player2;	// The second player of the match
	private String m_Player1ID; 	// Unique ID of player 1
	private String m_Player2ID; 	// Unique ID of player 2
	private Date m_Date;			// The date of the match
	private String m_Tournament; 	// The tournament in which the match took place
	private String m_MatchScore;	// The scores of each set of the match
	private int m_Winner;			// The winner of the match
	
	/**
	 * 
	 * @param m A string which defines the match MATCH/PLAYER 1/PLAYER 2/DATE/TOURNAMENET/RESULTS
	 */
	public TennisMatch(String m)
	{
		String[] match = new String[6];
		match = m.split("/");
		
		m_Player1ID 	= match[1];
		m_Player2ID 	= match[2];
		m_Date 			= new Date(match[3], '-');
		m_Tournament 	= match[4];
		m_MatchScore 	= match[5];
		
		// TODO check set validity
		m_Winner 		= determineWinnerRecursive(m_MatchScore.split(","));
	}
	
	/**
	 * 
	 * @param match An array which contains the following strings which define the match PLAYER 1/PLAYER 2/DATE/TOURNAMENET/RESULTS
	 */
	public TennisMatch(String[] match)
	{
		m_Player1ID 	= match[0];
		m_Player2ID 	= match[1];
		m_Date 			= new Date(match[2], '-');
		m_Tournament 	= match[3];
		m_MatchScore 	= match[4];
		
		// TODO check set validity
		m_Winner 		= determineWinnerRecursive(m_MatchScore.split(","));
	}
	
	public String getPlayer1ID()
	{
		return m_Player1ID;
	}
	
	public String getPlayer2ID()
	{
		return m_Player2ID;
	}
	
	public String getDateYYYYMMDD()
	{
		return m_Date.getYYYYMMDD();
	}
	
	public int getDateYear()
	{
		return m_Date.getYearNumeric();
	}
	
	public int getDateMonth()
	{
		return m_Date.getMonthNumeric();
	}
	
	public int getDateDay()
	{
		return m_Date.getDayNumeric();
	}
	
	public int getDateNumeric()
	{
		return m_Date.getDateNumeric();
	}
	
	public String getTournament()
	{
		return m_Tournament;
	}
	
	public String getMatchScore()
	{
		return m_MatchScore;
	}
	
	public int getWinner()
	{
		return m_Winner;
	}
	
	/**
	 * Returns this match as a string
	 */
	public String toString()
	{	
		// Returns PID, PID, DATE, TOURNAMENT NAME, SET SCORE,SET SCORE...
		return m_Player1ID + ", " + m_Player2ID + ", " + m_Date.getYYYYMMDD() + ", " + m_Tournament + ", " + m_MatchScore; 
	}
	
	/**
	 * Determines if a given player is in this match
	 * 
	 * @param playerID the unique ID of a player
	 * @return a boolean indicating if the player is player 1 or player 2.
	 */
	public boolean hasPlayer(String playerID)
	{
		return m_Player1ID.equals(playerID) || m_Player2ID.equals(playerID);
	}
	
	// TODO check to see if match score is valid and remove invalid sets
	// Every even character should be numeric
	// Odd characters should alternate between hyphens and commas
	// It should end on a numeric character following a hyphen
	public boolean checkSetScores()
	{
		return true;
	}
	
	/**
	 * Takes the sets of the match and determines the winner of each set. Each time P1 wins the result is incremented by 1 and for every P2 win the
	 * result is decreased by 1. If the final result is positive P1 won the set, if negative P2 won.
	 * 
	 * @param sets takes an array of the set scores of the match: SET SCORE,SET SCORE
	 * @return
	 */
	private int determineWinnerRecursive(String[] sets)
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
			return determineWinnerRecursive(smallSets) + 1;
		else if(scores[0] < scores[1])
			return determineWinnerRecursive(smallSets) - 1;
		else
			return determineWinnerRecursive(smallSets);
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

	@Override
	public int compareTo(TennisMatch other) 
	{
		if(m_Date.getDateNumeric() > other.getDateNumeric())
			return 1;
		
		else if(m_Date.getDateNumeric() < other.getDateNumeric())
			return -1;
		
		else
			return 0;
	}
}