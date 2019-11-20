package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Bean.AdminBean;
import Entities.Branch;
import Entities.Parallel;
import Entities.Student;
import Entities.StudentDataPojo;
import Entities.Subject;
import Entities.SubjectDataPojo;
import Entities.Year;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class AddEditSubject extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton ruajButton;
	private JButton mbyllButton;
	private JTextField textFieldLenda;
	private JComboBox comboBoxVitiTabLendet;
	private JComboBox comboBoxDegaTabLendet;
	public AdminBean adminBean = new AdminBean();


	/**
	 * Create the dialog.
	 */
	public AddEditSubject(SubjectDataPojo selectedSubject) {
		setTitle("Shto l\u00EBnd\u00EB");
		setBounds(100, 100, 454, 269);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		
		setModal(true); // nuk mun te kryhet asnje veprim pa mbyllur kete popup
		
		JLabel lblNewLabel = new JLabel("L\u00EBnda:");
		
		JLabel lblNewLabel_1 = new JLabel("Dega:");
		
		JLabel lblViti = new JLabel("Viti:");
		
		textFieldLenda = new JTextField();
		textFieldLenda.setColumns(10);
		
		comboBoxDegaTabLendet = new JComboBox();
		adminBean.populateComboboxBranches(comboBoxDegaTabLendet);
		
		comboBoxVitiTabLendet = new JComboBox();
		adminBean.populateComboboxYears(comboBoxVitiTabLendet);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1)
						.addComponent(lblViti))
					.addGap(32)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBoxVitiTabLendet, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(comboBoxDegaTabLendet, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
							.addComponent(textFieldLenda, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)))
					.addGap(80))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(textFieldLenda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(comboBoxDegaTabLendet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblViti)
						.addComponent(comboBoxVitiTabLendet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(79, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				mbyllButton = new JButton("Mbyll");
				mbyllButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				mbyllButton.setActionCommand("Cancel");
			}
			{
				ruajButton = new JButton("Ruaj");
				ruajButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(selectedSubject !=null)
						{
							updateSubject(selectedSubject);
						}
						else
						{
							addSubject();
						}						
					}
				});
				ruajButton.setActionCommand("OK");
				getRootPane().setDefaultButton(ruajButton);
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.TRAILING)
					.addGroup(Alignment.LEADING, gl_buttonPane.createSequentialGroup()
						.addGap(30)
						.addComponent(ruajButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
						.addComponent(mbyllButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addGap(34))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(ruajButton)
							.addComponent(mbyllButton, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(12, Short.MAX_VALUE))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
		populateFields(selectedSubject);
	}
	
	
	public void populateFields(SubjectDataPojo selectedSubject)
	{
		if(selectedSubject != null)
		{
			textFieldLenda.setText(selectedSubject.getLenda().getName());			
			comboBoxDegaTabLendet.setSelectedItem(selectedSubject.getDega().getName());
			comboBoxVitiTabLendet.setSelectedItem(selectedSubject.getViti().getYear());
		}
	}
	
	public void addSubject()
	{
		Subject subject = new Subject();
		
		
		if(textFieldLenda.getText() == null || textFieldLenda.getText().isEmpty() ||comboBoxDegaTabLendet.getSelectedItem()==null 
				|| comboBoxVitiTabLendet.getSelectedItem() ==null)
		{
			JOptionPane.showMessageDialog(null,"KUJDES! Nuk janë plotësuar të gjitha fushat. ", "Mungese të dhënash",
					JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			subject.setName(textFieldLenda.getText());
			

			ArrayList<Branch> listOfBranches = adminBean.getListOfBranches();
			if(listOfBranches.size()>0)
			{
				for (int i=0; i<listOfBranches.size();i++)
				{
					if (listOfBranches.get(i).getName() != null)
					{
						if(listOfBranches.get(i).getName().equalsIgnoreCase((String) comboBoxDegaTabLendet.getSelectedItem()))
						{
							subject.setIdBranch(listOfBranches.get(i).getIdBranch());
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
						if(listOfYears.get(i).getYear()==(Integer)comboBoxVitiTabLendet.getSelectedItem())
						{
							subject.setIdYear(listOfYears.get(i).getIdYear());
							break;
						}	
					}							
				}
			}
			

			adminBean.insertSubject(subject);
		}		
	
	}
	
	
	
	public void updateSubject(SubjectDataPojo selectedSubject)
	{
		Subject subject = selectedSubject.getLenda();
		
		
		if(textFieldLenda.getText() == null || textFieldLenda.getText().isEmpty() ||comboBoxDegaTabLendet.getSelectedItem() == null 
				|| comboBoxVitiTabLendet.getSelectedItem() == null)
		{
			JOptionPane.showMessageDialog(null,"KUJDES! Nuk janë plotësuar të gjitha fushat. ", "Mungese të dhënash",
					JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			subject.setName(textFieldLenda.getText());			

			ArrayList<Branch> listOfBranches = adminBean.getListOfBranches();
			if(listOfBranches.size()>0)
			{
				for (int i=0; i<listOfBranches.size();i++)
				{
					if (listOfBranches.get(i).getName() != null)
					{
						if(listOfBranches.get(i).getName().equalsIgnoreCase((String) comboBoxDegaTabLendet.getSelectedItem()))
						{
							subject.setIdBranch(listOfBranches.get(i).getIdBranch());
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
						if(listOfYears.get(i).getYear()==(Integer)comboBoxVitiTabLendet.getSelectedItem())
						{
							subject.setIdYear(listOfYears.get(i).getIdYear());
							break;
						}	
					}							
				}
			}
			adminBean.updateSubject(subject);
		}		
	
	}
}
