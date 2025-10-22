class Othello{
    void principal() {
		/*
		char[][] ab =  {{' ', 'x', 'x', 'o'},
						{' ', 'x', 'x', ' '}, 
						{' ', 'x', 'x', ' ', },
						{'o', ' ', ' ', 'o'}};
		int[][] positionsvalides = validCases(ab, 'o');
		displayGame(ab);
		System.out.println();
		afficherTableauInt(positionsvalides);
		applyMove(ab, 0, 0, 'o');
		displayGame(ab);
		*/
		System.out.println("#####_OTHELLO GAME_#####");
		int modeChoice = gameMode();
		int boardLines = nbLines();
		char[][] board = boardList(boardLines);
		// first time displaying the board
		displayGame(board);
		System.out.prinln();
		char player = gameStart(modeChoice); // faire un random pr savoir qui commence si duo
		
		while (!(isFullBoard(board))  ) {
        
			// Tour du joueur humain
			if (player == 'o') {
				playerTurn(board, player);
			} 
			// Tour du bot (ou joueur 2 en mode duo)
			else {
				if (modeChoice == 0) {
					botTurn(board, player);
				} else {
					playerTurn(board, player);
				}
			}
			player = ennemy(player);

			// fonction pour vérifier les conditions d'arrêt du jeu
		}
	
    }
    
    boolean isFullBoard(char[][] board) {
		boolean isFull = true;
		int i = 0;
		int j = 0;
		while (i < board.length && isFull) {
			j = 0;
			while (j < board[i].length && isFull) {
				if (board[i][j] == ' ') {
					isFull = false;
				}
				j ++;
			}
			i ++;
		}
		return isFull;
	}
		
    void playerTurn(char[][] board, char player) {
		int[][] possibleMoves = validCases(board, player);
		
		if (possibleMoves.length > 0) {
			System.out.println("### Turn of Player " + player + " ###");
			System.out.println("Valid moves:");
			afficherTableauInt(possibleMoves);
			
			boolean validMove = false;
			while (!validMove) {
				int row = SimpleInput.getInt("Enter row: ");
				int col = SimpleInput.getInt("Enter column: ");
				
				int i = 0;
				while (i < possibleMoves.length && !validMove) {
					if (possibleMoves[i][0] == row && possibleMoves[i][1] == col) {
						applyMove(board, row, col, player);
						validMove = true;
					}
					i++;
				}
				
				if (!validMove) {
					System.out.println("Invalid move! Please choose from the valid positions.");
				}
			}
			
			displayGame(board);
			System.out.println();
		} else {
			System.out.println("### No valid moves for Player " + player + " ###");
			// fonction pour gérer le passage de tour
		}
	}
	void botTurn(char[][] board, char player) {
		int[][] possibleMoves = validCases(board, player);
		if (possibleMoves.length > 0 ){
			System.out.println("### Turn of the bot ###");
			int indexRandomMove = (int)(Math.random() * possibleMoves.length);
			int row = validMove[indexRandomMove][0];
			int col = validMove[indexRandomMove][1];
			applyMove(board, row, col, player);
			displayGame(board);
		} else {
			System.out.println("### No valid moves for the bot ###");
		}
	}
    void afficherTableauInt(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print("|" + tab[i][j] + "|\t");
            }
            System.out.println();
        }
    }
    /*
    void afficherTableau(char[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + "\t");
            }
            System.out.println();
        }
    }
    */
    int nbLines() {
		int lines = SimpleInput.getInt("Numbers of rows for the board : ");
		while  (lines < 4 || lines % 2 != 0 || lines > 16) {
			System.out.println("Numbers of rows must be even, at least 4 and max 16");
			lines = SimpleInput.getInt("Numbers of rows for the board : ");
		}
		return lines;
	}
	
	int gameMode() {
		int gmode = SimpleInput.getInt("Choose the gamemode, solo (0) or duo (1): ");
		while  (!(gmode == 0) && !(gmode == 1)) {	
			System.out.println("Choose between solo or duo");
			gmode = SimpleInput.getInt("Choose the gamemode, solo (0) or duo (1): ");
		}
		return gmode;
	}
	/**A FAIRE FONCTION TEST SUR CETTE FONCTION, C'EST LA PLUS SIMPLE IMO
	 * 
	 * 
	 */
	char gameStart(int gmode) {
		char player;
		int choiceStart = SimpleInput.getInt("Do you want to play first : yes (0) no (1) ");
		if (gmode == 0) {
			while  (!(choiceStart == 0) && !(choiceStart == 1)) {	
				System.out.println("Choose to play first or no");
				choiceStart = SimpleInput.getInt("Do you want to play first : yes (0) no (1) ");
			}
		}
		if (choiceStart == 0) {
			player = 'o';
		} else {
			player = 'x';
		}
		return player;
	}
	
	char[][] boardList(int lines) {
		char[][] tab = new char[lines][lines];
		int firstMidTab = lines/2 - 1;
		int secMidTab = lines/2;
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab.length; j++) {
				if ((i == firstMidTab && j == firstMidTab) || (i == secMidTab && j == secMidTab)) {
					tab[i][j] = 'x';
				} else if ((i == secMidTab && j == firstMidTab) || (i == firstMidTab && j == secMidTab)) {
					tab[i][j] = 'o';
				} else {
					tab[i][j] = ' ';
				}
			}
		}
		return tab;
	}
	
	/**
	 * It displays to the console the board of the game
	 * @param lines An integer which is the number of lines (from nbLines())
	 */
	void displayGame(char[][] tab) {
		final int LENGTH = tab.length;
		System.out.print("   ");
		for (int i = 0; i < LENGTH; i++) {
			if ( i>= 10) {
				System.out.print("  "+i);
			} else {
				System.out.print("   "+i);
			}
		}
		for (int k = 0; k < LENGTH; k++) {
			System.out.println();
			if (k >= 10) {
				System.out.print(k + " ");
			} else {
				System.out.print(k + "  ");
			}
			for (int j = 0; j < LENGTH; j++) {
				System.out.print(" | " + tab[k][j]);
			
			}
			System.out.print(" |");
		}
	}

	int[][] validCases(char[][] board, char player) {
		char opponent = ennemy(player);
		final int LENGTH = board.length;
		//The 2D list validMove associate every single position around our case 
		//		topLeft, top, topRight,
		//		left, case, right
		//		botLeft, bot, botRight	
		int[][] move = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
		int[][] temp = new int[LENGTH * LENGTH][2];
		int count = 0;
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				if (board[i][j] == player) {
					for (int k = 0; k < move.length; k++) {
						// we start searching on top left
						int dx = move[k][0];
						int dy = move[k][1];
						// we add the previous movement to our actual position
						int x = i + dx;
						int y = j + dy;
						// we initialize this variable to search only when we found an opponent
						boolean hasOpponent = false; 
						
						while (x >= 0 && x < LENGTH && y >= 0 && y < LENGTH && board[x][y] == opponent) {
							// we circle around our player
							x += dx; // line
							y += dy; // column
							hasOpponent = true; // we found an opponent around our player
						}
						
						if (hasOpponent && x >= 0 && x < LENGTH && y >= 0 && y < LENGTH && board[x][y] == ' ') {
							// we verify if we did not already seen this position in our list
							boolean alrSeen = true; 
							int index = 0;
							while (index < count && alrSeen) {
								if (temp[index][0] == x && temp[index][1] == y) {
									alrSeen = false;
								}
								index ++;
							}
							// if its a new position we add it
							if (alrSeen) {
								temp[count][0] = x;
								temp[count][1] = y;
								count ++;
							}
						}
					}
				}
			}
		}
		// we sort the useless {0, 0} in our temp list to a fresh list result
		int[][] result = new int[count][2];
		for (int i = 0; i < count; i++) {
			result[i][0] = temp[i][0];
			result[i][1] = temp[i][1];
		}
		return result;
	}			
	char ennemy(char player) {
		char opponent;
		if (player == 'o') {
			opponent = 'x';
		} else {
			opponent = 'o';
		}
		return opponent;
				
	}
	
	void applyMove(char[][] board, int a, int b, char player) {
		char opponent = ennemy(player);
		final int LENGTH = board.length;
		int[][] move = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
		board[a][b] = player;
		for (int k = 0; k < move.length; k++) {
			// we start searching on top left
			int dx = move[k][0];
			int dy = move[k][1];
			// we add the previous movement to our actual position
			int x = a + dx;
			int y = b + dy;
			// we initialize this variable to search only when we found an opponent
			boolean hasOpponent = false;
			int count = 0;
			while (x >= 0 && x < LENGTH && y >= 0 && y < LENGTH && board[x][y] == opponent) {
				// we circle around our player
				x += dx; // line
				y += dy; // column
				count ++;
				hasOpponent = true; // we found an opponent around our player
			}
			
			if (hasOpponent && x >= 0 && x < LENGTH && y >= 0 && y < LENGTH && board[x][y] == player) {
				// We go backward
				for (int i = 0; i < count; i++) {
					x -= dx;
					y -= dy;
					board[x][y] = player;
				}
			}
		}
	}
}

