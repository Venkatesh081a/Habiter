package com.sidekick.apps.habiter

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.sidekick.apps.habiter.HabitListViewFragment.Companion.HABIT_ID
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase
import java.util.*
import kotlin.concurrent.thread

class HabitRenewFragment :DialogFragment() {
    lateinit var habitname:TextView
    lateinit var habitsDatabase: HabitsDatabase

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val habitId = arguments.getInt(HABIT_ID)
        habitsDatabase = HabitsDatabase.getDatabase(activity.applicationContext)
        thread {
            val habit = habitsDatabase.habitsDao().getOneHabit(habitId)
            habitname = view.findViewById(R.id.dialog_habit_name)
            habitname.text = habit.name

        }
        val view:View = View.inflate(activity,R.layout.fragment_renew_habit,null)

        return AlertDialog.Builder(activity).apply { 
            setTitle("Reconsider the Frequency")
            setView(view)
            setPositiveButton("lets go",positiveButtonOnClickListener(habitId))
            setNegativeButton("Cancel",negativeButtonOnClickListener())

        }.create()

    }
    private fun positiveButtonOnClickListener(habitId:Int) = DialogInterface.OnClickListener()
    {dialogInterface, i ->
        Log.d("renewHabit","renewed")
        thread {
            val habit = habitsDatabase.habitsDao().getOneHabit(habitId)
            habit.levelUp()
            habitsDatabase.habitsDao().updateHabit(habit)
            refresh()
        }

    }
    private fun negativeButtonOnClickListener() = DialogInterface.OnClickListener()
    {
        d,i ->
        dialog.onBackPressed()

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