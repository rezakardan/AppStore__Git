package com.example.appstore.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.appstore.BuildConfig
import com.example.appstore.R
import com.example.appstore.data.stored.SessionManagerDataStore
import com.example.appstore.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding


    @Inject
    lateinit var dataStore: SessionManagerDataStore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBottom.load(R.drawable.bg_circle)

        binding.txtVersion.text = "${getString(R.string.version)} : ${BuildConfig.VERSION_NAME}"




        checkAnimation()


    }


    private fun checkAnimation() {

        binding.motionLay.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {


                lifecycleScope.launch {


                    val token = dataStore.getToken.first()

                    Log.e("usertoken",token.toString())

                    findNavController().popBackStack(R.id.splashFragment,true)
                    if (token == null) {

                        findNavController().navigate(R.id.action_splashFragment_to_navLogin)
                    } else {


                        findNavController().navigate(R.id.action_to_navMain)

                    }


                }


            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {


            }
        })


    }


}