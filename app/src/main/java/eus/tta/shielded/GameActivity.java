package eus.tta.shielded;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class GameActivity extends Activity implements IF_pv_game, View.OnClickListener {

    /*public static String EXTRA_MAP = "eus.tta.shielded.map";
    public static String EXTRA_THEME = "eus.tta.shielded.theme";*/

    public static String EXTRA_MAP = "map";
    public static String EXTRA_THEME = "theme";

    private IF_vp_game presenter;

    /*Parámetros de salida por pantalla*/
    private Drawable square1=null;
    private Drawable square2=null;
    private Drawable stickv1=null;
    private Drawable stickv2=null;
    private Drawable stickh1=null;
    private Drawable stickh2=null;
    private Drawable stickv1r=null;
    private Drawable stickv2r=null;
    private Drawable stickh1r=null;
    private Drawable stickh2r=null;
    private Drawable background;
    private int music;

    private MediaPlayer player;
    private MediaPlayer buttonSound;
    private MediaPlayer shieldSound;

    private int maxX;
    private int maxY;

    @Override
    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.game);
        Intent intent = getIntent();
        int map = intent.getIntExtra(ViewConstant.EXTRA_MAP, 0);
        int theme = intent.getIntExtra(ViewConstant.EXTRA_THEME,1);
        int type = intent.getIntExtra(ViewConstant.EXTRA_TYPE,0);
        int id = intent.getIntExtra(ViewConstant.EXTRA_ID,0);
        presenter = new GamePresenter(this,map,theme,type,id);
    }

    @Override
    public void onResume(){
        player.start();
        super.onResume();
    }

    @Override
    public void onPause(){
        player.pause();
        super.onPause();
    }

    @Override
    public void onDestroy(){
        player.release();
        buttonSound.release();
        shieldSound.release();
        super.onDestroy();
    }

    @Override
    public void loadTheme(int theme) {
        switch(theme){
            case IF_vp_game.THEME_JAPAN:
			/*japon*/
                square1=getResources().getDrawable(R.drawable.japonrojo);
                square2=getResources().getDrawable(R.drawable.japonverde);
                stickv1=getResources().getDrawable(R.drawable.katanaroja2);
                stickv2=getResources().getDrawable(R.drawable.katanaverde2);
                stickh1=getResources().getDrawable(R.drawable.katanarojah2);
                stickh2=getResources().getDrawable(R.drawable.katanaverdeh2);
                stickv1r=getResources().getDrawable(R.drawable.katanaresaltadar);
                stickv2r=getResources().getDrawable(R.drawable.katanaresaltadav);
                stickh1r=getResources().getDrawable(R.drawable.katanaresaltadarh);
                stickh2r=getResources().getDrawable(R.drawable.katanaresaltadavh);
                music=R.raw.juegojapon;
                background = getResources().getDrawable(R.drawable.japon);
                break;
            case IF_vp_game.THEME_VIKING:
                /*viking*/
                square1=getResources().getDrawable(R.drawable.vikingorojo);
                square2=getResources().getDrawable(R.drawable.vikingoverde);
                stickv1=getResources().getDrawable(R.drawable.vikingaroja);
                stickv2=getResources().getDrawable(R.drawable.vikingaverde);
                stickh1=getResources().getDrawable(R.drawable.vikingarojah);
                stickh2=getResources().getDrawable(R.drawable.vikingaverdeh);
                stickv1r=getResources().getDrawable(R.drawable.vikingaresaltadar);
                stickv2r=getResources().getDrawable(R.drawable.vikingaresaltadav);
                stickh1r=getResources().getDrawable(R.drawable.vikingaresaltadarh);
                stickh2r=getResources().getDrawable(R.drawable.vikingaresaltadavh);
                music=R.raw.juegovikingo;
                background = getResources().getDrawable(R.drawable.vikingo);
                break;
            case IF_vp_game.THEME_EGIPT:
                /*egipt*/
                square1=getResources().getDrawable(R.drawable.egipciorojo);
                square2=getResources().getDrawable(R.drawable.egipcioverde);
                stickv1=getResources().getDrawable(R.drawable.egipciaroja);
                stickv2=getResources().getDrawable(R.drawable.egipciaverde);
                stickh1=getResources().getDrawable(R.drawable.egipciarojah);
                stickh2=getResources().getDrawable(R.drawable.egipciaverdeh);
                stickv1r=getResources().getDrawable(R.drawable.egipciaresaltadar);
                stickv2r=getResources().getDrawable(R.drawable.egipciaresaltadav);
                stickh1r=getResources().getDrawable(R.drawable.egipciaresaltadarh);
                stickh2r=getResources().getDrawable(R.drawable.egipciaresaltadavh);
                music=R.raw.juegoegipto;
                background = getResources().getDrawable(R.drawable.egipto);
                break;
            case IF_vp_game.THEME_HYRULE:
                /*hyrule*/
                square1=getResources().getDrawable(R.drawable.hyrulerojo2);
                square2=getResources().getDrawable(R.drawable.hyruleverde2);
                stickv1=getResources().getDrawable(R.drawable.maestraroja);
                stickv2=getResources().getDrawable(R.drawable.maestraverde);
                stickh1=getResources().getDrawable(R.drawable.maestrarojah);
                stickh2=getResources().getDrawable(R.drawable.maestraverdeh);
                stickv1r=getResources().getDrawable(R.drawable.maestraresaltadar);
                stickv2r=getResources().getDrawable(R.drawable.maestraresaltadav);
                stickh1r=getResources().getDrawable(R.drawable.maestraresaltadarh);
                stickh2r=getResources().getDrawable(R.drawable.maestraresaltadavh);
                music=R.raw.juegohirule;
                background = getResources().getDrawable(R.drawable.hyrule);
                break;
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.game_layout);
        ll.setBackgroundDrawable(background);

        /*start music*/
        player = MediaPlayer.create(this,music);
        player.setLooping(true);

        buttonSound = MediaPlayer.create(this,R.raw.corte);
        shieldSound = MediaPlayer.create(this,R.raw.shield);
    }

    @Override
    public void generateTable(int x, int y) {
        maxX = x;
        maxY = y;

        /*add the GridLayout*/
        GridLayout gl = new GridLayout(this);
        gl.setId(R.id.game_gridLayout);
        GridLayout.LayoutParams gl_params = new GridLayout.LayoutParams();
        gl_params.width=GridLayout.LayoutParams.WRAP_CONTENT;
        gl_params.height=GridLayout.LayoutParams.WRAP_CONTENT;
        gl_params.setGravity(Gravity.CENTER);
        gl.setLayoutParams(gl_params);
        gl.setColumnCount(maxY * 2 + 1);
        int hx,hy,vx,vy,sx,sy;
        hx=0;
        hy=0;
        vx=0;
        vy=0;
        sx=0;
        sy=0;
        int i,j;
        int id;
        ViewGroup.LayoutParams params;
        View view;
        Button button;
        /*add the button and surrounding view*/
        for(i=0;i<(maxX*2+1);i++){
            if((i%2)==0){
                /*row with horizontal buttons and small view*/
                for(j=0;j<(maxY*2+1);j++){
                    if((j%2)==0){
                        /*small view*/
                        params = new ViewGroup.LayoutParams(
                                (int) getResources().getDimension(R.dimen.small_dimen),
                                (int) getResources().getDimension(R.dimen.small_dimen));
                        view = new View(this);
                        view.setLayoutParams(params);
                        gl.addView(view);
                    }else{
                        /*horizontal buttons*/
                        params = new ViewGroup.LayoutParams(
                                (int) getResources().getDimension(R.dimen.big_dimen),
                                (int) getResources().getDimension(R.dimen.small_dimen));
                        button = new Button(this);
                        button.setLayoutParams(params);
                        id=getResources().getIdentifier("game_stickH_"+hx+"_"+hy,"id",getBaseContext().getPackageName());
                        button.setId(id);
                        button.setOnClickListener(this);
                        hy++;
                        gl.addView(button);
                    }
                }
                hx++;
                hy=0;
            }else{
                /*row with vertical buttons and big view*/
                for(j=0;j<(maxY*2+1);j++){
                    if((j%2)!=0){
                        /*big view*/
                        params = new ViewGroup.LayoutParams(
                                (int) getResources().getDimension(R.dimen.big_dimen),
                                (int) getResources().getDimension(R.dimen.big_dimen));
                        view = new View(this);
                        view.setLayoutParams(params);
                        id=getResources().getIdentifier("game_square_"+sx+"_"+sy,"id",getBaseContext().getPackageName());
                        view.setId(id);
                        sy++;
                        gl.addView(view);
                    }else{
                        /*vertical button*/
                        /*horizontal buttons*/
                        params = new ViewGroup.LayoutParams(
                                (int) getResources().getDimension(R.dimen.small_dimen),
                                (int) getResources().getDimension(R.dimen.big_dimen));
                        button = new Button(this);
                        button.setLayoutParams(params);
                        id=getResources().getIdentifier("game_stickV_"+vx+"_"+vy,"id",getBaseContext().getPackageName());
                        button.setId(id);
                        button.setOnClickListener(this);
                        vy++;
                        gl.addView(button);
                    }
                }
                vx++;
                vy=0;
                sx++;
                sy=0;
            }
        }
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.game_rLayout);
        rl.addView(gl);
    }

    @Override
    public void activeStick(int x, int y, int user,boolean vertical) {
        int id = getStickId(x,y,vertical);
        final View view = findViewById(id);
        if(user==1){
            /*user1*/
            if(vertical){
                /*palo vertical*/
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundDrawable(stickv1r);
                    }
                });
            }else{
                /*palo horizontal*/
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundDrawable(stickh1r);
                    }
                });
            }
        }else{
            /*user2*/
            if(vertical){
                /*palo vertical*/
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundDrawable(stickv2r);
                    }
                });
            }else{
                /*palo horizontal*/
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundDrawable(stickh2r);
                    }
                });
            }
        }
        buttonSound.start();
    }

    @Override
    public void changeStick(int x, int y, int user, boolean vertical){
        int id = getStickId(x, y, vertical);
        final View view = findViewById(id);
        if(user==1){
            /*user1*/
            if(vertical){
                /*palo vertical*/
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundDrawable(stickv1);
                    }
                });

            }else{
                /*palo horizontal*/
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundDrawable(stickh1);
                    }
                });
            }
        }else{
            /*user2*/
            if(vertical){
                /*palo vertical*/
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundDrawable(stickv2);
                    }
                });
            }else{
                /*palo horizontal*/
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundDrawable(stickh2);
                    }
                });
            }
        }
    }

    @Override
    public void activeSquare(int x, int y, int user) {
        int id = getSquareId(x, y);
        final View view = findViewById(id);
        if(user==1){
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setBackgroundDrawable(square1);
                }
            });
        }else {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setBackgroundDrawable(square2);
                }
            });
        }
        shieldSound.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Log.i("demo","id: "+id);
        String name = getResources().getResourceName(id);
        name = name.substring((name.length()-5));
        boolean vertical;
        int x,y;
        char c = name.charAt(0);
        vertical = (c=='V');
        c=name.charAt(2);
        x = Character.getNumericValue(c);
        c=name.charAt(4);
        y = Character.getNumericValue(c);
        presenter.stickPressed(x, y, vertical);
    }

    @Override
    public void changeTurn(int player){
        final TextView tv1 = (TextView) findViewById(R.id.txt1);
        final TextView tv2 = (TextView) findViewById(R.id.txt2);

        if(player == 1){
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    tv1.setTextSize(24);
                    tv2.setTextSize(14);
                }
            });
        }
        else{
            tv1.post(new Runnable() {
                @Override
                public void run() {
                    tv1.setTextSize(14);
                    tv2.setTextSize(24);
                }
            });
        }
    }

    @Override
    public void changeScore(final int score1,final int score2){
        final TextView tv1 = (TextView) findViewById(R.id.txt1);
        final TextView tv2 = (TextView) findViewById(R.id.txt2);

        tv1.post(new Runnable() {
            @Override
            public void run() {
                tv1.setText("Player1: " + score1);
                tv2.setText("Player2: "+score2);
            }
        });
    }

    @Override
    public void finish(int score1, int score2){
        finish();
    }

    @Override
    public void disable(){
        final GridLayout gl = (GridLayout) findViewById(R.id.game_gridLayout);
        gl.post(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<gl.getChildCount();i++){
                    View child = gl.getChildAt(i);
                    child.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void enable(){
        final GridLayout gl = (GridLayout) findViewById(R.id.game_gridLayout);
        gl.post(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<gl.getChildCount();i++){
                    View child = gl.getChildAt(i);
                    child.setEnabled(true);
                }
            }
        });
    }

    private int getStickId(int x, int y, boolean vertical){
        int id;
        if(vertical){
            /*botones verticales*/
            id=getResources().getIdentifier("game_stickV_"+x+"_"+y,"id",getBaseContext().getPackageName());
        }else{
            /*botones horizontales*/
            id=getResources().getIdentifier("game_stickH_"+x+"_"+y,"id",getBaseContext().getPackageName());
        }
        return id;
    }

    private int getSquareId(int x,int y){
        return getResources().getIdentifier("game_square_"+x+"_"+y,"id",getBaseContext().getPackageName());
    }
}
