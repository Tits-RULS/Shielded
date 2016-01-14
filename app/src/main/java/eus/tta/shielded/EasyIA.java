/*
 * 
 */
package eus.tta.shielded;



// TODO: Auto-generated Javadoc

import android.util.Log;

/**
 * The Class EasyIA.
 */
public class EasyIA extends ClassIA{


	public EasyIA(Stick [][] vertical, Stick [][] horizontal,int maxX,int maxY){
		super(vertical,horizontal,maxX,maxY);
	}
	/* (non-Javadoc)
	 * @see com.example.shielded2.IA#turn()
	 */
	public Data turn(){
		int act=-1;
		int cnt=0;
		/*no exceptions*/
		do{
			int i = r.nextInt(buttons);
			cnt++;
			if(array[i].vertical){
				if(!vertical[array[i].x][array[i].y].isActivated()){
					act=i;
				}
			}
			else{
				if(!horizontal[array[i].x][array[i].y].isActivated()){
					act=i;
				}
			}
			if(cnt==buttons/2){
				/*tras buscar la mitad de las posibilidades se busca secuencialmente*/
				for(i=0;act==1;i++){
					if(array[i].vertical){
						if(!vertical[array[i].x][array[i].y].isActivated()){
							act=i;
						}
					}
					else{
						if(!horizontal[array[i].x][array[i].y].isActivated()){
							act=i;
						}
					}
				}
			}
		}while(act==-1);

		return array[act];
	}
	
}
