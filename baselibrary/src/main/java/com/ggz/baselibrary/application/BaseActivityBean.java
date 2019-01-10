package com.ggz.baselibrary.application;

import com.ggz.baselibrary.statuslayout.StatusLayoutManager;

import java.io.Serializable;

import io.reactivex.subjects.BehaviorSubject;

/**
 * 使用 ActivityLifecycleCallbacks 实现给所有 Activity 执行 ButterKnife.bind
 *
 * @author fangs
 * @date 2017/5/18
 */
public class BaseActivityBean implements Serializable {

    private BaseOrientationListener orientoinListener;

    public BaseOrientationListener getOrientoinListener() {
        return orientoinListener;
    }

    public void setOrientoinListener(BaseOrientationListener orientoinListener) {
        this.orientoinListener = orientoinListener;
    }

    private StatusLayoutManager slManager;

    public StatusLayoutManager getSlManager() {
        return slManager;
    }

    public void setSlManager(StatusLayoutManager slManager) {
        this.slManager = slManager;
    }

    private BehaviorSubject<String> subject;

    public BehaviorSubject<String> getSubject() {
        return subject;
    }

    public void setSubject(BehaviorSubject<String> subject) {
        this.subject = subject;
    }
}
