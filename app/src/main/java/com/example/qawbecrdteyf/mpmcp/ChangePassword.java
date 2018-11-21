package com.example.qawbecrdteyf.mpmcp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        final EditText newPasswordField = findViewById(R.id.newPField);
        Button confirmPasswordChangeButton = findViewById(R.id.CButton);
        confirmPasswordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newText = newPasswordField.getText().toString();
                if(newText.length()!=4){
                    Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("newpassword", newText);
                    MyPrecious mp = new MyPrecious();
                    mp.setPassword(newText);
                    Intent intent;
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
