package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
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
import javax.swing.JPanel;

public class MainPage {

	private Boolean position63=false;
	private static JFrame frame;
	private static List<Player> playerList = new ArrayList<Player>() ;
	private int nrOfPlayers=0;
	private int indexOfCurrentPlayer;
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
	private JPanel panel;
	private JLabel firstDicePanel;
	private JLabel secondDicePanel;

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
		frame.setBounds(100, 100, 1399, 703);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JLabel label = new JLabel("Goose Game");
		label.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		buttonStartGame = new JButton("Start Game");
		buttonStartGame.setEnabled(false);
		buttonStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAddPlayer.setEnabled(false);
				textArea1.setEnabled(true);
				buttonRollDices1.setEnabled(true);
				nrOfPlayers=playerList.size();
				indexOfCurrentPlayer=0;
				nextTurn();
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
				rollDices();
			}
		});
		
		buttonRollDices2 = new JButton("Roll Dices");
		buttonRollDices2.setVisible(false);
		buttonRollDices2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDices();
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
				rollDices();
			}
		});
		
		buttonRollDices4 = new JButton("Roll Dices");
		buttonRollDices4.setVisible(false);
		buttonRollDices4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDices();
			}
		});
		
		panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(67)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(buttonStartGame, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(buttonRollDices3, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(textArea3, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(151)
										.addComponent(buttonRollDices1, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
									.addComponent(textArea1, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)))
							.addGap(165)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
							.addGap(236)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(buttonAddPlayer, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(149))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(textArea4, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
							.addComponent(buttonRollDices2, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(buttonRollDices4, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
									.addGap(151))
								.addComponent(textArea2, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE))))
					.addGap(550))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(587)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
					.addGap(1122))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(buttonAddPlayer)
							.addGap(32)
							.addComponent(textArea2, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRollDices2)
							.addGap(57)
							.addComponent(textArea4, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRollDices4))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(buttonStartGame)
							.addGap(32)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textArea1, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(buttonRollDices1)))
							.addGap(57)
							.addComponent(textArea3, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRollDices3)))
					.addContainerGap(136, Short.MAX_VALUE))
		);
		
		firstDicePanel = new JLabel("");
		
		secondDicePanel = new JLabel("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addComponent(firstDicePanel, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(secondDicePanel, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(secondDicePanel, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
				.addComponent(firstDicePanel, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	
	public int rollDices() {
		
		int firstDice=((int) (Math.random()*10000) % 6)+ 1;
		firstDicePanel.setIcon(new ImageIcon(MainPage.class.getResource("/images/"+firstDice+".png")));
		int secondDice=((int) (Math.random()*10000) % 6)+ 1;
		secondDicePanel.setIcon(new ImageIcon(MainPage.class.getResource("/images/"+secondDice+".png")));
		nextTurn();
		return firstDice + secondDice; 
	}
	
	public void nextTurn() {
		if(indexOfCurrentPlayer==0) {
			textArea1.setEnabled(true);
			buttonRollDices1.setEnabled(true);
			textArea2.setEnabled(false);
			buttonRollDices2.setEnabled(false);
			textArea3.setEnabled(false);
			buttonRollDices3.setEnabled(false);
			textArea4.setEnabled(false);
			buttonRollDices4.setEnabled(false);
			indexOfCurrentPlayer++;
		}else if(indexOfCurrentPlayer==1) {
			textArea1.setEnabled(false);
			buttonRollDices1.setEnabled(false);
			textArea2.setEnabled(true);
			buttonRollDices2.setEnabled(true);
			textArea3.setEnabled(false);
			buttonRollDices3.setEnabled(false);
			textArea4.setEnabled(false);
			buttonRollDices4.setEnabled(false);
			indexOfCurrentPlayer++;
		}else if(indexOfCurrentPlayer==2) {
			textArea1.setEnabled(false);
			buttonRollDices1.setEnabled(false);
			textArea2.setEnabled(false);
			buttonRollDices2.setEnabled(false);
			textArea3.setEnabled(true);
			buttonRollDices3.setEnabled(true);
			textArea4.setEnabled(false);
			buttonRollDices4.setEnabled(false);
			indexOfCurrentPlayer++;
		}else if(indexOfCurrentPlayer==3) {
			textArea1.setEnabled(false);
			buttonRollDices1.setEnabled(false);
			textArea2.setEnabled(false);
			buttonRollDices2.setEnabled(false);
			textArea3.setEnabled(false);
			buttonRollDices3.setEnabled(false);
			textArea4.setEnabled(true);
			buttonRollDices4.setEnabled(true);
			indexOfCurrentPlayer++;
		}
		if(indexOfCurrentPlayer==playerList.size()) {
			indexOfCurrentPlayer=0;
		}		
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
