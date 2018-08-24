package com.sidekick.apps.habiter

import android.support.v4.app.Fragment

/**
 * Created by HaRRy on 8/22/2018.
 */
class AddHabitActivity:SingleFragmentActivity()
{

    override fun addFragment(): Fragment {
        return AddHabitFragment.getInstance()
    }
    companion object {
        val TAG = "AddHabitActivity"
    }
}