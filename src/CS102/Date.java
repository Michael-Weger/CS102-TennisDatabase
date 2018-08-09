package CS102;

public class Date {
	
	private String m_Year;		// The year
	private String m_Month;		// The month
	private String m_Day;		// The date
	public char m_Separator;	// The character which separates each value
	
	
	/**
	 * Constructor
	 * @param date The string from which to create the date. Must be YYYYMMDD
	 * @param seperator The character which will later separate the date values
	 */
	public Date(String date, char seperator)
	{
		m_Year = date.substring(0, 4);
		m_Month = date.substring(4, 6);
		m_Day = date.substring(6, 8);
		m_Separator = '-';
	}
	
	/**
	 * @return Returns the year of this date as a string.
	 */
	public String getYear()
	{
		return m_Year;
	}

	/**
	 * @return Returns the month of this date as a string.
	 */
	public String getMonth()
	{
		return m_Month;
	}

	/**
	 * @return Returns the day of this date as a string.
	 */
	public String getDay()
	{
		return m_Day;
	}

	/**
	 * @return Returns the year of this date as an integer.
	 */
	public int getYearNumeric()
	{
		return Integer.parseInt(m_Year);
	}

	/**
	 * @return Returns the month of this date as an integer.
	 */
	public int getMonthNumeric()
	{
		return Integer.parseInt(m_Month);
	}

	/**
	 * @return Returns the year of this date as a string.
	 */
	public int getDayNumeric()
	{
		return Integer.parseInt(m_Day);
	}

	/**
	 * @return Returns the day of this date as an integer.
	 */
	public int getDateNumeric()
	{
		return Integer.parseInt(m_Year + m_Month + m_Day);
	}
	
	public void setSeperator(char c)
	{
		m_Separator = c;
	}
	
	/**
	 * @return Returns this date in the YYYY-MM-DD format
	 */
	public String getYYYYMMDD()
	{
		return m_Year + m_Separator + m_Month + m_Separator + m_Day;
	}
	
	/**
	 * @return Returns this date in the MM-DD-YYYY format
	 */
	public String getMMDDYYYY()
	{
		return m_Month + m_Separator + m_Day + m_Separator + m_Year;
	}
	
	/**
	 * @return Returns this date in the DD-MM-YYYY format
	 */
	public String getDDMMYYYY()
	{
		return m_Day + m_Separator + m_Month + m_Separator + m_Year;
	}
	
}
