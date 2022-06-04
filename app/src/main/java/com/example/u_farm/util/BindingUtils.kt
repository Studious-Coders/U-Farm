package com.example.u_farm.util


import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
//
//var option= chosedlang
////chosedlang=chosedlang
//

@SuppressLint("SetTextI18n")
@BindingAdapter("bind:text","bind:lang")
fun TextView.setUser(item: String?,item1:String) {
    item?.let {
        text=item
        val py = Python.getInstance();
        val pyobj = py.getModule("translate")
        if(item1=="Tamil")
          text=pyobj.callAttr("tam",item).toString()
        else if(item1=="English")
            text=pyobj.callAttr("eng",item).toString()
        else
            text=pyobj.callAttr("hin",item).toString()
    }
}



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



