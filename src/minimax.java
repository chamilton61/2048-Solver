import java.util.ArrayList;
import java.awt.Point;

public class minimax {
	puzzle Puzzle;
	// initializes 
	public minimax(puzzle Puzzle){
		this.Puzzle = Puzzle;
	}	

	public int run(){
		String message = "";
		int moveNumber = 0;
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
				Puzzle.move("up");			
			if(move == "LEFT")
				Puzzle.move("left");
			if(move == "DOWN")
				Puzzle.move("down");
			if(move == "RIGHT")
				Puzzle.move("right");
			System.out.println("Move: " + move + " Move Number: " + moveNumber);
			Puzzle.printState(Puzzle.getState());
			moveNumber++;

		}
		System.out.println("You " + message + ".");
		return moveNumber;
	}

	private String getMove(int[][] state){
		simpleData best = new simpleData(10000, state, "");
		simpleData secondBest = new simpleData(10000, state, "");
		ArrayList<simpleData> states = new ArrayList<simpleData>();
		for(int i = 0; i < 4; i++){
			states.add(value(rotate(state, i), i));
			if(states.get(i).getValue() < best.getValue()){
				if(best.getValue() != 10000)
					secondBest = best;
				best = states.get(i);
			} else {
				if( states.get(i).getValue() < secondBest.getValue())
					secondBest = states.get(i);
			}
		}
		System.out.println("BEST: " + best.getMove() + " value: " + best.getValue());
		System.out.println("SECOND: " + secondBest.getMove() + " value: " + secondBest.getValue());
		if(secondBest.getValue() == 10000)
			return best.getMove();
		int bestValue = calculateValue(best);
		int secondValue = calculateValue(secondBest);
		if(bestValue > secondValue)
			return best.getMove();
		return secondBest.getMove();
	}


	private int calculateValue(simpleData state){
		ArrayList<positionValue> highs = getHighest(state.getState(), 5);
		int value = 0;
		for(int i = 0; i<highs.size(); i++){
			
			positionValue temp = highs.get(i);
			if(i == 0)
				value += corner(temp);
			if(temp.getX() == 0 || temp.getX() == 3 || temp.getY() == 0 || temp.getY() == 3)
				value += 2;
			value += valueOf(getAbove(state.getState(), temp.getX(), temp.getY()), temp.getValue());
			value += valueOf(getBelow(state.getState(), temp.getX(), temp.getY()), temp.getValue());
			value += valueOf(getAfter(state.getState(), temp.getX(), temp.getY()), temp.getValue());
			value += valueOf(getBefore(state.getState(), temp.getX(), temp.getY()), temp.getValue());
			value += isUnique(state.getState(), temp.getValue());
		}
		return value;
	}

	private int isUnique(int[][] state, int tileValue){
		int times = 0;
		for(int i =0; i<4; i++){
			for(int j = 0; j<4; j++){
				if(state[i][j] == tileValue)
					times++;
			}
		}
		if(times == 1)
			return 5;
		return 0;
	}

	// returns 
	private int valueOf(int tileValue, int compare){
		if(tileValue == compare)
			return 3;
		if(tileValue == (compare/2))
			return 1;
		return 0;
	}

	// returns 1 if this value is in a corner, 0 otherwise
	private int corner(positionValue tile){
		int x = tile.getX();
		int y = tile.getY();
		if((x == 0 && y == 0) || (x == 0 && y == 3) || (x == 3 && y == 0) || (x == 3 && y == 3))
			return 2;
		return 0;
	}

	// returns in an ArrayList the howMany highest tiles
	private ArrayList<positionValue> getHighest(int[][] state, int howMany){
		ArrayList<positionValue> highest = new ArrayList<positionValue>();
		for(int i = 0; i<4; i++){
			for(int j = 0; j<4; j++){
				if(highest.size() < howMany)
					highest.add(new positionValue(state[i][j], i, j));
				else {
					int lowest = getLowest(highest, state[i][j]);
					if(lowest != -1){
						highest.remove(lowest);
						highest.add(new positionValue(state[i][j], i, j));
					}
				}
			}
		}
		int highValue = 0;
		for(int j = 0; j<howMany; j++){
			if(highest.get(j).getValue() > highValue){
				positionValue temp = highest.get(0);
				highest.set(0, highest.get(j));
				highest.set(j, temp);
				highValue = highest.get(0).getValue();
			}
		}
		return highest;
	}

	// returns the position in the array if the value is higher than the lowest, else it returns a -1
	private int getLowest(ArrayList<positionValue> arr, int value){
		for(int i = 0; i< arr.size(); i++){
			if(value > arr.get(i).getValue())
				return i;
		}
		return -1;
	}

	// returns a data structure with the optimal move of the opponent put in the state set
	private simpleData value(int[][] state, int direction){
		String move = "";
		if(direction == 0)
			move = "UP";
		if(direction == 1)
			move = "LEFT";
		if(direction == 2)
			move = "DOWN";
		if(direction == 3)
			move = "RIGHT";
		int[][] temp = pushUp(state);
		if(!compareArray(temp, state)){
			return getOptimalMove(temp, move);
		}
		else
			return new simpleData(10000, state, "");
			
	}

	// returns a modified version of the state set with the predicted best move the opponent can make
	private simpleData getOptimalMove(int[][] state, String move){
		ArrayList<Point> zeroes = getZeroes(state);
		int max = 0;
		int type = 0;
		int col = 0;
		int row = 0;
		for(int i = 0; i<zeroes.size(); i++){
			int current = getColumnValue(state, zeroes.get(i).x, zeroes.get(i).y, 2) + getRowValue(state, zeroes.get(i).x, zeroes.get(i).y, 2);
			int temp = getColumnValue(state, zeroes.get(i).x, zeroes.get(i).y, 4) + getRowValue(state, zeroes.get(i).x, zeroes.get(i).y, 4);
			if(current > max || temp > max){
				col = zeroes.get(i).x;
				row = zeroes.get(i).y;
				if(current > temp){
					type = 2;
					max = current;
				} else {
					type = 4;
					max = temp;
				}
					
			}
		}
		System.out.println("Opponent's optimal move at Row: " + col + " and Column: " + row + " with expected value of: " + max + " and of type: " + type + " State: " + state[col][row]);
		int[][] toReturn = copy(state);
		toReturn[col][row] = type;
		simpleData req = new simpleData(max, toReturn, move);
		return req;
	}	

	// returns the value of the given row (taken from the added values before and after this tile)
	private int getRowValue(int[][] state, int row, int column, int type){
		int before = getBefore(state, row, column);
		int after = getAfter(state, row, column);
		if(before == type)
			before = 0;
		if(after == type)
			after = 0;
		return before + after;
	}

	// returns the value of the column (value is taken by added the above value)
	private int getColumnValue(int[][] state, int row, int column, int type){	
		int above = 0;
		int roww = 0;
		int col = 0;
		above = getAbove(state, row, column);
		if(above == type)
			return 0;
		return above;
	}

	// returns the next value above the current tile, else returns 0
	private int getAbove(int[][] state, int row, int column){
		int above = 0;
		for(int i = 0; i<4; i++){
			if(row == i)
				break;
			if(state[i][column] != 0){
				above = state[i][column];
			}
		}
		return above;
	}

	// returns the next value below the selected tile, else returns 0
	private int getBelow(int[][] state, int row, int column){
		int below = 0;
		for(int i = row+1; i<4; i++){
			if(state[i][column] == 0)
				continue;
			else{
				return state[i][column];
			}
		}
		return below;
	}

	// returns the next value befroe the selected tile, else returns 0
	private int getBefore(int[][] state, int row, int column){
		int before = 0;
		for(int j = 0; j<4; j++){
			if(j == column)
				break;
			if(state[row][j] == 0)
				continue;
			else
				before = state[row][j];		
		}
		return before;
	}

	// returns the next value after the selected tile, else returns 0
	private int getAfter(int[][] state, int row, int column){
		int after = 0;
		for(int k = column+1; k<4; k++){
			if(state[row][k] == 0)
				continue;
			else{
				after = state[row][k];
				break;
			}
		}
		return after;
	}

	// returns all the zeroes in an array list made up of points (the zeroes positions)
	private ArrayList<Point> getZeroes(int[][] state){
		ArrayList<Point> zeroes = new ArrayList<Point>();
		for(int i = 0; i<4; i++){
			for(int j = 0; j<4; j++){
				if(state[i][j] == 0)
					zeroes.add(new Point(i,j));
			}
		}
		return zeroes;
	}

	// returns a modified state rotated times about of times
	private int[][] rotate(int[][] state, int times){
		int[][] rotate = new int[4][4];
		int[][] temp = copy(state);
		for(int i = 0; i < times; i++){
			rotate[0][0] = temp[3][0];
			rotate[1][0] = temp[3][1];
			rotate[2][0] = temp[3][2];
			rotate[3][0] = temp[3][3];

			rotate[0][1] = temp[2][0];
			rotate[1][1] = temp[2][1];
			rotate[2][1] = temp[2][2];
			rotate[3][1] = temp[2][3];

			rotate[0][2] = temp[1][0];
			rotate[1][2] = temp[1][1];
			rotate[2][2] = temp[1][2];
			rotate[3][2] = temp[1][3];

			rotate[0][3] = temp[0][0];
			rotate[1][3] = temp[0][1];
			rotate[2][3] = temp[0][2];
			rotate[3][3] = temp[0][3];
			temp = copy(rotate);
		}
		return temp;
	}

	// returns a modified state of all the values pushed upward according to the rules of 2048
	public int[][] pushUp(int[][] state){
		int[][] stated = copy(state);
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				movePiece: for (int k = i-1; k >= 0; k--){
					if (stated[k][j]!=stated[i][j] && stated[k][j]!= 0){
						break movePiece;
					}
					if (stated[k][j]==stated[i][j]){
						stated[i][j]=0;
						stated[k][j]*=2;
						break movePiece;
					}
				}
				for (int l = 0; l <= i-1; l++){
					if (stated[l][j]==0){
						stated[l][j] = stated[i][j];
						stated[i][j] = 0;
						break;
					}
				}
			}
		}
		return stated;
	}

	// compares two different 2d arrays returns false if they are different, true otherwise
	public boolean compareArray(int[][] x, int[][] y){
		for (int i = 0; i < x.length; i++){
			for (int j = 0; j < x[i].length; j++){
				if (x[i][j]!=y[i][j]){
					return false;
				}
			}
		}
		return true;
	}

	// copies on 2d array to another new one
	public int[][] copy(int[][] x){
		int[][] temp = new int[4][4];
		for (int i= 0; i<4; i++){
			for (int j = 0; j < 4; j++){
				temp[i][j] = x[i][j];
			}
		}
		return temp;
	}
}