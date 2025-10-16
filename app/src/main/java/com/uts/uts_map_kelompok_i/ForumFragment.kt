package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.uts.uts_map_kelompok_i.adapter.ForumAdapter
import com.uts.uts_map_kelompok_i.data.Forum

class ForumFragment : Fragment() {

    private lateinit var recyclerViewPosts: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var inputPost: EditText
    private lateinit var postAdapter: ForumAdapter
    private val allPosts = mutableListOf<Forum>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupResultListener()

        recyclerViewPosts = view.findViewById(R.id.recyclerViewPosts)
        tabLayout = view.findViewById(R.id.tabLayout)
        inputPost = view.findViewById(R.id.inputPost)

        if (allPosts.isEmpty()) {
            setupInitialPosts()
        }

        setupRecyclerView()
        setupTabs()
        setupButtonActions(view)
    }

    private fun setupResultListener() {
        setFragmentResultListener("editPostRequest") { _, bundle ->
            val postId = bundle.getLong("postId")
            val newContent = bundle.getString("newContent")

            if (newContent != null) {
                val index = allPosts.indexOfFirst { it.id == postId }
                if (index != -1) {
                    allPosts[index] = allPosts[index].copy(content = newContent)
                    filterPosts(tabLayout.selectedTabPosition) // Refresh list
                    Toast.makeText(context, "Postingan diperbarui!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        postAdapter = ForumAdapter(mutableListOf(),
            onEditClicked = { post ->
                val editSheet = EditForumPostBottomSheetFragment.newInstance(post.id, post.content)
                editSheet.show(parentFragmentManager, "EditForumPostBottomSheet")
            },
            onDeleteClicked = { post ->
                allPosts.remove(post)
                filterPosts(tabLayout.selectedTabPosition)
                Toast.makeText(context, "Postingan dihapus", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerViewPosts.layoutManager = LinearLayoutManager(context)
        recyclerViewPosts.adapter = postAdapter
        filterPosts(0)
    }

    private fun setupTabs() {
        tabLayout.removeAllTabs()
        tabLayout.addTab(tabLayout.newTab().setText("Semua Postingan"))
        tabLayout.addTab(tabLayout.newTab().setText("Postingan Anda"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                filterPosts(tab?.position ?: 0)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun filterPosts(tabPosition: Int) {
        val filteredList = when (tabPosition) {
            1 -> allPosts.filter { it.isUserPost }
            else -> allPosts
        }
        postAdapter.updatePosts(filteredList.toMutableList())
    }

    private fun setupButtonActions(view: View) {
        val btnPost: Button = view.findViewById(R.id.btnPost)
        val btnCancel: Button = view.findViewById(R.id.btnCancel)
        val btnLoadMore: Button = view.findViewById(R.id.btnLoadMore)

        btnPost.setOnClickListener {
            val content = inputPost.text.toString().trim()
            if (content.isNotEmpty()) {
                val newPost = Forum(
                    id = System.currentTimeMillis(),
                    username = "Anda",
                    content = content,
                    timestamp = "Baru saja",
                    likes = 0,
                    comments = 0,
                    isUserPost = true
                )
                allPosts.add(0, newPost)
                filterPosts(tabLayout.selectedTabPosition)
                recyclerViewPosts.scrollToPosition(0)
                inputPost.text.clear()
                Toast.makeText(context, "Postingan dibagikan!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Postingan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener { inputPost.text.clear() }
        btnLoadMore.setOnClickListener {
            Toast.makeText(context, "Memuat postingan lainnya...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupInitialPosts() {
        allPosts.addAll(listOf(
            Forum(1, "Sarah Wijaya", "2 jam lalu", "Hari ini saya memutuskan untuk memulai lagi. Tidak pernah terlambat untuk berubah menjadi versi terbaik diri kita. 💪", 234, 45, isUserPost = false),
            Forum(2, "Budi Hartono", "5 jam lalu", "Lari pagi tadi benar-benar menyegarkan! Siapa yang mau gabung besok?", 150, 22, isUserPost = false),
            Forum(3, "Anda", "1 hari lalu", "Ini adalah postingan pertama saya...", 10, 2, isUserPost = true)
        ))
    }
}