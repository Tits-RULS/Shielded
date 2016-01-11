package eus.tta.shielded;

/**
 * Created by ainhoa on 10/01/2016.
 */
public interface IF_pm_game {

    void stickPressed(int x, int y,boolean vertical);

    int [] getDimensions();

    int getTurn();

    int [] getScores();
}
