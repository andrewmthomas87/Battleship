package server;

public class Board {

	private Tile[][] tiles = new Tile[10][10];
	private int shipCount = 0;

	public Board() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				this.tiles[x][y] = new Tile();
			}
		}
	}

	public void bomb(int x, int y) {
		Ship ship = this.tiles[x][y].bomb();
		if (ship != null) {
			System.out.println("Hit!");
			if (ship.isDestroyed()) {
				this.shipCount--;
				System.out.println(ship.getName() + " destroyed.");
			}
		}
		else {
			System.out.println("Miss.");
		}
	}


	public boolean isDone() {
		return this.shipCount == 0;
	}

	public boolean canPlaceShip(Ship ship, int x, int y) {
		if (ship.isHorizontal()) {
			return x + ship.getLength() - 1 < 10;
		}
		else {
			return y + ship.getLength() - 1 < 10;
		}
	}

	public void placeShip(Ship ship, int x, int y) {
		assert this.canPlaceShip(ship, x, y) : "Invalid ship placement";

		this.shipCount++;
		for (int i = 0; i < ship.getLength(); i++) {
			if (ship.isHorizontal()) {
				this.tiles[x + i][y].setShip(ship);
			}
			else {
				this.tiles[x][y + i].setShip(ship);
			}
		}
	}

	public String toString() {
		String board = "";
		for (int y = 0; y < this.tiles[0].length; y++) {
			for (int x = 0; x < this.tiles.length; x++) {
				board += this.tiles[x][y] + " ";
			}
			board += "\n";
		}
		return board;
	}


}
