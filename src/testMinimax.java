
public class testMinimax {

	public static void main(String[] args) {
		minimax mini; 
		simpleData[] arr = new simpleData[50];
		double average = 0;
		int win = 0;
		for(int i = 0; i<50; i++){
			mini = new minimax(new puzzle());
			simpleData temp = mini.run();
			arr[i] = temp;
			average += temp.getValue();
			if(temp.getMove() == "WIN")
				win++;
		}
		average = average/50.0;
		System.out.println("Average: " + average + " Win rate: " + win + "/" + 50);
		for(int i = 0; i < 50; i++){
			System.out.print(" Moves: " + arr[i].getValue() + " " + arr[i].getMove());
		}
	}

}
