package com.neppplus.finalproject_20210915.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.finalproject_20210915.fragments.MyFriendsListFragment
import com.neppplus.finalproject_20210915.fragments.RequestedUserListFragment

class FriendPagerAdapter (fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "내 친구목록"
            else -> "친구추가요청"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MyFriendsListFragment()
            else -> RequestedUserListFragment.getFrag()
        }
    }
}