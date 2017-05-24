package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShipPlacement extends JPanel implements MouseListener
{

	private Ship ship;
	private JLabel shipName    = new JLabel();
	private JLabel orientation = new JLabel();

	public ShipPlacement()
	{
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );

		this.shipName.setFont( new Font( "Courier New", Font.PLAIN, 20 ) );
		this.orientation.setFont( new Font( "Courier New", Font.BOLD, 24 ) );
		this.orientation.setOpaque( true );

		this.orientation.addMouseListener( this );

		JPanel labels = new JPanel();
		labels.setAlignmentX( 0.5F );
		labels.add( this.shipName );
		labels.add( this.orientation );

		this.add( labels );
	}

	public Ship getShip()
	{
		return this.ship;
	}

	public void setShip( Ship ship )
	{
		this.ship = ship;
		this.shipName.setText( "Place your " + ship.getName() + " (" + ship.getLength() + ") " );
		this.orientation.setBackground( ship.getColor() );
		this.orientation.setText( ship.isHorizontal() ? " Horizontal " : " Vertical " );
	}

	@Override
	public void mouseClicked( MouseEvent e )
	{
		this.ship.setHorizontal( !this.ship.isHorizontal() );
		this.orientation.setText( ship.isHorizontal() ? " Horizontal " : " Vertical " );
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
