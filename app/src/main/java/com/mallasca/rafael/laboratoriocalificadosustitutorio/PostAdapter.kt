package com.mallasca.rafael.laboratoriocalificadosustitutorio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mallasca.rafael.laboratoriocalificadosustitutorio.databinding.ItemPostBinding

class PostAdapter(
    private val onItemClick: (Post) -> Unit,
    private val onItemLongClick: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.titleTextView.text = post.title
            binding.bodyTextView.text = post.body

            binding.root.setOnClickListener { onItemClick(post) }
            binding.root.setOnLongClickListener {
                onItemLongClick(post)
                true
            }
        }
    }
}