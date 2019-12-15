package com.example.dietapp.ui.base

abstract class BasePresenter<V> {
    protected var mvpView: V? = null

    fun bind(mvpView: V?) {
        this.mvpView = mvpView
    }

    fun unbind() {
        this.mvpView = null
    }
}