package com.yossisegev.movienight.common

import android.transition.Transition

/**
 * Created by Yossi Segev on 12/01/2018.
 */
class SimpleTransitionEndedCallback(private var callback: () -> Unit): Transition.TransitionListener {

    override fun onTransitionEnd(transition: Transition?) {
        callback()
    }

    override fun onTransitionResume(transition: Transition?) {

    }

    override fun onTransitionPause(transition: Transition?) {

    }

    override fun onTransitionCancel(transition: Transition?) {

    }

    override fun onTransitionStart(transition: Transition?) {

    }

}