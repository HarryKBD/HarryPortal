package com.harryportal.app

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.harryportal.app.databinding.ActivityKakaoMapBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextBuilder
import com.kakao.vectormap.label.LabelTextStyle
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles

class KakaoMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKakaoMapBinding

    // 서울 주요 관광 명소 POI 목록
    private val poiList = listOf(
        PoiItem("경복궁",       "조선 왕조의 법궁",         LatLng.from(37.5796,  126.9770)),
        PoiItem("남산 서울타워", "서울의 랜드마크 전망대",   LatLng.from(37.5512,  126.9882)),
        PoiItem("홍대입구",     "젊음과 문화의 거리",        LatLng.from(37.5574,  126.9245)),
        PoiItem("강남역",       "서울 최대 번화가",          LatLng.from(37.4981,  127.0276)),
        PoiItem("코엑스",       "국제 무역 전시 센터",       LatLng.from(37.5115,  127.0590))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "카카오 지도"

        startKakaoMap()
    }

    private fun startKakaoMap() {
        binding.mapView.start(
            object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                    // 지도 API 가 정상적으로 종료될 때 호출됨
                }

                override fun onMapError(error: Exception) {
                    binding.tvError.text = "지도 오류: ${error.message}\n\n카카오 개발자 콘솔에서\n발급받은 Native App Key를\n설정하세요."
                    binding.tvError.visibility = android.view.View.VISIBLE
                }
            },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {
                    onKakaoMapReady(kakaoMap)
                }
            }
        )
    }

    private fun onKakaoMapReady(kakaoMap: KakaoMap) {
        // 지도 중심을 서울 중심으로 이동
        val seoulCenter = LatLng.from(37.5340, 126.9900)
        kakaoMap.moveCamera(
            CameraUpdateFactory.newCenterPosition(seoulCenter, 11)
        )

        addMarkersAndPolyline(kakaoMap)
    }

    private fun addMarkersAndPolyline(kakaoMap: KakaoMap) {
        val labelManager = kakaoMap.labelManager ?: return

        // 라벨 스타일 생성 (텍스트만 표시)
        val labelStyle = LabelStyle.from(R.drawable.ic_location_pin)
            .setTextStyles(
                LabelTextStyle.from(this, R.style.MapLabelTextStyle)
            )
        val labelStyles = LabelStyles.from(labelStyle)

        // 각 POI에 마커 추가
        poiList.forEach { poi ->
            val options = LabelOptions.from(poi.latLng)
                .setStyles(labelStyles)
                .setTexts(LabelTextBuilder().setTexts(poi.name))
            labelManager.layer?.addLabel(options)
        }

        // POI 간 연결선 (RouteLine) 추가
        val points = poiList.map { it.latLng }
        val routeLineStyle = RouteLineStyle.from(16f, Color.argb(200, 66, 133, 244))
        val routeLineStyles = RouteLineStyles.from(routeLineStyle)
        val segment = RouteLineSegment.from(points, routeLineStyles)
        val routeLineOptions = RouteLineOptions.from(listOf(segment))
        kakaoMap.routeLineManager?.layer?.addRouteLine(routeLineOptions)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.pause()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    data class PoiItem(
        val name: String,
        val description: String,
        val latLng: LatLng
    )
}
