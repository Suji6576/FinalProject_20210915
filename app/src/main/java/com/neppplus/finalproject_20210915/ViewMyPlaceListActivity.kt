package com.neppplus.finalproject_20210915

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_20210915.adapters.MyPlaceRecyclerAdapter
import com.neppplus.finalproject_20210915.databinding.ActivityViewMyPlaceListBinding
import com.neppplus.finalproject_20210915.datas.BasicResponse
import com.neppplus.finalproject_20210915.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMyPlaceListActivity : BaseActivity() {

    lateinit var binding: ActivityViewMyPlaceListBinding

    val mMyPlaceList = ArrayList<PlaceData>()

    lateinit var mPlaceAdapter : MyPlaceRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_my_place_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {


        addBtn.setOnClickListener {

            val myIntent = Intent(mContext, EditMyPlaceActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {
        
        titleTxt.text = "내가 자주 쓰는 출발장소들"

        addBtn.visibility = View.VISIBLE

        binding.myPlaceRecyclerView.layoutManager = LinearLayoutManager(mContext)

        mPlaceAdapter = MyPlaceRecyclerAdapter(mContext, mMyPlaceList)
        binding.myPlaceRecyclerView.adapter = mPlaceAdapter

    }

    override fun onResume() {
        super.onResume()
        getMyPlaceListFromServer()
    }

    fun getMyPlaceListFromServer() {

        apiService.getRequestMyPlaceList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val basicResponse = response.body()!!

                    mMyPlaceList.clear()

                    mMyPlaceList.addAll( basicResponse.data.places )

                    mPlaceAdapter.notifyDataSetChanged()


                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }


        })

    }

}