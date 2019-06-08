package com.sidekick.apps.habiter

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.sidekick.apps.habiter.HabitListViewFragment.Companion.HABIT_ID
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.Rewards
import java.util.*
import kotlin.concurrent.thread

class HabitRenewFragment :DialogFragment() {
    lateinit var habitName:TextView
    lateinit var frequencySeekBar: SeekBar
    lateinit var rewardEditText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val habitId = arguments.getInt(HABIT_ID)
        val view:View = View.inflate(activity,R.layout.fragment_renew_habit,null)
            habitName = view.findViewById(R.id.dialog_habit_name)
            frequencySeekBar = view.findViewById(R.id.dialog_renew_frequency)
            rewardEditText = view.findViewById(R.id.dialog_renew_reward)

        thread {
            val habitsDatabase = HabitsDatabase.getDatabase(activity.applicationContext)
            val habit = habitsDatabase
                    .habitsDao()
                    .getOneHabit(habitId)
            habitName.text = habit.name
            val reward = habitsDatabase.rewardsDao().getReward(habit.currentRewardId)
            val rewardText = reward.reward
            rewardEditText.setText(rewardText)
        }
        return AlertDialog.Builder(activity).apply { 
            setTitle("renew it")
            setView(view)
            setPositiveButton("lets go",positiveButtonOnClickListener(habitId))
            setNegativeButton("Cancel",positiveButtonOnClickListener(habitId))

        }.create()

    }
    private fun positiveButtonOnClickListener(habitId:Int) = DialogInterface.OnClickListener()
    {dialogInterface, i ->
        Log.d("renewHabit","renewed")
        thread {
            val habitsDatabase = HabitsDatabase.getDatabase(activity.applicationContext)
            val habit = habitsDatabase.habitsDao().getOneHabit(habitId)
            val currentReward = habitsDatabase.rewardsDao().getReward(habit.currentRewardId)
            currentReward.isAvailable = true
            habitsDatabase.rewardsDao().updateReward(currentReward)
            habit.levelUp()
            val reward = Rewards()
            reward.id = habitsDatabase.rewardsDao().rewardCount
            reward.reward = rewardEditText.text.toString()
            habit.currentRewardId = reward.id
            habitsDatabase.rewardsDao().insertOneReward(reward)
            habitsDatabase.habitsDao().updateHabit(habit)

            refresh()
        }

    }


    private fun refresh() {
        val refreshData = this.activity as RefreshData
        refreshData.refreshData()

    }


    companion object {
        fun getInstance(habit: Habit):HabitRenewFragment
        {
            val bundle = Bundle()
            bundle.putInt(HABIT_ID,habit.id)

            val fragment = HabitRenewFragment()
            fragment.arguments = bundle
            return fragment



        }
    }
}