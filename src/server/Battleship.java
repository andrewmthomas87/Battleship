package server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Battleship
{

	private Set<Client>       clients = new HashSet<>();
	private Map<String, Ship> ships   = new HashMap<>();

	public Battleship()
	{
		this.ships.put( "Carrier", new Ship( "Carrier", 5, true ) );
		this.ships.put( "Battleship", new Ship( "Battleship", 4, true ) );
		this.ships.put( "Cruiser", new Ship( "Cruiser", 3, true ) );
		this.ships.put( "Submarine", new Ship( "Submarine", 3, true ) );
		this.ships.put( "Destroyer", new Ship( "Destroyer", 2, true ) );
	}

	public boolean addClient( Socket socket ) throws IOException
	{
		if ( this.clients.size() < 2 )
		{
			Client client = new Client( this, socket );
			this.clients.add( client );
			new Thread( client ).start();

			return true;
		}

		return false;
	}

	public void removeClient( Client client )
	{
		this.clients.remove( client );

		for ( Client otherClient : this.clients )
		{
			otherClient.reset();
		}
	}

	public void handleMessage( Client client, String type, String body )
	{
		System.out.println( " [" + client + "] " + type + "\t\t\t" + body );

		switch ( type )
		{
			case "placeShip":
			{
				String[] args = body.split( "," );
				this.placeShip( client, args[ 0 ], Integer.parseInt( args[ 1 ] ), Integer.parseInt( args[ 2 ] ), Boolean.parseBoolean( args[ 3 ] ) );
				break;
			}
			case "connect":
				this.connect( client );
				break;
			case "bomb":
			{
				String[] args = body.split( "," );

				this.bomb( client, Integer.parseInt( args[ 0 ] ), Integer.parseInt( args[ 1 ] ) );
				break;
			}
		}
	}

	private void placeShip( Client client, String shipName, int x, int y, boolean horizontal )
	{
		Ship ship = this.ships.get( shipName );
		if ( ship != null )
		{
			ship = ship.clone();
			ship.setHorizontal( horizontal );
			if ( client.board.canPlaceShip( ship, x, y ) )
			{
				client.board.placeShip( ship, x, y );
				client.send( "log", "Placed " + ship.getName() + " " + ( ship.isHorizontal() ? "horizontally" : "vertically" ) + " at " + x + ", " + y );
				client.send( "placeShip", shipName, x, y, horizontal );
			}
			else
			{
				client.send( "log", "Cannot place " + ship.getName() + " " + ( ship.isHorizontal() ? "horizontally" : "vertically" ) + " at " + x + ", " + y );
			}
		}
	}

	private void connect( Client client )
	{
		client.setReady( true );

		if ( this.clients.size() == 2 && this.getOtherClient( client )
		                                     .isReady() )
		{
			for ( Client otherClient : this.clients )
			{
				otherClient.send( "log", "Starting game" );
				if ( otherClient != client )
				{
					otherClient.send( "ready", true );
				}
			}

			client.send( "ready", false );
		}
		else
		{
			client.send( "log", "Waiting for opponent..." );
		}
	}

	private void bomb( Client client, int x, int y )
	{
		Client otherClient = this.getOtherClient( client );

		switch ( otherClient.board.bomb( x, y ) )
		{
			case 0:
				client.send( "miss", x, y );
				client.send( "log", "Miss" );
				otherClient.send( "log", "Your opponent missed!" );
				break;
			case 1:
				client.send( "hit", x, y );
				client.send( "log", "Hit!" );
				otherClient.send( "bomb", x, y );
				otherClient.send( "log", "Your opponent hit your ship" );
				break;
			case -1:
				client.send( "hit", x, y );
				client.send( "log", "Hit!" );
				client.send( "log", "You sunk your opponent's ship!" );
				otherClient.send( "bomb", x, y );
				otherClient.send( "log", "Your opponent hit and sunk your ship" );
				break;
		}

		if ( otherClient.board.isDone() )
		{
			client.send( "finished", true );
			otherClient.send( "finished", false );
		}
		else
		{
			client.send( "nextTurn", "" );
			otherClient.send( "nextTurn", "" );
		}
	}

	private Client getOtherClient( Client client )
	{
		for ( Client otherClient : this.clients )
		{
			if ( otherClient != client )
			{
				return otherClient;
			}
		}

		return null;
	}

	public static void main( String[] args )
	{
		Battleship battleship = new Battleship();
		try
		{
			new Thread( new Server( battleship ) ).run();
		}
		catch ( IOException exception )
		{
			exception.printStackTrace();
		}
	}

}
