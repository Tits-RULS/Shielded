/*
 * 
 */
package eus.tta.shielded;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class MediumIA.
 */
public class MediumIA extends EasyIA {

	public MediumIA(Stick [][] vertical, Stick [][] horizontal,Square [][] square,int maxX,int maxY){
		super(vertical,horizontal,square,maxX,maxY);
	}
	
	/* (non-Javadoc)
	 * @see com.example.shielded2.IA#turn()
	 */
	public Data turn(){
		/*comprobar cuadrados*/
		Data stick = checkSquares();
		if(stick==null){
			/*en caso de no detectar cuadrado*/
			return super.turn();
		}else{
			return stick;
		}

	}

	protected Data checkSquares(){
		for(int ix=0; ix<xTam;ix++){
			for(int iy=0; iy<yTam;iy++){
				int actNum=square[ix][iy].HowMuchActivated();
				if(actNum==3){
					/*lo tienes para puntuar*/
					if(!vertical[ix][iy].isActivated()){
						Data stick = new Data();
						stick.x=ix;
						stick.y=iy;
						stick.vertical=true;
						return stick;
					}
					if(!vertical[ix][iy+1].isActivated()){
						Data stick = new Data();
						stick.x=ix;
						stick.y=iy+1;
						stick.vertical=true;
						return stick;
					}
					if(!horizontal[ix][iy].isActivated()){
						Data stick = new Data();
						stick.x=ix;
						stick.y=iy;
						stick.vertical=false;
						return stick;
					}
					if(!horizontal[ix+1][iy].isActivated()){
						Data stick = new Data();
						stick.x=ix+1;
						stick.y=iy;
						stick.vertical=false;
						return stick;
					}

				}
			}
		}
		return null;
	}
}
