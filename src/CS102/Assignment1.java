package CS102;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import TennisDatabase.TennisDatabase;

/**
 * Main class of the project
 * 
 * @author Michael Weger
 *
 */
public class Assignment1 {
	
	static TennisDatabase tennisDatabase;
	static Scanner fileScanner;
	static Scanner userScanner;
	
	public static void main(String args[])
	{
		tennisDatabase = new TennisDatabase();
		userScanner = new Scanner(System.in);
		File dataFile = new File(args[0]);
		
		// Attempt to load the data file 
		if(!dataFile.exists())
		{
			System.out.println("File not found, terminating program.");
			System.exit(1);
		}
		else if(!dataFile.canRead() || !args[0].contains(".txt"))
		{
			System.out.println("File is of an invalid format, terminating program.");
			System.exit(1);
		}
		else
		{
			try
			{
				fileScanner = new Scanner(dataFile);
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File not found, terminating program.");
				System.out.println("");
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		// Import all valid data from the file
		while(fileScanner.hasNextLine())
		{
			String importedData = fileScanner.nextLine();
			
			// Make sure the line is long enough to determine if its a match or a player before continuing the validity test
			if(importedData.length() < 7)
			{
				// Determine if the given line of text is for a match or a player
				if(importedData.substring(0, 6).equals("MATCH/"))
					tennisDatabase.addMatch(importedData, false);
				else if(importedData.substring(0, 7).equals("PLAYER/"))
					tennisDatabase.addPlayer(importedData, false);
			}
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
					System.out.println("!Exit			- Terminates the program.");
					System.out.println("----------------------------------------------------------------------------------------------------");
					System.out.println("");
					break;
					
				case("!matches"):
					System.out.println("");
					tennisDatabase.printMatches();
					break;
					
				case("!matchesbyplayer"):
					System.out.println("Enter the 5 character unique ID of the player.");
					System.out.println("");
					tennisDatabase.printMatches(userScanner.nextLine());
					break;
					
				case("!players"):
					System.out.println("");
					// tennisDatabase.printPlayers();
					break;
					
				case("!insertmatch"):
					System.out.println("Enter your match in the following format: PLAYER ID/PLAYER ID/DATE/TOURNAMENET/SET SCORE, SET SCORE");
					tennisDatabase.addMatch("MATCH/" + userScanner.nextLine(), true);
					break;
					
				case("!insertplayer"):
					System.out.println("Enter your player in the following format: PLAYER ID/FIRST NAME/LAST NAME/BIRTH YEAR/NATIONALITY");
					tennisDatabase.addPlayer(userScanner.nextLine(), true);
					break;
					
				case("!exit"):
					System.out.println("Terminating program.");
					System.exit(0);
					break;
					
				case("!debug"):
					break;
					
				default:
					System.out.println(s + " is an unknown command. use !Help to see a list of commands.");
					break;
			}
		}
	}
}
