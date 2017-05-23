package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel implements MouseListener {

	private TileListener tileListener;

	private TileState[][] tiles = new TileState[10][10];

	public Board(TileListener tileListener) {
		this.tileListener = tileListener;

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				this.tiles[x][y] = TileState.EMPTY;
			}
		}

		this.addMouseListener(this);
	}

	public void placeShip(Ship ship, int x, int y) {
		for (int i = 0; i < ship.getLength(); i++) {
			if (ship.isHorizontal()) {
				this.tiles[x + i][y] = ship.getTileState();
			}
			else {
				this.tiles[x][y + i] = ship.getTileState();
			}
		}
	}

	public void bomb(int x, int y) {
		if (this.tiles[x][y] != TileState.EMPTY) {
			this.tiles[x][y] = TileState.DESTROYED;
		}
	}

	private Color getColorFromTileState(TileState tileState) {
		switch (tileState) {
			case CARRIER:
				return Color.WHITE;
			case BATTLESHIP:
				return Color.LIGHT_GRAY;
			case SUBMARINE:
				return Color.CYAN;
			case CRUISER:
				return Color.ORANGE;
			case DESTROYER:
				return Color.GREEN;
			case DESTROYED:
				return Color.RED;
			default:
				return Color.BLUE;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = this.getWidth();
		int height = this.getHeight();
		int horizontalOffset, verticalOffset;
		if (width > height) {
			horizontalOffset = (width - height) / 2;
			verticalOffset = 0;
		}
		else {
			verticalOffset = (height - width) / 2;
			horizontalOffset = 0;
		}
		int tileSize = Math.min(width, height) / 10;

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				g.setColor(this.getColorFromTileState(this.tiles[x][y]));
				g.fillRect(horizontalOffset + x * tileSize, verticalOffset + y * tileSize, tileSize, tileSize);
				g.setColor(Color.BLACK);
				g.drawRect(horizontalOffset + x * tileSize, verticalOffset + y * tileSize, tileSize, tileSize);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int width = this.getWidth();
		int height = this.getHeight();
		if (width > height) {
			x -= (width - height) / 2;
		}
		else {
			y -= (height - width) / 2;
		}
		int tileSize = Math.min(width, height) / 10;

		if (x < 0 || y < 0 || x > tileSize * 10 || y > tileSize * 10) {
			return;
		}

		this.tileListener.onTileClick(x / tileSize, y / tileSize);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
