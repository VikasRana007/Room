package com.learning.roomlibraryproject.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {
    // write function to insert the subscriber object into the data base
    /* there for we need to execute these functions in the
       background thread, for that we can use async task, rxJava, executors,
       but we have very convenient and fast way by coroutine, Room facilitates us
       to write these functions as suspending function with suspend modifier


       different way of defining function according to requirement
      @Insert
      fun insertSubscriber2(subscriber: Subscriber):Long

      @Insert
      fun insertSubscriber(subscriber1: Subscriber,subscriber2: Subscriber,subscriber3: Subscriber):List<String>

      @Insert
      fun insertSubscribers(subscribers: List<Subscriber>):List<Long>

      @Insert
      fun insertSubscriber2(subscriber: Subscriber,subscribers: List<Subscriber>):List<Long>

      these all crud functions have different variation of defining function according to requirement.
     */

//    (onConflict = OnConflictStrategy.REPLACE)   we can also use


    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber):Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll()

    /**
     * Asynchronous Query because of these query which has a LiveData as a return value, room always
     * run them on the background thread by itself so we dont have to write special code to run them in the background
     * also we have to use Coroutine , AsyncTask or executors to execute these functions
     */


    // write fun below to get the list of all subscribers entities from the database as LiveData
    // we have not use suspend modifier cuase no need to run it in the background thread using coroutines

    @Query("SELECT* FROM subscriber_data_table ")
    fun getAllSubscriber():LiveData<List<Subscriber>>






}