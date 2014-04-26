
public class positionValue {
	int value;
	int x;
	int y;

	// initializes state
	public positionValue(int value, int x, int y){
		this.value = value;
		this.x = x;
		this.y = y;
	}	

	public int getValue(){
		return this.value;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}
}