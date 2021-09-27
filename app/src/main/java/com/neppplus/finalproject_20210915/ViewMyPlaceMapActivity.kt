package com.neppplus.finalproject_20210915

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.neppplus.finalproject_20210915.datas.PlaceData

class ViewMyPlaceMapActivity : BaseActivity() {

    lateinit var mPlaceData : PlaceData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_my_place_map)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mPlaceData = intent.getSerializableExtra("place") as PlaceData

        titleTxt.text = mPlaceData.name

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapFrag) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naverMapFrag, it).commit()
            }

        mapFragment.getMapAsync {

            val appointmentLatLng = LatLng(mPlaceData.latitude, mPlaceData.longitude)

//           약속장소로 카메라를 옮겨주자.
            val cameraUpdate = CameraUpdate.scrollTo(appointmentLatLng)
            it.moveCamera(cameraUpdate)

//            마커를 찍고 표시

            val marker = Marker()
            marker.position = appointmentLatLng
            marker.map = it
        }

    }

}