package client;

import javax.swing.*;
import java.awt.*;

public class BoardToggler extends JPanel
{

	private Board         board;
	private OpponentBoard opponentBoard;

	private JLabel label = new JLabel();

	private boolean opponent = false;

	public BoardToggler( Board board, OpponentBoard opponentBoard )
	{
		this.board = board;
		this.opponentBoard = opponentBoard;

		this.setLayout( new BorderLayout() );
		this.label.setFont( new Font( "Courier New", Font.BOLD, 32 ) );

		this.renderUI();
	}

	public void toggle()
	{
		this.opponent = !this.opponent;

		this.renderUI();
	}

	private void renderUI()
	{
		this.removeAll();

		this.label.setText( this.opponent ? "Your turn" : "Their turn" );

		this.add( this.label, BorderLayout.NORTH );
		this.add( this.opponent ? this.opponentBoard : this.board, BorderLayout.CENTER );

		this.revalidate();
		this.repaint();
	}

}
