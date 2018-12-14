package com.sidekick.apps.habiter

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.User
import de.hdodenhof.circleimageview.CircleImageView


/**
 * Created by HaRRy on 8/4/2018.
 */

class ProfileOverviewFragment:Fragment()
{
    private lateinit var profileImage:CircleImageView
    private lateinit var lvlTextView:TextView
    private lateinit var pointsTextView:TextView
    private lateinit var streakTextView:TextView
    private lateinit var database:HabitsDatabase
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_profile_overview,container,false)
        database = HabitsDatabase.getDatabase(context.applicationContext)
        initializeWidgets(view)
        return view

    }

    private fun initializeWidgets(view: View) {
        try {
         Thread().run {val user:User = database.userDao().user[0]

        profileImage = view.findViewById(R.id.profile_image_view)
        profileImage.setOnClickListener(profileImageOnClick())
        lvlTextView = view.findViewById(R.id.profile_lvl_text_view)
        lvlTextView.text = user.lvl.toString()
        pointsTextView = view.findViewById(R.id.profile_points_text_view)
        pointsTextView.text = user.points.toString()
        streakTextView = view.findViewById(R.id.profile_streak_text_view)
        streakTextView.text = user.streak.toString()}}
        catch (exception:Exception)
        {

        }
    }


    private fun profileImageOnClick(): View.OnClickListener = View.OnClickListener { view ->
        val intent = Intent(activity,ProfileActivity::class.java)
        startActivity(intent)

    }

}
