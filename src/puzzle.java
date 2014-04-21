import java.util.Random;


public class puzzle {
	public int[][] state;

	// initializes state
	public puzzle(){
		state = new int[4][4];
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				state[i][j]=0;
			}
		}
		Random rand = new Random();
		int X1 = rand.nextInt(3);
		int Y1 = rand.nextInt(3);
		if (rand.nextDouble() < .90){
			state[X1][Y1] = 2;
		}
		else{
			state[X1][Y1] = 4;
		}
		int X2 = rand.nextInt(3);
		int Y2 = rand.nextInt(3);
		while (X2 == X1 && Y2 == Y1){
			X2 = rand.nextInt(3);
			Y2 = rand.nextInt(3);
		}
		if (rand.nextDouble() < .90){
			state[X2][Y2] = 2;
		}
		else{
			state[X2][Y2] = 4;
		}
	}	
	public boolean checkWin(){
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				if (state[i][j] == 2048){
					return true;
				}
			}
		}
		return false;
	}
	public int[][] getState(){
		return state;
	}
	public void setState(int[][] x){
		state = x;
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
	public boolean checkLose(){
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				if (state[i][j]==0){
					return false;
				}
			}
		}
		if (state != moveUp())
			return false;
		if (state != moveDown())
			return false;
		if (state != moveLeft())
			return false;
		if (state != moveRight())
			return false;
		return true;
	}
	public boolean move(String str){
		if (str.equals("up")){
			if (!compareArray(moveUp(),state)){
				state = moveUp();
				generateTile();
				return true;
			}
			else{
				return false;
			}
		}
		else if (str.equals("down")){
			if (!compareArray(moveDown(),state)){
				state = moveDown();
				generateTile();
				return true;
			}
			else
				return false;
		}
		else if (str.equals("left")){
			if (!compareArray(moveLeft(),state)){
				state = moveLeft();
				generateTile();
				return true;
			}
			else
				return false;
		}
		else if (str.equals("right")){
			if (!compareArray(moveRight(),state)){
				state = moveRight();
				generateTile();
				return true;
			}
			else
				return false;
		}
		else{
			System.out.println("invalid input: use up, down, left, or right as input");
			return false;
		}
	}
	public boolean move(String str, int x, int y, int v){
		if (str.equals("up")){
			if (!compareArray(moveUp(),state)){
				state = moveUp();
				generateTile(x,y,v);
				return true;
			}
			else{
				return false;
			}
		}
		else if (str.equals("down")){
			if (!compareArray(moveDown(),state)){
				state = moveDown();
				generateTile(x,y,v);
				return true;
			}
			else
				return false;
		}
		else if (str.equals("left")){
			if (!compareArray(moveLeft(),state)){
				state = moveLeft();
				generateTile(x,y,v);
				return true;
			}
			else
				return false;
		}
		else if (str.equals("right")){
			if (!compareArray(moveRight(),state)){
				state = moveRight();
				generateTile(x,y,v);
				return true;
			}
			else
				return false;
		}
		else{
			System.out.println("invalid input: use up, down, left, or right as input");
			return false;
		}
	}
	public int[][] moveUp(){ 
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
	public int[][] moveDown(){
		int[][] stated = copy(state);
		for (int i = 3; i >= 0; i--){
			for (int j = 0; j < 4; j++){
				movePiece: for (int k = i+1; k <= 3; k++){
					if (stated[k][j]!=stated[i][j] && stated[k][j]!= 0){
						break movePiece;
					}
					if (stated[k][j]==stated[i][j]){
						stated[i][j]=0;
						stated[k][j]*=2;
						break movePiece;
					}

				}
				for (int l = 3; l >= i+1; l--){
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
	public int[][] moveLeft(){
		int[][] stated = copy(state);
		for (int j = 0; j < 4; j++){
			for (int i = 0; i < 4; i++){
				movePiece: for (int k = j-1; k >= 0; k--){
					if (stated[i][k]!=stated[i][j] && stated[i][k]!= 0){
						break movePiece;
					}
					if (stated[i][k]==stated[i][j]){
						stated[i][j]=0;
						stated[i][k]*=2;
						break movePiece;
					}
				}
			for (int l = 0; l <= j-1; l++){
				if (stated[i][l]==0){
					stated[i][l] = stated[i][j];
					stated[i][j] = 0;
					break;
				}
			}
			}
		}
		return stated;
	}
	public int[][] moveRight(){

		int[][] stated = copy(state);
		for (int j = 3; j >= 0; j--){
			for (int i = 0; i < 4; i++){
				movePiece: for (int k = j+1; k <= 3; k++){
					if (stated[i][k]!=stated[i][j] && stated[i][k]!= 0){
						break movePiece;
					}
					if (stated[i][k]==stated[i][j]){
						stated[i][j]=0;
						stated[i][k]*=2;
						break movePiece;
					}
				}
				for (int l = 3; l >= j+1; l--){
					if (stated[i][l]==0){
						stated[i][l] = stated[i][j];
						stated[i][j] = 0;
						break;
					}
				}
			}
		}
		return stated;
	}
	public void generateTile(){
		Random rand = new Random();
		int X2 = rand.nextInt(3);
		int Y2 = rand.nextInt(3);
		while (state[X2][Y2] != 0){
			X2 = rand.nextInt(3);
			Y2 = rand.nextInt(3);
		}
		if (rand.nextDouble() < .90){
			state[X2][Y2] = 2;
		}
		else{
			state[X2][Y2] = 4;
		}
	}
	public void generateTile(int x, int y, int v){
		state[x][y] = v;
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
}
