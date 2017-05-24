package client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Console extends JPanel
{

	private List<String> messages = new ArrayList<>();

	public void println( String message )
	{
		this.messages.add( message );
		this.repaint();
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension( 400, 150 );
	}

	@Override
	protected void paintComponent( Graphics g )
	{
		super.paintComponent( g );

		g.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
		int lineHeight = g.getFontMetrics()
		                  .getHeight();

		int height          = this.getHeight();
		int messageCapacity = Math.min( ( height - 26 ) / (int) Math.round( lineHeight * 1.5 ), this.messages.size() );

		for ( int i = 0; i < messageCapacity; i++ )
		{
			if ( i == 0 )
			{
				g.setColor( Color.BLUE );
			}
			else
			{
				g.setColor( Color.BLACK );
			}
			String message = this.messages.get( this.messages.size() - 1 - i );
			g.drawString( message, 13, height - 13 - ( i + 1 ) * (int) Math.round( lineHeight * 1.5 ) + (int) Math.round( lineHeight * 0.125 ) );
		}
	}

}
