package com.sidekick.apps.habiter

import android.app.Application
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.User
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by HaRRy on 7/9/2018.
 */
class ProfileFragment:Fragment() {


    private lateinit var dp: CircleImageView
    private lateinit var levelProgressBar: ProgressBar
    private lateinit var pointsProgressBar: ProgressBar
    private lateinit var userName:TextView
    private lateinit var streakTextView: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        initializeWidgets(view)

        return view
    }

    private fun initializeWidgets(view: View) {
        dp = view.findViewById(R.id.profile_dp)
        userName = view.findViewById(R.id.profile_name)
        levelProgressBar = view.findViewById(R.id.profile_progress_level)
        pointsProgressBar = view.findViewById(R.id.profile_progress_points)
        streakTextView = view.findViewById(R.id.streak_text_view_profile_fragment)
        bindData()
    }
    private fun bindData() {
        try {
            Thread().run {
                val user: User = HabitsDatabase.getDatabase(context.applicationContext).userDao().user[0]

                streakTextView.text = user.streak.toString()
                userName.text = user.userName
                val level: Int = user.lvl + 25
                val points: Int = user.points + 30
                levelProgressBar.progress = level
                pointsProgressBar.progress = points
            }
        } catch (exception: Exception) {
            Log.e("DataBaseError", "UserNotFound")
                       }
    }
    companion object {
        fun getInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}
