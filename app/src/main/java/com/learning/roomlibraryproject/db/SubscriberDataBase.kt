package com.learning.roomlibraryproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This class is used to represent the actual data of application and it a sub class of RoomDatabase clss
 */

@Database(entities = [Subscriber::class], version = 1, exportSchema = false)
abstract class SubscriberDataBase : RoomDatabase() {
    // we have only one entity class and a corresponding DAO Interface. so we
    // need one DAO interface corresponding with entity class to access data
    abstract val subscriberDAO: SubscriberDAO

    // make one instance to use in whole application Singleton Instance to access invoke data

    companion object {
        private var INSTANCE: SubscriberDataBase? = null
        fun getInstance(context: Context): SubscriberDataBase {
            synchronized(this) {
                var instance: SubscriberDataBase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDataBase::class.java, "subscriber_data_database"
                    )
                        .build()
                }
                return instance
            }
        }
    }

}