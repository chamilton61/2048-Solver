
public class greedySolution {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double average = 0;
		for (int i = 0; i < 500; i++){
			average += ((double)attemptPuzzle())/500;
		}
		System.out.println("average: "+average);
	}
	
	public static int attemptPuzzle(){
		puzzle greedy = new puzzle();
		int turnCounter = 0;
		while (!greedy.checkLose()){
			turnCounter ++;
			System.out.println("turn: "+turnCounter);
			greedy.move(chooseMove(greedy));
			greedy.printState(greedy.getState());
		}
		return turnCounter;
	}
	static public int getBlanks(int[][] x){
		int counter = 0;
		for (int i = 0; i <=3; i++){
			for (int j = 0; j <=3; j++){
				if (x[i][j] == 0){
					counter++;
				}
			}
		}
		return counter;
	}

	static public String chooseMove(puzzle x){
		int counter = 0;
		String direction = "";
		if (getBlanks(x.moveUp())>counter && !x.compareArray(x.getState(), x.moveUp())){
			counter = getBlanks(x.moveUp());
			direction = "up";
		}
		if (getBlanks(x.moveLeft())>counter && !x.compareArray(x.getState(), x.moveLeft())){
			counter = getBlanks(x.moveLeft());
			direction = "left";
		}
		if (getBlanks(x.moveDown())>counter && !x.compareArray(x.getState(), x.moveDown())){
			counter = getBlanks(x.moveDown());
			direction = "down";
		}
		if (getBlanks(x.moveRight())>counter && !x.compareArray(x.getState(), x.moveRight())){
			counter = getBlanks(x.moveRight());
			direction = "right";
		}
		return direction;
	}
	
}
