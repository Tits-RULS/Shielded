package eus.tta.shielded;

import android.content.Intent;

/**
 * Created by kevin on 10/01/16.
 */

//Interfaz que comunica a la vista con el presentador
public interface IF_vp_menu {
    public void toCoverPresenterVista();

    public void toMenuPresenterVista();
    //Metodo para ir al modo Campaña
    public void toCampanaPresenterVista();
    //Metodo para ir al modo Partida Rapida
    public void toMatchPresenterVista();

    public void selectIAPresenterVista(int id);
    //Metodo para ir al modo Versus
    public void toVSPresenterVista ();
    //Metodo para ir a Ajustes
    public void toSettingsPresenterVista ();

    public void saveUserPresenterVista(String nick, String pss);

    public void showUserPresenterVista();

    public void selectPhotoPresenterVista(String photoPath);

    public void toThemePresenterVista();

    public void selectThemePresenterVista(int id);

    public void toMapsPresenterVista();

    public void selectMapPresenterVista(int id);

    public void toGamePresenterVista();

    public void onBackPressedPresenterVista();

    public void onStopPresenterVista();

    public boolean getIA();
    public String getNickname();
    public String getPicture();
    public int getType();
    public int getTheme();
    public int getMap();
}
