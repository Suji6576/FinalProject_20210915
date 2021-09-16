package com.neppplus.finalproject_20210915

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.finalproject_20210915.databinding.ActivityMySettingBinding
import com.neppplus.finalproject_20210915.datas.BasicResponse
import com.neppplus.finalproject_20210915.utils.GlobalData
import com.neppplus.finalproject_20210915.utils.URIPathHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MySettingActivity : BaseActivity() {

    lateinit var binding: ActivityMySettingBinding

//
    val REQ_FOR_GALLERY = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_setting)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        프로필 사진 누르면 => 프사변경의 의미로 사용 => 갤러리 프사 선택하러 진입
//        안드로이드가 제공하는 갤러리 화면 활용 .Intent(4) 추가항목
//        어떤사진? 결과를 얻기 위해 화면이동. Intent(3) 활용

        binding.profileImg.setOnClickListener {

//            갤러리를 개발자가 이용하겠다 => 허락을 받아야 볼 수 있다. => 권한세팅필요
//            TedPermission라이브러리

            val permissionListener = object : PermissionListener{
                override fun onPermissionGranted() {
//                    권한 ok일때
//                    갤러리로 사진 가지러 이동. (추가작업)

                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_GET_CONTENT
                    myIntent.type = "image/*"
                    startActivityForResult(Intent.createChooser(myIntent,"프로필사진 선택하기"), REQ_FOR_GALLERY)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//               (최종으로) 권한 거절되었을때 => 토스트로 안내
                    Toast.makeText(mContext, "권한이 거부되어 접근이 불가능합니다.", Toast.LENGTH_SHORT).show()
                }

            }
//            실제로 권한체크/
//            manifest에 권한 등록 => 실제 라이브러리에 질문
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("[설정] > [권한]에서 갤러리 권한을 열어주세요.")
                .check()

        }

       binding.myPlacesLayout.setOnClickListener {
            val myIntent = Intent(mContext, ViewMyPlaceListActivity::class.java)
            startActivity(myIntent)
        }

        binding.editNicknameLayout.setOnClickListener {

//            응용문제 => AlertDialog로 닉네임을 입력받자.
//             EditText를 사용할 수 있는 방법?

//            PATCH - /user => field : nickname으로 보내서 닉변.

            val customView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_alert_nickname, null)
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("닉네임 변경")
            alert.setView(customView)
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                val nicknameEdt = customView.findViewById<EditText>(R.id.nicknameEdt)

                apiService.patchRequestMyInfo("nickname", nicknameEdt.text.toString()).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val basicResponse = response.body()!!
                            GlobalData.loginUser = basicResponse.data.user

                            setUserInfo()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                })

            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

        binding.readyTimeLayout.setOnClickListener {

//            응용문제 => AlertDialog로 준비시간을 입력받자.
//             EditText를 사용할 수 있는 방법? 구글링

            val customView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_alert_edt, null)

            val alert = AlertDialog.Builder(mContext)

            alert.setTitle("준비 시간 설정")
//            커스텀뷰를 가져와서, 얼럿의 View로 설정.
            alert.setView(customView)
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                val minuteEdt = customView.findViewById<EditText>(R.id.minuteEdt)

//                Toast.makeText(mContext, "${minuteEdt.text.toString()}", Toast.LENGTH_SHORT).show()

                apiService.patchRequestMyInfo("ready_minute", minuteEdt.text.toString()).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {

//                            내 수정된 정보 파싱. => 로그인한 사용자의 정보로 갱신.
                            val basicResponse = response.body()!!

                            GlobalData.loginUser = basicResponse.data.user

                            setUserInfo()


                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                })


            })
            alert.setNegativeButton("취소", null)
            alert.show()


        }

    }

    override fun setValues() {

        titleTxt.text = "내 정보 설정"

        setUserInfo()
    }

    fun setUserInfo() {
        binding.nicknameTxt.text = GlobalData.loginUser!!.nickName

//       로그인한사람의 준비시간이 1시간 이상 or 아니냐
        if (GlobalData.loginUser!!.readyMinute >= 60) {
            val hour = GlobalData.loginUser!!.readyMinute / 60
            val minute = GlobalData.loginUser!!.readyMinute % 60

            binding.readyTimeTxt.text = "${hour}시간 ${minute}분"
        }
        else {
            binding.readyTimeTxt.text = "${GlobalData.loginUser!!.readyMinute}분"
        }

//        페북 / 카톡 / 일반 이냐 에 따라 이미지를 다르게  처리.

        when(GlobalData.loginUser!!.provider) {
            "facebook" -> binding.socialLoginImg.setImageResource(R.drawable.facebook_login_icon)
            "kakao" -> binding.socialLoginImg.setImageResource(R.drawable.kakao_login_icon)
            else -> binding.socialLoginImg.visibility = View.GONE
        }

//        일반로그인 (default) 는 비번 변경 UI 표시.
        when (GlobalData.loginUser!!.provider) {
            "default" -> binding.passwordLayout.visibility = View.VISIBLE
            else -> binding.passwordLayout.visibility = View.GONE
        }

//        로그인한 사용자는 프로필사진경로 (URL -String)도 들고있다. => profileImg에 적용.(Glide)

        Glide.with(mContext).load(GlobalData.loginUser!!.profileImgUrl).into(binding.profileImg)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        갤러리에서 사진 가져온 경우?
        if (requestCode == REQ_FOR_GALLERY) {

//            실제로 이미지를 선택한건지?
            if (resultCode == RESULT_OK) {

//                어떤 사진을 골랐는지? 파악해보자.
//                임시 : 고른 사진을 profileImg에 바로 적용만. (서버전송 X)

//                data?  =>  이전 화면이 넘겨준 intent
//                data?.data => 선택한 사진이 들어있는 경로 정보 (Uri)
                val dataUri = data?.data

//                Uri -> 이미지뷰의 사진으로. (Glide)
//                Glide.with(mContext).load(dataUri).into(binding.profileImg)

//                API서버에 사진을 전송. => PUT - /user/image 로 API 활용.
//                파일을 같이 첨부해야한다. => Multipart 형식의 데이터 첨부 활용. (기존 FormData와는 다르다!)

//                Uri -> File형태로 변환 -> 그 파일의 실제 경로? 얻어낼 필요가 있다.

                val file = File( URIPathHelper().getPath(mContext, dataUri!!) )

//                파일을 Retrofit에 첨부할 수 있는 => RequestBody => Multipartbody 형태로 변환
                val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)
                val body = MultipartBody.Part.createFormData("profile_image","myFile.jpg", fileReqBody)

                apiService.putRequestProfileImage(body).enqueue(object : Callback<BasicResponse>{
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

    }

}