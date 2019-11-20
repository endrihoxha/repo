package Entities;

public class Schedule {
	private int idSchedule;
	private int dayWeek;
	private int time;
	private int subject;
	private int subjectType;
	private int branch;
	private int year;
	private int parallel;
	private int professor;
	private int classroom;
	
	
	public Schedule()
	{
		
	}
	
	public int getIdSchedule() {
		return idSchedule;
	}
	public void setIdSchedule(int idSchedule) {
		this.idSchedule = idSchedule;
	}
	public int getDayWeek() {
		return dayWeek;
	}
	public void setDayWeek(int dayWeek) {
		this.dayWeek = dayWeek;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getSubject() {
		return subject;
	}
	public void setSubject(int subject) {
		this.subject = subject;
	}
	public int getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
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
	public int getProfessor() {
		return professor;
	}
	public void setProfessor(int professor) {
		this.professor = professor;
	}
	public int getClassroom() {
		return classroom;
	}
	public void setClassroom(int classroom) {
		this.classroom = classroom;
	}		
	
}
