package com.example.my_telegram.ui.fragments

import androidx.fragment.app.Fragment
import com.example.my_telegram.MainActivity
import com.example.my_telegram.utils.APP_ACTIVITY


open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }

}