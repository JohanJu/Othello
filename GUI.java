import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {

	JButton clear, solve;
	JButton[][] matb = new JButton[8][9];
	JFrame frame = new JFrame("Reversi");
	JPanel panel = new JPanel(new BorderLayout());
	private JLabel label = new JLabel();
	int b = 0;

	/**
	 * Creates a graphical board 
	 */
	public GUI() {
		panel.setLayout(new GridLayout(9, 8));
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				JButton temp = new JButton();
				temp.setName(10 * i + j + "");
				temp.setBackground(new Color(0, 128, 0));
				temp.addActionListener(this);
				matb[i][j] = temp;
				panel.add(temp);
			}
		}	
		
		JButton temp = new JButton();
		temp.setName(101+"");
		temp.addActionListener(this);
		matb[0][8] = temp;
		panel.add(temp);
		panel.add(label);
		frame.add(panel);
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Show a dialog value so the user can decide the maximal limit that the algorithm 
	 * has to run to give the utility values
	 * 
	 * @return
	 * 			the limit time that the algorithm has to run to give the utility values
	 */
	public int getTime() {
		return Integer.parseInt(JOptionPane.showInputDialog("Enter the maximum time limit in seconds:"));
	}
	
	/**
	 * Draw the current state of the board
	 * 
	 * @param matrix
	 * 				represent how the board looks like
	 */
	public void paint(int[][] matrix) {
		int count = 0;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				matb[x][y].setLabel("");
				if (matrix[x + 1][y + 1] == 1) {
					matb[x][y].setBackground(Color.WHITE);
					matb[x][y].setOpaque(true);
					matb[x][y].setBorderPainted(false);
					count++;
				
				} else if (matrix[x + 1][y + 1] == -1) {
					matb[x][y].setBackground(Color.BLACK);
					matb[x][y].setOpaque(true);
					matb[x][y].setBorderPainted(false);
					count--;
				}

			}
		}
		if(count>0) {
		matb[0][8].setLabel("White: " + count);
		} else if(count<0) {
			matb[0][8].setLabel("Black: " + count);

		} else {
			matb[0][8].setLabel("Equal");

		}
	}
	
	/**
	 * Set a label to display who's turn it is
	 * 
	 * @param player
	 *				represent the player, -1 for black player and 1 for white player
	 */
	public void updateLabel(int player) {
		if(player == -1) {
			label.setText("White to move");
		} else {
			label.setText("Black to move");
		}
	}

	/**
	 * Draw all the best possible moves and return which button the are being pressed
	 * 
	 * @param rec
	 * 			a matrix which represent the best possible moves
	 * @return b
	 * 			return which button the are being pressed
	 */
	public int paintrec(int[][] rec) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (rec[x + 1][y + 1] != 100) {

					matb[x][y].setLabel(rec[x + 1][y + 1]+"");

				} else {
					matb[x][y].setLabel("");
				}
			}
		}
		b = -1;
		while (b == -1 || (b-100)>0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return b;
	}
	/**
	 * Show message dialog
	 * 
	 * @param msg
	 * 			message that is being shown
	 */
	public void msg(String msg) {
		JOptionPane.showMessageDialog(frame, msg);	
	}
	/**
	 * Sets which button that is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		JButton o = (JButton) e.getSource();
		String name = o.getName();
		b = Integer.parseInt(name);
	}

}
