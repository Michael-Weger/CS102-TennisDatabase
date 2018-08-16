package CS102;

import java.io.IOException;
import java.util.Scanner;

import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;

/**
 * Main class of the project
 * 
 * @author Michael Weger
 *
 */
public class Assignment2 {
	
	static TennisDatabase tennisDatabase;
	static Scanner userScanner;
	
	/**
	 * Loads a given file and stores all information on matches and players then waits for user input
	 * 
	 * @param args the path to the text file containing data on matches and players
	 */
	public static void main(String args[])
	{
		tennisDatabase = new TennisDatabase();
		userScanner = new Scanner(System.in);
		
		// Load data file
		try
		{
			tennisDatabase.readFromFile(args[0]);
		}
		catch(TennisDatabaseRuntimeException e)
		{
			System.out.println("");
			System.out.println(e.getMessage());
			System.out.println("");
		}
		catch(TennisDatabaseException e)
		{
			System.out.println("");
			System.out.println(e.getMessage());
			System.out.println("");
		}
		catch(ArrayIndexOutOfBoundsException e1)
		{
			System.out.println("");
			System.out.println("No path specified, continuing without loading a data file.");
			System.out.println("");
		}
		catch(Exception e)
		{
			
		}
		
		// Display all commands for the user
		System.out.println("");
		System.out.println("---------------------------------------CS102 Tennis Database---------------------------------------");
		System.out.println("!Help			- Displays this message again.");
		System.out.println("!Matches		- Displays all matches.");
		System.out.println("!MatchesByPlayer	- Displays all matches a given player has participated in.");
		System.out.println("!Players		- Displays all players.");
		System.out.println("!InsertMatch		- Adds a match to the database.");
		System.out.println("!InsertPlayer		- Adds a player to the database.");
		System.out.println("!RemovePlayer		- Adds a player to the database.");
		System.out.println("!Import			- Imports all players and matches from a given file.");
		System.out.println("!Export			- Exports all players and matches to a given file.");
		System.out.println("!Clear			- Clears all players and matches from the database.");
		System.out.println("!Exit			- Terminates the program.");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("");
		
		
		// Loop to check for user input until the program terminates
		while(true)
		{
			String s = userScanner.nextLine();
			
			// Change all input to lower case for easier use
			switch(s.toLowerCase())
			{
				case("!help"):
					System.out.println("");
					System.out.println("---------------------------------------CS102 Tennis Database---------------------------------------");
					System.out.println("!Help			- Displays this message again.");
					System.out.println("!Matches		- Displays all matches.");
					System.out.println("!MatchesByPlayer	- Displays all matches a given player has participated in.");
					System.out.println("!Players		- Displays all players.");
					System.out.println("!InsertMatch		- Adds a match to the database.");
					System.out.println("!InsertPlayer		- Adds a player to the database.");
					System.out.println("!RemovePlayer		- Adds a player to the database.");
					System.out.println("!Import			- Imports all players and matches from a given file.");
					System.out.println("!Export			- Exports all players and matches to a given file.");
					System.out.println("!Clear			- Clears all players and matches from the database.");
					System.out.println("!Exit			- Terminates the program.");
					System.out.println("----------------------------------------------------------------------------------------------------");
					break;
					
				case("!matches"):
					try
					{
						tennisDatabase.printAllMatches();
					}
					catch(TennisDatabaseRuntimeException e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
				case("!matchesbyplayer"):
					System.out.println("Enter the 5 character unique ID of the player.");
					try
					{
						s = userScanner.nextLine().toUpperCase();
						tennisDatabase.printMatchesOfPlayer(s);
					}
					catch(TennisDatabaseException e)
					{
						System.out.println(e.getMessage());
					}
					catch(TennisDatabaseRuntimeException e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
				case("!players"):
					try
					{
						tennisDatabase.printAllPlayers();
					}
					catch(TennisDatabaseRuntimeException e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
				case("!insertmatch"):
					System.out.println("Enter your match in the following format: PLAYER ID/PLAYER ID/DATE/TOURNAMENET/SET SCORE, SET SCORE");
					try
					{
						s = userScanner.nextLine().toUpperCase();
						tennisDatabase.insertMatch("MATCH/" + s);
						System.out.println("Match successfully added.");
					}
					catch(TennisDatabaseException e)
					{
						System.out.println(e.getMessage());
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
				case("!insertplayer"):
					System.out.println("Enter your player in the following format: PLAYER ID/FIRST NAME/LAST NAME/BIRTH YEAR/COUNTRY");
					try
					{
						s = userScanner.nextLine().toUpperCase();
						tennisDatabase.insertPlayer("PLAYER/" + s);
						System.out.println("Player successfully added.");
					}
					catch(TennisDatabaseException e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
				case("!removeplayer"):
					System.out.println("Enter the 5 character unique ID of the player.");
					try
					{
						s = userScanner.nextLine().toUpperCase();
						tennisDatabase.removePlayer(s);
						System.out.println("Player and all associated matches successfully removed.");
					}
					catch(TennisDatabaseException e)
					{
						System.out.println(e.getMessage());
					}
					catch(TennisDatabaseRuntimeException e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
				case("!import"):
					int errors = 0;
					System.out.println("Please specify a file path.");
					try
					{
						s = userScanner.nextLine();
						tennisDatabase.readFromFile(s);
					}
					catch(TennisDatabaseRuntimeException e)
					{
						errors = 1;
					}
					catch(TennisDatabaseException e)
					{
						System.out.println(e.getMessage());
						errors = -1;
					}
					catch(Exception e)
					{
						errors = 1;
					}
					if(errors == 0)
						System.out.println("Import complete with no errors.");
					else if(errors == 1)
						System.out.println("Import complete, some data was invalid and discarded.");
					
					break;
					
				case("!export"):
					System.out.println("Please specify a file path.");
					System.out.println("WARNING: All data in the file will be overwritten.");
					try
					{
						s = userScanner.nextLine();
						tennisDatabase.writeToFile(s);
						System.out.println("Export complete.");
					}
					catch(IOException e)
					{
						System.out.println(e.getMessage());
					}
					catch(TennisDatabaseException e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
				case("!clear"):
					try
					{
						tennisDatabase.clear();
						System.out.println("Successfully cleared all data from the database.");
					}
					catch(TennisDatabaseException e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
				case("!exit"):
					System.out.println("");
					System.out.println("Terminating program.");
					System.out.println("");
					System.exit(0);
					break;
					
				case("!debug"):
					break;
					
				default:
					System.out.println("");
					System.out.println(s + " is an unknown command. use !Help to see a list of commands.");
					break;
			}
			System.out.println("");
		}
	}
}
