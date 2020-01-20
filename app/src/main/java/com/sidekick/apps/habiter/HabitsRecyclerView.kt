package com.sidekick.apps.habiter

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.sidekick.apps.habiter.models.Habit
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.User
import me.itangqi.waveloadingview.WaveLoadingView
import java.util.*
import kotlin.concurrent.thread
import com.sidekick.apps.habiter.HabitRenewFragment
import com.sidekick.apps.habiter.models.Rewards
import java.util.logging.Handler

/**
 * Created by HaRRy on 8/7/2018.
 */
 class HabitsRecyclerViewAdapter(private val habitsList: List<Habit>, private val context: Context, private val clickListener: HabitClickListener,private val fm:android.app.FragmentManager)
    :RecyclerView.Adapter<HabitsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val habitName: TextView? = itemView.findViewById(R.id.list_item_habit_name)
        val habitStreak: TextView? = itemView.findViewById(R.id.list_item_habit_streak)
        val dayCount: TextView? = itemView.findViewById(R.id.list_item_count)
        val neededPoints: TextView? = itemView.findViewById(R.id.list_item_need_points)
        // val health:TextView = itemView.findViewById(R.id.list_item_health)
        val doneButton: Button = itemView.findViewById(R.id.list_item_button_done)
        val healthWaveLoadingView: WaveLoadingView = itemView.findViewById(R.id.list_item_health)
        //val progressWaveLoadingView:WaveLoadingView = itemView.findViewById(R.id.list_item_progress)


    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.habit_list_view, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return habitsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habit = habitsList[position]
        holder.habitName?.text = habit.name
        holder.habitStreak?.text = habit.streak.toString()
        holder.itemView.setOnClickListener {
            clickListener.onClick(holder.itemView, habit.id)
        }
        holder.dayCount?.text = habit.daysToComplete.toString()
        holder.healthWaveLoadingView.progressValue = habit.health * 10
        // holder.healthWaveLoadingView.waveColor = context.applicationContext.resources.getColor(R.color.bright_foreground_material_light)

        //holder.progressWaveLoadingView.progressValue = 58
        //holder.progressWaveLoadingView.waveColor = context.applicationContext.resources.getColor(R.color.colorGreen_A400)


        setUpDoneButton(holder, habit)


    }

    private fun setUpDoneButton(holder: HabitsRecyclerViewAdapter.ViewHolder, habit: Habit) {
        if (habit.daysToComplete != 0) {
            holder.doneButton.isEnabled = habit.enabledButton()
            holder.doneButton.setOnClickListener(doneButtonOnClickListener(habit))
        } else {
            holder.doneButton.text = "Next Level"
            holder.doneButton.isEnabled = true
            holder.doneButton.setOnClickListener(nextLevelOnClickListener(habit))
        }
    }

    private fun doneButtonOnClickListener(habit: Habit) = View.OnClickListener {
        Log.d("doneButton", "is clicked")
        Toast.makeText(context.applicationContext, habit.lastDoneDate.toString(), Toast.LENGTH_SHORT).show()

        habit.habitDone()
        if (habit.daysToComplete == 0) {
            val currentRewardId = habit.currentRewardId
            thread {
                val reward = HabitsDatabase.getDatabase(context.applicationContext).rewardsDao().getReward(currentRewardId)
                reward.isAvailable = true
                HabitsDatabase.getDatabase(context.applicationContext).rewardsDao().updateReward(reward)
                //createSnacKbarHERE
            }

        }
        thread {
            val habitDatabase: HabitsDatabase = HabitsDatabase.getDatabase(context.applicationContext)
            val user: User = habitDatabase.userDao().user[0]
            user.habitDone(100)
            habitDatabase.habitsDao().updateHabit(habit)
            habitDatabase.userDao().updateUser(user)
        }.run()
        notifyDataSetChanged()

    }

    private fun nextLevelOnClickListener(habit: Habit) = View.OnClickListener {
        Log.d("nextLevelOnClickLister", "is clicked")


        val habitRenewFragment = renewDialog(habit)
        habitRenewFragment.show()

        notifyDataSetChanged()
    }

    private fun renewDialog(habit: Habit): Dialog {
        val view: View = View.inflate(context, R.layout.fragment_renew_habit, null)
        val habitName: TextView = view.findViewById(R.id.dialog_habit_name)
        val frequencyTextView: TextView = view.findViewById(R.id.dialog_frequency_textview)
        val frequencySeekBar: SeekBar = view.findViewById(R.id.dialog_renew_frequency)
        frequencySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                val days = p1 + 1
                val message = when (p1 > 1) {
                    true -> "Repeat in $days days"
                    false -> "Repeat in $days day"
                }


                frequencyTextView.text = message

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        val rewardEditText: TextView = view.findViewById(R.id.dialog_renew_reward)
        thread {
            val habitsDatabase = HabitsDatabase.getDatabase(context.applicationContext)
            /* val habit = habitsDatabase
                    .habitsDao()
                    .getOneHabit(habitId)*/
            habitName.text = habit.name
            val reward = habitsDatabase.rewardsDao().getReward(habit.currentRewardId)
            val rewardText = reward.reward
            rewardEditText.text = rewardText
        }


        /*  fun positiveButtonOnClickListener(habitId:Int) = DialogInterface.OnClickListener()
        {_, _ ->
            Log.d("renewHabit","renewed")
            thread {
                val habitsDatabase = HabitsDatabase.getDatabase(context.applicationContext)
                val habit = habitsDatabase.habitsDao().getOneHabit(habitId)
                val currentReward = habitsDatabase.rewardsDao().getReward(habit.currentRewardId)
                currentReward.isAvailable = true
                habitsDatabase.rewardsDao().updateReward(currentReward)
                habit.levelUp()
                val reward = Rewards()
                reward.id = habitsDatabase.rewardsDao().rewardCount
                reward.reward = rewardEditText.text.toString()
                habit.currentRewardId = reward.id
                habitsDatabase.rewardsDao().insertOneReward(reward)
                habitsDatabase.habitsDao().updateHabit(habit)

            }*/


        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        builder.setPositiveButton("Lets go") { dialog, which ->
            thread {
                val habitsDatabase = HabitsDatabase.getDatabase(context.applicationContext)
                val currentReward = habitsDatabase.rewardsDao().getReward(habit.currentRewardId)
                currentReward.isAvailable = true
                habitsDatabase.rewardsDao().updateReward(currentReward)
                habit.levelUp()
                val reward = Rewards()
                reward.id = habitsDatabase.rewardsDao().rewardCount
                reward.reward = rewardEditText.text.toString()
                habit.currentRewardId = reward.id
                habitsDatabase.rewardsDao().insertOneReward(reward)
                habitsDatabase.habitsDao().updateHabit(habit)
            }


        builder.setNegativeButton("cancel") { dialog, which ->
            Toast.makeText(context,"no? ok",Toast.LENGTH_SHORT)
        }





    }
        return builder.create()
}

}