package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class Home extends JFrame {

	private Boolean position63=false;
	private static List<Player> playerList = new ArrayList<Player>() ;
	private int nrOfPlayers=0;
	private int indexOfCurrentPlayer;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private static JButton buttonStartGame;
	private static JButton buttonAddPlayer;
	private static JButton buttonRollDices1;
	private static JButton buttonRollDices2;
	private static JButton buttonRollDices3;
	private static JButton buttonRollDices4;
	private static JTextPane textArea1;
	private static JTextPane textArea2;
	private static JTextPane textArea3;
	private static JTextPane textArea4;
	private JLabel firstDicePanel;
	private JLabel secondDicePanel;
	private JPanel panel;
	private JPanel describeMove;
	private JTextPane textPaneDescribeMove;
	private JButton btnNewGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 819, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblNewLabel = new JLabel("Goose Game");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		textArea1 = new JTextPane();
		textArea1.setText("Player 1: \nPosition:");
		textArea1.setVisible(false);
		
		
		textArea2 = new JTextPane();
		textArea2.setText("Player 2:");
		textArea2.setVisible(false);
		
		
		textArea3 = new JTextPane();
		textArea3.setText("Player 3:");
		textArea3.setVisible(false);		
		
		
		textArea4 = new JTextPane();
		textArea4.setText("Player 4:");
		textArea4.setVisible(false);
		
		
		buttonStartGame = new JButton("Start Game");
		buttonStartGame.setEnabled(false);
		buttonStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAddPlayer.setEnabled(false);
				textArea1.setEnabled(true);
				buttonRollDices1.setEnabled(true);
				nrOfPlayers=playerList.size();
				indexOfCurrentPlayer=0;
				buttonStartGame.setEnabled(false);
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
		
		firstDicePanel = new JLabel("");
		
		secondDicePanel = new JLabel("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addComponent(firstDicePanel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(secondDicePanel, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(secondDicePanel, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
				.addComponent(firstDicePanel, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
		describeMove = new JPanel();
		
		btnNewGame = new JButton("New Game");
		btnNewGame.setVisible(false);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonStartGame.setEnabled(true);
				textArea1.setVisible(false);
				textArea2.setVisible(false);
				textArea3.setVisible(false);
				textArea4.setVisible(false);
				buttonRollDices1.setVisible(false);
				buttonRollDices2.setVisible(false);
				buttonRollDices3.setVisible(false);
				buttonRollDices4.setVisible(false);
				buttonAddPlayer.setEnabled(true);
				playerList.clear();
				btnNewGame.setVisible(false);
				buttonStartGame.setEnabled(false);
				textPaneDescribeMove.setText("");
				firstDicePanel.setVisible(false);
				secondDicePanel.setVisible(false);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(buttonRollDices1)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnNewGame)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(buttonStartGame))
								.addComponent(textArea1, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(81)
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(88))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(25)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(describeMove, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
										.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGap(18)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(buttonRollDices2)
								.addComponent(buttonAddPlayer)
								.addComponent(textArea2, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(150)
							.addComponent(buttonRollDices3)
							.addPreferredGap(ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
							.addComponent(buttonRollDices4)
							.addGap(150))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textArea3, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
							.addComponent(textArea4, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(27))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(buttonStartGame)
								.addComponent(buttonAddPlayer)
								.addComponent(btnNewGame))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textArea1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRollDices1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textArea2, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRollDices2))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(textArea3, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
								.addComponent(textArea4, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(4)
									.addComponent(buttonRollDices3))
								.addComponent(buttonRollDices4)))
						.addComponent(describeMove, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		textPaneDescribeMove = new JTextPane();
		textPaneDescribeMove.setToolTipText("");
		GroupLayout gl_describeMove = new GroupLayout(describeMove);
		gl_describeMove.setHorizontalGroup(
			gl_describeMove.createParallelGroup(Alignment.LEADING)
				.addComponent(textPaneDescribeMove, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
		);
		gl_describeMove.setVerticalGroup(
			gl_describeMove.createParallelGroup(Alignment.LEADING)
				.addComponent(textPaneDescribeMove, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
		);
		describeMove.setLayout(gl_describeMove);
		contentPane.setLayout(gl_contentPane);
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
			
		}else if(indexOfCurrentPlayer==1) {
			textArea1.setEnabled(false);
			buttonRollDices1.setEnabled(false);
			textArea2.setEnabled(true);
			buttonRollDices2.setEnabled(true);
			textArea3.setEnabled(false);
			buttonRollDices3.setEnabled(false);
			textArea4.setEnabled(false);
			buttonRollDices4.setEnabled(false);
			
		}else if(indexOfCurrentPlayer==2) {
			textArea1.setEnabled(false);
			buttonRollDices1.setEnabled(false);
			textArea2.setEnabled(false);
			buttonRollDices2.setEnabled(false);
			textArea3.setEnabled(true);
			buttonRollDices3.setEnabled(true);
			textArea4.setEnabled(false);
			buttonRollDices4.setEnabled(false);
			
		}else if(indexOfCurrentPlayer==3) {
			textArea1.setEnabled(false);
			buttonRollDices1.setEnabled(false);
			textArea2.setEnabled(false);
			buttonRollDices2.setEnabled(false);
			textArea3.setEnabled(false);
			buttonRollDices3.setEnabled(false);
			textArea4.setEnabled(true);
			buttonRollDices4.setEnabled(true);
			
		}
		if(indexOfCurrentPlayer==playerList.size()) {
			indexOfCurrentPlayer=0;
			nextTurn();
		}		
	}
	
		public void rollDices() {
		firstDicePanel.setVisible(true);
		secondDicePanel.setVisible(true);
		int firstDice=((int) (Math.random()*10000) % 6)+ 1;
		firstDicePanel.setIcon(new ImageIcon(Home.class.getResource("/images/"+firstDice+".png")));
		int secondDice=((int) (Math.random()*10000) % 6)+ 1;
		secondDicePanel.setIcon(new ImageIcon(Home.class.getResource("/images/"+secondDice+".png")));
		makeTheMove(firstDice , secondDice,indexOfCurrentPlayer);
		
	}
		
		public void makeTheMove(int firstDice,int secondDice,int playerIndex) {
			Player thePlayer=playerList.get(playerIndex);
			int currentPosition=thePlayer.getPosition();
			int newPosition = newPosition(currentPosition, firstDice, secondDice, thePlayer);
			if(playerIndex==0) {
				thePlayer.setPosition(newPosition);
				playerList.set(playerIndex, thePlayer);
				textArea1.setText("Player 1:"+thePlayer.getName() +" : \nPosition: "+thePlayer.getPosition());
			}
			else if(playerIndex==1) {
				thePlayer.setPosition(newPosition);
				playerList.set(playerIndex, thePlayer);
				textArea2.setText("Player 2:"+thePlayer.getName() +" : \nPosition: "+thePlayer.getPosition());
			}
			else if(playerIndex==2) {
				thePlayer.setPosition(newPosition);
				playerList.set(playerIndex, thePlayer);
				textArea3.setText("Player 3:"+thePlayer.getName() +" : \nPosition: "+thePlayer.getPosition());
			}
			else if(playerIndex==3) {
				thePlayer.setPosition(newPosition);
				playerList.set(playerIndex, thePlayer);
				textArea4.setText("Player 4:"+thePlayer.getName() +" : \nPosition: "+thePlayer.getPosition());
			}
			
			if(newPosition==63) {
				JOptionPane.showMessageDialog(null,thePlayer.getName()+" WINS!!! ", "WINNER!!!",
						JOptionPane.ERROR_MESSAGE);
				textArea1.setEnabled(false);
				buttonRollDices1.setEnabled(false);
				textArea2.setEnabled(false);
				buttonRollDices2.setEnabled(false);
				textArea3.setEnabled(false);
				buttonRollDices3.setEnabled(false);
				textArea4.setEnabled(false);
				buttonRollDices4.setEnabled(false);
				btnNewGame.setVisible(true);
			}else {
				indexOfCurrentPlayer++;
				nextTurn();
			}
		}
		
		public int newPosition(int currentPosition,int firstDice,int secondDice,Player currPlayer) {
			int finalPosition = 0;
			//bridge(6)
			int calculation = currentPosition+firstDice+secondDice;
			if(calculation==6) {
				calculation=12;
				finalPosition=calculation;
				textPaneDescribeMove.setText(currPlayer.getName()+" rrols "+firstDice+","+secondDice+" ."+currPlayer.getName()+
						" moves from "+currentPosition+" to the bridge." + currPlayer.getName() +" jumps to 12.");
				checkFreeSpace(finalPosition, currPlayer);
			}
			// goose (5,9,14,18,23,27)
			else if(calculation==5 ||calculation==9 ||calculation==14||calculation==18||calculation==23||calculation==27) {
				
				int secondMove=calculation+firstDice+secondDice;
				textPaneDescribeMove.setText(currPlayer.getName()+" rrols "+firstDice+","+secondDice+" ."+currPlayer.getName()+
						" moves from "+currentPosition+" to "+ calculation+". The GOOSE. "+ currPlayer.getName() +" moves again and goes to "+secondMove);
				
				finalPosition=secondMove;
				checkFreeSpace(finalPosition, currPlayer);
			}
			else if(calculation>63) {
				int secondMove=63-(calculation-63);
				textPaneDescribeMove.setText(currPlayer.getName()+" rrols "+firstDice+","+secondDice+" ."+currPlayer.getName()+
						" moves from "+currentPosition+" to "+ calculation+". "+currPlayer.getName()+" bounces! "+currPlayer.getName()+" returns to "+secondMove );
				finalPosition=secondMove;
				checkFreeSpace(finalPosition, currPlayer);
			}
			else {
				finalPosition=calculation;
				textPaneDescribeMove.setText(currPlayer.getName()+" rrols "+firstDice+","+secondDice+" ."+currPlayer.getName()+
						" moves from "+currentPosition+" to "+ calculation+".");
				checkFreeSpace(finalPosition, currPlayer);
			}
			
			return finalPosition;
		}
		
		public void checkFreeSpace(int position,Player currPlayer) {
			for(int i=0 ;i<playerList.size();i++) {
				Player checkPlayer=playerList.get(i);
				if((checkPlayer.getName() != currPlayer.getName()) && (checkPlayer.getPosition()==position)) {
					checkPlayer.setPosition(currPlayer.getPosition());
					playerList.set(i, checkPlayer);
					textPaneDescribeMove.setText(textPaneDescribeMove.getText() + 
							"On "+position+" is "+checkPlayer.getName()+" who moves to "+currPlayer.getPosition());
					
					if(i==0) {
						textArea1.setText("Player 1:"+checkPlayer.getName() +" : \nPosition: "+currPlayer.getPosition());
					}
					else if(i==1) {
						textArea2.setText("Player 2:"+checkPlayer.getName() +" : \nPosition: "+currPlayer.getPosition());
					}
					else if(i==2) {
						textArea3.setText("Player 3:"+checkPlayer.getName() +" : \nPosition: "+currPlayer.getPosition());
					}
					else if(i==3) {
						textArea4.setText("Player 4:"+checkPlayer.getName() +" : \nPosition: "+currPlayer.getPosition());
					}
				}
			}
		}

		public static List<Player> getPlayerList() {
			return playerList;
		}

		public static void setPlayerList(List<Player> playerList) {
			Home.playerList = playerList;
		}

		public static JTextPane getTextArea1() {
			return textArea1;
		}

		public static void setTextArea1(JTextPane textArea1) {
			Home.textArea1 = textArea1;
		}

		public static JTextPane getTextArea2() {
			return textArea2;
		}

		public static void setTextArea2(JTextPane textArea2) {
			Home.textArea2 = textArea2;
		}

		public static JTextPane getTextArea3() {
			return textArea3;
		}

		public static void setTextArea3(JTextPane textArea3) {
			Home.textArea3 = textArea3;
		}

		public static JTextPane getTextArea4() {
			return textArea4;
		}

		public static void setTextArea4(JTextPane textArea4) {
			Home.textArea4 = textArea4;
		}

		public static JButton getButtonRollDices1() {
			return buttonRollDices1;
		}

		public static void setButtonRollDices1(JButton buttonRollDices1) {
			Home.buttonRollDices1 = buttonRollDices1;
		}

		public static JButton getButtonRollDices2() {
			return buttonRollDices2;
		}

		public static void setButtonRollDices2(JButton buttonRollDices2) {
			Home.buttonRollDices2 = buttonRollDices2;
		}

		public static JButton getButtonRollDices3() {
			return buttonRollDices3;
		}

		public static void setButtonRollDices3(JButton buttonRollDices3) {
			Home.buttonRollDices3 = buttonRollDices3;
		}

		public static JButton getButtonRollDices4() {
			return buttonRollDices4;
		}

		public static void setButtonRollDices4(JButton buttonRollDices4) {
			Home.buttonRollDices4 = buttonRollDices4;
		}

		public static JButton getButtonStartGame() {
			return buttonStartGame;
		}

		public static void setButtonStartGame(JButton buttonStartGame) {
			Home.buttonStartGame = buttonStartGame;
		}

		public static JButton getButtonAddPlayer() {
			return buttonAddPlayer;
		}

		public static void setButtonAddPlayer(JButton buttonAddPlayer) {
			Home.buttonAddPlayer = buttonAddPlayer;
		}				
}
