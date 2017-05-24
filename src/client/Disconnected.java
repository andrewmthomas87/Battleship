package client;

import javax.swing.*;

public class Disconnected extends JPanel
{

	public Disconnected()
	{
		JPanel panel = new JPanel();
		panel.setAlignmentX( 0.5F );
		panel.add( new JLabel( "Socket disconnected. Please restart." ) );
		this.add( panel );
	}

}
