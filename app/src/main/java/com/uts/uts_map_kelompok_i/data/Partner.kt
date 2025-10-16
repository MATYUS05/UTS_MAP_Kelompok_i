package com.uts.uts_map_kelompok_i.data

import java.util.UUID

data class Partner(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val favoriteSport: String,
    val availableSchedule: String,
    val location: String,
    val phoneNumber: String,
    val photo: Int,
    val isUserCreated: Boolean = false
)