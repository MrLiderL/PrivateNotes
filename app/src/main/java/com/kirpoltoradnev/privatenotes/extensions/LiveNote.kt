package com.kirpoltoradnev.privatenotes.extensions

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveNote(defaultValue: T? = null): MutableLiveData<T>{
    val data = MutableLiveData<T>()

    if (defaultValue!=null){
        data.value = defaultValue
    }

    return data
}