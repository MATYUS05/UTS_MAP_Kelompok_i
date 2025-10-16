package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditPostBottomSheetFragment : BottomSheetDialogFragment() {

    private val partnerViewModel: PartnerViewModel by activityViewModels()
    private var partnerIdToEdit: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            partnerIdToEdit = it.getString("PARTNER_ID")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSport: EditText = view.findViewById(R.id.et_edit_sport)
        val etSchedule: EditText = view.findViewById(R.id.et_edit_schedule)
        val etLocation: EditText = view.findViewById(R.id.et_edit_location)
        val btnSave: Button = view.findViewById(R.id.btn_save_changes)
        val btnClose: ImageButton = view.findViewById(R.id.btn_close_edit_sheet)

        val existingPartner = partnerViewModel.getPartnerById(partnerIdToEdit)
        if (existingPartner == null) {
            Toast.makeText(context, "Error: Data tidak ditemukan", Toast.LENGTH_SHORT).show()
            dismiss()
            return
        }

        etSport.setText(existingPartner.favoriteSport)
        etSchedule.setText(existingPartner.availableSchedule)
        etLocation.setText(existingPartner.location)

        btnSave.setOnClickListener {
            val updatedSport = etSport.text.toString().trim()
            val updatedSchedule = etSchedule.text.toString().trim()
            val updatedLocation = etLocation.text.toString().trim()

            if (updatedSport.isEmpty() || updatedSchedule.isEmpty() || updatedLocation.isEmpty()) {
                Toast.makeText(context, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showConfirmationDialog(
                "Simpan Perubahan",
                "Apakah Anda yakin ingin menyimpan perubahan ini?"
            ) {
                val updatedPartner = existingPartner.copy(
                    favoriteSport = updatedSport,
                    availableSchedule = updatedSchedule,
                    location = updatedLocation
                )
                partnerViewModel.updatePartner(updatedPartner)
                Toast.makeText(context, "Ajakan berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun showConfirmationDialog(title: String, message: String, onConfirm: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ya, Simpan") { dialog, _ ->
                onConfirm()
                dialog.dismiss()
            }
            .setNegativeButton("Batal", null)
            .create()
            .show()
    }

    companion object {
        fun newInstance(partnerId: String) =
            EditPostBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString("PARTNER_ID", partnerId)
                }
            }
    }
}