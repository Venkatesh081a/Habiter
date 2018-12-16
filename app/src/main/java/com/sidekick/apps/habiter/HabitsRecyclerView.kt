package com.sidekick.apps.habiter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase
import java.time.YearMonth
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by HaRRy on 8/7/2018.
 */
 class HabitsRecyclerViewAdapter(private val habitsList:List<Habit>,private val context:Context,private val clickListener:HabitClickListener)
    :RecyclerView.Adapter<HabitsRecyclerViewAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habit = habitsList[position]
        holder.habitName?.text =habit.name
        holder.habitStreak?.text = habit.streak.toString()
        holder.itemView.setOnClickListener {
            clickListener.onClick(holder.itemView,position)
        }
        holder.dayCount?.text = habit.daysToComplete.toString()
        holder.health.text = habit.health.toString()
        //holder.neededPoints = habit.needPoints
       holder.doneButton.isEnabled = calculateEnabledButton(habit)
        holder.doneButton.setOnClickListener(doneButtonOnClickListener(habit))

    }

    private fun calculateEnabledButton(habit: Habit):Boolean
    {   val difference:Int
       // val yearMonth:YearMonth = YearMonth.of(habit.lastDoneDate.year,habit.lastDoneDate.month)

        val day:Int = Date().day
        val habitDays = habit.lastDoneDate.day
        val month = Date().month
        val habitMonth = habit.lastDoneDate.month
        //val totalRemainingDays = yearMonth.lengthOfMonth() -habitDays
        difference = when {
            (month == habitMonth) -> day - habitDays

            else -> 5 + habitDays
        }


//        val difference:Int = Date().minutes - habit.lastDoneDate.minutes
       if(difference > habit.frequency)
       {
           return true
       }
        return false
    }

    private fun doneButtonOnClickListener(habit:Habit) =View.OnClickListener {
        Log.d("doneButton","is clicked")
        Toast.makeText(context.applicationContext,habit.lastDoneDate.minutes.toString(),Toast.LENGTH_SHORT).show()
        habit.habitDone()
        thread {HabitsDatabase.getDatabase(context.applicationContext).habitsDao().updateHabit(habit)  }.run()

    }



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.habit_list_view,parent,false)
        v.setOnClickListener {
            Toast.makeText(context,"item clicked",Toast.LENGTH_SHORT).show()
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return habitsList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val habitName:TextView? = itemView.findViewById(R.id.list_item_habit_name)
        val habitStreak:TextView? = itemView.findViewById(R.id.list_item_habit_streak)
        val dayCount:TextView? = itemView.findViewById(R.id.list_item_count)
        val neededPoints:TextView? = itemView.findViewById(R.id.list_item_need_points)
        val health:TextView = itemView.findViewById(R.id.list_item_health)
        val doneButton:Button = itemView.findViewById(R.id.list_item_button_done)


    }
}