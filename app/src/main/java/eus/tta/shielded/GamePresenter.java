package eus.tta.shielded;

/**
 * Created by ainhoa on 10/01/2016.
 */
public class GamePresenter implements IF_vp_game, IF_mp_game{
    private IF_pv_game vista;
    private IF_pm_game model;
    private boolean firtsStick;
    private int last_x;
    private int last_y;
    private int last_user;
    private boolean last_vertical;

    public GamePresenter (IF_pv_game vista, int map, int theme, int type, int id,String user, String password){
        this.vista = vista;
        firtsStick = true;
        this.vista.loadTheme(theme);
        model = new GameModel(this,map,theme,type,id,user,password);
        this.vista.changeTurn(model.getTurn());
        int scores [] = model.getScores();
        this.vista.changeScore(scores[0],scores[1]);
    }

    @Override
    public void loadMap(int maxX, int maxY){
        this.vista.generateTable(maxX, maxY);
    }

    @Override
    public void stickPressed(int x, int y, boolean vertical) {
        model.stickPressed(x, y, vertical);
    }

    @Override
    public void activeStick(int x, int y, int user,boolean vertical) {
        if(firtsStick){
           /*primer palo de la partida*/
            vista.activeStick(x,y,user,vertical);
            firtsStick=false;
            last_x = x;
            last_y = y;
            last_user = user;
            last_vertical = vertical;
        }else{
            /*ya se han activado palos anterioremente*/
            vista.changeStick(last_x,last_y,last_user,last_vertical);
            vista.activeStick(x,y,user,vertical);
            last_x = x;
            last_y = y;
            last_user = user;
            last_vertical = vertical;

        }
    }

    @Override
    public void activeSquare(int x, int y, int user) {
        vista.activeSquare(x,y,user);
        int scores [] = model.getScores();
        this.vista.changeScore(scores[0], scores[1]);
    }

    @Override
    public void finish(int score1, int score2){
        vista.finish(score1, score2);
    }

    @Override
    public void changeTurn(){
        this.vista.changeTurn(model.getTurn());
    }

    @Override
    public void disable(){
        vista.disable();
    }

    @Override
    public void enable(){
        vista.enable();
    }
}
