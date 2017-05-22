package server;

public class Battleship {

	public static void main(String[] args) {
		Board board = new Board();
		Ship ship1 = new Ship("ship1", 5, true);
		board.placeShip(ship1, 2, 1);
		System.out.println(board);
		board.bomb(0, 0);
		System.out.println(board);
		board.bomb(2, 1);
		board.bomb(3, 1);
		board.bomb(4, 1);
		board.bomb(5, 1);
		board.bomb(6, 1);
		System.out.println(board);
	}

}
