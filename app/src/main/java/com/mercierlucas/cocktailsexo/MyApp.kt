package com.mercierlucas.cocktailsexo

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    companion object{
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "final")
            .fallbackToDestructiveMigration()
            .build()
    }
}

