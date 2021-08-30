package com.example.teca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mToMapButton, mLoginButton, mRegisterButton, mChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar(R.id.mainToolbar, "Main");
        initializeVariables();

    }

    public void initializeVariables(){
        mToMapButton = (Button) findViewById(R.id.toMapBtn);
        mLoginButton = (Button) findViewById(R.id.btnMainLogin);
        mRegisterButton = (Button) findViewById(R.id.btnMainRegister);
        mChatButton = (Button) findViewById(R.id.buttonMaintToChat);
        setButtonOnClickListeners();
    }

    // method to setup the actionbar
    public void setActionBar(int id, String title){
        Toolbar mToolbar = findViewById(id);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    public void setButtonOnClickListeners(){
        // map click listener
        mToMapButton.setOnClickListener(View -> {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        });

        mChatButton.setOnClickListener(View ->{
            startActivity(new Intent(this, ChatRoomActivity.class));
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