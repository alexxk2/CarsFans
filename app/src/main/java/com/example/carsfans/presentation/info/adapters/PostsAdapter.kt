package com.example.carsfans.presentation.info.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carsfans.R
import com.example.carsfans.databinding.PostItemBinding
import com.example.carsfans.domain.models.PostInfo

class PostsAdapter(
    private val context: Context

    ): ListAdapter<PostInfo, PostsAdapter.PostViewHolder>(diffCallBack) {

    class PostViewHolder(val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(inflater,parent,false)

        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding){
            Glide.with(context)
                .load(item.imageSrc)
                .placeholder(R.drawable.ic_car)
                .centerCrop()
                .into(carImage)

            postText.text = item.postText
            numberOfLikes.text = item.likeCount.toString()
            numberOfComments.text = item.commentCount.toString()
            root.tag = this
            postConstraintLayout.tag = this
        }

    }

    companion object{
        val diffCallBack = object : DiffUtil.ItemCallback<PostInfo>() {

            override fun areItemsTheSame(oldItem: PostInfo, newItem: PostInfo): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: PostInfo, newItem: PostInfo): Boolean {
                return false
            }
        }
    }
}