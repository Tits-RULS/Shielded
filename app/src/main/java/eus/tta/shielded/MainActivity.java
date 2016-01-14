package eus.tta.shielded;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity implements IF_pv_menu{
	/*-- Atributos --*/
	//Audio player
	private MediaPlayer player;
	//Bluetooth activo o no
	private boolean bt=false;
	//Rol de Bluetooth
	private short btRole;

	IF_vp_menu presentador;

	/*-- Metodos sobreescritos --*/
	//Metodo al arrancar la actividad (cuando el jugador arranque juego)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.cover);
		player = MediaPlayer.create(this, R.raw.menu);
		player.setLooping(true);
		presentador = new MenuPresenter(this);
	}

	//Método OnStart
	@Override
	protected void onStart(){
		System.out.println("Main: onStart");
		super.onStart();
	}

	//Método OnResume
	@Override
	protected void onResume(){
		System.out.println("Main: onResume");
		player.start();
		super.onResume();
	}

	@Override
	public void toCoverVista(){
		setContentView(R.layout.cover);
	}
	//Metodo para pasar al menú inicial
	@Override
	public void toMenuVista(){
		setContentView(R.layout.menu);
	}
	//Metodo para ir al modo Campaña
	@Override
	public void toCampanaVista(){
		//Proximanente
	}
	@Override
	public void toMatchVista(){
		setContentView(R.layout.menu_match);
	}
	//Metodo para ir al modo Versus
	@Override
	public void toVSVista(){
		setContentView(R.layout.menu_vs);
	}
	//Metodo para ir a Ajustes
	@Override
	public void toSettingsVista(){
		//Proximamente
	}

	@Override
	public void toThemeVista(){
		setContentView(R.layout.menu_theme);
	}

	@Override
	public void toMapsVista(){
		setContentView(R.layout.menu_dimension);
	}

	@Override
	public void toGameVista(){
		setContentView(R.layout.loading_layout);

		Intent intent;
		if(presentador.getIA()){
			intent = new Intent(getBaseContext(), GameIA.class);
			/*set IA*/
			intent.putExtra("dificult", presentador.getIALevel());
		}
		else{
			/*if(bt){
				intent = new Intent(getBaseContext(),GameServer.class);
			}
			else{*/
				/*Linea para llamar al juego en versión MVP (no tocar)*/
			intent = new Intent(getBaseContext(), GameActivity.class);
			//}
		}

		/*set map*/
		intent.putExtra("map",presentador.getMap());

		/*set theme*/
		intent.putExtra("theme", presentador.getTheme());

		startActivity(intent);
	}
	@Override
	public void toExitVista(){
		super.onBackPressed();
	}

	@Override
	protected void onPause(){
		System.out.println("Main: onPause");
		player.pause();
		super.onPause();
	}

	@Override
	protected void onStop(){
		System.out.println("Main: onStop");
		super.onStop();
		presentador.onStopPresenterVista();
	}

	@Override
	protected void onDestroy(){
		player.stop();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		presentador.onBackPressedPresenterVista();
	}

	/*-- Métodos de clase --*/
	public void toCover(View view){
		presentador.toCoverPresenterVista();
	}
	//Método para pasar al menú inicial
	public void toMenu(View view){
		presentador.toMenuPresenterVista();
	}
	//Metodo para ir al modo Campaña
	public void toCampana(View view){
		presentador.toCampanaPresenterVista();
	}

	//Metodo para ir al modo Partida Rapida
	public void toMatch(View view){
		presentador.toMatchPresenterVista();
	}

	public void selectIA(View view){
		int id = view.getId();
		presentador.selectIAPresenterVista(id);
		toTheme(view);
	}

	//Metodo para ir al modo Versus
	public void toVS (View view){
		presentador.toVSPresenterVista();
	}

	//Metodo para ir a Ajustes
	public void toSettings (View view){
		presentador.toSettingsPresenterVista();
	}

	public void toTheme (View view){
		presentador.toThemePresenterVista();
	}

	public void selectTheme(View view){
		int id = view.getId();
		presentador.selectThemePresenterVista(id);

		/*if(bt){
			if(btRole==2){
				//lanzar la actividad clientes
				Intent intent;
				state=GAME;
				intent = new Intent(getBaseContext(),GameCliente.class);
				intent.putExtra("theme",theme);
				intent.putExtra("map", -1);
				startActivity(intent);
				return;
			}

		}*/
		toMaps(view);
	}

	public void toMaps (View view) {
		presentador.toMapsPresenterVista();
	}

	public void selectMap(View view){
		int id = view.getId();
		presentador.selectMapPresenterVista(id);
		start(view);
	}

	public void start(View view){
		presentador.toGamePresenterVista();
	}

	/*public void toThemeBT(View view){
		bt=true;
		toTheme(view);
		switch(view.getId()){
		case R.id.server:
			btRole=1;
			break;
		case R.id.client:
			System.out.println("SELECCIONADO CLIENTE");
			btRole=2;
			break;
		}
	}*/

}
