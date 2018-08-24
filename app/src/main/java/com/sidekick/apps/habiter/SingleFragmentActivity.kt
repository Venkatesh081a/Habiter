package com.sidekick.apps.habiter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HaRRy on 8/22/2018.
 */

public abstract class SingleFragmentActivity:AppCompatActivity()
{
    lateinit var fragmentManager:FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)
        fragmentManager = supportFragmentManager
        val fragment:Fragment = addFragment()
        fragmentManager.beginTransaction().add(R.id.single_fragment_activity_container,fragment).commit()

    }
    abstract fun addFragment():Fragment

}

