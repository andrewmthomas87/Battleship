public class Board {

	private Tile[][] tiles = new Tile[10][10];

	public Board() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				this.tiles[x][y] = new Tile();
			}
		}
	}

	public boolean canPlaceShip(Ship ship, int x, int y) {
		if (ship.getHorizontal()) {
			return x + ship.getLength() - 1 < 10;
		}
		else {
			return y + ship.getLength() - 1 < 10;
		}
	}

	public void placeShip(Ship ship, int x, int y) {
		assert this.canPlaceShip(ship, x, y) : "Invalid ship placement";

		for (int i = 0; i < ship.getLength(); i++) {
			if (ship.getHorizontal()) {
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
