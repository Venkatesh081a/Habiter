package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Created by HaRRy on 7/18/2018.
 */
@Entity
public class Habit {
    @PrimaryKey
    @Embedded
    private UUID id;
    private String name;
    private int lvl;
    private int points;
    private int streak;
    private int health;
    private int daysToComplete;
    @Embedded
    private Date startDate;
    @Embedded
    private Date lastDoneDate;
    public Habit()
    {
        id = UUID.randomUUID();
        startDate = new Date();
        lvl = 1;
        streak = 0;
        health = 10;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
    public void setDaysTocomplete(Boolean isFalse)
    {
        this.daysToComplete = -1;
    }
}
