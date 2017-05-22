package client;

import java.awt.*;

public class Ship {

	private String name;
	private int length;
	private boolean horizontal;
	private TileState tileState;

	public Ship(String name, int length, boolean horizontal, TileState tileState) {
		this.name = name;
		this.length = length;
		this.horizontal = horizontal;
		this.tileState = tileState;
	}

	public String getName() {
		return this.name;
	}

	public int getLength() {
		return this.length;
	}

	public boolean isHorizontal() {
		return this.horizontal;
	}

	public TileState getTileState() {
		return this.tileState;
	}

	public Color getColor() {
		switch (this.tileState) {
			case AIRCRAFT_CARRIER:
				return Color.WHITE;
			case BATTLESHIP:
				return Color.LIGHT_GRAY;
			case SUBMARINE:
				return Color.CYAN;
			case CRUISER:
				return Color.ORANGE;
			case DESTROYER:
				return Color.GREEN;
		}

		return Color.BLUE;
	}

}
