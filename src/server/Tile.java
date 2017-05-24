package server;

public class Tile
{

	private TileState state;
	private Ship      ship;

	public Tile()
	{
		this( TileState.EMPTY, null );
	}

	public Tile( TileState state, Ship ship )
	{
		this.state = state;
		this.ship = ship;
	}

	public boolean isOccupied()
	{
		return this.ship != null;
	}

	public void setShip( Ship ship )
	{
		this.ship = ship;
		if ( this.ship == null )
		{
			this.state = TileState.EMPTY;
		}
		else
		{
			this.state = TileState.OCCUPIED;
		}
	}

	public Ship bomb()
	{
		if ( this.state == TileState.OCCUPIED )
		{
			this.state = TileState.DESTROYED;
			this.ship.bomb();
		}

		return this.ship;
	}

	public String toString()
	{
		switch ( this.state )
		{
			case OCCUPIED:
				return "█";
			case DESTROYED:
				return "⌫";
			default:
				return "•";
		}
	}

}
