package com.example.appstore.ui.cart

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.R
import com.example.appstore.databinding.FragmentCartBinding
import com.example.appstore.utils.DECREMENT
import com.example.appstore.utils.DELETECART
import com.example.appstore.utils.INCREMENT
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.event.EventBus
import com.example.appstore.utils.event.Events
import com.example.appstore.utils.moneySeparating
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    lateinit var binding: FragmentCartBinding


    private val viewModel by viewModels<CartViewModel>()


    @Inject
    lateinit var adapter: CartAdapter

    private var cartContinueClicked=false

    private var recyclerState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (isNetworkAvailable) {
            viewModel.getCartList()
        }


        binding.cartsList.addOnScrollListener(object:RecyclerView.OnScrollListener(){


            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy>0 && binding.continueFABtn.isExtended){

                    binding.continueFABtn.shrink()


                }else if (dy<0 && !binding.continueFABtn.isExtended){
                    binding.continueFABtn.extend()
                }





            }





        })


        loadCartList()

        loadIncrementCart()
        loadDecrementCart()
        loadDeleteCart()

        loadContinueCart()

    }


    private fun loadCartList() {


        viewModel.getCartListLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }

                is NetworkRequest.Success -> {

                    binding.loading.visibility = View.GONE
                    response.data?.let { data ->


                        if (data.orderItems != null) {
                            if (data.orderItems.isNotEmpty()) {
                                binding.cartsList.visibility = View.VISIBLE
                                binding.emptyLay.visibility = View.GONE
                                binding.toolbarPriceTxt.text =
                                    data.itemsPrice.toString().toInt().moneySeparating()
                                binding.continueFABtn.visibility=View.VISIBLE

                                binding.continueFABtn.setOnClickListener {

cartContinueClicked=true
                                    if (isNetworkAvailable){

                                        viewModel.callCartContinueApi()

                                    }



                                }
                                adapter.setData(data.orderItems)

                                initRecycler()


                            } else {
                                binding.toolbarPriceTxt.text = "0 ${getString(R.string.toman)}"
                                binding.continueFABtn.visibility=View.GONE

                                binding.cartsList.visibility = View.GONE
                                binding.emptyLay.visibility = View.VISIBLE

                            }
                        } else {
                            binding.toolbarPriceTxt.text = "0 ${getString(R.string.toman)}"

                            binding.cartsList.visibility = View.GONE
                            binding.emptyLay.visibility = View.VISIBLE
                        }


                    }
                }

                is NetworkRequest.Error -> {
                    binding.loading.visibility = View.GONE
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }


    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            EventBus.publish(Events.IsUpdateCart)
        }
    }


    private fun initRecycler() {
        binding.cartsList.adapter = adapter
        binding.cartsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



        binding.cartsList.layoutManager?.onRestoreInstanceState(recyclerState)


        adapter.onItemClickListener { id, type ->


            recyclerState = binding.cartsList.layoutManager?.onSaveInstanceState()


            when (type) {

                INCREMENT -> {
                    if (isNetworkAvailable)
                        viewModel.callIncrementCartApi(id)
                }


                DECREMENT -> {
                    if (isNetworkAvailable)

                        viewModel.callDecrementCartApi(id)
                }

                DELETECART -> {
                    if (isNetworkAvailable)

                        viewModel.callDeleteCartApi(id)
                }


            }


        }


    }


    private fun loadIncrementCart() {

        viewModel.incrementCartLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is NetworkRequest.Loading -> {

                    binding.loading.visibility = View.VISIBLE
                }


                is NetworkRequest.Success -> {
                    binding.loading.visibility = View.GONE

                    response.data?.let { data ->

                        if (isNetworkAvailable) {
                            viewModel.getCartList()
                        }


                        lifecycleScope.launch {


                            EventBus.publish(Events.IsUpdateCart)


                        }
                    }
                }

                is NetworkRequest.Error -> {
                    binding.loading.visibility = View.GONE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }


    private fun loadDecrementCart() {

        viewModel.decrementCartLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is NetworkRequest.Loading -> {

                    binding.loading.visibility = View.VISIBLE
                }


                is NetworkRequest.Success -> {
                    binding.loading.visibility = View.GONE

                    response.data?.let { data ->

                        if (isNetworkAvailable) {
                            viewModel.getCartList()
                        }


                        lifecycleScope.launch {


                            EventBus.publish(Events.IsUpdateCart)

                        }
                    }
                }

                is NetworkRequest.Error -> {
                    binding.loading.visibility = View.GONE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }


    private fun loadDeleteCart() {

        viewModel.deleteCartLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is NetworkRequest.Loading -> {

                    binding.loading.visibility = View.VISIBLE
                }


                is NetworkRequest.Success -> {
                    binding.loading.visibility = View.GONE

                    response.data?.let { data ->

                        if (isNetworkAvailable) {
                            viewModel.getCartList()
                        }

                        lifecycleScope.launch {


                            EventBus.publish(Events.IsUpdateCart)

                        }

                    }
                }

                is NetworkRequest.Error -> {
                    binding.loading.visibility = View.GONE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }


    private fun loadContinueCart() {

        viewModel.cartContinueLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is NetworkRequest.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }


                is NetworkRequest.Success -> {
                    binding.loading.visibility = View.GONE

                    response.data?.let { data ->


                        if (cartContinueClicked) {

                            findNavController().navigate(R.id.actionToShipping)
                        }
                        cartContinueClicked=false

                    }

                }


                is NetworkRequest.Error -> {
                    binding.loading.visibility = View.GONE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }
        }


    }


}