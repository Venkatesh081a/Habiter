package com.sidekick.apps.habiter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.*
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.User
import java.util.*

/**
 * Created by HaRRy on 7/19/2018.
 */
class AddHabitFragment:Fragment() {

    private lateinit var habitNameEditText:EditText
    private lateinit var addButton:Button
    private lateinit var frequencySeekBar:SeekBar
    private lateinit var dayGoalSeekBar:SeekBar
    private lateinit var dayGoalTextView:TextView
    private lateinit var frequencyTextView: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view:View= inflater.inflate(R.layout.fragment_add_habit,container,false)
        initializeWidgets(view)
        return view
    }


    private fun initializeWidgets(view:View) {
        habitNameEditText = view.findViewById(R.id.fragment_addhabits_habit_name)
        dayGoalTextView = view.findViewById(R.id.day_goal_textview)
        frequencyTextView = view.findViewById(R.id.frequency_text_view)
        addButton = view.findViewById(R.id.fragment_addhabits_habit_add_button)
        addButton.setOnClickListener(addButtonOnclick())
        frequencySeekBar = view.findViewById(R.id.add_fragment_frequency)
        frequencySeekBar.setOnSeekBarChangeListener(frequencySeekBarChangeListener())
        dayGoalSeekBar = view.findViewById(R.id.add_fragment_day_goal)
        dayGoalSeekBar.setOnSeekBarChangeListener(dayGoalSeekBarChangeListener())
    }
    private fun frequencySeekBarChangeListener() = object :SeekBar.OnSeekBarChangeListener
    {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
             var message = String()
            if(p1>1) {
                message= "Repeat in $p1 days"
            }
            else
            {
                message = "Repeat in $p1 day"
            }

            frequencyTextView.text = message

        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }
    private fun dayGoalSeekBarChangeListener() = object :SeekBar.OnSeekBarChangeListener
    {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            val message:String = "$p1 times"
            dayGoalTextView.text = message
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }


    private fun addButtonOnclick(): OnClickListener? {
        return OnClickListener {
            val name:String = habitNameEditText.text.toString()
            if(name.isNotEmpty() ) {
                 val habit:Habit = setUpHabitData(name)
                 insertHabit(habit)

                habitNameEditText.setText("")
                Toast.makeText(context, "habit Inserted", Toast.LENGTH_SHORT).show()

            }
            else
            {
                habitNameEditText.hint = " enter habit"
            }

        }
    }
    private fun setUpHabitData(name:String): Habit {
        val habit = Habit()
        habit.name = name
        habit.daysToComplete = dayGoalSeekBar.progress
        habit.frequency = frequencySeekBar.progress
        habit.startDate = Date()
        habit.lastDoneDate = Date()
        return habit
    }

    private fun insertHabit(habit :Habit) {
        Thread().run()
                {
                    val appContext = context.applicationContext
                    val user: User = HabitsDatabase.getDatabase(appContext).userDao().user[0]
                    habit.id = user.habitCount
                    if(user.habitLimit !=0)
                    {
                    HabitsDatabase.getDatabase(appContext).habitsDao().insertHabit(habit)
                    user.habitCount += 1
                    user.habitLimit -= 1
                    HabitsDatabase.getDatabase(appContext).userDao().updateUser(user)
                    activity.onBackPressed()
                }
                }
    }



    companion object {
        fun getInstance():AddHabitFragment
        {
            return AddHabitFragment()
        }
    }
}