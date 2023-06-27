package com.example.carsfans.presentation.info.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carsfans.R
import com.example.carsfans.databinding.PostItemBinding
import com.example.carsfans.domain.PostInfo

class PostsAdapter(
    private var dataSet: List<PostInfo>,
    private val context: Context,
    private val actionListener: PostActionListener

    ): RecyclerView.Adapter<PostsAdapter.PostViewHolder>(), View.OnClickListener {

    class PostViewHolder(val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(inflater,parent,false)

        binding.postConstraintLayout.setOnClickListener(this)
        binding.root.setOnClickListener(this)

        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = dataSet[position]

        with(holder.binding){
            Glide.with(context)
                .load(item.imageSrc)
                .placeholder(R.drawable.ic_car)
                .centerCrop()
                .into(carImage)

            postText.text = item.postText
            numberOfLikes.text = item.likeCount.toString()
            numberOfComments.text = item.commentCount.toString()
        }

    }

    override fun getItemCount(): Int  = dataSet.size

    override fun onClick(v: View?) {
        val postInfo = v?.tag as PostInfo
        when(v.id){
            R.id.post_constraint_layout -> actionListener.onItemClick(postInfo)
            else -> actionListener.onItemClick(postInfo)
        }
    }

    interface PostActionListener{
        fun onItemClick(postInfo: PostInfo)
    }
}