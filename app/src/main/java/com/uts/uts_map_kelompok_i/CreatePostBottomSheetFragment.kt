package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uts.uts_map_kelompok_i.data.Partner

class CreatePostBottomSheetFragment : BottomSheetDialogFragment() {

    private val partnerViewModel: PartnerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSport: EditText = view.findViewById(R.id.et_sport)
        val etSchedule: EditText = view.findViewById(R.id.et_schedule)
        val etLocation: EditText = view.findViewById(R.id.et_location)
        val btnCreate: Button = view.findViewById(R.id.btn_create_invitation)
        val btnClose: ImageButton = view.findViewById(R.id.btn_close_bottom_sheet)

        btnCreate.setOnClickListener {
            val sport = etSport.text.toString().trim()
            val schedule = etSchedule.text.toString().trim()
            val location = etLocation.text.toString().trim()

            if (sport.isEmpty() || schedule.isEmpty() || location.isEmpty()) {
                Toast.makeText(context, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newPartner = Partner(
                name = "Ajakan Anda",
                favoriteSport = sport,
                availableSchedule = schedule,
                location = location,
                phoneNumber = "6281200000000",
                photo = R.drawable.ic_profile
            )

            partnerViewModel.addPartner(newPartner)
            Toast.makeText(context, "Ajakan berhasil dibuat!", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        btnClose.setOnClickListener {
            dismiss()
        }
    }
}