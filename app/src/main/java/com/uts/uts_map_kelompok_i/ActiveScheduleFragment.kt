package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uts.uts_map_kelompok_i.adapter.ScheduleAdapter
import com.uts.uts_map_kelompok_i.data.Schedule
import java.util.Calendar

class ActiveScheduleFragment : Fragment() {

    private val scheduleViewModel: ScheduleViewModel by activityViewModels()
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var dayContainers: Map<String, LinearLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_active_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvToday: RecyclerView = view.findViewById(R.id.rv_todays_schedule)
        val tvEmpty: TextView = view.findViewById(R.id.tv_empty_schedule)
        val btnAdd: View = view.findViewById(R.id.btnAddSchedule)

        dayContainers = mapOf(
            "Senin" to view.findViewById(R.id.day_container_senin),
            "Selasa" to view.findViewById(R.id.day_container_selasa),
            "Rabu" to view.findViewById(R.id.day_container_rabu),
            "Kamis" to view.findViewById(R.id.day_container_kamis),
            "Jumat" to view.findViewById(R.id.day_container_jumat),
            "Sabtu" to view.findViewById(R.id.day_container_sabtu),
            "Minggu" to view.findViewById(R.id.day_container_minggu)
        )

        scheduleAdapter = ScheduleAdapter(
            schedules = emptyList(),
            showCompleteButton = true
        ) { schedule ->
            scheduleViewModel.markAsCompleted(schedule)
        }
        rvToday.layoutManager = LinearLayoutManager(context)
        rvToday.adapter = scheduleAdapter

        scheduleViewModel.activeSchedules.observe(viewLifecycleOwner) { schedules ->
            updateWeeklyCalendar(schedules)
            updateTodaysSchedule(schedules, tvEmpty)
        }

        btnAdd.setOnClickListener {
            AddScheduleBottomSheetFragment().show(parentFragmentManager, "AddScheduleSheet")
        }
    }

    private fun updateWeeklyCalendar(schedules: List<Schedule>) {
        dayContainers.values.forEach { it.removeAllViews() }

        schedules.forEach { schedule ->
            val dayContainer = dayContainers[schedule.dayOfWeek]
            if (dayContainer != null) {
                val entryView = LayoutInflater.from(context).inflate(R.layout.item_calendar_entry, dayContainer, false)
                val tvActivity = entryView.findViewById<TextView>(R.id.tv_calendar_activity)
                tvActivity.text = schedule.activityName
                dayContainer.addView(entryView)
            }
        }
    }

    private fun updateTodaysSchedule(schedules: List<Schedule>, tvEmpty: TextView) {
        val todayString = getTodayAsString()
        val todaySchedules = schedules.filter { it.dayOfWeek == todayString }

        scheduleAdapter.updateSchedules(todaySchedules)
        tvEmpty.visibility = if (todaySchedules.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun getTodayAsString(): String {
        return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Senin"
            Calendar.TUESDAY -> "Selasa"
            Calendar.WEDNESDAY -> "Rabu"
            Calendar.THURSDAY -> "Kamis"
            Calendar.FRIDAY -> "Jumat"
            Calendar.SATURDAY -> "Sabtu"
            Calendar.SUNDAY -> "Minggu"
            else -> ""
        }
    }
}