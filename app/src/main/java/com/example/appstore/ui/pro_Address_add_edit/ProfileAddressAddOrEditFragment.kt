package com.example.appstore.ui.pro_Address_add_edit

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appstore.R
import com.example.appstore.data.profile_address.BodyResponseInsertOrUpdate
import com.example.appstore.databinding.DialogDeleteAddressBinding
import com.example.appstore.databinding.FragmentProfileAddressAddOrEditBinding
import com.example.appstore.ui.profile_Address.ProfileAddressFragment
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
class ProfileAddressAddOrEditFragment : BaseFragment() {


    lateinit var binding: FragmentProfileAddressAddOrEditBinding

@Inject
    lateinit var body: BodyResponseInsertOrUpdate

    private val viewModel by viewModels<ProfileAddressViewModel>()

    private val provincesNameList = mutableListOf<String>()
    private var provinceId = 0

    private val citiesNameList = mutableListOf<String>()


    private lateinit var cityAdapter: ArrayAdapter<String>

    private var cityId = 0

    private val args by navArgs<ProfileAddressAddOrEditFragmentArgs>()

    private var addressId=0
    private lateinit var provincesAdapter: ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileAddressAddOrEditBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isNetworkAvailable) {

            viewModel.callProvinceAddressApi()


        }















        binding.toolbar.toolbarBackImg.setOnClickListener {
            findNavController().popBackStack()
        }


        if (args.data!=null){

            binding.toolbar.toolbarTitleTxt.text =getString(R.string.editAddress)

            binding.toolbar.toolbarOptionImg.setImageResource(R.drawable.trash_can)
            binding.toolbar.toolbarOptionImg.setColorFilter(ContextCompat.getColor(requireContext(),R.color.red))
            binding.toolbar.toolbarOptionImg.setOnClickListener {


                showDeleteAddressDialog()



            }
args.data?.apply {
    addressId=id!!
    body.addressId=id.toString()
            binding.nameEdt.setText(receiverFirstname)
            binding.familyEdt.setText(receiverLastname)
            binding.phoneEdt.setText(receiverCellphone)
    body.provinceId=province?.id.toString()
            binding.provinceAutoTxt.setText(province?.title)
    binding.cityInpLay.visibility=View.VISIBLE
    body.cityId=city?.id.toString()
            binding.cityAutoTxt.setText(city?.title)
            binding.floorEdt.setText(floor)
            binding.plateEdt.setText(plateNumber)
            binding.postalEdt.setText(postalCode)
            binding.addressEdt.setText(postalAddress)
}
        }else{

            binding.toolbar.toolbarTitleTxt.text = getString(R.string.addNewAddress)

            binding.toolbar.toolbarOptionImg.visibility = View.GONE
        }


        binding.submitBtn.setOnClickListener {


            body.receiverFirstname = binding.nameEdt.text.toString()

            body.receiverLastname = binding.familyEdt.text.toString()
            body.receiverCellphone = binding.phoneEdt.text.toString()
            body.floor = binding.floorEdt.text.toString()

            body.plateNumber = binding.plateEdt.text.toString()
            body.postalCode = binding.postalEdt.text.toString()
            body.postalAddress = binding.addressEdt.text.toString()
            body.latitude="5"
            body.longitude="4"

            if (isNetworkAvailable) {
                viewModel.callInsertOrUpdateAddressApi(body)
            }

        }




        loadProvinceApi()
        loadCityApi()
        loadInsertOrUpdateAddressApi()
        loadDeleteAddressApi()
    }

private fun loadDeleteAddressApi(){

    viewModel.deleteAddressLiveData.observe(viewLifecycleOwner){response->


        when(response){

            is NetworkRequest.Loading->{

            }

            is NetworkRequest.Success->{
response.data?.let {



                findNavController().popBackStack()
                lifecycleScope.launch {
                EventBus.publish(Events.IsUpdateAddress)
            }
            }
            }
            is NetworkRequest.Error->{
                Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()
            }




        }





    }





}
    private fun loadInsertOrUpdateAddressApi() {

        viewModel.insertOrUpdateAddressLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                }


                is NetworkRequest.Success -> {
                    response.data?.let { data ->


                        lifecycleScope.launch {
                            EventBus.publish(Events.IsUpdateAddress)

                        }

                        findNavController().popBackStack()


                    }
                }


                is NetworkRequest.Error -> {
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }


    private fun loadProvinceApi() {


        viewModel.provinceLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                }

                is NetworkRequest.Success -> {
                    response.data?.let { data ->


                        if (data.isNotEmpty()) {


                            data.forEach {

                                provincesNameList.add(it.title!!)


                            }

                            provincesAdapter = ArrayAdapter<String>(requireContext(),R.layout.dropdown_menu_popup_item,provincesNameList)
                            binding.provinceAutoTxt.apply {

                                setAdapter(provincesAdapter)

                                setOnItemClickListener { _, _, position, _ ->

                                    provinceId = data[position].id!!
                                    body.provinceId = provinceId.toString()

                                    if (isNetworkAvailable) {
                                        viewModel.callCityApi(provinceId)
                                    }
                                }


                            }
                        }


                    }
                }

                is NetworkRequest.Error -> {
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }


    private fun loadCityApi() {

        viewModel.cityLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is NetworkRequest.Loading -> {

                }


                is NetworkRequest.Success -> {


                    response.data?.let { data ->
                        binding.cityInpLay.visibility = View.VISIBLE
                        if (data.isNotEmpty()) {
                            citiesNameList.clear()
                            data.forEach {

                                citiesNameList.add(it.title!!)


                            }


                            cityAdapter = ArrayAdapter<String>(
                                requireContext(),
                                R.layout.dropdown_menu_popup_item,
                                citiesNameList
                            )
                            binding.cityAutoTxt.apply {

                                setAdapter(cityAdapter)
                                setOnItemClickListener { _, _, position, _ ->

                                    cityId = data[position].id!!

                                    body.cityId = cityId.toString()

                                }


                            }

                        }


                    }


                }


                is NetworkRequest.Error -> {
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }

private fun showDeleteAddressDialog(){


val dialog=Dialog(requireContext())
    val dialogBinding=DialogDeleteAddressBinding.inflate(layoutInflater)

    dialog.setContentView(dialogBinding.root)

    dialogBinding.yesBtn.setOnClickListener {
        dialog.dismiss()
        if (isNetworkAvailable) {
            viewModel.callDeleteAddressApi(addressId)
        }



    }
    dialogBinding.noBtn.setOnClickListener { dialog.dismiss() }

    dialog.show()



}
}