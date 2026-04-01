package com.harryportal.app

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harryportal.app.databinding.ActivityWebPageBinding

class WebPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebPageBinding

    companion object {
        private const val TARGET_URL = "http://harrykbd.iptime.org:8988/exchange_rate/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "웹 페이지"

        setupWebView()
        loadUrl(TARGET_URL)
    }

    private fun setupWebView() {
        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = false
                displayZoomControls = false
                setSupportZoom(true)
            }

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                    binding.progressBar.visibility = android.view.View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.visibility = android.view.View.GONE
                    supportActionBar?.title = view?.title ?: "웹 페이지"
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    if (request?.isForMainFrame == true) {
                        binding.progressBar.visibility = android.view.View.GONE
                        showErrorPage()
                    }
                }
            }
        }
    }

    private fun loadUrl(url: String) {
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.webView.loadUrl(url)
    }

    private fun showErrorPage() {
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        font-family: 'Segoe UI', sans-serif;
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        justify-content: center;
                        min-height: 100vh;
                        margin: 0;
                        background: #f5f5f5;
                        color: #333;
                        text-align: center;
                        padding: 20px;
                        box-sizing: border-box;
                    }
                    .icon { font-size: 64px; margin-bottom: 20px; }
                    h2 { color: #e53935; margin-bottom: 12px; }
                    p { color: #666; font-size: 14px; line-height: 1.6; }
                    .url {
                        background: #fff;
                        border: 1px solid #ddd;
                        border-radius: 8px;
                        padding: 10px 16px;
                        margin-top: 16px;
                        font-family: monospace;
                        font-size: 13px;
                        color: #1565C0;
                    }
                </style>
            </head>
            <body>
                <div class="icon">🔌</div>
                <h2>서버에 연결할 수 없습니다</h2>
                <p>로컬 웹 서버가 실행 중인지 확인하세요.</p>
                <div class="url">http://localhost:8976</div>
                <p style="margin-top:20px; font-size:12px;">서버를 시작한 후 앱을 다시 실행해 주세요.</p>
            </body>
            </html>
        """.trimIndent()
        binding.webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
