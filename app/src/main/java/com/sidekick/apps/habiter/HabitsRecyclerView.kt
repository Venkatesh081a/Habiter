package com.sidekick.apps.habiter

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.sidekick.apps.habiter.models.Habit

/**
 * Created by HaRRy on 8/7/2018.
 */
 class HabitsRecyclerViewAdapter(private val habitsList:List<Habit>,private val context:Context,private val clickListener:CustomClickListener)
    :RecyclerView.Adapter<HabitsRecyclerViewAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.habitName?.text =habitsList[position].name
        holder.habitStreak?.text = habitsList[position].streak.toString()
        holder.itemView.setOnClickListener(View.OnClickListener {
            clickListener.onClick(holder.itemView,position)
        })


    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.habit_list_view,parent,false)
        v.setOnClickListener( {
            Toast.makeText(context,"item clicked",Toast.LENGTH_SHORT).show()
        })
        var viewHolder = ViewHolder(v)
        return viewHolder
    }

    override fun getItemCount(): Int {
       return habitsList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val habitName:TextView? = itemView.findViewById(R.id.list_item_habit_name)
        val habitStreak:TextView? = itemView.findViewById(R.id.list_item_habit_streak)


    }
}