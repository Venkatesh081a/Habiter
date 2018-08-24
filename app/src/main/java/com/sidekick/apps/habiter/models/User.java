package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by HaRRy on 7/18/2018.
 */
@Entity
public class User {
    @PrimaryKey @NonNull
    private String userName;
    private int lvl;
    private int points;
    private int streak;
    public User(String userName){
        this.lvl = 1;
        this.points = 0;
        this.userName = userName;
        this.streak = 0;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    //implements the Habit model
     //  @ForeignKey(Habit)
    //private Habit[] habits;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
