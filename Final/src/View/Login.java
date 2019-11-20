package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import DBConnection.*;
import Entities.ClassRoom;
import Entities.Professor;

public class Login extends JDialog {
	private JPanel buttonPane;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	public ConnectToDB getConnection = new ConnectToDB(); 

	/**
	 * Create the dialog.
	 */
	public Login() {
		getContentPane().setFont(new Font("Wide Latin", Font.BOLD, 16));
		setBounds(100, 100, 567, 352);
		 setModal(true);
		{
			buttonPane = new JPanel();
			JButton btnLogohuPopUp = new JButton("Logohu");
			btnLogohuPopUp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					tryLogin(textFieldUsername.getText(), String.valueOf(passwordField.getPassword()));
					
				}
			});
			
			
			
			JButton btnMbyll = new JButton("Mbyll");
			btnMbyll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(83)
						.addComponent(btnLogohuPopUp)
						.addGap(263)
						.addComponent(btnMbyll)
						.addContainerGap(81, Short.MAX_VALUE))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnMbyll, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLogohuPopUp, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(13, Short.MAX_VALUE))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
		
		JLabel loginPanel = new JLabel("Paneli i logimit");
		loginPanel.setForeground(Color.BLUE);
		loginPanel.setFont(new Font("Andalus", Font.BOLD, 18));
		loginPanel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel username = new JLabel("Emri i p\u00EBrdoruesit");
		username.setForeground(Color.BLUE);
		username.setFont(new Font("Andalus", Font.PLAIN, 14));
		
		JLabel password = new JLabel("Fjal\u00EBkalimi");
		password.setFont(new Font("Andalus", Font.PLAIN, 14));
		password.setForeground(Color.BLUE);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(buttonPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(103)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(username)
								.addComponent(password))
							.addGap(54)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(passwordField)
								.addComponent(textFieldUsername, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))))
					.addGap(44))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(username)
						.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(password)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void tryLogin (String username, String password)
	{
		try
		{
			Connection con = getConnection.connect();
			PreparedStatement stm = con.prepareStatement("select * from admin where username = ? and password = ?");
			PreparedStatement stm2 = con.prepareStatement("select * from professor where username = ? and password = ?");
			PreparedStatement stm3 = con.prepareStatement("select * from classroom where name = ? and password = ?");			
			stm.setString(1, username);
			stm.setString(2, password);
			stm2.setString(1, username);
			stm2.setString(2, password);
			stm3.setString(1, username);
			stm3.setString(2, password);
			ResultSet rs = stm.executeQuery();
			ResultSet rs2 = stm2.executeQuery();
			ResultSet rs3 = stm3.executeQuery();
			if (rs.first())
			{
				Admin admin = new Admin();
				dispose();
				admin.setVisible(true);
				admin.setExtendedState(JFrame.MAXIMIZED_BOTH);
				MainPage.closeWindow();
			}
			else if(rs2.first())
			{
				Professor loggedinProf = new Professor();
				loggedinProf.setIdProfessor(rs2.getInt("idprofessor"));
				loggedinProf.setName(rs2.getString("name"));
				loggedinProf.setSurname(rs2.getString("surname"));
				Prof professor = new Prof(loggedinProf);
				dispose();
				professor.setVisible(true);
				professor.setExtendedState(JFrame.MAXIMIZED_BOTH);
				MainPage.closeWindow();
			}
			else if(rs3.first())
			{
				ClassRoom salla = new ClassRoom();
				salla.setIdClassroom(rs3.getInt("idclassroom"));
				salla.setName(rs3.getString("name"));
				salla.setClassroomType(rs3.getInt("type"));
				ClassRoomView classRoom = new ClassRoomView(salla);
				dispose();
				classRoom.setVisible(true);
				classRoom.setExtendedState(JFrame.MAXIMIZED_BOTH);
				MainPage.closeWindow();
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Nuk ekziston perdorues me keto te dhena! ", "Kredenciale te pasakta",
						JOptionPane.ERROR_MESSAGE);
			}			
		}
		catch (SQLException ex) 
		{
			System.err.println("Problem lidhje me bazen e te dhenave");
		}
		finally 
		{
			getConnection.disconnect();
		}
	}
}
