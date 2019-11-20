package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
import com.digitalpersona.onetouch.DPFPData;
import com.digitalpersona.*;
import com.toedter.calendar.JDateChooser;

import Bean.AdminBean;
import DBConnection.ConnectToDB;
import Entities.Branch;
import Entities.Parallel;
import Entities.Student;
import Entities.StudentDataPojo;
import Entities.Year;

import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class AddEditStudent extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblEmri;
	private JLabel lblAtesia;
	private JLabel lblMbiemri;
	private JLabel lblDatlindja;
	private JTextField textFieldEmri;
	private JTextField textFieldAtesia;
	private JTextField textFieldMbiemri;
	private JPanel paneliFingerprint;
	private JLabel fingerprintImage;
	private JPanel panel;
	private JButton button;
	private JButton button_1;
	private JDateChooser dateChooser;
	private JComboBox comboBoxDega;
	public AdminBean adminBean = new AdminBean();
	private JComboBox comboBoxParaleli;
	private JComboBox comboBoxViti;
	private DPFPCapture reader = DPFPGlobal.getCaptureFactory().createCapture();
	private DPFPEnrollment enroll = DPFPGlobal.getEnrollmentFactory().createEnrollment();
	private DPFPVerification verify = DPFPGlobal.getVerificationFactory().createVerification();
	private DPFPTemplate template ; 
	public static String TEMPLATE_PROPERTY = "template";
	public DPFPFeatureSet featureInscription;
	public DPFPFeatureSet featureVerification;
	private PropertyChangeSupport changeSupport;
	
	private ConnectToDB cn = new ConnectToDB();
	private JTextPane txtTextfield;


	/**
	 * Create the dialog.
	 */
	public AddEditStudent(StudentDataPojo selectedStudent) {
		setBounds(100, 100, 805, 568);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		
		
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
		
		
		getContentPane().add(contentPanel, BorderLayout.WEST);
		setModal(true);
		lblEmri = new JLabel("Emri");
		lblAtesia = new JLabel("At\u00EBsia");
		lblMbiemri = new JLabel("Mbiemri");
		
		lblDatlindja = new JLabel("Dat\u00EBlindja");
		
		textFieldEmri = new JTextField();
		textFieldEmri.setColumns(10);
		
		textFieldAtesia = new JTextField();
		textFieldAtesia.setColumns(10);
		
		textFieldMbiemri = new JTextField();
		textFieldMbiemri.setColumns(10);
		
		dateChooser = new JDateChooser();
		
		JLabel lblDega = new JLabel("Dega");
		
		JLabel lblViti = new JLabel("Viti");
		
		JLabel lblParaleli = new JLabel("Paraleli");
		
		comboBoxDega = new JComboBox();
		adminBean.populateComboboxBranches(comboBoxDega);
		
		comboBoxViti = new JComboBox();
		adminBean.populateComboboxYears(comboBoxViti);
		
		comboBoxParaleli = new JComboBox();
		adminBean.populateComboboxParallels(comboBoxParaleli);
		
		paneliFingerprint = new JPanel();
		paneliFingerprint.setLayout(null);
		paneliFingerprint.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Shenja e gishtit", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		fingerprintImage = new JLabel("");
		fingerprintImage.setBounds(10, 22, 345, 216);
		paneliFingerprint.add(fingerprintImage);
		
		panel = new JPanel();
		
		button = new JButton("Ruaj");
		button.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/ruaj.png")));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedStudent !=null)
				{
//					modifikimi i studentit ekzistues
					updateStudent(selectedStudent);
				}
				else if(selectedStudent ==null)
				{
//					shtimi i nje studenti te ri
					insertStudent();
				}
			}
		});
		button.setActionCommand("OK");
		
		button_1 = new JButton("Mbyll");
		button_1.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/mbyll.png")));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stop();
				dispose();
			}
		});
		button_1.setActionCommand("Cancel");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(73)
					.addComponent(button)
					.addPreferredGap(ComponentPlacement.RELATED, 466, Short.MAX_VALUE)
					.addComponent(button_1)
					.addGap(72))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(button_1)))
		);
		panel.setLayout(gl_panel);
		
		txtTextfield = new JTextPane();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(17)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblEmri)
										.addComponent(lblAtesia)
										.addComponent(lblMbiemri)
										.addComponent(lblDatlindja)
										.addComponent(lblDega)
										.addComponent(lblViti, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
									.addGap(4))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblParaleli)
									.addGap(18)))
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtTextfield, GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(comboBoxParaleli, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
											.addComponent(textFieldMbiemri, 275, 275, Short.MAX_VALUE)
											.addComponent(textFieldAtesia, 275, 275, Short.MAX_VALUE)
											.addComponent(textFieldEmri, 275, 275, Short.MAX_VALUE)
											.addComponent(comboBoxDega, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
											.addComponent(comboBoxViti, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)))
									.addGap(10)
									.addComponent(paneliFingerprint, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE)))
							.addGap(42))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblEmri)
								.addComponent(textFieldEmri, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(24)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAtesia)
								.addComponent(textFieldAtesia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(24)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMbiemri)
								.addComponent(textFieldMbiemri, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblDatlindja)
								.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDega)
								.addComponent(comboBoxDega, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBoxViti, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblViti))
							.addGap(21)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblParaleli)
								.addComponent(comboBoxParaleli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6))
						.addComponent(paneliFingerprint, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))
					.addGap(65)
					.addComponent(txtTextfield, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(15))
		);
		contentPanel.setLayout(gl_contentPanel);	
		
		populateFields(selectedStudent);	
	}
	
