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
import Entities.Professor;
import Entities.Professor_Subject;
import Entities.Subject;
import Entities.SubjectType;
import Entities.Year;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.awt.event.ItemEvent;

public class AddProf_Subject extends JDialog {
	private JButton btnRuaj;
	private JButton btnMbyll;
	private JPanel panel;
	private JLabel lblDega;
	private JLabel lblViti;
	private JComboBox comboBoxViti_Prof;
	private JComboBox comboBoxLenda_Prof;
	private JComboBox comboBoxDega_Prof;
	private JComboBox comboBoxLlojiLendes;
	public AdminBean adminBean = new AdminBean();
	private int idSelectedBranch;
	private int idSelectedYear;
	



	/**
	 * Create the dialog.
	 */
	public AddProf_Subject(Professor selectedProfessor) {
		setBounds(100, 100, 581, 371);
		
		setModal(true);
		
		panel = new JPanel();
		
		JLabel lblPedagogu = new JLabel("Pedagogu:");
		
		lblDega = new JLabel("Dega");
		
		lblViti = new JLabel("Viti");
		
		JLabel lblLnda = new JLabel("L\u00EBnda");
		
		comboBoxDega_Prof = new JComboBox();
		comboBoxDega_Prof.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					comboBoxViti_Prof.setEnabled(true);
					comboBoxViti_Prof.setSelectedItem(null);
					comboBoxLenda_Prof.setEnabled(false);
					comboBoxLenda_Prof.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
				}
			}
		});
		adminBean.populateComboboxBranches(comboBoxDega_Prof);
		
		comboBoxViti_Prof = new JComboBox();
		comboBoxViti_Prof.setEnabled(false);
		adminBean.populateComboboxYears(comboBoxViti_Prof);
		comboBoxViti_Prof.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					comboBoxLenda_Prof.setEnabled(true);
					comboBoxLenda_Prof.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
					
					
					ArrayList<Branch> listOfBranches = adminBean.getListOfBranches();
					if(listOfBranches.size()>0)
					{
						for (int i=0; i<listOfBranches.size();i++)
						{
							if (listOfBranches.get(i).getName() != null)
							{
								if(listOfBranches.get(i).getName().equalsIgnoreCase((String) comboBoxDega_Prof.getSelectedItem()))
								{
									idSelectedBranch=listOfBranches.get(i).getIdBranch();
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
								if(listOfYears.get(i).getYear()==(Integer)comboBoxViti_Prof.getSelectedItem())
								{
									idSelectedYear=listOfYears.get(i).getIdYear();
									break;
								}	
							}							
						}
					}
					
					
					adminBean.populateComboboxPropperSubjects(comboBoxLenda_Prof,idSelectedBranch,idSelectedYear);
				}
				else
				{
					comboBoxLenda_Prof.setEnabled(false);
					comboBoxLenda_Prof.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
				}
			}
		});
		
		
		comboBoxLenda_Prof = new JComboBox();
		comboBoxLenda_Prof.setEnabled(false);
		
		comboBoxLenda_Prof.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					comboBoxLlojiLendes.setEnabled(true);
					comboBoxLlojiLendes.setSelectedItem(null);
				}
				else
				{
					comboBoxLlojiLendes.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
				}
			}
		});
		
		JLabel lblLlojiLnds = new JLabel("Lloji l\u00EBnd\u00EBs");
		
		comboBoxLlojiLendes = new JComboBox();
		adminBean.populateComboboxSubjectType(comboBoxLlojiLendes);
		comboBoxLlojiLendes.setEnabled(false);
		comboBoxLlojiLendes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					
				}
			}
		});
		
		
	
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(155)
					.addComponent(lblPedagogu)
					.addContainerGap(358, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(67)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblLlojiLnds, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblLnda, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblViti, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblDega, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(comboBoxLlojiLendes, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxLenda_Prof, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxViti_Prof, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxDega_Prof, 0, 235, Short.MAX_VALUE))
					.addContainerGap(177, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPedagogu)
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDega)
						.addComponent(comboBoxDega_Prof, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblViti)
						.addComponent(comboBoxViti_Prof, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLnda)
						.addComponent(comboBoxLenda_Prof, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLlojiLnds)
						.addComponent(comboBoxLlojiLendes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
		);
		btnRuaj = new JButton("Ruaj");
		btnRuaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if(comboBoxDega_Prof.getSelectedItem()==null 
					|| comboBoxViti_Prof.getSelectedItem() ==null 
					|| comboBoxLenda_Prof.getSelectedItem() == null
					|| comboBoxLlojiLendes.getSelectedItem() ==null )
					{
						JOptionPane.showMessageDialog(null,"KUJDES! Nuk janë plotësuar të gjitha fushat. ", "Mungese të dhënash",
								JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						Professor_Subject addSubjectToProf = new Professor_Subject();
						addSubjectToProf.setIdProfessor(selectedProfessor.getIdProfessor());
						
						
						ArrayList<Subject> listOfSubjects = adminBean.getPropperSubjects(idSelectedBranch,idSelectedYear);
						if(listOfSubjects.size()>0)
						{
							for (int i=0; i<listOfSubjects.size();i++)
							{
								if (listOfSubjects.get(i).getName() != null)
								{
									if(listOfSubjects.get(i).getName().equalsIgnoreCase((String) comboBoxLenda_Prof.getSelectedItem()))
									{
										addSubjectToProf.setIdSubject(listOfSubjects.get(i).getIdSubject());
										break;
									}
								}								
							}
						}
						
						
						ArrayList<SubjectType> listOfSubjectTypes = adminBean.getSubjectTypes();
						if(listOfSubjectTypes.size()>0)
						{
							for (int i=0; i<listOfSubjectTypes.size();i++)
							{
								if(listOfSubjectTypes.get(i).getSubjectTypeName()!=null)
								{
									if(listOfSubjectTypes.get(i).getSubjectTypeName().equalsIgnoreCase((String)comboBoxLlojiLendes.getSelectedItem()))
									{
										addSubjectToProf.setIdSubjectType(listOfSubjectTypes.get(i).getIdSubjectType());
										break;
									}	
								}							
							}
						}
						
						
						EditProf.subjectsToBeAdded.add(addSubjectToProf);
						EditProf.populateProfSubjectTable(selectedProfessor);
						dispose();
						//adminBean.addSubjectToProf(addSubjectToProf);
						//retherritet funksioni i popullimit te tabels se lendeve te profesorit
						
					}
				
			}
		});
		btnRuaj.setActionCommand("OK");
		btnMbyll = new JButton("Mbyll");
		btnMbyll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnMbyll.setActionCommand("Cancel");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(49)
					.addComponent(btnRuaj, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(277)
					.addComponent(btnMbyll, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(60, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnMbyll, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRuaj, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
	}
}
