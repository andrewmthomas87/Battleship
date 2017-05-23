package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	private ServerSocket server;

	public Server() throws IOException {
		this.server = new ServerSocket();
	}

	@Override
	public void run() {
		try {
			this.server.bind(new InetSocketAddress("localhost", 6666));

			while (true) {
				try {
					Socket socket = this.server.accept();
					new Thread(new Client(socket)).start();
				}
				catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new Thread(new Server()).run();
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}
