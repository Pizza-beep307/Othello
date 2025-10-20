class Othello{
    void principal() {
		
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
		int lines = SimpleInput.getInt("Numbers of lines for the board : ");
		while  (lines < 4 || lines % 2 != 0 || lines > 16) {
			System.out.println("Numbers of lines must be even, at least 4 and max 16");
			lines = SimpleInput.getInt("Numbers of lines for the board : ");
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
	
	boolean gameStart(int gmode) {
		boolean start = false;
		int choiceStart = SimpleInput.getInt("Do you want to play first : yes (0) no (1) ");
		if (gmode == 0) {
			while  (!(choiceStart == 0) && !(choiceStart == 1)) {	
				System.out.println("Choose to play first or no");
				choiceStart = SimpleInput.getInt("Do you want to play first : yes (0) no (1) ");
			}
		}
		if (choiceStart == 0) {
			start = true;
		}
		return start;
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
				for (int i = 0; i < count; i++) {
					x -= dx;
					y -= dy;
					board[x][y] = player;
				}
			}
		}
	}
	
	/*
	char[][] applyMove (char[][]board, int x, int y, char player) {
		char opponent = ennemy(player);
	*/
	/*
	char[][] botPlay(char[][] board, int[][] validMove, char symbole) {
		int indexDomMove = (int)(Math.random() * validMove.length);
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (i == validMove[indexDomMove][0] && j ==  validMove[indexDomMove][1]) {
					board[i][j] = symbole;
				}
			}
		}
		return board;
	}
	*/
	
}

