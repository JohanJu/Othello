import java.util.ArrayList;

public class jjucbr2 {
	int[][] mult;
	int round = 0;

	public jjucbr2() {
		System.out.println("ver 2");
	}

	/*
	 * returnerar en matrix som anger skillnaden från nuvarande ställning och
	 * ställningen om alla drag väljs efter max antal vända brickor level antal
	 * steg ner
	 */

	public int[][] getAlphaBetaMatrix(int[][] matrix, int player, int level) {
		if(level < 3){
			mult(false);
		}else{
			mult(true);
		}
		round = 64;
		int[][] movesMatrix = new int[10][10];
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (matrix[i][j] == 0) {
					round--;
					ArrayList<Integer> move = count(matrix, i, j, player);
					if (move.get(0) != 0) {
						int t = AlphaBeta(matrix, i, j, -1000, 1000, player, level, move);
						movesMatrix[i][j] = t;
					} else {
						movesMatrix[i][j] = 100;
					}
				} else {
					movesMatrix[i][j] = 100;
				}
			}
		}
		if (round > 40) {
			mult(false);
		} else {
			multEdit(matrix);
		}
		return movesMatrix;
	}

	public int AlphaBeta(int[][] matrix, int x, int y, int a, int b, int player, int level, ArrayList<Integer> move) {
		if (level == 1) {
			return move.get(0);
		}

		// kopiera matrix till newMatrix
		int[][] newMatrix = new int[10][10];
		for (int j = 0; j < 10; j++) {
			for (int k = 0; k < 10; k++) {
				newMatrix[j][k] = matrix[j][k];
			}
		}

		/* ändra matrix som om man lagt pjäs på pos x,y */
		newMatrix[x][y] = player;
		int f = 0, g = 0;
		for (int k = 1; k < move.size(); k++) {
			f = move.get(k);
			k++;
			g = move.get(k);
			newMatrix[f][g] = player;
		}

		player = player * -1;

		/* others turn */
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (newMatrix[i][j] == 0) {
					ArrayList<Integer> move2 = count(newMatrix, i, j, player);

					if (move2.get(0) != 0) {
						move2.set(0, move2.get(0) + move.get(0));
						if (player == -1) {
							b = Math.min(b, AlphaBeta(newMatrix, i, j, a, b, player, level - 1, move2));
							if (a >= b) {
								return a;
							}
						} else {
							a = Math.max(a, AlphaBeta(newMatrix, i, j, a, b, player, level - 1, move2));
							if (a >= b) {
								return b;
							}
						}
					}
				}
			}
		}

		if (player == -1) {
			if (b == 1000) {
				b = 8;
			}
			return b;
		} else {
			if (a == -1000) {
				a = -8;
			}
			return a;
		}
	}

	public ArrayList<Integer> count(int[][] matrix, int x, int y, int player) {

		int count = 0;
		boolean valid = false;
		ArrayList<Integer> re = new ArrayList<Integer>();
		re.add(0);
		if (matrix[x][y] == 0) {
			int temp = 0;
			if (matrix[x - 1][y - 1] == player * -1) {
				int tx = x - 1, ty = y - 1;
				while (matrix[tx][ty] == player * -1) {
					temp = temp + player * mult[tx - 1][ty - 1];
					tx--;
					ty--;
				}
				if (matrix[tx][ty] == player) {
					tx = x - 1;
					ty = y - 1;
					while (matrix[tx][ty] == player * -1) {
						re.add(tx);
						re.add(ty);
						tx--;
						ty--;
					}
					valid = true;
					count = count + temp;
				}
				temp = 0;
			}

			if (matrix[x][y - 1] == player * -1) {
				int tx = x, ty = y - 1;
				while (matrix[tx][ty] == player * -1) {

					temp = temp + player * mult[tx - 1][ty - 1];
					ty--;
				}
				if (matrix[tx][ty] == player) {
					tx = x;
					ty = y - 1;
					while (matrix[tx][ty] == player * -1) {
						re.add(tx);
						re.add(ty);
						ty--;
					}
					valid = true;
					count = count + temp;
				}
				temp = 0;
			}

			if (matrix[x + 1][y - 1] == player * -1) {
				int tx = x + 1, ty = y - 1;
				while (matrix[tx][ty] == player * -1) {
					temp = temp + player * mult[tx - 1][ty - 1];
					ty--;
					tx++;
				}
				if (matrix[tx][ty] == player) {
					tx = x + 1;
					ty = y - 1;
					while (matrix[tx][ty] == player * -1) {
						re.add(tx);
						re.add(ty);
						tx++;
						ty--;
					}
					valid = true;
					count = count + temp;
				}
				temp = 0;

			}

			if (matrix[x + 1][y] == player * -1) {
				int tx = x + 1, ty = y;
				while (matrix[tx][ty] == player * -1) {
					temp = temp + player * mult[tx - 1][ty - 1];
					tx++;
				}
				if (matrix[tx][ty] == player) {
					tx = x + 1;
					ty = y;
					while (matrix[tx][ty] == player * -1) {
						re.add(tx);
						re.add(ty);
						tx++;
					}
					valid = true;
					count = count + temp;
				}
				temp = 0;

			}

			if (matrix[x + 1][y + 1] == player * -1) {
				int tx = x + 1, ty = y + 1;
				while (matrix[tx][ty] == player * -1) {
					temp = temp + player * mult[tx - 1][ty - 1];
					ty++;
					tx++;
				}
				if (matrix[tx][ty] == player) {
					tx = x + 1;
					ty = y + 1;
					while (matrix[tx][ty] == player * -1) {
						re.add(tx);
						re.add(ty);
						tx++;
						ty++;
					}
					valid = true;
					count = count + temp;
				}
				temp = 0;
			}

			if (matrix[x][y + 1] == player * -1) {
				int tx = x, ty = y + 1;
				while (matrix[tx][ty] == player * -1) {
					temp = temp + player * mult[tx - 1][ty - 1];
					ty++;
				}
				if (matrix[tx][ty] == player) {
					tx = x;
					ty = y + 1;
					while (matrix[tx][ty] == player * -1) {
						re.add(tx);
						re.add(ty);
						ty++;
					}
					valid = true;
					count = count + temp;
				}
				temp = 0;
			}

			if (matrix[x - 1][y + 1] == player * -1) {
				int tx = x - 1, ty = y + 1;
				while (matrix[tx][ty] == player * -1) {
					temp = temp + player * mult[tx - 1][ty - 1];
					ty++;
					tx--;
				}
				if (matrix[tx][ty] == player) {
					tx = x - 1;
					ty = y + 1;
					while (matrix[tx][ty] == player * -1) {
						re.add(tx);
						re.add(ty);
						tx--;
						ty++;
					}
					valid = true;
					count = count + temp;
				}
				temp = 0;
			}

			if (matrix[x - 1][y] == player * -1) {
				int tx = x - 1, ty = y;
				while (matrix[tx][ty] == player * -1) {
					temp = temp + player * mult[tx - 1][ty - 1];
					tx--;
				}
				if (matrix[tx][ty] == player) {
					tx = x - 1;
					ty = y;
					while (matrix[tx][ty] == player * -1) {
						re.add(tx);
						re.add(ty);
						tx--;
					}
					valid = true;
					count = count + temp;
				}
				temp = 0;
			}
		}

		if (valid) {
			re.set(0, count * 2 + player * mult[x - 1][y - 1]);

		} else {
			re.set(0, 0);
		}
		return re;
	}

	public void mult(boolean set) {
		
		if (set) {
			mult = new int[][] { { 20, -5, 5, 5, 5, 5, -5, 20 }, { -5, -10, -2, -2, -2, -2, -10, -5 },
					{ 5, -2, 1, 1, 1, 1, -2, 8 }, { 5, -2, 1, 1, 1, 1, -2, 6 }, { 5, -2, 1, 1, 1, 1, -2, 6 },
					{ 5, -2, 1, 1, 1, 1, -2, 8 }, { -5, -10, -2, -2, -2, -2, -10, -5 },
					{ 20, -5, 8, 6, 6, 8, -5, 20 } };
		} else {
			mult = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1 } };
		}
	}

	public void multEdit(int[][] matrix){
		if (matrix[0][0] != 0) {
			mult[0][0] = 1;
			mult[1][0] = 1;
			mult[0][1] = 1;
			mult[1][1] = 1;
		}
		if (matrix[0][7] != 0) {
			mult[0][7] = 1;
			mult[1][7] = 1;
			mult[0][6] = 1;
			mult[1][6] = 1;
		}
		if (matrix[7][0] != 0) {
			mult[7][0] = 1;
			mult[6][0] = 1;
			mult[7][1] = 1;
			mult[6][1] = 1;
		}
		if (matrix[7][7] != 0) {
			mult[7][7] = 1;
			mult[6][7] = 1;
			mult[7][6] = 1;
			mult[6][6] = 1;
		}
		
	}

}
