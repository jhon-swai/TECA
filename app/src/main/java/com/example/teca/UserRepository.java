package com.example.teca;

import android.app.Application;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private List<UserEntity> mUserData;
    private UserEntity mUserLogin;

    UserRepository(Application application){
        UserDatabase db = UserDatabase.getDatabase(application);
        mUserDao = db.userDao();
        setUserData();



    }

    // Method to get user data
    List<UserEntity> getUserData(){
        return mUserData;
    }

    void setUserData(){
        UserDatabase.databaseWriteExecutor.execute(()->{
            mUserData = mUserDao.getUser();
        });
    }


    // method to register a user
    void registerUser(UserEntity userData){
        UserDatabase.databaseWriteExecutor.execute(() ->{
            mUserDao.registerUser(userData);
        });
    }

    // user login
    UserEntity userLogin(String email, String password){
        UserDatabase.databaseWriteExecutor.execute(() ->{
            mUserLogin = mUserDao.userLogin(email, password);
        });
        return mUserLogin;
    }


}
