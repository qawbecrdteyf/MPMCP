package com.example.qawbecrdteyf.mpmcp;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import java.util.Iterator;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

class BluetoothC {

    public BluetoothDevice getdevice() {
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        BluetoothDevice device = null;
        boolean found = false;
        if(bondedDevices.isEmpty()) {
            Log.d("DEVF","no devices found");
            return null;
            //Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();

        } else {
            Log.d("DEVF","Devices found1");
            Iterator iter = bondedDevices.iterator();
            Log.d("DEVF",String.valueOf(iter.next())+"CHIRAG");
            Object first = iter.next();
            return (BluetoothDevice) first;
            /*for (BluetoothDevice iterator : bondedDevices) {
                Log.d("DEVF", String.valueOf(iterator));

                if(iterator.getAddress().equals(R.string.DEVICE_NAME)) //Replace with iterator.getName() if comparing Device names.

                {
                    device=iterator; //device is an object of type BluetoothDevice
                    found=true;
                    break;
                }
            }
            return  device;*/
        }
    }
}
