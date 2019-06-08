package com.sidekick.apps.habiter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.Rewards
import kotlin.concurrent.thread


public class RewardsRecyclerViewAdapter(private val rewards:ArrayList<Rewards>,private val habitsDatabase: HabitsDatabase):RecyclerView.Adapter<RewardsRecyclerViewAdapter.ViewHolder>()
{


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val rewardsNameTextName:TextView = itemView.findViewById(R.id.reward_list_item_name)
        val rewardsRecieveButton: Button = itemView.findViewById(R.id.reward_list_item_button)
        val rewardHabitName:TextView = itemView.findViewById(R.id.reward_list_item_habitname)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
      val view  = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_rewards,null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempReward = rewards[position]
        holder.rewardsNameTextName.text = tempReward.reward.toString()
        holder.rewardsRecieveButton.isEnabled = tempReward.isAvailable
        holder.rewardsRecieveButton.setOnClickListener(rewardsRecieveButtonClickListener(tempReward))
        holder.rewardHabitName.text = tempReward.habitName
        if(tempReward.isReceived)
        {
            holder.rewardsRecieveButton.text = "Completed"
        }
        else
        {
            holder.rewardsRecieveButton.text = "Available soon"
        }

    }


    override fun getItemCount(): Int {
        return rewards.size
    }

    private fun rewardsRecieveButtonClickListener(reward: Rewards): View.OnClickListener? {
        return View.OnClickListener {
            thread {
                reward.isReceived = true
                reward.isAvailable = false
                habitsDatabase.rewardsDao().updateReward(reward)

            }
            notifyDataSetChanged()
        }
    }


}