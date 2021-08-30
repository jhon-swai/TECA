package com.example.teca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private UserEntity mUserLogin;
    private UserViewModel mUserViewModel;

    FirebaseAuth auth;

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
                Log.d("Sky Walker", "On click successful");
                firebaseLogin();
            }
        });

        mBtnRegister.setOnClickListener(View -> startActivity(new Intent(this, RegistrationActivity.class)));

    }
    public boolean validateInput(){

        mUserLogin = mUserViewModel.userLogin( mEmail.getText().toString(), mPassword.getText().toString() );

        if (TextUtils.isEmpty(mEmail.getText()) || TextUtils.isEmpty(mPassword.getText())){
            Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            if (mUserLogin == null){
                mUserLogin = mUserViewModel.userLogin( mEmail.getText().toString(), mPassword.getText().toString() );
                if (mUserLogin == null){
                    Toast.makeText(this, "Credentials do not match", Toast.LENGTH_SHORT).show();
                    Log.d("YODA", "login failed");
                    return false;
                } else {
                    Log.d("YODA", "Suprisingly it worked on the second one");
                    return true;
                }
            } else{
                Log.d("YODA", "login validation successful");
                return true;
            }
        }
    }

    public void firebaseLogin(){
        auth = FirebaseAuth.getInstance();
        String email_txt = mEmail.getText().toString();
        String password_txt = mPassword.getText().toString();
        auth.signInWithEmailAndPassword(email_txt, password_txt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, ChatRoomActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


}