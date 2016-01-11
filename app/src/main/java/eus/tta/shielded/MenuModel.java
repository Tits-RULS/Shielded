package eus.tta.shielded;

/**
 * Created by kevin on 10/01/16.
 */
public class MenuModel implements IF_pm_menu{
    /*-- Atributos --*/
    IF_mp_menu presentador;

    private final int COVER = 0;
    private final int MENU = 1;		//
    private final int MATCH = 2;
    private final int THEME = 3;	//select theme
    private final int MAP = 4;		//select map
    private final int GAME = 5;		//playing
    private final int HISTORY = 6;
    private final int SETTINGS = 7;
    private int IA=0; //IA activa (1) o no (0)
    private short state = COVER; //Estado en el que se encuentra la aplicacion

    public MenuModel(IF_mp_menu presentador){
        this.presentador=presentador;
    }

    @Override
    public void toCoverModelo(){
        state=COVER;
        IA=0;
        presentador.toPresenterModelo(state,IA);
    }
    @Override
    public void toMenuModelo() {
        state=MENU;
        IA=0;
        presentador.toPresenterModelo(state, IA);
    }
    @Override
    public void toCampanaModelo(){
        state=HISTORY;
        IA=0;
        presentador.toPresenterModelo(state, IA);
    }
    @Override
    public void toMatchModelo() {
        state=MATCH;
        IA=1;
        presentador.toPresenterModelo(state, IA);
    }
    @Override
    public void toVSModelo() {
        state=MATCH;
        IA=0;
        presentador.toPresenterModelo(state, IA);
    }
    @Override
    public void toSettingsModelo(){
        state=SETTINGS;
        IA=0;
        presentador.toPresenterModelo(state, IA);
    }
    @Override
    public void toThemeModelo(){
        state=THEME;
        presentador.toPresenterModelo(state, IA);
    }

    @Override
    public void toMapsModelo(){
        state=MAP;
        presentador.toPresenterModelo(state,IA);
    }
}
