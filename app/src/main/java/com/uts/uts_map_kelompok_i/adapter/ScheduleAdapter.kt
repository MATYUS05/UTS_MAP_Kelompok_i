package com.uts.uts_map_kelompok_i.adapter

import com.uts.uts_map_kelompok_i.R
import com.uts.uts_map_kelompok_i.data.Schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter(
    private var schedules: List<Schedule>,
    private val showCompleteButton: Boolean,
    private val onCompleteClick: (Schedule) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activityName: TextView = itemView.findViewById(R.id.tv_activity_name)
        val activityTime: TextView = itemView.findViewById(R.id.tv_activity_time)
        val completeButton: Button = itemView.findViewById(R.id.btn_complete_schedule)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = schedules[position]
        holder.activityName.text = schedule.activityName
        holder.activityTime.text = schedule.time

        if (showCompleteButton) {
            holder.completeButton.visibility = View.VISIBLE
            holder.completeButton.setOnClickListener {
                onCompleteClick(schedule)
            }
        } else {
            holder.completeButton.visibility = View.GONE
        }
    }

    override fun getItemCount() = schedules.size

    fun updateSchedules(newSchedules: List<Schedule>) {
        schedules = newSchedules
        notifyDataSetChanged()
    }
}