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
	
	private TennisPlayer m_Player1;	// The first player of the match
	private TennisPlayer m_Player2;	// The second player of the match
	private Date m_Date;			// The date of the match
	private String m_Tournament; 	// The tournament in which the match took place
	private String m_MatchScore;	// The scores of each set of the match
	private int m_Winner;			// The winner of the match
	
	/**
	 * Constructor
	 * @param m A string which defines the match MATCH/PLAYER 1/PLAYER 2/DATE/TOURNAMENET/RESULTS
	 */
	public TennisMatch(String m, TennisPlayer player1, TennisPlayer player2)
	{
		String[] match = new String[6];
		match = m.split("/");
		
		this.m_Player1 		= player1;
		this.m_Player2 		= player2;
		this.m_Date 		= new Date(match[3], '-');
		this.m_Tournament 	= match[4];
		this.m_MatchScore 	= match[5];
		
		if(!checkSetScores(this.m_MatchScore))
			this.m_MatchScore = "Invalid";
		
		this.m_Winner = determineWinnerRecursive(this.m_MatchScore, 0);
		updatePlayerWinLoss(m_Winner);
	}
	
	/**
	 * Constructor
	 * @param match An array which contains the following strings which define the match PLAYER 1/PLAYER 2/DATE/TOURNAMENET/RESULTS
	 */
	public TennisMatch(String[] match, TennisPlayer player1, TennisPlayer player2)
	{
		this.m_Player1 		= player1;
		this.m_Player2 		= player2;
		this.m_Date 		= new Date(match[2], '-');
		this.m_Tournament 	= match[3];
		this.m_MatchScore 	= match[4];
		
		if(!checkSetScores(m_MatchScore))
			this.m_MatchScore = "Invalid";
		
		this.m_Winner = determineWinnerRecursive(this.m_MatchScore, 0);
		updatePlayerWinLoss(m_Winner);
	}
	
	/**
	 * @return Returns the first player of this tennis match.
	 */
	public TennisPlayer getPlayer1()
	{
		return this.m_Player1;
	}
	
	/**
	 * @return Returns the second player of this tennis match.
	 */
	public TennisPlayer getPlayer2()
	{
		return this.m_Player2;
	}
	
	/**
	 * @return Returns the date in a YYYYMMDD format.
	 */
	public String getDateYYYYMMDD()
	{
		return this.m_Date.getYYYYMMDD();
	}
	
	/**
	 * @return Returns the year in which this match took place.
	 */
	public int getDateYear()
	{
		return this.m_Date.getYearNumeric();
	}
	
	/**
	 * @return Returns the month in which this match took place.
	 */
	public int getDateMonth()
	{
		return this.m_Date.getMonthNumeric();
	}
	
	/**
	 * @return Returns the day on which this match took place.
	 */
	public int getDateDay()
	{
		return this.m_Date.getDayNumeric();
	}
	
	/**
	 * @return Returns the date in which this match took place as an integer.
	 */
	public int getDateNumeric()
	{
		return this.m_Date.getDateNumeric();
	}
	
	/**
	 * @return Returns tournament in which this match took place.
	 */
	public String getTournament()
	{
		return this.m_Tournament;
	}
	
	/**
	 * @return Returns the scores from this match.
	 */
	public String getMatchScore()
	{
		return this.m_MatchScore;
	}
	
	/**
	 * @return Returns the winner of the match. A postitive value means player1 won. 0 means it is an invalid match. A negative value means player2 won.
	 */
	public int getWinner()
	{
		return this.m_Winner;
	}
	
	/**
	 * Determines if a given player is in this match
	 * 
	 * @param playerID the unique ID of a player
	 * @return hasPlayer boolean indicating if the player is player 1 or player 2.
	 */
	public boolean hasPlayer(String playerID)
	{
		return this.m_Player1.equals(playerID) || this.m_Player2.equals(playerID);
	}
	
	/**
	 * Determines if a given match score is valid
	 * 
	 * @param s The string to check
	 * @return isValid A boolean which is either true or false depending on if the string is valid or not
	 */
	private boolean checkSetScores(String s)
	{
		boolean wasHyphen = false;
		int length = s.length();
		char c;
		
		// Too short to be a valid set
		if(length < 3)
			return false;
		
		// Last set ends short
		if(!Character.isDigit(s.charAt(length-1)) || s.charAt(length - 2) != '-')
			return false;
		
		// If the set isn't the pattern... numeric hyphen numeric comma... then it is invalid
		for(int i = 0; i < length; i++)
		{
			c = s.charAt(i);
			
			// Check even indices for numbers
			if(i == 0 || i % 2 == 0)
			{
				if(!Character.isDigit(c))
					return false;
			}
			// Check odd indices for hyphens and commas
			else if(i % 2 != 0)
			{
				if(c != '-' && !wasHyphen)
					return false;

				else if(c != ',' && wasHyphen)
					return false;
				
				// Update the boolean to know whether the next odd char should be a hyphen or comma
				if(c == '-')
					wasHyphen = true;
				else
					wasHyphen = false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param s The string which represents the match
	 * @param i The index at which to check for the first value of a given set
	 * @return An integer representing the player who won. Positive = P1, Negative = P2, 0 = Invalid (A draw is invalid).
	 */
	private int determineWinnerRecursive(String s, int i)
	{
		// Invalid string
		if(s.equals("Invalid"))
			return 0;
		
		// Exceeds length
		if(i + 2 >= s.length())
			return 0;
		
		// Get the integers here to make later code easier to read
		int player1Score = s.charAt(i);
		int player2Score = s.charAt(i + 2);
		
		// Last operation
		if(i + 2 == s.length())
		{	
			if(player1Score > player2Score)
				return 1;
			else if(player1Score < player2Score)
				return -1;
			else
				return 0;
		}
		// Checks who won the set and advances to the next set
		else
		{
			if(player1Score > player2Score)
				return 1 + determineWinnerRecursive(s, i + 4);
			else if(player1Score < player2Score)
				return -1 + determineWinnerRecursive(s, i + 4);
			else
				return 0 + determineWinnerRecursive(s, i + 4);
		}
	}
	
	/**
	 * Updates player1 and player2's win loss stats after the winner has been determined
	 * @param winner
	 */
	private void updatePlayerWinLoss(int winner)
	{
		// Player 1 won
		if(winner > 0)
		{
			m_Player1.incrementWins();
			m_Player2.incrementLosses();
		}
		// Player 2 won
		else if(winner < 0)
		{
			m_Player1.incrementLosses();
			m_Player2.incrementWins();
		}
	}

	/**
	 * Compares tennis matches by date. 
	 * @param other The tennis match to compare this tennis match to
	 * @return The return value. Returns 1 if this match or more recent. 0 if they are on the same date. -1 if this match is older.
	 */
	@Override
	public int compareTo(TennisMatch other) 
	{
		if(this.m_Date.getDateNumeric() > other.getDateNumeric())
			return 1;
		
		else if(this.m_Date.getDateNumeric() < other.getDateNumeric())
			return -1;
		
		else
			return 0;
	}
	
	/**
	 * Returns this match as a string
	 */
	public String toString()
	{	
		// Returns PID, PID, DATE, TOURNAMENT NAME, SET SCORE,SET SCORE...
		return m_Player1.getFullName() + ", " + m_Player2.getFullName() + ", " + this.m_Date.getYYYYMMDD() + ", " + this.m_Tournament + ", " + this.m_MatchScore; 
	}
	
	/**
	 * Prints this match to console
	 */
	public void print()
	{
		System.out.println(this);
	}
}