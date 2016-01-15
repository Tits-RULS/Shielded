/*
 * 
 */
package eus.tta.shielded;

import java.util.Random;

/**
 * The Class HardIA.
 */
public class HardIA extends MediumIA {
	

	public HardIA(Stick [][] vertical, Stick [][] horizontal,Square [][] square,int maxX,int maxY){
		super(vertical,horizontal,square,maxX,maxY);
	}
	
	/* (non-Javadoc)
	 * @see com.example.shielded2.IA#turn()
	 */
	@Override
	public Data turn(){
		/*comprobar cuadrados*/
		Data stick = checkSquares();
		
		/*en caso de no detectar cuadrado*/
		if(stick==null){
			int act=-1;
			/*no exceptions*/
			int count =0;
			do{
				int i = r.nextInt(buttons);
				if(array[i].vertical){
					if(!vertical[array[i].x][array[i].y].isActivated()){
						if(array[i].y==0||array[i].y==yTam){
							/*un cuadrados*/
							if(array[i].y==0){
								if(square[array[i].x][array[i].y].HowMuchActivated()<2)
									act=i;
							}
							else{
								if(square[array[i].x][array[i].y-1].HowMuchActivated()<2)
									act=i;
							}
						}
						else{
							/*dos cuadrados*/
							boolean activate=true;
							if(square[array[i].x][array[i].y].HowMuchActivated()>=2)
								activate=false;
							if(square[array[i].x][array[i].y-1].HowMuchActivated()>=2)
								activate=false;
							if(activate)
								act=i;
						}
					}
				}
				else{
					if(!horizontal[array[i].x][array[i].y].isActivated()){
						if(array[i].x==0||array[i].x==xTam){
							/*un solo cuadrado*/
							if(array[i].x==0){
								if(square[array[i].x][array[i].y].HowMuchActivated()<2)
									act=i;
							}
							else{
								if(square[array[i].x-1][array[i].y].HowMuchActivated()<2)
									act=i;
							}
						}
						else{
							/*dos cuadrados*/
							boolean activate=true;
							if(square[array[i].x][array[i].y].HowMuchActivated()>=2)
								activate=false;
							if(square[array[i].x-1][array[i].y].HowMuchActivated()>=2)
								activate=false;
							if(activate)
								act=i;
						}
					}
				}
				count++;
			}while(act==-1&&count<20);
			if(count>=20)
				act=r.nextInt(buttons);
			return array[act];
		}else{
			return  stick;
		}
	}
}
