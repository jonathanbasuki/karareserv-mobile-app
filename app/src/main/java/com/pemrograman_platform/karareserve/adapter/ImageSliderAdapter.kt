package com.pemrograman_platform.karareserve.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.pemrograman_platform.karareserve.R

class ImageSliderAdapter(private val imageList: List<Int>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.sliderImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slider_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val radiusPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            12f,
            holder.itemView.context.resources.displayMetrics
        ).toInt()

        Glide.with(holder.itemView.context)
            .load(imageList[position])
            .transform(CenterCrop(), RoundedCorners(radiusPx))
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = imageList.size
}
