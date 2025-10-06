import java.util.ArrayList;

class Othello{
    void principal() {
		/*
		String[][] tab = new String[4][4];
		int i;
		for (i = 0; i < tab.length - 1; i++) {
			System.out.print("  "+i);
		}
		System.out.print("  "+i);
		*/
		displayGame(boardList(8));
		
    }
    void afficherTableau(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
	String gameMode() {
		String gmode = SimpleInput.getString("Choose the gamemode, solo or duo : ");
		while  (!(gmode.equalsIgnoreCase("solo")) && !(gmode.equalsIgnoreCase("duo"))) {	
			System.out.println("Choose between solo or duo");
			gmode = SimpleInput.getString("Choose the gamemode, solo or duo : ");
		}
		return gmode;
	}
	
    int nbLines() {
		int lines = SimpleInput.getInt("Numbers of lines for the board : ");
		while  (lines < 4 || lines % 2 != 0 || lines > 16) {
			System.out.println("Numbers of lines must be even, at least 4 and max 16");
			lines = SimpleInput.getInt("Numbers of lines for the board : ");
		}
		return lines;
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
			System.out.print("   "+i);
		}
		i = 0;
		for (i = 0; i < tab.length; i++) {
			System.out.println();
			System.out.print(i + "  ");
			for (j = 0; j < tab.length; j++) {
				System.out.print("|" + tab[i][j]);
			}
			System.out.print("|");
		}

	}
}
