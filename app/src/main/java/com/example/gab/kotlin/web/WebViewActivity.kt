package com.example.gab.kotlin.web

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.*
import android.webkit.*
import com.example.gab.kotlin.R
import com.example.gab.kotlin.base.BaseActivity
import com.ggz.baselibrary.application.IBaseActivity
import com.ggz.baselibrary.utils.ConstantUtils
import com.ggz.baselibrary.utils.SpfUtils
import com.ggz.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_webview.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.toast

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:50
 */
class WebViewActivity : BaseActivity(), IBaseActivity {

    private var mURl: String? = null
    private var mIsCollect: Boolean? = null
    private var toolbar: Toolbar? = null

    //懒加载:使用的时候才会初始化
    private val mWebView: WebView by lazy {
        web_view
    }

    //在kotlin中的静态方法和变量是用companion object包裹，调用的时候用到Companion
    companion object {
        private const val WEB_URL = "web_url"

        fun startWebActivity(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.apply {
                intent.putExtra(WEB_URL, url)
            }
            context.startActivity(intent)
        }
    }

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

    @SuppressLint("SetJavaScriptEnabled", "ObsoleteSdkInt")
    fun initView() {
        intent.let {
            mURl = it.getStringExtra(WEB_URL)
        }
        mWebView.settings.run {
            loadsImagesAutomatically = true
            domStorageEnabled = true
            javaScriptEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //两者都可以
                mixedContentMode = mixedContentMode
                //mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        mWebView.webChromeClient = object : WebChromeClient() {
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

        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                mWebView.loadUrl(url)
                return true
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                mWebView.visibility = View.GONE
                showError.visibility = View.VISIBLE
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (!mWebView.settings.loadsImagesAutomatically) {
                    mWebView.settings.loadsImagesAutomatically = true
                }
                val position = SpfUtils.getInt(this@WebViewActivity, url, 0)
                view.scrollTo(0, position)
            }
        }
        mWebView.loadUrl(mURl)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_web_more, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.web_share -> {
                share(mURl!!, "分享到")
//                AndroidShareUtils.shareAllMsg(ConfigUtils.getAppCtx(), "分享到", mURl, AndroidShareUtils.TEXT, null)
            }
            R.id.web_collection -> {
                if (TextUtils.isEmpty(SpfUtils.getSpfSaveStr(ConstantUtils.userName))) {
                    ToastUtils.showShort(R.string.collect_login)
                } else {
                    if (mIsCollect!!) {
                        toast("已经收藏")
                    }
                }
            }
            R.id.web_browser -> {
                browse(mURl!!)

//                val intent = Intent()
//                intent.run {
//                    data = Uri.parse(mURl)
//                    action = Intent.ACTION_VIEW
//                }
//                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
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
        val scrollY = mWebView.scrollY
        //保存访问的位置
        SpfUtils.putInt(this, mURl, scrollY)
    }

    override fun onDestroy() {
        mWebView.run {
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            removeAllViews()
            tag = null
            clearHistory()
            (parent as ViewGroup).removeView(mWebView)
            destroy()
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
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}