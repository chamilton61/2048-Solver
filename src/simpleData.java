
public class simpleData {
	int[][] state;
	int value;
	String move;

	// initializes state
	public simpleData(int value, int[][] state, String move){
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
		return this.move;
	}
}