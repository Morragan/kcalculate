package com.example.dietapp.di.module

import android.app.Application
import androidx.room.Room
import com.example.dietapp.db.DietDatabase
import com.example.dietapp.db.dao.FriendDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    private var dietDbInstance: DietDatabase? = null

    @Provides
    @Singleton
    @JvmStatic
    fun provideDatabase(application: Application): DietDatabase{
        if(dietDbInstance != null) return dietDbInstance!!

        synchronized(this){
            val instance = Room.databaseBuilder(
                application.applicationContext,
                DietDatabase::class.java,
                "diet_database"
            ).build()
            dietDbInstance = instance
            return instance
        }
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideFriendDao(database: DietDatabase): FriendDao = database.friendDao()

}