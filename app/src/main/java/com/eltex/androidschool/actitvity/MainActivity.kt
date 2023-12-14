package com.eltex.androidschool.actitvity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.androidschool.R
import com.eltex.androidschool.adapter.EventsAdapter
import com.eltex.androidschool.databinding.ActivityMainBinding
import com.eltex.androidschool.db.AppDb
import com.eltex.androidschool.itemdecoration.OffsetDecoration
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.repository.SQLiteEventRepository
import com.eltex.androidschool.viewmodel.EventViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    companion object{
        private const val NEW_CONTENT_INTENT = "newContent"
        private const val OLD_CONTENT_INTENT = "oldContent"
        private const val SHARED_CONTENT_INTENT = "sharedContent"
        private const val ID_INTENT = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                initializer { EventViewModel(SQLiteEventRepository(AppDb.getInstance(applicationContext).eventDao)) }
            }
        }

        val newEventContract =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val content = it.data?.getStringExtra(Intent.EXTRA_TEXT)
                if (content != null) {
                    viewModel.addEvent(content)
                }
            }

        val editEventContract =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val content = it.data?.getStringExtra(NEW_CONTENT_INTENT)
                val id = it.data?.getLongExtra(ID_INTENT, 0L)
                if (content != null && id != null) {
                    viewModel.editById(id, content)
                }
            }

        val adapter = EventsAdapter(
            object: EventsAdapter.EventListener {
                override fun onShareClicked(event: Event) {
                    val intent = Intent()
                        .setAction(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TEXT, event.content)
                        .setType("text/plain")
                    val chooser = Intent.createChooser(intent, null)
                    startActivity(chooser)
                }

                override fun onLikeClicked(event: Event) {
                    viewModel.likeById(event.id)
                }

                override fun onDeleteClicked(event: Event) {
                    viewModel.deleteById(event.id)
                }

                override fun onParticipateClicked(event: Event) {
                    viewModel.participateById(event.id)
                }

                override fun onEditClicked(event: Event) {
                    editEventContract.launch(Intent(applicationContext, EditEventActivity::class.java)
                        .putExtra(ID_INTENT, event.id)
                        .putExtra(OLD_CONTENT_INTENT, event.content))
                }
            }
        )

        binding.list.adapter = adapter

        binding.list.addItemDecoration(
            OffsetDecoration(resources.getDimensionPixelSize(R.dimen.small_spacing))
        )

        binding.addEvent.setOnClickListener {
            newEventContract.launch(Intent(this, NewEventActivity::class.java))
        }

        if (intent.action == Intent.ACTION_SEND) {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            intent.removeExtra(Intent.EXTRA_TEXT)
            newEventContract.launch(Intent(this, NewEventActivity::class.java)
                .putExtra(SHARED_CONTENT_INTENT, text))
        }

        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach {
                adapter.submitList(it.events)
            }
            .launchIn(lifecycleScope)
    }
}

