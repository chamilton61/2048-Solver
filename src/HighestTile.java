import java.util.ArrayList;


public class HighestTile {

	ArrayList<String> moves;
	int totalWins;
	int totalLoses;
	
	public HighestTile() {
		moves = new ArrayList<String>();
		totalLoses = 0;
		totalWins = 0;
		
		
		moves.add("up");
		moves.add("down");
		moves.add("right");
		moves.add("left");
		
		int timesToRunGame = 50;
		int totalMovesForAllGames = 0;
		int averageMoves = 0;
		
		for (int i = 0; i < timesToRunGame; i++) {
			totalMovesForAllGames += solve();
		}
		
		averageMoves = totalMovesForAllGames/timesToRunGame;
		
		
		System.out.println("Total Wins: " + totalWins);
		System.out.println("Total Loses: " + totalLoses);
		System.out.println("Average Moves before Win or Loss: " + averageMoves);
		
		
	}
	
	int solve() {

		puzzle puz = new puzzle();
		puzzle tempPuzzle = new puzzle();

//		puz.printState(puz.getState());

		String bestMove = "none";
		int highestTileMovie;
		int totalMoves = 0;
		

		while (!puz.checkWin()) {
			totalMoves++;
			
			
			highestTileMovie = 0;
						for (String move : moves) {
				// store the temp state for each try
				tempPuzzle.setState(puz.getState());
				tempPuzzle.move(move);	
				if (biggestTiles(tempPuzzle.getState()) > highestTileMovie) {
					highestTileMovie = biggestTiles(tempPuzzle.getState());
					bestMove = move;
				}
			}
			
			puz.move(bestMove);
//			System.out.println("Moving " + bestMove);
//			puz.printState(puz.getState());
			
			if (puz.checkLose()) {
				System.out.println("Game Lost in " + totalMoves + " " + "moves" );
//				puz.printState(puz.getState());
				totalLoses++;
				return totalMoves;
			}
		}
		if (puz.checkWin()) {
			System.out.println("Game Won in " + totalMoves + " " + "moves" );
//			puz.printState(puz.getState());
			totalWins++;
			return totalMoves;
		}
		return 0;
		
	}
	
	public int biggestTiles(int[][] state){
		int sum = 0;
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				sum += state[i][j];
			}
		}
		return sum;
	}
	

}
