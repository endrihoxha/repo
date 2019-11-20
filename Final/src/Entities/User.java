package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;



public class User {
	
	private int id;
	private String name;
	private String surname;
	private int role;
	private byte finger[];
	private String username;
	private String password;
	
	public User()
	{
		
	}
	
	public User(int id , String name , String surname ,byte finger[], int role, String username, String password)
	{
		this.id = id;
		this.name= name;
		this.surname=surname;
		this.role=role;
		this.finger = finger;
		this.username=username;
		this.password=password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getRoleId() {
		return role;
	}

	public void setRoleId(int roleId) {
		this.role = roleId;
	}

	public byte[] getFinger() {
		return finger;
	}

	public void setFinger(byte finger[]) {
		this.finger = finger;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

private User convertRowToUser(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("idusers");
		String name = myRs.getString("name");
		String surname = myRs.getString("surname");
		byte finger[] = myRs.getBytes("finger");
		int role = myRs.getInt("role");
		String username = myRs.getString("username");
		String password = myRs.getString("password");
 		
		User user = new User(id, name, surname, finger, role ,username,password);
		
		return user;
	}

	

}

	
