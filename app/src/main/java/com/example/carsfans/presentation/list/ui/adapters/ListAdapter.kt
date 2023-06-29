package com.example.carsfans.presentation.list.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carsfans.R
import com.example.carsfans.databinding.CarItemBinding
import com.example.carsfans.domain.models.CarInfo

class ListAdapter(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val actionListener: ListActionListener

) : androidx.recyclerview.widget.ListAdapter<CarInfo, ListAdapter.ListViewHolder>(diffCallBack), View.OnClickListener {


    class ListViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CarItemBinding.inflate(inflater, parent, false)


        binding.root.layoutParams.height = recyclerView.measuredHeight/9
        binding.carConstraintLayout.setOnClickListener(this)
        binding.root.setOnClickListener(this)


        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val item = getItem(position)

        val x = Pair(item,position)

        val carInfoText = context.getString(
            R.string.car_info_text,
            item.brandName,
            item.modelName,
            item.engineVolume,
            item.engineName,
            item.transmissionName,
            item.year
        )

        Glide.with(context)
            .load(item.imageSrc)
            .placeholder(R.drawable.ic_car)
            .centerCrop()
            .into(holder.binding.carImage)


        holder.binding.brandName.text = carInfoText
        holder.binding.carConstraintLayout.tag = x
        holder.binding.root.tag = x

    }

    override fun onClick(v: View?) {
        val carInfo = v?.tag as Pair<CarInfo, Int>

        when(v.id){
            R.id.car_constraint_layout -> actionListener.onItemClick(carInfo.first, carInfo.second)
            else -> actionListener.onItemClick(carInfo.first, carInfo.second)
        }
    }

    interface ListActionListener {
        fun onItemClick(carInfo: CarInfo, position: Int)
    }

    companion object{
        val diffCallBack = object : DiffUtil.ItemCallback<CarInfo>() {

            override fun areItemsTheSame(oldItem: CarInfo, newItem: CarInfo): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: CarInfo, newItem: CarInfo): Boolean {
                return false
            }
        }
    }
}