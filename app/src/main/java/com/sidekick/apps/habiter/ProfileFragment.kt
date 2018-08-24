package com.sidekick.apps.habiter

import android.app.Application
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.v4.app.Fragment
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


    lateinit var dp: CircleImageView
    lateinit var levelProgressBar: ProgressBar
    lateinit var pointsProgressBar: ProgressBar
    private lateinit var streakTextView: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        initializeWidgets(view)

        return view
    }

    private fun initializeWidgets(view: View) {
        dp = view.findViewById(R.id.profile_dp)
        val user: User = HabitsDatabase.getDatabase(context).habitsDao().user[0]
        levelProgressBar = view.findViewById(R.id.profile_progress_level)
        pointsProgressBar = view.findViewById(R.id.profile_progress_points)
        streakTextView = view.findViewById(R.id.streak_text_view_profile_fragment)
        streakTextView.text = user.streak.toString()
        val level: Int = user.lvl + 25
        val points: Int = user.points + 30
        levelProgressBar.progress = level
        pointsProgressBar.progress = points
    }
    companion object {
        public fun getInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}
