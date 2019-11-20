package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Bean.AdminBean;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DelteSchedule extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public AdminBean adminBean = new AdminBean();
	private JButton okButton;
	private JButton cancelButton;
	private JPanel buttonPane;


	/**
	 * Create the dialog.
	 */
	public DelteSchedule(String lenda,String llojiLendes,String profname,String profsurname,
			String salla,int time,int dayweek,String dega,int viti,String paraleli) {
		setBounds(100, 100, 444, 371);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setModal(true);
		{
			buttonPane = new JPanel();
			{
				okButton = new JButton("Konfirmo");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						adminBean.removeFromSchedule(lenda,llojiLendes,profname,profsurname,salla,time,dayweek,dega,viti,paraleli);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Mbyll");
				cancelButton.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(45)
						.addComponent(okButton, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
						.addGap(196)
						.addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
						.addGap(43))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
		
		JLabel lblJeniDukesFshire = new JLabel("Jeni dukes fshire nga orari:");
		
		JLabel lblLenda = new JLabel("Lenda: "+lenda);
		
		JLabel lblPedagogu = new JLabel("Pedagogu: "+profname+" "+profsurname);
		
		JLabel lblSalla = new JLabel("Salla: "+salla);
		
		JLabel lblLlojiLendes = new JLabel("Lloji lendes: "+llojiLendes);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 417, Short.MAX_VALUE)
					.addGap(21))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblJeniDukesFshire, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(214, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLenda)
					.addContainerGap(337, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLlojiLendes)
					.addContainerGap(314, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPedagogu)
						.addComponent(lblSalla))
					.addContainerGap(260, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblJeniDukesFshire, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblLenda)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblLlojiLendes)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblPedagogu)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSalla)
					.addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
