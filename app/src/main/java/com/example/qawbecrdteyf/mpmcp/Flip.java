package com.example.qawbecrdteyf.mpmcp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import java.io.IOException;
import android.os.AsyncTask;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.UUID;
import java.util.zip.CheckedOutputStream;

public class Flip extends Activity {

    BluetoothDevice bd = null;
    String address = "98:D3:31:70:85:16";
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
          Iterator<BluetoothDevice> itr = myBluetooth.getBondedDevices().iterator();
          while(itr.hasNext()){
              BluetoothDevice b1 = itr.next();
              String s1 = b1.getName().toString();
              String s2 = b1.getAddress().toString();
              ParcelUuid[] s4 = b1.getUuids();
              for(int i=0;i<s4.length;i++){
                  String sU = s4[i].getUuid().toString();
                  Log.d("UUUIDS",sU);
              }
              if(s1!=null && s2!=null){
                  String s3  = "name: "+s1+" ADR: "+s2;
                  Toast.makeText(getApplicationContext(),s3,Toast.LENGTH_SHORT).show();
              }else{
                  Toast.makeText(getApplicationContext(),"CRYBABY",Toast.LENGTH_SHORT).show();
              }
              bd = b1;
          }
        try {
            btSocket = bd.createInsecureRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) {
              Log.d("error in insecure","kyakaru1");
            e.printStackTrace();
        }
        try {
            btSocket.connect();
        } catch (IOException e) {
            Log.d("error in connect","kyakaru2");
            e.printStackTrace();
        }
        //new ConnectBT().execute();
        Button flipButton = findViewById(R.id.button);
        flipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String data = "1";
                Log.d("trigger",data);
                OutputStream outputStream = null;
                try {
                    outputStream = btSocket.getOutputStream();
                    Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Errorup",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if(btSocket.isConnected()){
                    Toast.makeText(getApplicationContext(),"sockc",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        btSocket.connect();
                        Toast.makeText(getApplicationContext(),"sockc",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"ERUP",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                try
                {
                    outputStream.write("1".toString().getBytes());
                    btSocket.close();
                    Toast.makeText(getApplicationContext(),"Transmitted",Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    Log.d("errorinconn","checkplz");
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(getApplicationContext(), "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                Toast.makeText(getApplicationContext(),"Connection Failed. Is it a SPP Bluetooth? Try again.",Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

}
