package eus.tta.shielded;

import android.database.Cursor;
import android.graphics.AvoidXfermode;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

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
    public void toPresenterModelo() {
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
        }
    }

    @Override
    public void toCoverPresenterVista(){

    }
    @Override
    public void toMenuPresenterVista() {
        modelo.toMenuModelo();
    }
    @Override
    public void toCampanaPresenterVista(){
        modelo.toCampanaModelo();
    }
    @Override
    public void toMatchPresenterVista() {
        modelo.toMatchModelo();
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
    }
    @Override
    public void toSettingsPresenterVista(){
        modelo.toSettingsModelo();
    }

    @Override
    public void toThemePresenterVista(){
        modelo.toThemeModelo();
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
                break;
            case IF_pm_menu.MATCH://Match
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                break;
            case IF_pm_menu.THEME://Theme
                if(modelo.getIA()) {
                    System.out.println("Volviendo a Partida");
                    modelo.toMatchModelo();
                }else {
                    System.out.println("Volviendo a Versus");
                    modelo.toVSModelo();
                }
                break;
            case IF_pm_menu.MAP://Map
                System.out.println("Volviendo a Temas");
                modelo.toThemeModelo();
                break;
            case IF_pm_menu.GAME://Game
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                break;
            case IF_pm_menu.SETTINGS://Settings
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                break;
            case IF_pm_menu.HISTORY://history
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                break;
        }
    }

    @Override
    public void onStopPresenterVista(){
        switch(modelo.getState()){
            case 5:
                modelo.toCoverModelo();
                break;
            default:
                break;
        }
    }

    @Override
    public void saveUserPresenterVista(String nick, String pss){
        if(nick.isEmpty() || pss.isEmpty()){
            vista.notificacionesVista("void");
        }
        else {
            modelo.loadUser(nick, pss);
            modelo.setNick(nick);
            //modelo.setPassword(pss);

        }
    }

    @Override
    public void showUserPresenterModelo(){
        System.out.println("Resultado: " + modelo.getResultado());
        switch (modelo.getResultado()){
            case -1:
                vista.notificacionesVista("wrong");
                break;
            case 0:
                vista.notificacionesVista("new");
                vista.disableLoginVista();
                vista.updateUserVista();
                break;
            case 1:
                vista.notificacionesVista("loged");
                System.out.println("Si ya existe...");
                vista.disableLoginVista();
                vista.updateUserVista();
                break;
        }
    }

    @Override
    public void selectPhotoPresenterVista(String photoPath){
        modelo.setPic(photoPath);
    }

    @Override
    public String getNickname(){
        return modelo.getNick();
    }

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
}
