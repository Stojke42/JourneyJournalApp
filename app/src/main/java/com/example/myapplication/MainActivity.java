package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG=MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        EditText username=findViewById(R.id.editTextUserName);
        EditText userpassword=findViewById(R.id.editTextPassword);

        String usernamestr=username.getText().toString();
        String userpasswordstr=userpassword.getText().toString();

        Log.i(LOG_TAG,"Bejeletnkezet "+usernamestr+userpasswordstr);

    }

    public void register(View view) {
    }
}