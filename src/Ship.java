public class Ship {

	private String name;
	private int length;
	private boolean horizontal;
	private int tilesLeft;

	public Ship(String name, int length, boolean horizontal) {
		this.name = name;
		this.length = length;
		this.horizontal = horizontal;
		this.tilesLeft = this.length;
	}

	public int getLength() {
		return this.length;
	}

	public boolean getHorizontal() {
		return this.horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public void bomb() {
		this.tilesLeft--;
	}

	public boolean isDestroyed() {
		return this.tilesLeft <= 0;
	}

}
