class Othello{
    void principal() {
		
		char[][] ab = boardList(nbLines());
		int[][] positionsvalides = validCases(ab, 'x');
		displayGame(ab);
		System.out.println();
		afficherTableauInt(positionsvalides);
		
    }
    
    void afficherTableauInt(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print("|" + tab[i][j] + "|\t");
            }
            System.out.println();
        }
    }
    
    void afficherTableau(char[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
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
		System.out.print("   ");
		for (int i = 0; i < tab.length; i++) {
			if ( i>= 10) {
				System.out.print("  "+i);
			} else {
				System.out.print("   "+i);
			}
		}
		for (int k = 0; k < tab.length; k++) {
			System.out.println();
			if (k >= 10) {
				System.out.print(k + " ");
			} else {
				System.out.print(k + "  ");
			}
			for (int j = 0; j < tab.length; j++) {
				System.out.print(" | " + tab[k][j]);
			
			}
			System.out.print(" |");
		}
	}

	/*
	int[][] indiceTab(char[][] tab, char x) {
		int i = 0;
		int j = 0;
		int n = 0;
		int[][] indTab = new int[tab.length][2];
		while (i < tab.length) {
			j = 0;
			while (j < tab.length) {
				if (tab[i][j] == x) {
					indTab[n][0] = i;
					indTab[n][1] = j;
					n ++;
				}
				j ++;
			}
			i ++;
		}
		return indTab;
	}
	*/
	
	int[][] validCases(char[][] board, char player) {
		char opponent = ennemy(player);
		//The 2D list validMove associate every single position around our case 
		//		topLeft, top, topRight,
		//		left, case, right
		//		botLeft, bot, botRight	
		int[][] move = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
		int[][] temp = new int[board.length * board.length][2];
		int jspcompt = 0;
		boolean positionzerovalid = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == player) {
					for (int k = 0; k < move.length; k++) {
						int dx = move[k][0];
						int dy = move[k][1];
						int x = i + dx;
						int y = j + dy;
					
						if (x >= 0 && x < board.length && y >= 0 && y < board.length && board[x][y] == opponent) {
							int[] deplacement = new int[]{dx, dy};
							int[] pos = new int[]{x, y};
							int[] posi = searchCase(board, pos, deplacement, opponent);
							if (posi[0] == 0 && posi[1] == 0) {
								positionzerovalid = true;
							}
							if (posi[0] > 0 || posi[1] > 0) {
								temp[jspcompt] = posi;
								jspcompt += 1;
							}
						}
					}
				}
			}
		}
		// ON FAIT UNE NOUVELLE LISTE 2D AVEC QUE LES TRUC DONT ON A BESOIN
		if (positionzerovalid) {
			jspcompt += 1;
		}
		int[][] result = new int[jspcompt][2];
		for (int i = 0; i <= result.length - 1; i++) {
			result[i][0] = temp[i][0];
			result[i][1] = temp[i][1];
		}
		if (positionzerovalid) {
			result[result.length][0] = 0;
			result[result.length][1] = 0;
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
	/** PENSER A FAIRE UNE FONCTION QUI DETERMINE LENNEMI EN FONCTION DU SYMBOLE DU JOUEUR
	 * rôle : Cette fonction permet de savoir si une position prise en paramètre est valide 
	 * en regardant uniquement dans la direction prix en paramètre (en bas droite ou gauche etc...) 
	 * le plateau
	 * le joueur, donc l'ennemi aussi
	 * la position du pion
	 * La direction
	 * 
	 */
	int[] searchCase (char[][] board, int[] pos, int[] move, char opponent) {
		char player = ennemy(opponent);
		//final int MAXLINE = 2 * (board.length * board.length); // We use pythagore theroem to get the diagonal of the board (longest line)
		//int[][] listPionPasse = new int[MAXLINE][2];
		int dx = pos[0];
		int dy = pos[1];
		// we verify if we are in the board
		boolean valid = (((dx >= 0) && (dx < board.length)) && ((dy >= 0) && (dy < board.length)));
		// maybe do while better
		while ((board[dx][dy] == opponent && board[dx][dy] != ' ') && valid) {
			// on se déplace uniquement dans le sens ou on a trouvé le pion adverse
			dx += move[0];
			dy += move[1];
			// on vérifie à chaque itération si on est bien toujours dans le plateau
			valid = (((dx >= 0) && (dx < board.length)) && ((dy >= 0) && (dy < board.length)));
		}
		int[] result;
		if (valid) {
			result = new int[]{dx, dy}; // y a bien notre pion donc on peut dire que cette position est valide (je ne sais pas encore quoi retourner 
		} else {
			result = new int[]{-1, -1};
		}
		return result;
	}

	/** Liste de fonction/méthodes pour l'arrêt du jeu */
	
	/**
	 * Indique si le tableau est vide ou rempli
	 * @param boardList : Tableau du jeu
	 * @return Boolean, True si tableau remplis, False au contraire
	 */
	boolean tabFull(char[][] board){
		for (int i = 0 ; i < board.length; i++) {
			for (int j = 0 ; j < board[i].length; j++) {
				if (board[i][j] == ' ') {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Vérifie si les joueurs peuvent continuer de jouer
	 * @param board Plateau de jeu
	 * @return Boolean : True si aucun joueur peut jouer, la partie s'arrête. False si le jeu peut continuer
	 */
	boolean canPlay (char [][] board) {
		int[][] movesX = validCases(board, 'x');
        int[][] movesO = validCases(board, 'o');
        
        if (movesX.length == 0 && movesO.length == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean isGameOver(char[][] board) {
        return tabFull(board) || canPlay(board);
    }
    
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


