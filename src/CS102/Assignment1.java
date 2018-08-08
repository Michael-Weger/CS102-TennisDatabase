package CS102;

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
		
		tennisDatabase.loadFromFile(args[0]);
		
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
					tennisDatabase.printAllMatches();
					System.out.println("");
					break;
					
				case("!matchesbyplayer"):
					System.out.println("Enter the 5 character unique ID of the player.");
					System.out.println("");
					tennisDatabase.printMatchesOfPlayer(userScanner.nextLine());
					System.out.println("");
					break;
					
				case("!players"):
					System.out.println("");
					tennisDatabase.printAllPlayers();
					System.out.println("");
					break;
					
				case("!insertmatch"):
					System.out.println("Enter your match in the following format: PLAYER ID/PLAYER ID/DATE/TOURNAMENET/SET SCORE, SET SCORE");
					tennisDatabase.insertMatch("MATCH/" + userScanner.nextLine(), true);
					System.out.println("");
					break;
					
				case("!insertplayer"):
					System.out.println("Enter your player in the following format: PLAYER ID/FIRST NAME/LAST NAME/BIRTH YEAR/NATIONALITY");
					tennisDatabase.insertPlayer("PLAYER/" + userScanner.nextLine(), true);
					System.out.println("");
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
					System.out.println(s + " is an unknown command. use !Help to see a list of commands.");
					break;
			}
		}
	}
}
