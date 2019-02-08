package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.sidekick.apps.habiter.Util;

import java.util.Date;

/**
 * Created by HaRRy on 7/18/2018.
 */
@Entity
public class User {
    private int userId;
    @PrimaryKey @NonNull
    private String userName;
    private String favHabit;
    private int habitLimit;
    private int revives;
    private int revivesRemaining;
    private int lvl;
    private int points;
    private int streak;
    private int totalTimesDone;
    private int habitCount;
    @Embedded
    private Date lastDoneDate;
    private long refreshedDate;

    public User( @NonNull String userName){
        this.userId = 1;
        this.lvl = 1;
        this.points = 0;
        this.userName = userName;
        this.favHabit = "Not Available";
        this.streak = 0;
        this.revives = 2;
        this.habitLimit = 5;
        this.totalTimesDone = 0;
        this.habitCount = 0;
        this.refreshedDate = new Date().getDate();

    }

    public int getStreak() {
        return streak;
    }
    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getRevives() { return revives; }
    public void setRevives(int revives) {
        this.revives = revives;
    }

    public int getRevivesRemaining() {
        return revivesRemaining;
    }

    public void setRevivesRemaining(int revivesRemaining) {
        this.revivesRemaining = revivesRemaining;
    }

    //implements the Habit model
     //  @ForeignKey(Habit)
    //private Habit[] habits;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }
    public void setUserName(@NonNull String userName) {
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

    public int getHabitLimit() { return habitLimit; }
    public void setHabitLimit(int habitLimit) { this.habitLimit = habitLimit; }

    public String getFavHabit() { return favHabit; }
    public void setFavHabit(String favHabit) { this.favHabit = favHabit; }

    public int getTotalTimesDone() {
        return totalTimesDone;
    }

    public void setTotalTimesDone(int totalTimesDone) {
        this.totalTimesDone = totalTimesDone;
    }

    public Date getLastDoneDate() {
        return lastDoneDate;
    }

    public void setLastDoneDate(Date lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
    }

    public int getHabitCount() {
        return habitCount;
    }

    public void setHabitCount(int habitCount) {
        this.habitCount = habitCount;
    }

    public long getRefreshedDate() {
        return refreshedDate;
    }

    public void setRefreshedDate(long refreshedDate) {
        this.refreshedDate = refreshedDate;
    }

    public Boolean isRefreshed()
    {
        long day = new Date().getDay();

        if(day!=this.refreshedDate)
        {
            return true;
        }
        return false;
    }

    @Ignore
    public int habitDone(int points)
    {
        this.points += points;
        this.streak += 1;
        this.totalTimesDone += 1;

        return 0;
    }
}
