package Entities;

public class Year {
	
	private int idYear;
	private int year;
	
	public Year()
	{
		
	}
	
	public Year(int idYear, int year) 
	{
		this.idYear=idYear;
		this.year=year;
	}

	public int getIdYear() {
		return idYear;
	}

	public void setIdYear(int idYear) {
		this.idYear = idYear;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
}
