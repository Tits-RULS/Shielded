package eus.tta.shielded;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by kevin on 10/01/16.
 */
public class MenuModel implements IF_pm_menu{
    /*-- Atributos --*/
    IF_mp_menu presentador;
    //Lista de estados
    private final int COVER = 0;
    private final int MENU = 1;		//
    private final int MATCH = 2;
    private final int THEME = 3;	//select theme
    private final int MAP = 4;		//select map
    private final int GAME = 5;		//playing
    private final int HISTORY = 6;
    private final int SETTINGS = 7;
    //Lista de tipos
    private final int type_vs = 0; //1vs1
    private final int type_eia = 1; //easy
    private final int type_mia = 2; //medium
    private final int type_hia = 3; //hard
    private final int type_ns = 4; //new server
    private final int type_os = 5; //old server
    //Parámetros de decisión
    private boolean IA; //IA activa (1) o no (0)
    private short state = COVER; //Estado en el que se encuentra la aplicacion
    private int themeID;
    private int mapID;
    private int typeID;
    //Parametros de login
    private String nickname;
    private String password;

    /*-- Métodos de clase --*/
    public MenuModel(IF_mp_menu presentador){
        this.presentador=presentador;
    }

    /*-- Métodos sobreescritos --*/
    @Override
    public void toCoverModelo(){
        state=COVER;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toMenuModelo() {
        state=MENU;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toCampanaModelo(){
        state=HISTORY;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toMatchModelo() {
        state=MATCH;
        IA=true;
        presentador.toPresenterModelo();
    }
    @Override
    public void toVSModelo() {
        state=MATCH;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toSettingsModelo(){
        state=SETTINGS;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toThemeModelo(){
        state=THEME;
        presentador.toPresenterModelo();
    }

    @Override
    public void toMapsModelo(){
        state=MAP;
        presentador.toPresenterModelo();
    }

    @Override
    public void toGameModelo(){
        state=GAME;
        presentador.toPresenterModelo();
    }

    @Override
    public String getNick(){
        return nickname;
    }
    @Override
    public void setNick(String nick){
        this.nickname=nick;
        System.out.println(nickname);
    }

    @Override
    public String getPassword(){
        return password;
    }
    @Override
    public void setPassword(String pss){
        this.password=pss;
        System.out.println(password);
    }
    @Override
    public int getState(){
        return state;
    }

    @Override
    public boolean getIA(){
        return IA;
    }

    @Override
    public int getType(){
        return typeID;
    }

    @Override
    public void setType(int type){
        this.typeID=type;
    }

    @Override
    public int getTheme(){
        return themeID;
    }
    @Override
    public void setTheme(int theme){
        this.themeID = theme;
    }

    @Override
    public int getMap(){
        return mapID;
    }
    @Override
    public void setMap(int map){
        this.mapID = map;
    }


    public void sendFile(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.setType("*/*");
        //startActivityForResult(intent,READ_REQUEST_CODE);
    }

/* public void sendImage(View view){
     //Se comprueba que haya cámara
     if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
         Toast.makeText(getApplicationContext(), R.string.no_camera, Toast.LENGTH_SHORT).show();
     else{
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         //Se comprueba que haya aplicación para capturar imagen
         if(intent.resolveActivity(getPackageManager())!=null){
             //Hay aplicación para capturar imagen
                File dir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                try {
                    File file = File.createTempFile("tta", ".jpg", dir);
                    pictureURI = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureURI);
                    startActivityForResult(intent,PICTURE_REQUEST_CODE);
                }catch (IOException e){
                    Log.e("demo", e.getMessage(), e);
                }
            }else{
                //No hay aplicación para capturar imagen
                Toast.makeText(getApplicationContext(),R.string.no_app,Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
