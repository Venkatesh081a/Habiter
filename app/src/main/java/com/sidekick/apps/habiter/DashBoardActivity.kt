package com.sidekick.apps.habiter

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.User

/**
 * Created by HaRRy on 7/7/2018.
 */
 class DashBoardActivity:AppCompatActivity()
{
    private lateinit var toolbar:Toolbar
    private lateinit var fm:FragmentManager
    private lateinit var sharedprefs:SharedPreferences
    private lateinit var fabAddButton:FloatingActionButton
    private lateinit var relativeLayout:RelativeLayout
    override fun onStart() {
        super.onStart()
        oneTimeLaunchScreen()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        fm = supportFragmentManager
        setUpToolbar()
        initializeWidgets()
        addHabitsListViewFragment()
        }

    private fun initializeWidgets() {

        fabAddButton = findViewById(R.id.fab_add_habit)
        fabAddButton.setOnClickListener(fabAddButtonClickListener())
        relativeLayout = findViewById(R.id.dashboard_relative_layout)
    }

    private fun fabAddButtonClickListener(): View.OnClickListener? {
        return View.OnClickListener {
            //Snackbar.make(relativeLayout,"fab button clicked",Snackbar.LENGTH_LONG).show()
              val intent = Intent(this,AddHabitActivity::class.java)
                startActivity(intent)
        }
    }

    private fun addHabitsListViewFragment() {
      // fm.beginTransaction().add(R.id.list_view_fragment,HabitListViewFragment.getInstance()).commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_option_menu,menu)
        return true
    }


    private fun oneTimeLaunchScreen()

    {

        val userList = HabitsDatabase.getDatabase(applicationContext).habitsDao().user

        if(userList.size == 0 )   {
            val intent = Intent(applicationContext,LauncherActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)}

    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.dashboard_toolbar)
        setSupportActionBar(toolbar)
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
    }

    override fun onResume() {
        super.onResume()
       fm.beginTransaction().replace(R.id.list_view_fragment,HabitListViewFragment.getInstance()).commit()


    }

    companion object {
        val DASHBOARD_TAG = "DashBoardActivity"
    }


}