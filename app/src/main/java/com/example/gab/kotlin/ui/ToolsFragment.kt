package com.example.gab.kotlin.ui

import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gab.kotlin.R

/**
 * Created by 初夏小溪
 * data :2019/1/14 0014 14:27
 */
class ToolsFragment : PreferenceFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        addPreferencesFromResource(R.xml.setting)
    }
}