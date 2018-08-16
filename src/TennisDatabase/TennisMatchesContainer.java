package TennisDatabase;

import java.util.ArrayList;

/**
 * Stores tennis matches in a dynamically sized array.
 * 
 * @author Michael Weger
 *
 */
class TennisMatchesContainer implements TennisMatchesContainerInterface {
	
	// The array which will be manipulated.
	private ArrayList<TennisMatch> m_Array;
	
	/**
	 * Constructor which sets the array to default allocated size 20.
	 */
	public TennisMatchesContainer()
	{
		m_Array = new ArrayList<TennisMatch>(20);
	}
	
	/**
	 * Adds a tennis match to the database
	 * 
	 * @param match A valid tennis match after passing validity testing from the method in the TennisDatabase class.
	 */
	public void insertMatch(TennisMatch match) throws TennisDatabaseException
	{	
		
		if(match == null)
			throw new TennisDatabaseException("Attempted to insert null match into the database.");
		
		int index = -1;
		
		for(int i = 0; i < m_Array.size(); i++)
		{
			// Our match date is greater or equal to the one at index i
			if(match.compareTo(m_Array.get(i)) >= 1)
			{
				index = i;
				break;
			}
		}
		
		// No index was found its the new last item
		if(index == -1)
			m_Array.add(match);
		// Otherwise shift the array to the right at the index of insertion
		else
			m_Array.add(index, match);
	}
	
	/**
	 * Removes all matches of a given player from the array.
	 * @param playerId The ID of the given player.
	 */
	public void removeAllMatchesOfPlayer(String playerId)
	{
		Object[] tempArray = m_Array.toArray();
		for(Object m : tempArray)
		{
			if(((TennisMatch) m).hasPlayer(playerId))
			{
				m_Array.remove(m);
			}
		}
	}
	
	/**
	 * Prints all matches currently in the database.
	 */
	public void printAllMatches() throws TennisDatabaseRuntimeException
	{
		// Provide feedback to the user if there aren't any matches.
		if(m_Array.size() == 0)
			throw new TennisDatabaseRuntimeException("There are currently no matches in the database.");
		
		
		// Print all matches.
		// Blank line is printed here to avoid a blank line from printing when an exception is thrown.
		System.out.println("");
		for(TennisMatch m : m_Array)
		{
			if(m != null)
				m.print();
		}
	}
	
	/**
	 * Returns a boolean value based on whether the given match is in the container.
	 * @return Whether or not the match is in the database.
	 */
	public boolean containsMatch(TennisMatch m)
	{
		return m_Array.contains(m);
	}
	
	/**
	 * Exports the contents of the array to a single condensed string. Each match is its own line.
	 * @return The condensed string of matches.
	 */
	public String exportTennisMatches()
	{
		if(m_Array.size() == 0)
			return "";
		
		String exportStr = "";
		
		for(TennisMatch m : m_Array)
		{
			String s = "MATCH/" + m.getPlayer1().getPlayerID() + "/" + m.getPlayer2().getPlayerID() + "/" 
					+ m.getDateYearString() + m.getDateMonthString() + m.getDateDayString() + "/" + m.getTournament() + "/" 
					+ m.getMatchScore() + System.lineSeparator();
			
			exportStr += s;
		}
		
		return exportStr;
	}
	
	/**
	 * Clears the ArrayList.
	 */
	public void clear()
	{
		m_Array.clear();
	}
}
