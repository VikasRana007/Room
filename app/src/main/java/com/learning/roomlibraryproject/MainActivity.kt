package com.learning.roomlibraryproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.roomlibraryproject.databinding.ActivityMainBinding
import com.learning.roomlibraryproject.db.Subscriber
import com.learning.roomlibraryproject.db.SubscriberDAO
import com.learning.roomlibraryproject.db.SubscriberDataBase
import com.learning.roomlibraryproject.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var recyclerViewAdapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val dao: SubscriberDAO = SubscriberDataBase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        activityMainBinding.myViewModel = subscriberViewModel
        activityMainBinding.lifecycleOwner = this
        initRecyclerView()

        subscriberViewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun initRecyclerView() {
        activityMainBinding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter =
            MyRecyclerViewAdapter { selectedItem: Subscriber -> listItemClicked(selectedItem) }
        activityMainBinding.subscriberRecyclerView.adapter = recyclerViewAdapter
        displaySubscribersList()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun displaySubscribersList() {
        subscriberViewModel.subscriber.observe(this) {
            Log.i("MyTag", it.toString())
            recyclerViewAdapter.setList(it)
            recyclerViewAdapter.notifyDataSetChanged()

        }

    }


    //  To implement click event we can use Kotlin higher order function and lambda expression we can use in recycler adapter function
    private fun listItemClicked(subscriber: Subscriber) {
//        Toast.makeText(this,"Selected Name is ${subscriber.name}",Toast.LENGTH_SHORT).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)

    }


}