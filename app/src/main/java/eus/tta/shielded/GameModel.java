package eus.tta.shielded;

import android.util.Log;

/**
 * Created by ainhoa on 10/01/2016.
 */
public class GameModel implements IF_pm_game {

    static final public int MAP_3X3 = 0;
    static final public int MAP_4X4 = 1;
    static final public int MAP_5X4 = 2;

    static final public int TYPE_VS = 0;
    static final public int TYPE_EIA = 1;
    static final public int TYPE_MIA = 2;
    static final public int TYPE_HIA = 3;
    static final public int TYPE_NS = 4;
    static final public int TYPE_OS = 5;

    private IF_mp_game presenter;
    private int type;

    /*variables para la gestión del juego*/
    private Stick vertical[][];
    private Stick horizontal[][];
    private Square square [][];

    private Player player1;
    private Player player2;

    private int squaresActivated;


    /*parámetros de dimensiones*/
    private int xTam = 3;
    private int yTam = 4;
    private int squares = 9;

    private IA ia;


    public GameModel(IF_mp_game presenter,int map, int type, int id){
        this.presenter = presenter;
        this.type = type;
        switch(type){
            case TYPE_VS:
                /*genereta map*/
                loadMap(map);
                break;
            case TYPE_EIA:
                /*genereta map*/
                loadMap(map);
                ia = new EasyIA(vertical,horizontal,square,xTam,yTam);
                break;
            case TYPE_MIA:
                loadMap(map);
                ia = new MediumIA(vertical,horizontal,square,xTam,yTam);
                break;
            case TYPE_HIA:
                loadMap(map);
                ia = new HardIA(vertical,horizontal,square,xTam,yTam);
                break;
        }
            }

    private void loadMap(int map){
        switch (map){
            case MAP_3X3:
                //3x3
                xTam=3;
                yTam=3;
                squares=9;
                break;
            case MAP_4X4:
                //4x4
                xTam=4;
                yTam=4;
                squares=16;
                break;
            case MAP_5X4:
                //4x5
                xTam=5;
                yTam=4;
                squares=20;
                break;
        }

        /*initialize game classes*/
        vertical = new Stick[xTam][yTam+1];
        horizontal = new Stick[xTam+1][yTam];
        square = new Square[xTam][yTam];
        player1 = new Player(true,1);
        player2 = new Player(false,0);
        squaresActivated = 0;

        /*fill the arrays*/
        int sqx=0, sqy=0;
			/*dar valor a las clases*/
			/*inicializar los arrays*/
        for(sqx=0;sqx<(xTam+1);sqx++){
            for(sqy=0;sqy<yTam;sqy++){
                horizontal[sqx][sqy] = new Stick();
            }
        }

        for(sqx=0;sqx<xTam;sqx++){
            for(sqy=0;sqy<(yTam+1);sqy++){
                vertical[sqx][sqy]= new Stick();
            }
        }

			/*asignar stick a square*/
        for(sqx=0;sqx<xTam;sqx++){
            for(sqy=0;sqy<yTam;sqy++){
                square[sqx][sqy] = new Square(horizontal[sqx][sqy],horizontal[sqx+1][sqy],vertical[sqx][sqy],vertical[sqx][sqy+1]);
            }
        }
    }

    @Override
    public void stickPressed(int x, int y, boolean vertical) {
        switch (type){
            case TYPE_VS:
                processTurn(x,y,vertical);
                break;
            case TYPE_EIA:
                iaProcessTurn(x,y,vertical);
                break;
            case  TYPE_MIA:
                iaProcessTurn(x,y,vertical);
                break;
            case  TYPE_HIA:
                iaProcessTurn(x,y,vertical);
                break;
        }
    }

