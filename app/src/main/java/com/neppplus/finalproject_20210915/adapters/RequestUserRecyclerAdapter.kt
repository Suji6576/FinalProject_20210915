package com.neppplus.finalproject_20210915.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.finalproject_20210915.R
import com.neppplus.finalproject_20210915.datas.BasicResponse
import com.neppplus.finalproject_20210915.datas.UserData
import com.neppplus.finalproject_20210915.web.ServerAPI
import com.neppplus.finalproject_20210915.web.ServerAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RequestUserRecyclerAdapter (
    val mContext: Context,
    val mList:List<UserData>) : RecyclerView.Adapter<RequestUserRecyclerAdapter.UserViewHolder>() {

        class UserViewHolder(view:View) : RecyclerView.ViewHolder(view) {
            val profileImg = view.findViewById<ImageView>(R.id.profileImg)
            val nicknameTxt = view.findViewById<TextView>(R.id.nicknameTxt)
            val socialLoginImg = view.findViewById<ImageView>(R.id.socialLoginImg)
            val acceptBtn = view.findViewById<Button>(R.id.acceptBtn)
            val refuseBtn = view.findViewById<Button>(R.id.refuseBtn)

            fun bind(context: Context, data: UserData) {

                Glide.with(context).load(data.profileImgURL).into(profileImg)
                nicknameTxt.text = data.nickName
                when ( data.provider ) {
                    "facebook" -> {
                        socialLoginImg.visibility = View.VISIBLE
                        socialLoginImg.setImageResource(R.drawable.facebook_login_icon)
                    }
                    "kakao" -> {
                        socialLoginImg.visibility = View.VISIBLE
                        socialLoginImg.setImageResource(R.drawable.kakao_login_icon)
                    }
                    else -> {
                        socialLoginImg.visibility = View.GONE
                    }
                }

//                 수락/거절버튼 둘 다 하는 일은 동일. type에 첨부할 값만 다르다.
//                버튼에 미리 태그를 달아두고 => 꺼내서 쓰는 동일한 작업.

                val sendOkOrNoToSever = object : View.OnClickListener {
                    override fun onClick(p0: View?) {

                        val okOrNo = p0!!.tag.toString()

//                        어댑터에서 API서비스 사용법.
//                        (1) 직접 만들자(!)
//                        (2) 화면 (context)의 변수를 활용. => 액티비티의 어댑터에서 활용 편함.

                        val apiService = ServerAPI.getRetrofit(context).create(ServerAPIService::class.java)

                        apiService.putRequestSendOkOrNoFriend(data.id, okOrNo).enqueue(object : Callback<BasicResponse>{
                            override fun onResponse(
                                call: Call<BasicResponse>,
                                response: Response<BasicResponse>
                            ) {

                            }

                            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                            }

                        })

                    }

                }
                acceptBtn.setOnClickListener (sendOkOrNoToSever)
                refuseBtn.setOnClickListener(sendOkOrNoToSever)

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.request_user_list_item,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mContext, mList[position])
    }

    override fun getItemCount() = mList.size

}