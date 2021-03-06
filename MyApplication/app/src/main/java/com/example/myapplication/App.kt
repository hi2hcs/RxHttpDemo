package com.example.myapplication

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this;
    }
    companion object {
        lateinit var context: Context;
        fun getInstance():Context {
            return context;
        }
    }
}