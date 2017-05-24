package server;

public class Board
{

	private Tile[][] tiles     = new Tile[ 10 ][ 10 ];
	private int      shipCount = 0;

	public Board()
	{
		for ( int x = 0; x < 10; x++ )
		{
			for ( int y = 0; y < 10; y++ )
			{
				this.tiles[ x ][ y ] = new Tile();
			}
		}
	}

	public int bomb( int x, int y )
	{
		Ship ship = this.tiles[ x ][ y ].bomb();
		if ( ship != null )
		{
			if ( ship.isDestroyed() )
			{
				this.shipCount--;
				return -1;
			}

			return 1;
		}
		else
		{
			return 0;
		}
	}


	public boolean isDone()
	{
		return this.shipCount == 0;
	}

	public boolean canPlaceShip( Ship ship, int x, int y )
	{
		if ( ship.isHorizontal() )
		{
			if ( x + ship.getLength() - 1 < 10 )
			{
				for ( int i = 0; i < ship.getLength(); i++ )
				{
					if ( this.tiles[ x + i ][ y ].isOccupied() )
					{
						return false;
					}
				}

				return true;
			}

			return false;
		}
		else
		{
			if ( y + ship.getLength() - 1 < 10 )
			{
				for ( int i = 0; i < ship.getLength(); i++ )
				{
					if ( this.tiles[ x ][ y + i ].isOccupied() )
					{
						return false;
					}
				}

				return true;
			}

			return false;
		}
	}

	public void placeShip( Ship ship, int x, int y )
	{
		assert this.canPlaceShip( ship, x, y ) : "Invalid ship placement";

		this.shipCount++;
		for ( int i = 0; i < ship.getLength(); i++ )
		{
			if ( ship.isHorizontal() )
			{
				this.tiles[ x + i ][ y ].setShip( ship );
			}
			else
			{
				this.tiles[ x ][ y + i ].setShip( ship );
			}
		}
	}

	public String toString()
	{
		String board = "";
		for ( int y = 0; y < this.tiles[ 0 ].length; y++ )
		{
			for ( int x = 0; x < this.tiles.length; x++ )
			{
				board += this.tiles[ x ][ y ] + " ";
			}
			board += "\n";
		}
		return board;
	}


}
