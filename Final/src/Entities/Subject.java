package Entities;

public class Subject {

	private int idSubject;
	private String name;
	private int idBranch;
	private int idYear;
	
	public Subject ()
	{
		
	}
	
	public Subject (int idSubject, String name)
	{
		this.idSubject=idSubject;
		this.name=name;
	}

	public int getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(int idSubject) {
		this.idSubject = idSubject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdBranch() {
		return idBranch;
	}

	public void setIdBranch(int idBranch) {
		this.idBranch = idBranch;
	}

	public int getIdYear() {
		return idYear;
	}

	public void setIdYear(int idYear) {
		this.idYear = idYear;
	}
}
