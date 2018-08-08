


package TennisDatabase;

// Interface (package-private) providing the specifications for the TennisPlayer class.
// See: https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
interface TennisPlayerInterface extends Comparable<TennisPlayer> {
   
   // Accessors (getters).
   public String getPlayerID();
   public String getFirstName();
   public String getLastName();
   public int getBirthYearNumeric();
   public String getCountry();
   
   // Desc.: Prints this tennis player on the console.
   public void print();
   
}


