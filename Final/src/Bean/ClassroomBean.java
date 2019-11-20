package Bean;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.verification.DPFPVerification;

import DBConnection.ConnectToDB;
import Entities.Branch;
import Entities.ClassRoom;
import Entities.ClassroomPojo;
import Entities.DayWeek;
import Entities.Parallel;
import Entities.Professor;
import Entities.Schedule;
import Entities.ScheduleDataPojo;
import Entities.Student;
import Entities.StudentDataPojo;
import Entities.Subject;
import Entities.SubjectType;
import Entities.Time;
import Entities.Year;

public class ClassroomBean {

	private ConnectToDB c = new ConnectToDB();
	
	private DPFPCapture reader = DPFPGlobal.getCaptureFactory().createCapture();
	private DPFPEnrollment enroll = DPFPGlobal.getEnrollmentFactory().createEnrollment();
	private DPFPVerification verify = DPFPGlobal.getVerificationFactory().createVerification();
	private DPFPTemplate template ; 
	public static String TEMPLATE_PROPERTY = "template";
	public DPFPFeatureSet featureInscription;
	public DPFPFeatureSet featureVerification;
	private PropertyChangeSupport changeSupport;
	
	private StudentDataPojo convertRowToStudentData(ResultSet myRs) throws SQLException {
		
		int idStudent = myRs.getInt("idstudent");
		String name = myRs.getString("name");
		String surname = myRs.getString("surname");
		String fathersName = myRs.getString("fathers_name");
		Date birthdate = myRs.getDate("birthdate");
		int branch = myRs.getInt("branch");
		int year = myRs.getInt("year");
		int parallel = myRs.getInt("parallel");
		byte fingerprint[]=myRs.getBytes("fingerprint");
						
		Student tempStudent = new Student( idStudent,  name,  surname,   fathersName,  birthdate,   branch,   year,  parallel, fingerprint );
		


	int idDega = myRs.getInt("branch.idbranch");
	String degaName = myRs.getString("branch.name");
	Branch tempBranch=new Branch(idDega,degaName);
	
	
	int idYear = myRs.getInt("year.idyear");
	int currentYear = myRs.getInt("year.current_year");
	Year tempYear = new Year(idYear,currentYear);
	
	
	
	int idParallel=myRs.getInt("parallel.idparallel");
	String parallelName = myRs.getString("parallel.name");
	Parallel tempParallel = new Parallel(idParallel,parallelName);
	
	StudentDataPojo tempStudentData = new StudentDataPojo();
	tempStudentData.setStudent(tempStudent);
	tempStudentData.setDega(tempBranch);
	tempStudentData.setViti(tempYear);
	tempStudentData.setParaleli(tempParallel);
	
	return tempStudentData;
	
	
}
	