    private void iaProcessTurn(final int x,final  int y,final  boolean vertical){
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    if(player1.isTurn()){
                        processTurn(x,y,vertical);
                    }else {
                        try {
                            Thread.sleep(500);
                            Data stick = ia.turn();
                            processTurn(stick.x, stick.y, stick.vertical);
                        }catch (Exception e) {
                            Log.e("demo", e.getMessage(), e);
                        }
                    }
                }while (player2.isTurn());
            }
        }).start();
    }

    private void processTurn(int x, int y, boolean vertical){
        boolean stickAct = false;
        boolean squareAct = false;
        if(vertical){
            /*palo vertical*/
            if(this.vertical[x][y].activateStick()){
                /*palo activado*/
                stickAct = true;
                if(player1.isTurn()){
                    presenter.activeStick(x,y,1,vertical);
                }else{
                    presenter.activeStick(x,y,2,vertical);
                }
                /*comprobar si es una esquina*/
                if(y==0||y==yTam){
                    /*un solo cuadrado*/
                    if(y==0){
                        /*izquierda*/
                        if(square[x][y].activateSquare()){
                            /*activar cuadrado*/
                            squareAct = true;
                            squaresActivated++;
                            if(player1.isTurn()){
                                player1.addPoint();
                                presenter.activeSquare(x,y,1);
                            }else {
                                player2.addPoint();
                                presenter.activeSquare(x,y,2);
                            }
                        }
                    }else{
                        /*derecha*/
                        if(square[x][y-1].activateSquare()){
                            /*activar cuadrado*/
                            squareAct = true;
                            squaresActivated++;
                            if(player1.isTurn()){
                                player1.addPoint();
                                presenter.activeSquare(x,y-1,1);
                            }else {
                                player2.addPoint();
                                presenter.activeSquare(x,y-1,2);
                            }
                        }
                    }
                }else{
                    /*dos cuadrados*/
                    /*comprobar cuadrado izquierdo*/
                    if(square[x][y-1].activateSquare()){
                        /*activar cuadrado*/
                        squareAct = true;
                        squaresActivated++;
                        if(player1.isTurn()){
                            player1.addPoint();
                            presenter.activeSquare(x,y-1,1);
                        }else {
                            player2.addPoint();
                            presenter.activeSquare(x,y-1,2);
                        }
                    }
                    /*comprobar segundo cuadrado*/
                    if(square[x][y].activateSquare()){
                        /*activar cuadrado*/
                        squareAct = true;
                        squaresActivated++;
                        if(player1.isTurn()){
                            player1.addPoint();
                            presenter.activeSquare(x,y,1);
                        }else {
                            player2.addPoint();
                            presenter.activeSquare(x,y,2);
                        }
                    }
                }
            }
        }else{
            /*palo horizontal*/
            if(horizontal[x][y].activateStick()){
                /*palo activado*/
                stickAct = true;
                if(player1.isTurn()){
                    presenter.activeStick(x,y,1,vertical);
                }else{
                    presenter.activeStick(x,y,2,vertical);
                }
                /*comprobar si es esquina*/
                if(x==0||x==xTam){
                    /*un solo cuadrado*/
                    if(x==0){
                        /*esquina superior*/
                        if(square[x][y].activateSquare()){
                            /*activar cuadrado*/
                            squareAct = true;
                            squaresActivated++;
                            if(player1.isTurn()){
                                player1.addPoint();
                                presenter.activeSquare(x,y,1);
                            }else {
                                player2.addPoint();
                                presenter.activeSquare(x,y,2);
                            }
                        }
                    }else{
                        /*esquina inferior*/
                        if(square[x-1][y].activateSquare()){
                            /*activar cuadrado*/
                            squareAct = true;
                            squaresActivated++;
                            if(player1.isTurn()){
                                player1.addPoint();
                                presenter.activeSquare(x-1,y,1);
                            }else {
                                player2.addPoint();
                                presenter.activeSquare(x-1,y,2);
                            }
                        }
                    }
                }else {
                    /*dos cuadrados*/
                    /*comprobar cuadrado superior*/
                    if(square[x-1][y].activateSquare()){
                        /*activar cuadrado*/
                        squareAct = true;
                        squaresActivated++;
                        if(player1.isTurn()){
                            player1.addPoint();
                            presenter.activeSquare(x-1,y,1);
                        }else {
                            player2.addPoint();
                            presenter.activeSquare(x-1,y,2);
                        }
                    }
                    /*comprobar cuadrado inferior*/
                    if(square[x][y].activateSquare()){
                        /*activar cuadrado*/
                        squareAct = true;
                        squaresActivated++;
                        if(player1.isTurn()){
                            player1.addPoint();
                            presenter.activeSquare(x,y,1);
                        }else {
                            player2.addPoint();
                            presenter.activeSquare(x,y,2);
                        }
                    }
                }
            }
        }

        /*comprobar si se ha realizado el turno*/
        if(stickAct){
            /*turno realizado*/
            if(!squareAct){
                /*no se ha activado cuadrado, cambio de turno*/
                player1.changeTurn(1);
                player2.changeTurn(1);
                presenter.changeTurn();
            }else{
                /*comprobar si se ha terminado el juego*/
                if(squaresActivated>=squares){
                    /*juego terminado*/
                    presenter.finish(player1.getScore(),player1.getScore());
                }
            }
        }
    }

    @Override
    public int [] getDimensions(){
        int res [] = new int[2];
        res[0]=xTam;
        res[1]=yTam;
        return res;
    }

    @Override
    public int getTurn(){
        if(player1.isTurn()){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public int [] getScores(){
        int scores [] = new int[2];
        scores[0] = player1.getScore();
        scores[1] = player2.getScore();
        return scores;
    }
}
