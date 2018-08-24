package com.sidekick.apps.habiter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase

/**
 * Created by HaRRy on 7/19/2018.
 */
class AddHabitFragment:Fragment() {

    lateinit var habitName:EditText
    lateinit var addButton:Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view:View= inflater.inflate(R.layout.fragment_add_habit,container,false)
        initializeWidgets(view)
        return view
    }


    private fun initializeWidgets(view:View) {
        habitName = view.findViewById<EditText>(R.id.fragment_addhabits_habit_name)
        addButton = view.findViewById<Button>(R.id.fragment_addhabits_habit_add_button)
        addButton.setOnClickListener(addButtonOnclick())
    }

    private fun addButtonOnclick(): OnClickListener? {
        return OnClickListener {
            val name:String = habitName.text.toString()
            if(name.isNotEmpty() ) {
                val habit: Habit = Habit()
                habit.id = HabitsDatabase.getDatabase(context).habitsDao().habitCount + 1
                one += 1
                habit.name = name
                habit.lvl = 0
                habit.points = 0
                HabitsDatabase.getDatabase(context).habitsDao().insertHabit(habit)
                habitName.setText("")
                Toast.makeText(context, "habit Inserted", Toast.LENGTH_SHORT).show()
                activity.onBackPressed()
            }
            else
            {
                habitName.hint = " enter habit"
            }

        }
    }
    companion object {
        var one:Int = 2
        fun getInstance():AddHabitFragment
        {
            return AddHabitFragment()
        }
    }
}