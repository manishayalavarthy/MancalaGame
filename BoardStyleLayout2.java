package Mancala;

import java.awt.*;
import java.awt.geom.*;

/**
 * Concrete BoardLayout.
 * Part of Strategy pattern.
 * Red Color with Circle pits.
 * @author Dion Ghieuw
 * @author Casey Reyes
 * @author Manisha Yalavarthy
 */
public class BoardStyleLayout2 implements BoardLayoutInterface {

	/**
	 * Default constructor
	 */
	public BoardStyleLayout2() {
	}

	@Override
	public Color layoutColor() {
		// TODO Auto-generated method stub
		return Color.RED;
	}

	@Override
	public Shape thePitShape(PitDesign p) {
		// TODO Auto-generated method stub
		return new Ellipse2D.Double(p.getX(), p.getY(), p.getWidth(), p.getHeight());
	}
}