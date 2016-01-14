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
    private boolean IA; //IA activa (1) o no (0)
    private short state = COVER; //Estado en el que se encuentra la aplicacion
    private int IALevel;
    private int themeID;
    private int mapID;

    /*-- Métodos de clase --*/
    public MenuModel(IF_mp_menu presentador){
        this.presentador=presentador;
    }

    /*-- Métodos sobreescritos --*/
    @Override
    public void toCoverModelo(){
        state=COVER;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toMenuModelo() {
        state=MENU;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toCampanaModelo(){
        state=HISTORY;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toMatchModelo() {
        state=MATCH;
        IA=true;
        presentador.toPresenterModelo();
    }
    @Override
    public void toVSModelo() {
        state=MATCH;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toSettingsModelo(){
        state=SETTINGS;
        IA=false;
        presentador.toPresenterModelo();
    }
    @Override
    public void toThemeModelo(){
        state=THEME;
        presentador.toPresenterModelo();
    }

    @Override
    public void toMapsModelo(){
        state=MAP;
        presentador.toPresenterModelo();
    }

    @Override
    public void toGameModelo(){
        state=GAME;
        presentador.toPresenterModelo();
    }

    @Override
    public int getState(){
        return state;
    }

    @Override
    public boolean getIA(){
        return IA;
    }

    @Override
    public int getIALevel(){
        return themeID;
    }
    @Override
    public void setIALevel(int IALevel){
        this.IALevel = IALevel;
    }

    @Override
    public int getTheme(){
        return themeID;
    }
    @Override
    public void setTheme(int theme){
        this.themeID = theme;
    }

    @Override
    public int getMap(){
        return mapID;
    }
    @Override
    public void setMap(int map){
        this.mapID = map;
    }
}
