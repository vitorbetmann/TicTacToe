import java.util.Scanner;

//tem situaçao que ele poderia botar o symb numa casa pra dar 2 opçoes pra vitoria, mas ele nao faz

public class tictactoe2 {
	public static void main(String[] words) {

		// variables
		int round;
		Scanner in = new Scanner(System.in);
		boolean draw;
		char symb1 = 'X', symb2 = 'O';
		int[] aiPlay = new int[2];
		char playAgain;

		// welcome screen
		System.out.println("Hello! Welcome to TIC-TAC-TOE!");
		System.out.println();

		do {
			round = 1;
			draw = false;
			// board array
			char[][] board = new char[3][3];
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					board[i][j] = ' ';
				}
			}

			// pick symbol
			System.out.print("Would you like to be X or O? ");
			symb1 = in.next().toUpperCase().charAt(0);

			while (symb1 != 'X' && symb1 != 'O') {
				System.out.print("That's not a valid option. Please pick X or O:");
				symb1 = in.next().toUpperCase().charAt(0);
			}
			System.out.println();

			// determines first player
			int playerNum = (int) Math.round(Math.random() + 1);
			if (playerNum == 2 && symb1 == 'X') {
				symb1 = 'O';
				System.out.println("You will play SECOND. Good Luck!");
			} else if (playerNum == 2 && symb1 == 'O') {
				symb1 = 'X';
				System.out.println("You will play SECOND. Good Luck!");
			} else
				System.out.println("You will play first. Good Luck!");

			// game
			while (!gameover(board)) {
				// draw condition
				if (round == 10) {
					draw = true;
					break;
				}
				
				if (round != 1) {
					if (symb1 == 'X') {
						symb2 = symb1;
						symb1 = 'O';
					} else {
						symb2 = symb1;
						symb1 = 'X';
					}
				}

				System.out.println();
				System.out.println("It's " + symb1 + "'s turn.");
				System.out.println();
				printBoard(board);

				// if player turn
				if ((playerNum == 1 && round % 2 != 0) || (playerNum == 2 && round % 2 == 0)) {

					// check for valid play
					while (true) {

						// ask for player input
						System.out.println();
						System.out.print("pick a line: 1 - 3: ");
						int posx = in.nextInt();
						System.out.print("pick a row: 1 - 3: ");
						int posy = in.nextInt();

						// assign input
						if (board[posx - 1][posy - 1] != ' ') {
							System.out.println("\nSorry, that's not a valid play");
							System.out.println("Valid plays are:");

							// print valid plays
							for (int i = 0; i < board.length; i++) {
								for (int j = 0; j < board[i].length; j++) {
									if (board[i][j] == ' ')
										System.out.print("( " + (i + 1) + ", " + (j + 1) + " ); ");
								}
								System.out.println();
							}

						} else {
							board[posx - 1][posy - 1] = symb1;
							break;
						}
					}
				}

				// if ai turn
				else {
					aiPlay = aiPlay(board, symb1, symb2, round);
					board[aiPlay[0]][aiPlay[1]] = symb1;
				}

				round++;

			}

			System.out.println();
			printBoard(board);
			if (draw)
				System.out.println("\nOops, it's a draw!");
			else
				System.out.println("\nAmazing!! " + symb1 + " won the game!");

