package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaRRy on 7/18/2018.
 */
@Dao
public interface HabitsDao {
    @Insert
    public void insertHabit(Habit habit);
    @Insert
    public void insertUser(User user);
    @Query("SELECT * FROM USER")
    public List<User> getUser();
    @Query("SELECT * FROM HABIT WHERE ID=:id")
    public Habit getOneHabit(int id);
    @Query("SELECT COUNT(id) FROM HABIT")
    public int getHabitCount();
    @Query("SELECT * FROM HABIT")
    public List<Habit> getAllHabits();

}
