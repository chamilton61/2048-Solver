
public class simpleData {
	int[][] state;
	int value;
	int move;

	// initializes state
	public simpleData(int value, int[][] state, int move){
		this.value = value;
		this.state = state;
		this.move = move;
	}	

	public int getValue(){
		return this.value;
	}

	public int[][] getState(){
		return this.state;
	}

	public String getMove(){
		if(this.move == 0)
			return "UP";
		if(this.move == 1)
			return "LEFT";
		if(this.move == 2)
			return "DOWN";
		if(this.move == 3)
			return "RIGHT";
		return "";
	}

	public int move(){
		return this.move;
	}
}