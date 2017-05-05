public class Tile {

	private TileState state;
	private Ship ship;

	public Tile() {
		this(TileState.EMPTY, null);
	}

	public Tile(TileState state, Ship ship) {
		this.state = state;
		this.ship = ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
		if (this.ship == null) {
			this.state = TileState.EMPTY;
		}
		else {
			this.state = TileState.OCCUPIED;
		}
	}

	public void bomb() {
		if (this.state == TileState.OCCUPIED) {
			this.state = TileState.DESTROYED;
		}
	}

	public String toString() {
		switch (this.state) {
			case OCCUPIED:
				return "█";
			case DESTROYED:
				return "⌫";
			default:
				return "•";
		}
	}

}
