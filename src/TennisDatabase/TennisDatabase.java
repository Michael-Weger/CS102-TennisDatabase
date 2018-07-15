package TennisDatabase;

public class TennisDatabase {
	
	private TennisMatchesContainer m_MatchContainer;
	// private TennisPlayersContainer m_PlayersContainer;
	
	public TennisDatabase()
	{
		m_MatchContainer = new TennisMatchesContainer();
		// m_PlayerContainer = new TennisPlayersContainer();
	}
	public void addPlayer(String s, boolean userFeedback)
	{
		
	}
	public String getPlayerNameByUUID(String UUID)
	{
		return "Placeholder Name";
	}
	
	public void addMatch(String m, boolean userFeedback)
	{
		String[] importedMatch = m.split("/");
		
		// Test for "all conditions" where this would be an invalid match
		
		// Invalid format
		if(importedMatch.length != 6)
		{
			if(userFeedback)
				System.out.println("Invalid match format. Make sure there are no missing or extraneous fields.");
			return;
		}
		// Invalid unique player ID
		else if(importedMatch[1].length() != 5 || importedMatch[2].length() !=5)
		{
			if(userFeedback)
				System.out.println("Invalid player ID, player IDs must be 5 digits and contain only alphanumeric characters.");
			return;
		}
		// Identical unique player IDs
		else if(importedMatch[1].equals(importedMatch[2]))
		{
			if(userFeedback)
				System.out.println("Invalid player IDs, a match cannot have two identical player IDs.");
			return;
		}
		// Improper date format
		else if(importedMatch[3].length() != 8)
		{
			if(userFeedback)
				System.out.println("Invalid date, a date must have 8 digits: YYYYMMDD");
			return;
		}
		// Too small of a year
		else if(Integer.parseInt(importedMatch[3].substring(0, 4)) < 1100)
		{
			if(userFeedback)
				System.out.println("Invalid date, tennis didn't exist before the 12th century. Tell a historian about your match!");
			return;
		}
		// Too large of a month
		else if(Integer.parseInt(importedMatch[3].substring(4, 6)) > 12)
		{
			if(userFeedback)
				System.out.println("Invalid date, there are only 12 months in a year.");
			return;
		}
		// Too large of a date
		else if(Integer.parseInt(importedMatch[3].substring(6, 8)) > 31)
		{
			if(userFeedback)
				System.out.println("Invalid date, there are no more than 31 days in a month");
			return;
		}
		// Too many or too few sets in a match
		else if(importedMatch[5].split(",").length < 2)
		{
			if(userFeedback)
				System.out.println("Invalid set scores, a match must have at least two sets.");
			return;
		}
		// Actually adding the match
		else
		{
			TennisMatch match = new TennisMatch(m);
			m_MatchContainer.addMatch(match);
			
			if(userFeedback)
				System.out.println("Match successfully added.");
		}
	}
	public void printMatches()
	{
		m_MatchContainer.printMatches();
	}
	public void printMatches(String UUID)
	{
		m_MatchContainer.printMatches(UUID);
	}
	
	private class TennisMatchesContainer {
		
		// The array which will be manipulated
		private TennisMatch[] m_Array;
		
		// Tracks number of items in the array
		private int m_ArraySize;
		
		public TennisMatchesContainer()
		{
			// Create an array and a variable to track how many items are in the array at any given time
			m_ArraySize = 0;
			m_Array = new TennisMatch[5];
		}
		
		public void addMatch(TennisMatch match)
		{	
			if(m_Array.length < m_ArraySize + 1)
				resizeArray();
			
			m_Array[m_ArraySize] = match;
			m_ArraySize++;
			selectionSort();
		}
		
		public void printMatches()
		{
			if(m_ArraySize == 0)
			{
				System.out.println("There are currently no matches in the database.");
				return;
			}
			for(TennisMatch m : m_Array)
			{
				System.out.println(m.toString());
			}
		}
		
		public void printMatches(String UUID)
		{
			boolean noMatches = true;
			
			for(TennisMatch m : m_Array)
			{
				if(m.hasPlayer(UUID))
				{
					noMatches = false;
					String[] split = m.toString().split(", ");
					
					// Create full name
					split[0] = getPlayerNameByUUID(split[0]);
					split[1] = getPlayerNameByUUID(split[1]);
					
					// Reformat date YYYY-MM-DD
					split[2] = split[2].substring(0, 4) + "-" + split[2].substring(4, 6) + "-" + split[2].substring(6, 8);
					
					// Final String: FIRST LAST, YYYY-MM-DD, TOURNAMENT, SET SCORES
					String s = split[0] + " " + split[1] + ", " + split[2] + ", " + split[3] + ", " + split[4];
					
					System.out.println(s);
				}
			}
			
			if(noMatches)
				System.out.println("No matches found for player. Did you enter a valid UUID?");
		}
		
		private void resizeArray()
		{
			// Create a new array with size n+1 to fit the new item
			TennisMatch[] resizedArray = new TennisMatch[m_ArraySize + 1];
			
			// Move all items from the previous array to the new array
			for(int i = 0; i < m_ArraySize; i++)
			{
				resizedArray[i] = m_Array[i];
			}
			
			// Set the member variable to the new array for use in other functions
			m_Array = resizedArray;
		}
		
		private void selectionSort()
		{
			// Iterate over each index of the array. The nested for loop checks to see if the value is lower
			// than the assumed minimum at the current starting position. If the match at j is more recent then
			// Swap the matches at the minimum index and j. Once the algorithm iterates over the 
			// Entire array the array should be sorted.
			for(int i = 0; i < m_ArraySize; i++)
			{
				int minimumIndex = i;
				for(int j = i+1; j < m_ArraySize; j++)
				{
					if(m_Array[j].getDate() > m_Array[minimumIndex].getDate())
					{
						minimumIndex = j;
					}
				}
				
				// If i and the index with the most recent match aren't the same swap them
				if(minimumIndex != i)
				{
					TennisMatch temp = m_Array[i];
					m_Array[i] = m_Array[minimumIndex];
					m_Array[minimumIndex] = temp;
				}
			}
		}
	}
}