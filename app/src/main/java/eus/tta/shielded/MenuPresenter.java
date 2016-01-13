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
    public void toPresenterModelo() {
        int state = modelo.getState();
        int IA = modelo.getIA();
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
    public void selectIAPresenterVista(int id) {
        switch (id) {
            case R.id.bt_easy:
                modelo.setIALevel(1);
                break;
            case R.id.bt_medium:
                modelo.setIALevel(2);;
                break;
            case R.id.bt_hard:
                modelo.setIALevel(3);
                break;
        }
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
    public void selectThemePresenterVista(int id){
        switch(id){
            case R.id.bt_japon:
                modelo.setTheme(1);
                break;
            case R.id.bt_vikingo:
                modelo.setTheme(2);
                break;
            case R.id.bt_egipto:
                modelo.setTheme(3);
                break;
            case R.id.bt_hyrule:
                modelo.setTheme(4);
                break;
        }
    }
    @Override
    public void toMapsPresenterVista(){
        modelo.toMapsModelo();
    }
    @Override
    public void onBackPressedPresenterVista(){
        int state = modelo.getState();
        int IA = modelo.getIA();
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
