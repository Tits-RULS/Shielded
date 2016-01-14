/*
 * 
 */
package eus.tta.shielded;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class MediumIA.
 */
public class MediumIA implements IA {

	/** The buttons. */
	private int buttons=0;
	
	/** The exceptions. */
	private int exceptions[];
	
	/** The vertical. */
	private Stick vertical[][];
	
	/** The horizontal. */
	private Stick horizontal[][];
	
	/** The cuadrado. */
	private Square cuadrado[][];
	
	/** The array. */
	private Data array[];
	
	/** The r. */
	private Random r;
	
	/** The y tam. */
	private int xTam,yTam;
	
	/**
	 * Instantiates a new medium ia.
	 *
	 * @param but the but
	 * @param exc the exc
	 * @param gm the gm
	 */
	public MediumIA(int but,int exc[],GameIA gm){
		buttons= but;
		exceptions=exc;
		vertical = gm.getVertical();
		horizontal = gm.getHorizontal();
		cuadrado = gm.getCuadrado();
		array = gm.getData();
		r = new Random();
		xTam = gm.getXtam();
		yTam = gm.getYtam();
	}
	
	/* (non-Javadoc)
	 * @see com.example.shielded2.IA#turn()
	 */
	public Data turn(){
		/*comprobar cuadrados*/
		int act=-1;
		for(int ix=0; ix<xTam&&act==-1;ix++){
			for(int iy=0; iy<yTam&&act==-1;iy++){
				int actNum=cuadrado[ix][iy].HowMuchActivated();
				if(actNum==3){
					/*lo tienes para puntuar*/
					if(!vertical[ix][iy].isActivated()){
						act=ix*(2*yTam+2)+(iy*2+1);
						System.out.println("vertical");
					}
					if(!vertical[ix][iy+1].isActivated()){
						act=ix*(2*yTam+2)+((iy+1)*2+1);
						System.out.println("vertical");
					}
					if(!horizontal[ix][iy].isActivated()){
						act=ix*2*yTam+iy*2;
						System.out.println("horizontal");
					}
					if(!horizontal[ix+1][iy].isActivated()){
						act=(ix+1)*2*yTam+iy*2;
						System.out.println("horizontal");
					}
						
				}
			}
		}
		
		/*en caso de no detectar cuadrado*/
		if(act==-1){
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
		}
		return array[act];
	}
}
