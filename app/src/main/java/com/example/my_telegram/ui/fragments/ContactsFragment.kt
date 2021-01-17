package com.example.my_telegram.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my_telegram.R
import com.example.my_telegram.utils.APP_ACTIVITY

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Контакты"
    }

}