package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaRRy on 7/18/2018.
 */
@Dao
public interface HabitsDao {
    @Insert
    void insertHabit(Habit habit);
    @Delete
    void deleteHabit(Habit habit);

    @Update
    void updateHabit(Habit habit);

    @Update
    void updateAllHabits(List<Habit> habits);

    @Query("SELECT * FROM HABIT WHERE ID=:id")
    Habit getOneHabit(int id);

    @Query("SELECT COUNT(id) FROM HABIT")
    int getHabitCount();

    @Query("SELECT * FROM HABIT")
    List<Habit> getAllHabits();

}
