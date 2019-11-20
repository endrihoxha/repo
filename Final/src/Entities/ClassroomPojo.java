package Entities;

import java.util.ArrayList;

public class ClassroomPojo {
	
	private Subject subject;
	private SubjectType subjectType;
	private Branch branch;
	private Year year;
	private Parallel parallel;
	private ArrayList<Student> students;
	private Professor professor; 

	
	public ClassroomPojo()
	{
		
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public Branch getBranch() {
		return branch;
	}


	public void setBranch(Branch branch) {
		this.branch = branch;
	}


	public Year getYear() {
		return year;
	}


	public void setYear(Year year) {
		this.year = year;
	}


	public Parallel getParallel() {
		return parallel;
	}


	public void setParallel(Parallel parallel) {
		this.parallel = parallel;
	}



	public Professor getProfessor() {
		return professor;
	}


	public void setProfessor(Professor professor) {
		this.professor = professor;
	}


	public ArrayList<Student> getStudents() {
		return students;
	}


	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}


	public SubjectType getSubjectType() {
		return subjectType;
	}


	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}
	
	
}

