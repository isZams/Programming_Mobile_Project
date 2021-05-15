package com.example.programming_mobile_project.beach_view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BeachViewModel : ViewModel() {
    private val _ombrelli = MutableLiveData<MutableSet<Int>>(mutableSetOf())
    val ombrelli: LiveData<MutableSet<Int>>
        get() = _ombrelli

    fun selectedOmbrelliChanged(ombrello: Int) {
        if (_ombrelli.value?.add(ombrello) == true)
            return
        _ombrelli.value?.remove(ombrello)
    }
}