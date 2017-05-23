package server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Battleship {

	private Set<Client> clients = new HashSet<>();
	private Map<String, Ship> ships = new HashMap<>();

	public Battleship() {
		this.ships.put("Carrier", new Ship("Carrier", 5, true));
		this.ships.put("Battleship", new Ship("Battleship", 4, true));
		this.ships.put("Cruiser", new Ship("Cruiser", 3, true));
		this.ships.put("Submarine", new Ship("Submarine", 3, true));
		this.ships.put("Destroyer", new Ship("Destroyer", 2, true));
	}

	public boolean addClient(Socket socket) throws IOException {
		if (this.clients.size() < 2) {
			Client client = new Client(this, socket);
			client.board = new Board();
			this.clients.add(client);
			new Thread(client).start();

			if (this.clients.size() == 2) {
				// send message
			}
			return true;
		}

		return false;
	}

	public void removeClient(Client client) {
		this.clients.remove(client);

		for (Client otherClient : this.clients) {
			otherClient.board = new Board();
		}

		// send message
	}

	public void handleMessage(Client client, String type, String body) {
		switch (type) {
			case "placeShip":
				String[] args = body.split(",");
				this.placeShip(client, args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
				break;
		}
	}

	private void placeShip(Client client, String shipName, int x, int y, boolean horizontal) {
		Ship ship = this.ships.get(shipName);
		if (ship != null) {
			ship = ship.clone();
			ship.setHorizontal(horizontal);
			if (client.board.canPlaceShip(ship, x, y)) {
				client.board.placeShip(ship, x, y);
				client.send("log", "Placed " + ship.getName() + " " + (ship.isHorizontal() ? "horizontally" : "vertically") + " at " + x + ", " + y);
				client.send("placeShip", shipName + "," + x + "," + y);
			}
			else {
				client.send("log", "Cannot place " + ship.getName() + " " + (ship.isHorizontal() ? "horizontally" : "vertically") + " at " + x + ", " + y);
			}
		}
	}

}
