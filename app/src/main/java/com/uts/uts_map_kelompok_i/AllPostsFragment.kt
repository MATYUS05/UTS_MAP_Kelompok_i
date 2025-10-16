package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uts.uts_map_kelompok_i.adapter.PartnerAdapter
import com.uts.uts_map_kelompok_i.data.Partner
import java.util.*

class AllPostsFragment : Fragment() {

    private val partnerViewModel: PartnerViewModel by activityViewModels()
    private lateinit var partnerAdapter: PartnerAdapter
    private var fullListForFiltering = listOf<Partner>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_all_partners)
        val etSearch: EditText = view.findViewById(R.id.et_search_partner)
        val fabCreatePost: FloatingActionButton = view.findViewById(R.id.fab_create_post)

        recyclerView.layoutManager = GridLayoutManager(context, 2)

        partnerAdapter = PartnerAdapter(
            emptyList(),
            onInviteClick = { partner ->
                Toast.makeText(context, "Mengajak ${partner.name}", Toast.LENGTH_SHORT).show()
            },
            onEditClick = { partner ->
                // LANGSUNG TAMPILKAN BOTTOM SHEET TANPA KONFIRMASI
                val editSheet = EditPostBottomSheetFragment.newInstance(partner.id)
                editSheet.show(parentFragmentManager, "EditPostBottomSheet")
            },
            onDeleteClick = { partner ->
                // Konfirmasi untuk delete tetap di sini
                showConfirmationDialog(
                    "Hapus Ajakan",
                    "Apakah Anda yakin ingin menghapus ajakan ini?"
                ) {
                    partnerViewModel.deletePartner(partner.id)
                    Toast.makeText(context, "Ajakan dihapus", Toast.LENGTH_SHORT).show()
                }
            }
        )
        recyclerView.adapter = partnerAdapter

        partnerViewModel.partners.observe(viewLifecycleOwner) { allPartners ->
            fullListForFiltering = allPartners
            filter(etSearch.text.toString())
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { filter(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        fabCreatePost.setOnClickListener {
            val bottomSheet = CreatePostBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, "CreatePostBottomSheet")
        }
    }

    private fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullListForFiltering
        } else {
            fullListForFiltering.filter {
                it.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
            }
        }
        partnerAdapter.filterList(filteredList)
    }

    private fun showConfirmationDialog(title: String, message: String, onConfirm: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ya") { dialog, _ ->
                onConfirm()
                dialog.dismiss()
            }
            .setNegativeButton("Batal", null)
            .create()
            .show()
    }
}