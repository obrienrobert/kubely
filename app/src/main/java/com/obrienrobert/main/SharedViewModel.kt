package com.obrienrobert.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.obrienrobert.models.ClusterModel

class SharedViewModel : ViewModel() {

    val data = MutableLiveData<String>()
}