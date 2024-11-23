package com.fri.emp_projekt.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val userId = MutableLiveData<Int?>()
}
