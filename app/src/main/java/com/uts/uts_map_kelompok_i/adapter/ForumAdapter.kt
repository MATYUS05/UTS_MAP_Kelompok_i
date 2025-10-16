package com.uts.uts_map_kelompok_i.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uts.uts_map_kelompok_i.R
import com.uts.uts_map_kelompok_i.data.Forum

class ForumAdapter(
    private var posts: MutableList<Forum>,
    private val onEditClicked: (Forum) -> Unit,
    private val onDeleteClicked: (Forum) -> Unit
) : RecyclerView.Adapter<ForumAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val tvStats: TextView = itemView.findViewById(R.id.tvStats)
        val tvBadge: TextView = itemView.findViewById(R.id.tvBadge)
        val ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forum, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.tvUsername.text = post.username
        holder.tvTime.text = post.timestamp // Pastikan menggunakan 'timestamp'
        holder.tvContent.text = post.content
        holder.tvStats.text = "${post.likes} suka    ${post.comments} komentar"

        if (post.isUserPost) {
            holder.ivEdit.visibility = View.VISIBLE
            holder.ivDelete.visibility = View.VISIBLE
            holder.tvBadge.visibility = View.GONE
            holder.ivEdit.setOnClickListener { onEditClicked(post) }
            holder.ivDelete.setOnClickListener { onDeleteClicked(post) }
        } else {
            holder.ivEdit.visibility = View.GONE
            holder.ivDelete.visibility = View.GONE
            // Anda bisa menambahkan logika untuk badge di sini jika perlu
            holder.tvBadge.visibility = if (position == 0) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount() = posts.size

    // Fungsi untuk memperbarui seluruh daftar (untuk filtering)
    fun updatePosts(newPosts: List<Forum>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

    // Fungsi untuk menambah satu post baru di atas
    fun addPost(post: Forum) {
        posts.add(0, post)
        notifyItemInserted(0)
    }
}