
public class puzzleClassTester {

	public static void main(String[] args) {
		puzzle puz = new puzzle();
		puz.printState(puz.getState());
		puz.move("up");
		puz.printState(puz.getState());
		puz.move("right");
		puz.printState(puz.getState());
		puz.move("down");
		puz.printState(puz.getState());
	}

}
