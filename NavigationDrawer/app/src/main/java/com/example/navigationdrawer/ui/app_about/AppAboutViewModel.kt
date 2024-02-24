package com.example.navigationdrawer.ui.app_about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppAboutViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is About App Fragment"
    }
    val text: LiveData<String> = _text
}