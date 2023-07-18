package com.example.nupyandroid.feature.login

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.example.nupyandroid.R
import com.example.nupyandroid.base.BaseFragment
import com.example.nupyandroid.databinding.FragmentLoginBinding
import com.example.nupyandroid.feature.MainViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun initViews() {
        binding.btnLogin.setOnClickListener {
            activityViewModel.setFlow(MainViewModel.Flow.Login)
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}