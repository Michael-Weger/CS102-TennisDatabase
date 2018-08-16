package TennisDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Manages tennis matches, tennis players, and their respective data structures
 * 
 * @author Michael Weger
 *
 */
public class TennisDatabase implements TennisDatabaseInterface {
	
	private TennisMatchesContainer m_MatchContainer;	// The match container which manipulates matches in the database
	private TennisPlayersContainer m_PlayerContainer;	// The player container which manipulates players in the database
	
	public TennisDatabase()
	{
		m_MatchContainer = new TennisMatchesContainer();
		m_PlayerContainer = new TennisPlayersContainer();
	}
	
	/**
	 * Loads the file from a string with which the program was started
	 * @param fileName the path at which the text file is located
	 * @throws TennisDatabaseException, Exception
	 */
	public void readFromFile(String fileName) throws TennisDatabaseRuntimeException, TennisDatabaseException, Exception
	{
		Scanner scanner = null;
		File dataFile = new File(fileName);
		
		// Attempt to load the data file 
		if(!dataFile.exists())
		{
			throw new TennisDatabaseRuntimeException("File not found, , continuining without loading any preexisting data.");
		}
		else if(!dataFile.canRead() || !fileName.contains(".txt"))
		{
			throw new TennisDatabaseRuntimeException("File is of an invalid format, continuining without loading any preexisting data.");
		}
		else
		{
			try
			{
				scanner = new Scanner(dataFile);
			}
			catch(FileNotFoundException e)
			{
				throw new TennisDatabaseRuntimeException("File not found, , continuining without loading any preexisting data.");
			}
		}
		
		// Import all valid data from the file
		
		while(scanner.hasNextLine())
		{
			String importedData = scanner.nextLine();
			
			// Make sure the line is long enough to determine if its a player before continuing the validity test
			if(importedData.length() > 7 && importedData.substring(0, 7).equals("PLAYER/"))
			{
				try
				{
					this.insertPlayer(importedData, false);
				}
				catch(TennisDatabaseException e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
		
		// Resets the position of the file scanner to the top of the file by creating a new instance
		try 
		{
			scanner.close();
			scanner = new Scanner(dataFile);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		// Add matches now that players have been added to the database
		while(scanner.hasNext())
		{
			String importedData = scanner.nextLine();
			
			// Make sure the line is long enough to determine if its a match before continuing the validity test
			if(importedData.length() > 6 && importedData.substring(0, 6).equals("MATCH/"))
			{
				try
				{
					this.insertMatch(importedData, false);
				}
				catch(TennisDatabaseException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw e;
				}
			}
		}
		scanner.close();
	}
	
	/**
	 * Writes all players and matches to a specified file
	 * @param fileName The path at which the file can be found (or created)
	 * @throws IOException
	 */
	public void writeToFile(String fileName) throws IOException, TennisDatabaseRuntimeException
	{
		FileWriter writer = null;
		File dataFile = new File(fileName);
		
		if(!dataFile.exists())
			dataFile.createNewFile();
		
		try 
		{
			dataFile.createNewFile();
			writer = new FileWriter(dataFile, false);
		} 
		catch (IOException e) 
		{
			throw e;
		}
		
		try
		{
			String data = m_MatchContainer.exportTennisMatches() + m_PlayerContainer.exportTennisPlayers();
			writer.write(data);
		}
		catch (TennisDatabaseRuntimeException e)
		{
			writer.close();
			throw e;
		}
		
		writer.close();
	}
	
	/**
	 * Adds a player to the database after validating the string
	 * @param p The string from which a player will be created
	 * @param userFeedback a boolean value which determines whether or not to provide system prints to a user.
	 */
	public void insertPlayer(String p, boolean userFeedback) throws TennisDatabaseException
	{
		String[] importedPlayer = p.split("/");
		
		// Test for all conditions where p would be an invalid player
		
		// Invalid format
		if(importedPlayer.length != 6)
		{
			throw new TennisDatabaseException("Invalid player format. Make sure there are no missing or extraneous fields.");
		}
		// Player ID is not 5 characters long
		else if(importedPlayer[1].length() != 5)
		{
			throw new TennisDatabaseException("Invalid player ID, player IDs must be 5 characters.");
		}
		// Player ID contains non alphanumeric characters
		else if(!isAlphaNumeric(importedPlayer[1]))
		{
			throw new TennisDatabaseException("Invalid player ID, player IDs must be alphanumeric.");
		}
		// Birth year contains non numeric characters
		else if(!isNumeric(importedPlayer[4]))
		{
			throw new TennisDatabaseException("A birth year must contain only numeric characters.");
		}
		// Birth year is less than 4 digits
		else if(importedPlayer[4].length() != 4)
		{
			throw new TennisDatabaseException("A valid birth year must have four digits.");
		}
		// Player is already in database
		else if(m_PlayerContainer.containsPlayer(importedPlayer[1]))
		{
			throw new TennisDatabaseException("Player already exists in database.");
		}
		// Validation successful
		else
		{	
			try 
			{
				m_PlayerContainer.insertPlayer(new TennisPlayer(p));
				if(userFeedback)
					System.out.println("Successfully added player!");
			} 
			catch (TennisDatabaseException e) 
			{
				if(userFeedback)
					System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Removes a given player and all their matches from the database
	 * @param playerId The id of the given player
	 * @throws TennisDatabaseException
	 * @throws TennisDatabaseRuntimeException
	 */
	public void removePlayer(String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		try
		{
			m_PlayerContainer.removePlayer(playerId);
			m_MatchContainer.removeAllMatchesOfPlayer(playerId);
			System.out.println("Player successfully deleted.");
		}
		catch(TennisDatabaseException e)
		{
			throw e;
		}
		catch(TennisDatabaseRuntimeException e)
		{
			throw e;
		}
	}

	/**
	 * Prints all players in the database
	 */
	public void printAllPlayers() throws TennisDatabaseRuntimeException
	{
		try
		{
			m_PlayerContainer.printAllPlayers();
		}
		catch(TennisDatabaseRuntimeException e)
		{
			throw e;
		}
	}
	
	/**
	 * Prints all matches of a single player from the TennisMatchesContainer sorted by most recent.
	 * 
	 * @param playerID The unique player ID of the player whose matches the user wants to print.
	 */
	public void printMatchesOfPlayer(String playerID)  throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		try 
		{
			m_PlayerContainer.printMatchesOfPlayer(playerID);
		} 
		catch (TennisDatabaseRuntimeException e)
		{
			throw e;
		}
		catch (TennisDatabaseException e) 
		{
			throw e;
		}
	}
	
	/**
	 * Ensures the input string for a match is valid before adding it to the database.
	 * 
	 * @param m the match as a string PLAYER ID/PLAYER ID/DATE/TOURNAMENT/SET SCORE,SET SCORE...
	 * @param userFeedback a boolean value which determines whether or not to provide system prints to a user.
	 * @throws Exception 
	 */
	public void insertMatch(String m, boolean userFeedback) throws TennisDatabaseException, Exception
	{
		String[] importedMatch = m.split("/");
		
		// Test for "all conditions" where this would be an invalid match
		
		// Invalid format
		if(importedMatch.length != 6)
		{
			throw new TennisDatabaseException("Invalid match format. Make sure there are no missing or extraneous fields.");
		}
		// Invalid unique player ID
		else if(importedMatch[1].length() != 5 || importedMatch[2].length() !=5)
		{
			throw new TennisDatabaseException("Invalid player ID, player IDs must be 5 characters.");
		}
		// Player ID isn't alphanumeric
		else if(!isAlphaNumeric(importedMatch[1]) || !isAlphaNumeric(importedMatch[2]))
		{
			throw new TennisDatabaseException("Invalid player ID, player IDs must contain only alphanumeric characters.");
		}
		// Identical unique player IDs
		else if(importedMatch[1].equals(importedMatch[2]))
		{
			throw new TennisDatabaseException("Invalid player IDs, a match cannot have two identical player IDs.");
		}
		// Players aren't in the database
		else if(!m_PlayerContainer.containsPlayer(importedMatch[1]) || !m_PlayerContainer.containsPlayer(importedMatch[2]))
		{
			throw new TennisDatabaseException("Invalid player IDs, a match must have both it's players already in the database.");
		}
		// Date isnt numeric
		if(!isNumeric(importedMatch[3]))
		{
			throw new TennisDatabaseException("Invalid date, a date must contain only numeric characters.");
		}
		// Improper date format
		else if(importedMatch[3].length() != 8)
		{
			throw new TennisDatabaseException("Invalid date, a date must have 8 digits: YYYYMMDD");
		}
		// Too small of a year
		else if(Integer.parseInt(importedMatch[3].substring(0, 4)) < 1100)
		{
			throw new TennisDatabaseException("Invalid date, tennis didn't exist before the 12th century. Tell a historian about your match!");
		}
		// Too large of a month
		else if(Integer.parseInt(importedMatch[3].substring(4, 6)) > 12)
		{
			throw new TennisDatabaseException("Invalid date, there are only 12 months in a year.");
		}
		// Too large of a date
		else if(Integer.parseInt(importedMatch[3].substring(6, 8)) > 31)
		{
			throw new TennisDatabaseException("Invalid date, there are no more than 31 days in a month");
		}
		// Too many or too few sets in a match
		else if(importedMatch[5].split(",").length < 2)
		{
			throw new TennisDatabaseException("Invalid set scores, a match must have at least two sets.");
		}
		// Actually adding the match
		else
		{
			TennisPlayer player1 = m_PlayerContainer.getPlayer(importedMatch[1]);
			TennisPlayer player2 = m_PlayerContainer.getPlayer(importedMatch[2]);
			
			TennisMatch match = new TennisMatch(m, player1, player2);
			if(!m_MatchContainer.containsMatch(match))
			{
				m_MatchContainer.insertMatch(match);
				try 
				{
					try
					{
						m_PlayerContainer.insertMatch(match);
					}
					catch(Exception e)
					{
						throw e;
					}
					
					if(userFeedback)
						System.out.println("Match successfully added.");
				} 
				catch (TennisDatabaseException e) 
				{
					if(userFeedback)
						System.out.println(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Prints all matches from the TennisMatchesContainer sorted by most recent .
	 */
	public void printAllMatches() throws TennisDatabaseRuntimeException
	{
		try
		{
			m_MatchContainer.printAllMatches();
		}
		catch(TennisDatabaseRuntimeException e)
		{
			throw e;
		}
	}
	
	/**
	 * Removes all players and matches from the database
	 */
	public void clear()
	{
		m_MatchContainer.clear();
		m_PlayerContainer.clear();
	}
	
	/**
	 * Determines if a given string contains only numeric characters
	 * @param s The string to examine
	 * @return The boolean result of the operation
	 */
	private boolean isNumeric(String s)
	{
		char[] charArr = s.toCharArray();
		
		for(char c : charArr)
			if(!Character.isDigit(c))
				return false;
		
		return true;
	}
	
	/**
	 * Determines if a given string contains only alphanumeric characters
	 * @param s The string to examine
	 * @return The boolean result of the operation
	 */
	private boolean isAlphaNumeric(String s)
	{
		char[] charArr = s.toCharArray();
				
		for(char c : charArr)
			if(!Character.isDigit(c) && !Character.isLetter(c))
				return false;
		
		return true;
	}
}