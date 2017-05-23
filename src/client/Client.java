package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {

	private Battleship battleship;

	private Socket socket = new Socket();
	private PrintWriter out;

	public Client(Battleship battleship) throws IOException {
		this.battleship = battleship;

		this.socket.connect(new InetSocketAddress("localhost", 6666));
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

			while (this.socket.isConnected()) {
				String line = in.readLine();
				if (line != null) {
					String[] temp = line.split(":", 2);
					if (temp.length >= 2) {
						this.battleship.handleMessage(temp[0].trim(), temp[1].trim());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String type, String body) {
		this.out.println(type + ": " + body);
	}

}
