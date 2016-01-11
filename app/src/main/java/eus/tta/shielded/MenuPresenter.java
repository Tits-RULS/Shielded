package eus.tta.shielded;

/**
 * Created by kevin on 10/01/16.
 */
public class MenuPresenter implements IF_vp_menu, IF_mp_menu {
    /*-- Atributos --*/
    IF_pv_menu vista;
    IF_pm_menu modelo;
    int state;
    int IA;

    /*-- Métodos de clase --*/
    //Constructores
    public MenuPresenter(IF_pv_menu vista){
        this.vista=vista;
        modelo= new MenuModel(this);
    }

    /*-- Métodos sobreescritos --*/
    @Override
    public void toPresenterModelo(int state, int ia) {
        this.state = state;
        this.IA=ia;
        switch(state){
            case 0:
                System.out.println("Pasando a Cover");
                vista.toCoverVista();
                break;
            case 1:
                System.out.println("Pasando a Menu");
                vista.toMenuVista();
                break;
            case 2:
                if(IA==0) {
                    System.out.println("Pasando a Versus");
                    vista.toVSVista();
                }else {
                    System.out.println("Pasando a Partida");
                    vista.toMatchVista();
                }
                break;
            case 3:
                System.out.println("Pasando a Temas");
                vista.toThemeVista();
                break;
            case 4:
                System.out.println("Pasando a Mapas");
                vista.toMapsVista();
                break;
            case 5:
                break;
            case 6:
                System.out.println("Pasando a Campaña");
                vista.toCampanaVista();
                break;
            case 7:
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
    public void toVSPresenterVista(){
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
    public void toMapsPresenterVista(){
        modelo.toMapsModelo();
    }
    @Override
    public void onBackPressedPresenterVista(){
        switch(state){
            case 0:
                System.out.println("Volviendo a Cover");
                vista.toExitVista();
                break;
            case 1:
                System.out.println("Volviendo a Menu");
                modelo.toCoverModelo();
                break;
            case 2:
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                break;
            case 3:
                if(IA==0) {
                    System.out.println("Volviendo a Versus");
                    modelo.toVSModelo();
                }else {
                    System.out.println("Volviendo a Partida");
                    modelo.toMatchModelo();
                }
                break;
            case 4:
                System.out.println("Volviendo a Temas");
                modelo.toThemeModelo();
                break;
            case 5:
                break;
            case 6:
                System.out.println("Volviendo al Menu");
                modelo.toMenuModelo();
                break;
            case 7:
                System.out.println("Volviendo al Menu");
                vista.toMenuVista();
                break;
        }
    }
}
