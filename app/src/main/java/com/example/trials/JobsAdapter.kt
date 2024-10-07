package com.example.trials

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JobsAdapter(private val comList: List<company>) :
    RecyclerView.Adapter<JobsAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    // Interface for item and More Info button clicks
    interface onItemClickListener {
        fun onItemClick(position: Int)   // For clicking the item
        fun onMoreInfoClick(position: Int) // For clicking "More Info" button
    }

    // Set the click listener
    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    // Inflate the layout and return a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_postjobs_list, parent, false)
        return ViewHolder(itemView, mListener)
    }

    // Bind data to the views in each item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCom = comList[position]
        holder.companyName.text = currentCom.CcompanyName
        holder.jobTitle.text = currentCom.Ctitle
        holder.jobType.text = currentCom.Ctype
    }

    // Return the total number of items
    override fun getItemCount(): Int {
        return comList.size
    }

    // ViewHolder class to hold the views for each item
    class ViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val companyName: TextView = itemView.findViewById(R.id.companyname)
        val jobTitle: TextView = itemView.findViewById(R.id.job1)
        val jobType: TextView = itemView.findViewById(R.id.type)
        val moreInfoButton: Button = itemView.findViewById(R.id.more) // Reference to the "More Info" button

        init {
            // Item click
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }

            // More Info button click
            moreInfoButton.setOnClickListener {
                clickListener.onMoreInfoClick(adapterPosition)
            }
        }
    }
}
