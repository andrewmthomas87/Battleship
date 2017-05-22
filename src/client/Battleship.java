package client;

import javax.swing.*;
import java.awt.*;

public class Battleship implements TileListener {

	private JFrame frame = new JFrame("Battleship");
	private Board board = new Board(this);
	private Console console = new Console();

	public void show() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(this.board, BorderLayout.CENTER);
		panel.add(this.console, BorderLayout.EAST);
		ShipPlacement shipPlacement = new ShipPlacement();
		shipPlacement.setShip(new Ship("Submarine", 3, false, TileState.SUBMARINE));
		panel.add(shipPlacement, BorderLayout.NORTH);

		this.frame.setLocation(new Point(0, 0));
		this.frame.setSize(new Dimension(1250, 800));
		this.frame.setContentPane(panel);
		this.frame.setVisible(true);
	}

	@Override
	public void onTileClick(int x, int y) {
		this.console.println("Tile clicked at (" + x + ", " + y + ")");
	}

	public static void main(String[] args) {
		new Battleship().show();
	}

}
