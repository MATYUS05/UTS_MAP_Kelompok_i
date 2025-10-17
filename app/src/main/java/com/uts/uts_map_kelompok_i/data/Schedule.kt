package com.uts.uts_map_kelompok_i.data

data class Schedule(
    val id: Long = System.currentTimeMillis(),
    val activityName: String,
    val time: String,
    val dayOfWeek: String, // "Senin", "Selasa", ...
    val date: Long = System.currentTimeMillis() // timestamp untuk filter bulan & tahun
)
