package com.example.u_farm.util

import android.annotation.SuppressLint
import android.widget.TextView
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

@BindingAdapter("imgUrl")
fun bindImage(circleImageView: CircleImageView, imgUrl:String?) {
    imgUrl?.let{
        if(it!=""){
            Picasso.with(circleImageView.context).load(imgUrl).into(circleImageView)
        }
    }
}



