/*
 * 
 */
package eus.tta.shielded;

// TODO: Auto-generated Javadoc
/**
 * The Class Stick.
 */
public class Stick {

/** The activated. */
private boolean activated;
	
	/**
	 * Instantiates a new stick.
	 */
	public Stick(){
		activated=false;
	}
	
	/**
	 * Activate stick.
	 *
	 * @return true, if successful
	 */
	public boolean activateStick(){
		if(!activated){
			activated=true;
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks if is activated.
	 *
	 * @return true, if is activated
	 */
	public boolean isActivated(){
		return activated;
	}
	
}
