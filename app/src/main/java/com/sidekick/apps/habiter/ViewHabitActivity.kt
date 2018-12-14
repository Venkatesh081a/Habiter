package com.sidekick.apps.habiter

import android.support.v4.app.Fragment

/**
 * Created by HaRRy on 8/22/2018.
 */
class ViewHabitActivity:SingleFragmentActivity()
{
    override fun addFragment(): Fragment {
        val id:Int = intent.getIntExtra(HabitListViewFragment.HABIT_ID,0)
        return ViewHabitFragment.getInstance(id)
    }

}