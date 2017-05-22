package client;

import javax.swing.*;
import java.awt.*;

public class ShipPlacement extends JPanel {

	private Ship ship;
	private JLabel shipName = new JLabel();
	private JLabel orientation = new JLabel();

	public ShipPlacement() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.shipName.setFont(new Font("Courier New", Font.PLAIN, 20));
		this.orientation.setFont(new Font("Courier New", Font.BOLD, 24));
		this.orientation.setOpaque(true);

		JPanel labels = new JPanel();
		labels.setAlignmentX(0.5F);
		labels.add(this.shipName);
		labels.add(this.orientation);

		this.add(labels);
	}

	public void setShip(Ship ship) {
		this.ship = ship;
		this.shipName.setText("Place your " + ship.getName() + " (" + ship.getLength() + ") ");
		this.orientation.setForeground(Color.WHITE);
		this.orientation.setBackground(ship.getColor());
		this.orientation.setText(ship.isHorizontal() ? " Horizontal " : " Vertical ");
	}

}
