package com.example.carsfans.presentation.list.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carsfans.R
import com.example.carsfans.databinding.CarItemBinding
import com.example.carsfans.domain.CarInfo

class ListAdapter(
    private val dataSet: List<CarInfo>,
    private val context: Context,
    private val actionListener: ListActionListener

) : RecyclerView.Adapter<ListAdapter.ListViewHolder>(), View.OnClickListener {


    class ListViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CarItemBinding.inflate(inflater, parent, false)

        binding.carConstraintLayout.setOnClickListener(this)
        binding.root.setOnClickListener(this)

        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val item = dataSet[position]

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

        holder.binding.carInfo.text = carInfoText

    }

    override fun onClick(v: View?) {
        val carInfo = v?.tag as CarInfo
        when(v.id){
            R.id.car_constraint_layout -> actionListener.onItemClick(carInfo)
            else -> actionListener.onItemClick(carInfo)
        }
    }

    interface ListActionListener {
        fun onItemClick(carInfo: CarInfo)
    }
}