package com.example.teca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assign all the views
        mEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        mPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnRegister = (Button) findViewById(R.id.btnRegisterInstead);

        setButtonOnClickListeners();

    }

    public void setButtonOnClickListeners(){

        mBtnLogin.setOnClickListener(View -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        mBtnRegister.setOnClickListener(View -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });

    }
}