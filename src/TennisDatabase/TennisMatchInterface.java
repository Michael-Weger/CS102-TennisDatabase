


package TennisDatabase;

// Interface (package-private) providing the specifications for the TennisMatch class.
// See: https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
interface TennisMatchInterface extends Comparable<TennisMatch> {
   
   // Accessors (getters).
   public TennisPlayer getPlayer1();
   public TennisPlayer getPlayer2();
   public int getDateYear();
   public int getDateMonth();
   public int getDateDay();
   public String getTournament();
   public String getMatchScore();
   public int getWinner();
   
   // Desc.: Prints this tennis match on the console.
   public void print();
   
}


