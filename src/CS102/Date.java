package CS102;

public class Date {
	
	private String m_Year;
	private String m_Month;
	private String m_Day;
	public char m_Seperator;
	
	
	// Imports a date format YYYYMMDD
	public Date(String date, char seperator)
	{
		m_Year = date.substring(0, 4);
		m_Month = date.substring(4, 6);
		m_Day = date.substring(6, 9);
		m_Seperator = '-';
	}
	
	public String getYear()
	{
		return m_Year;
	}
	
	public String getMonth()
	{
		return m_Month;
	}
	
	public String getDay()
	{
		return m_Day;
	}
	
	public int getYearNumeric()
	{
		return Integer.parseInt(m_Year);
	}
	
	public int getMonthNumeric()
	{
		return Integer.parseInt(m_Month);
	}
	
	public int getDayNumeric()
	{
		return Integer.parseInt(m_Day);
	}
	
	public int getDateNumeric()
	{
		return Integer.parseInt(m_Year + m_Month + m_Day);
	}
	
	public void setSeperator(char c)
	{
		m_Seperator = c;
	}
	
	public String getYYYYMMDD()
	{
		return m_Year + m_Seperator + m_Month + m_Seperator + m_Day;
	}
	
	public String getMMDDYYYY()
	{
		return m_Month + m_Seperator + m_Day + m_Seperator + m_Year;
	}
	
	public String getDDMMYYYY()
	{
		return m_Day + m_Seperator + m_Month + m_Seperator + m_Year;
	}
	
}
