package com.harryportal.app.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * 대시보드 메뉴 아이템 모델
 * 새 메뉴 추가 시 MainActivity.kt 의 menuItems 리스트에 항목을 추가하세요.
 */
data class MenuItem(
    val id: Int,
    val title: String,
    val description: String,
    @DrawableRes val iconRes: Int,
    @ColorRes val colorRes: Int,
    val activityClass: Class<*>
)
