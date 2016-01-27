package eus.tta.shielded;


import org.json.JSONArray;
import org.json.JSONException;


/**
 * Created by kevin on 10/01/16.
 */
public class MenuPresenter implements IF_vp_menu, IF_mp_menu {
    /*-- Atributos --*/
    IF_pv_menu vista;
    IF_pm_menu modelo;


    /*-- Métodos de clase --*/
    //Constructor
    public MenuPresenter(IF_pv_menu vista){
        this.vista=vista;
        modelo= new MenuModel(this);

    }

    /*-- Métodos sobreescritos --*/
    @Override
    public void initOnlinePresenterVista(String nick, String pss, String pic){
        System.out.println("Se inicializa: " + nick + pss + pic);
        modelo.setNick(nick);
        modelo.setPassword(pss);
        modelo.setPic(pic);
    }
    @Override
    public void toCoverPresenterVista(){

    }
    @Override
    public void toMenuPresenterVista() {
        modelo.toMenuModelo();
        changeVista();
    }
    @Override
    public void toCampanaPresenterVista(){
        modelo.toCampanaModelo();
        changeVista();
    }
    @Override
    public void toMatchPresenterVista() {
        modelo.toMatchModelo();
        changeVista();
    }

    @Override
    public void selectIAPresenterVista(int id) {
        switch (id) {
            case R.id.bt_easy:
                modelo.setType(ModelConstant.TYPE_EIA);
                break;
            case R.id.bt_medium:
                modelo.setType(ModelConstant.TYPE_MIA);
                break;
            case R.id.bt_hard:
                modelo.setType(ModelConstant.TYPE_HIA);
                break;
        }
    }
    @Override
    public void toVSPresenterVista(){
        modelo.setType(ModelConstant.TYPE_VS);
        modelo.toVSModelo();
        changeVista();
    }

    @Override
    public void toOnlinePresenterVista(){
        modelo.toOnlineModelo();
        changeVista();
    }

    @Override
    public void loadMatchesPresenterVista(){
        modelo.loadMatches();
    }

    @Override
    public void showMatchesPresenterVista(){
        JSONArray matches = modelo.getMatches();
        try {
            for (int i = 0; i < matches.length(); i++) {
                String user1 = matches.getJSONObject(i).getString("user1");
                String foto1 = matches.getJSONObject(i).getString("foto1");
                System.out.println("Foto match: " + foto1);
                String user2 = matches.getJSONObject(i).getString("user2");
                String foto2 = matches.getJSONObject(i).getString("foto2");
                int id_match = matches.getJSONObject(i).getInt("id");
                vista.updateOnlineVista(id_match, user2, foto2);
            }
        }catch(JSONException e){

        }
    }

    @Override
    public void toNewOnlinePresenterVista(){
        modelo.setType(ModelConstant.TYPE_NS);
    }

    @Override
    public void toOnlineMatchPresenterVista(){
        modelo.setType(ModelConstant.TYPE_OS);
    }
    @Override
    public void toSettingsPresenterVista(){
        modelo.toSettingsModelo();
        changeVista();
    }

    @Override
    public void toThemePresenterVista(){
        modelo.toThemeModelo();
        changeVista();
    }

    @Override
    public void selectThemePresenterVista(int id){
        switch(id){
            case R.id.bt_japon:
                modelo.setTheme(ModelConstant.THEME_JAPAN);
                break;
            case R.id.bt_vikingo:
                modelo.setTheme(ModelConstant.THEME_VIKING);
                break;
            case R.id.bt_egipto:
                modelo.setTheme(ModelConstant.THEME_EGIPT);
                break;
            case R.id.bt_hyrule:
                modelo.setTheme(ModelConstant.THEME_HYRULE);
                break;
        }
    }
    @Override
    public void toMapsPresenterVista(){
        modelo.toMapsModelo();
        changeVista();
    }

    @Override
    public void selectMapPresenterVista(int id){
        switch(id){
            case R.id.button3_3:
                modelo.setMap(ModelConstant.MAP_3X3);
                break;
            case R.id.button4_4:
                modelo.setMap(ModelConstant.MAP_4X4);
                break;
            case R.id.button4_5:
                modelo.setMap(ModelConstant.MAP_5X4);
                break;
        }
    }

    @Override
    public void toGamePresenterVista(){
        modelo.toGameModelo();
        changeVista();
    }

