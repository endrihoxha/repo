package View;

import java.awt.EventQueue;

import Bean.*;
import Entities.Branch;
import Entities.Parallel;
import Entities.ProfessorDataPojo;
import Entities.Schedule;
import Entities.ScheduleDataPojo;
import Entities.Student;
import Entities.StudentDataPojo;
import Entities.SubjectDataPojo;
import Entities.Year;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Admin extends JFrame {
	public JFrame frmAdmin;
	public JTabbedPane tabbedPane;
	private JTable studentsTable;
	private JComboBox comboBoxDega;
	private JComboBox comboBoxViti;
	private JComboBox comboBoxParaleli;
	public static AdminBean adminBean = new AdminBean();
	public AddEditStudent addStudent;
	public AddEditSubject addEditSubject;
	public DeleteStudent deleteStudent;
	private StudentDataPojo selectedStudent;
	private JTable profTable;
	private JTable subjectsTable;
	private JTextField textFieldKerkoLende;
	private JComboBox comboBoxVitiTabLendet;
	private JComboBox comboBoxDegaTabLendet;
	private JTextField textFieldkerkoPedagog;
	private JButton buttonShtoLende;
	private JButton buttonModifikoLende;
	private JButton buttonFshiLende;
	private JButton btnDetaje;
	private JButton btnShtoPedagog;
	private static JTable tableOrari;
	private JComboBox comboBoxDegaOrar;
	private JComboBox comboBoxVitiOrar;
	private JComboBox comboBoxParaleliOrar;
	private JButton buttonShtoOrar;
	private JButton buttonFshiOrar;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Admin window = new Admin();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Admin() {
		setTitle("Admin");		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
		);
		
		JPanel studentPanel = new JPanel();
		tabbedPane.addTab("Student", null, studentPanel, null);
		
		JLabel lblFiltroStudentet = new JLabel("Filtro studentet:");
		
		JLabel lblDega = new JLabel("Dega:");		
		comboBoxDega = new JComboBox();
		comboBoxDega.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{
					comboBoxViti.setSelectedItem(null);
					comboBoxViti.setEnabled(true);
					comboBoxParaleli.setSelectedItem(null);
					comboBoxParaleli.setEnabled(false);
				}
				else
				{
					comboBoxViti.setSelectedItem(null);
					comboBoxViti.setEnabled(false);
					comboBoxParaleli.setSelectedItem(null);
					comboBoxParaleli.setEnabled(false);					
				}
			}
		});
		adminBean.populateComboboxBranches(comboBoxDega);
		
		
		
		
		JLabel lblViti = new JLabel("Viti:");		
		comboBoxViti = new JComboBox();
		comboBoxViti.setEnabled(false);
		comboBoxViti.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{	
					comboBoxParaleli.setSelectedItem(null);
					comboBoxParaleli.setEnabled(true);					
				}
				else
				{
					comboBoxParaleli.setSelectedItem(null);
					comboBoxParaleli.setEnabled(false);	
				}
			}
		});
		adminBean.populateComboboxYears(comboBoxViti);
		
		
		
		
		
		
		JLabel lblNewLabel = new JLabel("Paraleli:");		
		comboBoxParaleli = new JComboBox();
		comboBoxParaleli.setEnabled(false);
		comboBoxParaleli.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{	
					String dega=comboBoxDega.getSelectedItem().toString();	
					int viti = Integer.parseInt(comboBoxViti.getSelectedItem().toString());
					String paraleli=comboBoxParaleli.getSelectedItem().toString();
					populateStudentsTable(dega,viti,paraleli);
				}
				else
				{
					
				}
			}
		});
		adminBean.populateComboboxParallels(comboBoxParaleli);
		
		
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		GroupLayout gl_studentPanel = new GroupLayout(studentPanel);
		gl_studentPanel.setHorizontalGroup(
			gl_studentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_studentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_studentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_studentPanel.createSequentialGroup()
							.addComponent(lblFiltroStudentet)
							.addGap(28)
							.addComponent(lblDega)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxDega, 0, 369, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblViti)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxViti, 0, 102, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxParaleli, 0, 104, Short.MAX_VALUE))
						.addGroup(gl_studentPanel.createSequentialGroup()
							.addGap(56)
							.addGroup(gl_studentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE))))
					.addGap(36))
		);
		gl_studentPanel.setVerticalGroup(
			gl_studentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_studentPanel.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_studentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFiltroStudentet)
						.addComponent(lblDega)
						.addComponent(comboBoxDega, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblViti)
						.addComponent(comboBoxViti, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(comboBoxParaleli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(8))
		);
		
		
		JButton btnShto = new JButton("Shto");
		btnShto.setHorizontalAlignment(0);
		btnShto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addStudent = new AddEditStudent(null);
				addStudent.setTitle("Shto student");
				addStudent.setVisible(true);				
			}
		});
		
		
		
		JButton btnModifiko = new JButton("Modifiko");
		btnModifiko.setHorizontalAlignment(0);
		btnModifiko.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(studentsTable.getSelectedRow() != -1) // kontollon nese ka rresht te selektuar nga tabela
				{
					int iDselectedStudent = (int) studentsTable.getValueAt(studentsTable.getSelectedRow(), 0);
					String dega=comboBoxDega.getSelectedItem().toString();	
					int viti = Integer.parseInt(comboBoxViti.getSelectedItem().toString());
					String paraleli=comboBoxParaleli.getSelectedItem().toString();
					
					ArrayList<StudentDataPojo> studentsList=adminBean.filterListOfStudents(dega,viti,paraleli);
					
					for(int i=0;i<studentsList.size();i++)
					{
						if(studentsList.get(i).getStudent().getIdStudent()==iDselectedStudent)
						{
							selectedStudent = studentsList.get(i);
							break;
						}
					}
					addStudent = new AddEditStudent(selectedStudent);
					addStudent.setTitle("Modifiko student");
					addStudent.setVisible(true);				
				}
			}
		});
		
		JButton btnFshi = new JButton("Fshi");
		btnFshi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(studentsTable.getSelectedRow() != -1) // kontollon nese ka rresht te selektuar nga tabela
				{
					int iDselectedStudent = (int) studentsTable.getValueAt(studentsTable.getSelectedRow(), 0);
					String dega=comboBoxDega.getSelectedItem().toString();	
					int viti = Integer.parseInt(comboBoxViti.getSelectedItem().toString());
					String paraleli=comboBoxParaleli.getSelectedItem().toString();
					
					ArrayList<StudentDataPojo> studentsList=adminBean.filterListOfStudents(dega,viti,paraleli);
					
					for(int i=0;i<studentsList.size();i++)
					{
						if(studentsList.get(i).getStudent().getIdStudent()==iDselectedStudent)
						{
							selectedStudent = studentsList.get(i);
							break;
						}
					}
					deleteStudent = new DeleteStudent(selectedStudent);					
					deleteStudent.setTitle("Fshi student");
					deleteStudent.setVisible(true);
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(40)
					.addComponent(btnShto, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
					.addGap(226)
					.addComponent(btnModifiko, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(226)
					.addComponent(btnFshi, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
					.addGap(42))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnShto, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
						.addComponent(btnFshi, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
						.addComponent(btnModifiko, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addGap(12))
		);
		panel.setLayout(gl_panel);
		
		studentsTable = new JTable();
		studentsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {"Id", "Emri", "Mbiemri", "Atesia", "Dat\u00EBlindja", "Dega", "Viti", "Paraleli"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, Date.class, String.class, Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		studentsTable.getColumnModel().getColumn(0).setResizable(false);
		studentsTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		studentsTable.getColumnModel().getColumn(0).setMaxWidth(40);
		studentsTable.getColumnModel().getColumn(1).setMaxWidth(90);
		studentsTable.getColumnModel().getColumn(2).setMaxWidth(90);
		studentsTable.getColumnModel().getColumn(3).setMaxWidth(90);
		studentsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
		studentsTable.getColumnModel().getColumn(4).setMaxWidth(75);
		studentsTable.getColumnModel().getColumn(6).setPreferredWidth(35);
		studentsTable.getColumnModel().getColumn(6).setMaxWidth(35);
		studentsTable.getColumnModel().getColumn(7).setPreferredWidth(45);
		studentsTable.getColumnModel().getColumn(7).setMaxWidth(45);
		scrollPane.setViewportView(studentsTable);
		studentPanel.setLayout(gl_studentPanel);
		
		JPanel profPanel = new JPanel();
		tabbedPane.addTab("Pedagog", null, profPanel, null);
		
		JPanel panel_1 = new JPanel();
		
		btnShtoPedagog = new JButton("Shto");
		btnShtoPedagog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddProf shtoPedagog = new AddProf(null);
				shtoPedagog.setVisible(true);
			}
		});
		btnShtoPedagog.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnDetaje = new JButton("Detaje mbi pedagogun");
		btnDetaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(profTable.getSelectedRow() != -1) // kontollon nese ka rresht te selektuar nga tabela
				{
					int idSelectedProfessor = (int) profTable.getValueAt(profTable.getSelectedRow(), 0);
					ProfessorDataPojo selectedProfessor = adminBean.getSelectedProfessor(idSelectedProfessor);
					
					EditProf detajePedagogu = new EditProf(selectedProfessor.getProfessor());
					detajePedagogu.setVisible(true);		
				}
			}
		});
		btnDetaje.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnFshiPedagog = new JButton("Fshi");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(40)
					.addComponent(btnShtoPedagog, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
					.addGap(184)
					.addComponent(btnDetaje, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
					.addGap(169)
					.addComponent(btnFshiPedagog, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
					.addGap(42))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnShtoPedagog, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
						.addComponent(btnFshiPedagog, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
						.addComponent(btnDetaje, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addGap(12))
		);
		panel_1.setLayout(gl_panel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblNewLabel_1 = new JLabel("K\u00EBrko pedagog:");
		
		textFieldkerkoPedagog = new JTextField();
		textFieldkerkoPedagog.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String search;
				search = textFieldkerkoPedagog.getText();				
				if(search.compareToIgnoreCase("") == 0)
				{
					DefaultTableModel professorTableModel = (DefaultTableModel) profTable.getModel();
					professorTableModel.setRowCount(0);
				}
				else
				{
					populateProfTableBySearch(search);
				}
			}
		});
		textFieldkerkoPedagog.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		
		JPanel panel_4 = new JPanel();
		
		JPanel panel_5 = new JPanel();
		
		JPanel panel_6 = new JPanel();
		
		
		
		GroupLayout gl_profPanel = new GroupLayout(profPanel);
		gl_profPanel.setHorizontalGroup(
			gl_profPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profPanel.createSequentialGroup()
					.addGroup(gl_profPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
							.addGap(9)
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
							.addGap(54)
							.addComponent(textFieldkerkoPedagog, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
						.addGroup(gl_profPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_profPanel.createSequentialGroup()
					.addGap(42)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
					.addGap(23))
		);
		gl_profPanel.setVerticalGroup(
			gl_profPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profPanel.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_profPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_profPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(textFieldkerkoPedagog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_profPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(12))
		);
		
		profTable = new JTable();
		profTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Emri", "Mbiemri"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		profTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		profTable.getColumnModel().getColumn(0).setMinWidth(30);
		profTable.getColumnModel().getColumn(0).setMaxWidth(30);
		profTable.getColumnModel().getColumn(1).setPreferredWidth(85);
		profTable.getColumnModel().getColumn(1).setMinWidth(85);
		profTable.getColumnModel().getColumn(2).setPreferredWidth(85);
		profTable.getColumnModel().getColumn(2).setMinWidth(85);
		scrollPane_1.setViewportView(profTable);
		profPanel.setLayout(gl_profPanel);
		
		JPanel subjectPanel = new JPanel();
		tabbedPane.addTab("L\u00EBnd\u00EB", null, subjectPanel, null);
		
		JPanel panel_2 = new JPanel();
		
		

		buttonShtoLende = new JButton("Shto");
		buttonShtoLende.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				shto lende 
				addEditSubject = new AddEditSubject(null);
				addEditSubject.setVisible(true);				
			}
		});
		buttonShtoLende.setHorizontalAlignment(SwingConstants.CENTER);
		
		buttonModifikoLende = new JButton("Modifiko");
		buttonModifikoLende.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				modifiko lende 
				if(subjectsTable.getSelectedRow() != -1) // kontollon nese ka rresht te selektuar nga tabela lende 
				{
					int iDselectedSubject = (int) subjectsTable.getValueAt(subjectsTable.getSelectedRow(), 0);
					SubjectDataPojo selectedSubject = adminBean.getSelectedSubject(iDselectedSubject);
					addEditSubject = new AddEditSubject(selectedSubject);
					addEditSubject.setTitle("Modifiko lëndë");
					addEditSubject.setVisible(true);			
				}
			}
		});
		buttonModifikoLende.setHorizontalAlignment(SwingConstants.CENTER);
		
		buttonFshiLende = new JButton("Fshi");
		buttonFshiLende.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {if(subjectsTable.getSelectedRow() != -1) // kontollon nese ka rresht te selektuar nga tabela
			{
				int iDselectedSubject = (int) subjectsTable.getValueAt(subjectsTable.getSelectedRow(), 0);
				SubjectDataPojo subjectToDelete = adminBean.getSelectedSubject(iDselectedSubject);
				
				DeleteSubject deleteSubject = new DeleteSubject(subjectToDelete);					
				deleteSubject.setTitle("Fshi lëndë");
				deleteSubject.setVisible(true);
			}
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 758, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(40)
					.addComponent(buttonShtoLende, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
					.addGap(226)
					.addComponent(buttonModifikoLende, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(226)
					.addComponent(buttonFshiLende, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
					.addGap(42))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 35, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonShtoLende, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
						.addComponent(buttonFshiLende, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
						.addComponent(buttonModifikoLende, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addGap(12))
		);
		panel_2.setLayout(gl_panel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JLabel lblFiltroLnd = new JLabel("Filtro l\u00EBnd\u00EB:");
		
		JLabel labelDegaTabLendet = new JLabel("Dega:");		
		comboBoxDegaTabLendet = new JComboBox();
		comboBoxDegaTabLendet.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{
					comboBoxVitiTabLendet.setSelectedItem(null);
					comboBoxVitiTabLendet.setEnabled(true);
				}
				else
				{
					comboBoxVitiTabLendet.setSelectedItem(null);
					comboBoxVitiTabLendet.setEnabled(false);			
				}
			}
		});
		adminBean.populateComboboxBranches(comboBoxDegaTabLendet);
		
		
		
		
		
		JLabel labelViti = new JLabel("Viti:");		
		comboBoxVitiTabLendet = new JComboBox();
		comboBoxVitiTabLendet.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{	
					String dega=comboBoxDegaTabLendet.getSelectedItem().toString();	
					int viti = Integer.parseInt(comboBoxVitiTabLendet.getSelectedItem().toString());
					populateSubjectsTable(dega,viti);
				}
				else
				{
					
				}
			}
		});
		comboBoxVitiTabLendet.setEnabled(false);
		adminBean.populateComboboxYears(comboBoxVitiTabLendet);
		
		
		JLabel lblKerkoLnd = new JLabel("Kerko l\u00EBnd\u00EB:");
		
		textFieldKerkoLende = new JTextField();
		textFieldKerkoLende.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String search;
				search = textFieldKerkoLende.getText();
				if(search.compareToIgnoreCase("") == 0)
				{
					DefaultTableModel subjestsTableModel = (DefaultTableModel) subjectsTable.getModel();
					subjestsTableModel.setRowCount(0);
				}
				else
				{
					populateSubjectsTableBySearch(search);
				}				
			}
		});
		
		
		textFieldKerkoLende.setColumns(10);
		GroupLayout gl_subjectPanel = new GroupLayout(subjectPanel);
		gl_subjectPanel.setHorizontalGroup(
			gl_subjectPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_subjectPanel.createSequentialGroup()
					.addGroup(gl_subjectPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_subjectPanel.createSequentialGroup()
							.addGap(92)
							.addGroup(gl_subjectPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFiltroLnd, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblKerkoLnd))
							.addGap(20)
							.addComponent(labelDegaTabLendet, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_subjectPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldKerkoLende, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
								.addComponent(comboBoxDegaTabLendet, 0, 353, Short.MAX_VALUE))
							.addGap(18)
							.addComponent(labelViti, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxVitiTabLendet, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addGap(50))
						.addGroup(gl_subjectPanel.createSequentialGroup()
							.addGap(54)
							.addGroup(gl_subjectPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))))
					.addGap(45))
		);
		gl_subjectPanel.setVerticalGroup(
			gl_subjectPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_subjectPanel.createSequentialGroup()
					.addGroup(gl_subjectPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_subjectPanel.createSequentialGroup()
							.addGap(14)
							.addGroup(gl_subjectPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_subjectPanel.createSequentialGroup()
									.addGap(3)
									.addComponent(lblFiltroLnd))
								.addGroup(gl_subjectPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(comboBoxDegaTabLendet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(labelDegaTabLendet)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_subjectPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblKerkoLnd)
								.addComponent(textFieldKerkoLende, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_subjectPanel.createSequentialGroup()
							.addGap(14)
							.addGroup(gl_subjectPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBoxVitiTabLendet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelViti))))
					.addGap(11)
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		subjectsTable = new JTable();
		subjectsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Lenda", "Dega", "Viti"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		subjectsTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		subjectsTable.getColumnModel().getColumn(0).setMaxWidth(35);
		subjectsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		subjectsTable.getColumnModel().getColumn(1).setMinWidth(120);
		subjectsTable.getColumnModel().getColumn(2).setPreferredWidth(350);
		subjectsTable.getColumnModel().getColumn(2).setMinWidth(350);
		subjectsTable.getColumnModel().getColumn(3).setPreferredWidth(45);
		subjectsTable.getColumnModel().getColumn(3).setMinWidth(45);
		subjectsTable.getColumnModel().getColumn(3).setMaxWidth(45);
		scrollPane_2.setViewportView(subjectsTable);
		subjectPanel.setLayout(gl_subjectPanel);
		
		JPanel timetablePanel = new JPanel();
		tabbedPane.addTab("Orari", null, timetablePanel, null);
		
		JLabel lblFiltroOrar = new JLabel("Filtro orar:");
		
		
		JLabel label_1 = new JLabel("Dega:");		
		comboBoxDegaOrar = new JComboBox();
		comboBoxDegaOrar.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{
					comboBoxVitiOrar.setSelectedItem(null);
					comboBoxVitiOrar.setEnabled(true);
					comboBoxParaleliOrar.setSelectedItem(null);
					comboBoxParaleliOrar.setEnabled(false);
				}
				else
				{
					comboBoxVitiOrar.setSelectedItem(null);
					comboBoxVitiOrar.setEnabled(false);
					comboBoxParaleliOrar.setSelectedItem(null);
					comboBoxParaleliOrar.setEnabled(false);					
				}
			}
		});
		adminBean.populateComboboxBranches(comboBoxDegaOrar);
		
		
		JLabel label_2 = new JLabel("Viti:");		
		comboBoxVitiOrar = new JComboBox();
		comboBoxVitiOrar.setEnabled(false);
		comboBoxVitiOrar.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{	
					comboBoxParaleliOrar.setSelectedItem(null);
					comboBoxParaleliOrar.setEnabled(true);					
				}
				else
				{
					comboBoxParaleliOrar.setSelectedItem(null);
					comboBoxParaleliOrar.setEnabled(false);	
				}
			}
		});
		adminBean.populateComboboxYears(comboBoxVitiOrar);
		
		
		
		JLabel label_3 = new JLabel("Paraleli:");
		
		comboBoxParaleliOrar = new JComboBox();
		comboBoxParaleliOrar.setEnabled(false);
		comboBoxParaleliOrar.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{	
					String dega=comboBoxDegaOrar.getSelectedItem().toString();	
					int viti = Integer.parseInt(comboBoxVitiOrar.getSelectedItem().toString());
					String paraleli=comboBoxParaleliOrar.getSelectedItem().toString();
//					populateStudentsTable(dega,viti,paraleli);
					populateTableOrar(dega,viti,paraleli);
				}
				else
				{
					
				}
			}
		});
		adminBean.populateComboboxParallels(comboBoxParaleliOrar);
		
		
		JPanel panel_7 = new JPanel();
		
		buttonShtoOrar = new JButton("Shto");
		buttonShtoOrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//shto ne orar

				if(comboBoxDegaOrar.getSelectedItem()==null 
					|| comboBoxVitiOrar.getSelectedItem() ==null 
					|| comboBoxParaleliOrar.getSelectedItem() == null
					|| tableOrari.getSelectedRow() == -1
					|| tableOrari.getSelectedColumn() == -1)
				{
					JOptionPane.showMessageDialog(null,"KUJDES! Nuk janë përzgjedhur të gjithë filtrat ose nuk është zgjedhur qelizë në tabelë. ", "Mungese të dhënash",
							JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					//popup shto ne orar
					Schedule shtoOre = new Schedule();
					
					
					shtoOre.setDayWeek(tableOrari.getSelectedColumn());
					shtoOre.setTime(tableOrari.getSelectedRow()+1);
					ArrayList<Branch> listOfBranches = adminBean.getListOfBranches();
					if(listOfBranches.size()>0)
					{
						for (int i=0; i<listOfBranches.size();i++)
						{
							if (listOfBranches.get(i).getName() != null)
							{
								if(listOfBranches.get(i).getName().equalsIgnoreCase((String) comboBoxDegaOrar.getSelectedItem()))
								{
									shtoOre.setBranch(listOfBranches.get(i).getIdBranch());
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
								if(listOfYears.get(i).getYear()==(Integer)comboBoxVitiOrar.getSelectedItem())
								{
									shtoOre.setYear(listOfYears.get(i).getIdYear());
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
								if(listOfParallels.get(i).getName().equalsIgnoreCase((String) comboBoxParaleliOrar.getSelectedItem()))
								{
									shtoOre.setParallel(listOfParallels.get(i).getIdParallel());
									break;
								}
							}				
						}
					}
					
					AddSchedule addSchedule = new AddSchedule(shtoOre);
					addSchedule.setVisible(true);
					
				}
				
			}
		});
		buttonShtoOrar.setHorizontalAlignment(SwingConstants.CENTER);
		
		buttonFshiOrar = new JButton("Fshi");
		buttonFshiOrar.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					
					if(tableOrari.getSelectedRow() != -1
							&& tableOrari.getSelectedColumn() != -1) // kontollon nese ka qelize te selektuar nga tabela
					{
						String data = (String) tableOrari.getValueAt(tableOrari.getSelectedRow(), tableOrari.getSelectedColumn());
						if(data!=null && data!=""){
							String[] parts = data.split("/");
							String lenda = parts[0];
							String llojiLendes = parts[1]; 
							String profesori = parts[2];
							String[] profParts = profesori.split(" ");
							String profName=profParts[0];
							String profSurname = profParts[1];
							String salla = parts[3]; 
							int time = tableOrari.getSelectedRow() +1;
							int dayweek = tableOrari.getSelectedColumn();
							String dega=comboBoxDegaOrar.getSelectedItem().toString();	
							int viti = Integer.parseInt(comboBoxVitiOrar.getSelectedItem().toString());
							String paraleli=comboBoxParaleliOrar.getSelectedItem().toString();
							
							DelteSchedule fshiorar = new DelteSchedule(lenda,llojiLendes,profName,profSurname,salla,time,dayweek,dega,viti,paraleli);
							fshiorar.setTitle("Fshi orar");
							fshiorar.setVisible(true);
						}						
					}
				}
		});
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGap(40)
					.addComponent(buttonShtoOrar, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 499, Short.MAX_VALUE)
					.addComponent(buttonFshiOrar, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addGap(42))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonShtoOrar, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
						.addComponent(buttonFshiOrar, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
					.addGap(12))
		);
		panel_7.setLayout(gl_panel_7);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		GroupLayout gl_timetablePanel = new GroupLayout(timetablePanel);
		gl_timetablePanel.setHorizontalGroup(
			gl_timetablePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_timetablePanel.createSequentialGroup()
					.addGap(36)
					.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
					.addGap(54))
				.addGroup(gl_timetablePanel.createSequentialGroup()
					.addGap(36)
					.addComponent(lblFiltroOrar, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
					.addGap(28)
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxDegaOrar, 0, 333, Short.MAX_VALUE)
					.addGap(48)
					.addComponent(label_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxVitiOrar, 0, 102, Short.MAX_VALUE)
					.addGap(24)
					.addComponent(label_3, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
					.addGap(4)
					.addComponent(comboBoxParaleliOrar, 0, 104, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_timetablePanel.createSequentialGroup()
					.addGap(46)
					.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_timetablePanel.setVerticalGroup(
			gl_timetablePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_timetablePanel.createSequentialGroup()
					.addGroup(gl_timetablePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_timetablePanel.createSequentialGroup()
							.addGap(41)
							.addGroup(gl_timetablePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBoxDegaOrar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_timetablePanel.createSequentialGroup()
									.addGap(3)
									.addComponent(lblFiltroOrar))
								.addGroup(gl_timetablePanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(label_2)
									.addComponent(comboBoxVitiOrar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_timetablePanel.createSequentialGroup()
									.addGap(3)
									.addComponent(label_3))
								.addComponent(comboBoxParaleliOrar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_timetablePanel.createSequentialGroup()
							.addGap(44)
							.addComponent(label_1)))
					.addGap(72)
					.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tableOrari = new JTable();
		tableOrari.setColumnSelectionAllowed(true);
		tableOrari.setRowSelectionAllowed(true);
		tableOrari.setModel(new DefaultTableModel(
			new Object[][] {
				{"08:00-10:00", null, null, null, null, null},
				{"09:00-11:00", null, null, null, null, null},
				{"10:00-12:00", null, null, null, null, null},
				{"11:00-13:00", null, null, null, null, null},
				{"12:00-14:00", null, null, null, null, null},
				{"13:00-15:00", null, null, null, null, null},
				{"14:00-16:00", null, null, null, null, null},
				{"15:00-17:00", null, null, null, null, null},
				{"16:00-18:00", null, null, null, null, null},
				{"17:00-19:00", null, null, null, null, null},
				{"18:00-20:00", null, null, null, null, null},
			},
			new String[] {
				"Ora", "E h\u00EBn\u00EB", "E mart\u00EB", "E m\u00EBrkur\u00EB", "E enjte", "E premte"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableOrari.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableOrari.getColumnModel().getColumn(0).setMinWidth(70);
		tableOrari.getColumnModel().getColumn(0).setMaxWidth(70);
		
		tableOrari.getColumnModel().getSelectionModel().setSelectionMode(tableOrari.getColumnModel().getSelectionModel().SINGLE_SELECTION);
		tableOrari.getSelectionModel().setSelectionMode(tableOrari.getSelectionModel().SINGLE_SELECTION);
		
		
		scrollPane_3.setViewportView(tableOrari);
		timetablePanel.setLayout(gl_timetablePanel);
		
		
		getContentPane().setLayout(groupLayout);
		frmAdmin = new JFrame();
		frmAdmin.setTitle("Admin");
		frmAdmin.setBounds(100, 100, 450, 300);
		frmAdmin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	// popullimi i tabeles se studenteve
	public void populateStudentsTable(String branch , int year , String parallel)
	{
		ArrayList<StudentDataPojo> filteredListOfStudents = adminBean.filterListOfStudents(branch, year, parallel);
		DefaultTableModel studentsTableModel = (DefaultTableModel) studentsTable.getModel();
		studentsTableModel.setRowCount(0);
		Object row[];
		if(filteredListOfStudents.size()>0)
		{
			for(int i =0;i<filteredListOfStudents.size();i++)
			{
				row = new Object[8];
				row[0]=filteredListOfStudents.get(i).getStudent().getIdStudent();
				row[1]=filteredListOfStudents.get(i).getStudent().getName();
				row[2]=filteredListOfStudents.get(i).getStudent().getSurname();
				row[3]=filteredListOfStudents.get(i).getStudent().getFathersName();
				row[4]=filteredListOfStudents.get(i).getStudent().getBirthdate();
				row[5]=filteredListOfStudents.get(i).getDega().getName();
				row[6]=filteredListOfStudents.get(i).getViti().getYear();
				row[7]=filteredListOfStudents.get(i).getParaleli().getName();
				
				studentsTableModel.addRow(row);
			}
		}		
	}
	
	
	public static void populateTableOrar(String branch , int year , String parallel)
	{
		
//		tableOrari.setRowCount(0);
		
		tableOrari.setModel(new DefaultTableModel(
				new Object[][] {
					{"08:00-10:00", null, null, null, null, null},
					{"09:00-11:00", null, null, null, null, null},
					{"10:00-12:00", null, null, null, null, null},
					{"11:00-13:00", null, null, null, null, null},
					{"12:00-14:00", null, null, null, null, null},
					{"13:00-15:00", null, null, null, null, null},
					{"14:00-16:00", null, null, null, null, null},
					{"15:00-17:00", null, null, null, null, null},
					{"16:00-18:00", null, null, null, null, null},
					{"17:00-19:00", null, null, null, null, null},
					{"18:00-20:00", null, null, null, null, null},
				},
				new String[] {
					"Ora", "E h\u00EBn\u00EB", "E mart\u00EB", "E m\u00EBrkur\u00EB", "E enjte", "E premte"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		tableOrari.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableOrari.getColumnModel().getColumn(0).setMinWidth(70);
		tableOrari.getColumnModel().getColumn(0).setMaxWidth(70);
		
		
		
		tableOrari.getColumnModel().getSelectionModel().setSelectionMode(tableOrari.getColumnModel().getSelectionModel().SINGLE_SELECTION);
		tableOrari.getSelectionModel().setSelectionMode(tableOrari.getSelectionModel().SINGLE_SELECTION);
		
		
		
		ArrayList<ScheduleDataPojo> filteredListOfSchedule = adminBean.filterListOfSchedule(branch, year, parallel);
		DefaultTableModel tableOrariModel = (DefaultTableModel) tableOrari.getModel();
		
		for(int i = 0; i<filteredListOfSchedule.size();i++)
		{	

			tableOrariModel.setValueAt(filteredListOfSchedule.get(i).getSubject().getName()+"/"+
					filteredListOfSchedule.get(i).getSubjectType().getSubjectTypeName()+"/"+
					filteredListOfSchedule.get(i).getProfessor().getName()+" "+filteredListOfSchedule.get(i).getProfessor().getSurname()+"/"+
					filteredListOfSchedule.get(i).getClassroom().getName(), 
					filteredListOfSchedule.get(i).getSchedule().getTime()-1, 
					filteredListOfSchedule.get(i).getSchedule().getDayWeek());
			tableOrari.setModel(tableOrariModel);
		}
	}
		
//		Object row[];
//		if(filteredListOfSchedule.size()>0)
//		{
//			for(int i =0;i<filteredListOfSchedule.size();i++)
//			{
//				studentsTableModel.add
//				row = new Object[8];
//				row[0]=filteredListOfStudents.get(i).getStudent().getIdStudent();
//				row[1]=filteredListOfStudents.get(i).getStudent().getName();
//				row[2]=filteredListOfStudents.get(i).getStudent().getSurname();
//				row[3]=filteredListOfStudents.get(i).getStudent().getFathersName();
//				row[4]=filteredListOfStudents.get(i).getStudent().getBirthdate();
//				row[5]=filteredListOfStudents.get(i).getDega().getName();
//				row[6]=filteredListOfStudents.get(i).getViti().getYear();
//				row[7]=filteredListOfStudents.get(i).getParaleli().getName();
//				
//				studentsTableModel.addRow(row);
//				
//				
//				
//			}
//		}	
		
//		DefaultTableModel model = (DefaultTableModel) tabelaNjesive.getModel();
//		
//		
//		if(model.getRowCount() > 0)
//		{
//			for(int i = model.getRowCount()-1 ; i >= 0 ; i--)
//			{
//				model.removeRow(i);
//			}
//		}
//		
//		
//		for(int i = 0; i<ListaNjesive.size();i++)
//		{				
//			model.addRow(new Object[0]);
//			model.setValueAt(ListaNjesive.get(i).getKodiNjesise(), i, 0);
//			model.setValueAt(ListaNjesive.get(i).getPershkrimi(), i, 1);
//			model.setValueAt(ListaNjesive.get(i).getQyteti(), i, 2);
//			model.setValueAt(ListaNjesive.get(i).getAdresa(), i, 3);
//			model.setValueAt(ListaNjesive.get(i).getKontakti(), i, 4);
//			model.setValueAt(ListaNjesive.get(i).getAktiv(), i, 5);				
//		}
//		tabelaNjesive.setModel(model);
		
			
	
	
	public void populateSubjectsTable(String branch , int year)
	{
		ArrayList<SubjectDataPojo> filteredListOfStudents = adminBean.filterListOfSubjects(branch, year);
		DefaultTableModel subjestsTableModel = (DefaultTableModel) subjectsTable.getModel();
		subjestsTableModel.setRowCount(0);
		Object row[];
		if(filteredListOfStudents.size()>0)
		{
			for(int i =0;i<filteredListOfStudents.size();i++)
			{
				row = new Object[8];
				row[0]=filteredListOfStudents.get(i).getLenda().getIdSubject();
				row[1]=filteredListOfStudents.get(i).getLenda().getName();
				row[2]=filteredListOfStudents.get(i).getDega().getName();
				row[3]=filteredListOfStudents.get(i).getViti().getYear();				
				
				subjestsTableModel.addRow(row);
			}
		}		
	}
	
	public void populateSubjectsTableBySearch(String search)
	{
		ArrayList<SubjectDataPojo> searchSubject = adminBean.searchSubject(search);
		DefaultTableModel subjestsTableModel = (DefaultTableModel) subjectsTable.getModel();
		subjestsTableModel.setRowCount(0);
		Object row[];
		if(searchSubject.size()>0)
		{
			for(int i =0;i<searchSubject.size();i++)
			{
				row = new Object[8];
				row[0]=searchSubject.get(i).getLenda().getIdSubject();
				row[1]=searchSubject.get(i).getLenda().getName();
				row[2]=searchSubject.get(i).getDega().getName();
				row[3]=searchSubject.get(i).getViti().getYear();				
				
				subjestsTableModel.addRow(row);
			}
		}
		
	}
	
	public void populateProfTableBySearch(String search)
	{
		ArrayList<ProfessorDataPojo> searchProf = adminBean.searchProfessor(search);
		DefaultTableModel profTableModel = (DefaultTableModel) profTable.getModel();
		profTableModel.setRowCount(0);
		Object row[];
		if(searchProf.size()>0)
		{
			for(int i =0;i<searchProf.size();i++)
			{
				
				row = new Object[3];
				row[0]=searchProf.get(i).getProfessor().getIdProfessor();
				row[1]=searchProf.get(i).getProfessor().getName();
				row[2]=searchProf.get(i).getProfessor().getSurname();
				profTableModel.addRow(row);
			}
		}
		
	}
}
