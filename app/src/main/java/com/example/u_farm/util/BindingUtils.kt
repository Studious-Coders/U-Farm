package com.example.u_farm.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

@SuppressLint("SetTextI18n")
@BindingAdapter("userDetails")
fun TextView.setUserDetails(item: String?) {
    item?.let {
        text = item
        initialzePython()
        text=pyobj.callAttr(lang,text).toString()
    }
}


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl:String?) {
    imgUrl?.let{
        if(it!=""){
            Glide.with(imgView.context).load(imgUrl).into(imgView)
        }
    }
}

@BindingAdapter("imgUrl")
fun bindImage(circleImageView: CircleImageView, imgUrl:String?) {
    imgUrl?.let{
        if(it!=""){
            Glide.with(circleImageView.context).load(imgUrl).into(circleImageView)

//            Picasso.get().load(imgUrl).into(circleImageView)
        }
    }
}



