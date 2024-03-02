package com.example.gps_kotlin.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gps_kotlin.R

class MyDataAdapter(private var dataList: List<MyData>) :
    RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEntry: TextView = itemView.findViewById(R.id.entry_time)
        val textViewExit: TextView = itemView.findViewById(R.id.exit_time)
        val textViewCost: TextView = itemView.findViewById(R.id.cost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.textViewCost.text = item.cost
        holder.textViewEntry.text = item.entry_time.toString()
        holder.textViewExit.text = item.exit_time.toString()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newData: List<MyData>) {
        dataList = newData
        notifyDataSetChanged()
    }
}
