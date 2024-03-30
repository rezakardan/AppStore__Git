package com.example.appstore.ui.profile_Address

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentProfileAddressBinding
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.event.EventBus
import com.example.appstore.utils.event.Events
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.ProfileAddressViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileAddressFragment : BaseFragment() {


    lateinit var binding: FragmentProfileAddressBinding


    private val viewModel by viewModels<ProfileAddressViewModel>()

    @Inject
    lateinit var addressesAdapter:ProfileAddressesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.toolbar.toolbarBackImg.setOnClickListener { findNavController().popBackStack() }

        binding.toolbar.toolbarTitleTxt.text = getString(R.string.yourAddress)

        binding.toolbar.apply {

            toolbarOptionImg.setImageResource(R.drawable.location_plus)

            toolbarOptionImg.setOnClickListener {

                findNavController().navigate(R.id.action_profileAddressFragment_to_profileAddressAddOrEditFragment)

            }


        }


        if (isNetworkAvailable) {
            viewModel.callAddressesApi()
        }



        lifecycleScope.launch {




        EventBus.observe<Events.IsUpdateAddress> {



            viewModel.callAddressesApi()

        }

        }
        loadAddressesApi()
    }


    private fun loadAddressesApi() {


        viewModel.addressLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.addressList.showShimmer()

                }


                is NetworkRequest.Success -> {
                    binding.addressList.hideShimmer()
                    response.data?.let { data ->
if (data.isNotEmpty()){
    addressesAdapter.setData(data)
    initAddressesAdapter()

}else{

    binding.emptyLay.visibility=View.VISIBLE
    binding.addressList.visibility=View.GONE
}


                    }
                }


                is NetworkRequest.Error -> {
                    binding.addressList.hideShimmer()

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }


    private fun initAddressesAdapter(){

        binding.addressList.adapter=addressesAdapter

        binding.addressList.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)


addressesAdapter.setOnAddressClickListener {

val direction=ProfileAddressFragmentDirections.actionProfileAddressFragmentToProfileAddressAddOrEditFragment().setData(it)

findNavController().navigate(direction)


}



    }


}