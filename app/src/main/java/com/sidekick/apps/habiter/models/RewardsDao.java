package com.sidekick.apps.habiter.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RewardsDao {
   @Insert
   void insertOneReward(Rewards reward);

    @Query("SELECT * FROM rewards ORDER BY received")
    List<Rewards> getAllRewards();

    @Query("SELECT COUNT(id) FROM Rewards")
    int getRewardCount();

    @Query("SELECT * FROM rewards where habitId=:habitId")
    List<Rewards> getCurrentHabitRewards(int habitId);

    @Query("SELECT * FROM rewards where id=:rewardId")
    Rewards getReward(int rewardId);

    @Update
    void updateReward(Rewards reward);
}
