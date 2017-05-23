package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

	private Battleship battleship;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public Board board;

	public Client(Battleship battleship, Socket socket) throws IOException {
		this.battleship = battleship;
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		while (this.socket.isConnected()) {
			try {
				String line = this.in.readLine();

				String[] temp = line.split(":", 2);
				if (temp.length == 2) {
					this.battleship.handleMessage(this, temp[0].trim(), temp[1].trim());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.battleship.removeClient(this);
	}

	public void send(String type, String body) {
		this.out.println(type + ": " + body);
	}

}
