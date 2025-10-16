package com.uts.uts_map_kelompok_i.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uts.uts_map_kelompok_i.R
import com.uts.uts_map_kelompok_i.data.Partner

class PartnerAdapter(
    private var partnerList: List<Partner>,
    private val onInviteClick: (Partner) -> Unit,
    private val onEditClick: (Partner) -> Unit,
    private val onDeleteClick: (Partner) -> Unit
) : RecyclerView.Adapter<PartnerAdapter.PartnerViewHolder>() {

    class PartnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.iv_partner_photo)
        val name: TextView = itemView.findViewById(R.id.tv_partner_name)
        val sport: TextView = itemView.findViewById(R.id.tv_partner_sport)
        val schedule: TextView = itemView.findViewById(R.id.tv_partner_schedule)
        val location: TextView = itemView.findViewById(R.id.tv_partner_location)
        val inviteButton: Button = itemView.findViewById(R.id.btn_invite)
        val editDeleteContainer: LinearLayout = itemView.findViewById(R.id.edit_delete_container)
        val editButton: Button = itemView.findViewById(R.id.btn_edit)
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_partner, parent, false)
        return PartnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartnerViewHolder, position: Int) {
        val currentPartner = partnerList[position]

        holder.photo.setImageResource(currentPartner.photo)
        holder.name.text = currentPartner.name
        holder.sport.text = currentPartner.favoriteSport
        holder.schedule.text = currentPartner.availableSchedule
        holder.location.text = currentPartner.location

        if (currentPartner.isUserCreated) {
            holder.editDeleteContainer.visibility = View.VISIBLE
            holder.editButton.setOnClickListener { onEditClick(currentPartner) }
            holder.deleteButton.setOnClickListener { onDeleteClick(currentPartner) }
            holder.inviteButton.visibility = View.GONE
        } else {
            holder.editDeleteContainer.visibility = View.GONE
            holder.inviteButton.visibility = View.VISIBLE
            holder.inviteButton.setOnClickListener { onInviteClick(currentPartner) }
        }
    }

    override fun getItemCount() = partnerList.size

    fun filterList(filteredList: List<Partner>) {
        partnerList = filteredList
        notifyDataSetChanged()
    }
}