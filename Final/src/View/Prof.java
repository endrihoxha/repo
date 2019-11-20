package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Entities.PojoPresences;
import Entities.Professor;
import Entities.StudentDataPojo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Bean.ProfBean;

import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.event.ItemEvent;

public class Prof extends JFrame {

	private JPanel contentPane;
	private JLabel lblProffesor;
	private JTextField searchStudent;
	private JComboBox comboBoxDega;
	private JLabel lblViti;
	private JLabel lblParaleli;
	private JLabel lblLenda;
	private JComboBox comboBoxViti;
	private JComboBox comboBoxParaleli;
	private JComboBox comboBoxLenda;
	private JLabel lblLlojiLnds;
	private JComboBox comboBoxLlojiLendes;
	private JTable table;
	private ArrayList<PojoPresences> listPresence;
	
	public static ProfBean profBean = new ProfBean();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Prof frame = new Prof();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Prof(Professor loggedinProf) {
		setTitle("Profesor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 839, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblProffesor = new JLabel("Profesori: ");
		
		JLabel lblKerkoStudent = new JLabel("Kerko student:");
		lblKerkoStudent.setVisible(false);
		
		searchStudent = new JTextField();
		searchStudent.setVisible(false);
		searchStudent.setEnabled(false);
		searchStudent.setColumns(10);
		
		JLabel lblDega = new JLabel("Dega:");		
		comboBoxDega = new JComboBox();
		profBean.populateComboboxBranches(comboBoxDega,loggedinProf);
		comboBoxDega.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) 
				{
					comboBoxViti.setSelectedItem(null);
					comboBoxViti.setEnabled(true);
					comboBoxParaleli.setSelectedItem(null);
					comboBoxParaleli.setEnabled(false);
					comboBoxLenda.setSelectedItem(null);
					comboBoxLenda.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
					comboBoxViti.removeAllItems();
					profBean.populateComboboxYears(comboBoxViti,comboBoxDega.getSelectedItem().toString(),loggedinProf);
				}
				else
				{
					comboBoxViti.setSelectedItem(null);
					comboBoxViti.setEnabled(false);
					comboBoxParaleli.setSelectedItem(null);
					comboBoxParaleli.setEnabled(false);
					comboBoxLenda.setSelectedItem(null);
					comboBoxLenda.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
				}
			}
		});
		
		
		lblViti = new JLabel("Viti:");		
		comboBoxViti = new JComboBox();
		comboBoxViti.setEnabled(false);
		comboBoxViti.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev2) {
				if (ev2.getStateChange() == ItemEvent.SELECTED) 
				{
					comboBoxParaleli.setSelectedItem(null);
					comboBoxParaleli.setEnabled(true);
					comboBoxLenda.setSelectedItem(null);
					comboBoxLenda.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
					comboBoxParaleli.removeAllItems();
					profBean.populateComboboxParallels(comboBoxParaleli,comboBoxDega.getSelectedItem().toString(),loggedinProf, 
							Integer.parseInt(comboBoxViti.getSelectedItem().toString()));
				}
				else
				{
					comboBoxParaleli.setSelectedItem(null);
					comboBoxParaleli.setEnabled(false);
					comboBoxLenda.setSelectedItem(null);
					comboBoxLenda.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
				}
			}
		});
		
		lblParaleli = new JLabel("Paraleli:");		
		comboBoxParaleli = new JComboBox();
		comboBoxParaleli.setEnabled(false);
		comboBoxParaleli.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev3) {
				if (ev3.getStateChange() == ItemEvent.SELECTED) 
				{
					comboBoxLenda.setSelectedItem(null);
					comboBoxLenda.setEnabled(true);
					comboBoxLlojiLendes.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
					comboBoxLenda.removeAllItems();
					profBean.populateComboboxSubjects(comboBoxLenda,comboBoxDega.getSelectedItem().toString(),Integer.parseInt(comboBoxViti.getSelectedItem().toString()),
							comboBoxParaleli.getSelectedItem().toString(),loggedinProf);
				}
				else
				{
					comboBoxLenda.setSelectedItem(null);
					comboBoxLenda.setEnabled(false);
					comboBoxLlojiLendes.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
				}
			}
		});
		
		
		lblLenda = new JLabel("L\u00EBnda:");		
		comboBoxLenda = new JComboBox();
		comboBoxLenda.setEnabled(false);
		comboBoxLenda.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev4) {
				if (ev4.getStateChange() == ItemEvent.SELECTED) 
				{
					comboBoxLlojiLendes.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(true);
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
					comboBoxLlojiLendes.removeAllItems();
					profBean.populateComboboxSubjectType(comboBoxLlojiLendes,comboBoxDega.getSelectedItem().toString(),Integer.parseInt(comboBoxViti.getSelectedItem().toString()),
							comboBoxParaleli.getSelectedItem().toString(),comboBoxLenda.getSelectedItem().toString(),loggedinProf);
				}
				else
				{
					comboBoxLlojiLendes.setSelectedItem(null);
					comboBoxLlojiLendes.setEnabled(false);
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
				}
			}
		});
		
		
		JLabel lblFiltroStudent = new JLabel("Filtro student\u00EB:");
		
		lblLlojiLnds = new JLabel("Lloji l\u00EBnd\u00EBs:");
		
		comboBoxLlojiLendes = new JComboBox();
		comboBoxLlojiLendes.setEnabled(false);
		comboBoxLlojiLendes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev5) {
				if (ev5.getStateChange() == ItemEvent.SELECTED) 
				{
					searchStudent.setText(null);
					searchStudent.setEnabled(true);
					listPresence = profBean.getPresences(comboBoxLlojiLendes.getSelectedItem().toString(),
							comboBoxDega.getSelectedItem().toString(),
							Integer.parseInt(comboBoxViti.getSelectedItem().toString()),
							comboBoxParaleli.getSelectedItem().toString(),
							comboBoxLenda.getSelectedItem().toString(),loggedinProf);
					populatePresenceTable(listPresence);
				}
				else
				{
					searchStudent.setText(null);
					searchStudent.setEnabled(false);
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblProffesor, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
					.addGap(251)
					.addComponent(lblKerkoStudent)
					.addGap(18)
					.addComponent(searchStudent, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblFiltroStudent)
					.addGap(35)
					.addComponent(lblDega)
					.addGap(4)
					.addComponent(comboBoxDega, 0, 160, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblViti)
					.addGap(4)
					.addComponent(comboBoxViti, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblParaleli)
					.addGap(4)
					.addComponent(comboBoxParaleli, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblLenda)
					.addGap(4)
					.addComponent(comboBoxLenda, 0, 86, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblLlojiLnds)
					.addGap(4)
					.addComponent(comboBoxLlojiLendes, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(41)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
					.addGap(40))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblProffesor))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblKerkoStudent))
						.addComponent(searchStudent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblFiltroStudent))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDega))
						.addComponent(comboBoxDega, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblViti))
						.addComponent(comboBoxViti, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblParaleli))
						.addComponent(comboBoxParaleli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblLenda))
						.addComponent(comboBoxLenda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblLlojiLnds))
						.addComponent(comboBoxLlojiLendes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
					.addGap(10))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Studenti", "Totali i or\u00EBve", "Paraqitje", "Mungesa"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Integer.class, Integer.class
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
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(0).setMinWidth(200);
		table.getColumnModel().getColumn(1).setMinWidth(75);
		table.getColumnModel().getColumn(2).setMinWidth(75);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setMinWidth(75);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	
	
	
	public void populatePresenceTable(ArrayList<PojoPresences> listPresence)
	{
		DefaultTableModel presenceTableModel = (DefaultTableModel) table.getModel();
		presenceTableModel.setRowCount(0);
		Object row[];
		if(listPresence.size()>0)
		{
			for(int i =0;i<listPresence.size();i++)
			{
				row = new Object[8];
				row[0]=listPresence.get(i).getStudent().getName() +" "+listPresence.get(i).getStudent().getSurname();
				row[1]=listPresence.get(i).getTotal();
				row[2]=listPresence.get(i).getPresence();
				row[3]=listPresence.get(i).getAbsence();
				presenceTableModel.addRow(row);
			}
		}		
	}
}
