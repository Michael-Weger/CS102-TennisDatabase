package TennisDatabase;

public interface TennisMatchInterface extends Comparable<TennisMatch>{
	
	// Accessor functions
	
	public String getPlayer1ID();
	public String getPlayer2ID();
	public int getDateYear();
	public int getDateMonth();
	public int getDateDay();
	public String getTournament();
	public String getMatchScore();
	public int getWinner();
	
	public int compareTo(TennisMatch other);
}
