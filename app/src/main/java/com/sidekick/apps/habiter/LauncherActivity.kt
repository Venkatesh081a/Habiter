package com.sidekick.apps.habiter


import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.sidekick.apps.habiter.models.HabitsDatabase
import com.sidekick.apps.habiter.models.User


class LauncherActivity : AppCompatActivity() {
    private lateinit var layoutContainer: ViewGroup
    private lateinit var textViewOne: TextView
    private lateinit var startButton: Button
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var slideFromTop:Transition
    private lateinit var slideFromBottom:Transition
    private lateinit var submitButton:Button
    private lateinit var nameEditText:EditText
    private lateinit var nameTextView:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        initializeWidgets()
        transitionAnimation()

    }

    private fun initializeWidgets() {
        layoutContainer = findViewById(R.id.launcher_container)
        submitButton = layoutContainer.findViewById(R.id.user_name_submit_button)
        nameEditText = layoutContainer.findViewById(R.id.user_name_edit_text)
        nameTextView = layoutContainer.findViewById(R.id.user_name_text_view)
        textViewOne = layoutContainer.findViewById(R.id.launcher_text_view_one)
        startButton = layoutContainer.findViewById(R.id.launcher_text_view_two)
        startButton.setOnClickListener(startButtonOnClickListener())
        submitButton.setOnClickListener(submitButtonOnClickListener())
        animationDrawable = layoutContainer.background as AnimationDrawable
        animationDrawable.apply {
            setEnterFadeDuration(4000)
            setExitFadeDuration(2000)
        }
        slideFromTop = Slide(Gravity.TOP).setDuration(700)
        slideFromBottom = Slide(Gravity.BOTTOM).setDuration(700)
    }

    private fun submitButtonOnClickListener(): View.OnClickListener? = View.OnClickListener{
        val userName = nameEditText.text.toString()
        val user = User(userName)
        Thread().run{
            HabitsDatabase.getDatabase(applicationContext).habitsDao().insertUser(user)
        }
        Log.d("user","userInserted")
        val intent = Intent(applicationContext,DashBoardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun startButtonOnClickListener(): View.OnClickListener? = View.OnClickListener{
            Thread().run {
                TransitionManager.beginDelayedTransition(layoutContainer,slideFromBottom)
                textViewOne.visibility = View.GONE
                startButton.visibility = View.GONE
                submitButton.visibility = View.VISIBLE
                nameEditText.visibility = View.VISIBLE
                nameTextView.visibility = View.VISIBLE

            }
    }

    private fun transitionAnimation()
    {
        Handler().postDelayed({
            TransitionManager.beginDelayedTransition(layoutContainer, slideFromTop)
            textViewOne.visibility = View.VISIBLE
            Handler().postDelayed({
                TransitionManager.beginDelayedTransition(layoutContainer, slideFromBottom)
                startButton.visibility = View.VISIBLE
            }, 200)


        }, 300)

    }

    override fun onResume() {
        super.onResume()
        if(animationDrawable !=null && !animationDrawable.isRunning)
        {
            animationDrawable.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if(animationDrawable != null && animationDrawable.isRunning )
        {
            animationDrawable.stop()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}

