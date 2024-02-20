package com.example.acelanandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.acelanandroid.data.FloatListConverter
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.singleData.Material
import com.example.acelanandroid.data.singleData.MaterialDetails
import com.example.acelanandroid.data.singleData.Task
import com.example.acelanandroid.data.singleData.TaskDetails
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Singleton

@Database(
    entities = [
        MaterialMain::class,
        TaskMain::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(FloatListConverter::class)
abstract class MainDB : RoomDatabase() {
    abstract val dao: Dao
    companion object {
        fun createDataBase(context: Context): MainDB {
            return Room.databaseBuilder(
                context,
                MainDB::class.java,
                "main.db"
            ).build()
        }
    }
}