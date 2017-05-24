package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OpponentBoard extends JPanel implements MouseListener
{

	private OpponentTileListener tileListener;

	private OpponentTileState[][] tiles = new OpponentTileState[ 10 ][ 10 ];

	public OpponentBoard( OpponentTileListener tileListener )
	{
		this.tileListener = tileListener;

		for ( int x = 0; x < 10; x++ )
		{
			for ( int y = 0; y < 10; y++ )
			{
				this.tiles[ x ][ y ] = OpponentTileState.EMPTY;
			}
		}

		this.addMouseListener( this );
	}

	public boolean isEmpty( int x, int y )
	{
		return this.tiles[ x ][ y ] == OpponentTileState.EMPTY;
	}

	public void miss( int x, int y )
	{
		this.tiles[ x ][ y ] = OpponentTileState.MISS;

		this.repaint();
	}

	public void hit( int x, int y )
	{
		this.tiles[ x ][ y ] = OpponentTileState.HIT;

		this.repaint();
	}

	private Color getColorFromTileState( OpponentTileState tileState )
	{
		switch ( tileState )
		{
			case HIT:
				return Color.RED;
			case MISS:
				return Color.WHITE;
			default:
				return Color.BLUE;
		}
	}

	@Override
	protected void paintComponent( Graphics g )
	{
		super.paintComponent( g );

		int width  = this.getWidth();
		int height = this.getHeight();
		int horizontalOffset, verticalOffset;
		if ( width > height )
		{
			horizontalOffset = ( width - height ) / 2;
			verticalOffset = 0;
		}
		else
		{
			verticalOffset = ( height - width ) / 2;
			horizontalOffset = 0;
		}
		int tileSize = Math.min( width, height ) / 10;

		for ( int x = 0; x < 10; x++ )
		{
			for ( int y = 0; y < 10; y++ )
			{
				g.setColor( this.getColorFromTileState( OpponentTileState.EMPTY ) );
				g.fillRect( horizontalOffset + x * tileSize, verticalOffset + y * tileSize, tileSize, tileSize );
				g.setColor( Color.BLACK );
				g.drawRect( horizontalOffset + x * tileSize, verticalOffset + y * tileSize, tileSize, tileSize );

				OpponentTileState tileState = this.tiles[ x ][ y ];
				if ( tileState != OpponentTileState.EMPTY )
				{
					g.setColor( this.getColorFromTileState( this.tiles[ x ][ y ] ) );
					g.fillOval( (int) Math.round( horizontalOffset + ( x + 0.15 ) * tileSize ), (int) Math.round( verticalOffset + ( y + 0.15 ) * tileSize ), (int) Math.round( tileSize * 0.7 ), (int) Math.round( tileSize * 0.7 ) );
				}
			}
		}
	}

	@Override
	public void mouseClicked( MouseEvent e )
	{
		int x      = e.getX();
		int y      = e.getY();
		int width  = this.getWidth();
		int height = this.getHeight();
		if ( width > height )
		{
			x -= ( width - height ) / 2;
		}
		else
		{
			y -= ( height - width ) / 2;
		}
		int tileSize = Math.min( width, height ) / 10;

		if ( x < 0 || y < 0 || x > tileSize * 10 || y > tileSize * 10 )
		{
			return;
		}

		this.tileListener.onOpponentTileClick( x / tileSize, y / tileSize );
	}

	@Override
	public void mousePressed( MouseEvent e )
	{

	}

	@Override
	public void mouseReleased( MouseEvent e )
	{

	}

	@Override
	public void mouseEntered( MouseEvent e )
	{

	}

	@Override
	public void mouseExited( MouseEvent e )
	{

	}

}
