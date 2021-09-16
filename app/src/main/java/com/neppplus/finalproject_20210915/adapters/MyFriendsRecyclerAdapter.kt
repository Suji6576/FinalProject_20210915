package com.neppplus.finalproject_20210915.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.finalproject_20210915.R
import com.neppplus.finalproject_20210915.datas.UserData

class MyFriendsRecyclerAdapter(
    val mContext: Context,
    val mList: List<UserData>): RecyclerView.Adapter<MyFriendsRecyclerAdapter.FriendViewHolder> () {


    class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val friendProfileImg = view.findViewById<ImageView>(R.id.friendProfileImg)
        val nicknameTxt = view.findViewById<TextView>(R.id.nicknameTxt)
        val socialLoginImg = view.findViewById<ImageView>(R.id.socialLoginImg)

        fun bind(context: Context ,data: UserData){
            nicknameTxt.text = data.nickName

            Glide.with(context).load(data.profileImgUrl).into(friendProfileImg)

            when(data.provider){
                "facecook" -> {
                    socialLoginImg.setImageResource(R.drawable.facebook_login_icon)
                    socialLoginImg.visibility = View.VISIBLE
                }
                "kakao" -> {
                    socialLoginImg.setImageResource(R.drawable.kakao_login_icon)
                    socialLoginImg.visibility = View.VISIBLE
                }
                else -> {
                    socialLoginImg.visibility = View.GONE
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.friend_list_item, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        val data = mList[position]
        holder.bind(mContext,data)

    }

    override fun getItemCount() = mList.size

}