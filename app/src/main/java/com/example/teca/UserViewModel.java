package com.example.teca;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private List<UserEntity> mUserData;

    // initialize
    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mUserData = mRepository.getUserData();
    }

    List<UserEntity> getUserData(){
        return mUserData;
    }

    public void registerUser(UserEntity userData){
        mRepository.registerUser(userData);
    }

    public UserEntity userLogin(String email, String password){
        return mRepository.userLogin(email, password);
    }


}
