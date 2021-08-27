package com.example.teca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private UserEntity mUserLogin;
    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assign all the views
        mEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        mPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnRegister = (Button) findViewById(R.id.btnRegisterInstead);
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);



        setButtonOnClickListeners();

    }

    public void setButtonOnClickListeners(){

        mBtnLogin.setOnClickListener(View -> {
            if (validateInput()){
                startActivity(new Intent(this, MainActivity.class));
            }

        });

        mBtnRegister.setOnClickListener(View -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });

    }
    public boolean validateInput(){
        int duration = Toast.LENGTH_SHORT;
        if (TextUtils.isEmpty(mEmail.getText()) || TextUtils.isEmpty(mPassword.getText())){
            Toast.makeText(this, "Please complete the form", duration).show();
            return false;
        } else {
            mUserLogin = mUserViewModel.userLogin( mEmail.getText().toString(), mPassword.getText().toString() );
            if (mUserLogin == null ){
                Toast.makeText(this, "Credentials do not match", Toast.LENGTH_SHORT).show();
                return false;
            } else{
                return true;
            }
        }
    }
}