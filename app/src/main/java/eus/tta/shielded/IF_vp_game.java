package eus.tta.shielded;

/**
 * Created by ainhoa on 10/01/2016.
 */
public interface IF_vp_game {

    int THEME_JAPAN = 1;
    int THEME_VIKING = 2;
    int THEME_EGIPT = 3;
    int THEME_HYRULE = 4;

    public void stickPressed(int x, int y, boolean vertical);
}
