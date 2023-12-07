package com.eltex.androidschool.actitvity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.ActivityEditEventBinding
import com.eltex.androidschool.utils.toast

class EditEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.content.setText(intent.getStringExtra("oldContent"))

        binding.toolbar.menu.findItem(R.id.save).setOnMenuItemClickListener {
            val content = binding.content.text?.toString().orEmpty()
            if (content.isBlank()) {
                toast(R.string.event_empty_error, true)
            } else {
                setResult(RESULT_OK, Intent()
                    .putExtra("newContent", content)
                    .putExtra("id", intent.getLongExtra("id", 0)))
                finish()
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}