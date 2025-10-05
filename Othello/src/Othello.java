import java.util.ArrayList;

class Othello{
    void principal() {

		nbLines();
		System.out.print(gameMode());
		
    }
	
    ArrayList nbLines() {
		int lines = SimpleInput.getInt("Numbers of lines for the board : ");
		while  (lines < 4 || lines % 2 != 0 || lines > 16) {
			System.out.println("Numbers of lines must be even, at least 4 and max 16");
			lines = SimpleInput.getInt("Numbers of lines for the board : ");
		}
		ArrayList board = new ArrayList();
		for (int i=0; i<lines; i++) {
			board.add(new char[lines]);
		}
		return board;
	}
	
	String gameMode() {
		String gmode = SimpleInput.getString("Choose the gamemode, solo or duo : ");
		while  (!(gmode.equalsIgnoreCase("solo")) && !(gmode.equalsIgnoreCase("duo"))) {	
			System.out.println("Choose between solo or duo");
			gmode = SimpleInput.getString("Choose the gamemode, solo or duo : ");
		}
		return gmode;
	}
	/**
	 * It displays to the console the board of the game
	 * @param lines An integer which is the number of lines (from nbLines())
	 */
}
