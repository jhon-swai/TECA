package com.example.teca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mToMapButton;
    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToMapButton = (Button) findViewById(R.id.toMapBtn);
        mLoginButton = (Button) findViewById(R.id.btnMainLogin);
        mRegisterButton = (Button) findViewById(R.id.btnMainRegister);
        setButtonOnClickListeners();

    }

    public void setButtonOnClickListeners(){
        // map click listener
        mToMapButton.setOnClickListener(View -> {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        });

        // login click listener
        mLoginButton.setOnClickListener(View -> {
           startActivity(new Intent(this, LoginActivity.class));
        });

        // register click listener
        mRegisterButton.setOnClickListener(View -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });

    }
}