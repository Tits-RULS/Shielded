package eus.tta.shielded;

import eus.tta.shielded.Data;
import eus.tta.shielded.Player;
import eus.tta.shielded.R;
import eus.tta.shielded.Square;
import eus.tta.shielded.Stick;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class Game.
 */
public class Game extends Activity {

	protected Bundle saveInstance;
	
	protected Stick vertical[][];
	protected Stick horizontal[][];
	
	protected Square cuadrado[][];
	
	protected Player jugador1;
	protected Player jugador2;

	protected Data array[];

	protected int squaresActivated;

	protected int exceptions[];
	
	/*par�metros de dimensiones*/
	protected int  xTam=3;
	protected int  yTam=3;
	protected int buttons=24;
	protected int squares=9;
	protected int mapLayout=R.layout.activity_game;
	
	/*par�metros para salida por pantalla*/
	protected Drawable square1=null;
	protected Drawable square2=null;
	protected Drawable stickv1=null;
	protected Drawable stickv2=null;
	protected Drawable stickh1=null;
	protected Drawable stickh2=null;
	protected Drawable stickv1r=null;
	protected Drawable stickv2r=null;
	protected Drawable stickh1r=null;
	protected Drawable stickh2r=null;
	

	protected int music;

	protected int mapid;
	protected Integer map;
	protected Drawable bck;
	
	/*variables para controlar el �ltimo pulso*/
	protected View lastView;
	protected int lastPlayer;
	protected Data lastData;
	protected boolean firstButton;
	protected RelativeLayout rl;
	protected MediaPlayer player;
	protected MediaPlayer buttonSound;
	protected MediaPlayer shieldSound;

	private MediaPlayer endSound;
	
	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saveInstance=savedInstanceState;
		
		firstButton=true;
		
		/*seleccionar mapa*/
		Bundle extras = getIntent().getExtras();
		map = extras.getInt("map");
		switch(map){
		case 0:
			//3x3
			xTam=3;
			yTam=3;
			buttons=24;
			squares=9;
			mapLayout=R.layout.activity_game;
			mapid=R.id.rl3_3;
			break;
		case 1:
			//4x4
			xTam=4;
			yTam=4;
			buttons=40;
			squares=16;
			mapLayout=R.layout.activity_game4_4;
			mapid=R.id.rl4_4;
			break;
		case 2:
			//4x5
			xTam=5;
			yTam=4;
			buttons=50;
			squares=20;
			mapLayout=R.layout.activity_game4_5;
			mapid=R.id.rl4_5;
			exceptions=new int [1];
			exceptions[0]=48;
			break;
		}
		
