package eus.tta.shielded;

import org.json.JSONArray;

/**
 * Created by kevin on 10/01/16.
 */

//Interfaz que comunica al presentador con el modelo
public interface IF_pm_menu {
    //Lista de estados
    int COVER = 0;
    int MENU = 1;		//
    int MATCH = 2;
    int THEME = 3;	//select theme
    int MAP = 4;		//select map
    int GAME = 5;		//playing
    int HISTORY = 6;
    int SETTINGS = 7;
    int ONLINE = 8;

    public void toCoverModelo();

    public void toMenuModelo();

    public void toSettingsModelo();

    public void toCampanaModelo();

    public void toMatchModelo();

    public void toVSModelo();

    public void toOnlineModelo();

    public void toThemeModelo();

    public void toMapsModelo();

    public void toGameModelo();

    public void setNick(String nick);

    public String getNick();

    public void setPassword(String pss);

    public String getPassword();

    public void setResultado(int resultado);

    public int getResultado();

    public void loadUser(String nick, String pss);

    public void loadMatches();

    public String getPic();

    public void setPic(String picPath);

    public int getState();

    public boolean getIA();

    public int getType();

    public void setType(int type);

    public int getTheme();

    public void setTheme(int theme);

    public int getMap();

    public void setMap(int map);

    public JSONArray getMatches();

    public void setMatches(JSONArray matches);

}
