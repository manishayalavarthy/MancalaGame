package Mancala;

/**
 * MancalaTest as Main. The MancalaTest program
 * implements an application of the Mancala Game.
 * The game is designed for two players. The board consists
 * of two rows of pits with a large pit as Mancala pit at each end.
 * Player A begins the game by selecting respective pit side.
 * Each move goes counter-clockwise. The game continues until all six
 * pits on one side is empty. The max amount of undos is set to three.
 * Player with the most amount of stones wins, if equal, then tie game.
 *
 * @author Dion Ghieuw
 * @author Casey Reyes
 * @author Manisha Yalavarthy
 * 
 *	CS151 MW 12-1.15pm. Group Project. 
 */
public class MancalaTest {

	public static void main(String[] args) {
		MancalaGame modelGame = new MancalaGame();
		InitialGUI intialGUI = new InitialGUI(modelGame);
	}
}