package eus.tta.shielded;

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

    public void toThemePresenterVista();

    public void selectThemePresenterVista(int id);

    public void toMapsPresenterVista();

    public void onBackPressedPresenterVista();
}
