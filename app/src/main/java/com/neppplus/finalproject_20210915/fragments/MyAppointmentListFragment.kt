package com.neppplus.finalproject_20210915.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_20210915.R
import com.neppplus.finalproject_20210915.adapters.AppointmentRecyclerAdapter
import com.neppplus.finalproject_20210915.databinding.FragmentMyAppointmentsListBinding
import com.neppplus.finalproject_20210915.datas.AppointmentData
import com.neppplus.finalproject_20210915.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAppointmentListFragment : BaseFragment() {

    companion object{
        private var frag : MyAppointmentListFragment? = null

        fun getFrag() : MyAppointmentListFragment {
            if (frag == null) {
                frag = MyAppointmentListFragment()
            }
            return frag!!
        }
    }

    lateinit var binding: FragmentMyAppointmentsListBinding

    val mAppointmentList = ArrayList<AppointmentData>()

    lateinit var mRecyclerAdapter : AppointmentRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_appointments_list, container, false)
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

        mRecyclerAdapter = AppointmentRecyclerAdapter(mContext, mAppointmentList)
        binding.myAppointmentRecyclerview.adapter = mRecyclerAdapter

        binding.myAppointmentRecyclerview.layoutManager = LinearLayoutManager(mContext)

    }

    override fun onResume() {
        super.onResume()
        getAppointmentListFromServer()
    }

    fun getAppointmentListFromServer() {

        apiService.getRequestAppointmentList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                val basicResponse = response.body()!!

                mAppointmentList.clear()

//                약속목록변수에 => 서버가 알려준 약속목록을 전부 추가.
                mAppointmentList.addAll( basicResponse.data.appointments )

//                어댑터 새로고침
                mRecyclerAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }
}