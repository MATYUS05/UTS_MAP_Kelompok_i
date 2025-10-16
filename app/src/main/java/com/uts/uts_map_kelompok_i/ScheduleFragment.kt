package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class ScheduleFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Initialize the "Tambah Jadwal" button
        val btnAddSchedule = view.findViewById<MaterialButton>(R.id.btnAddSchedule)
        
        btnAddSchedule.setOnClickListener {
            // Show toast message when button is clicked
            Toast.makeText(requireContext(), "Fitur Tambah Jadwal akan segera tersedia!", Toast.LENGTH_SHORT).show()
        }
    }
}