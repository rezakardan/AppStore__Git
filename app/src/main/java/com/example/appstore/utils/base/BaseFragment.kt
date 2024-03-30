package com.example.appstore.utils.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appstore.R
import com.example.appstore.utils.network.NetworkChecker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
abstract class BaseFragment : Fragment() {


    @Inject
    lateinit var network: NetworkChecker


    var isNetworkAvailable: Boolean = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        lifecycleScope.launch {


            network.network().collect {


                isNetworkAvailable = it

                if (!it) {


                    Toast.makeText(requireContext(), R.string.checkYourNetwork, Toast.LENGTH_SHORT)
                        .show()

                }

            }

        }

    }


}