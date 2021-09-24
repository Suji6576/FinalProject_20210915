package com.neppplus.finalproject_20210915

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_20210915.adapters.AppointmentAdapter
import com.neppplus.finalproject_20210915.adapters.AppointmentRecyclerAdapter
import com.neppplus.finalproject_20210915.databinding.ActivityMainBinding
import com.neppplus.finalproject_20210915.datas.AppointmentData
import com.neppplus.finalproject_20210915.datas.BasicResponse
import com.neppplus.finalproject_20210915.fragments.InvitedAppointmentListFragment
import com.neppplus.finalproject_20210915.fragments.MyAppointmentListFragment
import com.neppplus.finalproject_20210915.fragments.SettingsFragment
import com.neppplus.finalproject_20210915.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {


        binding.bottomNavigation.setOnItemSelectedListener {


            if (it.itemId == R.id.addAppointmentBtn) {
                val myIntent = Intent(mContext, EditAppoinmentActivity::class.java)
                startActivity(myIntent)
            }
            else {

                val frag = when (it.itemId) {
                    R.id.myAppointmentsBtn -> MyAppointmentListFragment.getFrag()
                    R.id.invitedAppointmentsBtn -> InvitedAppointmentListFragment.getFrag()
                    else -> SettingsFragment.getFrag()
                }
                changeFragment(frag)
            }

            return@setOnItemSelectedListener true
        }

    }

    override fun setValues() {

        changeFragment(MyAppointmentListFragment.getFrag())
        Toast.makeText(mContext, "${GlobalData.loginUser!!.nickName}님 환영합니다!", Toast.LENGTH_SHORT).show()

//        메인화면의 화면 제목 변경
        titleTxt.text = "메인 화면"

    }

    fun changeFragment(frag: Fragment) {

        val fragTransation = supportFragmentManager.beginTransaction()
        fragTransation.replace(R.id.fragFrameLayout, frag)
        fragTransation.commit()
    }

}