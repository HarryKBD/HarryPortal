package com.harryportal.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.harryportal.app.adapter.MenuAdapter
import com.harryportal.app.databinding.ActivityMainBinding
import com.harryportal.app.model.MenuItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * 대시보드 메뉴 목록
     * 새 메뉴 추가: 리스트에 MenuItem(...) 항목을 추가하세요 (최대 10개)
     * 메뉴 제거: 해당 항목을 리스트에서 삭제하세요
     */
    private val menuItems = listOf(
        MenuItem(
            id = 1,
            title = "웹 페이지",
            description = "로컬 웹 서버\nlocalhost:8976",
            iconRes = R.drawable.ic_web,
            colorRes = R.color.menu_color_web,
            activityClass = WebPageActivity::class.java
        ),
        MenuItem(
            id = 2,
            title = "네이버 지도",
            description = "서울 주요 명소\n지도 보기",
            iconRes = R.drawable.ic_map,
            colorRes = R.color.menu_color_map,
            activityClass = NaverMapActivity::class.java
        )
        // 새 메뉴를 여기에 추가하세요. 예:
        // MenuItem(
        //     id = 3,
        //     title = "메뉴 이름",
        //     description = "메뉴 설명",
        //     iconRes = R.drawable.ic_your_icon,
        //     colorRes = R.color.menu_color_3,
        //     activityClass = YourNewActivity::class.java
        // ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val spanCount = if (menuItems.size == 1) 1 else 2
        binding.rvMenu.layoutManager = GridLayoutManager(this, spanCount)
        binding.rvMenu.adapter = MenuAdapter(menuItems)
    }
}
