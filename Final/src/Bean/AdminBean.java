package Bean;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

import DBConnection.*;
import Entities.*;
import View.AddProf_Subject;
import View.Admin;
import View.ClassRoomView;
import View.MainPage;
import View.Prof;


import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

public class AdminBean {

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
	
	
	
//	public void populateComboboxProfessorSubject(JComboBox comboSubject)
//	{
//		ArrayList<Year> listOfYears = getListOfYears();
//		if(listOfYears.size()>0)
//		{
//			for (int i=0; i<listOfYears.size();i++)
//			{				
//				comboSubject.addItem(listOfYears.get(i).getYear());							
//			}
//		}
//	}
	
	
	
	
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
	
	
	public void updateStudent(StudentDataPojo studentsData)
	{
		try{
			Connection con = c.connect();
			if(studentsData.getFPDates()!=null && studentsData.getFpSize() !=null) // me fingerprint te ri
			{
				PreparedStatement updateStmt = con.prepareStatement("UPDATE student set name =? ,surname =?,fathers_name =?,birthdate =?, "
						+ " branch =?,year =?,parallel =?, fingerprint =?"
						+ " where idstudent= ? ");
				updateStmt.setString(1, studentsData.getStudent().getName());
				updateStmt.setString(2, studentsData.getStudent().getSurname());
				updateStmt.setString(3, studentsData.getStudent().getFathersName());
				 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				 Date date = new Date();
				 updateStmt.setString(4, dateFormat.format(studentsData.getStudent().getBirthdate()));
				 updateStmt.setInt(5, studentsData.getStudent().getBranch());
				 updateStmt.setInt(6, studentsData.getStudent().getYear());
				 updateStmt.setInt(7, studentsData.getStudent().getParallel());
				 updateStmt.setBinaryStream(8, studentsData.getFPDates(),studentsData.getFpSize());
				 updateStmt.setInt(9, studentsData.getStudent().getIdStudent());
				 updateStmt.execute();
				 updateStmt.close();
			}
			else
			{			// pa fingerprint te ri
				PreparedStatement updateStmt = con.prepareStatement("UPDATE student set name =? ,surname =?,fathers_name =?,birthdate =?, "
						+ " branch =?,year =?,parallel =?"
						+ " where idstudent= ?");
				updateStmt.setString(1, studentsData.getStudent().getName());
				updateStmt.setString(2, studentsData.getStudent().getSurname());
				updateStmt.setString(3, studentsData.getStudent().getFathersName());
				 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				 Date date = new Date();
				 updateStmt.setString(4, dateFormat.format(studentsData.getStudent().getBirthdate()));
				 updateStmt.setInt(5, studentsData.getStudent().getBranch());
				 updateStmt.setInt(6, studentsData.getStudent().getYear());
				 updateStmt.setInt(7, studentsData.getStudent().getParallel());
				 updateStmt.setInt(8, studentsData.getStudent().getIdStudent());
				 updateStmt.execute();
				 updateStmt.close();
			}			
			JOptionPane.showMessageDialog(null, "Shenja u ruajt me sukses");			
		}
		catch (SQLException ex) {
			System.err.println("Gabim ne ruajtjen e te dhenave te shenjes");
		}
		finally {
			c.disconnect();
		}		
	}
	
	public void insertStudent(StudentDataPojo studentsData)
	{
		try{
			Connection con = c.connect();
			PreparedStatement saveStmt = con.prepareStatement("INSERT INTO student(name ,surname,fathers_name,birthdate, branch,year,parallel, fingerprint)"
					+ "values (?,?,?,?,?,?,?,?)");
			saveStmt.setString(1, studentsData.getStudent().getName());
			saveStmt.setString(2, studentsData.getStudent().getSurname());
			saveStmt.setString(3, studentsData.getStudent().getFathersName());
			 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			 Date date = new Date();
			saveStmt.setString(4, dateFormat.format(studentsData.getStudent().getBirthdate()));
			saveStmt.setInt(5, studentsData.getStudent().getBranch());
			saveStmt.setInt(6, studentsData.getStudent().getYear());
			saveStmt.setInt(7, studentsData.getStudent().getParallel());
			saveStmt.setBinaryStream(8, studentsData.getFPDates(),studentsData.getFpSize());
			saveStmt.execute();
			saveStmt.close();
			JOptionPane.showMessageDialog(null, "Shenja u ruajt me sukses");
			
		}
		catch (SQLException ex) {
			System.err.println("Gabim ne ruajtjen e te dhenave te shenjes");
		}
		finally {
			c.disconnect();
		}
		
	}
	
