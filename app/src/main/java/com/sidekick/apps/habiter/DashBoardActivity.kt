package com.sidekick.apps.habiter

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.User

/**
 * Created by HaRRy on 7/7/2018.
 */
public class DashBoardActivity:AppCompatActivity()
{
    private lateinit var toolbar:Toolbar
    private lateinit var fm:FragmentManager
    private lateinit var sharedprefs:SharedPreferences
    private lateinit var fabAddButton:FloatingActionButton
    private lateinit var relativeLayout:RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        fm = supportFragmentManager
        oneTimeLaunchScreen()
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
        return View.OnClickListener { //Snackbar.make(relativeLayout,"fab button clicked",Snackbar.LENGTH_LONG).show()
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

   /* fun addHabitsFragment(view: View)
    {

        if(view.id == R.id.add_habits_fragment_button) {
                        Toast.makeText(applicationContext, "hello", Toast.LENGTH_SHORT).show()
                        fm.beginTransaction().addToBackStack("replace").replace(R.id.activity_dashboard_fragment_container_points, AddHabitFragment.getInstance()).commit()
                    }
        else if(view.id == R.id.view_habits_fragment_button)
                    {
                        Toast.makeText(applicationContext,"hello",Toast.LENGTH_SHORT).show()
                        fm.beginTransaction().addToBackStack("replaceWithViewFragment").replace(R.id.activity_dashboard_fragment_container_points,ViewHabitFragment.getInstance()).commit()
                    }

    }*/
    private fun oneTimeLaunchScreen()

    {
        sharedprefs = PreferenceManager.getDefaultSharedPreferences(this)
        var splashScreen:Boolean = sharedprefs.getBoolean("splashScreen",false)
        if(!splashScreen)
        {

           val intent:Intent = Intent(this,LauncherActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            val editor:SharedPreferences.Editor = sharedprefs.edit()
            editor.putBoolean("splashScreen",true)
            editor.commit()
            Thread().run { HabitsDatabase.getDatabase(applicationContext).habitsDao().insertUser(User("new user"))  }

        }

    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.dashboard_toolbar)
        setSupportActionBar(toolbar)
        window.statusBarColor = resources.getColor(R.color.accent_material_dark)
    }

    override fun onResume() {
        super.onResume()
        fm.beginTransaction().replace(R.id.list_view_fragment,HabitListViewFragment.getInstance()).commit()

    }

    companion object {
        val DASHBOARD_TAG = "DashBoardActivity"
    }


}