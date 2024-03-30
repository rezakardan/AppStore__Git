package com.example.appstore.ui.login

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.appstore.R
import com.example.appstore.data.model.login.BodyResponseLogin
import com.example.appstore.databinding.FragmentLoginBinding
import com.example.appstore.ui.MainActivity
import com.example.appstore.utils.IS_CALLED_VERIFY
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import com.example.appstore.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    lateinit var binding: FragmentLoginBinding


    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var body: BodyResponseLogin


    private val parentActivity by lazy { (activity as MainActivity) }

    var hashCode = ""
    var phone = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.bottomImg.load(R.drawable.bg_circle)








        hashCode = parentActivity.hashCode

        body.hashCode = hashCode

        binding.sendPhoneBtn.setOnClickListener {
            phone = binding.phoneEdt.text.toString()


            if (phone.length == 11) {

                body.login = phone





                if (isNetworkAvailable) {


                    IS_CALLED_VERIFY = true
                    loginViewModel.callLoginApi(body)


                }
            }


        }

        loadLoginApi()
        handleAnimation()
    }


    private fun handleAnimation() {


        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {

                lifecycleScope.launch {


                    delay(2000)

                    binding.animationView.playAnimation()


                }


            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })


    }

    private fun loadLoginApi() {

        loginViewModel.loginLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {

                is NetworkRequest.Loading -> {


                }

                is NetworkRequest.Error -> {


                    Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_SHORT).show()
                }


                is NetworkRequest.Success -> {


                    response.data?.let {

                        if (IS_CALLED_VERIFY) {
                            val direction =
                                LoginFragmentDirections.actionLoginFragmentToVerifyFragment()
                                    .setPhone(phone)


                            findNavController().navigate(direction)

                        }
                    }

                }


            }


        }


    }


}