    @Override
    public void onBackPressedPresenterVista(){
        switch(modelo.getState()){
            case IF_pm_menu.COVER://Cover
                System.out.println("Saliendo");
                vista.toExitVista();
                break;
            case IF_pm_menu.MENU://Menu
                System.out.println("Volviendo a Cover");
                modelo.toCoverModelo();
                changeVista();
                break;
            case IF_pm_menu.MATCH://Match
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                changeVista();
                break;
            case IF_pm_menu.THEME://Theme
                if(modelo.getIA()) {
                    System.out.println("Volviendo a Partida");
                    modelo.toMatchModelo();
                    changeVista();
                }else {
                    System.out.println("Volviendo a Versus");
                    modelo.toVSModelo();
                    changeVista();
                }
                break;
            case IF_pm_menu.MAP://Map
                System.out.println("Volviendo a Temas");
                modelo.toThemeModelo();
                changeVista();
                break;
            case IF_pm_menu.GAME://Game
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                changeVista();
                break;
            case IF_pm_menu.SETTINGS://Settings
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                changeVista();
                break;
            case IF_pm_menu.HISTORY://history
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                changeVista();
                break;
            case IF_pm_menu.ONLINE:
                System.out.println("Volviendo a Versus");
                modelo.toVSModelo();
                changeVista();
                break;
        }
    }

    @Override
    public void onStopPresenterVista(){
        switch(modelo.getState()){
            case 5:
                modelo.toCoverModelo();
                changeVista();
                break;
            default:
                break;
        }
    }

    @Override
    public void saveUserPresenterVista(String nick, String pss){

            modelo.loadUser(nick, pss);
            modelo.setNick(nick);
            modelo.setPassword(pss);
    }

    @Override
    public void showUserPresenterVista(){
        System.out.println("Resultado: " + modelo.getResultado());
        switch (modelo.getResultado()){
            case -3:
                vista.notificacionesVista("register");
                modelo.setResultado(99);
                break;
            case -2:
                vista.notificacionesVista("void");
                modelo.setResultado(99);
                break;
            case -1:
                vista.notificacionesVista("wrong");
                modelo.setResultado(99);
                break;
            case 0:
                vista.notificacionesVista("new");
                vista.disableLoginVista();
                vista.updateUserVista();
                modelo.setResultado(99);
                break;
            case 1:
                vista.notificacionesVista("loged");
                System.out.println("Si ya existe...");
                vista.disableLoginVista();
                vista.updateUserVista();
                modelo.setResultado(99);
                break;
            case 99:
                break;
        }

    }

    @Override
    public void selectPhotoPresenterVista(String photoPath){
        modelo.setPic(photoPath);
    }

    @Override
    public void uploadPicturePresenterVista(String picPath){
        modelo.uploadPictureModelo(picPath);
    }
    @Override
    public String getNickname(){
        return modelo.getNick();
    }

    @Override
    public String getPassword() {return modelo.getPassword();}

    @Override
    public String getPicture(){return modelo.getPic();}

    @Override
    public boolean getIA(){return modelo.getIA();}

    @Override
    public int getType(){return modelo.getType();}

    @Override
    public int getTheme(){
        return modelo.getTheme();
    }

    @Override
    public int getMap(){
        return modelo.getMap();
    }

    /*-- Métodos de clase --*/
    public void changeVista() {
        switch(modelo.getState()){
            case IF_pm_menu.COVER:
                System.out.println("Pasando a Cover");
                vista.toCoverVista();
                break;
            case IF_pm_menu.MENU:
                System.out.println("Pasando a Menu");
                vista.toMenuVista();
                break;
            case IF_pm_menu.MATCH:
                if(modelo.getIA()) {
                    System.out.println("Pasando a Partida");
                    vista.toMatchVista();
                }else {
                    System.out.println("Pasando a Versus");
                    vista.toVSVista();
                }
                break;
            case IF_pm_menu.THEME:
                System.out.println("Pasando a Temas");
                vista.toThemeVista();
                break;
            case IF_pm_menu.MAP:
                System.out.println("Pasando a Mapas");
                vista.toMapsVista();
                break;
            case IF_pm_menu.GAME:
                System.out.println("Pasando al juego");
                vista.toGameVista();
                break;
            case IF_pm_menu.HISTORY:
                System.out.println("Pasando a Campaña");
                vista.toCampanaVista();
                break;
            case IF_pm_menu.SETTINGS:
                System.out.println("Pasando a Ajustes");
                vista.toSettingsVista();
                break;
            case IF_pm_menu.ONLINE:
                System.out.println("Pasando a Online");
                vista.toOnlineVista();
                break;
        }
    }
}
