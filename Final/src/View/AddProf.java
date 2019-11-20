package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

import Bean.AdminBean;
import DBConnection.ConnectToDB;
import Entities.Branch;
import Entities.Parallel;
import Entities.Professor;
import Entities.ProfessorDataPojo;
import Entities.Student;
import Entities.StudentDataPojo;
import Entities.Year;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AddProf extends JDialog {
	private JButton btnRuajProf;
	private JButton btnMbyllProf;
	private JPanel buttonPane;
	private JLabel labelEmri;
	private JLabel labelMbiemri;
	private JLabel labelEmriPerdoruesit;
	private JLabel labelFjalekalimi;
	private JTextField textFieldEmri;
	private JTextField textFieldMbiemri;
	private JTextField textFieldEmriPerdoruesit;
	private JPasswordField passwordField;
	private JPanel panel;
	private JLabel fingerprintImage;
	private JTextPane txtTextfield;
	public AdminBean adminBean = new AdminBean();
	
	
	private DPFPCapture reader = DPFPGlobal.getCaptureFactory().createCapture();
	private DPFPEnrollment enroll = DPFPGlobal.getEnrollmentFactory().createEnrollment();
	private DPFPVerification verify = DPFPGlobal.getVerificationFactory().createVerification();
	private DPFPTemplate template ; 
	public static String TEMPLATE_PROPERTY = "template";
	public DPFPFeatureSet featureInscription;
	public DPFPFeatureSet featureVerification;
	private PropertyChangeSupport changeSupport;
	
	private ConnectToDB cn = new ConnectToDB();


	/**
	 * Create the dialog.
	 */
	public AddProf(Professor selectedProfessor) {
		setTitle("Shto Pedagog");
		setBounds(100, 100, 903, 634);
		setModal(true);
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
		{
			buttonPane = new JPanel();
			{
				btnRuajProf = new JButton("Ruaj");
				btnRuajProf.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// shto pedagog
						if(selectedProfessor !=null)
						{
							// update preofessor
						}
						else
						{
							// insert professor
							insertProfessor();
						}
					}
				});
				btnRuajProf.setActionCommand("OK");
				getRootPane().setDefaultButton(btnRuajProf);
			}
			{
				btnMbyllProf = new JButton("Mbyll");
				btnMbyllProf.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnMbyllProf.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(42)
						.addComponent(btnRuajProf, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addGap(591)
						.addComponent(btnMbyllProf, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(67, Short.MAX_VALUE))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnRuajProf, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnMbyllProf, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
						.addContainerGap())
			);
			buttonPane.setLayout(gl_buttonPane);
		}
		labelEmri = new JLabel("Emri");
		labelMbiemri = new JLabel("Mbiemri");
		labelEmriPerdoruesit = new JLabel("Emri i p\u00EBrdoruesit");
		labelFjalekalimi = new JLabel("Fjal\u00EBkalimi");
		textFieldEmri = new JTextField();
		textFieldEmri.setColumns(10);
		textFieldMbiemri = new JTextField();
		textFieldMbiemri.setColumns(10);
		textFieldEmriPerdoruesit = new JTextField();
		textFieldEmriPerdoruesit.setColumns(10);
		passwordField = new JPasswordField();
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Shenja e gishtit", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		{
			fingerprintImage = new JLabel("");
			fingerprintImage.setBounds(10, 22, 394, 216);
			panel.add(fingerprintImage);
		}
		txtTextfield = new JTextPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(buttonPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelEmri, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelMbiemri, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelEmriPerdoruesit, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelFjalekalimi, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(textFieldMbiemri, Alignment.LEADING)
						.addComponent(textFieldEmriPerdoruesit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
						.addComponent(textFieldEmri, Alignment.LEADING)
						.addComponent(passwordField, Alignment.LEADING))
					.addGap(54)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(102)
					.addComponent(txtTextfield, GroupLayout.PREFERRED_SIZE, 661, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(124, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(50)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelEmri)
								.addComponent(textFieldEmri, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(30)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelMbiemri)
								.addComponent(textFieldMbiemri, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(30)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelEmriPerdoruesit)
								.addComponent(textFieldEmriPerdoruesit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(27)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelFjalekalimi)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
					.addComponent(txtTextfield, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	
	public void stop()
	{
		reader.stopCapture();
		SendText("Nuk po perdoret me sensori");
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
	
	public void start()
	{
		reader.startCapture();
		SendText("Po perdoret sensori");
	}
	
	public void FPState(){
		SendText("Fingerprint "+ 
		enroll.getFeaturesNeeded());
	}
	
	public void SendText(String string){
		txtTextfield.setText(string + "\n");
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
				JOptionPane.showMessageDialog(null,AddProf.this, "Modeli i shenjes nuk u krijua, rivendos gishtin",JOptionPane.ERROR_MESSAGE);
				start();
			}
			else
			{
				setTemplate(enroll.getTemplate());
				SendText("Modeli i shenjes u krijua");
			}			
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
	
	public DPFPFeatureSet getCharacteristics (DPFPSample sample , DPFPDataPurpose purpose){
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
	
	public void setTemplate (DPFPTemplate template){
		DPFPTemplate old = this.template;
		this.template = template ; 
		firePropertyChange(TEMPLATE_PROPERTY,old,template);
	}
	
	
	
	
	
	public void insertProfessor()
	{
		ProfessorDataPojo professorData = new ProfessorDataPojo();
		Professor professor = new Professor();
		
		
		if(textFieldEmri.getText() == null || textFieldEmri.getText().isEmpty() 
				||textFieldMbiemri.getText()==null || textFieldMbiemri.getText().isEmpty() 
				||textFieldEmriPerdoruesit.getText() == null ||textFieldEmriPerdoruesit.getText().isEmpty() 
				|| String.valueOf(passwordField.getPassword()) == null || String.valueOf(passwordField.getPassword()).isEmpty()
				|| template == null)
		{
			JOptionPane.showMessageDialog(null,"KUJDES! Nuk janë plotësuar të gjitha fushat. ", "Mungese të dhënash",
					JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			professor.setName(textFieldEmri.getText());
			professor.setSurname(textFieldMbiemri.getText());
			professor.setUsername(textFieldEmriPerdoruesit.getText());
			professor.setPassword(String.valueOf(passwordField.getPassword()));

			
			ByteArrayInputStream FPDates = new ByteArrayInputStream(template.serialize());
			Integer  fpSize = template.serialize().length;


			professorData.setProfessor(professor);
			professorData.setFPDates(FPDates);
			professorData.setFpSize(fpSize);

			adminBean.insertProfessor(professorData);
		}		
	}
}
