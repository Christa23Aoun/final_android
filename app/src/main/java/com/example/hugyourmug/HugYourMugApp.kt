package com.example.hugyourmug

import android.app.Application
import com.google.firebase.FirebaseApp

class HugYourMugApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
