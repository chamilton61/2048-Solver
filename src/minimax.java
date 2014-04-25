

public class minimax {
	puzzle Puzzle;
	// initializes 
	public minimax(puzzle Puzzle){
		this.Puzzle = Puzzle;
	}	

	public void run(){
		String message = "";
		while(true){
			if(Puzzle.checkWin()){
				message = "WIN";
				break;
			}
			if (Puzzle.checkLose()){
				message = "LOSE";
				break;
			}
			String move = getMove(Puzzle.getState());
			if(move == "UP")
				Puzzle.moveUp();
			if(move == "LEFT")
				Puzzle.moveLeft();
			if(move == "DOWN")
				Puzzle.moveDown();
			if(move == "RIGHT")
				Puzzle.moveRight();
		}
	}

	private String getMove(int[][] state){
		String move = "UP";
		double bestMove = value(state);
		double leftValue = value(rotateLeft(state));
		double downValue = value(rotateLeftTwice(state));
		double rightValue = value(rotateRight(state));
		if ( leftValue > bestMove ){
			bestMove = leftValue;
			move = "LEFT";
		}
		if ( downValue > bestMove ){
			bestMove = downValue;
			move = "DOWN";
		}
		if ( rightValue > bestMove )
			move = "RIGHT";
		return move;
	}

	private double value(int[][] state){

		return 0;
	}

	private int[][] rotateLeft(int[][] state){
		int[][] left = new int[3][3];
		left[0][0] = state[0][2];
		left[0][1] = state[1][2];
		left[0][2] = state[2][2];

		left[1][0] = state[0][1];
		left[1][1] = state[1][1];
		left[1][2] = state[2][1];

		left[2][0] = state[0][0];
		left[2][1] = state[1][0];
		left[2][2] = state[2][0];
		return pushUp(left);
	}

	private int[][] rotateLeftTwice(int[][] state){
		int[][] upside = new int[3][3];
		upside[0][0] = state[2][2];
		upside[0][1] = state[2][1];
		upside[0][2] = state[2][0];

		upside[1][0] = state[1][2];
		upside[1][1] = state[1][1];
		upside[1][2] = state[1][0];

		upside[2][0] = state[0][2];
		upside[2][1] = state[0][1];
		upside[2][2] = state[0][0];
		return pushUp(upside);
	}

	private int[][] rotateRight(int[][] state){
		int[][] right = new int[3][3];
		right[0][0] = state[2][0];
		right[0][1] = state[1][0];
		right[0][2] = state[0][0];

		right[1][0] = state[2][1];
		right[1][1] = state[1][1];
		right[1][2] = state[0][1];

		right[2][0] = state[2][2];
		right[2][1] = state[1][2];
		right[2][2] = state[0][2];
		return pushUp(right);
	}

	public int[][] pushUp(int[][] state){
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				movePiece: for (int k = i-1; k >= 0; k--){
					if (state[k][j]!=state[i][j] && state[k][j]!= 0){
						break movePiece;
					}
					if (state[k][j]==state[i][j]){
						state[i][j]=0;
						state[k][j]*=2;
						break movePiece;
					}
				}
				for (int l = 0; l <= i-1; l++){
					if (state[l][j]==0){
						state[l][j] = state[i][j];
						state[i][j] = 0;
						break;
					}
				}
			}
		}

		return state;
	}

}