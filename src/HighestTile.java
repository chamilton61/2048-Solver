import java.util.ArrayList;


public class HighestTile {
	
	/**
	 * This doens't work correctly yet
	 */
	
	ArrayList<String> moves;
	
	public HighestTile() {
		moves = new ArrayList<String>();
		
		moves.add("up");
		moves.add("down");
		moves.add("right");
		moves.add("left");
		
		solve();
	}
	
	void solve() {

		puzzle puz = new puzzle();
		puzzle puzCopy = new puzzle();
		puzzle tempPuzzle = new puzzle();

		puz.printState(puz.getState());

		String bestMove = "none";
		int highestTileMovie;
		int totalMoves = 0;
		

		while (!puz.checkWin()) {
			totalMoves++;
			System.out.println("++++++++++++++++++++++++++++++++");
			highestTileMovie = 0;

			// store the used state of the puzzle
			puzCopy.setState(puz.getState());
			
			
			for (String move : moves) {
				// store the temp state for each try
				tempPuzzle.setState(puzCopy.getState());
				tempPuzzle.move(move);	
//				System.out.println("biggest move: " + tempPuzzle.biggestTile());
				if (biggestTile(tempPuzzle.getState()) > highestTileMovie) {
					highestTileMovie = biggestTile(tempPuzzle.getState());
					bestMove = move;
				}
			}
			
			puz.move(bestMove);
			puzCopy.setState(puz.getState());
			System.out.println("Moving " + bestMove);
			puz.printState(puz.getState());
			
			if (puz.checkLose()) {
				System.out.println("Game Lost in " + totalMoves + " " + "moves" );
				break;
			}
		}
		if (puz.checkWin()) {
			System.out.println("Game Won in " + totalMoves + " " + "moves" );
		}
		
	}
	
	public int biggestTile(int[][] state){
		int sum = 0;
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				sum += state[i][j];
			}
		}
		return sum;
	}

}
