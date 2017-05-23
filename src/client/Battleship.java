package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Battleship implements TileListener {

	private Client client = new Client(this);

	private JFrame frame = new JFrame("Battleship");
	private JPanel contentPane;
	private Board board = new Board(this);
	private Console console = new Console();

	private GameState gameState = GameState.PLACING;

	// PLACING
	private List<Ship> ships = Arrays.asList(new Ship("Carrier", 5, true, TileState.CARRIER), new Ship("Battleship", 4, true, TileState.BATTLESHIP), new Ship("Cruiser", 3, true, TileState.CRUISER), new Ship("Submarine", 3, true, TileState.SUBMARINE), new Ship("Destroyer", 2, true, TileState.DESTROYER));
	private Iterator<Ship> shipIterator;
	private ShipPlacement shipPlacement;

	public Battleship() throws IOException {
		new Thread(this.client).start();

		this.shipIterator = this.ships.iterator();
		this.shipPlacement = new ShipPlacement();
		this.shipPlacement.setShip(this.shipIterator.next());
	}

	public void handleMessage(String type, String body) {
		System.out.println(type);

		switch (this.gameState) {
			case PLACING:
				switch (type) {

				}
				break;
		}
	}

	public void show() {
		this.contentPane = new JPanel();
		this.contentPane.setLayout(new BorderLayout());

		this.renderUI();

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocation(new Point(0, 0));
		this.frame.setSize(new Dimension(1250, 800));
		this.frame.setContentPane(this.contentPane);
		this.frame.setVisible(true);
	}

	public void renderUI() {
		this.contentPane.removeAll();

		switch (this.gameState) {
			case PLACING:
				this.contentPane.add(this.board, BorderLayout.CENTER);
				this.contentPane.add(this.console, BorderLayout.EAST);
				this.contentPane.add(this.shipPlacement, BorderLayout.NORTH);
				break;
			case CONNECTING:
				break;
			case PLAYING:
				break;
			case POSTGAME:
				break;
		}

		this.contentPane.revalidate();
	}

	@Override
	public void onTileClick(int x, int y) {
		switch (this.gameState) {
			case PLACING:
				this.client.send("placeShip", x + "," + y);
				break;
		}
	}

	public static void main(String[] args) {
		try {
			new Battleship().show();
		}
		catch (IOException e) {
			System.out.println("Failed to connect to server. Ensure a server is running.");
		}
	}

}
