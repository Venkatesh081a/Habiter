package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by HaRRy on 10/6/2018.
 */
@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);
    @Query("SELECT * FROM USER")
    List<User> getUser();
}
