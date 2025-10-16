package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton // <-- Import ini
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uts.uts_map_kelompok_i.data.Schedule

class AddScheduleBottomSheetFragment : BottomSheetDialogFragment() {

    private val scheduleViewModel: ScheduleViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etActivity: EditText = view.findViewById(R.id.et_activity_name)
        val etTime: EditText = view.findViewById(R.id.et_time)
        val spinnerDay: Spinner = view.findViewById(R.id.spinner_day)
        val btnSave: Button = view.findViewById(R.id.btn_save_schedule)
        val btnClose: ImageButton = view.findViewById(R.id.btn_close_add_schedule) // <-- Ambil referensi tombol X

        val days = arrayOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, days)
        spinnerDay.adapter = adapter

        btnSave.setOnClickListener {
            val activityName = etActivity.text.toString().trim()
            val time = etTime.text.toString().trim()
            val day = spinnerDay.selectedItem.toString()

            if (activityName.isEmpty() || time.isEmpty()) {
                Toast.makeText(context, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newSchedule = Schedule(activityName = activityName, time = time, dayOfWeek = day)
            scheduleViewModel.addSchedule(newSchedule)
            dismiss()
        }

        btnClose.setOnClickListener {
            dismiss()
        }
    }
}