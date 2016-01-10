package eus.tta.shielded;

/**
 * Created by kevin on 10/01/16.
 */
public class MenuPresenter implements IF_vp_menu {
    IF_pv_menu vista;
    public MenuPresenter(IF_pv_menu vista){
        this.vista=vista;
    }

    @Override
    public void comenzar() {
        vista.toMenu();

    }
}
