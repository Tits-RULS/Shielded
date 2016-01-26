package eus.tta.shielded;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by kevin on 10/01/16.
 */
public class MenuModel implements IF_pm_menu{
    /*-- Atributos --*/
    IF_mp_menu presentador;
    HttpClient httpClient;

    //Parámetros de decisión
    private boolean IA; //IA activa (1) o no (0)
    private short state = COVER; //Estado en el que se encuentra la aplicacion
    private int themeID;
    private int mapID;
    private int typeID;
    //Parametros de login
    private String nickname;
    private String password;
    private int resultado;
    private String picPath;

    /*-- Métodos de clase --*/
    public MenuModel(IF_mp_menu presentador){
        this.presentador=presentador;
        httpClient = new HttpClient("http://51.254.221.215");
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
    }

    @Override
    public String getPassword(){
        return password;
    }
    @Override
    public void setPassword(String pss){
        this.password=pss;
    }

    @Override
    public void loadUser(final String nick,final String pss) {
        if (nick.isEmpty() || pss.isEmpty()) {
            setResultado(-2);
        } else {
            try {
                JSONObject profile = httpClient.getJson(String.format("login.php?user=%s&password=%s", nick, pss));
                System.out.println("Perfil: " + profile);
                setResultado(profile.getInt("result"));
                String foto = profile.getString("foto");
                //String foto = "http://51.254.221.215/uploads/prueba";
                System.out.println("Foto: " + foto);
                if (foto.equals("null")){
                    //Si el usuario no tiene foto de perfil, imagen por defecto
                    System.out.println("La foto es null");
                    //Uri path = Uri.parse("android.resource://eus.tta.shielded/" + R.drawable.egipcioazul);
                    setPic("http://51.254.221.215/uploads/yunque.png");
                } else {
                    System.out.println("cargando foto...");
                    setPic(foto);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getResultado(){
        return resultado;
    }
    @Override
    public void setResultado(int resultado){
        System.out.println("ResultadoModelo: " + resultado);
        this.resultado=resultado;
    }

    public void pruebaServer() throws IOException, JSONException {
        JSONObject json = httpClient.getJson(String.format("getmatches.php?user=prueba&password=prueba"));
        System.out.println("Contenido: " + json);
    }

    @Override
    public String getPic(){
        return picPath;
    }
    @Override
    public void setPic(String picPath){
        this.picPath=picPath;
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

}
