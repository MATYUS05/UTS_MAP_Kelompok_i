package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditForumPostBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_post_forum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etContent: EditText = view.findViewById(R.id.et_edit_content)
        val btnSave: Button = view.findViewById(R.id.btn_save_changes)
        val btnClose: ImageButton = view.findViewById(R.id.btn_close_edit_sheet)

        val postId = requireArguments().getLong("postId")
        val currentContent = requireArguments().getString("currentContent")

        etContent.setText(currentContent)

        btnClose.setOnClickListener {
            dismiss()
        }

        btnSave.setOnClickListener {
            val newContent = etContent.text.toString().trim()
            if (newContent.isNotEmpty()) {
                setFragmentResult("editPostRequest", bundleOf(
                    "postId" to postId,
                    "newContent" to newContent
                ))
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(postId: Long, currentContent: String) =
            EditForumPostBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putLong("postId", postId)
                    putString("currentContent", currentContent)
                }
            }
    }
}