		/*selecci�n de tema*/
		int theme = extras.getInt("theme");
		switch(theme){
		case 1:
			/*japon*/
			square1=getResources().getDrawable(R.drawable.japonrojo);
			square2=getResources().getDrawable(R.drawable.japonverde);
			stickv1=getResources().getDrawable(R.drawable.katanaroja2);
			stickv2=getResources().getDrawable(R.drawable.katanaverde2);
			stickh1=getResources().getDrawable(R.drawable.katanarojah2);
			stickh2=getResources().getDrawable(R.drawable.katanaverdeh2);
			stickv1r=getResources().getDrawable(R.drawable.katanaresaltadar);
			stickv2r=getResources().getDrawable(R.drawable.katanaresaltadav);
			stickh1r=getResources().getDrawable(R.drawable.katanaresaltadarh);
			stickh2r=getResources().getDrawable(R.drawable.katanaresaltadavh);
			music=R.raw.juegojapon;
			bck = getResources().getDrawable(R.drawable.japon);
			break;
		case 2:
			square1=getResources().getDrawable(R.drawable.vikingorojo);
			square2=getResources().getDrawable(R.drawable.vikingoverde);
			stickv1=getResources().getDrawable(R.drawable.vikingaroja);
			stickv2=getResources().getDrawable(R.drawable.vikingaverde);
			stickh1=getResources().getDrawable(R.drawable.vikingarojah);
			stickh2=getResources().getDrawable(R.drawable.vikingaverdeh);
			stickv1r=getResources().getDrawable(R.drawable.vikingaresaltadar);
			stickv2r=getResources().getDrawable(R.drawable.vikingaresaltadav);
			stickh1r=getResources().getDrawable(R.drawable.vikingaresaltadarh);
			stickh2r=getResources().getDrawable(R.drawable.vikingaresaltadavh);
			music=R.raw.juegovikingo;
			bck = getResources().getDrawable(R.drawable.vikingo);
			break;
		case 3:
			square1=getResources().getDrawable(R.drawable.egipciorojo);
			square2=getResources().getDrawable(R.drawable.egipcioverde);
			stickv1=getResources().getDrawable(R.drawable.egipciaroja);
			stickv2=getResources().getDrawable(R.drawable.egipciaverde);
			stickh1=getResources().getDrawable(R.drawable.egipciarojah);
			stickh2=getResources().getDrawable(R.drawable.egipciaverdeh);
			stickv1r=getResources().getDrawable(R.drawable.egipciaresaltadar);
			stickv2r=getResources().getDrawable(R.drawable.egipciaresaltadav);
			stickh1r=getResources().getDrawable(R.drawable.egipciaresaltadarh);
			stickh2r=getResources().getDrawable(R.drawable.egipciaresaltadavh);
			music=R.raw.juegoegipto;
			bck = getResources().getDrawable(R.drawable.egipto);
			break;
		case 4:
			square1=getResources().getDrawable(R.drawable.hyrulerojo2);
			square2=getResources().getDrawable(R.drawable.hyruleverde2);
			stickv1=getResources().getDrawable(R.drawable.maestraroja);
			stickv2=getResources().getDrawable(R.drawable.maestraverde);
			stickh1=getResources().getDrawable(R.drawable.maestrarojah);
			stickh2=getResources().getDrawable(R.drawable.maestraverdeh);
			stickv1r=getResources().getDrawable(R.drawable.maestraresaltadar);
			stickv2r=getResources().getDrawable(R.drawable.maestraresaltadav);
			stickh1r=getResources().getDrawable(R.drawable.maestraresaltadarh);
			stickh2r=getResources().getDrawable(R.drawable.maestraresaltadavh);
			music=R.raw.juegohirule;
			bck = getResources().getDrawable(R.drawable.hyrule);
			break;
		}
		
		/*musica de fondo*/
		player = MediaPlayer.create(this, music);
		player.setLooping(true);
		
		System.out.println("map: "+map);
		
