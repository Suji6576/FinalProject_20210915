package com.neppplus.finalproject_20210915.web

import com.neppplus.finalproject_20210915.datas.BasicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ServerAPIService {

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nickname: String) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") pw: String ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(
        @Field("provider") provider: String,
        @Field("uid") id: String,
        @Field("nick_name") name: String  ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/appointment")
    fun postRequestAppointment(
        @Field("title")  title: String,
        @Field("datetime") datetime:String,
        @Field("start_place") startPlaceName: String,
        @Field("start_latitude") startLat: Double,
        @Field("start_longitude") startLng: Double,
        @Field("place") placeName: String,
        @Field("latitude") lat: Double,
        @Field("longitude") lng: Double
    ) : Call<BasicResponse>


//    GET - 약속 목록 가져오기

    @GET("/appointment")
    fun getRequestAppointmentList() : Call<BasicResponse>

    @GET("/user")
    fun getRequestMyInfo() : Call<BasicResponse>


//    POST - PUT - PATCH : FormData 활용
//    회원정보 수정 API
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestMyInfo(
        @Field("field") field:String,
        @Field("value") value:String) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/place")
    fun postRequestAddMyPlace(
        @Field("name") name: String,
        @Field("latitude") lat: Double,
        @Field("longitude") lng: Double,
        @Field("is_primary") isPrimary: Boolean) : Call<BasicResponse>


    @GET("/user/place")
    fun getRequestMyPlaceList() : Call<BasicResponse>

//    프로필사진첨부 Multipart 활용.
//    Multipar방식의 통신에서는 Field를 담지 않고, MultipartBody.Part 양식으로 (파일)데이터 첨부.
//    사진 외의 데이터도 첨부할때는, 나머지 항목들은 Requestbody 형태로 첨부함.
    @Multipart
    @PUT("/user/image")
    fun putRequestProfileImage(@Part profileImg: MultipartBody.Part) : Call<BasicResponse>

//    친구목록불러오기
//    쿼리 파라미터를 넣어서 불러오기
    @GET("/user/friend")
    fun getRequestFriendList(@Query("type")type: String) : Call<BasicResponse>



}