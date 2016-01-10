package eus.tta.shielded;

import android.view.View;

/**
 * Created by kevin on 10/01/16.
 */

//Interfaz que comunica al presentador con la vista
public interface IF_pv_menu {
    public void toMenuVista();
    /*-- Métodos de clase --*/
    //Metodo para ir al modo Campaña
    public void toCampanaVista();

    //Metodo para ir al modo Partida Rapida
    public void toMatchVista();

    //Metodo para ir al modo Versus
    public void toVSVista ();

    //Metodo para ir a Ajustes
    public void toSettingsVista ();
}
