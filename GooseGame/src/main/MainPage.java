package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainPage {

	private JFrame frame;
	private static List<Player> playerList = new ArrayList<Player>() ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1096, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Goose Game");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(426, 11, 194, 89);
		frame.getContentPane().add(lblNewLabel);
		
		JTextArea txtrPlayer1 = new JTextArea();
		txtrPlayer1.setText("Player 1:");
		txtrPlayer1.setBounds(10, 158, 262, 126);
		frame.getContentPane().add(txtrPlayer1);
		
		JTextArea txtrPlayer2 = new JTextArea();
		txtrPlayer2.setText("Player 2:");
		txtrPlayer2.setBounds(808, 158, 262, 126);
		frame.getContentPane().add(txtrPlayer2);
		
		JTextArea txtrPlayer3 = new JTextArea();
		txtrPlayer3.setText("Player 3:");
		txtrPlayer3.setBounds(10, 420, 262, 126);
		frame.getContentPane().add(txtrPlayer3);
		
		JTextArea txtrPlayer4 = new JTextArea();
		txtrPlayer4.setText("Player 4:");
		txtrPlayer4.setBounds(808, 420, 262, 126);
		frame.getContentPane().add(txtrPlayer4);
		
		JButton btnNewButton = new JButton("Start Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(183, 103, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnAddPlayer = new JButton("Add Player");
		btnAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPlayer addPlayer=new AddPlayer();
				addPlayer.show();
			}
		});
		btnAddPlayer.setBounds(808, 103, 89, 23);
		frame.getContentPane().add(btnAddPlayer);
		
		JButton button = new JButton("Start Game");
		button.setBounds(183, 261, 89, 23);
		frame.getContentPane().add(button);
		
		JButton btnRollDices1 = new JButton("Roll Dices");
		btnRollDices1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnRollDices1.setBounds(183, 285, 89, 23);
		frame.getContentPane().add(btnRollDices1);
		
		JButton btnRollDices2 = new JButton("Roll Dices");
		btnRollDices2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRollDices2.setBounds(808, 285, 89, 23);
		frame.getContentPane().add(btnRollDices2);
		
		JButton btnRollDices3 = new JButton("Roll Dices");
		btnRollDices3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRollDices3.setBounds(183, 545, 89, 23);
		frame.getContentPane().add(btnRollDices3);
		
		JButton btnRollDices4 = new JButton("Roll Dices");
		btnRollDices4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRollDices4.setBounds(808, 545, 89, 23);
		frame.getContentPane().add(btnRollDices4);
	}

	public static List<Player> getPlayerList() {
		return playerList;
	}

	public static void setPlayerList(List<Player> playerList) {
		MainPage.playerList = playerList;
	}
	
	
}
