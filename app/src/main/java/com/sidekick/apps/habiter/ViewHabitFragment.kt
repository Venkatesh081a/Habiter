package com.sidekick.apps.habiter

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.sidekick.apps.habiter.models.HabitsDao
import com.sidekick.apps.habiter.models.HabitsDatabase

/**
 * Created by HaRRy on 7/25/2018.
 */
class ViewHabitFragment:Fragment()
{
    private lateinit var habitName:TextView
    private lateinit var pointsProgressBar:ProgressBar
    private lateinit var lvlProgressBar:ProgressBar
    private lateinit var roomDatabaseInstance:HabitsDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_habit,container,false)
        initializeWidgets(view)
        roomDatabaseInstance = HabitsDatabase.getDatabase(activity)
        val id:Int = arguments.getInt(HABIT_ID)
        setUpView(id)

        return view
    }

    private fun setUpView(id:Int) {
        try {

            val habit = roomDatabaseInstance.habitsDao().getOneHabit(id)
            habitName.text = habit.name
            pointsProgressBar.progress = habit.points + 56
            lvlProgressBar.progress = habit.lvl + 6*10
        }
        catch (exception:Exception)
        {


        }
    }

    private fun initializeWidgets(view:View) {
        habitName = view.findViewById(R.id.habit_name_view)
        pointsProgressBar = view.findViewById(R.id.progress_bar_points_view)
        lvlProgressBar = view.findViewById(R.id.progress_bar_lvl_view)
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