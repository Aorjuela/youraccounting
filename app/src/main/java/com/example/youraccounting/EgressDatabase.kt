package com.example.youraccounting

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Egress::class], version = 3, exportSchema = false)
abstract class EgressDatabase : RoomDatabase() {

    abstract fun egressDao(): EgressDao

    companion object {
        var INSTANCE: EgressDatabase? = null

        fun getInstance(context: Context): EgressDatabase? {
            if (INSTANCE == null){
                synchronized(EgressDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, EgressDatabase::class.java, "myDBEgresses")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}