	public void deleteStudent(StudentDataPojo selectedStudent)
	{
		try
		{
			Connection con = c.connect();
			PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM STUDENT WHERE idstudent=? ");
			deleteStmt.setInt(1, selectedStudent.getStudent().getIdStudent());
			deleteStmt.execute();
			deleteStmt.close();
		}
		catch(SQLException ex)
		{
			System.err.println("Gabim ne ruajtjen e te dhenave te shenjes");
		}
		finally 
		{
			c.disconnect();
		}
	}
	
	public void insertSubject(Subject subject)	
	{
		try{
			Connection con = c.connect();
			PreparedStatement saveStmt = con.prepareStatement("INSERT INTO subject (name , idbranch , idyear )"
					+ "values (?,?,?)");
			saveStmt.setString(1, subject.getName());
			saveStmt.setInt(2, subject.getIdBranch());
			saveStmt.setInt(3, subject.getIdYear());
			saveStmt.execute();
			saveStmt.close();
			JOptionPane.showMessageDialog(null, "Lënda u ruajt me sukses");
			
		}
		catch (SQLException ex) {
			System.err.println("Gabim ne ruajtjen e te dhenave");
		}
		finally {
			c.disconnect();
		}
	}
	
	public void updateSubject(Subject subject)	
	{
		try{
			Connection con = c.connect();
			PreparedStatement updateStm = con.prepareStatement("UPDATE subject SET name = ? , idbranch = ? , idyear = ?  where idsubject = ? ");
			updateStm.setString(1, subject.getName());
			updateStm.setInt(2, subject.getIdBranch());
			updateStm.setInt(3, subject.getIdYear());
			updateStm.setInt(4, subject.getIdSubject());
			updateStm.execute();
			updateStm.close();
			JOptionPane.showMessageDialog(null, "Lënda u modifikua me sukses");
			
		}
		catch (SQLException ex) {
			System.err.println("Gabim ne ruajtjen e të dhënave");
		}
		finally {
			c.disconnect();
		}
	}
	
	
	public ArrayList<SubjectDataPojo> filterListOfSubjects (String branch, int year)
	{
		ArrayList<SubjectDataPojo> filteredList = new ArrayList<SubjectDataPojo>();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from subject "
					+ " join branch on subject.idbranch = branch.idbranch "
					+ " join year on subject.idyear = year.idyear "
					+ " where branch.name = ? and year.current_year = ? ");
			stm.setString(1, branch);
			stm.setInt(2, year);
			ResultSet rs = stm.executeQuery();
			while (rs.next())
			{
				SubjectDataPojo currentSubject = new SubjectDataPojo();
				currentSubject=convertRowToSubjectDataPojo(rs);
				filteredList.add(currentSubject);
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
	
	public ArrayList<SubjectDataPojo> searchSubject (String search)
	{
		ArrayList<SubjectDataPojo> filteredList = new ArrayList<SubjectDataPojo>();
		String searchString = "%"+ search.toLowerCase() +"%";
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from subject "
					+ " join branch on subject.idbranch = branch.idbranch "
					+ " join year on subject.idyear = year.idyear "
					+ " where LOWER(subject.name) like ? ");
			stm.setString(1, searchString);
			ResultSet rs = stm.executeQuery();
			while (rs.next())
			{
				SubjectDataPojo currentSubject = new SubjectDataPojo();
				currentSubject=convertRowToSubjectDataPojo(rs);
				filteredList.add(currentSubject);
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
	
private SubjectDataPojo convertRowToSubjectDataPojo (ResultSet myRs) throws SQLException {	
						
		Subject tempSubject = new Subject();
		tempSubject.setIdSubject(myRs.getInt("idsubject"));
		tempSubject.setName(myRs.getString("name"));
		tempSubject.setIdBranch(myRs.getInt("idbranch"));
		tempSubject.setIdYear(myRs.getInt("idyear"));
		
		
		Branch tempBranch=new Branch();
		tempBranch=getBranchById(myRs.getInt("idbranch"));
		
		
		Year tempYear = new Year();
		tempYear=getYearById(myRs.getInt("idyear"));
		
		
		SubjectDataPojo subjectPojo = new SubjectDataPojo();
		subjectPojo.setLenda(tempSubject);
		subjectPojo.setDega(tempBranch);
		subjectPojo.setViti(tempYear);
		
		return subjectPojo;		
	}

	public SubjectDataPojo getSelectedSubject(int idSuject)
	{
		SubjectDataPojo currentSubject = new SubjectDataPojo();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from subject "
				+ " where idsubject = ? ");
			stm.setInt(1, idSuject);
			ResultSet rs = stm.executeQuery();
			if(rs.first())
			{			
				currentSubject=convertRowToSubjectDataPojo(rs);
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

		return currentSubject;	
	}
	
	public Branch getBranchById(int idBranch)
	{
		Branch returnBranch = new Branch();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from branch "
				+ " where idbranch = ? ");
			stm.setInt(1, idBranch);
			ResultSet rs = stm.executeQuery();
			if(rs.first())
			{			
				returnBranch.setIdBranch(idBranch);
				returnBranch.setName(rs.getString("name"));
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
		return returnBranch;
	}
	
	public Year getYearById(int idYear)
	{
		Year returnYear = new Year();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from year "
				+ " where idyear = ? ");
			stm.setInt(1, idYear);
			ResultSet rs = stm.executeQuery();
			if(rs.first())
			{			
				returnYear.setIdYear(idYear);
				returnYear.setYear(rs.getInt("current_year"));
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
		return returnYear;
	}
	
	public void deleteSubject(SubjectDataPojo selectedSubject)
	{
		try
		{
			Connection con = c.connect();
			PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM subject WHERE idsubject =? ");
			deleteStmt.setInt(1, selectedSubject.getLenda().getIdSubject());
			deleteStmt.execute();
			deleteStmt.close();
		}
		catch(SQLException ex)
		{
			System.err.println("Gabim në fshirjen e subjektit");
		}
		finally 
		{
			c.disconnect();
		}
	}
	
	public ProfessorDataPojo getProfessorById(int idProfessor)
	{
		Professor professor = new Professor();
		ProfessorDataPojo returnProfessor = new ProfessorDataPojo();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from professor "
				+ " where idprofessor = ? ");
			stm.setInt(1, idProfessor);
			ResultSet rs = stm.executeQuery();
			if(rs.first())
			{			
				professor.setIdProfessor(idProfessor);
				professor.setName(rs.getString("name"));
				professor.setSurname(rs.getString("surname"));
				professor.setUsername(rs.getString("username"));
				professor.setPassword(rs.getString("password"));
				professor.setFingerprint(rs.getBytes("fingerprint"));
			}
			returnProfessor.setProfessor(professor);
		}
		catch (SQLException ex) 
		{
			System.err.println("Problem në nivel baze të dhënash!");
		}
		finally 
		{
			c.disconnect();
		}
		return returnProfessor;
	}
	
