package com.learning.roomlibraryproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.roomlibraryproject.db.Subscriber
import com.learning.roomlibraryproject.db.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscriber = repository.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()

    val clearAllOrDeleteBottonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteBottonText.value = "Clear All"
    }


    fun saveOrUpdate() {
        val name: String = inputName.value!!
        val email: String = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputName.value = ""
        inputEmail.value = ""
    }


    fun clearAllOrDelete() {
        clearAll()
    }

    /** from we can call to the insert function of repository passing the subscriber instance
     * and in the view model we should alwyas do calls from back ground thread but here we have
     * viewModelScope luckily so use it , so no need to use dispatcher coroutine builder its by
     * default coroutine scope,.
     */

    fun insert(subscriber: Subscriber): Job =
        viewModelScope.launch { repository.insert(subscriber) }

    fun update(subscriber: Subscriber): Job =
        viewModelScope.launch { repository.update(subscriber) }

    fun delete(subscriber: Subscriber): Job =
        viewModelScope.launch { repository.delete(subscriber) }

    fun clearAll(): Job = viewModelScope.launch {repository.deleteAll()}
}


