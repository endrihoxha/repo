package Entities;

public class ScheduleDataPojo {

	private Schedule schedule;
	private DayWeek dayWeek;
	private Time scheduleTime;
	private Subject subject;
	private SubjectType subjectType;
	private Branch branch;
	private Year year;
	private Parallel parallel;
	private Professor professor;
	private ClassRoom classroom;
	
	
	public ScheduleDataPojo()
	{
		
	}


	public Schedule getSchedule() {
		return schedule;
	}


	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}


	public DayWeek getDayWeek() {
		return dayWeek;
	}


	public void setDayWeek(DayWeek dayWeek) {
		this.dayWeek = dayWeek;
	}


	public Time getScheduleTime() {
		return scheduleTime;
	}


	public void setScheduleTime(Time scheduleTime) {
		this.scheduleTime = scheduleTime;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public SubjectType getSubjectType() {
		return subjectType;
	}


	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
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


	public ClassRoom getClassroom() {
		return classroom;
	}


	public void setClassroom(ClassRoom classroom) {
		this.classroom = classroom;
	}
	
	
}
