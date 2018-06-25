package Mancala;

import java.awt.*;
import java.awt.geom.*;

/**
 * Class for design the pit object.
 * Designs the pit for Mancala or player pit.
 * @author Dion Ghieuw
 * @author Casey Reyes
 * @author Manisha Yalavarthy
 */
public class PitDesign {
	
	/**
	 * Pit design constructor.
	 * @param x 		The x position
	 * @param y 		The y position
	 * @param width		The width
	 * @param height	The height
	 * @param c			The color.
	 */
	public PitDesign(int x, int y, int width, int height, Color c)
	{
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.c = c;
	}
	
	/**
	 * Draw method to draw the shape of pit.
	 * @param g	The graphics
	 */
	public void draw(Graphics2D g)
	{
		g.draw(shape);
		fill(g);
	}
	
	/**
	 * Fill method to fill the shape
	 * @param g	The graphgics.
	 */
	public void fill(Graphics2D g)
	{
		g.setColor(c);
		g.fill(shape);
		
		int count = 1;
		double rad = (Math.min(this.height,  this.width) / 4.0);
		
		if(stone > 0)
		{
			g.setColor(Color.YELLOW);
			
			Font theFont = new Font("Algerian", Font.PLAIN, 18);
			FontMetrics fm = g.getFontMetrics();
			g.setFont(theFont);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);;
			g.drawString("" + stone, (this.x + width/2 - fm.stringWidth("" + stone) / 2), (this.y + height/2 + fm.getDescent() / 2));
		}
		
		for(int i = 0; i < stone; i++)
		{
			//Find angle.
			double theta = (2 * Math.PI) / (1.0 * stone) * (1.0 * count);
			//Center of the pit.
			double x = (this.x + width / 2 + Math.cos(theta) * rad - rad / 2.0);
			double y = (this. y + height / 2 + Math.sin(theta) * rad - rad / 2.0);
			
			Ellipse2D.Double theStone = new Ellipse2D.Double(x, y, SIZE_OF_STONE, SIZE_OF_STONE);
			
			g.setPaint(Color.YELLOW);
			g.fill(theStone);
			g.setColor(Color.WHITE);
			g.draw(theStone);
			count++;
		}	
	}
	
	/**
	 * Get the height.
	 * @return	The height
	 */
	public int getHeight()
	{return height;}
	
	/**
	 * Get the width.
	 * @return	The width
	 */
	public int getWidth()
	{return width;}
	
	/**
	 * Get the number of stones in pit.
	 * @return	The stones
	 */
	public int getStone()
	{return stone;}
	
	/**
	 * Get the x coordinate.
	 * @return	The x coordinate
	 */
	public int getX()
	{return x;}
	
	/**
	 * Get the y coordinate
	 * @return	The y coordinate
	 */
	public int getY()
	{return y;}
	
	/**
	 * Get the pit shape.
	 * @return	The shape of pit
	 */
	public Shape getShape()
	{return shape;}
	
	/**
	 * Set amount of stones in pit.
	 * @param num	The amount of stones
	 */
	public void setStone(int num)
	{stone = num;}
	
	/**
	 * Set the shape of pit.
	 * @param s	The shape of pit
	 */
	public void setShape(Shape s)
	{shape = s;}
	
	/**
	 * Contains method to check if point is in pit.
	 * @param thePoint	X,Y point.
	 * @return	If pit contains point.
	 */
	public boolean contains(Point2D thePoint)
	{
		if(shape.contains(thePoint))
		{return true;}
		else
		{return false;}
	}
	
	private int x, y, width, height, stone;
	private Shape shape;
	private Color c;
	final int SIZE_OF_STONE = 20;
}
