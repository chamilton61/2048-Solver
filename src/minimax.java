

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

	public void testRun(){
		printState(Puzzle.getState());
		printState(rotate(Puzzle.getState(), 1));
		printState(rotate(Puzzle.getState(), 2));
		printState(rotate(Puzzle.getState(), 3));
	}

	private String getMove(int[][] state){
		String move = "UP";
		double bestMove = value(state);
		double leftValue = value(rotate(state, 1));
		double downValue = value(rotate(state, 2));
		double rightValue = value(rotate(state, 3));
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
		state = getOptimalMove(state);

		return 0;
	}

	private int[][] getOptimalMove(int[][] state){


		return state;
	}

	private int[][] rotate(int[][] state, int times){
		int[][] rotate = new int[4][4];
		int temp = 0;
		for(int i = 0; i < times; i++){
			rotate[0][0] = state[3][0];
			rotate[1][0] = state[3][1];
			rotate[2][0] = state[3][2];
			rotate[3][0] = state[3][3];

			rotate[0][1] = state[2][0];
			rotate[1][1] = state[2][1];
			rotate[2][1] = state[2][2];
			rotate[3][1] = state[2][3];

			rotate[0][2] = state[1][0];
			rotate[1][2] = state[1][1];
			rotate[2][2] = state[1][2];
			rotate[3][2] = state[1][3];

			rotate[0][3] = state[0][0];
			rotate[1][3] = state[0][1];
			rotate[2][3] = state[0][2];
			rotate[3][3] = state[0][3];
			state = copy(rotate);
		}
		return state;
		//return pushUp(state);
	}

	public int[][] copy(int[][] x){
		int[][] temp = new int[4][4];
		for (int i= 0; i<4; i++){
			for (int j = 0; j < 4; j++){
				temp[i][j] = x[i][j];
			}
		}
		return temp;
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

	public void printState(int[][] x){
		int[][] state = x.clone();
		for (int i = 0; i < state.length; i++){
			System.out.println("---------");
			for (int j = 0; j < state.length; j++){
				if (j==0){
					System.out.print("|"+state[i][j]+"|");
				}
				else{
					System.out.print(state[i][j]+"|");
				}
			}
			System.out.println();
		}
		System.out.println("---------");
	}
}