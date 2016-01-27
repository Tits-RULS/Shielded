package eus.tta.shielded;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    private int ID;
    //Parametros de login
    private String nickname;
    private String password;
    private int resultado;
    private String picPath;
    private JSONArray matches;

    /*-- Métodos de clase --*/
    public MenuModel(IF_mp_menu presentador){
        this.nickname=null;
        this.password=null;
        this.presentador=presentador;
        httpClient = new HttpClient("http://51.254.221.215");
    }

    /*-- Métodos sobreescritos --*/
    @Override
    public void toCoverModelo(){
        state=COVER;
        IA=false;
    }
    @Override
    public void toMenuModelo() {
        state=MENU;
        IA=false;
    }
    @Override
    public void toCampanaModelo(){
        state=HISTORY;
        IA=false;
    }
    @Override
    public void toMatchModelo() {
        state=MATCH;
        IA=true;
    }
    @Override
    public void toVSModelo() {
        state=MATCH;
        IA=false;
    }

    @Override
    public void toOnlineModelo(){
        state=ONLINE;
        IA=false;
    }
    @Override
    public void toSettingsModelo(){
        state=SETTINGS;
        IA=false;
    }
    @Override
    public void toThemeModelo(){
        state=THEME;
    }

    @Override
    public void toMapsModelo(){
        state=MAP;
    }

    @Override
    public void toGameModelo(){
        state=GAME;
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
    public void loadUser(String nick,String pss) {
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
    public void loadMatches() {
        if (this.nickname==null || this.password==null) {
            setResultado(-3);
        } else {
            try {
                JSONObject json = httpClient.getJson(String.format("getmatches.php?user=%s&password=%s", this.nickname, this.password));
                System.out.println("Perfil: " + json);
                matches = json.getJSONArray("matches");
                setMatches(matches);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void uploadPictureModelo(String picPath){
        String nick = getNick();
        String pss = getPassword();
        String userPath = String.format("foto.php?user=%s&password=%s", nick, pss);
        File file = new File(picPath);
        String name = file.getName();
        try {
            InputStream is = new FileInputStream(picPath);
            httpClient.postFile(userPath, is, name);
        }catch(Exception e){

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
    public JSONArray getMatches(){
        return matches;
    }
    @Override
    public void setMatches(JSONArray matches){
        this.matches = matches;
    }

    @Override
    public int getMap(){
        return mapID;
    }
    @Override
    public void setMap(int map){
        this.mapID = map;
    }

    @Override
    public int getId(){
        return ID;
    }

    @Override
    public void setId(int id){
        this.ID = id;
    }

}
