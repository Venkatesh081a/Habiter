package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Created by HaRRy on 9/10/2018.
 */

public interface UnsuccessfulHabitsDao {
    @Insert
    void insertUnsuccessfulHabit(UnsuccessfulHabit habit);
    @Query("SELECT * FROM UNSUCCESSFULHABITS")
    List<UnsuccessfulHabit> getUnsuccessfulHabits();

}

