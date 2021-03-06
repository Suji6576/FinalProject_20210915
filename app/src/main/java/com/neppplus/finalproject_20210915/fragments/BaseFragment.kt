package com.neppplus.finalproject_20210915.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neppplus.finalproject_20210915.web.ServerAPI
import com.neppplus.finalproject_20210915.web.ServerAPIService
import retrofit2.Retrofit

abstract class BaseFragment :Fragment() {

    lateinit var mContext: Context

    private lateinit var retrofit: Retrofit
    lateinit var apiService : ServerAPIService

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireContext()
        retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIService::class.java)
    }

    abstract fun setUpEvents()
    abstract fun setValues()
}