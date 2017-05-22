package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {

	private Socket socket;

	@Override
	public void run() {
		this.socket = new Socket();
		try {
			this.socket.connect(new InetSocketAddress("localhost", 6666));
			PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
			out.println("Fuck");
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}
