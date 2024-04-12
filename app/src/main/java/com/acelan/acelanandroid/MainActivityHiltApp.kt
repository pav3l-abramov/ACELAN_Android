package com.acelan.acelanandroid

import android.app.Application
import android.content.Context
import com.acelan.acelanandroid.opengl.Model
import com.acelan.acelanandroid.room.MainDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class MainActivityHiltApp : Application() {
    val database by lazy { MainDB.createDataBase(this) }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        lateinit var instance: MainActivityHiltApp
        var currentModel: Model? = null
    }
    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {
        @Provides
        fun provideMainDB(@ApplicationContext context: Context): MainDB {
            return MainDB.createDataBase(context)
        }
    }
}