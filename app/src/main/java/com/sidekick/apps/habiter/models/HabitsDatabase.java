package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by HaRRy on 7/19/2018.
 */
@Database(entities = {Habit.class,User.class},version = 3,exportSchema = false)
public abstract class HabitsDatabase extends RoomDatabase {
    private static final String DB_NAME = "HABITER";
    private static HabitsDatabase habitsDatabase;
    public static HabitsDatabase getDatabase(Context context)
    {
        if(habitsDatabase == null)
        {
            habitsDatabase = Room.databaseBuilder(context.getApplicationContext(),HabitsDatabase.class,DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        }
        return habitsDatabase;
    }
    public  abstract HabitsDao habitsDao();


}
