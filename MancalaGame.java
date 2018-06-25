package Mancala;

import java.util.*;
import javax.swing.event.*;

/**
 * The model class in the MVC model. Keeps track of current game state and
 * performs all operations for accessing and mutating the data.
 *
 * @author Dion Ghieuw
 * @author Casey Reyes
 * @author Manisha Yalavarthy
 */
public class MancalaGame {

	/**
	 * Default constructor
	 */
	public MancalaGame() {

		pitArray = new int[NUM_OF_PITS];
		oldPitArray = new int[NUM_OF_PITS];

		aUndo = 0;
		bUndo = 0;
		curPlayer = Player.A;
		curState = GameState.INITIAL;
		ifInMancala = false;
		ifUndo = false;

		listenerList = new ArrayList<ChangeListener>();
	}

	/**
	 * Attaches ChangeListeners to be updated.
	 * 
	 * @param listener
	 *            the listener to attach
	 */
	public void attach(ChangeListener listener) {
		listenerList.add(listener);
	}

	/**
	 * Dumps stones when game ends in respective Mancala pit.
	 */
	private void dumpStones() {
		// Dumps A side to Mancala A and B side to Mancala B
		for (int i = 0; i < NUM_OF_PITS; ++i)
			if (!playerMancala(i)) {
				if (getPitSide(i) == Player.A) {
					pitArray[PLAYER_A_MANCALA] += pitArray[i];
					pitArray[i] = 0;
				} else {
					pitArray[PLAYER_B_MANCALA] += pitArray[i];
					pitArray[i] = 0;
				}
			}
	}

	/**
	 * Determines the next turn for opposite player.
	 */
	private void nextTurn() {
		if (curPlayer == Player.A) {
			curPlayer = Player.B;
		} else {
			curPlayer = Player.A;
		}
	}

	/**
	 * Saves the board state before next move.
	 */
	public void save() {
		oldPitArray = pitArray.clone();
	}

	/**
	 * Set the current state of game.
	 * 
	 * @param state
	 *            The current state
	 */
	public void setCurState(MancalaGame.GameState state) {
		curState = state;
	}

	/**
	 * Sets the initial amount of stones for each pit.
	 * 
	 * @param stoneNum
	 *            The number of stones at beginning of game.
	 */
	public void setNumStones(int stoneNum) {
		int pits = pitArray.length;
		for (int i = 0; i < pits; i++)
			if (!playerMancala(i)) {
				pitArray[i] = stoneNum;
			}
		this.update();
	}

	/**
	 * If the move is valid, makes the move depending on the pit number.
	 * 
	 * @param pitNum
	 *            The pit number.
	 */
	public void makeMove(int pitNum) {
		// Save current state of board/pit
		this.save();

		ifUndo = true;

		// Set B undo.
		if (curPlayer.equals(Player.A)) {
			bUndo = 0;
		} else {
			aUndo = 0;
		}

		int curStone = pitArray[pitNum];
		pitArray[pitNum] = 0;

		int place = pitNum;
		while (curStone > 0) {
			// Update position
			place++;
			if (place >= NUM_OF_PITS) {
				place = 0;
			}

			// If not curPlayer's Mancala, then skip.
			if (playerMancala(place) && getPitSide(place) != curPlayer) {
				continue;
			}

			// Places the stone in pit..
			pitArray[place]++;
			curStone--;
		}

		// Determines next curPlayer and last pit a stone was placed in.
		if (getPitSide(place) == curPlayer && playerMancala(place)) {
			ifInMancala = true;
		} // If in Mancala, free turn
		// Empty pit on curPlayer side.
		else if (getPitSide(place) == curPlayer && pitArray[place] == 1 && pitArray[oppositeSide(place)] > 0) {
			// Collect stones.
			int collect = pitArray[place] + pitArray[oppositeSide(place)];
			pitArray[place] = pitArray[oppositeSide(place)] = 0;
			if (curPlayer == Player.A) {
				pitArray[PLAYER_A_MANCALA] += collect;
			} else {
				pitArray[PLAYER_B_MANCALA] += collect;
			}

			// Determine next curPlayer turn.
			ifInMancala = false;
			nextTurn();
		} else {
			// Determine next curPlayer turn.
			ifInMancala = false;
			nextTurn();
		}

		// Check if the game is done.
		if (isGameDone()) {
			dumpStones();
			curState = GameState.END;
		}
		// Update to notify listeners.
		this.update();
	}

