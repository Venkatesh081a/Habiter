package com.sidekick.apps.habiter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase

/**
 * Created by HaRRy on 8/8/2018.
 */
class HabitListViewFragment:Fragment() {
    private class myListener(private val context: Context) : HabitClickListener {
        override fun onClick(p0: View?, position: Int) {
            Log.d("habit fragment", "clicked the habit" + position);
            val intent = Intent(context, ViewHabitActivity::class.java)
            intent.putExtra(HABIT_ID, position)
            startActivity(context, intent, null)
        }
    }

    private lateinit var habitsRecyclerView: RecyclerView
    private lateinit var recyclerViewLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: HabitsRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)

        habitsRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_habits_list)
        val habitsData: List<Habit> = HabitsDatabase.getDatabase(context.applicationContext).habitsDao().allHabits
        val fragmentManager = activity.fragmentManager

        mAdapter = HabitsRecyclerViewAdapter(habitsData, context, myListener(context),fragmentManager)
        recyclerViewLayoutManager = LinearLayoutManager(context.applicationContext)
       /* habitsRecyclerView.adapter = adapter
        habitsRecyclerView.layoutManager = recyclerViewLayoutManager
        habitsRecyclerView.hasFixedSize()*/
        habitsRecyclerView.apply {
            adapter = mAdapter
            layoutManager = recyclerViewLayoutManager
            hasFixedSize()
        }
        return view

    }


    companion object {
        val HABIT_ID = "habitId"
        public fun getInstance():HabitListViewFragment
        {
            return HabitListViewFragment()
        }
    }
}