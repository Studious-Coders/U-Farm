package com.example.u_farm.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


@SuppressLint("SetTextI18n")
@BindingAdapter("userDetails")
fun TextView.setUserDetails(item: String?) {
    item?.let {
        text=item
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl:String?) {
    imgUrl?.let{
        if(it!=""){
            Picasso.with(imgView.context).load(imgUrl).into(imgView)
        }
    }
}

@BindingAdapter("imgUrl")
fun bindImage(circleImageView: CircleImageView, imgUrl:String?) {
    imgUrl?.let{
        if(it!=""){
            Picasso.with(circleImageView.context).load(imgUrl).into(circleImageView)
        }
    }
}



