package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class AddPlayer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField playerNameTextField;

	/**
	 * Create the dialog.
	 */
	public AddPlayer() {
		setTitle("Add new player");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPlayerName = new JLabel("Player name:");
			lblPlayerName.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPlayerName.setBackground(new Color(240, 240, 240));
			lblPlayerName.setBounds(21, 91, 98, 26);
			contentPanel.add(lblPlayerName);
		}
		
		playerNameTextField = new JTextField();
		playerNameTextField.setBounds(142, 96, 237, 20);
		contentPanel.add(playerNameTextField);
		playerNameTextField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton addPlayerButton = new JButton("Add Player");
				addPlayerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Player newPlayer = new Player();
						newPlayer.setName(playerNameTextField.getText());
						newPlayer.setPosition(0);
						MainPage.getPlayerList().add(newPlayer);
						if(MainPage.getPlayerList().size()>0) {
							MainPage.getButtonStartGame().setEnabled(true);
							if(MainPage.getPlayerList().size()==1) {
								MainPage.getTextArea1().setText("Player 1:"+ MainPage.getPlayerList().get(0).getName()+" \nPosition:Start");
								MainPage.getTextArea1().setVisible(true);
								MainPage.getTextArea1().setEnabled(false);
								MainPage.getButtonRollDices1().setVisible(true);
								MainPage.getButtonRollDices1().setEnabled(false);
							}							
							if(MainPage.getPlayerList().size()==2) {
								MainPage.getTextArea2().setText("Player 2:"+ MainPage.getPlayerList().get(1).getName()+" \nPosition:Start");
								MainPage.getTextArea2().setVisible(true);
								MainPage.getTextArea2().setEnabled(false);
								MainPage.getButtonRollDices2().setVisible(true);
								MainPage.getButtonRollDices2().setEnabled(false);
							}
							if(MainPage.getPlayerList().size()==3) {
								MainPage.getTextArea3().setText("Player 3:"+ MainPage.getPlayerList().get(2).getName()+" \nPosition:Start");
								MainPage.getTextArea3().setVisible(true);
								MainPage.getTextArea3().setEnabled(false);
								MainPage.getButtonRollDices3().setVisible(true);
								MainPage.getButtonRollDices3().setEnabled(false);
							}
							if(MainPage.getPlayerList().size()==4) {
								MainPage.getTextArea4().setText("Player 4:"+ MainPage.getPlayerList().get(3).getName()+" \nPosition:Start");
								MainPage.getTextArea4().setVisible(true);
								MainPage.getTextArea4().setEnabled(false);
								MainPage.getButtonRollDices4().setVisible(true);
								MainPage.getButtonRollDices4().setEnabled(false);
							}
						}
						dispose();
					}
				});
				addPlayerButton.setActionCommand("Add Player");
				buttonPane.add(addPlayerButton);
				getRootPane().setDefaultButton(addPlayerButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
