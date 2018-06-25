package Mancala;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Presents the intial GUI for the user to interact
 * with.
 * @author Dion Ghieuw
 * @author Casey Reyes
 * @author Manisha Yalavarthy
 */
public class InitialGUI {

	/**
	 * Create GUI
	 *@param aModel the game model
	 */
	public InitialGUI(MancalaGame aModel) {

		showButton = true;

		final JFrame theFrame = new JFrame();
		theModel = aModel;
		theControl = new GameView(theModel);

		final JPanel lowerPart = new JPanel();
		JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theModel.undo();
			}
		});

		final JPanel thePanel = new JPanel();
		JLabel theLabel = new JLabel("Please select layout and amount of stones");
		thePanel.add(theLabel);

		// Strategy pattern buttons for Layout One.
		JButton layoutButton1 = new JButton("Layout One");
		JButton layoutButton2 = new JButton("Layout Two");

		layoutButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// theModel.setNumStones(0);

				showButton = false;
				setShowButton(false, thePanel);
				theControl.setLayout(new BoardStyleLayout1());
				setShowButton(true, lowerPart);//show buttons
				queryNumStones();
			}
		});

		layoutButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// theModel.setNumStones(4);
				
				showButton = false;
				setShowButton(false, thePanel);
				theControl.setLayout(new BoardStyleLayout2());
				setShowButton(true, lowerPart);
				queryNumStones();
			}
		});

		thePanel.add(layoutButton1);
		thePanel.add(layoutButton2);
		lowerPart.add(undo);
		theFrame.setSize(1000, 750);
		theFrame.setTitle("Gryffindor Mancala");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.add(theControl, BorderLayout.CENTER);
		theFrame.add(thePanel, BorderLayout.NORTH);
		theFrame.add(lowerPart, BorderLayout.SOUTH);
		setShowButton(false, lowerPart);
		theFrame.setResizable(false);
		theFrame.setVisible(true);
	}

	/**
	 * setButtonVisible method to show button visibility when not INGAME. Turns
	 * off visibility if INGAME.
	 * 
	 * @param show
	 *            The button.
	 * @param p
	 *            The panel.
	 */
	public void setShowButton(boolean show, JPanel p) {
		p.setVisible(show);

		if (!showButton) {
			theControl.run();
		}
	}
	
	/**
	 * Displays a frame asking for user input for the initial number of stones.
	 */
	public void queryNumStones() {
		JFrame theFrame = new JFrame("Stone Select");
		JLabel label = new JLabel("Choose the initial amount of stones: ");
		JButton threeButton = new JButton("Three");
		JButton fourButton = new JButton("Four");
		
		threeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				theModel.setNumStones(3);
				theFrame.setVisible(false);
				
			}
			
		});
		fourButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				theModel.setNumStones(4);
				theFrame.setVisible(false);
			}
			
		});
		theFrame.add(label, BorderLayout.NORTH);
		theFrame.add(threeButton,BorderLayout.WEST);
		theFrame.add(fourButton, BorderLayout.EAST);
		theFrame.pack();
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setVisible(true);
	}

	private boolean showButton;
	private GameView theControl;
	private MancalaGame theModel;
}