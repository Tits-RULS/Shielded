/*
 * 
 */
package eus.tta.shielded;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


// TODO: Auto-generated Javadoc
/**
 * The Class GameIA.
 */
public class GameIA extends Game {

	/** The ia. */
	private IA ia;
	
	/** The winner. */
	private MediaPlayer winner;
	
	/** The loser. */
	private MediaPlayer loser;
	
	/* (non-Javadoc)
	 * @see com.example.shielded2.Game#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saveInstance=savedInstanceState;
		
		/*seleccionar mapa*/
		Bundle extras = getIntent().getExtras();
		
		/*cargar musicas*/
		winner = MediaPlayer.create(this, R.raw.winner);
		loser = MediaPlayer.create(this, R.raw.loser);
		
		
		/*cargar IA*/
		int dificult = extras.getInt("dificult");
		switch(dificult){
		case 1:
			//ia = new EasyIA(buttons,exceptions,this);
			break;
		case 2:
			//ia = new MediumIA(buttons,exceptions,this);
			break;
		case 3:
			//ia = new HardIA(buttons,exceptions,this);
			break;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.example.shielded2.Game#event(android.view.View)
	 */
	@Override
	public void event(View view){
		if(jugador1.isTurn()){
			int id=view.getId();    	
			new Computation().execute(this,id,xTam,yTam);
		}
    }
	
	/**
	 * Event ia.
	 *
	 * @param view the view
	 */
	public void eventIA(View view){
		if(jugador2.isTurn()){
			int id=view.getId();    	
			new Computation().execute(this,id,xTam,yTam);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.example.shielded2.Game#comprobation()
	 */
	@Override
	protected void comprobation(){
		
    	/*comprobar si el juego a terminado*/
		if(squaresActivated>=squares){
			String st;
			if(jugador1.getScore()>jugador2.getScore()){
				st = "VICTORIA";
				player.stop();
				winner.start();
			}
			else{
				if(jugador2.getScore()>jugador1.getScore()){
					st = "DERROTA";
					player.stop();
					loser.start();
				}
				else{
					st = "EMPATE";
					player.stop();
					loser.start();
				}
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(st).setCancelable(false)
				.setPositiveButton("Volver a jugar",
					new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface arg0, int arg1){
							player.stop();
							winner.stop();
							loser.stop();
							onCreate(saveInstance);
							onStart();
							onResume();
						}
				})
				.setNegativeButton("Salir",
					new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface arg0, int arg1){
							winner.stop();
							loser.stop();
							onBackPressed();
						}
				});
			AlertDialog gameOverDialog = builder.create();
			gameOverDialog.show();
			
		}
		else{
			/*comprobar si le toca a IA*/
			if(jugador2.isTurn()){
				//int vwId= ia.turn();
				//System.out.println("ID= "+vwId);
				//View vw = findViewById(vwId);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			//	eventIA(vw);
			}
		}
    }
	
		
}


