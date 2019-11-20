package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Bean.AdminBean;
import Entities.ClassRoom;
import Entities.ProfessorDataPojo;
import Entities.Schedule;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddSchedule extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;
	private JComboBox comboBoxLendaShtoOre;
	private JComboBox comboBoxTipiLendesShtoOre;
	private JComboBox comboBoxPedagogShtoOre;
	private JComboBox comboBoxSallaShtoOre;
	public AdminBean adminBean = new AdminBean();


	/**
	 * Create the dialog.
	 */
	public AddSchedule(Schedule shtoOre) {
		setBounds(100, 100, 450, 300);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblLnda = new JLabel("L\u00EBnda");
		
		JLabel lblTipiIlnds = new JLabel("Tipi il\u00EBnd\u00EBs");
		
		JLabel lblPedagogu = new JLabel("Pedagogu");
		
		JLabel lblSalla = new JLabel("Salla");
		
		comboBoxLendaShtoOre = new JComboBox();
		adminBean.populateComboboxPropperSubjects(comboBoxLendaShtoOre,shtoOre.getBranch(),shtoOre.getYear());
		comboBoxLendaShtoOre.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					
					comboBoxTipiLendesShtoOre.setEnabled(true);
					comboBoxTipiLendesShtoOre.setSelectedItem(null);
					comboBoxPedagogShtoOre.setEnabled(false);
					comboBoxPedagogShtoOre.setSelectedItem(null);
					comboBoxSallaShtoOre.setEnabled(false);
					comboBoxSallaShtoOre.setSelectedItem(null);
				}
				else
				{
					comboBoxTipiLendesShtoOre.setEnabled(false);
					comboBoxTipiLendesShtoOre.setSelectedItem(null);
					comboBoxPedagogShtoOre.setEnabled(false);
					comboBoxPedagogShtoOre.setSelectedItem(null);
					comboBoxSallaShtoOre.setEnabled(false);
					comboBoxSallaShtoOre.setSelectedItem(null);
				}
			}
		});
		
		comboBoxTipiLendesShtoOre = new JComboBox();
		comboBoxTipiLendesShtoOre.setEnabled(false);
		adminBean.populateComboboxSubjectType(comboBoxTipiLendesShtoOre);
		comboBoxTipiLendesShtoOre.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{	
					comboBoxPedagogShtoOre.removeAllItems();
					adminBean.populateComboboxProfessorSchedule(comboBoxPedagogShtoOre,shtoOre.getBranch(),shtoOre.getYear()
							,(String) comboBoxLendaShtoOre.getSelectedItem(),(String) comboBoxTipiLendesShtoOre.getSelectedItem());
					comboBoxPedagogShtoOre.setEnabled(true);
					comboBoxPedagogShtoOre.setSelectedItem(null);
					comboBoxSallaShtoOre.setEnabled(false);
					comboBoxSallaShtoOre.setSelectedItem(null);
				}
				else
				{
					comboBoxPedagogShtoOre.setEnabled(false);
					comboBoxPedagogShtoOre.setSelectedItem(null);
					comboBoxSallaShtoOre.setEnabled(false);
					comboBoxSallaShtoOre.setSelectedItem(null);
				}
			}
		});
		
		comboBoxPedagogShtoOre = new JComboBox();
		comboBoxPedagogShtoOre.setEnabled(false);
		comboBoxPedagogShtoOre.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{	
					comboBoxSallaShtoOre.removeAllItems();
					adminBean.populateComboboxClassroomSchedule(comboBoxSallaShtoOre);					
					comboBoxSallaShtoOre.setEnabled(true);
					comboBoxSallaShtoOre.setSelectedItem(null);
				}
				else
				{
					comboBoxSallaShtoOre.setEnabled(false);
					comboBoxSallaShtoOre.setSelectedItem(null);
				}
			}
		});
		
		comboBoxSallaShtoOre = new JComboBox();
		comboBoxSallaShtoOre.setEnabled(false);
		comboBoxSallaShtoOre.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					
				}
				else
				{
					
				}
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLnda)
						.addComponent(lblTipiIlnds)
						.addComponent(lblPedagogu)
						.addComponent(lblSalla))
					.addGap(76)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(comboBoxSallaShtoOre, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxPedagogShtoOre, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxTipiLendesShtoOre, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxLendaShtoOre, 0, 242, Short.MAX_VALUE))
					.addContainerGap(44, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLnda)
						.addComponent(comboBoxLendaShtoOre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTipiIlnds)
						.addComponent(comboBoxTipiLendesShtoOre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPedagogu)
						.addComponent(comboBoxPedagogShtoOre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSalla)
						.addComponent(comboBoxSallaShtoOre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(103, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Ruaj");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(comboBoxLendaShtoOre.getSelectedItem() ==null || comboBoxTipiLendesShtoOre.getSelectedItem() == null
								|| comboBoxPedagogShtoOre.getSelectedItem() ==null ||comboBoxSallaShtoOre.getSelectedItem() == null)
						{
							JOptionPane.showMessageDialog(null,"KUJDES! Nuk janë plotësuar të gjitha fushat. ", "Mungese të dhënash",
									JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							
							shtoOre.setSubject(adminBean.getIdSelectedSubject(shtoOre.getBranch(),shtoOre.getYear(),(String)comboBoxLendaShtoOre.getSelectedItem()));
							if(comboBoxTipiLendesShtoOre.getSelectedItem().equals("Leksion"))
							{
								shtoOre.setSubjectType(1);
							}
							else if(comboBoxTipiLendesShtoOre.getSelectedItem().equals("Seminar"))
							{
								shtoOre.setSubjectType(2);
							}
							
							ArrayList<ProfessorDataPojo> selectedProf =adminBean.searchProfessor((String)comboBoxPedagogShtoOre.getSelectedItem());
							if(selectedProf.size()==1)
							{
								shtoOre.setProfessor(selectedProf.get(0).getProfessor().getIdProfessor());
							}						
							
							
							
							ArrayList<ClassRoom> listOfClassrooms = new ArrayList<ClassRoom> ();
							listOfClassrooms = adminBean.getListOfClassroomsToSchedule();
							if(listOfClassrooms.size()>0)
							{
								for (int i=0; i<listOfClassrooms.size();i++)
								{
									if(listOfClassrooms.get(i).getName().equalsIgnoreCase((String)comboBoxSallaShtoOre.getSelectedItem()))
									{
										shtoOre.setClassroom(listOfClassrooms.get(i).getIdClassroom());
									}
								}
							}
							
							
							adminBean.insertSchedule(shtoOre);
							dispose();
//							Admin.populateTableOrar(String branch , int year , String parallel)
							
							
						
						}
					}
				});
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Mbyll");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(33)
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addGap(34))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(okButton)
							.addComponent(cancelButton)))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}
}
