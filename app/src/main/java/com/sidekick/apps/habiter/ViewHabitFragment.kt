package com.sidekick.apps.habiter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.sidekick.apps.habiter.models.HabitsDatabase
import java.util.*

/**
 * Created by HaRRy on 7/25/2018.
 */
class ViewHabitFragment:Fragment()
{
    private lateinit var habitName:TextView
    private lateinit var pointsProgressBar:ProgressBar
    private lateinit var lvlProgressBar:ProgressBar
    private lateinit var streak:TextView
    private lateinit var daysToComplete:TextView
    private lateinit var totalTimesDone:TextView
    private lateinit var frequency:TextView
    private lateinit var lastDone:TextView
    private lateinit var startDate:TextView
    private lateinit var health:TextView
    private lateinit var backButton:Button
    private lateinit var roomDatabaseInstance:HabitsDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_habit,container,false)
        initializeWidgets(view)
        roomDatabaseInstance = HabitsDatabase.getDatabase(activity)
        val id:Int = arguments.getInt(HABIT_ID)
        setUpView(id)

        return view
    }



    private fun initializeWidgets(view:View) {
        habitName = view.findViewById(R.id.view_habit_name)
        pointsProgressBar = view.findViewById(R.id.view_habit_points_progressbar)
        lvlProgressBar = view.findViewById(R.id.view_habit_lvl_progress_bar)
        streak = view.findViewById(R.id.view_habit_streak)
        daysToComplete = view.findViewById(R.id.view_habit_days_to_comlete)
        totalTimesDone = view.findViewById(R.id.view_habit_total_times_done)
        frequency = view.findViewById(R.id.view_habit_frequency)
        lastDone = view.findViewById(R.id.view_habit_last_done_date)
        startDate = view.findViewById(R.id.view_habit_start_date)
        health = view.findViewById(R.id.view_habit_health)
        backButton = view.findViewById(R.id.view_habit_button_back)
    }

    private fun setUpView(id:Int) {
        try {

            val habit = roomDatabaseInstance.habitsDao().getOneHabit(id)
            habitName.text = habit.name
            pointsProgressBar.progress = habit.points + 56
            lvlProgressBar.progress = habit.lvl + 60
            streak.text = habit.streak.toString()
            daysToComplete.text = habit.daysToComplete.toString()
            totalTimesDone.text = habit.totalTimesDone.toString()
            frequency.text = (habit.frequency).toString()
            lastDone.text = Date(habit.lastDoneDate).toString()
            startDate.text = Date(habit.startDate).toString()
            health.text = habit.health.toString()
            backButton.setOnClickListener(backButtonOnClickListener())

        }
        catch (exception:Exception)
        {


        }
    }
    private fun backButtonOnClickListener():View.OnClickListener  = View.OnClickListener {
        activity.onBackPressed()
    }
    companion object {
        val VIEW_HABIT_FRAGMENT_TAG = "ViewHabitFragment"
        val HABIT_ID = "HabitId"
        fun getInstance(id:Int): ViewHabitFragment {
            val viewFragment = ViewHabitFragment()
            val bundle = Bundle()
            bundle.putInt(HABIT_ID,id)
            viewFragment.arguments = bundle
            return viewFragment
            }
    }
}