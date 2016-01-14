package eus.tta.shielded;

import android.util.Log;

import java.util.Random;

/**
 * Created by ainhoa on 13/01/2016.
 */
public abstract class ClassIA implements IA {

    int buttons;
    Stick [][] vertical;
    Stick [][] horizontal;
    Random r;
    Data [] array;

    public ClassIA(Stick [][] vertical, Stick [][] horizontal,final int maxX,final int maxY){
        buttons= maxY*(maxX+1)+maxX*(maxY+1);
        this.vertical = vertical;
        this.horizontal = horizontal;
        r = new Random();
		/*Llenar array de datos*/
        array = new Data [buttons];
        int i;
        int hx,hy,vx,vy;
        hx=0;
        hy=0;
        vx=0;
        vy=0;
        for(i=0;i<buttons;i++){
            if(hy==maxY&&hx==maxX){
                /*ya se han llenado los horizontales, verticales*/
                array[i] = new Data();
                array[i].vertical = true;
                array[i].y = vy++;
                array[i].x = vx;
                if(vy==maxY+1){
                    vx++;
                    vy=0;
                }
            }else{
                /*horizontales*/
                array[i] = new Data();
                array[i].vertical = false;
                array[i].y = hy++;
                array[i].x = hx;
                if(hy==(maxY)&&hx!=maxX){
                    hx++;
                    hy=0;
                }
            }
        }
    }
}
