package com.harryportal.app

import android.app.Application
import com.kakao.vectormap.KakaoMapSdk

class HarryPortalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Kakao Maps SDK 초기화
        // 카카오 개발자 콘솔(https://developers.kakao.com)에서 발급받은 네이티브 앱 키를 입력하세요
        KakaoMapSdk.init(this, "YOUR_KAKAO_NATIVE_APP_KEY")
    }
}
