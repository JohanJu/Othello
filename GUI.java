import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

	JButton clear, solve;
	JButton[][] matb = new JButton[8][8];
	JFrame frame = new JFrame("Reversi");
	JPanel panel = new JPanel(new BorderLayout());
	JLabel turnLabel = new JLabel();
	JLabel scoreLabel = new JLabel();
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
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		turnLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(scoreLabel);
		panel.add(turnLabel);
		frame.add(panel);
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Show a dialog value so the user can decide the maximal limit that the
	 * algorithm has to run to give the utility values
	 * 
	 * @return the limit time that the algorithm has to run to give the utility
	 *         values
	 */
	public int getLevel() {
		return Integer.parseInt(JOptionPane.showInputDialog("Enter the level:"));
	}

	public boolean help() {
		int n = JOptionPane.showConfirmDialog(frame, "Show valid moves?", "", JOptionPane.YES_NO_OPTION);
		return n == 0;

	}

	public boolean ai() {
		int n = JOptionPane.showConfirmDialog(frame, "Play against AI", "", JOptionPane.YES_NO_OPTION);
		return n == 0;
	}

	/**
	 * Draw the current state of the board
	 * 
	 * @param matrix
	 *            represent how the board looks like
	 */
	@SuppressWarnings("deprecation")
	public void paint(int[][] matrix, int a, int b) {
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
				if (a != -1 && b != -1) {
					matb[a][b].setBackground(Color.RED);
				}
			}
		}
		if (count > 0) {
			scoreLabel.setText("White: +" + count);
		} else if (count < 0) {
			scoreLabel.setText("Black: +" + -count);

		} else {
			scoreLabel.setText("Equal");

		}
	}

	/**
	 * Set a label to display who's turn it is
	 * 
	 * @param player
	 *            represent the player, -1 for black player and 1 for white
	 *            player
	 */
	public void updateLabel(int player) {
		if (player == -1) {
			turnLabel.setText("White");
		} else {
			turnLabel.setText("Black");
		}
	}

	/**
	 * Draw all the best possible moves and return which button the are being
	 * pressed
	 * 
	 * @param rec
	 *            a matrix which represent the best possible moves
	 * @return b return which button the are being pressed
	 */
	@SuppressWarnings("deprecation")
	public int paintrec(int[][] rec, boolean help) {
		if (help) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (rec[x + 1][y + 1] != 100) {

						matb[x][y].setLabel("X");

					} else {
						matb[x][y].setLabel("");
					}
				}
			}
		}
		b = -1;
		while (b == -1 || (b - 100) > 0) {
			try {
				Thread.sleep(50);
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
	 *            message that is being shown
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
