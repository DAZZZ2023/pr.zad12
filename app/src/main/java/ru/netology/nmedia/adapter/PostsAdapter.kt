package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            textView.text = post.author
            textView2.text = post.published
            connn.text = post.content
            var k = 12
            imageView5.setImageResource(
                if (post.likedByMe) R.drawable.unlike else R.drawable.heart
            )
            textView3.text = if (post.kolLikes) "111" else "112"

            imageView5.setOnClickListener() {
                post.likedByMe = !post.likedByMe
                post.kolLikes = !post.kolLikes
                imageView5.setImageResource(
                    if (post.likedByMe) R.drawable.unlike else R.drawable.heart
                )
                textView3.text = if (post.kolLikes) "111" else "112"
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            imageView2.setOnClickListener() {
                k = k + 100
                textView4.text = k.toString()
                if (k >= 1000) {
                    textView4.text =
                        (k / 1000).toString() + "." + ((k % 1000) / 100).toString() + "K"
                }
                if (k >= 10000) {
                    textView4.text = (k / 1000).toString() + "K"
                }
                if (k >= 1000000) {
                    textView4.text =
                        (k / 1000000).toString() + "." + ((k % 1000000) / 100000).toString() + "M"
                }
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
