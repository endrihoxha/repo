package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class MainPage {

	private static JFrame frame;
	private static List<Player> playerList = new ArrayList<Player>() ;
	private static JButton buttonStartGame;
	private static JTextArea textArea1;
	private static JButton buttonRollDices1;
	private static JTextArea textArea3;
	private static JButton buttonRollDices3;
	private static JButton buttonRollDices4;
	private static JTextArea textArea4;
	private static JButton buttonRollDices2;
	private static JTextArea textArea2;
	private static JButton buttonAddPlayer;

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
		frame.setBounds(100, 100, 1929, 1056);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JLabel label = new JLabel("Goose Game");
		label.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		buttonStartGame = new JButton("Start Game");
		buttonStartGame.setEnabled(false);
		buttonStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAddPlayer.setEnabled(false);
			}
		});
		
		buttonAddPlayer = new JButton("Add Player");
		buttonAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPlayer addPlayer=new AddPlayer();
				addPlayer.show();
			}
		});
		
		textArea1 = new JTextArea();
		textArea1.setText("Player 1: \nPosition:");
		textArea1.setVisible(false);
		
		textArea2 = new JTextArea();
		textArea2.setText("Player 2:");
		textArea2.setVisible(false);
		
		buttonRollDices1 = new JButton("Roll Dices");
		buttonRollDices1.setVisible(false);
		buttonRollDices1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		buttonRollDices2 = new JButton("Roll Dices");
		buttonRollDices2.setVisible(false);
		buttonRollDices2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		textArea3 = new JTextArea();
		textArea3.setText("Player 3:");
		textArea3.setVisible(false);
		
		textArea4 = new JTextArea();
		textArea4.setText("Player 4:");
		textArea4.setVisible(false);
		
		buttonRollDices3 = new JButton("Roll Dices");
		buttonRollDices3.setVisible(false);
		buttonRollDices3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		buttonRollDices4 = new JButton("Roll Dices");
		buttonRollDices4.setVisible(false);
		buttonRollDices4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(67)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(149)
							.addComponent(buttonStartGame, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 1228, Short.MAX_VALUE)
							.addComponent(buttonAddPlayer, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(149))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(buttonRollDices3, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(textArea3, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(151)
										.addComponent(buttonRollDices1, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
									.addComponent(textArea1, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)))
							.addGap(1228)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textArea4, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonRollDices2, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(buttonRollDices4, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
										.addGap(151))
									.addComponent(textArea2, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)))))
					.addGap(90))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(847)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
					.addGap(862))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(buttonStartGame)
						.addComponent(buttonAddPlayer))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textArea1, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
						.addComponent(textArea2, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(buttonRollDices1)
						.addComponent(buttonRollDices2))
					.addGap(57)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textArea4, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
						.addComponent(textArea3, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(buttonRollDices4)
						.addComponent(buttonRollDices3))
					.addContainerGap(484, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	public static void closeWindow()
	{
		frame.dispose();
	}
	
	public static List<Player> getPlayerList() {
		return playerList;
	}

	public static void setPlayerList(List<Player> playerList) {
		MainPage.playerList = playerList;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		MainPage.frame = frame;
	}

	public static JButton getButtonStartGame() {
		return buttonStartGame;
	}

	public static void setButtonStartGame(JButton buttonStartGame) {
		MainPage.buttonStartGame = buttonStartGame;
	}

	public static JTextArea getTextArea1() {
		return textArea1;
	}

	public static void setTextArea1(JTextArea textArea1) {
		MainPage.textArea1 = textArea1;
	}

	public static JButton getButtonRollDices1() {
		return buttonRollDices1;
	}

	public static void setButtonRollDices1(JButton buttonRollDices1) {
		MainPage.buttonRollDices1 = buttonRollDices1;
	}

	public static JTextArea getTextArea3() {
		return textArea3;
	}

	public static void setTextArea3(JTextArea textArea3) {
		MainPage.textArea3 = textArea3;
	}

	public static JButton getButtonRollDices3() {
		return buttonRollDices3;
	}

	public static void setButtonRollDices3(JButton buttonRollDices3) {
		MainPage.buttonRollDices3 = buttonRollDices3;
	}

	public static JButton getButtonRollDices4() {
		return buttonRollDices4;
	}

	public static void setButtonRollDices4(JButton buttonRollDices4) {
		MainPage.buttonRollDices4 = buttonRollDices4;
	}

	public static JTextArea getTextArea4() {
		return textArea4;
	}

	public static void setTextArea4(JTextArea textArea4) {
		MainPage.textArea4 = textArea4;
	}

	public static JButton getButtonRollDices2() {
		return buttonRollDices2;
	}

	public static void setButtonRollDices2(JButton buttonRollDices2) {
		MainPage.buttonRollDices2 = buttonRollDices2;
	}

	public static JTextArea getTextArea2() {
		return textArea2;
	}

	public static void setTextArea2(JTextArea textArea2) {
		MainPage.textArea2 = textArea2;
	}

	public static JButton getButtonAddPlayer() {
		return buttonAddPlayer;
	}

	public static void setButtonAddPlayer(JButton buttonAddPlayer) {
		MainPage.buttonAddPlayer = buttonAddPlayer;
	}

	

}
