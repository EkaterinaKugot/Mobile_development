package com.example.weather2

import android.app.Dialog
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogFragment : DialogFragment(){
    var onPositiveClickListener: OnClickListener? = null
    var onNegativeClickListener: OnClickListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Attention!")
            .setMessage("Choose a way to display the weather in London")
            .setPositiveButton("Briefly") { dialogInterface, which ->
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, BriefWeatherFragment())
                    .commitAllowingStateLoss()
                onPositiveClickListener?.onClick(dialog, which)
                dismiss()
            }
            .setNegativeButton("In detail") { dialogInterface, which ->
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, DetailedWeatherFragment())
                    .commitAllowingStateLoss()
                onNegativeClickListener?.onClick(dialog, which)
                dismiss()
            }
            .create()
            .apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
            }
    }
}