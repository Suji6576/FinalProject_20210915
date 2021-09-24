package com.neppplus.finalproject_20210915.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_20210915.R
import com.neppplus.finalproject_20210915.databinding.FragmentInvitedAppointmentsListBinding

class SettingsFragment : BaseFragment() {

    companion object{
        private var frag : SettingsFragment? = null

        fun getFrag() : SettingsFragment {
            if (frag == null) {
                frag = SettingsFragment()
            }
            return frag!!
        }
    }

    lateinit var binding: FragmentInvitedAppointmentsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {

    }

    override fun setValues() {

    }

    override fun onResume() {
        super.onResume()
    }
}