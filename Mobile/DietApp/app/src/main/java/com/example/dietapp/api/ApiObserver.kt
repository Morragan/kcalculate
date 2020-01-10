package com.example.dietapp.api

import androidx.lifecycle.Observer

class ApiObserver<T>(private val changeListener: ChangeListener<T>) : Observer<ApiResponse<T>>{

    override fun onChanged(apiResponse: ApiResponse<T>?) {
        apiResponse?.data?.let{
            changeListener.onSuccess(it)
        }
    }

    interface ChangeListener<T>{
        fun onSuccess(apiResponse: T)
        fun onException(e: Exception)
    }
}