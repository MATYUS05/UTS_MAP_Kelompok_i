package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uts.uts_map_kelompok_i.adapter.ScheduleAdapter

class HistoryScheduleFragment : Fragment() {

    private val scheduleViewModel: ScheduleViewModel by activityViewModels()
    private lateinit var historyAdapter: ScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_history_schedule)
        val tvEmpty: TextView = view.findViewById(R.id.tv_empty_history)

        recyclerView.layoutManager = LinearLayoutManager(context)

        historyAdapter = ScheduleAdapter(
            schedules = emptyList(),
            showCompleteButton = false
        ) {
        }
        recyclerView.adapter = historyAdapter

        scheduleViewModel.completedSchedules.observe(viewLifecycleOwner) { completed ->
            historyAdapter.updateSchedules(completed)
            tvEmpty.visibility = if (completed.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}