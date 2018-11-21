package com.example.qawbecrdteyf.mpmcp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText inp = findViewById(R.id.inpfield);
        Button validateButton = findViewById(R.id.valB);
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(),"Device doesn't Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled()){
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
        }
        BluetoothDevice device;
        //BluetoothC bc = new BluetoothC();
        //device = bc.getdevice();
        //if(device == null){
        //    Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        //}
        final boolean[] act = {false};
        final String[] res = {"valid"};
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(inp.getText().toString().isEmpty())){
                    act[0] = checkDataEntered(inp.getText().toString());
                    Log.d("input", inp.getText().toString());
                    MyPrecious mp = new MyPrecious();
                    Log.d("password", mp.getPassword());
                    res[0] = (act[0]) ? "Valid" : "Invalid";
                    Toast.makeText(getApplicationContext(),res[0],Toast.LENGTH_SHORT).show();
                    if(act[0]==true){
                        Intent intent;
                        intent = new Intent(getApplicationContext(), SelectorActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });


    }

    private boolean checkDataEntered(String s) {
        MyPrecious mp = new MyPrecious();
        if(s.equals(mp.getPassword())){
            return true;
        }return false;
    }
}
