package eus.tta.shielded;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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

		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		String nick=sharedPreferences.getString("PREF_NICK", null);
		String pss=sharedPreferences.getString("PREF_PASS", null);
		String pic=sharedPreferences.getString("PREF_PIC",null);

		presentador.initOnlinePresenterVista(nick, pss, pic);

		//prefs = this.getPreferences("eus.tta.shielded", MODE_PRIVATE);
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

	@Override
	public void toOnlineVista(){
		setContentView(R.layout.menu_server);
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

		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		String nick=sharedPreferences.getString("PREF_NICK", null);
		String pss=sharedPreferences.getString("PREF_PASS",null);

		/*set user*/
		intent.putExtra(ViewConstant.EXTRA_USER, nick);

		/*set user*/
		intent.putExtra(ViewConstant.EXTRA_PASSWORD, pss);

		/*set type*/
		intent.putExtra(ViewConstant.EXTRA_TYPE, presentador.getType());

		/*set map*/
		intent.putExtra(ViewConstant.EXTRA_MAP, presentador.getMap());

		/*set theme*/
		intent.putExtra(ViewConstant.EXTRA_THEME, presentador.getTheme());

		/*set id*/
		intent.putExtra(ViewConstant.EXTRA_ID, presentador.getId());
		startActivity(intent);
	}
	@Override
	public void toExitVista(){
		super.onBackPressed();
	}

	@Override
	public void notificacionesVista(String toast){
		switch(toast){
			case "register":
				Toast.makeText(getApplicationContext(),R.string.register_please,Toast.LENGTH_SHORT).show();
				break;
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
	public void disableLoginVista(){
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		String nick=sharedPreferences.getString("PREF_NICK", null);
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
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		String nick=sharedPreferences.getString("PREF_NICK", null);

		if(nick!=null) {
			TextView tv = (TextView) findViewById(R.id.user_name);
			tv.setText(nick);
			String pic = sharedPreferences.getString("PREF_PIC",null);
			if(pic.startsWith("http")){
				getBitmapFromURL getBitmap = new getBitmapFromURL() {
				};
				getBitmap.execute();
			}
			else{
				ImageView img = (ImageView) findViewById(R.id.user_pic);
				img.setVisibility(View.VISIBLE);
				img.setImageURI(Uri.parse(pic));
			}

		}
	}

	@Override
	public void updateOnlineVista(int id_match, String user1, String user2, String foto2){

		GridLayout gl = new GridLayout(this);
		GridLayout.LayoutParams gl_params = new GridLayout.LayoutParams();
		gl_params.width=GridLayout.LayoutParams.WRAP_CONTENT;
		gl_params.height=GridLayout.LayoutParams.WRAP_CONTENT;
		gl.setLayoutParams(gl_params);
		gl.setColumnCount(2);
		gl.setRowCount(1);

		Button bt = new Button(this);
		bt.setId(id_match);
		bt.setText("Continuar");
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toOnlineMatch(v);
			}
		});

		gl.addView(bt);
		TextView tv = new TextView(this);
		tv.setText(user1 + " VS " + user2);
		tv.setTypeface(null, Typeface.BOLD);
		tv.setTextColor(Color.BLACK);
		tv.setBackgroundColor(Color.WHITE);
		gl.addView(tv);

		final LinearLayout ll = (LinearLayout) findViewById(R.id.online_list);
		System.out.println("What: " + gl);
		ll.addView(gl);
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

	public void toOnline (View view){
		loadOnline clase = new loadOnline() {
		};
		clase.execute();

	}

	public void toNewOnline(View view){
		presentador.toNewOnlinePresenterVista();
		start(view);
	}

	public void toOnlineMatch (View view){
		presentador.toOnlineMatchPresenterVista(view.getId());
		start(view);
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
		TareaAsincrona clase = new TareaAsincrona() {
		};
		clase.execute();
	}

	//@Override
	public void saveLoginVista(){

		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString("PREF_NICK", presentador.getNickname());
		editor.putString("PREF_PASS", presentador.getPassword());
		editor.putString("PREF_PIC", presentador.getPicture());
		editor.commit();

		System.out.println("UserPref: "+sharedPreferences.getString("PREF_NICK","garcia"));
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
				uploadPhoto clase = new uploadPhoto() {
				};
				clase.execute();
			}
			if (requestCode == PICTURE_REQUEST_CODE){
				Uri takenImageUri = data.getData();
				String selectedImagePath = getPath(takenImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				presentador.selectPhotoPresenterVista(selectedImagePath);
				uploadPhoto clase = new uploadPhoto() {
				};
				clase.execute();

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

	/*-- Clases --*/
	private abstract class TareaAsincrona extends AsyncTask<Void, Integer, Boolean> {
		String pss;
		String nick;
		@Override
		protected void onPreExecute(){
			System.out.println("En el pre");
			EditText editLogin = (EditText) findViewById(R.id.login);
			EditText editPasswd = (EditText) findViewById(R.id.password);
			this.pss = editPasswd.getText().toString();
			this.nick = editLogin.getText().toString();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			System.out.println("Durante");
			presentador.saveUserPresenterVista(nick, pss);
			//modelo.setPassword(pss);

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			System.out.println("En el post");
			saveLoginVista();
			presentador.showUserPresenterVista();
		}

	}

	private abstract class getBitmapFromURL extends AsyncTask<Void, Integer, Boolean> {
		Bitmap bitmap;
		String pic;
		ImageView img;

		@Override
		protected void onPreExecute(){
			SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
			this.pic=sharedPreferences.getString("PREF_PIC", null);
			this.img = (ImageView) findViewById(R.id.user_pic);
			img.setVisibility(View.VISIBLE);
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			try{
				URL url = new URL(pic);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				this.bitmap = BitmapFactory.decodeStream(input);

			}catch(IOException e){

			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			img.setImageBitmap(bitmap);
		}

	}

	private abstract class loadOnline extends AsyncTask<Void, Integer, Boolean> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			System.out.println("Durante");
			presentador.loadMatchesPresenterVista();
			//modelo.setPassword(pss);

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			System.out.println("En el post");
			SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
			String nick = sharedPreferences.getString("PREF_NICK", null);

			if(nick == null){
				presentador.showUserPresenterVista();
			}
			else{
				presentador.toOnlinePresenterVista();
				presentador.showMatchesPresenterVista();
			}
		}
	}

	private abstract class uploadPhoto extends AsyncTask<Void, Integer, Boolean> {
		String picPath;
		@Override
		protected void onPreExecute() {
			SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
			picPath = sharedPreferences.getString("PREF_PIC", null);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			System.out.println("Durante");
			presentador.uploadPicturePresenterVista(picPath);

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			System.out.println("En el post");
			updateUserVista();
		}
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
