package Entities;

public class ClassRoom {

	private int idClassroom;
	private String name;
	private String password;
	private int classroomType;
	
	public ClassRoom()
	{
		
	}

	public int getIdClassroom() {
		return idClassroom;
	}

	public void setIdClassroom(int idClassroom) {
		this.idClassroom = idClassroom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getClassroomType() {
		return classroomType;
	}

	public void setClassroomType(int classroomType) {
		this.classroomType = classroomType;
	}
	
	
	
	
}
