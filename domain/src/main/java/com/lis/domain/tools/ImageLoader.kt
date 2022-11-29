package com.lis.domain.tools

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader {
    fun setImage(imageView: ImageView, image: Any) {
        Glide
            .with(imageView)
            .load(image)
            .into(imageView)
    }
}