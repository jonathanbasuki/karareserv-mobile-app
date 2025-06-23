package com.pemrograman_platform.karareserve.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.pemrograman_platform.karareserve.R

class ImageSliderAdapter(private val imageUrls: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.sliderImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slider_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val radiusInDp = 12
        val scale = holder.itemView.context.resources.displayMetrics.density
        val radiusInPx = (radiusInDp * scale).toInt()

        Glide.with(holder.itemView.context)
            .load(imageUrls[position])
            .transform(CenterCrop(), RoundedCorners(radiusInPx))
            .into(holder.image)

    }

    override fun getItemCount(): Int = imageUrls.size
}