		/*inicializamos las clases del juego*/
		if(map!=-1){
			System.out.println("GAME: Cargar clases");
			vertical=new Stick[xTam][yTam+1];
			horizontal=new Stick[xTam+1][yTam];
			cuadrado= new Square[xTam][yTam];
			jugador1=new Player(true,1);
			jugador2=new Player(false,0);
			array=new Data[buttons];
			squaresActivated=0;
		
		
			int sqx=0, sqy=0;
			/*dar valor a las clases*/
			/*inicializar los arrays*/
			for(sqx=0;sqx<(xTam+1);sqx++){
				for(sqy=0;sqy<yTam;sqy++){
					horizontal[sqx][sqy] = new Stick();
				}
			}
		
			for(sqx=0;sqx<xTam;sqx++){
				for(sqy=0;sqy<(yTam+1);sqy++){
					vertical[sqx][sqy]= new Stick();
				}
			}
		
			/*asignar stick a square*/
			for(sqx=0;sqx<xTam;sqx++){
				for(sqy=0;sqy<yTam;sqy++){
					cuadrado[sqx][sqy] = new Square(horizontal[sqx][sqy],horizontal[sqx+1][sqy],vertical[sqx][sqy],vertical[sqx][sqy+1]);
				}
			}	
		
			if(exceptions!=null){
				/*crear lo tabla de conversi�n*/
				int exc=0;
				for(int i=0;i<buttons;i++){
					if(exceptions[exc]!=i){
						array[i]= new Data();
						if(i%2==0){
							/*horizontales*/
							array[i].vertical=false;
							array[i].x=i/(yTam*2);
							array[i].y=(i%(yTam*2))/2;
						}
						else{
							/*verticales*/
							array[i].vertical=true;
							array[i].x=i/((yTam+1)*2);
							array[i].y=(i%((yTam+1)*2))/2;
						}
					}
					else{
						if(exceptions.length<exc){
							exc++;	
						}
					}
				}
			}
			else{
				for(int i=0;i<buttons;i++){
					array[i]= new Data();
					if(i%2==0){
						/*horizontales*/
						array[i].vertical=false;
						array[i].x=i/(yTam*2);
						array[i].y=(i%(yTam*2))/2;
					}
					else{
						/*verticales*/
						array[i].vertical=true;
						array[i].x=i/((yTam+1)*2);
						array[i].y=(i%((yTam+1)*2))/2;
					}
				}
			
			}
		
			/*cargar sonido para botones*/
			buttonSound = MediaPlayer.create(this, R.raw.corte);
			shieldSound = MediaPlayer.create(this, R.raw.shield);
		
			setContentView(mapLayout);
		
			/*fondo*/
			rl = (RelativeLayout) findViewById(mapid);
			rl.setBackgroundDrawable(bck);
		
			for(int i=0; i<buttons;i ++){
				View bt= findViewById(getVariable("R.id.button"+i));
				bt.setId(i);
			}
		}
	}
	
	/**
	 * On start.
	 */
	@Override
    protected void onStart(){
		System.out.println("Game: onstart");
    	super.onStart();
    	if(jugador1!=null){
    		System.out.println("ONSTART: MAL");
    		int pj1= jugador1.getScore();
    		int pj2= jugador2.getScore();
    	
    		TextView tv1 = (TextView) findViewById(R.id.txt1);
    		TextView tv2 = (TextView) findViewById(R.id.txt2);
    		
    		tv1.setText("Player1: "+pj1);
    		tv1.setTextColor(Color.RED);
    		tv2.setText("Player2: "+pj2);
    		tv2.setTextColor(Color.GREEN);
    		if(jugador1.isTurn()){
				tv1.setTextSize(24);
				tv2.setTextSize(14);
    		}
    		else{
    			tv1.setTextSize(14);
				tv2.setTextSize(24);
    		}
    	}
    	
    }
    
    /**
     * On resume.
     */
    @Override
    protected void onResume(){
    	System.out.println("Game: onResume");
    	player.start();
    	super.onResume();
    }
    
    /**
     * On pause.
     */
    @Override
    protected void onPause(){
    	System.out.println("Game: onPause NUEVO");
    	player.pause();
    	super.onPause();
    	System.out.println("Game: onPause FIN");
    }
    
    /**
     * On stop.
     */
    @Override
    protected void onStop(){
    	System.out.println("Game: onStop");
    	super.onStop();
    }
    
    /**
     * On destroy.
     */
    @Override
    protected void onDestroy(){
    	System.out.println("Game: onDestroy");
    	player.stop();
    	super.onDestroy();
    }
    
    /**
     * On back pressed.
     */
    @Override
    public void onBackPressed() {
    	System.out.println("back");
    	super.onBackPressed();
    }


	/**
	 * Event.
	 *
	 * @param view the view
	 */
	public void event(View view){
    	int id=view.getId();
    	
    	new Computation().execute(this,id,xTam,yTam);    	 
    }
    
    /**
     * Comprobation.
     */
    protected void comprobation(){
    	/*comprobar si el juego a terminado*/
		if(squaresActivated>=squares){
			String st;
			if(jugador1.getScore()>jugador2.getScore()){
				st = "JUGADOR 1 GANA :"+jugador1.getScore()+"vs"+jugador2.getScore();
			}
			else{
				if(jugador2.getScore()>jugador1.getScore()){
					st = "JUGADOR 2 GANA :"+jugador1.getScore()+"vs"+jugador2.getScore();
				}
				else{
					st = "EMPATE";
				}
			}
			endSound = MediaPlayer.create(this, R.raw.winner);
			endSound.start();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(st).setCancelable(false)
				.setPositiveButton("Volver a jugar",
					new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface arg0, int arg1){
							player.stop();
							endSound.stop();
							onCreate(saveInstance);
							onStart();
							onResume();
						}
				})
				.setNegativeButton("Salir",
					new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface arg0, int arg1){
							endSound.stop();
							onBackPressed();
						}
				});
			AlertDialog gameOverDialog = builder.create();
			gameOverDialog.show();
			
		}
    }
    
    /**
     * Activate square.
     *
     * @param id the id
     */
    protected void activateSquare(int id){
    	System.out.println("Mostrar cuadrado activado");
    	squaresActivated++;
    	View view  = findViewById(id);
    	/*comprobar jugador*/
    	if(jugador1.isTurn()){
    		//view.setBackgroundColor(0x8800FF00);
    		view.setBackgroundDrawable(square1);
    	}
    	else{
    		//view.setBackgroundColor(0x880000FF);
    		view.setBackgroundDrawable(square2);
    	}
    	shieldSound.start();
    	comprobation();

    }
    
    /**
     * Activate stick.
     *
     * @param id the id
     */
    protected void activateStick(int id){
    	/*comprobar jugador*/
    	View  view = findViewById(id);
    	System.out.println("Mostrar palo activado");
    	if(!firstButton){
    		System.out.println("CAMBIAR A ESPADAS");
    		/*sistuir el remarcado por el normal*/
    		if(lastPlayer==1){
    			if(!lastData.vertical){
    				/*horizontal*/
    				//lastView.setBackgroundColor(0x00000000);
    				lastView.setBackgroundDrawable(stickh1);
    			}
    			else{
    				/*vertical*/
    				//lastView.setBackgroundColor(0x00000000);
    				lastView.setBackgroundDrawable(stickv1);
    			}
    		}
    		else{
    			//view.setBackgroundColor(0xFF0000FF);
    			if(!lastData.vertical){
    				/*horizontal*/
    				//lastView.setBackgroundColor(0x00000000);
    				lastView.setBackgroundDrawable(stickh2);
    			}
    			else{
    				/*vertical*/
    				//lastView.setBackgroundColor(0x00000000);
    				lastView.setBackgroundDrawable(stickv2);
    			}
    		}
    	}
    	else{
    		firstButton=false;
    	}
    	
    	/*poner el remarcado (nuevo)*/
    	if(jugador1.isTurn()){
    		//view.setBackgroundColor(0xFF00FF00);
    		if(id%2==0){
    			/*horizontal*/
    			view.setBackgroundDrawable(stickh1r);
    			//view.setBackgroundColor(0xFFFF0000);
    		}
    		else{
    			/*vertical*/
    			view.setBackgroundDrawable(stickv1r);
    			//view.setBackgroundColor(0xFFFF0000);
    		}
    		jugador1.selectionDown();
    	}
    	else{
    		//view.setBackgroundColor(0xFF0000FF);
    		if(id%2==0){
    			/*horizontal*/
    			view.setBackgroundDrawable(stickh2r);
    			//view.setBackgroundColor(0xFF00FF00);
    		}
    		else{
    			/*vertical*/
    			view.setBackgroundDrawable(stickv2r);
    			//view.setBackgroundColor(0xFF00FF00);
    		}
    		jugador2.selectionDown();
    	}
    	
    	lastView=view;
		if(jugador1.isTurn())
			lastPlayer=1;
		else
			lastPlayer=2;
		lastData = array[id];
    	buttonSound.start();
    }
    
    
    /**
     * Adds the point.
     *
     * @param point the point
     */
    protected void addPoint(int point){

    	if(jugador1.isTurn()){
    		for(int i=0;i<point;i++){
    			jugador1.addPoint();
    		}
    		jugador1.extraTurn();
    	}
    	else{
    		for(int i=0;i<point;i++){
    			jugador2.addPoint();
    		}
    		jugador2.extraTurn();
    	}

    	int pj1= jugador1.getScore();
    	int pj2= jugador2.getScore();
    	
    	TextView tv1 = (TextView) findViewById(R.id.txt1);
    	TextView tv2 = (TextView) findViewById(R.id.txt2);

    	tv1.setText("Player1: "+pj1);
    	tv2.setText("Player2: "+pj2);

    }
    
    /**
     * Change turn.
     */
    protected void changeTurn(){
    	if(jugador1.isTurn()){
    		if(jugador1.getSelections_left()<=0){
    			jugador1.changeTurn(0);
    			jugador2.changeTurn(1);
    			TextView tw1 = (TextView) findViewById(R.id.txt1);
    			tw1.setTextSize(14);
    			TextView tw2 = (TextView) findViewById(R.id.txt2);
    			tw2.setTextSize(24);
    		}
    	}
    	else{
    		if(jugador2.getSelections_left()<=0){
    			jugador2.changeTurn(0);
    			jugador1.changeTurn(1);
    			TextView tw1 = (TextView) findViewById(R.id.txt1);
    			tw1.setTextSize(24);
    			TextView tw2 = (TextView) findViewById(R.id.txt2);
    			tw2.setTextSize(14);
    		}
    	}
    }
    
		
	/**
	 * Gets the variable.
	 *
	 * @param str the str
	 * @return the variable
	 */
	protected int getVariable(String str){
		if(str.equals("R.id.button0"))
			return R.id.button0;
		if(str.equals("R.id.button1"))
			return R.id.button1;
		if(str.equals("R.id.button2"))
			return R.id.button2;
		if(str.equals("R.id.button3"))
			return R.id.button3;
		if(str.equals("R.id.button4"))
			return R.id.button4;
		if(str.equals("R.id.button5"))
			return R.id.button5;
		if(str.equals("R.id.button6"))
			return R.id.button6;
		if(str.equals("R.id.button7"))
			return R.id.button7;
		if(str.equals("R.id.button8"))
			return R.id.button8;
		if(str.equals("R.id.button9"))
			return R.id.button9;
		if(str.equals("R.id.button10"))
			return R.id.button10;
		if(str.equals("R.id.button11"))
			return R.id.button11;
		if(str.equals("R.id.button12"))
			return R.id.button12;
		if(str.equals("R.id.button13"))
			return R.id.button13;
		if(str.equals("R.id.button14"))
			return R.id.button14;
		if(str.equals("R.id.button15"))
			return R.id.button15;
		if(str.equals("R.id.button16"))
			return R.id.button16;
		if(str.equals("R.id.button17"))
			return R.id.button17;
		if(str.equals("R.id.button18"))
			return R.id.button18;
		if(str.equals("R.id.button19"))
			return R.id.button19;
		if(str.equals("R.id.button20"))
			return R.id.button20;
		if(str.equals("R.id.button21"))
			return R.id.button21;
		if(str.equals("R.id.button22"))
			return R.id.button22;
		if(str.equals("R.id.button23"))
			return R.id.button23;
		if(str.equals("R.id.button24"))
			return R.id.button24;
		if(str.equals("R.id.button25"))
			return R.id.button25;
		if(str.equals("R.id.button26"))
			return R.id.button26;
		if(str.equals("R.id.button27"))
			return R.id.button27;
		if(str.equals("R.id.button28"))
			return R.id.button28;
		if(str.equals("R.id.button29"))
			return R.id.button29;
		if(str.equals("R.id.button30"))
			return R.id.button30;
		if(str.equals("R.id.button31"))
			return R.id.button31;
		if(str.equals("R.id.button32"))
			return R.id.button32;
		if(str.equals("R.id.button33"))
			return R.id.button33;
		if(str.equals("R.id.button34"))
			return R.id.button34;
		if(str.equals("R.id.button35"))
			return R.id.button35;
		if(str.equals("R.id.button36"))
			return R.id.button36;
		if(str.equals("R.id.button37"))
			return R.id.button37;
		if(str.equals("R.id.button38"))
			return R.id.button38;
		if(str.equals("R.id.button39"))
			return R.id.button39;
		if(str.equals("R.id.button40"))
			return R.id.button40;
		if(str.equals("R.id.button41"))
			return R.id.button41;
		if(str.equals("R.id.button42"))
			return R.id.button42;
		if(str.equals("R.id.button43"))
			return R.id.button43;
		if(str.equals("R.id.button44"))
			return R.id.button44;
		if(str.equals("R.id.button45"))
			return R.id.button45;
		if(str.equals("R.id.button46"))
			return R.id.button46;
		if(str.equals("R.id.button47"))
			return R.id.button47;
		if(str.equals("R.id.button48"))
			return R.id.button48;
		if(str.equals("R.id.button49"))
			return R.id.button49;
		if(str.equals("R.id.sq0_0"))
			return R.id.sq0_0;
		if(str.equals("R.id.sq0_1"))
			return R.id.sq0_1;
		if(str.equals("R.id.sq0_2"))
			return R.id.sq0_2;
		if(str.equals("R.id.sq0_3"))
			return R.id.sq0_3;;
		if(str.equals("R.id.sq1_0"))
			return R.id.sq1_0;
		if(str.equals("R.id.sq1_1"))
			return R.id.sq1_1;
		if(str.equals("R.id.sq1_2"))
			return R.id.sq1_2;
		if(str.equals("R.id.sq1_3"))
			return R.id.sq1_3;
		if(str.equals("R.id.sq2_0"))
			return R.id.sq2_0;
		if(str.equals("R.id.sq2_1"))
			return R.id.sq2_1;
		if(str.equals("R.id.sq2_2"))
			return R.id.sq2_2;
		if(str.equals("R.id.sq2_3"))
			return R.id.sq2_3;
		if(str.equals("R.id.sq3_0"))
			return R.id.sq3_0;
		if(str.equals("R.id.sq3_1"))
			return R.id.sq3_1;
		if(str.equals("R.id.sq3_2"))
			return R.id.sq3_2;
		if(str.equals("R.id.sq3_3"))
			return R.id.sq3_3;
		if(str.equals("R.id.sq4_0"))
			return R.id.sq4_0;
		if(str.equals("R.id.sq4_1"))
			return R.id.sq4_1;
		if(str.equals("R.id.sq4_2"))
			return R.id.sq4_2;
		if(str.equals("R.id.sq4_3"))
			return R.id.sq4_3;
		return -1;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Data[] getData(){
		return array;
	}
	
	/**
	 * Gets the vertical.
	 *
	 * @return the vertical
	 */
	public Stick[][] getVertical(){
		return vertical;
	}
	
	/**
	 * Gets the horizontal.
	 *
	 * @return the horizontal
	 */
	public Stick[][] getHorizontal(){
		return horizontal;
	}
	
	/**
	 * Gets the cuadrado.
	 *
	 * @return the cuadrado
	 */
	public Square[][] getCuadrado(){
		return cuadrado;
	}
	
	/**
	 * Gets the xtam.
	 *
	 * @return the xtam
	 */
	public int getXtam(){
		return xTam;
	}
	
	/**
	 * Gets the ytam.
	 *
	 * @return the ytam
	 */
	public int getYtam(){
		return yTam;
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
    			activateStick(id);
    			if(array[id].y==0||array[id].y==yTam){
    				System.out.println("Palo de un borde");
    				/*un solo cuadrado*/
    				if(array[id].y==0){
    					/*esquina izquierda*/
    					if(cuadrado[array[id].x][array[id].y].activateSquare()){
    						System.out.println("Palo de esquina izquierda");
    						activateSquare(getVariable("R.id.sq"+array[id].x+"_"+array[id].y));
    						addPoint(1);
        				}
    				}
    				else{
    					/*esquina derecha*/
    					if(cuadrado[array[id].x][array[id].y-1].activateSquare()){
    						System.out.println("Palo de esquina derecha");
    						activateSquare(getVariable("R.id.sq"+array[id].x+"_"+(array[id].y-1)));
        					addPoint(1);
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
    					activateSquare(getVariable("R.id.sq"+array[id].x+"_"+(array[id].y-1)));
    					point++;
    				}
    				/*comprobar el segundo (derecho)*/
    				if(cuadrado[array[id].x][array[id].y].activateSquare()){
    					activateSquare(getVariable("R.id.sq"+array[id].x+"_"+array[id].y));
    					point++;
    				}
    				/*a�adir puntos y turno*/
    				if(point>0)
    					addPoint(point);
    			}
    			changeTurn();
    		}
    		/*palo no activo*/
    	}
    	else{
    		/*horizontal*/
    		if(horizontal[array[id].x][array[id].y].activateStick()){
    			/*palo activado*/
    			activateStick(id);
    			if(array[id].x==0||array[id].x==xTam){
    				/*un solo cuadrado*/
    				if(array[id].x==0){
    					/*esquina superior*/
    					if(cuadrado[array[id].x][array[id].y].activateSquare()){
    						activateSquare(getVariable("R.id.sq"+array[id].x+"_"+array[id].y));
    						addPoint(1);
        				}
    				}
    				else{
    					/*esquina inferior*/
    					if(cuadrado[array[id].x-1][array[id].y].activateSquare()){
    						activateSquare(getVariable("R.id.sq"+(array[id].x-1)+"_"+array[id].y));
    						addPoint(1);
        				}
    				}
    			}
    			else{
    				/*dos cuadrados*/
    				int point=0;
    				/*comprobar el primero (superior)*/
    				if(cuadrado[array[id].x-1][array[id].y].activateSquare()){
    					activateSquare(getVariable("R.id.sq"+(array[id].x-1)+"_"+array[id].y));
    					point++;
    				}
    				/*comprobar el segundo (inferior)*/
    				if(cuadrado[array[id].x][array[id].y].activateSquare()){
    					activateSquare(getVariable("R.id.sq"+array[id].x+"_"+array[id].y));
    					point++;
    				}
    				/*a�adir puntos y turno*/
    				if(point>0)
    					addPoint(point);
    				
    			}
    			changeTurn();
    		}
    		/*palo no activo*/
    	}
		comprobation();
	}


}
