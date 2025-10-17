package com.uts.uts_map_kelompok_i

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedPreferences = activity?.getSharedPreferences("FitMatePrefs", MODE_PRIVATE)

        val btnLogout: Button = view.findViewById(R.id.btn_logout_profile)
        val imgAvatar: CircleImageView = view.findViewById(R.id.img_avatar)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
        val ivEditUsername: ImageView = view.findViewById(R.id.iv_edit_username)

        // Load username
        tvUsername.text = sharedPreferences?.getString("username", "")

        // Logout
        btnLogout.setOnClickListener {
            sharedPreferences?.edit()?.clear()?.apply()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }

        // Edit username
        ivEditUsername.setOnClickListener {
            val editText = EditText(requireContext())
            editText.setText(tvUsername.text)
            editText.selectAll()

            AlertDialog.Builder(requireContext())
                .setTitle("Edit Nama")
                .setView(editText)
                .setPositiveButton("Simpan") { dialog, _ ->
                    val newName = editText.text.toString().trim()
                    if (newName.isNotEmpty()) {
                        tvUsername.text = newName
                        sharedPreferences?.edit()?.putString("username", newName)?.apply()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        return view
    }
}
