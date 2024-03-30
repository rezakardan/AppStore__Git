package com.example.appstore.ui.profile_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentProfileOrdersBinding
import com.example.appstore.ui.profile_orders.adapters.OrdersAdapter
import com.example.appstore.utils.CANCELED
import com.example.appstore.utils.DELIVERED
import com.example.appstore.utils.PENDING
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.ProfileOrderViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileOrdersFragment : BaseFragment() {


    private val viewModel by viewModels<ProfileOrderViewModel>()

    lateinit var binding: FragmentProfileOrdersBinding

    private val args by navArgs<ProfileOrdersFragmentArgs>()


    @Inject
    lateinit var orderAdapter: OrdersAdapter
    private var status = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.statusorder.let {

            status = it

        }

        if (isNetworkAvailable) {
            viewModel.callProfileOrderApi(status)
        }

        binding.toolbar.apply {

            toolbarTitleTxt.text = when (status) {


                DELIVERED -> {
                    getString(R.string.delivered)
                }

                CANCELED -> {
                    getString(R.string.canceled)
                }

                PENDING -> {
                    getString(R.string.pending)
                }

                else -> ""


            }

            toolbarBackImg.setOnClickListener { findNavController().popBackStack() }

            toolbarOptionImg.visibility = View.GONE

        }



        loadOrdersApi()
    }


    private fun loadOrdersApi() {


        viewModel.profileOrderLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.ordersList.showShimmer()
                }


                is NetworkRequest.Success -> {
                    binding.ordersList.hideShimmer()
                    response.data?.let { data ->

                        if (data.data.isNotEmpty()) {

                            orderAdapter.setData(data.data)
                            initOrderAdapter()

                        } else {

                            binding.emptyLay.visibility = View.VISIBLE
                            binding.ordersList.visibility = View.GONE
                        }


                    }
                }


                is NetworkRequest.Error -> {
                    binding.ordersList.hideShimmer()

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }


        }
    }


    private fun initOrderAdapter() {

        binding.ordersList.adapter = orderAdapter

        binding.ordersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


    }
}