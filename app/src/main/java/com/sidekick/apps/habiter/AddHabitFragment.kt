package com.sidekick.apps.habiter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase

/**
 * Created by HaRRy on 7/19/2018.
 */
class AddHabitFragment:Fragment() {

    lateinit var habitNameEditText:EditText
    lateinit var addButton:Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view:View= inflater.inflate(R.layout.fragment_add_habit,container,false)
        initializeWidgets(view)
        return view
    }


    private fun initializeWidgets(view:View) {
        habitNameEditText = view.findViewById<EditText>(R.id.fragment_addhabits_habit_name)
        addButton = view.findViewById<Button>(R.id.fragment_addhabits_habit_add_button)
        addButton.setOnClickListener(addButtonOnclick())
    }

    private fun addButtonOnclick(): OnClickListener? {
        return OnClickListener {
            val name:String = habitNameEditText.text.toString()
            if(name.isNotEmpty() ) {
                val habit: Habit = Habit()
                habit.name = name
                HabitsDatabase.getDatabase(context).habitsDao().insertHabit(habit)
                habitNameEditText.setText("")
                Toast.makeText(context, "habit Inserted", Toast.LENGTH_SHORT).show()
                activity.onBackPressed()
            }
            else
            {
                habitNameEditText.hint = " enter habit"
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