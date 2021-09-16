package com.neppplus.finalproject_20210915

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.neppplus.finalproject_20210915.adapters.FriendPagerAdapter
import com.neppplus.finalproject_20210915.databinding.ActivityViewMyFriendsListBinding
import com.neppplus.finalproject_20210915.datas.BasicResponse

class ViewMyFriendsListActivity : BaseActivity() {

    lateinit var binding: ActivityViewMyFriendsListBinding

    lateinit var mFPA : FriendPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_view_my_friends_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        binding.friendsViewPager.addOnAdapterChangeListener(object : TabLayout.ViewPagerOnTabSelectedListener {
//
//        })

        addBtn.setOnClickListener {
            val myIntent = Intent(mContext, AddFriendActivity::class.java)
            startActivity(myIntent)
        }


    }

    override fun setValues() {

        titleTxt.text = "친구 관리"
        addBtn.visibility = View.VISIBLE

        mFPA = FriendPagerAdapter(supportFragmentManager)
        binding.friendsViewPager.adapter = mFPA

        binding.friendsTabLayout.setupWithViewPager(binding.friendsViewPager)

    }
}