//	popullon popupin me te dhena ekzistuese
	public void populateFields(StudentDataPojo selectedStudent)
	{
		if(selectedStudent != null)
		{
			textFieldEmri.setText(selectedStudent.getStudent().getName());
			textFieldAtesia.setText(selectedStudent.getStudent().getFathersName());
			textFieldMbiemri.setText(selectedStudent.getStudent().getSurname());
			dateChooser.setDate(selectedStudent.getStudent().getBirthdate());
			comboBoxDega.setSelectedItem(selectedStudent.getDega().getName());
			comboBoxViti.setSelectedItem(selectedStudent.getViti().getYear());
			comboBoxParaleli.setSelectedItem(selectedStudent.getParaleli().getName());
						
//			DPFPSample getSample =DPFPGlobal.getSampleFactory().createSample(selectedStudent.getStudent().getFingerprint());
//			Image image = CreateFPImage(getSample);
			fingerprintImage.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/shenje_ekzistuese.png")));
//			DrawFP(button.setIcon(new ImageIcon(AddEditStudent.class.getResource("/Images/ruaj.png"))););
		}
	}
	public void insertStudent()
	{
		StudentDataPojo studentsData = new StudentDataPojo();
		Student student = new Student();
		
		
		if(textFieldEmri.getText() == null || textFieldEmri.getText().isEmpty() 
				||textFieldMbiemri.getText()==null || textFieldMbiemri.getText().isEmpty() 
				||textFieldAtesia.getText() == null ||textFieldAtesia.getText().isEmpty() 
				|| dateChooser.getDate() == null ||comboBoxDega.getSelectedItem()==null 
				|| comboBoxViti.getSelectedItem() ==null || comboBoxParaleli.getSelectedItem() == null
				|| template == null)
		{
			JOptionPane.showMessageDialog(null,"KUJDES! Nuk janë plotësuar të gjitha fushat. ", "Mungese të dhënash",
					JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			student.setName(textFieldEmri.getText());
			student.setSurname(textFieldMbiemri.getText());
			student.setFathersName(textFieldAtesia.getText());
			student.setBirthdate(dateChooser.getDate());

			ArrayList<Branch> listOfBranches = adminBean.getListOfBranches();
			if(listOfBranches.size()>0)
			{
				for (int i=0; i<listOfBranches.size();i++)
				{
					if (listOfBranches.get(i).getName() != null)
					{
						if(listOfBranches.get(i).getName().equalsIgnoreCase((String) comboBoxDega.getSelectedItem()))
						{
							student.setBranch(listOfBranches.get(i).getIdBranch());
							break;
						}
					}								
				}
			}
			
			
			ArrayList<Year> listOfYears = adminBean.getListOfYears();
			if(listOfYears.size()>0)
			{
				for (int i=0; i<listOfYears.size();i++)
				{
					if(listOfYears.get(i)!=null)
					{
						if(listOfYears.get(i).getYear()==(Integer)comboBoxViti.getSelectedItem())
						{
							student.setYear(listOfYears.get(i).getIdYear());
							break;
						}	
					}							
				}
			}
			
			
			ArrayList<Parallel> listOfParallels = adminBean.getListOfParallels();
			if(listOfParallels.size()>0)
			{
				for (int i=0; i<listOfParallels.size();i++)
				{
					if(listOfParallels.get(i).getName() !=null)
					{
						if(listOfParallels.get(i).getName().equalsIgnoreCase((String) comboBoxParaleli.getSelectedItem()))
						{
							student.setParallel(listOfParallels.get(i).getIdParallel());
							break;
						}
					}				
				}
			}

			ByteArrayInputStream FPDates = new ByteArrayInputStream(template.serialize());
			Integer  fpSize = template.serialize().length;


			studentsData.setStudent(student);
			studentsData.setFPDates(FPDates);
			studentsData.setFpSize(fpSize);

			adminBean.insertStudent(studentsData);
			stop();
			dispose();
		}		
	}
	
	
	
	public void updateStudent(StudentDataPojo selectedStudent)
	{		
		Student student =selectedStudent.getStudent();		
		
		if(textFieldEmri.getText() == null || textFieldEmri.getText().isEmpty() 
			||textFieldMbiemri.getText()==null || textFieldMbiemri.getText().isEmpty() 
			||textFieldAtesia.getText() == null ||textFieldAtesia.getText().isEmpty() 
			|| dateChooser.getDate() == null ||comboBoxDega.getSelectedItem()==null 
			|| comboBoxViti.getSelectedItem() ==null || comboBoxParaleli.getSelectedItem() == null )
		{
			JOptionPane.showMessageDialog(null,"KUJDES! Nuk janë plotësuar të gjitha fushat. ", "Mungese të dhënash",
					JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			student.setName(textFieldEmri.getText());
			student.setSurname(textFieldMbiemri.getText());
			student.setFathersName(textFieldAtesia.getText());
			student.setBirthdate(dateChooser.getDate());
			
			ArrayList<Branch> listOfBranches = adminBean.getListOfBranches();
			if(listOfBranches.size()>0)
			{
				for (int i=0; i<listOfBranches.size();i++)
				{
					if (listOfBranches.get(i).getName() != null)
					{
						if(listOfBranches.get(i).getName().equalsIgnoreCase((String) comboBoxDega.getSelectedItem()))
						{
							student.setBranch(listOfBranches.get(i).getIdBranch());
							break;
						}
					}								
				}
			}
			
			
			ArrayList<Year> listOfYears = adminBean.getListOfYears();
			if(listOfYears.size()>0)
			{
				for (int i=0; i<listOfYears.size();i++)
				{
					if(listOfYears.get(i)!=null)
					{
						if(listOfYears.get(i).getYear()==(Integer)comboBoxViti.getSelectedItem())
						{
							student.setYear(listOfYears.get(i).getIdYear());
							break;
						}	
					}							
				}
			}
			
			
			ArrayList<Parallel> listOfParallels = adminBean.getListOfParallels();
			if(listOfParallels.size()>0)
			{
				for (int i=0; i<listOfParallels.size();i++)
				{
					if(listOfParallels.get(i).getName() !=null)
					{
						if(listOfParallels.get(i).getName().equalsIgnoreCase((String) comboBoxParaleli.getSelectedItem()))
						{
							student.setParallel(listOfParallels.get(i).getIdParallel());
							break;
						}
					}				
				}
			}
			
			if(template != null)
			{
				ByteArrayInputStream FPDates = new ByteArrayInputStream(template.serialize());
				Integer  fpSize = template.serialize().length;
				selectedStudent.setFPDates(FPDates);
				selectedStudent.setFpSize(fpSize);
			}		
			selectedStudent.setStudent(student);				
			adminBean.updateStudent(selectedStudent);
			stop();
			dispose();
		}				
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
				JOptionPane.showMessageDialog(null,AddEditStudent.this, "Modeli i shenjes nuk u krijua, rivendos gishtin",JOptionPane.ERROR_MESSAGE);
				start();
			}
			else
			{
				setTemplate(enroll.getTemplate());
				SendText("Modeli i shenjes u krijua");
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
		txtTextfield.setText(string + "\n");
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
	
	

}