			System.out.println();
			System.out.print("Would you like to play again? (y/n) ");
			playAgain = in.next().charAt(0);
			System.out.println();

		} while (playAgain == 'y');

		in.close();
		System.out.println("Thank you for playing!!");

	}

	// methods
	//////////////////////////////////////////////

	public static int[] aiPlay(char[][] board, char symb1, char symb2, int round) {

		int[] result = new int[2];
		if (round == 1) {
			result[0] = (int) Math.round(Math.random() * 2);
			result[1] = (int) Math.round(Math.random() * 2);
			return result;
		}

		int i, j = 0;

		// try to win horizontal
		// need to fix cause it tries not to lose before it tries to win
		for (i = 0; i < board.length; i++) {
			if (board[i][0] == board[i][1] && board[i][0] == symb1 && board[i][2] == ' ') {
				result[0] = i;
				result[1] = 2;
				return result;
			} else if (board[i][0] == board[i][2] && board[i][0] == symb1 && board[i][1] == ' ') {
				result[0] = i;
				result[1] = 1;
				return result;
			} else if (board[i][1] == board[i][2] && board[i][1] == symb1 && board[i][0] == ' ') {
				result[0] = i;
				result[1] = 0;
				return result;
			}
		}

		// try to win vertical
		for (i = 0; i < board.length; i++) {
			if (board[0][i] == board[1][i] && board[0][i] == symb1 && board[2][i] == ' ') {
				result[0] = 2;
				result[1] = i;
				return result;
			} else if (board[0][i] == board[2][i] && board[0][i] == symb1 && board[1][i] == ' ') {
				result[0] = 1;
				result[1] = i;
				return result;
			} else if (board[1][i] == board[2][i] && board[1][i] == symb1 && board[0][i] == ' ') {
				result[0] = 0;
				result[1] = i;
				return result;
			}
		}

		// try to win diagonal
		// /
		if (board[0][0] == board[1][1] && board[0][0] == symb1 && board[2][2] == ' ') {
			result[0] = 2;
			result[1] = 2;
			return result;
		}
		if (board[0][0] == board[2][2] && board[0][0] == symb1 && board[1][1] == ' ') {
			result[0] = 1;
			result[1] = 1;
			return result;
		}
		if (board[1][1] == board[2][2] && board[1][1] == symb1 && board[0][0] == ' ') {
			result[0] = 0;
			result[1] = 0;
			return result;
		}

		// \
		if (board[0][2] == board[1][1] && board[0][2] == symb1 && board[2][0] == ' ') {
			result[0] = 2;
			result[1] = 0;
			return result;
		}
		if (board[0][2] == board[2][0] && board[0][2] == symb1 && board[1][1] == ' ') {
			result[0] = 1;
			result[1] = 1;
			return result;
		}
		if (board[1][1] == board[2][0] && board[1][1] == symb1 && board[0][2] == ' ') {
			result[0] = 0;
			result[1] = 2;
			return result;
		}

		// prevent player win horizontal
		for (i = 0; i < board.length; i++) {
			if (board[i][0] == board[i][1] && board[i][0] == symb2 && board[i][2] == ' ') {
				result[0] = i;
				result[1] = 2;
				return result;
			} else if (board[i][0] == board[i][2] && board[i][0] == symb2 && board[i][1] == ' ') {
				result[0] = i;
				result[1] = 1;
				return result;
			} else if (board[i][1] == board[i][2] && board[i][1] == symb2 && board[i][0] == ' ') {
				result[0] = i;
				result[1] = 0;
				return result;
			}
		}

		// prevent player win vertical
		for (i = 0; i < board.length; i++) {
			if (board[0][i] == board[1][i] && board[0][i] == symb2 && board[2][i] == ' ') {
				result[0] = 2;
				result[1] = i;
				return result;
			} else if (board[0][i] == board[2][i] && board[0][i] == symb2 && board[1][i] == ' ') {
				result[0] = 1;
				result[1] = i;
				return result;
			} else if (board[1][i] == board[2][i] && board[1][i] == symb2 && board[0][i] == ' ') {
				result[0] = 0;
				result[1] = i;
				return result;
			}
		}

		// prevent player win diagonal
		// upwards
		if (board[0][0] == board[1][1] && board[0][0] == symb2 && board[2][2] == ' ') {
			result[0] = 2;
			result[1] = 2;
			return result;
		}
		if (board[0][0] == board[2][2] && board[0][0] == symb2 && board[1][1] == ' ') {
			result[0] = 1;
			result[1] = 1;
			return result;
		}
		if (board[1][1] == board[2][2] && board[1][1] == symb2 && board[0][0] == ' ') {
			result[0] = 0;
			result[1] = 0;
			return result;
		}

		// downwards
		if (board[0][2] == board[1][1] && board[0][2] == symb2 && board[2][0] == ' ') {
			result[0] = 2;
			result[1] = 0;
			return result;
		}
		if (board[0][2] == board[2][0] && board[0][2] == symb2 && board[1][1] == ' ') {
			result[0] = 1;
			result[1] = 1;
			return result;
		}
		if (board[1][1] == board[2][0] && board[1][1] == symb2 && board[0][2] == ' ') {
			result[0] = 0;
			result[1] = 2;
			return result;
		}

		// other plays
		// advance
		for (i = 0; i < board.length; i++) {
			for (j = 0; j < board[i].length; j++) {
				if (board[i][j] == symb2) {
					break;
				}

				if (board[i][j] == ' ') {
					if (j == 0) {
						result[0] = i;
						if (board[i][1] == ' ')
							result[1] = 1;
						else if (board[i][2] == ' ')
							result[1] = 2;
						return result;
					}

					else if (j == 1) {
						result[0] = i;
						if (board[i][0] == ' ')
							result[1] = 0;
						else if (board[i][2] == ' ')
							result[1] = 2;
						return result;
					} else if (j == 2) {
						result[0] = i;
						if (board[i][0] == ' ')
							result[1] = 0;
						else if (board[i][1] == ' ')
							result[1] = 1;
						return result;
					}

					// 2nd and 3rd plays
					else {
						int px = (int) Math.round(Math.random() * 2);
						int py = (int) Math.round(Math.random() * 2);
						while (board[px][py] == ' ') {
							result[0] = px;
							result[1] = py;
							return result;
						}
					}
				}
			}
		}

		for (i = 0; i < board.length; i++) {
			for (j = 1; j < board[i].length; j++) {
				if (board[i][j] != ' ')
					continue;
				else {
					result[0] = i;
					result[1] = j;
					return result;
				}
			}
		}
		return result;

	}

	public static boolean gameover(char[][] board) {

		// horizontal
		if (board[0][0] != ' ' && board[0][0] == board[0][1] && board[0][1] == board[0][2])
			return true;
		if (board[1][0] != ' ' && board[1][0] == board[1][1] && board[1][1] == board[1][2])
			return true;
		if (board[2][1] != ' ' && board[2][0] == board[2][1] && board[2][1] == board[2][2])
			return true;

		// vertical
		if (board[0][0] != ' ' && board[0][0] == board[1][0] && board[1][0] == board[2][0])
			return true;
		if (board[0][1] != ' ' && board[0][1] == board[1][1] && board[1][1] == board[2][1])
			return true;
		if (board[0][2] != ' ' && board[0][2] == board[1][2] && board[1][2] == board[2][2])
			return true;

		// diagonal
		if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
			return true;
		if (board[2][0] != ' ' && board[2][0] == board[1][1] && board[1][1] == board[0][2])
			return true;
		
		return false;
	}

	public static void printBoard(char[][] board) {

		System.out.println(" " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " ");
		System.out.println("---|---|---");
		System.out.println(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " ");
		System.out.println("---|---|---");
		System.out.println(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + " ");

	}
}
