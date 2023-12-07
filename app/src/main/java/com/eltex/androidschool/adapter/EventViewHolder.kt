package com.eltex.androidschool.adapter

import androidx.recyclerview.widget.RecyclerView
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.model.Event

class EventViewHolder(
    private val binding: CardEventBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(payload: EventPayload) {
        if (payload.like != null) {
            updateLike(payload.like)
        }

        if (payload.participate != null) {
            updateParticipate(payload.participate)
        }
    }
    fun bind(event: Event) {
        binding.content.text = event.content
        updateLike(event.likedByMe)
        updateParticipate(event.participatedByMe)
        binding.author.text = event.author
        binding.published.text = event.published
        binding.authorInitials.text = event.author.take(1)
    }
    private fun updateLike(likedByMe: Boolean){
        binding.like.setIconResource(
            if (likedByMe) {
                R.drawable.baseline_favorite_24
            } else {
                R.drawable.baseline_favorite_border_24
            }
        )
        binding.like.text = if (likedByMe) {
            1
        } else {
            0
        }
            .toString()
    }

    private fun updateParticipate(participatedByMe: Boolean){
        binding.participate.setIconResource(
            if (participatedByMe) {
                R.drawable.baseline_people_24
            } else {
                R.drawable.baseline_people_outline_24
            }
        )
        binding.participate.text = if (participatedByMe) {
            1
        } else {
            0
        }
            .toString()
    }
}