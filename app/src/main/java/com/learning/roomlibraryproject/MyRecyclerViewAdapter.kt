package com.learning.roomlibraryproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.learning.roomlibraryproject.databinding.ListItemBinding
import com.learning.roomlibraryproject.db.Subscriber

class MyRecyclerViewAdapter(private val clickListener:(Subscriber)->Unit) :
    RecyclerView.Adapter<MyViewHolder>() {

    private val subscribersList = ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        /**
         * this function is used to create the view details of subscriber, to create list item
         */
        val layoutInflater = LayoutInflater.from(parent.context)
        // now get data binding object
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        // now return the object(instance) of MyViewHolder & pass this data binding object as an argument with ViewHolder instance
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // use to display data in the list_item card view
        holder.bind(subscribersList[position],clickListener)
    }

    override fun getItemCount(): Int {
        // total number of item
        return subscribersList.size
    }

    fun setList(subscriber: List<Subscriber>){
        subscribersList.clear()
        subscribersList.addAll(subscriber)
    }


}


/**
 * we mainly use this class to bind values of the each list item component
 * for that we need a function
 */
class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber, clickListener:(Subscriber)->Unit) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.listItemLayout.setOnClickListener { clickListener(subscriber) }
    }

}