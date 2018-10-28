package com.example.android.mvvmarchitectureexample.viewmodel

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Greta Grigutė on 2018-10-27.
 */
class SchedulersWrapper {
    fun io(): Scheduler {
        return Schedulers.io()
    }

    fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}