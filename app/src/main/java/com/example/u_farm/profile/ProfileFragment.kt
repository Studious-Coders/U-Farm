package com.example.u_farm.profile

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.u_farm.R
import com.example.u_farm.databinding.FragmentNewsBinding
import com.example.u_farm.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)
        (activity as AppCompatActivity).supportActionBar?.title="Profile"
        //val application= requireNotNull(this.activity).application
        return binding.root
    }

//    fun onpreferredlang()
//    {
//        val builder = AlertDialog.Builder()
//        builder.setTitle("Languages")
//            .setItems(R.array.languageslist,
//                DialogInterface.OnClickListener { dialog, which ->
//                    // The 'which' argument contains the index position
//                    // of the selected item
//                })
//        builder.create()
//    }
}




