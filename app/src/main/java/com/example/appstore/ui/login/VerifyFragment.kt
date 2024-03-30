package com.example.appstore.ui.login

import android.animation.Animator
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.appstore.R
import com.example.appstore.data.model.login.BodyResponseLogin
import com.example.appstore.data.stored.SessionManagerDataStore
import com.example.appstore.databinding.FragmentVerifyBinding
import com.example.appstore.utils.IS_CALLED_VERIFY
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.otp.SmsBroadCast
import com.example.appstore.viewmodels.LoginViewModel
import com.goodiebag.pinview.Pinview
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VerifyFragment : BaseFragment() {
    lateinit var binding: FragmentVerifyBinding


    private var intentFilter: IntentFilter? = null

    private val viewModel: LoginViewModel by viewModels()


    @Inject
    lateinit var smsBroadCast: SmsBroadCast


    @Inject
    lateinit var body: BodyResponseLogin

    @Inject
    lateinit var dataStore: SessionManagerDataStore

    private val args: VerifyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

IS_CALLED_VERIFY=false
        args.let {


            body.login = it.phone


        }
        binding.bottomImg.load(R.drawable.bg_circle)

        binding.pinView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))


        binding.pinView.setPinViewEventListener(object : Pinview.PinViewEventListener {
            override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {

                body.code = pinview?.value?.toInt()

                if (isNetworkAvailable) {

                    viewModel.verifyLogin(body)
                    handleTimer().cancel()
                }


            }
        })

        binding.sendAgainBtn.setOnClickListener {


            if (isNetworkAvailable) {

                viewModel.callLoginApi(body)
                handleTimer().cancel()

            }


        }



        lifecycleScope.launch {

            delay(500)

            handleTimer().start()

        }

        handleAnimation()
        loadLoginApi()
        loadVerifyData()
        initBroadCast()
        smsListener()

    }


    private fun loadLoginApi() {


        viewModel.loginLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {


                is NetworkRequest.Loading -> {}

                is NetworkRequest.Error -> {

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


                is NetworkRequest.Success -> {

                    response.data?.let {


                        handleTimer().start()


                    }


                }

            }


        }


    }


    private fun loadVerifyData() {

        viewModel.verifyLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {


                    binding.timerLay.alpha = 0.3f
                }

                is NetworkRequest.Error -> {
                    binding.timerLay.alpha = 1.0f
                    Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_SHORT).show()
                }

                is NetworkRequest.Success -> {
                    binding.timerLay.alpha = 1.0f
                    response.data?.let { data ->


                        lifecycleScope.launch {


                            dataStore.saveToken(data.accessToken.toString())


                        }


                        findNavController().popBackStack(R.id.verifyFragment, true)

                        findNavController().popBackStack(R.id.loginFragment, true)

                           findNavController().navigate(R.id.action_to_navMain)

                    }
                }

            }


        }


    }


    private fun handleTimer(): CountDownTimer {

        return object : CountDownTimer(60000, 1000) {
            override fun onTick(p0: Long) {
                binding.sendAgainBtn.visibility = View.GONE
                binding.timerTxt.visibility = View.VISIBLE
                binding.timerProgress.visibility = View.VISIBLE
                binding.timerTxt.text = "${p0 / 1000} ${getString(R.string.second)}"

                binding.timerProgress.setProgressCompat((p0 / 1000).toInt(), true)

                if (p0 < 1000) binding.timerProgress.progress = 0
            }

            override fun onFinish() {
                binding.sendAgainBtn.visibility = View.VISIBLE
                binding.timerTxt.visibility = View.GONE
                binding.timerProgress.visibility = View.GONE

                binding.timerProgress.progress = 0

            }


        }


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


    private fun initBroadCast() {

        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

        smsBroadCast.onReceiveMessage {


            val code = it.split(":")[1].trim().subSequence(0, 4)

            binding.pinView.value = code.toString()

        }


    }


    private fun smsListener() {

        val client = SmsRetriever.getClient(requireContext())
        client.startSmsRetriever()


    }


    override fun onResume() {
        super.onResume()

        requireContext().registerReceiver(smsBroadCast, intentFilter)
    }


    override fun onStop() {
        super.onStop()
        handleTimer().cancel()
        requireContext().unregisterReceiver(smsBroadCast)

    }

}