	/**
	 * If not MAX_UNDO, then undo last move.
	 */
	public void undo() {
		if (!ifUndo) {
			return;
		}

		boolean theUndo = false;

		switch (curPlayer) {
		case A: {
			if (ifInMancala && aUndo < MAX_UNDO) {
				aUndo++;
				theUndo = true;
			} else if (!ifInMancala && bUndo < MAX_UNDO) {
				bUndo++;
				curPlayer = Player.B;
				theUndo = true;
			}
			break;
		}
		case B: {
			if (ifInMancala && bUndo < MAX_UNDO) {
				bUndo++;
				theUndo = true;
			} else if (!ifInMancala && aUndo < MAX_UNDO) {
				aUndo++;
				theUndo = true;
				curPlayer = Player.A;
			}
			break;
		}
		}

		if (aUndo < MAX_UNDO && curState.equals(GameState.END) || bUndo < MAX_UNDO && curState.equals(GameState.END)) {
			curState = GameState.INGAME;
		}

		if (theUndo) {
			ifUndo = false;
			pitArray = oldPitArray.clone();
			update();
		}
	}

	/**
	 * Updates all ChangeListeners.
	 */
	public void update() {
		for (ChangeListener c : listenerList) {
			c.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Checks if move is valid.
	 * 
	 * @param pitSide the side the pit is on
	 * @return The curPlayer side of pit
	 */
	public boolean isMoveValid(int pitSide) {
		// Checks if pit is valid
		if (pitSide < 0 || pitSide > NUM_OF_PITS || pitSide == PLAYER_A_MANCALA || pitSide == PLAYER_B_MANCALA) {
			return false;
		}

		// Checks if still INGAME
		if (curState != GameState.INGAME) {
			return false;
		}

		// Checks if pit is empty
		if (pitArray[pitSide] == 0) {
			return false;
		}

		// Checks if pit belongs to curPlayer
		if (getPitSide(pitSide) != curPlayer) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if game is over by checking of all the pits on one side are empty.
	 * @return true if the game is done
	 */
	public boolean isGameDone() {
		int aStone = 0;
		int bStone = 0;
		for (int i = 0; i < NUM_OF_PITS; ++i) {
			if (!playerMancala(i)) {
				if (getPitSide(i) == Player.A) {
					aStone += pitArray[i];
				} else {
					bStone += pitArray[i];
				}
			}
		}
		return (aStone == 0 || bStone == 0);
	}

	/**
	 * Return the score of player
	 * 
	 * @param thePlayer the player
	 * @return The score of player
	 */
	public int getScore(MancalaGame.Player thePlayer) {
		if (thePlayer == MancalaGame.Player.A) {
			return pitArray[PLAYER_A_MANCALA];
		}
		if (thePlayer == MancalaGame.Player.B) {
			return pitArray[PLAYER_B_MANCALA];
		}

		return 0;
	}

	/**
	 * Get pit array with current number of stones
	 * 
	 * @return The pit array
	 */
	public int[] getPit() {
		return pitArray;
	}

	/**
	 * Get current state
	 * 
	 * @return The current state
	 */
	public GameState getCurState() {
		return curState;
	}

	/**
	 * Get the curPlayer
	 * 
	 * @return The current Player
	 */
	public Player getCurPlayer() {
		return curPlayer;
	}

	/**
	 * Determines if the pit is Mancala pit
	 * 
	 * @param pitSide
	 * @return If the pit is Mancala pit.
	 */
	private boolean playerMancala(int pitSide) {
		return (pitSide == PLAYER_A_MANCALA || pitSide == PLAYER_B_MANCALA);
	}

	/**
	 * Determine pit on opposite side.
	 * 
	 * @param pitSide
	 * @return The pit number on opposite side
	 */
	private int oppositeSide(int pitSide) {
		return Math.abs(12 - pitSide);
	}

	/**
	 * Determines pitSide depending on pit number.
	 * 
	 * @param pitSide
	 * @return The curPlayer that owns respective side.
	 */
	private Player getPitSide(int pitSide) {
		if (pitSide >= 0 && pitSide <= PLAYER_A_MANCALA) {
			return Player.A;
		} else {
			return Player.B;
		}
	}

	public static enum GameState {
		INITIAL, INGAME, END
	};

	public static enum Player {
		A, B
	};

	public static final int NUM_OF_PITS = 14;
	public static final int PLAYER_A_MANCALA = 6;
	public static final int PLAYER_B_MANCALA = 13;
	private final int MAX_UNDO = 3;

	private int[] pitArray;
	private int[] oldPitArray;

	private int aUndo;
	private int bUndo;
	private Player curPlayer;
	private GameState curState;
	private boolean ifUndo;
	private boolean ifInMancala;

	private ArrayList<ChangeListener> listenerList;
}