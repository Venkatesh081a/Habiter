package com.sidekick.apps.habiter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

/**
 * Created by HaRRy on 8/4/2018.
 */
class ProfileActivity:SingleFragmentActivity(){
    override fun addFragment(): Fragment {
        return ProfileFragment.getInstance()
    }
}