	public ClassroomPojo getProperData (int currHour,int classroom ,int weekDay)
	{
		ClassroomPojo properData = new ClassroomPojo();

		
		try {
			Connection con = c.connect();
			PreparedStatement stmt = con.prepareStatement(" select * from schedule "
					+ " join day_week on day_week.idday_week = schedule.day_week "
					+ " join time on time.idtime = schedule.time "
					+ " join subject on subject.idsubject = schedule.subject "
					+ " join subject_type on subject_type.idsubject_type = schedule.subject_type "
					+ " join branch on branch.idbranch = schedule.branch "
					+ " join year on year.idyear = schedule.year "
					+ " join parallel on parallel.idparallel = schedule.parallel "
					+ " join professor on professor.idprofessor = schedule.professor "
					+ " join classroom on classroom.idclassroom = schedule.classroom "
					+ " where schedule.time = ? and schedule.day_week = ? and schedule.classroom = ? ");
			stmt.setInt(1, currHour);
			stmt.setInt(2, weekDay);
			stmt.setInt(3, classroom);
			
			ResultSet myRs = stmt.executeQuery();

			while (myRs.next())
			{ 
				ClassroomPojo classroomPojo = new ClassroomPojo();
				//						classroomPojo = convertRowToClassroomPojo(rs);
				
				Subject lenda = new Subject();
				 lenda.setIdSubject(myRs.getInt("subject.idsubject"));
				 lenda.setName(myRs.getString("subject.name"));
				 lenda.setIdBranch(myRs.getInt("subject.idbranch"));
				 lenda.setIdYear(myRs.getInt("subject.idyear"));
				 
				 SubjectType tipiLendes = new SubjectType();
				 tipiLendes.setIdSubjectType(myRs.getInt("subject_type.idsubject_type"));
				 tipiLendes.setSubjectTypeName(myRs.getString("subject_type.name"));
				 
				 Branch dega = new Branch();
				 dega.setIdBranch(myRs.getInt("branch.idbranch"));
				 dega.setName(myRs.getString("branch.name"));
				 
				 Year viti = new Year();
				 viti.setIdYear(myRs.getInt("year.idyear"));
				 viti.setYear(myRs.getInt("year.current_year"));
				 
				 Parallel paraleli = new Parallel();
				 paraleli.setIdParallel(myRs.getInt("parallel.idparallel"));
				 paraleli.setName(myRs.getString("parallel.name"));
				 
				 Professor pedagog = new Professor();
				 pedagog.setIdProfessor(myRs.getInt("professor.idprofessor"));
				 pedagog.setName(myRs.getString("professor.name"));
				 pedagog.setSurname(myRs.getString("professor.surname"));
				 pedagog.setFingerprint(myRs.getBytes("professor.fingerprint"));
				 
				 ArrayList<Student> students = new  ArrayList<Student>();
				 students = getProperStudents(myRs.getInt("branch.idbranch"),
						 myRs.getInt("year.idyear"),myRs.getInt("parallel.idparallel"));
				 
				 classroomPojo.setBranch(dega);
				 classroomPojo.setParallel(paraleli);
				 classroomPojo.setYear(viti);
				 classroomPojo.setSubject(lenda);
				 classroomPojo.setSubjectType(tipiLendes);
				 classroomPojo.setProfessor(pedagog);
				 classroomPojo.setStudents(students);
				 
				System.out.println(myRs.getInt("branch.idbranch") +" "+ myRs.getString("branch.name"));
				
				
				
				properData = classroomPojo;
				registerSubjectToPresence(properData);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}		
		
		return properData;
	}
	
	
	private ArrayList<Student> getProperStudents(int branch, int year , int parallel)
	{
		ArrayList<Student> students = new ArrayList<Student>();		
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement(" select * from student "
					+ "where year = ? and branch = ? and parallel = ? ");
			stm.setInt(1, year);
			stm.setInt(2, branch);
			stm.setInt(3, parallel);
			ResultSet myRs = stm.executeQuery();
			while(myRs.next())
			{
				Student student = new Student();
				student.setIdStudent(myRs.getInt("idstudent"));
				student.setName(myRs.getString("name"));
				student.setSurname(myRs.getString("surname"));
				student.setFathersName(myRs.getString("fathers_name"));
				student.setBirthdate(myRs.getDate("birthdate"));
				student.setFingerprint(myRs.getBytes("fingerprint"));
				student.setBranch(branch);
				student.setYear(year);
				student.setParallel(parallel);
				
				students.add(student);
			}
		}
		catch (SQLException ex) 
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		System.out.println("Studentet e ketij grupi: "+ students.size());
		return students;
		
	}
	
	
	public void registerSubjectToPresence(ClassroomPojo properData){
		if(properData.getSubject()!=null){
			try{
				Connection con = c.connect();
				
				PreparedStatement searchForExisting = con.prepareStatement("SELECT * FROM presence WHERE DATEDIFF(TIME,NOW())=0"
						+ " AND subject =? AND subject_type = ? AND branch=? "
						+ " AND year = ? AND parallel = ?");
				searchForExisting.setInt(1, properData.getSubject().getIdBranch());
				searchForExisting.setInt(2, properData.getSubjectType().getIdSubjectType());
				searchForExisting.setInt(3, properData.getBranch().getIdBranch());
				searchForExisting.setInt(4, properData.getYear().getIdYear());
				searchForExisting.setInt(5, properData.getParallel().getIdParallel());
				ResultSet rs = searchForExisting.executeQuery();
				if(!rs.next()){//nqs nuk ekziton kjo lende ne orar atehere e shtojme
					PreparedStatement saveStmt = con.prepareStatement("INSERT INTO presence(subject,subject_type,branch,year,parallel,time)"
							+ "values (?,?,?,?,?,?)");
					saveStmt.setInt(1, properData.getSubject().getIdBranch());
					saveStmt.setInt(2, properData.getSubjectType().getIdSubjectType());
					saveStmt.setInt(3, properData.getBranch().getIdBranch());
					saveStmt.setInt(4, properData.getYear().getIdYear());
					saveStmt.setInt(5, properData.getParallel().getIdParallel());
					saveStmt.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
					saveStmt.execute();
					saveStmt.close();
				}				
			}
			catch (SQLException ex) {
				System.err.println("Gabim ne ruajtjen e te dhenave te lendes");
			}
			finally {
				c.disconnect();
			}
		}		
	}
	
	
	public String registerStudentPresence(ClassroomPojo properData, Student student){
		String result="";
		try{
			Connection con = c.connect();
			
			PreparedStatement searchForExisting = con.prepareStatement("SELECT * FROM presence WHERE DATEDIFF(TIME,NOW())=0"
					+ " AND subject =? AND subject_type = ? AND branch=? "
					+ " AND year = ? AND parallel = ? AND student = ?");
			searchForExisting.setInt(1, properData.getSubject().getIdBranch());
			searchForExisting.setInt(2, properData.getSubjectType().getIdSubjectType());
			searchForExisting.setInt(3, properData.getBranch().getIdBranch());
			searchForExisting.setInt(4, properData.getYear().getIdYear());
			searchForExisting.setInt(5, properData.getParallel().getIdParallel());
			searchForExisting.setInt(6, student.getIdStudent());
			ResultSet rs = searchForExisting.executeQuery();
			if(!rs.next()){//nqs nuk ekziton ky student ne listprezence, atehere e shtojme
				PreparedStatement saveStmt = con.prepareStatement("INSERT INTO presence(subject,subject_type,branch,year,parallel,student,time)"
						+ "values (?,?,?,?,?,?,?)");
				saveStmt.setInt(1, properData.getSubject().getIdBranch());
				saveStmt.setInt(2, properData.getSubjectType().getIdSubjectType());
				saveStmt.setInt(3, properData.getBranch().getIdBranch());
				saveStmt.setInt(4, properData.getYear().getIdYear());
				saveStmt.setInt(5, properData.getParallel().getIdParallel());
				saveStmt.setInt(6, student.getIdStudent());
				saveStmt.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
				saveStmt.execute();
				saveStmt.close();
				result = "OK";
			}
			else{
				result = "already_exists";
			}
		}
		catch (SQLException ex) {
			System.err.println("Gabim ne regjistrimin e prezences se studentit");
		}
		finally {
			c.disconnect();
		}
		return result;
	}
	
	
	public String registerProfessorPresence(ClassroomPojo properData){
		String result="";
		try{
			Connection con = c.connect();
			
			PreparedStatement searchForExisting = con.prepareStatement("SELECT * FROM presence WHERE DATEDIFF(TIME,NOW())=0"
					+ " AND subject =? AND subject_type = ? AND branch=? "
					+ " AND year = ? AND parallel = ? AND professor = ?");
			searchForExisting.setInt(1, properData.getSubject().getIdBranch());
			searchForExisting.setInt(2, properData.getSubjectType().getIdSubjectType());
			searchForExisting.setInt(3, properData.getBranch().getIdBranch());
			searchForExisting.setInt(4, properData.getYear().getIdYear());
			searchForExisting.setInt(5, properData.getParallel().getIdParallel());
			searchForExisting.setInt(6, properData.getProfessor().getIdProfessor());
			ResultSet rs = searchForExisting.executeQuery();
			if(!rs.next()){//nqs nuk ekziton ky profesor ne listprezence, atehere e shtojme
				PreparedStatement saveStmt = con.prepareStatement("INSERT INTO presence(subject,subject_type,branch,year,parallel,professor,time)"
						+ "values (?,?,?,?,?,?,?)");
				saveStmt.setInt(1, properData.getSubject().getIdBranch());
				saveStmt.setInt(2, properData.getSubjectType().getIdSubjectType());
				saveStmt.setInt(3, properData.getBranch().getIdBranch());
				saveStmt.setInt(4, properData.getYear().getIdYear());
				saveStmt.setInt(5, properData.getParallel().getIdParallel());
				saveStmt.setInt(6, properData.getProfessor().getIdProfessor());
				saveStmt.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
				saveStmt.execute();
				saveStmt.close();
				result = "OK";
			}
			else{
				result = "already_exists";
			}
		}
		catch (SQLException ex) {
			System.err.println("Gabim ne regjistrimin e prezences se profersorit");
		}
		finally {
			c.disconnect();
		}
		return result;
	}
	
/*private ClassroomPojo convertRowToClassroomPojo(ResultSet myRs) throws SQLException {
		
		int idStudent = myRs.getInt("idstudent");
		String name = myRs.getString("name");
		String surname = myRs.getString("surname");
		String fathersName = myRs.getString("fathers_name");
		Date birthdate = myRs.getDate("birthdate");
		int branch = myRs.getInt("branch");
		int year = myRs.getInt("year");
		int parallel = myRs.getInt("parallel");
		byte fingerprint[]=myRs.getBytes("fingerprint");
						
		Student tempStudent = new Student( idStudent,  name,  surname,   fathersName,  birthdate,   branch,   year,  parallel, fingerprint );
		
		
		int idDega = myRs.getInt("branch.idbranch");
		String degaName = myRs.getString("branch.name");
		Branch tempBranch=new Branch(idDega,degaName);
		
		
		int idYear = myRs.getInt("year.idyear");
		int currentYear = myRs.getInt("year.current_year");
		Year tempYear = new Year(idYear,currentYear);
		
		
		
		int idParallel=myRs.getInt("parallel.idparallel");
		String parallelName = myRs.getString("parallel.name");
		Parallel tempParallel = new Parallel(idParallel,parallelName);
		
		
		ClassroomPojo classroom = new ClassroomPojo();
		classroom.setStudent(tempStudent);
		classroom.setDega(tempBranch);
		classroom.setViti(tempYear);
		classroom.setParaleli(tempParallel);
		
		return classroom;
		
		
	}*/
	
