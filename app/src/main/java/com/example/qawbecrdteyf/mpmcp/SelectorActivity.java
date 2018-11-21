package com.example.qawbecrdteyf.mpmcp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.UUID;

public class SelectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        OutputStream outputStream = null;
        final InputStream[] inputStream = {null};
        final RadioGroup radioOptionGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioOptionGroup.clearCheck();
        Button subB = findViewById(R.id.proceedB);
        final OutputStream finalOutputStream1 = outputStream;
        subB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = -2;
                selected = radioOptionGroup.getCheckedRadioButtonId();
                Log.d("selected",String.valueOf(selected));
                if (selected>-1){
                    RadioButton radioOptionButton = findViewById(selected);
                    String texttosend = "00";
                    if(radioOptionButton.getText().toString().equals("Alter Load 1")) texttosend = "10";
                    if(radioOptionButton.getText().toString().equals("Alter Load 2")) texttosend = "11";
                    Log.d("selected option",texttosend);
                    /*BluetoothDevice device;
                    BluetoothC bc = new BluetoothC();
                    device = bc.getdevice();
                    BluetoothSocket socket = null;
                    try {
                        socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        socket.connect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try{
                        finalOutputStream1.write(texttosend.getBytes());
                    } catch(IOException e){
                        e.printStackTrace();
                    }*/
                    if(texttosend=="00"){
                        Intent intent;
                        intent = new Intent(getApplicationContext(), ChangePassword.class);
                        startActivity(intent);
                    }else if(texttosend=="10"){
                        Intent intent;
                        intent = new Intent(getApplicationContext(), Flip.class);
                        startActivity(intent);
                    }else if(texttosend=="11"){
                        Intent intent;
                        intent = new Intent(getApplicationContext(),Flip2.class);
                        startActivity(intent);
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Do something pliz",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

}
