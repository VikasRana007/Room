package com.learning.roomlibraryproject.db

/**
 * The purpose of repository class is to provide a clean API for view models
 * to easily get and send data. it is like a mediator between different data sources such as local db,
 * web services and caches without repository mediator we can also make communication between
 * Model(database & DAO interface) and View Model, but
 * according to learn best practice we must have to follow MVVM  architecture pattern
 */
class SubscriberRepository(private val subscriberDAO: SubscriberDAO) {
    /**
     * Here we have added instance of SubscriberDAO because we are going to call to
     * function of the DAO from the repository
     */

        val subscribers = subscriberDAO.getAllSubscriber()
    // No need to call from back ground thread Room library do it by itself
     // DAO functions should be called from back ground thread

      //room library automatically process these functions with live data as a return type in a background thread.
    // we will use coroutine in the view model class to execute them . But to support that , we need to define these
    // functions as suspending functions, Coroutine can resume , pause and cancel of suspend function  any time


    suspend fun insert(subscriber : Subscriber):Long{
      return  subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int{
       return subscriberDAO.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber):Int{
       return subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int{
       return subscriberDAO.deleteAll()
    }
}