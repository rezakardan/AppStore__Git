package com.example.appstore.ui.shipping

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.data.model.shipping.BodyCouponShipping
import com.example.appstore.data.model.shipping.BodySetAddress
import com.example.appstore.data.model.shipping.ResponseShippingList
import com.example.appstore.databinding.DialogChangeAddressBinding
import com.example.appstore.databinding.FragmentShippingBinding
import com.example.appstore.ui.shipping.adapters.AddressesAdapter
import com.example.appstore.ui.shipping.adapters.ShippingAdapter
import com.example.appstore.utils.ENABLE
import com.example.appstore.utils.PERCENT
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.moneySeparating
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.ShippingViewModel
import com.example.appstore.viewmodels.WalletViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShippingFragment : BaseFragment() {


    lateinit var binding: FragmentShippingBinding

    private val viewModel by viewModels<ShippingViewModel>()

    @Inject
    lateinit var shippingAdapter: ShippingAdapter

    @Inject
    lateinit var addressesAdapter: AddressesAdapter

    @Inject
    lateinit var setAddressBody: BodySetAddress


    @Inject
    lateinit var couponBody: BodyCouponShipping
    private val walletViewModel by viewModels<WalletViewModel>()

    private var finalPrice=0
    private var coupon=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShippingBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolbar.toolbarBackImg.setOnClickListener { findNavController().popBackStack() }

        binding.toolbar.toolbarOptionImg.visibility = View.GONE

        binding.toolbar.toolbarTitleTxt.text = getString(R.string.invoiceWithDeliveryPrice)


        if (isNetworkAvailable) {
            viewModel.callShippingApi()
        }







        loadShipping()
        loadWalletApi()

        loadCoupon()

        loadPaymentApi()
        binding.shippingDiscountLay.apply {

            checkTxt.setOnClickListener {


                if (codeEdt.text.length > 0) {

                 coupon=codeEdt.text.toString()
                    couponBody.couponId = coupon
                }

                if (isNetworkAvailable) {

                    viewModel.callCouponShippingApi(couponBody)
                }
            }


        }


        binding.shippingDiscountLay.codeEdt.setOnTouchListener { view, _ ->

            view.performClick()
            lifecycleScope.launch {
                delay(300)
                binding.scrollLay.fullScroll(View.FOCUS_DOWN)

            }

            false
        }

    }


    private fun loadShipping() {


        viewModel.shippingLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.containerGroup.visibility = View.GONE
                }


                is NetworkRequest.Success -> {
                    binding.loading.visibility = View.GONE
                    binding.containerGroup.visibility = View.VISIBLE

                    response.data?.let { data ->


                        initShippingViews(data)


                    }
                }


                is NetworkRequest.Error -> {
                    binding.loading.visibility = View.GONE
                    binding.containerGroup.visibility = View.VISIBLE
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }

    private fun initShippingViews(data: ResponseShippingList) {

        binding.apply {

            data.order?.let { data ->

                finalPrice=data.finalPrice.toString().toInt()
                invoiceTitle.text = data.finalPrice.toString().toInt().moneySeparating()
                shippingAdapter.setData(data.orderItems)






                initRecycler()

            }

            if (isNetworkAvailable) {
                walletViewModel.getWalletApi()
            }

            if (data.addresses.isNullOrEmpty().not()) {

                data.addresses?.get(0).let { address ->

                    shippingAddressLay.apply {

                        recipientNameTxt.text =
                            "${address!!.receiverFirstname} ${address!!.receiverLastname}"

                        locationTxt.text = address.postalAddress

                        phoneTxt.text = address.receiverCellphone

                    }
                    setAddressBody.addressId = address!!.id.toString()
                    if (isNetworkAvailable) {
                        viewModel.callSetAddressApi(setAddressBody)
                    }
                }
                if (data.addresses!!.size > 1) {

                    binding.shippingAddressLay.changeAddressTxt.apply {

                        visibility = View.VISIBLE

                        setOnClickListener {


                            showAddressDialog(data.addresses)


                        }

                    }


                }
            }
            binding.submitBtn.setOnClickListener {

if (data.addresses.isNullOrEmpty().not()) {
    if (isNetworkAvailable) {
        viewModel.callPaymentApi(couponBody)
    }
}else{
    Snackbar.make(binding.root,getString(R.string.addressCanNotBeEmpty),Snackbar.LENGTH_SHORT).show()
}

            }

        }


    }

    private fun showAddressDialog(list: List<ResponseShippingList.Addresse>) {
        val dialog = Dialog(requireContext())

        val dialogBinding = DialogChangeAddressBinding.inflate(layoutInflater)

        dialog.setContentView(dialogBinding.root)
        addressesAdapter.setData(list)

        dialogBinding.addressList.adapter = addressesAdapter
        dialogBinding.addressList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        addressesAdapter.onAddressesClickListener { address ->

            binding.shippingAddressLay.apply {

                recipientNameTxt.text = "${address.receiverFirstname} ${address.receiverLastname}"

                locationTxt.text = address.postalAddress

                phoneTxt.text = address.receiverCellphone


            }

            setAddressBody.addressId = address.id.toString()

            if (isNetworkAvailable) {
                viewModel.callSetAddressApi(setAddressBody)
            }

            dialog.dismiss()

        }
        dialog.show()
    }


    private fun initRecycler() {


        binding.productsList.adapter = shippingAdapter

        binding.productsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


    }


    private fun loadWalletApi() {


        walletViewModel.walletLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.walletLoading.visibility = View.VISIBLE
                    binding.walletTxt.visibility = View.GONE
                }


                is NetworkRequest.Success -> {
                    binding.walletLoading.visibility = View.GONE
                    binding.walletTxt.visibility = View.VISIBLE

                    response.data?.let { data ->


                        binding.walletTxt.text = data.wallet.toString().toInt().moneySeparating()


                    }
                }


                is NetworkRequest.Error -> {
                    binding.walletLoading.visibility = View.GONE
                    binding.walletTxt.visibility = View.VISIBLE
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }
        }


    }

    private fun loadCoupon() {

        viewModel.couponLiveData.observe(viewLifecycleOwner) { response ->

            binding.shippingDiscountLay.apply {

            when (response) {


                is NetworkRequest.Loading -> {
                    couponLoading.visibility = View.VISIBLE
                    checkTxt.visibility = View.GONE
                }


                is NetworkRequest.Success -> {
                    couponLoading.visibility = View.GONE
                   checkTxt.visibility = View.VISIBLE

                    response.data?.let { data ->

                       checkTxt.visibility = View.GONE
                        removeTxt.visibility=View.VISIBLE

                        couponTitle.text="${getString(R.string.discountCode)} (${data.title})"

                        if (data.status== ENABLE){

                            coupon=data.code!!
                            couponBody.couponId=coupon
                           val discountPrice= if (data.type== PERCENT){


                                finalPrice - (finalPrice* data.percent.toString().toInt())/100



                            }else{
                                finalPrice - data.percent.toString().toInt()
                            }
                            binding.invoiceTitle.text=discountPrice.toString().toInt().moneySeparating()
                            removeTxt.setOnClickListener {
                                checkTxt.visibility=View.VISIBLE
                                removeTxt.visibility=View.GONE
                                codeEdt.setText("")
                                couponTitle.text=getString(R.string.discountCode)
                                coupon=""
                                couponBody.couponId=null
                                binding.invoiceTitle.text=finalPrice.toString().toInt().moneySeparating()

                            }
                        }


                    }


                }

                is NetworkRequest.Error -> {
                    couponBody.couponId=null

                    couponLoading.visibility = View.GONE
                   checkTxt.visibility = View.VISIBLE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }


        }

        }
    }




    private fun loadPaymentApi(){

        viewModel.paymentLiveData.observe(viewLifecycleOwner){response->

            when(response){


                is NetworkRequest.Loading->{

                }


                is NetworkRequest.Success->{

response.data?.let { data->

if (data.startPay!=null) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.startPay))

    startActivity(intent)

}else{
Snackbar.make(binding.root,data.message.toString(),Snackbar.LENGTH_SHORT).show()
}
    findNavController().popBackStack(R.id.shippingFragment, true)

}
                }
                is NetworkRequest.Error->{
                    Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()
                }



            }






        }




    }
}