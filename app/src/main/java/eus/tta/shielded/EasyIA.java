/*
 * 
 */
package eus.tta.shielded;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class EasyIA.
 */
public class EasyIA implements IA{

	/** The buttons. */
	private int buttons=0;
	
	/** The exceptions. */
	private int exceptions[];
	
	/** The vertical. */
	private Stick vertical[][];
	
	/** The horizontal. */
	private Stick horizontal[][];
	//private Square cuadrado[][];
	/** The array. */
	private Data array[];
	
	/** The r. */
	private Random r;
	
	/**
	 * Instantiates a new easy ia.
	 *
	 * @param but the but
	 * @param exc the exc
	 * @param gm the gm
	 */
	public EasyIA(int but,int exc[],GameIA gm){
		buttons= but;
		exceptions=exc;
		vertical = gm.getVertical();
		horizontal = gm.getHorizontal();
		//cuadrado = gm.getCuadrado();
		array = gm.getData();
		r = new Random();
	}
	
	/* (non-Javadoc)
	 * @see com.example.shielded2.IA#turn()
	 */
	public int turn(){
		int act=-1;
		if(exceptions!=null){
			do{
				int i = r.nextInt(buttons);
				boolean exc=false;		//control de excepciones
				for(int n=0;n<exceptions.length&&exc==false;n++){
					if(i==exceptions[n]){
						exc=true;		//hay excepciones
					}
				}
				if(!exc){		//si no hay excepciones, se ejecuta
					if(array[i].vertical){
						if(!vertical[array[i].x][array[i].y].isActivated()){
							act=i;
						}
					}
					else{
						if(!horizontal[array[i].x][array[i].y].isActivated()){
							act=i;
						}
					}
				}
				
			}while(act==-1);
		}
		else{
			/*no exceptions*/
			do{
				int i = r.nextInt(buttons);
				if(array[i].vertical){
					if(!vertical[array[i].x][array[i].y].isActivated()){
						act=i;
					}
				}
				else{
					if(!horizontal[array[i].x][array[i].y].isActivated()){
						act=i;
					}
				}
			}while(act==-1);
			
		}
		return act;
	}
	
}
