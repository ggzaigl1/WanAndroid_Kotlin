package com.example.gab.kotlin.web

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.*
import android.webkit.*
import com.example.gab.kotlin.R
import com.ggz.baselibrary.application.IBaseActivity
import com.ggz.baselibrary.retrofit.ioc.ConfigUtils
import com.ggz.baselibrary.utils.*
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:50
 */
class WebViewActivity : AppCompatActivity(), IBaseActivity {

    private var mURl: String? = null
    private var mIsCollect: Boolean? = null
    private var toolbar: Toolbar? = null

    override fun isShowHeadView(): Boolean {
        return true
    }

    override fun setView(): Int {
        return R.layout.activity_webview
    }

    override fun setStatusBar(activity: Activity?) {
    }

    override fun initData(activity: Activity?, savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        initView()
    }

    override fun onClick(v: View?) {
    }

    override fun reTry() {
    }

    //在kotlin中的静态方法和变量是用companion object包裹，调用的时候用到Companion
    companion object {
        private const val WEB_URL = "web_url"
        private const val WEB_ID = "web_id"
        private const val IS_COLLECT = "is_collect"

        fun startWebActivity(context: Context, url: String, id: Int, isCollect: Boolean) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(WEB_URL, url)
            intent.putExtra(WEB_ID, id)
            intent.putExtra(IS_COLLECT, isCollect)
            context.startActivity(intent)
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "ObsoleteSdkInt")
    fun initView() {
        mURl = intent.getStringExtra(WEB_URL)
//        mURl = intent.getStringExtra("Link")
        web_view.settings.loadsImagesAutomatically = true
        web_view.isHorizontalScrollBarEnabled = false
        web_view.isVerticalScrollBarEnabled = false
        web_view.loadUrl(mURl)

        val webSettings = web_view.settings
        webSettings.domStorageEnabled = true
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            webSettings.mixedContentMode = webSettings.mixedContentMode
            //mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        web_view.webViewClient = WebViewClient()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        web_view.webChromeClient = object : WebChromeClient() {
            //用网页的标题来设置自己的标题栏
            override fun onReceivedTitle(view: WebView, title: String?) {
                super.onReceivedTitle(view, title)
                if (null != title) {
                    toolbar.title = title
                } else {
                    toolbar.title = "资讯新闻"
                }
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress != 0) {
                    if (newProgress == 100) {
                        //加载完网页进度条消失
                        progressBar1.visibility = View.GONE
                    } else {
                        //开始加载网页时显示进度条
                        progressBar1.visibility = View.VISIBLE
                        //设置进度值
                        progressBar1.progress = newProgress
                    }
                } else {
                    progressBar1.visibility = View.GONE
                }
            }
        }

        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                web_view.loadUrl(url)
                return true
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                web_view.visibility = View.GONE
                showError.visibility = View.VISIBLE
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (!web_view.settings.loadsImagesAutomatically) {
                    web_view.settings.loadsImagesAutomatically = true
                }
                val position = SpfUtils.getInt(this@WebViewActivity, url, 0)
                view.scrollTo(0, position)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_web_more, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.web_share -> {
                AndroidShareUtils.shareAllMsg(ConfigUtils.getAppCtx(), "一起玩Android", mURl, AndroidShareUtils.TEXT, null)
            }
            R.id.web_collection -> {
                if (TextUtils.isEmpty(SpfUtils.getSpfSaveStr(ConstantUtils.userName))) {
                    T.showShort(R.string.collect_login)
                } else {
                    if (mIsCollect!!) {
                        T.showShort("已经收藏")
                    }
                }
            }
            R.id.web_browser -> {
                val intent = Intent()
                intent.data = Uri.parse(mURl)
                intent.action = Intent.ACTION_VIEW
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
//         if (menu != null) {
//            if ("MenuBuilder".equalsIgnoreCase(menu.getClass().getSimpleName())) {
//                try {
//                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
//                    method.setAccessible(true);
//                    method.invoke(menu, true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        if (menu != null) {
            if ("MenuBuilder".equals(menu.javaClass.simpleName, ignoreCase = true)) {
                try {
                    val method = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                    method.isAccessible = true
                    method.invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return super.onMenuOpened(featureId, menu)
    }

    override fun onPause() {
        super.onPause()
        if (web_view != null) {
            val scrollY = web_view.scrollY
            //保存访问的位置
            SpfUtils.putInt(this, mURl, scrollY)
        }
    }

    override fun onDestroy() {
        if (web_view != null) {
            web_view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            web_view.removeAllViews()
            web_view.tag = null
            web_view.clearHistory()
            (web_view.parent as ViewGroup).removeView(web_view)
            web_view.destroy()
        }
        super.onDestroy()
    }

    /**
     * 改写物理按键——返回的逻辑
     *
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}