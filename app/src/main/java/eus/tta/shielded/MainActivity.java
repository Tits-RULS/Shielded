package eus.tta.shielded;


import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


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

	private static final int SELECT_PICTURE = 1;
	private static final int PICTURE_REQUEST_CODE=2;

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
		updateUserVista();
	}
	//Metodo para ir al modo Campaña
	@Override
	public void toCampanaVista(){
		//Proximanente
	}
	@Override
	public void toMatchVista(){
		setContentView(R.layout.menu_match);
		updateUserVista();
	}
	//Metodo para ir al modo Versus
	@Override
	public void toVSVista(){
		setContentView(R.layout.menu_vs);
		updateUserVista();
	}
	//Metodo para ir a Ajustes
	@Override
	public void toSettingsVista(){
		setContentView(R.layout.menu_settings);
		disableLoginVista();
		updateUserVista();
	}

	@Override
	public void toThemeVista(){
		setContentView(R.layout.menu_theme);
		updateUserVista();
	}

	@Override
	public void toMapsVista(){
		setContentView(R.layout.menu_dimension);
		updateUserVista();
	}

	@Override
	public void toGameVista(){
		setContentView(R.layout.loading_layout);

		Intent intent = new Intent(getBaseContext(), GameActivity.class);

		/*set type*/
		intent.putExtra(ViewConstant.EXTRA_TYPE, presentador.getType());

		/*set map*/
		intent.putExtra(ViewConstant.EXTRA_MAP, presentador.getMap());

		/*set theme*/
		intent.putExtra(ViewConstant.EXTRA_THEME, presentador.getTheme());

		startActivity(intent);
	}
	@Override
	public void toExitVista(){
		super.onBackPressed();
	}

	@Override
	public void notificacionesVista(String toast){
		switch(toast){
			case "foto":
				Toast.makeText(getApplicationContext(),R.string.horizontal_cam,Toast.LENGTH_SHORT).show();
				break;
			case "void":
				Toast.makeText(getApplicationContext(),R.string.void_user,Toast.LENGTH_SHORT).show();
				break;
			case "wrong":
				Toast.makeText(getApplicationContext(),R.string.wrong_user,Toast.LENGTH_SHORT).show();
				break;
			case "new":
				Toast.makeText(getApplicationContext(),R.string.new_user,Toast.LENGTH_SHORT).show();
				break;
			case "loged":
				Toast.makeText(getApplicationContext(),R.string.loged_user,Toast.LENGTH_SHORT).show();
				break;
		}
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

	//Metodo para ir a Ajustes
	public void toSettings (View view){
		presentador.toSettingsPresenterVista();
	}

	public void login (View view){
		EditText editLogin = (EditText) findViewById(R.id.login);
		EditText editPasswd = (EditText) findViewById(R.id.password);
		final String pss = editPasswd.getText().toString();
		final String nick = editLogin.getText().toString();
		presentador.saveUserPresenterVista(nick, pss);
	}

	@Override
	public void disableLoginVista(){
		String nick=presentador.getNickname();
		if(nick!=null) {
			GridLayout gl1 =(GridLayout) findViewById(R.id.login_grid);
			gl1.setVisibility(View.GONE);
			Button bt =(Button) findViewById(R.id.login_button);
			bt.setVisibility(View.GONE);
			GridLayout gl2 =(GridLayout) findViewById(R.id.photo_grid);
			gl2.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void updateUserVista(){
		String nick=presentador.getNickname();
		String pic=presentador.getPicture();
		if(nick!=null) {
			TextView tv = (TextView) findViewById(R.id.user_name);
			tv.setText(nick);
			if (pic != null) {
				ImageView img = (ImageView) findViewById(R.id.user_pic);
				img.setVisibility(View.VISIBLE);
				img.setImageURI(Uri.parse(pic));
			}
		}
	}

	public void selectPhoto(View view){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
	}

	public void takePhoto(View view){
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		notificacionesVista("foto");
		startActivityForResult(intent, PICTURE_REQUEST_CODE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				String selectedImagePath = getPath(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				presentador.selectPhotoPresenterVista(selectedImagePath);
				updateUserVista();
			}
			if (requestCode == PICTURE_REQUEST_CODE){
				Uri takenImageUri = data.getData();
				String selectedImagePath = getPath(takenImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				presentador.selectPhotoPresenterVista(selectedImagePath);
				updateUserVista();
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
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
