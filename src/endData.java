
public class endData {
	int moves;
	String end;

	// initializes state
	public endData(int moves, String end){
		this.moves = moves;
		this.end = end;
	}	

	public int getMoves(){
		return this.moves;
	}

	public String getEnd(){
		return this.end;
	}
}