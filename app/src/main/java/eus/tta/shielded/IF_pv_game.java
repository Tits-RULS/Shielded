package eus.tta.shielded;

/**
 * Created by ainhoa on 10/01/2016.
 */
public interface IF_pv_game {

    void loadTheme (int theme);

    void generateTable(int x, int y);

    void activeStick(int x, int y, int user, boolean vertical);

    void changeStick(int x, int y, int user, boolean vertical);

    void activeSquare(int x, int y, int user);

    void changeTurn(int player);

    void changeScore(int score1, int score2);

    void finish(int score1, int score2);

    void disable();

    void enable();

}
