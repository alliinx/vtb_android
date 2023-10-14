package com.example.vtb_test

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.vtb_test.data.database.BankDatabase


class App:Application() {

    companion object {
        lateinit var instance: App
        lateinit var database: BankDatabase
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        database = databaseBuilder(this, BankDatabase::class.java, "database")
            .build()
    }

    fun getInstance(): App? {
        return instance
    }

    fun getDatabase(): BankDatabase? {
        return database
    }
}