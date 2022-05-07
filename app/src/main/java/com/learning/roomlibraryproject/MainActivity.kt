package com.learning.roomlibraryproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.learning.roomlibraryproject.databinding.ActivityMainBinding
import com.learning.roomlibraryproject.db.SubscriberDAO
import com.learning.roomlibraryproject.db.SubscriberDataBase
import com.learning.roomlibraryproject.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val dao: SubscriberDAO = SubscriberDataBase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        activityMainBinding.myViewModel = subscriberViewModel
        activityMainBinding.lifecycleOwner = this
        displaySubscribersList()

    }


    private fun displaySubscribersList() {
        subscriberViewModel.subscriber.observe(this, Observer { Log.i("MyTag", it.toString()) })
    }
}