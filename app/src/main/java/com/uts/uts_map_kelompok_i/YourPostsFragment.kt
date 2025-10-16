package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uts.uts_map_kelompok_i.adapter.PartnerAdapter

class YourPostsFragment : Fragment() {

    private val partnerViewModel: PartnerViewModel by activityViewModels()
    private lateinit var partnerAdapter: PartnerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_your_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_your_posts)
        val tvEmpty: TextView = view.findViewById(R.id.tv_empty_your_posts)

        recyclerView.layoutManager = GridLayoutManager(context, 2)

        partnerAdapter = PartnerAdapter(
            emptyList(),
            onInviteClick = {},
            onEditClick = { partner ->
                val editSheet = EditPostBottomSheetFragment.newInstance(partner.id)
                editSheet.show(parentFragmentManager, "EditPostBottomSheet")
            },
            onDeleteClick = { partner ->
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
            val yourPosts = allPartners.filter { it.isUserCreated }
            partnerAdapter.filterList(yourPosts)
            tvEmpty.visibility = if (yourPosts.isEmpty()) View.VISIBLE else View.GONE
        }
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