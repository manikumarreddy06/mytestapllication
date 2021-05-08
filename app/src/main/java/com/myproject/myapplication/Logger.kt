package com.medfin

import android.util.Log
import com.myproject.myapplication.BuildConfig


class Logger {


    private fun Loggers() {
        throw UnsupportedOperationException("Cannot instantiate this class")
    }

    companion object {
        private val shouldShow: Boolean = BuildConfig.DEBUG

        fun e(tag: String, message: String) {
            if (shouldShow)
                Log.e(tag, message)
        }

        fun d(tag: String, message: String) {
            if (shouldShow)
                Log.d(tag, message)
        }

        fun i(tag: String, message: String) {
            if (shouldShow)
                Log.i(tag, message)
        }

    }

}