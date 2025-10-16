package com.uts.uts_map_kelompok_i.data

data class Forum(
    val id: Long,
    val username: String,
    val timestamp: String, // Menggunakan 'timestamp'
    val content: String,
    val likes: Int,
    val comments: Int,
    val isUserPost: Boolean
)
