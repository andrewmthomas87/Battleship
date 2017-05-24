package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Battleship implements TileListener, OpponentTileListener, PlayAgainListener
{

	private Client client = new Client( this );

	private JFrame frame = new JFrame( "Battleship" );
	private JPanel contentPane;
	private Board         board         = new Board( this );
	private OpponentBoard opponentBoard = new OpponentBoard( this );
	private Console       console       = new Console();

	private GameState gameState = GameState.PLACING;

	private Map<String, Ship> ships = new HashMap<>();

	// PLACING
	private List<Ship> shipsList = new ArrayList<>();
	private Iterator<Ship> shipIterator;
	private ShipPlacement  shipPlacement;

	// PLAYING
	private BoardToggler boardToggler;
	private boolean bombed = false;

	// POSTGAME
	private Postgame postgame = new Postgame( this );

	public Battleship() throws IOException
	{
		new Thread( this.client ).start();

		this.ships.put( "Carrier", new Ship( "Carrier", 5, true, TileState.CARRIER ) );
		this.ships.put( "Battleship", new Ship( "Battleship", 4, true, TileState.BATTLESHIP ) );
		this.ships.put( "Cruiser", new Ship( "Cruiser", 3, true, TileState.CRUISER ) );
		this.ships.put( "Submarine", new Ship( "Submarine", 3, true, TileState.SUBMARINE ) );
		this.ships.put( "Destroyer", new Ship( "Destroyer", 2, true, TileState.DESTROYER ) );

		for ( Ship ship : this.ships.values() )
		{
			this.shipsList.add( ship );
		}
		this.shipIterator = this.shipsList.iterator();

		this.shipPlacement = new ShipPlacement();
		this.shipPlacement.setShip( this.shipIterator.next()
		                                             .clone() );
	}

	private void placeShip( String shipName, int x, int y, boolean horizontal )
	{
		Ship ship = this.ships.get( shipName );
		if ( ship != null )
		{
			ship = ship.clone();
			ship.setHorizontal( horizontal );
			this.board.placeShip( ship, x, y );

			if ( this.shipIterator.hasNext() )
			{
				this.shipPlacement.setShip( this.shipIterator.next()
				                                             .clone() );
			}
			else
			{
				this.gameState = GameState.CONNECTING;
				this.renderUI();
				this.connect();
			}
		}
	}

	private void connect()
	{
		this.client.send( "connect", "" );
	}

	private void hit( int x, int y )
	{
		this.opponentBoard.hit( x, y );
		try
		{
			Thread.sleep( 500 );
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	private void miss( int x, int y )
	{
		this.opponentBoard.miss( x, y );
		try
		{
			Thread.sleep( 500 );
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	private void bomb( int x, int y )
	{
		this.board.bomb( x, y );
		try
		{
			Thread.sleep( 1000 );
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	private void reset()
	{
		this.board = new Board( this );
		this.opponentBoard = new OpponentBoard( this );
		this.console = new Console();

		this.gameState = GameState.PLACING;

		this.shipIterator = this.shipsList.iterator();

		this.shipPlacement = new ShipPlacement();
		this.shipPlacement.setShip( this.shipIterator.next()
		                                             .clone() );

		this.renderUI();
	}

	public void handleMessage( String type, String body )
	{
		System.out.println( " [<-] " + type + "\t\t\t" + body );

		switch ( type )
		{
			case "log":
				this.console.println( body );
				break;
			case "placeShip":
			{
				String[] args = body.split( "," );

				this.placeShip( args[ 0 ], Integer.parseInt( args[ 1 ] ), Integer.parseInt( args[ 2 ] ), Boolean.parseBoolean( args[ 3 ] ) );
				break;
			}
			case "ready":
				boolean yourTurn = Boolean.parseBoolean( body );

				this.gameState = GameState.PLAYING;
				this.boardToggler = new BoardToggler( this.board, this.opponentBoard );
				if ( yourTurn )
				{
					this.boardToggler.toggle();
				}
				this.renderUI();
				break;
			case "hit":
			{
				String[] args = body.split( "," );

				this.hit( Integer.parseInt( args[ 0 ] ), Integer.parseInt( args[ 1 ] ) );
				break;
			}
			case "miss":
			{
				String[] args = body.split( "," );

				this.miss( Integer.parseInt( args[ 0 ] ), Integer.parseInt( args[ 1 ] ) );
				break;
			}
			case "bomb":
			{
				String[] args = body.split( "," );

				this.bomb( Integer.parseInt( args[ 0 ] ), Integer.parseInt( args[ 1 ] ) );
				break;
			}
			case "nextTurn":
				this.boardToggler.toggle();
				this.bombed = false;
				break;
			case "finished":
				boolean youWon = Boolean.parseBoolean( body );

				this.postgame.setYouWon( youWon );
				this.gameState = GameState.POSTGAME;
				this.renderUI();
				break;
			case "reset":
				this.reset();
				break;
		}
	}

	public void renderUI()
	{
		this.contentPane.removeAll();

		switch ( this.gameState )
		{
			case PLACING:
				this.contentPane.add( this.board, BorderLayout.CENTER );
				this.contentPane.add( this.console, BorderLayout.EAST );
				this.contentPane.add( this.shipPlacement, BorderLayout.NORTH );
				break;
			case CONNECTING:
				this.contentPane.add( this.board, BorderLayout.CENTER );
				this.contentPane.add( this.console, BorderLayout.EAST );
				break;
			case PLAYING:
				this.contentPane.add( this.boardToggler, BorderLayout.CENTER );
				this.contentPane.add( this.console, BorderLayout.EAST );
				break;
			case POSTGAME:
				this.contentPane.add( this.postgame, BorderLayout.CENTER );
				break;
			case DISCONNECTED:
				this.contentPane.add( new Disconnected(), BorderLayout.CENTER );
				break;
		}

		this.contentPane.revalidate();
	}

	public void show()
	{
		this.contentPane = new JPanel();
		this.contentPane.setLayout( new BorderLayout() );

		this.renderUI();

		this.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.frame.setLocation( new Point( 0, 0 ) );
		this.frame.setSize( new Dimension( 1250, 800 ) );
		this.frame.setContentPane( this.contentPane );
		this.frame.setVisible( true );
	}

	public void stop()
	{
		this.gameState = GameState.DISCONNECTED;
		this.renderUI();
	}

	@Override
	public void onTileClick( int x, int y )
	{
		switch ( this.gameState )
		{
			case PLACING:
				Ship ship = this.shipPlacement.getShip();
				this.client.send( "placeShip", ship.getName(), x, y, ship.isHorizontal() );
				break;
		}
	}

	@Override
	public void onOpponentTileClick( int x, int y )
	{
		if ( !this.opponentBoard.isEmpty( x, y ) )
		{
			this.console.println( "You already bombed that tile" );
		}
		else if ( !this.bombed )
		{
			this.client.send( "bomb", x, y );
			this.bombed = true;
		}
	}

	public static void main( String[] args )
	{
		try
		{
			new Battleship().show();
		}
		catch ( IOException e )
		{
			System.out.println( "Failed to connect to server. Ensure a server is running." );
		}
	}

	@Override
	public void playAgain()
	{
		this.reset();
	}

}
