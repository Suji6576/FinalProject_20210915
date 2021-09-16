package com.neppplus.finalproject_20210915.fragments

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment :Fragment() {

    lateinit var mContext: Context

    abstract fun setUpEvents()
    abstract fun setValues()
}