	public ArrayList<StudentDataPojo> filterListOfStudents (String branch, int year , String parallel)
	{
		ArrayList<StudentDataPojo> filteredList = new ArrayList<StudentDataPojo>();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from student "
					+ " join branch on student.branch = branch.idbranch "
					+ " join year on student.year = year.idyear "
					+ " join parallel on student.parallel = parallel.idparallel " 
					+ " where branch.name = ? and year.current_year = ? and parallel.name =? ");
			stm.setString(1, branch);
			stm.setInt(2, year);
			stm.setString(3, parallel);
			ResultSet rs = stm.executeQuery();
			while (rs.next())
			{
				StudentDataPojo currentStudent = new StudentDataPojo();
				currentStudent=convertRowToStudentData(rs);
				filteredList.add(currentStudent);
			}
			
		}
		catch (SQLException ex) 
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		
		return filteredList;
	}
	
	public ArrayList<ScheduleDataPojo> filterListOfSchedule (String branch, int year , String parallel)
	{
		ArrayList<ScheduleDataPojo> filteredList = new ArrayList<ScheduleDataPojo>();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement(" select * from schedule "
					+ " join day_week on day_week.idday_week = schedule.day_week "
					+ " join time on time.idtime = schedule.time "
					+ " join subject on subject.idsubject = schedule.subject "
					+ " join subject_type on subject_type.idsubject_type = schedule.subject_type "
					+ " join branch on branch.idbranch = schedule.branch "
					+ " join year on year.idyear = schedule.year "
					+ " join parallel on parallel.idparallel = schedule.parallel "
					+ " join professor on professor.idprofessor = schedule.professor "
					+ " join classroom on classroom.idclassroom = schedule.classroom "
					+ " where branch.name = ? and year.current_year = ? and parallel.name = ? ");
			stm.setString(1, branch);
			stm.setInt(2, year);
			stm.setString(3, parallel);
			ResultSet myRs = stm.executeQuery();
			while(myRs.next())
			{
//				
				DayWeek dita = new DayWeek();
				 dita.setIdDayWeek(myRs.getInt("day_week.idday_week"));
				 dita.setNameDayWeek(myRs.getString("day_week.day"));
				 
				 Time orari = new Time();
				 orari.setIdTime(myRs.getInt("time.idtime"));
				 orari.setScheduleTime(myRs.getString("time.time"));
				 
				 Subject lenda = new Subject();
				 lenda.setIdSubject(myRs.getInt("subject.idsubject"));
				 lenda.setName(myRs.getString("subject.name"));
				 lenda.setIdBranch(myRs.getInt("subject.idbranch"));
				 lenda.setIdYear(myRs.getInt("subject.idyear"));
				 
				 SubjectType tipiLendes = new SubjectType();
				 tipiLendes.setIdSubjectType(myRs.getInt("subject_type.idsubject_type"));
				 tipiLendes.setSubjectTypeName(myRs.getString("subject_type.name"));
				 
				 Branch dega = new Branch();
				 dega.setIdBranch(myRs.getInt("branch.idbranch"));
				 dega.setName(myRs.getString("branch.name"));
				 
				 Year viti = new Year();
				 viti.setIdYear(myRs.getInt("year.idyear"));
				 viti.setYear(myRs.getInt("year.current_year"));
				 
				 Parallel paraleli = new Parallel();
				 paraleli.setIdParallel(myRs.getInt("parallel.idparallel"));
				 paraleli.setName(myRs.getString("parallel.name"));
				 
				 Professor pedagog = new Professor();
				 pedagog.setIdProfessor(myRs.getInt("professor.idprofessor"));
				 pedagog.setName(myRs.getString("professor.name"));
				 pedagog.setSurname(myRs.getString("professor.surname"));
				 
				 ClassRoom salla = new ClassRoom();
				 salla.setIdClassroom(myRs.getInt("classroom.idclassroom"));
				 salla.setName(myRs.getString("classroom.name"));
				 salla.setPassword(myRs.getString("classroom.password"));
				 salla.setClassroomType(myRs.getInt("classroom.type"));
				 
				 
				 Schedule schedule = new Schedule();
				 schedule.setIdSchedule(myRs.getInt("schedule.idschedule"));
				 schedule.setDayWeek(myRs.getInt("schedule.day_week"));
				 schedule.setTime(myRs.getInt("schedule.time"));
				 schedule.setSubject(myRs.getInt("schedule.subject"));
				 schedule.setSubjectType(myRs.getInt("schedule.subject_type"));
				 schedule.setBranch(myRs.getInt("schedule.branch"));
				 schedule.setYear(myRs.getInt("schedule.year"));
				 schedule.setParallel(myRs.getInt("schedule.parallel"));
				 schedule.setProfessor(myRs.getInt("schedule.professor"));
				 schedule.setClassroom(myRs.getInt("schedule.classroom"));
				 
				 ScheduleDataPojo result = new ScheduleDataPojo();
				 result.setSchedule(schedule);
				 result.setBranch(dega);
				 result.setClassroom(salla);
				 result.setDayWeek(dita);
				 result.setParallel(paraleli);
				 result.setProfessor(pedagog);
				 result.setScheduleTime(orari);
				 result.setSubject(lenda);
				 result.setSubjectType(tipiLendes);
				 result.setYear(viti);
				 
				
				filteredList.add(result);
			}
			
		}
		catch (SQLException ex) 
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		
		return filteredList;
	}
	
	
	public ArrayList<Branch> getListOfBranches()
	{
		ArrayList<Branch> listOfBranches = new ArrayList<Branch>();
		listOfBranches.add(new Branch());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from branch");
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				Branch currentBranch = new Branch();
				currentBranch.setIdBranch(rs.getInt("idbranch"));
				currentBranch.setName(rs.getString("name"));
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
	
	
	public void populateComboboxBranches(JComboBox comboBranches)
	{
		ArrayList<Branch> listOfBranches = getListOfBranches();
		if(listOfBranches.size()>0)
		{
			for (int i=0; i<listOfBranches.size();i++)
			{
				comboBranches.addItem(listOfBranches.get(i).getName());
			}
		}
		
	}
	
	
	
	public void populateComboboxProfessorSchedule(JComboBox comboProffesor,int branch,int year,String subject, String subjectType)
	{
		int idSelectedSubject = getIdSelectedSubject(branch,year,subject);
		ArrayList<Professor> listOfProfessors = new ArrayList<Professor> ();
		listOfProfessors = getListOfProfessorsToSchedule(idSelectedSubject , subjectType);
		if(listOfProfessors.size()>0)
		{
			for (int i=0; i<listOfProfessors.size();i++)
			{
				comboProffesor.addItem(listOfProfessors.get(i).getName() +" "+ listOfProfessors.get(i).getSurname()) ;
			}
		}
		
	}
	
	
	public void populateComboboxClassroomSchedule(JComboBox comboClassroom)
	{
		ArrayList<ClassRoom> listOfClassrooms = new ArrayList<ClassRoom> ();
		listOfClassrooms = getListOfClassroomsToSchedule();
		if(listOfClassrooms.size()>0)
		{
			for (int i=0; i<listOfClassrooms.size();i++)
			{
				comboClassroom.addItem(listOfClassrooms.get(i).getName()) ;
			}
		}
		
	}
	
	public int getIdSelectedSubject(int branch,int year,String subject)
	{
		int idSubject=0;
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from subject "
					+ "where idbranch = ? and idyear = ? and name = ?");
			stm.setInt(1, branch);
			stm.setInt(2, year);
			stm.setString(3, subject);
			ResultSet rs = stm.executeQuery();
			
			if(rs.first())
			{
				idSubject=rs.getInt("idsubject");
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
		return idSubject;
	}
	
	public ArrayList<Professor> getListOfProfessorsToSchedule(int idSelectedSubject , String subjectType)
	{
		ArrayList<Professor> profList = new ArrayList<Professor>();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from professor "
					+ "JOIN professor_subject ON professor_subject.id_professor = professor.idprofessor "
					+ " JOIN subject ON subject.idsubject = professor_subject.id_subject "
					+ " JOIN subject_type ON subject_type.idsubject_type = professor_subject.id_subject_type "
					+ " where subject.idsubject = ? and subject_type.name = ? ");
			stm.setInt(1, idSelectedSubject);
			stm.setString(2, subjectType);
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				Professor currProf = new Professor();
				currProf.setIdProfessor(rs.getInt("professor.idprofessor"));
				currProf.setName(rs.getString("professor.name"));
				currProf.setSurname(rs.getString("professor.surname"));
				profList.add(currProf);
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
		return profList;
		
	}
	
	
	public ArrayList<ClassRoom> getListOfClassroomsToSchedule()
	{
		ArrayList<ClassRoom> classroomList = new ArrayList<ClassRoom>();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from classroom ");
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				ClassRoom classroom = new ClassRoom();
				classroom.setIdClassroom(rs.getInt("idclassroom"));
				classroom.setName(rs.getString("name"));
				classroomList.add(classroom);
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
		return classroomList;
		
	}
	
	
	public ArrayList<Year> getListOfYears()
	{
		ArrayList<Year> listOfYears = new ArrayList<Year>();
		listOfYears.add(new Year());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from year");
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
	
	
	public void populateComboboxYears(JComboBox comboYears)
	{
		ArrayList<Year> listOfYears = getListOfYears();
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
	
	

	public ArrayList<Parallel> getListOfParallels()
	{
		ArrayList<Parallel> listOfParallels = new ArrayList<Parallel>();
		listOfParallels.add(new Parallel());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from parallel");
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
	
	
	public void populateComboboxParallels(JComboBox comboParallels)
	{
		ArrayList<Parallel> listOfParallels = getListOfParallels();
		if(listOfParallels.size()>0)
		{
			for (int i=0; i<listOfParallels.size();i++)
			{
				comboParallels.addItem(listOfParallels.get(i).getName());
			}
		}
		
	}
	
	
	
	
	public DPFPTemplate getFpImg (int idStudent) throws IOException
	{
		try 
		{
			Connection con = c.connect();

			//kap te gjitha shenjat qe ndodhen ne DB
			PreparedStatement stm = con.prepareStatement("SELECT fingerprint FROM student where idstudent = ?");
			stm.setInt(1, idStudent);
			ResultSet rs = stm.executeQuery();
			if(rs.first())
				{
					byte templateBuffer[] = rs.getBytes("fingerprint");
					DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
					setTemplate(referenceTemplate);					
				}
			else
				{
					JOptionPane.showMessageDialog(null,"Nuk ka shenjë gishti për këtë student ", "Verifikimi i shenjes",
					JOptionPane.ERROR_MESSAGE);
					setTemplate(null);					
				}		
		}
		catch (SQLException e)
		{
			System.err.println("Gabim ne identifikimin e shenjes"+e.getMessage());
		}
		finally
		{
			c.disconnect();
		}
		return getTemplate();
	}
	
	public void setTemplate (DPFPTemplate template){
		DPFPTemplate old = this.template;
		this.template = template ; 
		firePropertyChange(TEMPLATE_PROPERTY,old,template);
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		PropertyChangeSupport changeSupport = this.changeSupport;
		if (changeSupport == null || oldValue == newValue) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	
	public DPFPTemplate getTemplate(){
		return template;
	}
	
}
