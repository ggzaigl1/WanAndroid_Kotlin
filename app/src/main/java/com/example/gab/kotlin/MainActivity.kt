package com.example.gab.kotlin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.gab.kotlin.ui.PhotoViewActivity
import com.example.gab.kotlin.ui.fragment.*
import com.ggz.baselibrary.retrofit.ioc.ConfigUtils
import com.ggz.baselibrary.utils.*
import com.ggz.baselibrary.utils.cache.ACache
import kotlinx.android.synthetic.main.activity_app_bar_main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mHomeFragment: HomeFragment
    private lateinit var mViewFragment: ViewFragment
    private lateinit var mNavigationViewFragment: NavigationViewFragment
    private lateinit var mStarFragment: StarFragment
    private lateinit var mOfficialAccountFragment: OfficialAccountFragment

    private var mFragment: Fragment? = null
    private var exitTime: Long = 0
    private val time = 2000
    private val mTvNevHeaderLogin: TextView? = null
    private val mTvNevHeaderTitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
        if (!isTaskRoot) {
            val intent = intent
            val action = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action == Intent.ACTION_MAIN) {
                finish()
                return
            }
        }

        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        iv_head_right.visibility = View.VISIBLE
        mFragmentManager = supportFragmentManager
        //初始化主要的Fragment
        mHomeFragment = HomeFragment()
        mViewFragment = ViewFragment()
        mNavigationViewFragment = NavigationViewFragment()
        mStarFragment = StarFragment()
        mOfficialAccountFragment = OfficialAccountFragment()
        initBottomNavigation()
        switchContent(mHomeFragment)
        setSupportActionBar(toolbar)
        navigation.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //获取Navigation header控件方法1:
        val headerView = navigation.getHeaderView(0)
        val ivNevHeader = headerView.findViewById<AppCompatImageView>(R.id.nev_header_imageView)

        ivNevHeader.setOnClickListener {
            ivNevHeader.setOnClickListener { v -> JumpUtils.jumpFade(this@MainActivity, PhotoViewActivity::class.java, null) }
        }
        mTvNevHeaderLogin?.setOnClickListener { v ->
            val isLogin = SpfUtils.getSpfSaveBoolean(ConstantUtils.isLogin)
            if (isLogin) {
                MaterialDialog.Builder(this)
                        .cancelable(false)
                        .title(R.string.system_title)
                        .content(R.string.system_content)
                        .positiveText(R.string.ok)
                        .onPositive { dialog, which ->
                            nev_header_tv_title?.setText(R.string.notLogin)
                            nev_header_tv_login?.setText(R.string.clickLogin)
                            val mCache = ACache.get(ConfigUtils.getAppCtx())
                            mCache.clear()
                            SpfUtils.clear()
//                            mHomeFragment.mRefreshLayout.autoRefresh()
                        }.negativeText(R.string.cancel)
                        .onNegative { dialog, which -> dialog.dismiss() }.show()
            } else {
//                JumpUtils.jumpFade(this@MainActivity, LoginActivity::class.java, null)
            }
        }
    }

    private fun switchContent(fragment: Fragment) {
        if (mFragment != fragment) {
            if (!fragment.isAdded) {
                if (mFragment != null) {
                    mFragmentManager.beginTransaction()?.hide(mFragment)?.commit()
                }
                mFragmentManager.beginTransaction()?.add(R.id.fl_content, fragment)?.commit()
            } else {
                mFragmentManager.beginTransaction()?.hide(mFragment)?.show(fragment)?.commit()
            }
            mFragment = fragment
        }
    }

    private fun initBottomNavigation() {
        bottom_navigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setInActiveColor("#2c2c2c")
                .addItem(BottomNavigationItem(R.drawable.vector_home, getString(R.string.nav_home_title)).setActiveColorResource(R.color.pink))
                .addItem(BottomNavigationItem(R.drawable.vector_view_headline, getString(R.string.nav_system_title)).setActiveColorResource(R.color.pink))
                .addItem(BottomNavigationItem(R.drawable.vector_live_tv, getString(R.string.nav_view_title)).setActiveColorResource(R.color.pink))
                .addItem(BottomNavigationItem(R.drawable.vector_find, getString(R.string.nav_project_title)).setActiveColorResource(R.color.pink))
                .addItem(BottomNavigationItem(R.drawable.vector_official_account, getString(R.string.official_account)).setActiveColorResource(R.color.pink))
                .setFirstSelectedPosition(0)
                .setTabSelectedListener(this)
                .initialise()
    }


    override fun onResume() {
        super.onResume()
        val isLogin = SpfUtils.getSpfSaveBoolean(ConstantUtils.isLogin)
        mTvNevHeaderTitle?.text = if (isLogin) SpfUtils.getSpfSaveStr(ConstantUtils.userName) else ResourceUtils.getStr(R.string.notLogin)
        mTvNevHeaderLogin?.setText(if (isLogin) R.string.login_exit else R.string.clickLogin)
    }

    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
    }

    override fun onTabSelected(position: Int) {
        when (position) {
            0 -> switchContent(mHomeFragment)
            1 -> switchContent(mViewFragment)
            2 -> switchContent(mNavigationViewFragment)
            3 -> switchContent(mStarFragment)
            4 -> switchContent(mOfficialAccountFragment)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.nav_belle -> ToastUtils.showShort("111")
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            when {
                drawer_layout.isDrawerOpen(GravityCompat.START) -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                }
                (System.currentTimeMillis() - exitTime) > time -> {
                    ToastUtils.CustomToast.INSTANCE.showToast(R.string.exit_app)
                    exitTime = System.currentTimeMillis()
                }
                else -> {
                    finish()
                }
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
