package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import DBConnection.ConnectToDB;
import Entities.Branch;
import Entities.ClassRoom;
import Entities.Parallel;
import Entities.PojoPresences;
import Entities.Professor;
import Entities.Student;
import Entities.StudentsPresences;
import Entities.Subject;
import Entities.SubjectType;
import Entities.Year;
import View.Admin;
import View.ClassRoomView;
import View.MainPage;
import View.Prof;

public class ProfBean {
	
	private ConnectToDB c = new ConnectToDB();
	private int totalHours;
	
	
	public void populateComboboxBranches(JComboBox comboBranches,Professor loggedinProf)
	{
		ArrayList<Branch> listOfBranches = getListOfBranches(loggedinProf);
		if(listOfBranches.size()>0)
		{
			for (int i=0; i<listOfBranches.size();i++)
			{
				comboBranches.addItem(listOfBranches.get(i).getName());
			}
		}
		
	}
	
	public ArrayList<Branch> getListOfBranches(Professor prof)
	{
		ArrayList<Branch> listOfBranches = new ArrayList<Branch>();
		listOfBranches.add(new Branch());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("SELECT DISTINCT branch.* FROM SCHEDULE "
					+ " JOIN branch ON "
					+ " branch.idbranch= SCHEDULE.branch "
					+ " WHERE schedule.professor=? ");
			stm.setInt(1, prof.getIdProfessor());
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				Branch currentBranch = new Branch();
				currentBranch.setIdBranch(rs.getInt("branch.idbranch"));
				currentBranch.setName(rs.getString("branch.name"));
				listOfBranches.add(currentBranch);
			}
			
		}
		catch(SQLException ex)
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		return listOfBranches;
	}
	
	
	public void populateComboboxYears(JComboBox comboYears,String branch,Professor loggedinProf)
	{
		ArrayList<Year> listOfYears = getListOfYears(branch,loggedinProf);
		if(listOfYears.size()>0)
		{
			for (int i=0; i<listOfYears.size();i++)
			{
				if(listOfYears.get(i).getYear()==0)
				{
					comboYears.addItem(null);
				}
				else
				{
					comboYears.addItem(listOfYears.get(i).getYear());
				}				
			}
		}
	}
	
	public ArrayList<Year> getListOfYears(String selectedBranch,Professor prof)
	{
		ArrayList<Year> listOfYears = new ArrayList<Year>();
		listOfYears.add(new Year());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement(" SELECT DISTINCT year.* FROM SCHEDULE  "
					+ " JOIN branch ON  "
					+ " branch.idbranch= SCHEDULE.branch "
					+ " JOIN YEAR ON "
					+ " year.idyear = schedule.year "
					+ " WHERE branch.name =? "
					+ "AND schedule.professor=? ");
			stm.setString(1, selectedBranch);
			stm.setInt(2, prof.getIdProfessor());
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				Year currentYear = new Year();
				currentYear.setIdYear(rs.getInt("idyear"));
				currentYear.setYear(rs.getInt("current_year"));
				listOfYears.add(currentYear);
			}
			
		}
		catch(SQLException ex)
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		return listOfYears;
	}
	
	
	
	public void populateComboboxParallels(JComboBox comboParallels,String branch,Professor loggedinProf, int year)
	{
		ArrayList<Parallel> listOfParallels = getListOfParallels(branch,loggedinProf,year);
		if(listOfParallels.size()>0)
		{
			for (int i=0; i<listOfParallels.size();i++)
			{
				comboParallels.addItem(listOfParallels.get(i).getName());
			}
		}
		
	}
	
	public ArrayList<Parallel> getListOfParallels(String selectedBranch,Professor prof, int selectedYear)
	{
		ArrayList<Parallel> listOfParallels = new ArrayList<Parallel>();
		listOfParallels.add(new Parallel());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("SELECT DISTINCT parallel.* FROM SCHEDULE  "
					+ " JOIN branch ON  "
					+ " branch.idbranch= SCHEDULE.branch "
					+ " JOIN YEAR ON "
					+ " year.idyear = schedule.year "
					+ " JOIN parallel ON "
					+ " schedule.parallel= parallel.idparallel "
					+ " WHERE branch.name =? "
					+ " AND schedule.professor=?  "
					+ " AND year.current_year = ?");
			stm.setString(1, selectedBranch);
			stm.setInt(2, prof.getIdProfessor());
			stm.setInt(3, selectedYear);
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				Parallel currentParallel = new Parallel();
				currentParallel.setIdParallel(rs.getInt("idparallel"));
				currentParallel.setName(rs.getString("name"));
				listOfParallels.add(currentParallel);
			}
			
		}
		catch(SQLException ex)
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		return listOfParallels;
	}
	
	
	public void populateComboboxSubjects(JComboBox comboSubjects,String branch,int year, String parallel, Professor loggedinProf)
	{
		ArrayList<Subject> listOfSubjects = getListOfSubjects(branch,year,parallel,loggedinProf);
		if(listOfSubjects.size()>0)
		{
			for (int i=0; i<listOfSubjects.size();i++)
			{				
				comboSubjects.addItem(listOfSubjects.get(i).getName());				
			}
		}
	}
	
	public ArrayList<Subject> getListOfSubjects(String branch,int year, String parallel, Professor loggedinProf){
		ArrayList<Subject> listOfSubjects = new ArrayList<Subject>();
		listOfSubjects.add(new Subject());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("SELECT DISTINCT subject.* FROM SCHEDULE "
					+ " JOIN branch ON "
					+ " branch.idbranch= SCHEDULE.branch "
					+ " JOIN YEAR ON "
					+ " year.idyear = schedule.year "
					+ " JOIN parallel ON "
					+ " schedule.parallel= parallel.idparallel "
					+ " JOIN SUBJECT ON "
					+ " subject.idsubject=schedule.subject "
					+ " WHERE branch.name =? "
					+ " AND schedule.professor=?  "
					+ " AND year.current_year = ? "
					+ " AND parallel.name=? ");
			
			stm.setString(1, branch);
			stm.setInt(2, loggedinProf.getIdProfessor());
			stm.setInt(3, year);
			stm.setString(4, parallel);
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				Subject currentSubject = new Subject();
				currentSubject.setIdSubject(rs.getInt("subject.idsubject"));
				currentSubject.setName(rs.getString("subject.name"));
				listOfSubjects.add(currentSubject);
			}
			
		}
		catch(SQLException ex)
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		return listOfSubjects;
		
	}
	
	
	
	
	
	public void populateComboboxSubjectType(JComboBox comboSubjects,String branch,int year, String parallel,  String subject,Professor loggedinProf)
	{
		ArrayList<SubjectType> listOfSubjectTypes = getListOfSubjectTypes(branch,year,parallel,subject,loggedinProf);
		if(listOfSubjectTypes.size()>0)
		{
			for (int i=0; i<listOfSubjectTypes.size();i++)
			{				
				comboSubjects.addItem(listOfSubjectTypes.get(i).getSubjectTypeName());				
			}
		}
	}
	
	public ArrayList<SubjectType> getListOfSubjectTypes(String branch,int year, String parallel,  String subject,Professor loggedinProf){
		ArrayList<SubjectType> listOfSubjectTypes = new ArrayList<SubjectType>();
		listOfSubjectTypes.add(new SubjectType());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("SELECT DISTINCT subject_type.* FROM SCHEDULE "
					+ " JOIN branch ON "
					+ " branch.idbranch= SCHEDULE.branch "
					+ " JOIN YEAR ON "
					+ " year.idyear = schedule.year "
					+ " JOIN parallel ON "
					+ " schedule.parallel= parallel.idparallel "
					+ " JOIN SUBJECT ON "
					+ " subject.idsubject=schedule.subject "
					+ "JOIN subject_type ON "
					+ " subject_type.idsubject_type=schedule.subject_type "
					+ " WHERE branch.name = ? "
					+ " AND schedule.professor = ?  "
					+ " AND year.current_year = ? "
					+ " AND parallel.name = ? "
					+ " AND subject.name = ?");
			
			stm.setString(1, branch);
			stm.setInt(2, loggedinProf.getIdProfessor());
			stm.setInt(3, year);
			stm.setString(4, parallel);
			stm.setString(5, subject);
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				SubjectType currentSubjectType = new SubjectType();
				currentSubjectType.setIdSubjectType(rs.getInt("subject_type.idsubject_type"));
				currentSubjectType.setSubjectTypeName(rs.getString("subject_type.name"));
				listOfSubjectTypes.add(currentSubjectType);
			}
			
		}
		catch(SQLException ex)
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		return listOfSubjectTypes;
		
	}
	
	
	public ArrayList<PojoPresences> getPresences(String subjectType,String branch,int year, String parallel,  String subject,Professor loggedinProf){
		ArrayList<PojoPresences> listPresences = new ArrayList<PojoPresences>();
		
		
		ArrayList<StudentsPresences> studentsPresences = new ArrayList<StudentsPresences>();
		
		ArrayList<Student> studentsList = new ArrayList<Student>();
		
		
		try
		{
			Connection con = c.connect();
			
			PreparedStatement totHours = con.prepareStatement("SELECT COUNT(*) as tot FROM presence "
					+ " JOIN branch ON "
					+ " branch.idbranch = presence.branch "
					+ " JOIN YEAR ON "
					+ " year.idyear = presence.year "
					+ " JOIN parallel ON "
					+ " parallel.idparallel = presence.parallel "
					+ " JOIN SUBJECT ON  "
					+ " subject.idsubject = presence.subject "
					+ " JOIN subject_type ON "
					+ " subject_type.idsubject_type = presence.subject_type "
					+ " JOIN professor ON "
					+ " professor.idprofessor = presence.professor "
					+ " WHERE presence.professor = ? "
					+ " AND branch.name = ? "
					+ " AND year.current_year = ? "
					+ " AND parallel.name = ? "
					+ " AND subject.name = ? "
					+ " AND subject_type.name= ? ;");
			
			PreparedStatement totStudentPresence = con.prepareStatement("SELECT student.*, COUNT(*)  AS student_presence "
					+ " FROM presence  "
					+ " JOIN branch ON "
					+ " branch.idbranch = presence.branch "
					+ " JOIN YEAR ON "
					+ " year.idyear = presence.year "
					+ " JOIN parallel ON "
					+ " parallel.idparallel = presence.parallel "
					+ " JOIN SUBJECT ON  "
					+ " subject.idsubject = presence.subject "
					+ " JOIN subject_type ON "
					+ " subject_type.idsubject_type = presence.subject_type "
					+ " JOIN student  "
					+ " ON student.`idstudent` = presence.`student` "
					+ " WHERE presence.professor IS NULL "
					+ " AND branch.name = ? "
					+ " AND year.current_year = ? "
					+ " AND parallel.name = ? "
					+ " AND subject.name = ? "
					+ " AND subject_type.name= ? "
					+ " GROUP BY presence.student ;");
			
			PreparedStatement students = con.prepareStatement("SELECT * FROM student  "
					+ " JOIN branch ON "
					+ " branch.idbranch = student.branch "
					+ " JOIN YEAR ON "
					+ " year.idyear = student.year "
					+ " JOIN parallel ON "
					+ " parallel.idparallel = student.parallel "
					+ " WHERE branch.name = ? "
					+ " AND year.current_year = ? "
					+ " AND parallel.name = ? ");			
			totHours.setInt(1, loggedinProf.getIdProfessor());
			totHours.setString(2, branch);
			totHours.setInt(3, year);
			totHours.setString(4, parallel);
			totHours.setString(5, subject);
			totHours.setString(6, subjectType);
			
			totStudentPresence.setString(1, branch);
			totStudentPresence.setInt(2, year);
			totStudentPresence.setString(3, parallel);
			totStudentPresence.setString(4, subject);
			totStudentPresence.setString(5, subjectType);
			
			students.setString(1, branch);
			students.setInt(2, year);
			students.setString(3, parallel);
			
			ResultSet rs = totHours.executeQuery();
			ResultSet rs2 = totStudentPresence.executeQuery();
			ResultSet rs3 = students.executeQuery();
			if (rs.first())
			{
				totalHours=rs.getInt(1);
			}
			while(rs2.next())
			{
				Student newStudent = new Student();
				StudentsPresences studentPresence = new StudentsPresences();
				
				newStudent.setIdStudent(rs2.getInt("student.idstudent"));
				newStudent.setName(rs2.getString("student.name"));
				newStudent.setSurname(rs2.getString("student.surname"));
				studentPresence.setStudent(newStudent);
				studentPresence.setPresence(rs2.getInt("student_presence"));
				studentsPresences.add(studentPresence);
			}
			while(rs3.next())
			{
				Student student = new Student();
				student.setIdStudent(rs3.getInt("idstudent"));
				student.setName(rs3.getString("name"));
				student.setSurname(rs3.getString("surname"));
				studentsList.add(student);
			}
			
			for(int i=0;i<studentsList.size();i++){
				String equal = "false";
				for(int j=0;j<studentsPresences.size();j++){					
					if(studentsList.get(i).getIdStudent()==studentsPresences.get(j).getStudent().getIdStudent()){
						equal="true";
						PojoPresences present = new PojoPresences();
						present.setStudent(studentsList.get(i));
						present.setTotal(totalHours);
						present.setPresence(studentsPresences.get(j).getPresence());
						present.setAbsence(totalHours-studentsPresences.get(j).getPresence());
						listPresences.add(present);
						break;						
					}
				}
				if(equal.compareToIgnoreCase("false")==0){
					PojoPresences absent = new PojoPresences();
					absent.setStudent(studentsList.get(i));
					absent.setTotal(totalHours);
					absent.setPresence(0);
					absent.setAbsence(totalHours);
					listPresences.add(absent);
				}
				
			}
						
		}
		catch (SQLException ex) 
		{
			System.err.println("Problem lidhje me bazen e te dhenave");
		}
		finally 
		{
			c.disconnect();
		}
		
		return listPresences;
	}

}
