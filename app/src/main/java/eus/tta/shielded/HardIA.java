/*
 * 
 */
package eus.tta.shielded;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class HardIA.
 */
public class HardIA implements IA {
	

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
	 * Instantiates a new hard ia.
	 *
	 * @param but the but
	 * @param exc the exc
	 * @param gm the gm
	 */
	public HardIA(int but,int exc[],GameIA gm){
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
	@Override
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
				int count=0;
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
								if(array[i].y==0||array[i].y==yTam){
									/*un cuadrados*/
									if(array[i].y==0){
										if(cuadrado[array[i].x][array[i].y].HowMuchActivated()<2)
											act=i;
									}
									else{
										if(cuadrado[array[i].x][array[i].y-1].HowMuchActivated()<2)
											act=i;
									}
								}
								else{
									/*dos cuadrados*/
									boolean activate=true;
									if(cuadrado[array[i].x][array[i].y].HowMuchActivated()>=2)
										activate=false;
									if(cuadrado[array[i].x][array[i].y-1].HowMuchActivated()>=2)
										activate=false;
									if(activate)
										act=i;
									else
										count++;
								}
							}
						}
						else{
							if(!horizontal[array[i].x][array[i].y].isActivated()){
								if(array[i].x==0||array[i].x==xTam){
									/*un solo cuadrado*/
									if(array[i].x==0){
										if(cuadrado[array[i].x][array[i].y].HowMuchActivated()<2)
											act=i;
										else
											count++;
									}
									else{
										if(cuadrado[array[i].x-1][array[i].y].HowMuchActivated()<2)
											act=i;
										else
											count++;
									}
								}
								else{
									/*dos cuadrados*/
									boolean activate=true;
									if(cuadrado[array[i].x][array[i].y].HowMuchActivated()>=2)
										activate=false;
									if(cuadrado[array[i].x-1][array[i].y].HowMuchActivated()>=2)
										activate=false;
									if(activate)
										act=i;
								}
							}
						}
					}
				}while(act==-1&&count<20);
				if(count>=20)
					act=r.nextInt(buttons);
			}
			else{
				/*no exceptions*/
				int count =0;
				do{
					int i = r.nextInt(buttons);
					if(array[i].vertical){
						if(!vertical[array[i].x][array[i].y].isActivated()){
							if(array[i].y==0||array[i].y==yTam){
								/*un cuadrados*/
								if(array[i].y==0){
									if(cuadrado[array[i].x][array[i].y].HowMuchActivated()<2)
										act=i;
								}
								else{
									if(cuadrado[array[i].x][array[i].y-1].HowMuchActivated()<2)
										act=i;
								}
							}
							else{
								/*dos cuadrados*/
								boolean activate=true;
								if(cuadrado[array[i].x][array[i].y].HowMuchActivated()>=2)
									activate=false;
								if(cuadrado[array[i].x][array[i].y-1].HowMuchActivated()>=2)
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
									if(cuadrado[array[i].x][array[i].y].HowMuchActivated()<2)
										act=i;
								}
								else{
									if(cuadrado[array[i].x-1][array[i].y].HowMuchActivated()<2)
										act=i;
								}
							}
							else{
								/*dos cuadrados*/
								boolean activate=true;
								if(cuadrado[array[i].x][array[i].y].HowMuchActivated()>=2)
									activate=false;
								if(cuadrado[array[i].x-1][array[i].y].HowMuchActivated()>=2)
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
				
			}
		}
		return array[act];
	}

}
