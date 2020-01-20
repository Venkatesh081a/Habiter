package com.sidekick.apps.habiter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.Rewards


class RewardsFragment:Fragment() {
    lateinit var habitsDatabase: HabitsDatabase
    lateinit var rewardsRecyclerViewAdapter: RewardsRecyclerViewAdapter
    lateinit var rewardsRecyclerView:RecyclerView
    lateinit var rewards:ArrayList<Rewards>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_rewards,container,false)
        habitsDatabase = HabitsDatabase.getDatabase(context.applicationContext)
        rewards = habitsDatabase.rewardsDao().allRewards as ArrayList<Rewards>
        setupView(view)
        return view
    }

    private fun setupView(view:View) {
        val rewardsLayoutManager = LinearLayoutManager(context.applicationContext)
        rewardsRecyclerView =view.findViewById(R.id.rewards_recycler_view)
        rewardsRecyclerViewAdapter  = RewardsRecyclerViewAdapter(rewards,habitsDatabase)
        rewardsRecyclerView.apply {
            adapter = rewardsRecyclerViewAdapter
            layoutManager  = rewardsLayoutManager
        }

    }
    companion object {
        fun getInstance():RewardsFragment
        {
        return RewardsFragment()
        }
    }
}