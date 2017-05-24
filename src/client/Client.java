package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable
{

	private Battleship battleship;

	private Socket socket = new Socket();
	private PrintWriter out;

	public Client( Battleship battleship ) throws IOException
	{
		this.battleship = battleship;

		this.socket.connect( new InetSocketAddress( "0.0.0.0", 6789 ) );
		this.out = new PrintWriter( this.socket.getOutputStream(), true );
	}

	@Override
	public void run()
	{
		try
		{
			BufferedReader in = new BufferedReader( new InputStreamReader( this.socket.getInputStream() ) );

			String line = in.readLine();
			while ( line != null )
			{
				String[] temp = line.split( ":", 2 );
				if ( temp.length >= 2 )
				{
					this.battleship.handleMessage( temp[ 0 ].trim(), temp[ 1 ].trim() );
				}

				line = in.readLine();
			}

			this.battleship.stop();
		}
		catch ( IOException e )
		{
			this.battleship.stop();
		}
	}

	public void send( String type, Object... args )
	{
		StringBuilder body = new StringBuilder();
		for ( Object arg : args )
		{
			body.append( arg )
			    .append( "," );
		}
		this.send( type, body.deleteCharAt( body.length() - 1 )
		                     .toString() );
	}

	public void send( String type, String body )
	{
		System.out.println( " [->] " + type + "\t\t\t" + body );
		this.out.println( type + ": " + body );
	}

}
