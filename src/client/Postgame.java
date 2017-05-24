package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Postgame extends JPanel implements ActionListener
{

	private PlayAgainListener playAgainListener;

	private JLabel label = new JLabel();

	public Postgame( PlayAgainListener playAgainListener )
	{
		this.playAgainListener = playAgainListener;

		this.label.setFont( new Font( "Courier New", Font.BOLD, 48 ) );

		JButton button = new JButton( "Play again" );
		button.addActionListener( this );

		JPanel panel = new JPanel();
		panel.setAlignmentX( 0.5F );
		panel.add( this.label );
		panel.add( button );
		this.add( panel );
	}

	public void setYouWon( boolean youWon )
	{
		this.label.setForeground( youWon ? Color.GREEN : Color.RED );
		this.label.setText( youWon ? "You won!" : "You lost." );
	}

	@Override
	public void actionPerformed( ActionEvent e )
	{
		this.playAgainListener.playAgain();
	}

}
