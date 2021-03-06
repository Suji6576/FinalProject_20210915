package com.neppplus.finalproject_20210915.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_20210915.R
import com.neppplus.finalproject_20210915.adapters.MyFriendsRecyclerAdapter
import com.neppplus.finalproject_20210915.adapters.RequestUserRecyclerAdapter
import com.neppplus.finalproject_20210915.databinding.FragmentRequestedUserListBinding
import com.neppplus.finalproject_20210915.datas.BasicResponse
import com.neppplus.finalproject_20210915.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestedUserListFragment : BaseFragment() {

    companion object {
        private var frag : RequestedUserListFragment? = null

        fun getFrag() : RequestedUserListFragment {
            if ( frag == null ) {
                frag = RequestedUserListFragment()
            }
            return frag!!
        }

    }

    lateinit var binding:  FragmentRequestedUserListBinding

    val mReqUserList = ArrayList<UserData>()
    lateinit var mReqUserAdapter: RequestUserRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_requested_user_list, container, false)
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

        mReqUserAdapter = RequestUserRecyclerAdapter(mContext, mReqUserList)
        binding.requestUserRecyclerView.adapter = mReqUserAdapter

        binding.requestUserRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    override fun onResume() {
        super.onResume()
        getRequestUserListFromServer()
    }

    fun getRequestUserListFromServer() {

        apiService.postRequestAddFriend(id).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    val basicResponse = response.body()!!

                    mReqUserList.clear()
                    mReqUserList.addAll(basicResponse.data.friends)
                    mReqUserAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }


}