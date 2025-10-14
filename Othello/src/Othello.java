class Othello{
    void principal() {
		displayGame(boardList(nbLines()));

		
		
    }
    void afficherTableauInt(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print("|" + tab[i][j] + "|\t");
            }
            System.out.println();
        }
    }
    
    void afficherTableau(String[][] tab) {
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
		int j;
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
			for (j = 0; j < tab.length; j++) {
				System.out.print(" | " + tab[k][j]);
			
			}
			System.out.print(" |");
		}
	}

	
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
	
	int[][] validCases(char[][] tab, char player) {
		char opponent;
		if (player == 'o') {
			opponent = 'x';
		} else { 
			oppenent = 'o';
		}
		//The 2D list validMove associate every single position around our case 
		//		topLeft, top, topRight,
		//		left, case, right
		//		botLeft, bot, botRight	
		int[][] validMove = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab.length; j++) {
				if (tab[i][j] == ' ') {
					boolean validCase = false; // if we encounter just a space its not a valid case yet
					for (int k = 0; k < validMove.length; k++) {
						int dx = validMove[k][0];
						int dy = validMove[k][1];
						int x = i + dx;
						int y = j + dy;
						if (x >= 
					}


	
}

