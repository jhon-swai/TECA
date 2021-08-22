package com.example.teca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegistrationActivity extends AppCompatActivity {
    private Button mLoginInsteadButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mLoginInsteadButton = (Button) findViewById(R.id.btnLoginInstead);
        mRegisterButton = (Button) findViewById(R.id.btnRegister);
        setButtonOnClickListeners();

    }
    public void setButtonOnClickListeners(){
        // login button listeners
        mLoginInsteadButton.setOnClickListener(View -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        // registration button listener
        mRegisterButton.setOnClickListener(View ->{
            startActivity(new Intent(this, CategoriesUserActivity.class));
        });

    }
}