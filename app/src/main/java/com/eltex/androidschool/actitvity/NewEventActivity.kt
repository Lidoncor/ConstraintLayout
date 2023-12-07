package com.eltex.androidschool.actitvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.ActivityNewEventBinding
import com.eltex.androidschool.utils.toast

class NewEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedContent = intent.getStringExtra("sharedContent")
        if (sharedContent != null)
            binding.content.setText(sharedContent)

        binding.toolbar.menu.findItem(R.id.save).setOnMenuItemClickListener {
            val content = binding.content.text?.toString().orEmpty()
            if (content.isBlank()) {
                toast(R.string.event_empty_error, true)
            } else {
                setResult(RESULT_OK, Intent().putExtra(Intent.EXTRA_TEXT, content))
                finish()
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}