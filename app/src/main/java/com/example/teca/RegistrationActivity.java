package com.example.teca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button mLoginInsteadButton;
    private Button mRegisterButton;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Spinner mSpinner;
    private String mSpinnerText;
    private String mPositionArray [] = {"Student", "Developer"};;

    // Database viewmodel
    private UserViewModel mUserViewModel;
    public static final int REGISTER_USER_ACTIVITY_REQUEST_CODE = 1;
    private UserEntity mUserData;

    // firebase
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeAllVariables();
        setButtonOnClickListeners();
    }

    public void setButtonOnClickListeners(){
        // login button listeners
        mLoginInsteadButton.setOnClickListener(View -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        // registration button listener
        mRegisterButton.setOnClickListener(View ->{
            if (validateUserData() ){
                setUserData();
                firebaseRegister();
                mUserViewModel.registerUser(mUserData);
                startActivity(new Intent(this, CategoriesUserActivity.class));
            }

        });
    }

    private boolean validateUserData(){
        int duration = Toast.LENGTH_SHORT;

        // validate the input data
        if ( TextUtils.isEmpty(mFirstName.getText()) || TextUtils.isEmpty(mLastName.getText()) || TextUtils.isEmpty(mEmail.getText() )|| TextUtils.isEmpty(mPassword.getText()) || TextUtils.isEmpty(mConfirmPassword.getText())) {
            Toast.makeText( this, "Please complete the form", duration).show();
            return false;
        } else {

            // check if the passwords and confirm password match
            if ( mPassword.getText().toString().equals(mConfirmPassword.getText().toString() )){
                return true;
            } else {
                Toast.makeText( this, "Passwords don't match", duration).show();
                return false;
            }
        }
    }

    private void setUserData(){
        mUserData.setEmail(mEmail.getText().toString().toLowerCase());
        mUserData.setFirst_name(mFirstName.getText().toString().toLowerCase());
        mUserData.setLast_name(mLastName.getText().toString().toLowerCase());
        mUserData.setPassword(mPassword.getText().toString());
        mUserData.setPosition(mSpinnerText);
    }

    public void initializeAllVariables(){
        mLoginInsteadButton = (Button) findViewById(R.id.btnLoginInstead);
        mRegisterButton = (Button) findViewById(R.id.btnRegister);
        mFirstName = (EditText) findViewById(R.id.editTextRegisterFirstName);
        mLastName = (EditText) findViewById(R.id.editTextRegisterLastName);
        mEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        mPassword = (EditText) findViewById(R.id.editTextTextRegisterPassword);
        mConfirmPassword = (EditText) findViewById(R.id.editTextRegisterConfirmPassword);
        mSpinner = (Spinner) findViewById(R.id.spinnerRegisterDevStudent);

        auth = FirebaseAuth.getInstance();
        // getting the viewModel from the viewModelProvider
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // mUserViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(UserViewModel.class);

        mUserData = new UserEntity();

        mSpinner.setOnItemSelectedListener(this);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dev_or_student, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mPositionArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        String selectedItem = mPositionArray[pos];
        mSpinnerText = selectedItem;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

        Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();

    }

    private void firebaseRegister(){
        String username = mFirstName.getText().toString() + " " +mLastName.getText().toString();
        String password = mPassword.getText().toString();
        String email = mEmail.getText().toString();
        fRegister(username, email, password);
    }

    // TODO: Move the registration method to data repository
    // registration method
    private void fRegister(String username, String email, String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            // assigning the values
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
//                                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegistrationActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}