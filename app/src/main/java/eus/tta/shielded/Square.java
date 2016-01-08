/*
 * 
 */
package eus.tta.shielded;

import eus.tta.shielded.Stick;

// TODO: Auto-generated Javadoc
/**
 * The Class Square.
 */
public class Square {

	/** The up_stick. */
	private Stick up_stick;
	
	/** The down_stick. */
	private Stick down_stick;
	
	/** The left_stick. */
	private Stick left_stick;
	
	/** The right_stick. */
	private Stick right_stick;
	
	/** The completed. */
	private boolean completed;
	
	/**
	 * Instantiates a new square.
	 *
	 * @param up the up
	 * @param down the down
	 * @param left the left
	 * @param right the right
	 */
	public Square(Stick up,Stick down, Stick left, Stick right){
		up_stick=up;
		down_stick=down;
		left_stick=left;
		right_stick=right;
		completed=false;
	}
	
	/**
	 * Activate square.
	 *
	 * @return true, if successful
	 */
	public boolean activateSquare(){
		if(completed){
			return false;
		}
		else{
			if(up_stick.isActivated()&&down_stick.isActivated()&&left_stick.isActivated()&&right_stick.isActivated()){
				completed=true;
				return true;
			}
			else
				return false;
		}
	}
	
	/**
	 * How much activated.
	 *
	 * @return the int
	 */
	public int HowMuchActivated(){
		int count=0;
		if(up_stick.isActivated())
			count++;
		if(down_stick.isActivated())
			count++;
		if(left_stick.isActivated())
			count++;
		if(right_stick.isActivated())
			count++;
		return count;
	}
	
	/**
	 * Checks if is completed.
	 *
	 * @return true, if is completed
	 */
	public boolean isCompleted(){
		return completed;
	}
	
}
