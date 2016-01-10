package eus.tta.shielded;

/**
 * Created by kevin on 10/01/16.
 */
public class MenuModel implements IF_pm_menu{
    /*-- Atributos --*/
    IF_mp_menu presentador;
    private short state; //Estado en el que se encuentra la aplicacion
    private final int IA = 1; //IA activa (1) o no (0)

    public MenuModel(IF_mp_menu presentador){
        this.presentador=presentador;
    }

    @Override
    public void toMenuModelo(){
        state=IA;
        presentador.toMenuPresenterModelo();
        System.out.println("Baja hasta el modelo" + state);
    }
    @Override
    public void toCampanaModelo(){
        state=IA;
        System.out.println("Baja hasta el modelo 2");
    }
    @Override
    public void toMatchModelo(){
        state=IA;
    }
    @Override
    public void toVSModelo(){
        state=IA;
    }
    @Override
    public void toSettingsModelo(){
        state=IA;
    }
}
