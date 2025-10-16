package com.uts.uts_map_kelompok_i

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uts.uts_map_kelompok_i.data.Schedule

class ScheduleViewModel : ViewModel() {

    private val _activeSchedules = MutableLiveData<List<Schedule>>(emptyList())
    val activeSchedules: LiveData<List<Schedule>> = _activeSchedules

    private val _completedSchedules = MutableLiveData<List<Schedule>>(emptyList())
    val completedSchedules: LiveData<List<Schedule>> = _completedSchedules

    fun addSchedule(newSchedule: Schedule) {
        val currentList = _activeSchedules.value?.toMutableList() ?: mutableListOf()
        currentList.add(newSchedule)
        _activeSchedules.value = currentList
    }

    fun markAsCompleted(schedule: Schedule) {
        val currentActive = _activeSchedules.value?.toMutableList() ?: mutableListOf()
        currentActive.remove(schedule)
        _activeSchedules.value = currentActive

        val currentCompleted = _completedSchedules.value?.toMutableList() ?: mutableListOf()
        currentCompleted.add(schedule)
        _completedSchedules.value = currentCompleted
    }
}