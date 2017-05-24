package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable
{

	private Battleship battleship;

	private Socket         socket;
	private BufferedReader in;
	private PrintWriter    out;

	public Board board;
	private boolean ready = false;

	public Client( Battleship battleship, Socket socket ) throws IOException
	{
		this.battleship = battleship;
		this.socket = socket;
		this.in = new BufferedReader( new InputStreamReader( this.socket.getInputStream() ) );
		this.out = new PrintWriter( this.socket.getOutputStream(), true );

		this.board = new Board();
	}

	@Override
	public void run()
	{
		try
		{
			String line = this.in.readLine();

			while ( line != null )
			{
				String[] temp = line.split( ":", 2 );
				if ( temp.length == 2 )
				{
					this.battleship.handleMessage( this, temp[ 0 ].trim(), temp[ 1 ].trim() );
				}

				line = this.in.readLine();
			}
		}
		catch ( IOException e )

		{
			e.printStackTrace();
		}

		this.battleship.removeClient( this );
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
		this.out.println( type + ": " + body );
	}

	public boolean isReady()
	{
		return this.ready;
	}

	public void setReady( boolean ready )
	{
		this.ready = ready;
	}

	public void reset()
	{
		this.board = new Board();
		this.ready = false;

		this.send( "reset", "" );
	}

}
