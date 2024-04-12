package com.acelan.acelanandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.acelan.acelanandroid.data.MaterialMain
import com.acelan.acelanandroid.data.TaskMain
import com.acelan.acelanandroid.data.UserData

@Database(
    entities = [
        MaterialMain::class,
        TaskMain::class,
        UserData::class
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