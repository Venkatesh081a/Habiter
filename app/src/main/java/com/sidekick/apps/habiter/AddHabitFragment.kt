package com.sidekick.apps.habiter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.*
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.Rewards
import com.sidekick.apps.habiter.models.User
import java.util.*

/**
 * Created by HaRRy on 7/19/2018.
 */
class AddHabitFragment:Fragment() {

    private lateinit var habitNameEditText:EditText
    private lateinit var addButton:Button
    private lateinit var frequencySeekBar:SeekBar
//    private lateinit var dayGoalTextView:TextView
    private lateinit var frequencyTextView: TextView
    private lateinit var rewardTextView: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view:View= inflater.inflate(R.layout.fragment_add_habit,container,false)
        initializeWidgets(view)
        return view
    }


    private fun initializeWidgets(view:View) {
        habitNameEditText = view.findViewById(R.id.fragment_addhabits_habit_name)
       // habitNameEditText.onKeyDown()
       // dayGoalTextView = view.findViewById(R.id.day_goal_textview)
        frequencyTextView = view.findViewById(R.id.frequency_text_view)
        rewardTextView = view.findViewById(R.id.reward)
        addButton = view.findViewById(R.id.fragment_addhabits_habit_add_button)
        addButton.isEnabled = addButtonEnabledConditions()
        addButton.setOnClickListener(addButtonOnclick())
        frequencySeekBar = view.findViewById(R.id.add_fragment_frequency)
        frequencySeekBar.setOnSeekBarChangeListener(frequencySeekBarChangeListener())

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


    private fun addButtonEnabledConditions():Boolean
    {
        val habitsDatabase = HabitsDatabase.getDatabase(context.applicationContext)
        val tokens:Int = habitsDatabase.userDao().user[0].tokens
        if(tokens <= 0)
        {
            return false
        }
        return true

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
                habitNameEditText.hint = " enter habi"
            }

        }
    }

    private fun setUpHabitData(name:String): Habit {
        val habit = Habit()
        habit.name = name
        habit.frequency = frequencySeekBar.progress+1
        habit.startDate = Date().time
        habit.lastDoneDate = Date().time
        return habit
    }

    private fun insertHabit(habit :Habit) {
        Thread().run()
                {
                    val habitsDatabase = HabitsDatabase.getDatabase(context.applicationContext)

                    val user: User = habitsDatabase.userDao().user[0]
                    habit.id = user.habitCount
                    val rewards = Rewards()
                    rewards.id = habitsDatabase.rewardsDao().rewardCount
                    rewards.reward = rewardTextView.text.toString()
                    rewards.habitId = habit.id
                    rewards.habitName = habit.name
                    habit.currentRewardId = rewards.id
                    if(user.tokens !=0)
                    {

                    habitsDatabase.habitsDao().insertHabit(habit)
                    user.habitCount += 1
                    user.tokens -= 1
                    habitsDatabase.userDao().updateUser(user)
                    habitsDatabase.rewardsDao().insertOneReward(rewards)
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