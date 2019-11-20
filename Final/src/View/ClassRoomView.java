package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Entities.ClassRoom;
import Entities.ClassroomPojo;
import Entities.Student;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

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

import Bean.ClassroomBean;
import DBConnection.ConnectToDB;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClassRoomView extends JFrame {
	
	
	Timer timer;
	public ClassroomPojo properData;
	
	class RemindTask extends TimerTask {
	    public void run() {
	    	Calendar calendar = Calendar.getInstance();
	    	if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)
	    	{
	    		filterData(1,calendar);
	    	}
	    	else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY)
	    	{
	    		filterData(2,calendar);
	    	}
	    	else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY)
	    	{
	    		filterData(3,calendar);
	    	}
	    	else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY)
	    	{
	    		filterData(4,calendar);
	    	}
	    	else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY)
	    	{
	    		filterData(5,calendar);
	    	}
	    	else 
	    	{
	    		System.out.println("It's weekend");
	    	}
	    }
	}
	
	public void executeSchedule()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date time = calendar.getTime();

		timer = new Timer();
		timer.scheduleAtFixedRate(new RemindTask(), time,60*60*1000);
	}
	

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel fingerprintImage;
	private JLabel lblSalla;
	
	private String lenda;
	private String dega;
	private int viti;
	private String paraleli;
	private ClassRoom classroom;
	
	private DPFPCapture reader = DPFPGlobal.getCaptureFactory().createCapture();
	private DPFPEnrollment enroll = DPFPGlobal.getEnrollmentFactory().createEnrollment();
	private DPFPVerification verify = DPFPGlobal.getVerificationFactory().createVerification();
	private DPFPTemplate template ; 
	public static String TEMPLATE_PROPERTY = "template";
	public DPFPFeatureSet featureInscription;
	public DPFPFeatureSet featureVerification;
	private PropertyChangeSupport changeSupport;
	
	public ClassroomBean classroomBean = new ClassroomBean();
	
	private ConnectToDB cn = new ConnectToDB();
	private JLabel lblLnda;
	private JLabel lblDega;
	private JLabel lblViti;
	private JLabel lblParaleli;
	private JLabel imgResult;
	private JLabel lblStudenti;


	/**
	 * Create the frame.
	 */
	public ClassRoomView(ClassRoom salla) {	
		
		classroom=salla;
		
		executeSchedule();
		
		setTitle("Salle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1019, 665);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		// -- Fingerprint --//
				this.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						stop();
					}
					
					@Override
					public void windowOpened(WindowEvent e) {
						InitFP();
						start();
						FPState();				
					}
					
				});
		
		
		
		lblSalla = new JLabel("Salla: " +classroom.getName());
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Informacion mbi l\u00EBnd\u00EBn", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Informacion mbi studentin", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(528)
					.addComponent(lblSalla, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
					.addGap(143))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
					.addGap(155)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(39)
					.addComponent(lblSalla)
					.addGap(166)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
					.addGap(16))
		);
		
		lblStudenti = new JLabel("Studenti: ");
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		fingerprintImage = new JLabel("");
		fingerprintImage.setBounds(10, 11, 202, 154);
		panel_2.add(fingerprintImage);
		
		imgResult = new JLabel("");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblStudenti)
					.addPreferredGap(ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(imgResult, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(44)
							.addComponent(lblStudenti))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(28)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(imgResult, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		lblLnda = new JLabel("L\u00EBnda: " +lenda);
		
		lblDega = new JLabel("Dega: " +dega);
		
		lblViti = new JLabel("Viti: " +viti);
		
		lblParaleli = new JLabel("Paraleli: " +paraleli);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblParaleli, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblViti, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblDega, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblLnda, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
					.addContainerGap(269, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(36)
					.addComponent(lblLnda)
					.addGap(30)
					.addComponent(lblDega)
					.addGap(33)
					.addComponent(lblViti)
					.addGap(38)
					.addComponent(lblParaleli)
					.addContainerGap(119, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);			
		
		
	}
	
	
	
	
	
	public String registerStudentPresence(ClassroomPojo properData, Student student){
		String registrationResult=classroomBean.registerStudentPresence(properData, student);
		
		return registrationResult;
	}
	
	
	
	public String registerProfessorPresence(ClassroomPojo properData){
		String registrationResult=classroomBean.registerProfessorPresence(properData);
		
		return registrationResult;		
	}
	
	
	
	protected void InitFP()
	{
		reader.addDataListener(new DPFPDataListener() {
			
			@Override
			public void dataAcquired(final DPFPDataEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					SendText("Shenja e gishtit u kap!");
					ProcessCapture(e.getSample());
				}
			});
				
			}
		});
		reader.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			
			@Override
			public void readerDisconnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						SendText("Sensori nuk eshte lidhur");
					}
				});					
			}
			
			@Override
			public void readerConnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						SendText("Sensori u lidh");
					}
				});				
			}
		});
		
		
		reader.addSensorListener(new DPFPSensorAdapter(){
			@Override public void fingerTouched (final DPFPSensorEvent e){				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						SendText("Gishti u vendos mbi sensor");						
					}
				});
			}
			
			@Override public void fingerGone (final DPFPSensorEvent e){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						SendText("Gishti u hoq nga sensori");
						
					}
				});
			}			
		});
		
		reader.addErrorListener(new DPFPErrorAdapter(){
			public void errorReader(final DPFPErrorEvent e){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						SendText("Gabim: "+ e.getError());
						
					}
				});

			}
		});
	}
	
	
	
	public void ProcessCapture(DPFPSample sample){
		enroll.clear();
		featureInscription = getCharacteristics(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
		featureVerification = getCharacteristics(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
		
		//kontrolli i cilesise se shenjes se marre
		if(featureInscription != null)
			try {
				System.out.println("Karakteristikat e fingerprintit u krijuan");
				enroll.addFeatures(featureInscription);
				enroll.addFeatures(featureInscription);
				enroll.addFeatures(featureInscription);
				enroll.addFeatures(featureInscription);
				
				
				//vizatimi i shenjes se gishtit te kapur
				Image image = CreateFPImage(sample);
				DrawFP(image);
				try {
					identifyFP();
				} catch (IOException ex) {
					Logger.getLogger(ClassRoomView.class.getName()).log(Level.SEVERE,null,ex);
				}
			} catch (DPFPImageQualityException ex) {
				System.err.println("Error: "+ex.getMessage());
			}
		finally{			
			FPState();
			if(enroll.getTemplateStatus().name() == "TEMPLATE_STATUS_FAILED:")
			{
				enroll.clear();
				stop();
				FPState();
				setTemplate(null);
				JOptionPane.showMessageDialog(null,ClassRoomView.this, "Modeli i shenjes nuk u krijua, rivendos gishtin",JOptionPane.ERROR_MESSAGE);
				start();
			}
			else
			{
				setTemplate(enroll.getTemplate());
				SendText("Modeli i shenjes u krijua");
			}			
		}
	}
	
	
	
	public void identifyFP () throws IOException{
		String resgistrationResult ="KO";
		if(properData.getProfessor()!=null){
			DPFPTemplate professorTemplate = DPFPGlobal.getTemplateFactory().createTemplate(properData.getProfessor().getFingerprint());
			setTemplate(professorTemplate);
			DPFPVerificationResult result = verify.verify(featureVerification, getTemplate());
			if(result.isVerified()){//kontrollojme nese eshte profesor

				resgistrationResult=registerProfessorPresence(properData);
				if(resgistrationResult.compareToIgnoreCase("OK")==0){
					imgResult.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/OK.png"))); 
				}
				else if(resgistrationResult.compareToIgnoreCase("already_exists")==0){
					imgResult.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/already_registered.png"))); 
				}
				lblStudenti.setText("Profesori : "+properData.getProfessor().getName()+" "+
						properData.getProfessor().getSurname());

				return;
			}
			else{ //kontrollojme nese eshte student				
				for(int i=0 ; i < properData.getStudents().size();i++){						
					DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(properData.getStudents().get(i).getFingerprint());

					setTemplate(referenceTemplate);

					DPFPVerificationResult result2 = verify.verify(featureVerification, getTemplate());

					if(result2.isVerified()){
						//	JOptionPane.showMessageDialog(null,"Shenja e kapur i perket "+properData.getStudents().get(i).getName(), "Verifikimi i shenjes",
						//	JOptionPane.INFORMATION_MESSAGE);
						resgistrationResult = registerStudentPresence(properData , properData.getStudents().get(i));
						if(resgistrationResult.compareToIgnoreCase("OK")==0){
							imgResult.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/OK.png"))); 
						}
						else if(resgistrationResult.compareToIgnoreCase("already_exists")==0){
							imgResult.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/already_registered.png"))); 
						}
						lblStudenti.setText("Studenti : "+properData.getStudents().get(i).getName()+" "+
								properData.getStudents().get(i).getSurname());
						return;
					}
				}
			}
			//			KO message, nese nuk eshte as student dhe as pedagog
			if(resgistrationResult.compareToIgnoreCase("KO")==0){
				lblStudenti.setText("Personi nuk i përket kësaj klase");
				imgResult.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/KO.png")));
			}
		}		
	}
	
	public DPFPFeatureSet getCharacteristics (DPFPSample sample , DPFPDataPurpose purpose){
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
	
	
	public Image CreateFPImage (DPFPSample sample){
		return DPFPGlobal.getSampleConversionFactory().createImage(sample);
	}
	
	public void DrawFP (Image img){
		fingerprintImage.setIcon(new ImageIcon(
				img.getScaledInstance(fingerprintImage.getWidth(),
				fingerprintImage.getHeight(), 
				img.SCALE_DEFAULT)));
	}
	
	public void FPState(){
				SendText("Fingerprint "+ 
				enroll.getFeaturesNeeded());
	}
	
	public void SendText(String string){
//		txtTextfield.setText(string + "\n");
	}
	
	public void start()
	{
		reader.startCapture();
		SendText("Po perdoret sensori");
	}

	public void stop()
	{
		reader.stopCapture();
		SendText("Nuk po perdoret me sensori");
	}
	
	
	public DPFPTemplate getTemplate(){
		return template;
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
	

	
	public void testFunction(String weekDay)
	{
		System.out.println("testFunction "+weekDay);
	}
	
	public void filterData(int weekDay, Calendar time)
	{
		Date currDate = time.getTime();
		
		int currHour= currDate.getHours();
		if(currHour==8)
		{
			properData = classroomBean.getProperData(1,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==9)
		{
			properData = classroomBean.getProperData(2,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==10)
		{
			properData = classroomBean.getProperData(3,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==11)
		{
			properData = classroomBean.getProperData(4,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==12)
		{
			properData = classroomBean.getProperData(5,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==13)
		{
			properData = classroomBean.getProperData(6,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==14)
		{
			properData = classroomBean.getProperData(7,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==15)
		{
			properData = classroomBean.getProperData(8,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==16)
		{
			properData = classroomBean.getProperData(9,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==17)
		{
			properData = classroomBean.getProperData(10,classroom.getIdClassroom(),weekDay);
		}
		else if(currHour==18)
		{
			properData = classroomBean.getProperData(11,classroom.getIdClassroom(),weekDay);
		}
		setLabels(properData);
		System.out.println("\n Time: "+time.getTime().getHours()+":" + time.getTime().getMinutes()+":"
				+time.getTime().getSeconds()
				+"\n"+ "lenda  : "+ lenda);
	}
	
	
	public void setLabels(ClassroomPojo properData){
		dega = properData.getBranch().getName();
		lenda = properData.getSubject().getName();
		viti = properData.getYear().getYear();
		paraleli = properData.getParallel().getName();
		
		lblLnda.setText("L\u00EBnda: " +lenda);
		
		lblDega.setText("Dega: " +dega);
		
		lblViti.setText("Viti: " +viti); 
		
		lblParaleli.setText( "Paraleli: " +paraleli);
		
		
	}
}
