package CS102;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import TennisDatabase.TennisDatabase;

public class Assignment1 {
	
	static TennisDatabase tennisDatabase;
	static Scanner fileScanner;
	static Scanner userScanner;
	
	public static void main(String args[])
	{
		tennisDatabase = new TennisDatabase();
		userScanner = new Scanner(System.in);
		File dataFile = new File(args[0]);
		
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
		
		while(fileScanner.hasNextLine())
		{
			String importedData = fileScanner.next();
			
			if(importedData.substring(0, 6).equals("MATCH/"))
				tennisDatabase.addMatch(importedData, false);
			else if(importedData.substring(0, 7).equals("PLAYER/"))
				tennisDatabase.addPlayer(importedData, false);
		}
		
		System.out.println("");
		System.out.println("---------------------------------------CS102 Tennis Database---------------------------------------");
		System.out.println("!Help				- Displays this message again.");
		System.out.println("!Matches			- Displays all matches.");
		System.out.println("!MatchesByPlayer	- Displays all matches a given player has participated in.");
		System.out.println("!Players			- Displays all players.");
		System.out.println("!InsertMatch		- Adds a match to the database.");
		System.out.println("!InsertPlayer		- Adds a player to the database.");
		System.out.println("!Exit				- Terminates the program.");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("");
		
		
		while(true)
		{
			String s = userScanner.nextLine();
			switch(s.toLowerCase())
			{
				case("!help"):
					System.out.println("");
					System.out.println("---------------------------------------CS102 Tennis Database---------------------------------------");
					System.out.println("!Help				- Displays this message again.");
					System.out.println("!Matches			- Displays all matches.");
					System.out.println("!MatchesByPlayer	- Displays all matches a given player has participated in.");
					System.out.println("!Players			- Displays all players.");
					System.out.println("!InsertMatch		- Adds a match to the database.");
					System.out.println("!InsertPlayer		- Adds a player to the database.");
					System.out.println("!Exit				- Terminates the program.");
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
					tennisDatabase.addMatch(userScanner.nextLine(), true);
					break;
					
				case("!insertplayer"):
					System.out.println("Enter your player in the following format: PLAYER ID/FIRST NAME/LAST NAME/BIRTH YEAR/NATIONALITY");
					tennisDatabase.addPlayer(userScanner.nextLine(), true);
					break;
					
				case("!exit"):
					System.out.println("Terminating program.");
					System.exit(0);
					break;
					
				default:
					System.out.println(s + " is an unknown command. use !Help to see a list of commands.");
					break;
			}
		}
	}
	
}
