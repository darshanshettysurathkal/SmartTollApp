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
        val textViewMoneyDeducted: TextView = itemView.findViewById(R.id.MoneyDeducted)
        val textViewTime: TextView = itemView.findViewById(R.id.HistoryTime)
        val textViewDate: TextView = itemView.findViewById(R.id.HistoryDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.textViewMoneyDeducted.text = item.moneyDeducted
        holder.textViewTime.text = item.time
        holder.textViewDate.text = item.date
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newData: List<MyData>) {
        dataList = newData
        notifyDataSetChanged()
    }
}
