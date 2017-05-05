public class Battleship {

	public static void main(String[] args) {
		Board board = new Board();
		Ship ship1 = new Ship("ship1", 5, true);
		board.placeShip(ship1, 2, 1);
		System.out.println(board);
	}

}
