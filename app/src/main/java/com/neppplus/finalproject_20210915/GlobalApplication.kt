package com.neppplus.finalproject_20210915

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "785cb45e455d8321cc1f26cd80611ecd")

    }

}