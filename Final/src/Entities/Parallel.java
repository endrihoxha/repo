package Entities;

public class Parallel {

	private int idParallel;
	private String name;
	
	public Parallel()
	{
		
	}
	
	public Parallel(int idParallel ,String name )
	{
		this.idParallel=idParallel;
		this.name=name;
	}

	public int getIdParallel() {
		return idParallel;
	}

	public void setIdParallel(int idParallel) {
		this.idParallel = idParallel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
