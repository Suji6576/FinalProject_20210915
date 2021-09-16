package com.neppplus.finalproject_20210915.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neppplus.finalproject_20210915.R
import com.neppplus.finalproject_20210915.adapters.MyFriendsRecyclerAdapter
import com.neppplus.finalproject_20210915.databinding.FragmentMyFriendsListBinding
import com.neppplus.finalproject_20210915.datas.BasicResponse
import com.neppplus.finalproject_20210915.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendsListFragment : BaseFragment() {

    lateinit var binding: FragmentMyFriendsListBinding
    val mFriendsList = ArrayList<UserData>()
    lateinit var mFriendAdapter: MyFriendsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_friends_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {



    }

    override fun setValues() {

        mFriendAdapter = MyFriendsRecyclerAdapter(mContext, mFriendsList)
        binding.myFriendsRecyclerView.adapter = mFriendAdapter

    }

    override fun onResume() {
        super.onResume()
        getMyFriendsListFromServer()
    }

    fun getMyFriendsListFromServer() {

        apiService.getRequestFriendList("my").enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful){

                    val BasicResponse = response.body()!!

                    mFriendsList.clear()

                    mFriendsList.addAll(BasicResponse.data.friends)
                    mFriendAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }

}