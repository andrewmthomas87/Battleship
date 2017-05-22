package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler implements Runnable {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public SocketHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				String line = this.in.readLine();
				System.out.println(" [" + this.socket.getChannel() + "] " + line);
			}
			catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

}
