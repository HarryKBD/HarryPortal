package com.harryportal.app

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.harryportal.app.databinding.ActivityNaverMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PolylineOverlay

class NaverMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityNaverMapBinding

    // 서울 주요 관광 명소 POI 목록
    private val poiList = listOf(
        PoiItem("경복궁",        LatLng(37.5796, 126.9770)),
        PoiItem("남산 서울타워", LatLng(37.5512, 126.9882)),
        PoiItem("홍대입구",      LatLng(37.5574, 126.9245)),
        PoiItem("강남역",        LatLng(37.4981, 127.0276)),
        PoiItem("코엑스",        LatLng(37.5115, 127.0590))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaverMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "네이버 지도"

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.mapFragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        // 서울 중심으로 카메라 이동
        naverMap.moveCamera(CameraUpdate.scrollAndZoomTo(LatLng(37.5340, 126.9900), 10.0))

        // 각 POI에 마커 추가
        poiList.forEach { poi ->
            Marker().apply {
                position = poi.latLng
                captionText = poi.name
                map = naverMap
            }
        }

        // POI 간 연결선 (PolylineOverlay)
        PolylineOverlay().apply {
            coords = poiList.map { it.latLng }
            color = Color.argb(200, 66, 133, 244)
            width = (6 * resources.displayMetrics.density).toInt()
            map = naverMap
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    data class PoiItem(
        val name: String,
        val latLng: LatLng
    )
}
