/*
 * 
 */
package eus.tta.shielded;

import android.os.AsyncTask;


// TODO: Auto-generated Javadoc
/**
 * The Class Computation.
 */
public class Computation extends AsyncTask<Object, Integer, Long>{

	/** The gm. */
	Game gm;
	
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		/**
		 * Do in background.
		 *
		 * @param arg the arg
		 * @return the long
		 */
		@Override
	protected Long doInBackground(Object... arg) {
		System.out.println("COMPUTATION");
		gm  =(Game) arg[0];
		Integer id = (Integer) arg[1];
		Stick vertical[][] = gm.getVertical();
		Stick horizontal[][] = gm.getHorizontal();
		Square cuadrado[][] = gm.getCuadrado();
		Data array[] = gm.getData();
		Integer xTam = (Integer) arg[2];
		Integer yTam = (Integer) arg[3];
		
		System.out.println("BOTON PULSADO");
		System.out.println("Comprobar horientaci�n");
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
			
		return null;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
	 */
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

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	/**
	 * On post execute.
	 *
	 * @param result the result
	 */
	@Override
    protected void onPostExecute(Long result) {
		gm.comprobation();
    }
}
