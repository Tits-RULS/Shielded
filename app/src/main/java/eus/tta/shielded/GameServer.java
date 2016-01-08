package eus.tta.shielded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class GameServer.
 */
public class GameServer extends Game {

	protected final int REQUEST_ENABLE_BT = 1;
	protected final int REQUEST_ENABLE_DS = 2;

	protected final int time = 300;
	protected final String NAME="Shielded2";
	protected final String NUM ="38400000-8cf0-11bd-b23e-10b96e4ef00d";
	
	protected BluetoothSocket socket;
	protected BluetoothAdapter mBluetoothAdapter;
	protected BluetoothServerSocket mmServerSocket = null;
	protected DataInputStream dis;
	protected DataOutputStream dos;
	
	protected Player you;
	protected boolean first=true;
	protected Bluetooth input;
	
	/**************************M�TODOS**************************/
	@Override
	public void onStart(){
		/*porque?*/
		if(first){
			super.onStart();
		}
		else{
			jugador1 = null;
			super.onStart();
			jugador1 = you;
		}
	}
	
	@Override
	public void onResume(){
		System.out.println("GameServer: onREsume");
		/*solo si es la primera vez que se realiza*/
		if(first){
			first=false;
			setContentView(R.layout.loading_layout);
			super.onResume();
		//	TODO saldr� el juego antes de que se establezca la conexi�n
		
			/*realizar la conexi�n bluetooth*/
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
				AlertDialog gameOverDialog = builder.create();
				gameOverDialog.show();
			}
		
			/*Comprobar que el bluetooth est� activado*/
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
			else{
				discoverable();
			}
		}
		else{
			super.onResume();
		}
	}
	
	/**
	 * Discoverable.
	 */
	private void discoverable(){
		System.out.println("Server: Antes de hacerlo descubrible");
		/*Hacer que el dispositivo descubrible*/
		Intent discoverableIntent = new
				Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, time);
		/*discoverable for 300 sec*/
		startActivityForResult(discoverableIntent,REQUEST_ENABLE_DS);
	}
	
	/**
	 * Conexion.
	 */
	private void conexion(){
		// Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        try {
            // MY_UUID is the app's UUID string, also used by the client code
        	UUID id=UUID.fromString(NUM);
            mmServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, id);
        } catch (IOException e) { 
        }
        
        socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (socket==null) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // conexi�n aceptada
            if (socket != null) {
                try {
                	dos = new DataOutputStream(socket.getOutputStream());
                	dis = new DataInputStream(socket.getInputStream());
                	dos.writeInt(map);
					mmServerSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
                System.out.println("antes de cargar el juego");
                super.onCreate(saveInstance);
                super.onStart();
                super.onResume();
                jugador1 = new Player(true,1);
                jugador2 = new Player(false,0);
                you = jugador1;
                
                System.out.println("Server: antes de input");
                
                /***lanzar el input*/
	    		input = new Bluetooth();
	    		input.execute(1,this,dis,xTam,yTam);
	    		System.out.println("Server: despues del input");
                break;
            }
        }
	}
	
	/**
	 * On activity result.
	 *
	 * @param requestCode the request code
	 * @param resultCode the result code
	 * @param data the data
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
		case REQUEST_ENABLE_BT:
			switch(resultCode){
			case RESULT_OK:
				discoverable();
				break;
			case RESULT_CANCELED:
				onBackPressed();
				break;
			}
			break;
		case REQUEST_ENABLE_DS:
			switch(resultCode){
				case time:
					conexion();
					break;
				case RESULT_CANCELED:
					/*vulea al menu*/
					onBackPressed();
					break;
			}
			break;
		}
		
	}
	
	
	/**
	 * ********GESTI�N DEL JUEGO*************.
	 *
	 * @param view the view
	 */
	@Override
	public void event(View view){
		System.out.println("antes de tur");
		if(you.isTurn()){
			System.out.println("despues del turn");
			int id=view.getId();    	
			super.gestion(id);
			try {
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
	
/*	@Override
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
 * On destroy.
 */
@Override
	protected void onDestroy(){
		super.onDestroy();
		try {
			mmServerSocket.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.cancel(false);
	}
}
