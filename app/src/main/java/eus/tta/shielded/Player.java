/*
 * 
 */
package eus.tta.shielded;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player {

	/** The turn. */
	protected boolean turn;
	
	/** The selections_left. */
	protected int selections_left;
	
	/** The score. */
	protected int score;
	
	/**
	 * Instantiates a new player.
	 *
	 * @param start the start
	 * @param selections the selections
	 */
	public Player(boolean start, int selections){
		turn=start;
		selections_left=selections;
		score=0;
	}
	
	/**
	 * Adds the point.
	 */
	public void addPoint(){
		score++;
	}
	
	/**
	 * Extra turn.
	 */
	public void extraTurn(){
		selections_left++;
	}
	
	/**
	 * Change turn.
	 *
	 * @param selections the selections
	 */
	public void changeTurn(int selections){
		turn=!turn;
		selections_left=selections;
	}
	
	/**
	 * Gets the selections_left.
	 *
	 * @return the selections_left
	 */
	public int getSelections_left(){
		return selections_left;
	}
	
	
	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * Checks if is turn.
	 *
	 * @return true, if is turn
	 */
	public boolean isTurn() {
		return turn;
	}
	
	/**
	 * Selection down.
	 */
	public void selectionDown(){
		selections_left--;
	}

	/*public void setTurn(boolean turn) {
		this.turn = turn;
	}*/

	/*public void setSelections_left(int selections_left) {
		this.selections_left = selections_left;
	}*/

	/*public void setScore(int score) {
		this.score = score;
	}*/
	
}
