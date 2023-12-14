package com.eltex.androidschool.actitvity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.ActivityEditEventBinding
import com.eltex.androidschool.utils.toast

class EditEventActivity : AppCompatActivity() {

    companion object{
        private const val NEW_CONTENT_INTENT = "newContent"
        private const val OLD_CONTENT_INTENT = "oldContent"
        private const val ID_INTENT = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.content.setText(intent.getStringExtra(OLD_CONTENT_INTENT))

        binding.toolbar.menu.findItem(R.id.save).setOnMenuItemClickListener {
            val content = binding.content.text?.toString().orEmpty()
            if (content.isBlank()) {
                toast(R.string.event_empty_error, true)
            } else {
                setResult(RESULT_OK, Intent()
                    .putExtra(NEW_CONTENT_INTENT, content)
                    .putExtra(ID_INTENT, intent.getLongExtra(ID_INTENT, 0)))
                finish()
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}