package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Bean.AdminBean;
import Entities.SubjectDataPojo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteSubject extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton konfirmoDeleteSubject;
	private JButton mbyllDeleteSubject;
	public AdminBean adminBean = new AdminBean();



	/**
	 * Create the dialog.
	 */
	public DeleteSubject(SubjectDataPojo subjectToDelete) {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblJeniDukeFshir = new JLabel("Jeni Duke fshir\u00EB l\u00EBnd\u00EBn me t\u00EB dh\u00EBnat e m\u00EBposhtme");
		
		JLabel lblLnda = new JLabel("L\u00EBnda: "+ subjectToDelete.getLenda().getName());
		
		JLabel lblDega = new JLabel("Dega: "+ subjectToDelete.getDega().getName());
		
		JLabel lblViti = new JLabel("Viti: "+ subjectToDelete.getViti().getYear());
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblJeniDukeFshir)
						.addComponent(lblLnda)
						.addComponent(lblDega)
						.addComponent(lblViti))
					.addContainerGap(169, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblJeniDukeFshir)
					.addGap(18)
					.addComponent(lblLnda)
					.addGap(18)
					.addComponent(lblDega)
					.addGap(18)
					.addComponent(lblViti)
					.addContainerGap(97, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				konfirmoDeleteSubject = new JButton("Konfirmo");
				konfirmoDeleteSubject.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						adminBean.deleteSubject(subjectToDelete);
						dispose();
					}
				});
				konfirmoDeleteSubject.setActionCommand("OK");
				getRootPane().setDefaultButton(konfirmoDeleteSubject);
			}
			{
				mbyllDeleteSubject = new JButton("Mbyll");
				mbyllDeleteSubject.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(45)
						.addComponent(konfirmoDeleteSubject, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addGap(199)
						.addComponent(mbyllDeleteSubject)
						.addGap(40))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(mbyllDeleteSubject)
							.addComponent(konfirmoDeleteSubject, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}
}
