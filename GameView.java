package Mancala;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * The view and controller class responsible for displaying the game board and
 * its actions from user input.
 * 
 * @author Dion Ghieuw
 * @author Casey Reyes
 * @author Manisha Yalavarthy
 */
@SuppressWarnings("serial")
public class GameView extends JComponent implements ChangeListener {

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            the game to serve as the model
	 */
	//this is what you see when you run the game
	
	public GameView(MancalaGame game) {
		model = game;

		stone = new int[14];
		setVisible(false);
		model.attach(this);

		//mouselistener to make move
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for (int i = 0; i < thePit.size(); i++) {
					if (thePit.get(i).contains(e.getPoint()) && model.isMoveValid(i)) {
						model.makeMove(i);
						return;
					}
				}
			}
		});
	}

	/**
	 * Create the initial board view.
	 */
	
	private void initializeBoard() {
		thePit = new ArrayList<PitDesign>();
		theShape = new ArrayList<Shape>();
		Rectangle2D.Double theBoard = new Rectangle2D.Double(5, 20, 965, 670);

		// Pit location variables.
		final int PIT_WIDTH = 100;
		final int PIT_HEIGHT = 150;
		final int TOP_PIT_Y = 75;
		final double BOTTOM_PIT_Y = theBoard.getHeight() - TOP_PIT_Y - PIT_HEIGHT;

		Color c = layout.layoutColor();

		// Player B Side
		PitDesign b1 = new PitDesign(PIT_WIDTH + PIT_WIDTH / 4, TOP_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign b2 = new PitDesign((2 * PIT_WIDTH + PIT_WIDTH / 2), TOP_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign b3 = new PitDesign((3 * PIT_WIDTH + 3 * PIT_WIDTH / 4), TOP_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign b4 = new PitDesign(5 * PIT_WIDTH, TOP_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign b5 = new PitDesign((6 * PIT_WIDTH + PIT_WIDTH / 4), TOP_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign b6 = new PitDesign((7 * PIT_WIDTH + PIT_WIDTH / 2), TOP_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign m1 = new PitDesign(10, 120, PIT_WIDTH, 3 * PIT_HEIGHT, c);//creates the player b storage

		b1.setShape(layout.thePitShape(b1));
		b1.setStone(stone[0]);
		b2.setShape(layout.thePitShape(b2));
		b2.setStone(stone[1]);
		b3.setShape(layout.thePitShape(b3));
		b3.setStone(stone[2]);
		b4.setShape(layout.thePitShape(b4));
		b4.setStone(stone[3]);
		b5.setShape(layout.thePitShape(b5));
		b5.setStone(stone[4]);
		b6.setShape(layout.thePitShape(b6));
		b6.setStone(stone[5]);
		m1.setShape(layout.thePitShape(m1));//left hand side (counter clockwise)
		m1.setStone(stone[6]);

		// Player A Side
		PitDesign a1 = new PitDesign((PIT_WIDTH + PIT_WIDTH / 4), (int) BOTTOM_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign a2 = new PitDesign((2 * PIT_WIDTH + PIT_WIDTH / 2), (int) BOTTOM_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign a3 = new PitDesign((3 * PIT_WIDTH + 3 * PIT_WIDTH / 4), (int) BOTTOM_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign a4 = new PitDesign(5 * PIT_WIDTH, (int) BOTTOM_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign a5 = new PitDesign((6 * PIT_WIDTH + PIT_WIDTH / 4), (int) BOTTOM_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign a6 = new PitDesign((7 * PIT_WIDTH + PIT_WIDTH / 2), (int) BOTTOM_PIT_Y, PIT_WIDTH, PIT_HEIGHT, c);
		PitDesign m2 = new PitDesign((int) theBoard.getWidth() - PIT_WIDTH, 120, PIT_WIDTH, 3 * PIT_HEIGHT, c);//create player a storage

		a1.setShape(layout.thePitShape(a1));
		a1.setStone(stone[7]);
		a2.setShape(layout.thePitShape(a2));
		a2.setStone(stone[8]);
		a3.setShape(layout.thePitShape(a3));
		a3.setStone(stone[9]);
		a4.setShape(layout.thePitShape(a4));
		a4.setStone(stone[10]);
		a5.setShape(layout.thePitShape(a5));
		a5.setStone(stone[11]);
		a6.setShape(layout.thePitShape(a6));
		a6.setStone(stone[12]);
		m2.setShape(layout.thePitShape(m2));//right hand side
		m2.setStone(stone[13]);

		// Add shapes
		addShape(a1);
		addShape(a2);
		addShape(a3);
		addShape(a4);
		addShape(a5);
		addShape(a6);
		addShape(m2);
		addShape(b6);
		addShape(b5);
		addShape(b4);
		addShape(b3);
		addShape(b2);
		addShape(b1);
		addShape(m1);

		theShape.add(theBoard);
	}

	/**
	 * addShape method to add the new shape to component.
	 * 
	 * @param pitShapeDesign
	 *            The added shape
	 */
	public void addShape(PitDesign pitShapeDesign) {
		thePit.add(pitShapeDesign);
	}

	/**
	 * paintComponent method to paint the component
	 * 
	 * @param g
	 *            The graphics.
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		for (Shape s : theShape) {
			g2.draw(s);
		}

		for (PitDesign p : thePit) {
			p.fill(g2);
		}

		// The label for pits.
		String aSide = "A";
		String a1 = "A1";
		String a2 = "A2";
		String a3 = "A3";
		String a4 = "A4";
		String a5 = "A5";
		String a6 = "A6";
		String bSide = "B";
		String b1 = "B1";
		String b2 = "B2";
		String b3 = "B3";
		String b4 = "B4";
		String b5 = "B5";
		String b6 = "B6";

		// The text in the center.
		String centerText = "";
		if (model.getCurState() == MancalaGame.GameState.INGAME) {
			if (model.getCurPlayer() == MancalaGame.Player.A) {
				centerText = "Turn: Player A";
			} else {
				centerText = "Turn: Player B";
			}
		} else if (model.getCurState() == MancalaGame.GameState.END) {
			centerText = "Final Score: " + model.getScore(MancalaGame.Player.B) + " vs "
					+ model.getScore(MancalaGame.Player.A);
		}

		g.setColor(Color.ORANGE);

		Font font = new Font("Algerian", Font.PLAIN, 26);

		g2.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//helps us manipulate the font (look at the api)type name = new type()
		
		g2.drawString(centerText, this.getX() + this.getWidth() / 2 - fm.stringWidth("" + stone) / 2,
				this.getY() + this.getHeight() / 2 - fm.getAscent() / 2);
		g2.drawString(aSide, this.getX() + this.getWidth() - 75, this.getY() + this.getHeight() - 375);
		g2.drawString(a1, this.getX() + this.getWidth() - 825, this.getY() + this.getHeight() - fm.getAscent());
		g2.drawString(a2, this.getX() + this.getWidth() - 700, this.getY() + this.getHeight() - fm.getAscent());
		g2.drawString(a3, this.getX() + this.getWidth() - 575, this.getY() + this.getHeight() - fm.getAscent());
		g2.drawString(a4, this.getX() + this.getWidth() - 450, this.getY() + this.getHeight() - fm.getAscent());
		g2.drawString(a5, this.getX() + this.getWidth() - 325, this.getY() + this.getHeight() - fm.getAscent());
		g2.drawString(a6, this.getX() + this.getWidth() - 200, this.getY() + this.getHeight() - fm.getAscent());
		g2.drawString(bSide, this.getX() + this.getWidth() - 935, this.getY() + this.getHeight() - 375);
		g2.drawString(b1, this.getX() + this.getWidth() - 200, this.getY() + this.getHeight() - 630);
		g2.drawString(b2, this.getX() + this.getWidth() - 325, this.getY() + this.getHeight() - 630);
		g2.drawString(b3, this.getX() + this.getWidth() - 450, this.getY() + this.getHeight() - 630);
		g2.drawString(b4, this.getX() + this.getWidth() - 575, this.getY() + this.getHeight() - 630);
		g2.drawString(b5, this.getX() + this.getWidth() - 700, this.getY() + this.getHeight() - 630);
		g2.drawString(b6, this.getX() + this.getWidth() - 825, this.getY() + this.getHeight() - 630);
	}

	/**
	 * stateChanged method to update pits when changed.
	 */
	@Override
	public void stateChanged(ChangeEvent c) {
		// TODO Auto-generated method stub
		initializeBoard();
		stone = model.getPit();

		int pitSize = thePit.size();
		for (int i = 0; i < pitSize; i++) {
			thePit.get(i).setStone(stone[i]);
		}

		repaint();
	}

	/**
	 * setBoardVisible method to set board visibility
	 * 
	 * @param show
	 *            If visible or not
	 */
	public void setBoardVisible(boolean show) {
		setVisible(show);
	}

	/**
	 * Sets the board layout depending on which button is clicked. Follows
	 * Strategy pattern.
	 * 
	 * @param bli
	 *            The BoardLayoutInterface
	 */
	public void setLayout(BoardLayoutInterface bli) {
		layout = bli;
		initializeBoard();
	}

	/**
	 * run method to start Mancala game.
	 */
	public void run() {
		// TODO implement here
		setBoardVisible(true);
		model.setCurState(MancalaGame.GameState.INGAME);
	}

	private ArrayList<Shape> theShape;
	private ArrayList<PitDesign> thePit;
	private MancalaGame model;
	private BoardLayoutInterface layout;
	private int[] stone;
}