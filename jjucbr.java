import java.util.ArrayList;

public class jjucbr {

	long endTime;

	/**
	 * Takes the current board and returns the utility of all the possible moves
	 * 
	 * @param matrix
	 *            represent how the pieces are places
	 * @param player
	 *            represent the player, -1 for black player and 1 for white
	 *            player
	 * @param level
	 *            how many levels you have left to search in before you return a
	 *            value
	 * @param maxTime
	 *            the maximal time the algorithm has to find all the best moves
	 *            in
	 * 
	 * @return returnMatrix a matrix that represent a value of how good a move
	 *         is
	 * 
	 */

	public int[][] getAlphaBetaMatrix(int[][] matrix, int player, int level,
			int maxTime) {
		long time = System.currentTimeMillis();
		endTime = maxTime * 1000 + time;
		long halfEndTime = maxTime * 500 + time;
		int[][] returnMatrix = new int[10][10];
		level = 1;
		int temp = 0;
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (matrix[i][j] == 0) {
					temp++;
				}
			}
		}
//		if (temp <= level) {
//			level = temp;
//		}
		while (System.currentTimeMillis() < halfEndTime) {
			int[][] movesMatrix = new int[10][10];
			for (int i = 1; i < 9; i++) {
				for (int j = 1; j < 9; j++) {
					if (matrix[i][j] == 0) {

						ArrayList<Integer> move = count(matrix, i, j, player);
						if (move.get(0) != 0) {
							int t = alphaBeta(matrix, i, j, -1000, 1000,
									player, level, move);
							if (t == 2000) {
								System.out.println(level);
								return returnMatrix;
							}
							movesMatrix[i][j] = t;
						} else {
							movesMatrix[i][j] = 100;
						}
					} else {
						movesMatrix[i][j] = 100;
					}
				}
			}

			for (int i = 1; i < 9; i++) {
				for (int j = 1; j < 9; j++) {
					returnMatrix[i][j] = movesMatrix[i][j];
				}
			}
			if (level == temp) {
				break;
			}
			level++;

		}
		System.out.println(level);
		return returnMatrix;
	}

	/**
	 * This is a recursive function that for every possible move, make that move
	 * and look for the best move of the opponent and calculate which position
	 * is the best position to place your piece. This includes the alpha-beta
	 * pruning which decreases the search time by ignoring moves that's not
	 * optimal.
	 * 
	 * @param matrix
	 *            represent how the pieces are places
	 * @param x
	 *            x position of the piece that is going to be placed
	 * @param y
	 *            y position of the piece that is going to be placed
	 * @param a
	 *            Alpha is the maximum lower bound of possible solution
	 * @param b
	 *            Beta is the minimum upper bound of possible solutions
	 * @param player
	 *            represent the player, -1 for black player and 1 for white
	 *            player
	 * @param level
	 *            how many levels you have left to search in before you return a
	 *            value
	 * @param move
	 *            list of which pieces that you turn if you place a piece at
	 *            position XY except for the first position which represent the
	 *            change in (number of white pieces) - (number of black pieces)
	 *            from the last state
	 * 
	 * @return an integer which represent the max or the min value depending on
	 *         player if there is a timeout 2000 is returned
	 */

	public int alphaBeta(int[][] matrix, int x, int y, int a, int b,
			int player, int level, ArrayList<Integer> move) {
		if (level > 6) {
			/*
			 * if(System.currentTimeMillis() > endTime) { return 2000; }
			 */
		}

		if (level == 1) {
			return move.get(0);
		}

		// copies the matrix to newMatrix
		int[][] newMatrix = new int[10][10];
		for (int j = 0; j < 10; j++) {
			for (int k = 0; k < 10; k++) {
				newMatrix[j][k] = matrix[j][k];
			}
		}

		// changes the matrix to as if you placed a piece at position (x,y)
		newMatrix[x][y] = player;
		int f = 0, g = 0;
		for (int k = 1; k < move.size(); k++) {
			f = move.get(k);
			k++;
			g = move.get(k);
			newMatrix[f][g] = player;
		}

		boolean changed = false; // if it goes into the if(move2.get(0) != 0)
									// method

		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (newMatrix[i][j] == 0) {
					ArrayList<Integer> move2 = count(newMatrix, i, j, player
							* -1);
					if (move2.get(0) != 0) {
						changed = true;
						move2.set(0, move2.get(0) + move.get(0));
						if (player == 1) {
							int temp = alphaBeta(newMatrix, i, j, a, b, player
									* -1, level - 1, move2);
							if (temp == 2000) {
								return 2000;
							}
							// opponents best min value
							b = Math.min(b, temp);
							if (a >= b) {
								return a;
							}
						} else {
							int temp = alphaBeta(newMatrix, i, j, a, b, player
									* -1, level - 1, move2);
							if (temp == 2000) {
								return 2000;
							}
							// opponents best max value
							a = Math.max(a, temp);
							if (a >= b) {
								return b;
							}
						}
					}
				}
			}
		}
		if (changed) {
			if (player == 1) {
				return b;
			} else {
				return a;
			}
		} else {
			// TEST !!!!
			// ----------------------------------------------------------------------------------
			if (player == 1) {
				return a;
			}
			return b;
		}
	}

	/**
	 * Takes the current board and returns a list of which pieces that you turn
	 * if you place a piece at position XY except for the first position which
	 * represent the change in (number of white pieces) - (number of black
	 * pieces) from the last state
	 * 
	 * @param matrix
	 *            represent how the pieces are places
	 * @param x
	 *            x position of the piece that is going to be placed
	 * @param y
	 *            y position of the piece that is going to be placed
	 * @param player
	 *            represent the player, -1 for black player and 1 for white
	 *            player
	 * 
	 * @return re list of which pieces that you turn if you place a piece at
	 *         position XY except for the first position which represent the
	 *         change in (number of white pieces) - (number of black pieces)
	 *         from the last state
	 */

	public ArrayList<Integer> count(int[][] matrix, int x, int y, int player) {
		int count = 0, a = 0, b = 0;
		ArrayList<Integer> change = new ArrayList<Integer>();
		ArrayList<Integer> re = new ArrayList<Integer>();
		re.add(0);
		if (matrix[x][y] == 0) {
			int temp = 0;
			if (matrix[x - 1][y - 1] == player * -1) {
				int tx = x - 1, ty = y - 1;
				while (matrix[tx][ty] == player * -1) {
					change.add(tx);
					change.add(ty);
					tx--;
					ty--;
					temp = temp + player;
				}
				if (matrix[tx][ty] == player) {
					for (int i = 0; i < change.size(); i++) {
						a = change.get(i);
						re.add(a);
						i++;
						b = change.get(i);
						re.add(b);

					}

					count = count + temp;
					re.set(0, count);
				}
				temp = 0;
				change.clear();
			}

			if (matrix[x][y - 1] == player * -1) {
				int tx = x, ty = y - 1;
				while (matrix[tx][ty] == player * -1) {
					change.add(tx);
					change.add(ty);
					temp = temp + player;
					ty--;
				}
				if (matrix[tx][ty] == player) {
					for (int i = 0; i < change.size(); i++) {
						a = change.get(i);
						re.add(a);
						i++;
						b = change.get(i);
						re.add(b);

					}
					count = count + temp;
					re.set(0, count);
				}
				temp = 0;
				change.clear();
			}

			if (matrix[x + 1][y - 1] == player * -1) {
				int tx = x + 1, ty = y - 1;
				while (matrix[tx][ty] == player * -1) {
					change.add(tx);
					change.add(ty);
					temp = temp + player;
					ty--;
					tx++;
				}
				if (matrix[tx][ty] == player) {
					for (int i = 0; i < change.size(); i++) {
						a = change.get(i);
						re.add(a);
						i++;
						b = change.get(i);
						re.add(b);

					}
					count = count + temp;
					re.set(0, count);
				}
				temp = 0;
				change.clear();

			}

			if (matrix[x + 1][y] == player * -1) {
				int tx = x + 1, ty = y;
				while (matrix[tx][ty] == player * -1) {
					change.add(tx);
					change.add(ty);
					temp = temp + player;
					tx++;
				}
				if (matrix[tx][ty] == player) {
					for (int i = 0; i < change.size(); i++) {
						a = change.get(i);
						re.add(a);
						i++;
						b = change.get(i);
						re.add(b);

					}
					count = count + temp;
					re.set(0, count);
				}
				temp = 0;
				change.clear();

			}

			if (matrix[x + 1][y + 1] == player * -1) {
				int tx = x + 1, ty = y + 1;
				while (matrix[tx][ty] == player * -1) {
					change.add(tx);
					change.add(ty);
					temp = temp + player;
					ty++;
					tx++;
				}
				if (matrix[tx][ty] == player) {
					for (int i = 0; i < change.size(); i++) {
						a = change.get(i);
						re.add(a);
						i++;
						b = change.get(i);
						re.add(b);
					}
					count = count + temp;
					re.set(0, count);
				}
				temp = 0;
				change.clear();
			}

			if (matrix[x][y + 1] == player * -1) {
				int tx = x, ty = y + 1;
				while (matrix[tx][ty] == player * -1) {
					change.add(tx);
					change.add(ty);
					temp = temp + player;
					ty++;
				}
				if (matrix[tx][ty] == player) {
					for (int i = 0; i < change.size(); i++) {
						a = change.get(i);
						re.add(a);
						i++;
						b = change.get(i);
						re.add(b);

					}
					count = count + temp;
					re.set(0, count);
				}
				temp = 0;
				change.clear();
			}

			if (matrix[x - 1][y + 1] == player * -1) {
				int tx = x - 1, ty = y + 1;
				while (matrix[tx][ty] == player * -1) {
					change.add(tx);
					change.add(ty);
					temp = temp + player;
					ty++;
					tx--;
				}
				if (matrix[tx][ty] == player) {
					for (int i = 0; i < change.size(); i++) {
						a = change.get(i);
						re.add(a);
						i++;
						b = change.get(i);
						re.add(b);

					}
					count = count + temp;
					re.set(0, count);
				}
				temp = 0;
				change.clear();
			}

			if (matrix[x - 1][y] == player * -1) {
				int tx = x - 1, ty = y;
				while (matrix[tx][ty] == player * -1) {
					change.add(tx);
					change.add(ty);
					temp = temp + player;
					tx--;
				}
				if (matrix[tx][ty] == player) {
					for (int i = 0; i < change.size(); i++) {
						a = change.get(i);
						re.add(a);
						i++;
						b = change.get(i);
						re.add(b);

					}
					count = count + temp;
					re.set(0, count);
				}
				temp = 0;
				change.clear();
			}
		}
		if (count != 0) {
			re.set(0, count * 2 + player);
		} else {
			re.set(0, 0);
		}
		return re;
	}

}
