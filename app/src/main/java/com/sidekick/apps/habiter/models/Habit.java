package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.text.style.TtsSpan;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HaRRy on 7/18/2018.
 */
@Entity
public class Habit {
    @PrimaryKey
    private int id;
    private String name;
    private int lvl;
    private int points;
    private int streak;
    private int health;
    private int daysToComplete;
    private int totalTimesDone;
    @Embedded
    private Date startDate;
    @Embedded
    private Date lastDoneDate;
    private int frequency;
    @Ignore
    private int[] dayGoals = {1,2,2,3,3,3,4,5,6,5,6,7,7,7,8,8,8,9,10,14,15};

    public Habit()
    {
        startDate = new Date();
        lvl = 1;
        streak = 0;
        health = 10;
        lastDoneDate = new Date();

    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLastDoneDate() {
        return lastDoneDate;
    }

    public void setLastDoneDate(Date lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
    }

    public int getDaysToComplete() {
        return daysToComplete;
    }

    public void setDaysToComplete(int daysToComplete) {
        this.daysToComplete = daysToComplete;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    public int getTotalTimesDone() {
        return totalTimesDone;
    }

    public void setTotalTimesDone(int totalTimesDone) {
        this.totalTimesDone = totalTimesDone;
    }

    public int habitDone()
    {

        this.daysToComplete -= 1;
        this.streak +=1;
        this.totalTimesDone +=1;
        this.lastDoneDate = new Date();
        return rewardCode();
    }
    public int rewardCode()
    {
        return 0;
    }



    @Ignore
    public boolean isNotDone()
    {
       return false;
    }
    @Ignore
    public void decreaseHealth() {
        if(this.health >0)
        {
            this.health -= 1;
        }
        else
        {
            this.health = 0;
        }
    }
}
