package Entities;

public class Branch {

	private int idBranch;
	private String name;
	
	public Branch ()
	{
		
	}
	
	public Branch (int idBranch, String name)
	{
		this.idBranch=idBranch;
		this.name=name;
	}

	public int getIdBranch() {
		return idBranch;
	}

	public void setIdBranch(int idBranch) {
		this.idBranch = idBranch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
