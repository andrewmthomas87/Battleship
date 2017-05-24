package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable
{

	private ServerSocket server;
	private Battleship   battleship;

	public Server( Battleship battleship ) throws IOException
	{
		this.server = new ServerSocket();
		this.battleship = battleship;
	}

	@Override
	public void run()
	{
		try
		{
			this.server.bind( new InetSocketAddress( "0.0.0.0", 6789 ) );

			while ( true )
			{
				try
				{
					Socket socket = this.server.accept();
					if ( !this.battleship.addClient( socket ) )
					{
						socket.close();
					}
				}
				catch ( IOException exception )
				{
					exception.printStackTrace();
				}
			}
		}
		catch ( IOException exception )
		{
			exception.printStackTrace();
		}
	}

}
