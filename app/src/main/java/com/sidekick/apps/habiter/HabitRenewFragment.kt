package com.sidekick.apps.habiter

import android.app.Activity
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
import android.widget.Toast
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
    lateinit var frequencyTextView:TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val habitId = arguments.getInt(HABIT_ID)
        val view:View = View.inflate(activity,R.layout.fragment_renew_habit,null)
            habitName = view.findViewById(R.id.dialog_habit_name)
            frequencyTextView = view.findViewById(R.id.dialog_frequency_textview)
            frequencySeekBar = view.findViewById(R.id.dialog_renew_frequency)
            frequencySeekBar.setOnSeekBarChangeListener(frequencySeekBarChangeListener())
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
    {_, _ ->
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

    private fun frequencySeekBarChangeListener() = object :SeekBar.OnSeekBarChangeListener
    {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            val days = p1 +1
            val message = when(p1>1)
            {
                true -> "Repeat in $days days"
                false -> "Repeat in $days day"
            }


            frequencyTextView.text = message

        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }
        private fun refresh()
        {
            val activity  = this.activity
             val refreshData = activity as RefreshData
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