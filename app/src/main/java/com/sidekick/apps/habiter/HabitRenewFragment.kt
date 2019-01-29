package com.sidekick.apps.habiter

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sidekick.apps.habiter.HabitListViewFragment.Companion.HABIT_ID
import com.sidekick.apps.habiter.models.Habit
import java.util.*

class HabitRenewFragment :DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val habitId = arguments.getInt(HABIT_ID)
        val view:View = View.inflate(activity,R.layout.fragment_renew_habit,null)

        return AlertDialog.Builder(activity).apply { 
            setTitle("renew it")
            setView(view)
            setPositiveButton("lets go",positiveButtonOnClickListener(habit = Habit()))


        }.create()

    }
    private fun positiveButtonOnClickListener(habit: Habit) = DialogInterface.OnClickListener()
    {dialogInterface, i ->
        Log.d("renewHabit","renewed")

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