package eus.tta.shielded;

/**
 * Created by ainhoa on 10/01/2016.
 */
public interface IF_mp_game {

    void activeStick(int x, int y, int user,boolean vertical);

    void activeSquare(int x, int y, int user);

    void finish(int score1, int score2);
}
