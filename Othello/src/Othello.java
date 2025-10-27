class Othello{
    void principal() {

		// Launch test
		
		testBoardList();
		
		System.out.println("#####_OTHELLO GAME_#####");
		int modeChoice = gameMode();
		
		// FOR SOLO : Bot Selection :
		int selectBotDifficulty = 0 ; // 0 Random ; 1 Intelligent
			if (modeChoice==0){
				selectBotDifficulty = SimpleInput.getInt("-- Select Bot difficulty -- random (0) ou intelligent (1) : ");
				while  (!(selectBotDifficulty == 0) && !(selectBotDifficulty == 1)) {
					System.out.println("Invalid Choice. Please choice 0 (for random bot) or 1 (for intelligent bot).");
					selectBotDifficulty = SimpleInput.getInt("-- Select Bot difficulty -- random (0) ou intelligent (1) : ");
				}
			}
					
		
		int boardLines = nbLines();
		char[][] board = boardList(boardLines);
		// first time displaying the board
		displayGame(board);
		System.out.println();
		char player = gameStart(modeChoice);
		
		// While Game == Start
		while (!isGameOver(board)) {
			int numOfValidPos = validCases(board, player).length;
			// Human Player turn
			if (player == 'o' && numOfValidPos > 0) {
				playerTurn(board, player);
			}
			// Bot turn or player2 in duo mode
			else {
				numOfValidPos = validCases(board, player).length;
				if (numOfValidPos > 0) {
					if (modeChoice == 0) {
						if (selectBotDifficulty == 0) {
							botTurn(board, player);
						} else {
							botTurnSmart(board, player);
						}
					} else {
						playerTurn(board, player);
					}
				}
			}
			player = ennemy(player);
		}
		winner(board);
    }

    /** List of functions/methods for stopping the game */
	
	/**
	 * Indicates if the table is empty or filled
	 * @param board : Table of the game
	 * @return Boolean, True if boardList filled, False on the contrary
	 */
	boolean tabFull (char[][] board) {
		boolean result = true;
		for (int i = 0 ; i < board.length; i++) {
			for (int j = 0 ; j < board[i].length; j++) {
				if (board[i][j] == ' ') {
					result =  false;
				}
			}
		}
		return result;
	}
	
	/**
	 * Check if the actual players can continue playing
	 * @param board : Table of the game
	 * @return Boolean : True if nobody can play, else True ⇒ Game End.
	 * False ⇒ The game can continue
	 */
	boolean cannotPlay (char [][] board) {
		int[][] movesX = validCases(board, 'x');
        int[][] movesO = validCases(board, 'o');
        boolean result = false;
        
        if (movesX.length == 0 && movesO.length == 0) {
			result = true;
		} 
		return result;
	}
	/**
	 * Check if the game is still playable
	 * @param board : Table of the game
	 * @return Boolean : False if game end, True if game continue to play
	 */
	boolean isGameOver(char[][] board) {
        return tabFull(board) || cannotPlay(board);
    }
    
    /**
	 * Determines the winner of the game
	 * @param board : Table of the game
	 */
    void winner (char[][] board) {
		int scoreX = 0;
		int scoreO = 0;
		
		for (int i = 0 ; i < board.length; i++) {
			for (int j = 0 ; j < board[i].length; j++) {
				if (board[i][j] == 'x'){
					scoreX++;
				} else if (board[i][j] == 'o'){
					scoreO++;
				}
			}
		}
		
		System.out.println("--- Partie Terminée ---");
		System.out.println("Résultat de la partie : ");
		System.out.println("Score du pion X : "+ scoreX);
		System.out.println("Score du pion O : "+ scoreO);
		
		if (scoreO > scoreX){
			System.out.println("Le joueur O gagne la partie !!");
		} else if (scoreX > scoreO){
			System.out.println("Le joueur X gagne la partie !!");
		} else {
			System.out.println("Égalité, Bien joué à vous deux !!");
		}
	}

	/**
	 * Determines if the board game is full
	 * @param board : Table of the game
	 * @return Boolean : True if the game isfull, else False
	 */
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
	
	/**
	 * Allows the current player to play
	 * @param board : Table of the game
	 * @param player : Present player in the game
	 */
    void playerTurn(char[][] board, char player) {
		int temp = validCases(board, ennemy(player)).length;
		if (temp == 0) {
			System.out.println("Player " + ennemy(player) + " has no valid moves, he pass the turn");
		}
		int[][] possibleMoves = validCases(board, player);
		
		if (possibleMoves.length > 0) {
			System.out.println("");
			System.out.println("### Turn of Player " + player + " ###");
			System.out.println("");
			System.out.println("Valid moves:");
			displayValidMoove(board, possibleMoves);
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
	
	/**
	 * Implement the bot turn with random choices
	 * @param board : Table of the game
	 * @param player : Present player in the game
	 */
	void botTurn(char[][] board, char player) {
		int temp = validCases(board, ennemy(player)).length;
		if (temp == 0) {
			System.out.println("Player " + ennemy(player) + " has no valid moves, he pass the turn");
		}
		int[][] possibleMoves = validCases(board, player);
		if (possibleMoves.length > 0 ){
			System.out.println("### Turn of the bot ###");
			int indexRandomMove = (int)(Math.random() * possibleMoves.length);
			int row = possibleMoves[indexRandomMove][0];
			int col = possibleMoves[indexRandomMove][1];
			applyMove(board, row, col, player);
			displayGame(board);
		} else {
			System.out.println("### No valid moves for the bot ###");
		}
	}
	
	/**
	 * Implement the bot with intelligent choice, he chooses the movement that turns over the most pawns
	 * @param board : board of the game
	 * @param player : Present player in the game
	 * @param int a : row
	 * @param int b : col
	 * @return totalFlips : Number of pawn can be flip
	 */
	int countFlips (char[][] board, char player, int a, int b){
		char opponent = ennemy(player);
		int length = board.length;
		int[][] move = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
		int totalFlips = 0;
		
		for (int j = 0; j < move.length; j++){
			int dx = move[j][0];
			int dy = move[j][1];
			// we add the previous movement to our actual position
			int x = a + dx;
			int y = b + dy;
			// we initialize this variable to search only when we found an opponent
			boolean hasOpponent = false; 
			int count = 0; // count of opponements in this direction
			
			while (x >= 0 && x < length && y >= 0 && y < length && board[x][y] == opponent) {
				// we circle around our player
				x += dx; //line
				y += dy; // column
				count++;
				hasOpponent = true; // we found an opponent around our player
			}
						
			if (hasOpponent && x >= 0 && x < length && y >= 0 && y < length && board[x][y] == player) {
				totalFlips += count;
			}
		}
		return totalFlips;
	}
				
				
	
	void botTurnSmart(char[][] board, char player){
		int valid = validCases(board, ennemy(player)).length;
		if (valid == 0) {
			System.out.println("Le joueur " + ennemy(player) + " passe son tour.");
		}
		
		int[][] possibleMoves = validCases(board, player);
		if (possibleMoves.length > 0) {
			System.out.println("");
			System.out.println("### Turn of the bot ###");
			int bestMoveRow = -1;
			int bestMoveCol = -1;
			int maxFlips = -1;
			
			for (int i = 0; i < possibleMoves.length; i++) {
				int row = possibleMoves[i][0];
				int col = possibleMoves[i][1];
				int currentFlips = countFlips(board, player, row, col);
				
				if (currentFlips > maxFlips) {
					maxFlips = currentFlips;
					bestMoveRow = row;
					bestMoveCol = col;
				}
			}
			applyMove(board, bestMoveRow, bestMoveCol, player);
			displayGame(board);
		} else {
			System.out.println("### No valid moves for the bot ###");
		}
	}
			
			
						
	
	/**
	 * Display Tab
	 * @param tab : Tab you want to display
	 */
    void afficherTableauInt(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print("|" + tab[i][j] + "|\t");
            }
            System.out.println();
        }
    }
   
   	/**
	 * Ask for the number of lines on the board game (board)
	 * @return lines : Number of line
	 */
    int nbLines() {
		int lines = SimpleInput.getInt("Numbers of rows for the board : ");
		while  (lines < 4 || lines % 2 != 0 || lines > 16) {
			System.out.println("Numbers of rows must be even, at least 4 and max 16");
			lines = SimpleInput.getInt("Numbers of rows for the board : ");
		}
		return lines;
	}
	
	/**
	 * Ask the gamemode to player
	 * @return gmode : The game mode player choose
	 */
	int gameMode() {
		int gmode = SimpleInput.getInt("Choose the gamemode, solo (0) or duo (1): ");
		while  (!(gmode == 0) && !(gmode == 1)) {	
			System.out.println("Choose between solo or duo");
			gmode = SimpleInput.getInt("Choose the gamemode, solo (0) or duo (1): ");
		}
		return gmode;
	}
	
	/**
	 * Manages or requests, which begins depending on the game mode
	 * @return player : player play first
	 */
	char gameStart(int gmode) {
		char player;
		
		if (gmode == 0) {
			int choiceStart = SimpleInput.getInt("Do you want to play first : yes (0) no (1) ");
			while  (!(choiceStart == 0) && !(choiceStart == 1)) {	
				System.out.println("Choose to play first or no");
				choiceStart = SimpleInput.getInt("Do you want to play first : yes (0) no (1) ");
			}
		
			if (choiceStart == 0) {
				player = 'o';
			} else {
				player = 'x';
			}
		} else {
			player = 'o';
			System.out.println("Player o start to play!");
		}
		return player;
	}
	
	/**
	 * Ask for the number of lines on the board game (board)
	 * @return lines : Number of line
	 */
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
	
	/**
	 * Determines which box is valid based on the current player
	 * @param board : Table of the game
	 * @param player : Current player
	 * @return result : tab with all possibilites coordinate and for the bot the number of ennemies we see in a row
	 */
	int[][] validCases(char[][] board, char player) {
		char opponent = ennemy(player);
		final int LENGTH = board.length;
		//The 2D list validMove associate every single position around our case 
		//		topLeft, top, topRight,
		//		left, case, right
		//		botLeft, bot, botRight	
		int[][] move = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
		int[][] temp = new int[LENGTH * LENGTH][3];
		int count = 0;
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				if (board[i][j] == player) {
					for (int k = 0; k < move.length; k++) {
						int nbOpponent = 0;
						// we start searching on top left
						int dx = move[k][0];
						int dy = move[k][1];
						// we add the previous movement to our actual position
						int x = i + dx;
						int y = j + dy;
						// we initialize this variable to search only when we found an opponent
						boolean hasOpponent = false; 
						
						while (x >= 0 && x < LENGTH && y >= 0 && y < LENGTH && board[x][y] == opponent) {
							nbOpponent ++;
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
								temp[count][2] = nbOpponent;
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
	
	/**
	 * Determine ennemy of player
	 * @param player : Current player
	 * @return opponement : ennemy of the player
	 */
	char ennemy(char player) {
		char opponent;
		if (player == 'o') {
			opponent = 'x';
		} else {
			opponent = 'o';
		}
		return opponent;
				
	}
	
	boolean isAValidMoove(int[][] possibleMoves, int row, int col) {
		for (int i = 0 ; i < possibleMoves.length ; i++) {
			if (possibleMoves[i][0] == row && possibleMoves[i][1] == col) {
				return true;
			}
		}
		return false;
	}
	
	void displayValidMoove(char[][] tab, int[][] possibleMoves) {
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
				char content = tab[k][j];
				if (content == ' ' && possibleMoves!= null && isAValidMoove(possibleMoves, k, j)){
					System.out.print(" | " + '.');
				} else {
					System.out.print(" | " + content);
				}
			}
			System.out.print(" |");
		}
		System.out.println();
	}
		
	
	/**
	 * Apply move of a player
	 * @param board : Table of the game
	 * @param a : player's x coordinates
	 * @param b : player's y coordinates
	 * @param player : Current player
	 */
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
	
	/**
	 * Runs boardList tests for 4, 8, 16
	 */
	void testBoardList () {
		System.out.println("--- Début boardList ---");
		int nbTest = 0;
		
		if (testCasBoardList(4)) {
			nbTest++;
		}
		if (testCasBoardList(8)) {
			nbTest++;
		}
		
		if (testCasBoardList(16)) {
			nbTest++;
		}
	}
	
	/**
	 * Check if the 4 central pawns are valid. &  Check the length of table & Check if cases (excepted the middle) are empty
	 * @param lineTest : test for a certain number of line
	 * @return : True if test are valid, else false
	*/
	boolean testCasBoardList (int lineTest) {
		System.out.println ("Test de la fonc boardList avec " + lineTest+" en cours...");
		char[][] boardTest = boardList(lineTest);
		
		// Vérifie taille de liste
		
		if (boardTest.length != lineTest) {
			System.out.println("Erreur : Liste de taille incorrect");
			return false;
		}
		
		if (boardTest[0].length != lineTest) {
			System.out.println("Erreur : Liste de taille incorrect");
			return false;
		}
		
		
	/* * - Vérifie si les 4 centraux sont valide*/	
	
		int mid1 = lineTest / 2 - 1;
		int mid2 = lineTest / 2;
		
		if (boardTest[mid1][mid1] != 'x') {
			System.out.println("ERREUR : La position est non correct");
			return false;
		}
		
		if (boardTest[mid1][mid2] != 'o') {
			System.out.println("ERREUR : La position est non correct");
			return false;
		}
		if (boardTest[mid2][mid1] != 'o') {
			System.out.println("ERREUR : La position est non correct");
			return false;
		}
		if (boardTest[mid2][mid2] != 'x') {
			System.out.println("ERREUR : La position est non correct");
			return false;
		}
		
		for (int i = 0; i < lineTest; i++) {
			for (int j = 0; j < lineTest; j++) {
				boolean Center = (i == mid1 && j == mid1) || (i == mid2 && j == mid2) || (i == mid1 && j == mid2) || (i == mid2 && j == mid1);
				
				if (!Center && boardTest[i][j] != ' ') {
					System.out.println("ERREUR: Case [" + i + "][" + j + "] devrait être vide (' ')");
					return false;
				}
			}
		}
		
		System.out.println("Fin du test -> OK");
		return true;
	}
}

