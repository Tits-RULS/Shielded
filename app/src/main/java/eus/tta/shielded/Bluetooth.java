/*
 * 
 */
package eus.tta.shielded;

import java.io.DataInputStream;
import java.io.IOException;

import android.os.AsyncTask;

// TODO: Auto-generated Javadoc
/**
 * The Class Bluetooth.
 */
public class Bluetooth extends AsyncTask<Object, Integer, Long>{

	/** The type. */
	private Integer type;
	
	/** The gms. */
	private GameServer gms;
	
	/** The gmc. */
	private GameCliente gmc;
	
	/** The dis. */
	private DataInputStream dis;
	
	
	/** The vertical. */
	private Stick vertical[][];
	
	/** The horizontal. */
	private Stick horizontal[][];
	
	/** The cuadrado. */
	private Square cuadrado[][];
	
	/** The array. */
	private Data array[];
	
	/** The x tam. */
	private Integer xTam;
	
	/** The y tam. */
	private Integer yTam;
	
	/** The gm. */
	private Game gm;
	
	/*Objects:
	 * arg[0] = 1 or 2 (Server or Client)
	 * arg[1] = GameServer or GameClient
	 * arg[2] = DataInputStream
	 * arg[3] = xTam
	 * arg[4] = yTam 
	 */
	
	/**
	 * Do in background.
	 *
	 * @param arg the arg
	 * @return the long
	 */
	@Override
	protected Long doInBackground(Object... arg) {
		System.out.println("EN BT");
		type = (Integer) arg[0];
		System.out.println("antes del swith BT");
		gm = (Game) arg[1];
		switch(type){
		case 1:
			gms = (GameServer) arg[1];
			vertical = gms.getVertical();
			horizontal = gms.getHorizontal();
			cuadrado = gms.getCuadrado();
			array = gms.getData();
			xTam = (Integer) arg[3];
			yTam = (Integer) arg[4];
			break;
		case 2:
			gmc = (GameCliente) arg[1];
			vertical = gmc.getVertical();
			horizontal = gmc.getHorizontal();
			cuadrado = gmc.getCuadrado();
			array = gmc.getData();
			xTam = (Integer) arg[3];
			yTam = (Integer) arg[4];
			break;
		}
		System.out.println("Despues del switch BT");
		dis =  (DataInputStream) arg[2];
		System.out.println("Antes del while BT");
		int id = 0;
		while(true){
			try {
				System.out.println("En el while BT");
				id = dis.readInt();
			} catch (IOException e) {
				System.out.println("Exception");
				e.printStackTrace();
			}
			
			switch(type){
			case 1:
				if(gms.eventRemote())
					gestion(id);
				break;
			case 2:
				if(gmc.eventRemote())
					gestion(id);
				break;
			}
			
		}
		
	}
	
	/**
	 * Gestion.
	 *
	 * @param id the id
	 */
	protected void gestion(int id){
		if(array[id].vertical){
    		/*vertical*/
			System.out.println("Palo vertical");
    		if(vertical[array[id].x][array[id].y].activateStick()){
    			System.out.println("Palo activo");
    			/*palo activado*/
    			publishProgress(0,id);
    			if(array[id].y==0||array[id].y==yTam){
    				System.out.println("Palo de un borde");
    				/*un solo cuadrado*/
    				if(array[id].y==0){
    					/*esquina izquierda*/
    					if(cuadrado[array[id].x][array[id].y].activateSquare()){
    						System.out.println("Palo de esquina izquierda");
        					publishProgress(1,gm.getVariable("R.id.sq"+array[id].x+"_"+array[id].y));
        					publishProgress(3,1);
        				}
    				}
    				else{
    					/*esquina derecha*/
    					if(cuadrado[array[id].x][array[id].y-1].activateSquare()){
    						System.out.println("Palo de esquina derecha");
        					publishProgress(1,gm.getVariable("R.id.sq"+array[id].x+"_"+(array[id].y-1)));
        					publishProgress(3,1);
        				}
    				}
    			}
    			else{
    				/*dos cuadrados*/
    				System.out.println("Palo no de borde");
    				int point=0;
    				/*comprobar el primero (izquierdo)*/
    				if(cuadrado[array[id].x][array[id].y-1].activateSquare()){
    					System.out.println("primer cuadrado activo");
    					publishProgress(1,gm.getVariable("R.id.sq"+array[id].x+"_"+(array[id].y-1)));
    					point++;
    				}
    				/*comprobar el segundo (derecho)*/
    				if(cuadrado[array[id].x][array[id].y].activateSquare()){
    					publishProgress(1,gm.getVariable("R.id.sq"+array[id].x+"_"+array[id].y));
    					point++;
    				}
    				/*a�adir puntos y turno*/
    				if(point>0)
    					publishProgress(3,point);
    			}
    			publishProgress(2);
    		}
    		/*palo no activo*/
    	}
    	else{
    		/*horizontal*/
    		if(horizontal[array[id].x][array[id].y].activateStick()){
    			/*palo activado*/
    			publishProgress(0,id);
    			if(array[id].x==0||array[id].x==xTam){
    				/*un solo cuadrado*/
    				if(array[id].x==0){
    					/*esquina superior*/
    					if(cuadrado[array[id].x][array[id].y].activateSquare()){
        					publishProgress(1,gm.getVariable("R.id.sq"+array[id].x+"_"+array[id].y));
        					publishProgress(3,1);
        				}
    				}
    				else{
    					/*esquina inferior*/
    					if(cuadrado[array[id].x-1][array[id].y].activateSquare()){
        					publishProgress(1,gm.getVariable("R.id.sq"+(array[id].x-1)+"_"+array[id].y));
        					publishProgress(3,1);
        				}
    				}
    			}
    			else{
    				/*dos cuadrados*/
    				int point=0;
    				/*comprobar el primero (superior)*/
    				if(cuadrado[array[id].x-1][array[id].y].activateSquare()){
    					publishProgress(1,gm.getVariable("R.id.sq"+(array[id].x-1)+"_"+array[id].y));
    					point++;
    				}
    				/*comprobar el segundo (inferior)*/
    				if(cuadrado[array[id].x][array[id].y].activateSquare()){
    					publishProgress(1,gm.getVariable("R.id.sq"+array[id].x+"_"+array[id].y));
    					point++;
    				}
    				/*a�adir puntos y turno*/
    				if(point>0)
    					publishProgress(3,point);
    				
    			}
    			publishProgress(2);
    		}
    		/*palo no activo*/
    	}
		//gm.comprobation();
	}
	
	/**
	 * On progress update.
	 *
	 * @param arg the arg
	 */
	@Override
	protected void onProgressUpdate(Integer... arg) {
       switch(arg[0]){
       case 0:
    	   gm.activateStick(arg[1]);
    	   break;
       case 1:
    	   gm.activateSquare(arg[1]);
    	   break;
       case 2:
    	   gm.changeTurn();
    	   break;
       case 3:
    	   gm.addPoint(arg[1]);
    	   break;
       }
    }

}
