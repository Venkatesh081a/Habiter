package com.sidekick.apps.habiter

import android.support.v4.app.Fragment

class RewardsActivity:SingleFragmentActivity() {
    override fun addFragment(): Fragment {
        return RewardsFragment.getInstance()
    }

}