	public ArrayList<ProfessorDataPojo> searchProfessor(String search)
	{		
		ArrayList<ProfessorDataPojo> filteredList = new ArrayList<ProfessorDataPojo>();
		ProfessorDataPojo professorDataPojo = new ProfessorDataPojo();
		String searchString = search.toLowerCase();
		String segments[]= searchString.split(" ");
		
		try
		{
			String selectQuerry=null;
			Connection con = c.connect();
			PreparedStatement stm ;
		
			if(segments.length<2)
			{
				selectQuerry="select * from professor "
						+ " where LOWER(name) like ? || LOWER(surname) like ? ";
				stm = con.prepareStatement(selectQuerry);
				stm.setString(1,"%"+ segments[0] +"%");
				stm.setString(2,"%"+ segments[0] +"%");
			}
			else
			{
				selectQuerry="select * from professor "
						+ " where LOWER(name) like ? || LOWER(surname) like ? || LOWER(name) like ?  || LOWER(surname) like ? ";
				stm = con.prepareStatement(selectQuerry);
				stm.setString(1, "%"+ segments[0] +"%");
				stm.setString(2, "%"+ segments[0] +"%");
				stm.setString(3, "%"+ segments[1] +"%");
				stm.setString(4, "%"+ segments[1] +"%");
			}		
			ResultSet rs = stm.executeQuery();
			while (rs.next())
			{			
				professorDataPojo=convertRowToProfessorDataPojo(rs);
				filteredList.add(professorDataPojo);
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
	
	
	private ProfessorDataPojo convertRowToProfessorDataPojo (ResultSet myRs) throws SQLException {	
		
		Professor tempProfessor = new Professor();
		tempProfessor.setIdProfessor(myRs.getInt("idprofessor"));
		tempProfessor.setName(myRs.getString("name"));
		tempProfessor.setSurname(myRs.getString("surname"));
		tempProfessor.setUsername(myRs.getString("username"));
		tempProfessor.setPassword(myRs.getString("password"));
		tempProfessor.setFingerprint(myRs.getBytes("fingerprint"));
				
		ProfessorDataPojo professor = new ProfessorDataPojo();
		professor.setProfessor(tempProfessor);
		
		return professor;		
	}
	
	
	public void insertProfessor(ProfessorDataPojo professorData)
	{
		try{
			Connection con = c.connect();
			PreparedStatement saveStmt = con.prepareStatement("INSERT INTO professor(name , surname , username , password, fingerprint)"
					+ "values (?,?,?,?,?)");
			saveStmt.setString(1, professorData.getProfessor().getName());
			saveStmt.setString(2, professorData.getProfessor().getSurname());
			saveStmt.setString(3, professorData.getProfessor().getUsername());
			saveStmt.setString(4, professorData.getProfessor().getPassword());
			saveStmt.setBinaryStream(5, professorData.getFPDates(),professorData.getFpSize());
			saveStmt.execute();
			saveStmt.close();
			JOptionPane.showMessageDialog(null, "Shenja gishtit te pedagogut u ruajt me sukses");			
		}
		catch (SQLException ex) {
			System.err.println("Gabim ne ruajtjen e te dhenave te pedagogut");
		}
		finally {
			c.disconnect();
		}		
	}
	
	public void updateProfessor(ProfessorDataPojo professorData)
	{
		try{
			Connection con = c.connect();
			if(professorData.getFPDates()!=null && professorData.getFpSize() !=null) 
			{
				PreparedStatement updateStmt = con.prepareStatement("UPDATE professor SET name = ? , surname = ?  , username = ? , "
						+ " password = ?, fingerprint = ? "
						+ " where idprofessor = ? ");
				updateStmt.setString(1, professorData.getProfessor().getName());
				updateStmt.setString(2, professorData.getProfessor().getSurname());
				updateStmt.setString(3, professorData.getProfessor().getUsername());
				updateStmt.setString(4, professorData.getProfessor().getPassword());
				updateStmt.setBinaryStream(5, professorData.getFPDates(),professorData.getFpSize());
				updateStmt.setInt(6, professorData.getProfessor().getIdProfessor());
				updateStmt.execute();
				updateStmt.close();
				JOptionPane.showMessageDialog(null, "Shenja gishtit te pedagogut u ruajt me sukses");	
			}
			else
			{
				PreparedStatement updateStmt = con.prepareStatement("UPDATE professor SET name = ? , surname = ?  , username = ? , "
						+ " password = ? "
						+ " where idprofessor = ? ");
				updateStmt.setString(1, professorData.getProfessor().getName());
				updateStmt.setString(2, professorData.getProfessor().getSurname());
				updateStmt.setString(3, professorData.getProfessor().getUsername());
				updateStmt.setString(4, professorData.getProfessor().getPassword());
				updateStmt.setInt(5, professorData.getProfessor().getIdProfessor());
				updateStmt.execute();
				updateStmt.close();
				JOptionPane.showMessageDialog(null, "Shenja gishtit te pedagogut u ruajt me sukses");	
			}
					
		}
		catch (SQLException ex) {
			System.err.println("Gabim ne ruajtjen e te dhenave te pedagogut");
		}
		finally {
			c.disconnect();
		}		
	}
	
	
	public ProfessorDataPojo getSelectedProfessor(int idProfessor)
	{
		ProfessorDataPojo currentProfessor = new ProfessorDataPojo();
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("select * from professor "
				+ " where idprofessor = ? ");
			stm.setInt(1, idProfessor);
			ResultSet rs = stm.executeQuery();
			if(rs.first())
			{			
				currentProfessor=convertRowToProfessorDataPojo(rs);
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

		return currentProfessor;	
	}
	
	public void populateComboboxPropperSubjects(JComboBox comboSubject,int idBranch, int idYear)
	{
		ArrayList<Subject> listOfSubjects = getPropperSubjects(idBranch,idYear);		
		comboSubject.removeAllItems();
		
		if(listOfSubjects.size()>0)
		{
			for (int i=0; i<listOfSubjects.size();i++)
			{
				comboSubject.addItem(listOfSubjects.get(i).getName());								
			}
		}
	}
	
	
	
	public ArrayList<Subject> getPropperSubjects(int idBranch, int idYear)
	{
		ArrayList<Subject> listOfSubjects = new ArrayList<Subject>();
		listOfSubjects.add(new Subject());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("SELECT * FROM subject where idbranch = ? and idyear = ?");
			stm.setInt(1, idBranch);
			stm.setInt(2, idYear);
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				Subject currentSubject = new Subject();
				currentSubject.setIdSubject(rs.getInt("idsubject"));
				currentSubject.setName(rs.getString("name"));
				currentSubject.setIdBranch(rs.getInt("idbranch"));
				currentSubject.setIdYear(rs.getInt("idYear"));
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
	
	
	public void populateComboboxSubjectType(JComboBox comboSubjectType)
	{
		ArrayList<SubjectType> listOfSubjectTypes = getSubjectTypes();
		if(listOfSubjectTypes.size()>0)
		{
			for (int i=0; i<listOfSubjectTypes.size();i++)
			{
				comboSubjectType.addItem(listOfSubjectTypes.get(i).getSubjectTypeName());								
			}
		}
	}
	
	
	
	public ArrayList<SubjectType> getSubjectTypes()
	{
		ArrayList<SubjectType> listOfSubjectTypes = new ArrayList<SubjectType>();
		listOfSubjectTypes.add(new SubjectType());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("SELECT * FROM subject_type ");
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				SubjectType currentSubjectType = new SubjectType();
				currentSubjectType.setIdSubjectType(rs.getInt("idsubject_type"));
				currentSubjectType.setSubjectTypeName(rs.getString("name"));				
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
	
	public void addSubjectToProf(Professor_Subject addSubjectToProf)	
	{
		try{
			Connection con = c.connect();
			PreparedStatement saveStmt = con.prepareStatement("INSERT INTO professor_subject (id_professor , id_subject , id_subject_type )"
					+ "values (?,?,?)");
			saveStmt.setInt(1, addSubjectToProf.getIdProfessor());
			saveStmt.setInt(2, addSubjectToProf.getIdSubject());
			saveStmt.setInt(3, addSubjectToProf.getIdSubjectType());
			saveStmt.execute();
			saveStmt.close();
//			JOptionPane.showMessageDialog(null, "Lënda u shtua me sukses");
			
		}
		catch (SQLException ex) {
			System.err.println("Gabim në shtimin e lendes per pedagogun");
		}
		finally {
			c.disconnect();
		}
	}
	
	public void removeSubjectFromProf(Integer idSubjectToRemove)	
	{
		try{
			Connection con = c.connect();
			PreparedStatement saveStmt = con.prepareStatement("DELETE FROM professor_subject "
					+ " where idprofessor_subject = ? ");
			saveStmt.setInt(1, idSubjectToRemove);
			saveStmt.execute();
			saveStmt.close();
//			JOptionPane.showMessageDialog(null, "Lënda u fshi me sukses");
			
		}
		catch (SQLException ex) {
			System.err.println("Gabim në shtimin e lendes per pedagogun");
		}
		finally {
			c.disconnect();
		}
	}
	
	
	public ArrayList<ProfessorSubjectDataPojo> getProfessorsSubjects(int idProfessor)
	{
		ArrayList<ProfessorSubjectDataPojo> listOfProfessorSubjects = new ArrayList<ProfessorSubjectDataPojo>();
//		listOfSubjectTypes.add(new Professor_Subject());
		try
		{
			Connection con = c.connect();
			PreparedStatement stm = con.prepareStatement("SELECT * FROM professor_subject "
					+ " JOIN professor ON professor.idprofessor = professor_subject.id_professor "
					+ " JOIN subject ON subject.idsubject = professor_subject.id_subject "
					+ " JOIN subject_type ON subject_type.idsubject_type = professor_subject.id_subject_type "
					+ " JOIN branch ON branch.idbranch = subject.idbranch "
					+ " JOIN year ON year.idyear = subject.idyear "
					+  "WHERE professor_subject.id_professor = ?  ");
			stm.setInt(1, idProfessor);
			ResultSet rs = stm.executeQuery();
			
			while(rs.next())
			{
				ProfessorSubjectDataPojo currentProfessorsSubject = new ProfessorSubjectDataPojo();
				currentProfessorsSubject.setIdProfessor_Subject(rs.getInt("professor_subject.idprofessor_subject"));
				currentProfessorsSubject.setSubject(rs.getString("subject.name"));
				currentProfessorsSubject.setType(rs.getString("subject_type.name"));
				currentProfessorsSubject.setBranch(rs.getString("branch.name"));
				currentProfessorsSubject.setYear(rs.getInt("year.current_year"));
				listOfProfessorSubjects.add(currentProfessorsSubject);
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
		return listOfProfessorSubjects;
	}
	
	
	
	public ArrayList<ProfessorSubjectDataPojo> convertToDataPojo ( ArrayList<Professor_Subject> subjectsToBeAdded )
	{
		ArrayList<ProfessorSubjectDataPojo> listOfProfessorSubjects = new ArrayList<ProfessorSubjectDataPojo>();
//		listOfSubjectTypes.add(new Professor_Subject());
		if(subjectsToBeAdded.size()>0)
		{
			for(int i=0;i<subjectsToBeAdded.size();i++)
			{
				ProfessorSubjectDataPojo currentProfessorsSubject = new ProfessorSubjectDataPojo();
				
				try
				{
					Connection con = c.connect();
					PreparedStatement stm = con.prepareStatement("SELECT * FROM subject "
							+ " JOIN branch ON subject.idbranch = branch.idbranch "
							+ " JOIN year ON subject.idyear=year.idyear "
							+ " where subject.idsubject = ? ");
					stm.setInt(1, subjectsToBeAdded.get(i).getIdSubject());
					ResultSet rs = stm.executeQuery();
					
					while(rs.next())
					{
						currentProfessorsSubject.setIdProfessor_Subject(0);
						currentProfessorsSubject.setSubject(rs.getString("subject.name"));
						currentProfessorsSubject.setBranch(rs.getString("branch.name"));
						currentProfessorsSubject.setYear(rs.getInt("year.current_year"));
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
				
				try
				{
					Connection con = c.connect();
					PreparedStatement stm2 = con.prepareStatement("SELECT * FROM subject_type "
							+ " where subject_type.idsubject_type = ? ");
					stm2.setInt(1, subjectsToBeAdded.get(i).getIdSubjectType());
					ResultSet rs2 = stm2.executeQuery();
					
					while(rs2.next())
					{
						
						currentProfessorsSubject.setType(rs2.getString("subject_type.name"));
					}
					
					listOfProfessorSubjects.add(currentProfessorsSubject);
					
				}
				catch(SQLException ex)
				{
					System.err.println("Problem në nivel baze të dhënash!");
				}
				finally 
				{
					c.disconnect();
				}
			}
		}
		
		
		return listOfProfessorSubjects;
	}
	
	
	public void removeFromSchedule(String lenda,String llojiLendes,String profname,String profsurname,
			String salla,int time,int dayweek,String dega,int viti,String paraleli){
		try{
			Connection con = c.connect();
			PreparedStatement removeStmt = con.prepareStatement(" DELETE FROM SCHEDULE WHERE idschedule in ( "
					+ " select * from ( "
					+ " select idschedule from schedule "
					+ " JOIN branch ON  "
					+ " branch.idbranch= SCHEDULE.branch  "
					+ " JOIN parallel ON "
					+ " schedule.parallel= parallel.idparallel "
					+ " JOIN SUBJECT ON "
					+ " subject.idsubject=schedule.subject "
					+ " JOIN subject_type ON "
					+ " subject_type.idsubject_type=schedule.subject_type "
					+ " JOIN professor ON "
					+ " professor.idprofessor=schedule.professor "
					+ " JOIN classroom ON "
					+ " classroom.idclassroom = schedule.classroom "
					+ " WHERE branch.name =? "
					+ " AND professor.name = ? "
					+ " AND professor.surname = ?  "
					+ " AND schedule.year = ? "
					+ " AND schedule.day_week = ? "
					+ " AND schedule.time = ? "
					+ " AND parallel.name = ? "
					+ " AND subject.name =  ? "
					+ " AND subject_type.name = ? "
					+ " AND classroom.name = ? )"
					+ " as ID_SCHEDULE ) ");
			removeStmt.setString(1, dega);
			removeStmt.setString(2, profname);
			removeStmt.setString(3, profsurname);
			removeStmt.setInt(4, viti);
			removeStmt.setInt(5, dayweek);
			removeStmt.setInt(6, time);
			removeStmt.setString(7, paraleli);
			removeStmt.setString(8, lenda);
			removeStmt.setString(9, llojiLendes);
			removeStmt.setString(10, salla);
			removeStmt.executeUpdate();
			removeStmt.close();
//			JOptionPane.showMessageDialog(null, "Lënda u shtua me sukses");
			
		}
		catch (SQLException ex) {
			System.err.println("Gabim në shtimin e ores");
		}
		finally {
			c.disconnect();
		}
	}
	
	
	public void insertSchedule(Schedule shtoOre)
	{
		try{
			Connection con = c.connect();
			PreparedStatement saveStmt = con.prepareStatement("INSERT INTO schedule (day_week , time , subject, subject_type "
					+ ", branch , year , parallel, professor , classroom )"
					+ "values (?,?,?,?,?,?,?,?,?)");
			saveStmt.setInt(1, shtoOre.getDayWeek());
			saveStmt.setInt(2, shtoOre.getTime());
			saveStmt.setInt(3, shtoOre.getSubject());
			saveStmt.setInt(4, shtoOre.getSubjectType());
			saveStmt.setInt(5, shtoOre.getBranch());
			saveStmt.setInt(6, shtoOre.getYear());
			saveStmt.setInt(7, shtoOre.getParallel());
			saveStmt.setInt(8, shtoOre.getProfessor());
			saveStmt.setInt(9, shtoOre.getClassroom());
			saveStmt.execute();
			saveStmt.close();
//			JOptionPane.showMessageDialog(null, "Lënda u shtua me sukses");
			
		}
		catch (SQLException ex) {
			System.err.println("Gabim në shtimin e ores");
		}
		finally {
			c.disconnect();
		}
	}
	
}
