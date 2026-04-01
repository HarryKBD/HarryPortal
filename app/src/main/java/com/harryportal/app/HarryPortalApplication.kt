package com.harryportal.app

import android.app.Application

class HarryPortalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 네이버 Maps SDK는 AndroidManifest.xml의 CLIENT_ID 메타데이터로 자동 초기화됩니다.
    }
}
