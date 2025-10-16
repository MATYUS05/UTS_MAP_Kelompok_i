package com.uts.uts_map_kelompok_i

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uts.uts_map_kelompok_i.data.Partner

class PartnerViewModel : ViewModel() {

    private val _partners = MutableLiveData<List<Partner>>()
    val partners: LiveData<List<Partner>> = _partners

    private val partnerList = mutableListOf<Partner>()

    init {
        createDummyData()
        _partners.value = partnerList
    }

    fun addPartner(newPartner: Partner) {
        partnerList.add(0, newPartner.copy(isUserCreated = true))
        _partners.value = partnerList
    }

    fun updatePartner(updatedPartner: Partner) {
        val index = partnerList.indexOfFirst { it.id == updatedPartner.id }
        if (index != -1) {
            partnerList[index] = updatedPartner
            _partners.value = partnerList
        }
    }

    fun deletePartner(partnerId: String) {
        partnerList.removeAll { it.id == partnerId }
        _partners.value = partnerList
    }

    fun getPartnerById(partnerId: String?): Partner? {
        if (partnerId == null) return null
        return partnerList.firstOrNull { it.id == partnerId }
    }

    private fun createDummyData() {
        partnerList.add(Partner(name = "Andi Saputra", favoriteSport = "Badminton", availableSchedule = "Sabtu Pagi", location = "GBK Senayan", phoneNumber = "6281234567890", photo = R.drawable.ic_profile))
        partnerList.add(Partner(name = "Bunga Citra", favoriteSport = "Yoga", availableSchedule = "Minggu Sore", location = "Taman Suropati", phoneNumber = "6281234567891", photo = R.drawable.ic_profile))
        partnerList.add(Partner(name = "Charlie Wijaya", favoriteSport = "Futsal", availableSchedule = "Rabu Malam", location = "Planet Futsal", phoneNumber = "6281234567892", photo = R.drawable.ic_profile))
    }
}