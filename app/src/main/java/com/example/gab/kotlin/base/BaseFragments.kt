package com.example.gab.kotlin.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ggz.baselibrary.utils.LogUtils
import com.ggz.baselibrary.utils.cache.ACache
import com.kaopiz.kprogresshud.KProgressHUD
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.toast

/**
 * Created by 初夏小溪
 * data :2019/1/14 0014 10:41
 */
abstract class BaseFragments : Fragment(), AnkoLogger {

    private lateinit var mCache: ACache
    protected lateinit var mContext: AppCompatActivity

    var mKProgressHUD: KProgressHUD? = null
    var mRootView: View? = null
    //视图是否已经初始化完毕
    private var isViewReady: Boolean = false
    //fragment是否处于可见状态
    private var isFragmentVisible: Boolean = false
    //是否已经初始化加载过
    private var isLoaded: Boolean = false

    //是否使用懒加载 (Fragment可见时才进行初始化操作(以下四个方法))
    protected abstract fun isLazyLoad(): Boolean

//    protected abstract fun initView(view: View)

    protected abstract fun setContentLayout(): Int

    protected abstract fun initData()


    override fun onCreateView(@Nullable inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(setContentLayout(), container, false)
        } else {
            val parent = mRootView?.parent as ViewGroup
            parent.removeView(mRootView)
        }
        debug {
            "onCreateView()"
        }
//        LogUtils.e(TAG, "onCreateView()")
        return mRootView
    }

    /**
     * //当Activity中的onCreate方法执行完后调用
     *
     * @param savedInstanceState
     */
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewReady = true
        if (!isLazyLoad() || isFragmentVisible) {
            init()
        }
    }

    /**
     * tab切换一次，执行一次setUserVisibleHint()方法
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isFragmentVisible = isVisibleToUser
        //如果视图准备完毕且Fragment处于可见状态，则开始初始化操作
        if (isLazyLoad() && isViewReady && isFragmentVisible) {
            init()
        }
    }

    private fun init() {
        if (!isLoaded) {
            isLoaded = true
//            initView(mRootView!!)
            initData()
        }
    }

    fun onClick(view: View) {}

    /**
     * 子线程做处理
     */
    protected fun myToast(msg: String) {
        mContext.runOnUiThread { mContext.toast(msg) }
    }

    /**
     * Fragment生命周期管理
     *
     * @param hidden
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        // 不在最前端界面显示
        if (hidden) {
            onPause()
            // 重新显示到最前端中
        } else {
            onResume()
        }
    }

    /**
     * //Fragment和Activity建立关联的时候调用
     *
     * @param context
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        debug {
            "onAttach()"
        }
        this.mContext = (context as AppCompatActivity?)!!
        mCache = ACache.get(mContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debug {
            "onCreate()"
        }
        init()
    }


    override fun onStart() {
        super.onStart()
        debug {
            "onStart()"
        }
    }

    override fun onResume() {
        super.onResume()
        debug {
            "onResume()"
        }
    }

    override fun onPause() {
        super.onPause()
        debug {
            "onPause()"
        }
    }

    override fun onStop() {
        super.onStop()
        debug {
            "onStop()"
        }
    }

    /**
     * //Fragment中的布局被移除时调用
     */
    override fun onDestroyView() {
        super.onDestroyView()
        debug {
            "onDestroyView()"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        debug {
            "onDestroy()"
        }

        mKProgressHUD?.dismiss()
        isViewReady = false
        isLoaded = false
    }

    /**
     * Fragment和Activity解除关联的时候调用
     */
    override fun onDetach() {
        super.onDetach()
        debug {
            "onDetach()"
        }
    }
}