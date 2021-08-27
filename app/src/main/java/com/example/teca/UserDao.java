package com.example.teca;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users")
    List<UserEntity> getUser();

    @Query("SELECT * FROM users WHERE email=(:email) and password=(:password)")
    UserEntity userLogin(String email, String password);

}
