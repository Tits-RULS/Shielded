package eus.tta.shielded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

// TODO: Auto-generated Javadoc
/**
 * The Class GameCliente.
 */
public class GameCliente extends Game{
	
	
	/** The mm socket. */
	protected BluetoothSocket mmSocket;
	
	/** The m array adapter. */
	protected ArrayAdapter<String> mArrayAdapter;
	
	/** The devices. */
	protected ArrayList <BluetoothDevice> devices;
	
	/** The m bluetooth adapter. */
	protected BluetoothAdapter mBluetoothAdapter;
	
	/** The act. */
	protected GameCliente act;
	
	/** The dis. */
	protected DataInputStream dis;
	
	/** The dos. */
	protected DataOutputStream dos;
	
	/** The input. */
	protected Bluetooth input;
	
	/** The you. */
	protected Player you;
	
	/** The first. */
	protected boolean first=true;
	
	/** The request enable bt. */
	protected final int REQUEST_ENABLE_BT = 1;
	
	/** The num. */
	protected final String NUM ="38400000-8cf0-11bd-b23e-10b96e4ef00d";
	
	
	// Create a BroadcastReceiver for ACTION_FOUND
		/** The m receiver. */
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        // When discovery finds a device
		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		            // Get the BluetoothDevice object from the Intent
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		            // Add the name and address to an array adapter to show in a ListView
		            /*comprobar que ese elemento no est� ya cacheado*/
		            if(devices.size()<=0){
		            	System.out.println(device.getName() + " " + device.getAddress());
	            		mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			            devices.add(device);
		            }
		            else{
		            	for(int i=0;i<devices.size();i++){
		            		if(device!=devices.get(i)){
		            			System.out.println(device.getName() + " " + device.getAddress());
		            			mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		            			devices.add(device);
		            			i=devices.size();
		            		}
		            	}
		            }
		        }
		    }
		};
	
	
	/**
	 * On resume.
	 */
	@Override
	protected void onResume(){
		if(first){
			first=false;
			super.onResume();
			act=this;
			
			mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
			devices = new ArrayList<BluetoothDevice>();
		
			/*cargar pantalla de selecci�n de dispositivo*/
			setContentView(R.layout.device_selection);
		
			/*comprobar que hay bluetooth en el disp*/
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("El dispositivo no soporta bluetooth").setCancelable(false)
					.setPositiveButton("Salir",
							new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface arg0, int arg1){
								onBackPressed();
							}
					});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		
			/*Comprobar que el bluetooth est� activado*/
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
			else{
				conexion();
			}		
		}
		else{
			super.onResume();
		}
	}
	
	/**
	 * Conexion.
	 */
	private void conexion(){
		System.out.println("conexi�n");
		/*el bluetooth est� activado*/
		
		/*obtener la lista de los dispositivos ya conocidos*/
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		        devices.add(device);
		    }
		}
		
		System.out.println("antes del discovery");
		boolean discovering=false;
		/*comenzar el discoveri*/
		/*aqu� solo se lanza, es un proceso asincrono del que se deben capturar los broadcast*/
		do{
			if(!mBluetoothAdapter.startDiscovery()){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("No se pudo iniciar la b�squeda de partidas").setCancelable(false)
					.setPositiveButton("Reintentar",
						new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface arg0, int arg1){
							/*no se hace nada devido al do-while*/
						}
					})
					.setNegativeButton("Salir",
						new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface arg0, int arg1){
							onBackPressed();
							/*se vuelve al menu*/
						}
					});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
			else{
				discovering=true;
			}
		}while(!discovering);
		/*el discovering a comenzado*/
		
		/*RECUERDA*/
		/*En el momento que se quiere parar de buscar
		 * mBluetoothAdapter.ucancelDiscovery()
		 */
		
		
		System.out.println("Cliente: Antes del comenzar el receiver");
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
		
		/*poner todos los device en un listview*/
		ListView lst = (ListView) findViewById(R.id.listView1);
		lst.setAdapter(mArrayAdapter);
		
		/*Se establece un listener para el click en el listView*/
		
		lst.setOnItemClickListener(new OnItemClickListener() {
	           
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long id) {
				mmSocket = null;
		        // Get a BluetoothSocket to connect with the given BluetoothDevice
		        try {
		            // MY_UUID is the app's UUID string, also used by the server code
		        	UUID my_id=UUID.fromString(NUM);
		            mmSocket = devices.get(position).createRfcommSocketToServiceRecord(my_id);
		        } catch (IOException e) { }
		        
		        // Cancel discovery because it will slow down the connection
		        mBluetoothAdapter.cancelDiscovery();
		 
		        try {
		            // Connect the device through the socket. This will block
		            // until it succeeds or throws an exception
		            mmSocket.connect();
		        } catch (IOException connectException) {
		            // Unable to connect; close the socket and get out
		        	/*SACAR UN MENSAJE DE ERROR DE CONEXI�N*/
		        	connectException.printStackTrace();
		        	AlertDialog.Builder builder = new AlertDialog.Builder(act);
					builder.setMessage("No se pudo iniciar la conexi�n").setCancelable(false)
						.setPositiveButton("Salir",
							new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface arg0, int arg1){
								onBackPressed();
							}
						});
					AlertDialog dialog = builder.create();
					dialog.show();
					/*cerrar el socket*/
		            try {
		                mmSocket.close();
		            } catch (IOException closeException) { }
		            return;
		        }
		 
		        int map=-1;
		        
		        // conexi�n establecida, 
				try {
					dis = new DataInputStream(mmSocket.getInputStream());
					dos  =new DataOutputStream(mmSocket.getOutputStream());
					map = dis.readInt();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Map: "+map);
				/*tras conocer el mapa, comienza la gesti�n y carga*/
				switch(map){
				case 0:
					//3x3
					xTam=3;
					yTam=3;
					buttons=24;
					squares=9;
					mapLayout=R.layout.activity_game;
					mapid=R.id.rl3_3;
					break;
				case 1:
					//4x4
					xTam=4;
					yTam=4;
					buttons=40;
					squares=16;
					mapLayout=R.layout.activity_game4_4;
					mapid=R.id.rl4_4;
					break;
				case 2:
					//4x5
					xTam=5;
					yTam=4;
					buttons=50;
					squares=20;
					mapLayout=R.layout.activity_game4_5;
					mapid=R.id.rl4_5;
					exceptions=new int [1];
					exceptions[0]=48;
					break;
				case -1:
					/*error en la conexi�n*/
					AlertDialog.Builder builder = new AlertDialog.Builder(act);
					builder.setMessage("Error de conexi�n").setCancelable(false)
						.setPositiveButton("Salir",
							new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface arg0, int arg1){
								onBackPressed();
							}
						});
					AlertDialog dialog = builder.create();
					dialog.show();
					try {
		                mmSocket.close();
		            } catch (IOException closeException) { }
					break;
				}
				
				System.out.println("Cliente: antes de cargar las calses");
				/*variables cargadas gesti�n de inicio*/
				vertical=new Stick[xTam][yTam+1];
				horizontal=new Stick[xTam+1][yTam];
				cuadrado= new Square[xTam][yTam];
				jugador1=new Player(true,1);
				jugador2=new Player(false,0);
				array=new Data[buttons];
				squaresActivated=0;
				you=jugador2;
			
			
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
						cuadrado[sqx][sqy] = new Square(horizontal[sqx][sqy],horizontal[sqx+1][sqy],vertical[sqx][sqy],vertical[sqx][sqy+1]);
					}
				}	
			
				if(exceptions!=null){
					/*crear lo tabla de conversi�n*/
					int exc=0;
					for(int i=0;i<buttons;i++){
						if(exceptions[exc]!=i){
							array[i]= new Data();
							if(i%2==0){
								/*horizontales*/
								array[i].vertical=false;
								array[i].x=i/(yTam*2);
								array[i].y=(i%(yTam*2))/2;
							}
							else{
								/*verticales*/
								array[i].vertical=true;
								array[i].x=i/((yTam+1)*2);
								array[i].y=(i%((yTam+1)*2))/2;
							}
						}
						else{
							if(exceptions.length<exc){
								exc++;	
							}
						}
					}
				}
				else{
					for(int i=0;i<buttons;i++){
						array[i]= new Data();
						if(i%2==0){
							/*horizontales*/
							array[i].vertical=false;
							array[i].x=i/(yTam*2);
							array[i].y=(i%(yTam*2))/2;
						}
						else{
							/*verticales*/
							array[i].vertical=true;
							array[i].x=i/((yTam+1)*2);
							array[i].y=(i%((yTam+1)*2))/2;
						}
					}
				
				}
			
				/*cargar sonido para botones*/
				buttonSound = MediaPlayer.create(act, R.raw.corte);
				shieldSound = MediaPlayer.create(act, R.raw.shield);
			
				setContentView(mapLayout);
			
				/*fondo*/
				rl = (RelativeLayout) findViewById(mapid);
				rl.setBackgroundDrawable(bck);
			
				for(int i=0; i<buttons;i ++){
					View bt= findViewById(getVariable("R.id.button"+i));
					bt.setId(i);
				}
				
				/*marcar el texto*/
				int pj1= jugador1.getScore();
	    		int pj2= jugador2.getScore();
	    	
	    		TextView tv1 = (TextView) findViewById(R.id.txt1);
	    		TextView tv2 = (TextView) findViewById(R.id.txt2);
	    		
	    		tv1.setText("Player1: "+pj1);
	    		tv1.setTextColor(Color.RED);
	    		tv2.setText("Player2: "+pj2);
	    		tv2.setTextColor(Color.GREEN);
	    		if(jugador1.isTurn()){
					tv1.setTextSize(24);
					tv2.setTextSize(14);
	    		}
	    		else{
	    			tv1.setTextSize(14);
					tv2.setTextSize(24);
	    		}
	    		
	    		System.out.println("Cliente: antes del input");
	    		
	    		/***lanzar el input*/
	    		input = new Bluetooth();
	    		input.execute(2,act,dis,xTam,yTam);
	    		
	    		System.out.println("Cliente: despues del input");
			}
        });
		
	}
	
	/**
	 * On activity result.
	 *
	 * @param requestCode the request code
	 * @param resultCode the result code
	 * @param data the data
	 */
	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		switch(requestCode){
		case REQUEST_ENABLE_BT:
			switch(resultCode){
			case RESULT_OK:
				conexion();
				break;
			case RESULT_CANCELED:
				onBackPressed();
				break;
			}
			break;
		}
	}
	
	/**
	 * ************gesti�n del juego **************************.
	 *
	 * @param view the view
	 */
	@Override
	public void event(View view){
		System.out.println("Cliente: event");
		if(you.isTurn()){
			System.out.println("Cliente: tu turno");
			int id=view.getId();    	
			super.gestion(id);
			try {
				System.out.println("Cliente: antes de enviar");
				dos.writeInt(id);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
	/**
	 * Event remote.
	 *
	 * @return true, if successful
	 */
	public boolean eventRemote(){
		if(!you.isTurn()){    	
			return true;
		}
		else
			return false;
	}
	
	/*@Override
	protected void comprobation(){
		super.comprobation();
		
		//comprobar si le toca al otro
		if(!you.isTurn()){
			try {
				int id = dis.readInt();
				eventRemote(id);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	/**
	 * *****************************.
	 */
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		unregisterReceiver(mReceiver);
		try {
			mmSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.cancel(true);
		
	}
	
}
