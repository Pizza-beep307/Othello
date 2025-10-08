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
	
	String[][] boardList(int lines) {
		String[][] tab = new String[lines][lines];
		int firstMidTab = lines/2 - 1;
		int secMidTab = lines/2;
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab.length; j++) {
				if ((i == firstMidTab && j == firstMidTab) || (i == secMidTab && j == secMidTab)) {
					tab[i][j] = " x ";
				} else if ((i == secMidTab && j == firstMidTab) || (i == firstMidTab && j == secMidTab)) {
					tab[i][j] = " o ";
				} else {
					tab[i][j] = "   ";
				}
			}
		}
		return tab;
	}
	
	/**
	 * It displays to the console the board of the game
	 * @param lines An integer which is the number of lines (from nbLines())
	 */
	void displayGame(String[][] tab) {
		int i;
		int j;
		System.out.print("  ");
		for (i = 0; i < tab.length; i++) {
			if (i>=10) {
				System.out.print("  "+i);
			} else {
				System.out.print("   "+i);
			}
		}
		i = 0;
		for (i = 0; i < tab.length; i++) {
			System.out.println();
			if (i >= 10) {
				System.out.print(i + " ");
			} else {
				System.out.print(i + "  ");
			}
			for (j = 0; j < tab.length; j++) {
				System.out.print("|" + tab[i][j]);
			}
			System.out.print("|");
		}
	}

	
	int[][] indiceTab(String[][] tab, String x) {
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

	
}
	/*
	//int[] validCase(String[][] tab) {
		
	//}
//}
*/
