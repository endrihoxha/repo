package Entities;

import java.util.Date;

public class Student {
	
	private int idStudent;
	private String name;
	private String surname;
	private String fathersName;
	private Date birthdate;
	private int branch;
	private int year;
	private int parallel;
	private byte fingerprint[]; 	

	public Student ()
	{
		
	}
	
	public Student (int idStudent, String name, String surname,  String fathersName, Date birthdate,  int branch,  int year, int parallel,  byte fingerprint[])
	{
		this.idStudent = idStudent;
		this.name=name;
		this.surname=surname;
		this.fathersName=fathersName;
		this.birthdate=birthdate;
		this.branch=branch;
		this.year=year;
		this.parallel=parallel;
		this.fingerprint=fingerprint;
	}

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
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

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getParallel() {
		return parallel;
	}

	public void setParallel(int parallel) {
		this.parallel = parallel;
	}

	public byte[] getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(byte[] fingerprint) {
		this.fingerprint = fingerprint;
	}
	
	
	
	
	
	
}
