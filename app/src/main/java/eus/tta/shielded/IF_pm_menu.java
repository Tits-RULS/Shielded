package eus.tta.shielded;

/**
 * Created by kevin on 10/01/16.
 */

//Interfaz que comunica al presentador con el modelo
public interface IF_pm_menu {

    public void toCoverModelo();

    public void toMenuModelo();

    public void toSettingsModelo();

    public void toCampanaModelo();

    public void toMatchModelo();

    public void toVSModelo();

    public void toThemeModelo();

    public void toMapsModelo();

    public void toGameModelo();

    public void setNick(String nick);

    public String getNick();

    public void setPassword(String pss);

    public String getPassword();

    public int getState();

    public boolean getIA();

    public int getType();

    public void setType(int type);

    public int getTheme();

    public void setTheme(int theme);

    public int getMap();

    public void setMap(int map);

}
