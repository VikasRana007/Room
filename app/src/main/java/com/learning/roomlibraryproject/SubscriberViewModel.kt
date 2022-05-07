package com.learning.roomlibraryproject

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.roomlibraryproject.db.Subscriber
import com.learning.roomlibraryproject.db.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber
    val subscriber = repository.subscribers

    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteBottonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage    // work as public getter outside classes can just observe the live data, since we
    // are not willing to modify it that why we don't need define this as mutable live data

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteBottonText.value = "Clear All"
    }


    fun saveOrUpdate() {
        if (inputName.value == null) {
            statusMessage.value = Event("Please Enter Subscriber's Name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please Enter Subscriber's Email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please Enter Correct Email Address")
        } else {
            if (isUpdateOrDelete) {
                update(subscriberToUpdateOrDelete)
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
            } else {
                val name: String = inputName.value!!
                val email: String = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }
    }


    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    /** from we can call to the insert function of repository passing the subscriber instance
     * and in the view model we should alwyas do calls from back ground thread but here we have
     * viewModelScope luckily so use it , so no need to use dispatcher coroutine builder its by
     * default coroutine scope,.
     */

    fun insert(subscriber: Subscriber): Job =
        viewModelScope.launch {
            val newRowId = repository.insert(subscriber)
            if (newRowId > -1) {
                statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
            } else {
                statusMessage.value = Event("Error Occurred")
            }


        }

    fun update(subscriber: Subscriber): Job =
        viewModelScope.launch {
            val noOfRows = repository.update(subscriber)
            if (noOfRows > -1) {
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteBottonText.value = "Clear All"

                statusMessage.value = Event("$noOfRows Row Updated Successfully")
            } else {
                statusMessage.value = Event("Error Occurred While Updating")
            }
        }

    fun delete(subscriber: Subscriber): Job =
        viewModelScope.launch {
            val noOfDeletedRow = repository.delete(subscriber)
            if (noOfDeletedRow > 0) {
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteBottonText.value = "Clear All"
                statusMessage.value = Event("$noOfDeletedRow Row Deleted Successfully")
            } else {
                statusMessage.value = Event("Subscriber Not Deleted")
            }
        }

    fun clearAll(): Job = viewModelScope.launch {
        val noOfDeletedRow = repository.deleteAll()
        if (noOfDeletedRow > 0) {
            statusMessage.value = Event("$noOfDeletedRow Subscriber Deleted Successfully")
        } else {
            statusMessage.value = Event("$noOfDeletedRow Row Not Deleted Successfully")
        }
    }


    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteBottonText.value = "Delete"
    }

}


