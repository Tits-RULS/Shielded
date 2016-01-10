package eus.tta.shielded;

/**
 * Created by kevin on 10/01/16.
 */
public class MenuPresenter implements IF_vp_menu, IF_mp_menu {
    /*-- Atributos --*/
    IF_pv_menu vista;
    IF_pm_menu modelo;

    /*-- Métodos de clase --*/
    //Constructores
    public MenuPresenter(IF_pv_menu vista){
        this.vista=vista;
        modelo= new MenuModel(this);
    }

    /*-- Métodos sobreescritos --*/
    @Override
    public void toMenuPresenterModelo() {
        vista.toMenuVista();
    }
    @Override
    public void toCampanaPresenterModelo(){
        vista.toCampanaVista();
    }
    @Override
    public void toMatchPresenterModelo() {
        vista.toMatchVista();
    }
    @Override
    public void toVSPresenterModelo(){
        vista.toVSVista();
    }
    @Override
    public void toSettingsPresenterModelo(){
        vista.toSettingsVista();
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
    public void toVSPresenterVista(){
        modelo.toVSModelo();
    }
    @Override
    public void toSettingsPresenterVista(){
        modelo.toSettingsModelo();
    }
}
