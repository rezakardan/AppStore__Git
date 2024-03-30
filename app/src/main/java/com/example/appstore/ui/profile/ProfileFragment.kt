package com.example.appstore.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.app.imagepickerlibrary.ImagePicker
import com.app.imagepickerlibrary.ImagePicker.Companion.registerImagePicker
import com.app.imagepickerlibrary.listener.ImagePickerResultListener
import com.app.imagepickerlibrary.model.PickExtension
import com.app.imagepickerlibrary.model.PickerType
import com.example.appstore.R
import com.example.appstore.databinding.FragmentProfileBinding
import com.example.appstore.utils.*
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.event.EventBus
import com.example.appstore.utils.event.Events
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.ProfileViewModel
import com.example.appstore.viewmodels.WalletViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.Utf8
import java.io.File
import java.net.URLEncoder


@AndroidEntryPoint
class ProfileFragment : BaseFragment(),ImagePickerResultListener {
    lateinit var binding: FragmentProfileBinding


    private val viewModel by activityViewModels<ProfileViewModel>()

    private val walletViewModel by viewModels<WalletViewModel>()


    private val imagePicker:ImagePicker by  lazy { registerImagePicker(this)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (isNetworkAvailable) {
            viewModel.callProfileApi()
        }


        binding.avatarEditImg.setOnClickListener {


            openImagePicker()


        }


        binding.menuLay.apply {



            menuEditLay.setOnClickListener { findNavController().navigate(R.id.action_To_ProfileEditFragment) }

            menuWalletLay.setOnClickListener { findNavController().navigate(R.id.action_To_IncreaseWallet) }

            menuCommentsLay.setOnClickListener { findNavController().navigate(R.id.action_To_Comments) }
            menuFavoritesLay.setOnClickListener { findNavController().navigate(R.id.action_To_Favorite) }
            menuAddressesLay.setOnClickListener { findNavController().navigate(R.id.action_To_ProfileAddress) }
        }

        binding.orderLay.apply {

            menuDeliveredLay.setOnClickListener {
                val direction=ProfileFragmentDirections.actionToProfileOrders(DELIVERED)
                findNavController().navigate(direction)
            }

            menuCanceledLay.setOnClickListener {
                val direction=ProfileFragmentDirections.actionToProfileOrders(CANCELED)
                findNavController().navigate(direction)
            }

            menuPendingLay.setOnClickListener {
                val direction=ProfileFragmentDirections.actionToProfileOrders(PENDING)
                findNavController().navigate(direction)
            }


        }


        lifecycleScope.launch {

        EventBus.observe<Events.IsUpdateProfile> {

            if (isNetworkAvailable) {

                viewModel.callProfileApi()
            }

        }

        }


        loadProfileApi()

        loadWalletApi()
        loadUploadingAvatar()
    }


    override fun onResume() {
        super.onResume()
        walletViewModel.getWalletApi()
    }

    private fun loadWalletApi() {

        walletViewModel.walletLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.infoLay.walletLoading.visibility = View.VISIBLE

                }

                is NetworkRequest.Success -> {
                    binding.infoLay.walletLoading.visibility = View.GONE

                    response.data?.let { data->



                        binding.infoLay.walletTxt.text=data.wallet.toString().toInt().moneySeparating()







                    }

                }

                is NetworkRequest.Error -> {
                    binding.infoLay.walletLoading.visibility = View.GONE
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }









    private fun loadProfileApi() {


        viewModel.profileLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                    binding.loading.visibility = View.VISIBLE
                    binding.scrollLay.visibility = View.GONE
                }


                is NetworkRequest.Success -> {
                    binding.loading.visibility = View.GONE
                    binding.scrollLay.visibility = View.VISIBLE


                    response.data?.let { data ->


                        if (data.avatar != null) {

                            binding.avatarImg.load(data.avatar)


                        } else {
                            binding.avatarImg.load(R.drawable.placeholder_user)
                        }






                        if (data.firstname.isNullOrEmpty().not()) {

                            binding.nameTxt.text = "${data.firstname} ${data.lastname}"


                        }


                        binding.infoLay.apply {

                            phoneTxt.text = data.cellphone




                            if (data.birthDate!!.isNotEmpty()) {


                                val birthDaySplit = data.birthDate.split("T")[0].replace("-", "/")


                                birthDateTxt.text = birthDaySplit


                            } else {
                                infoBirthDateLay.visibility = View.GONE
                                line2.visibility = View.GONE
                            }

                        }


                    }

                }


                is NetworkRequest.Error -> {
                    binding.loading.visibility = View.GONE
                    binding.scrollLay.visibility = View.VISIBLE
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }
    private fun openImagePicker(){


        imagePicker
            .title(getString(R.string.galleryImages))
            .multipleSelection(false)
            .showCountInToolBar(false)
            .showFolder(true)
            .cameraIcon(true)
            .doneIcon(true)
            .allowCropping(true)
            .compressImage(false)
            .maxImageSize(5)
            .extension(PickExtension.ALL)
        imagePicker.open(PickerType.GALLERY)




    }
    override fun onImagePick(uri: Uri?) {

       val imageFile= getRealFileFromUri(requireContext(),uri!!)?.let {path->
            File(path) }


        val multiPart=MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("_method","post")



        if (imageFile!=null){

val fileName=URLEncoder.encode(imageFile.absolutePath, "UTF-8")

            val requestFile=imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
multiPart.addFormDataPart("avatar",fileName,requestFile)
        }
      val requestBody= multiPart.build()

        if (isNetworkAvailable){

            viewModel.uploadAvatar(requestBody)


        }
    }

    override fun onMultiImagePick(uris: List<Uri>?) {
    }





    private fun loadUploadingAvatar(){

        viewModel.uploadAvatarLiveData.observe(viewLifecycleOwner){response->


            when(response){



                is NetworkRequest.Loading->{
                    binding.avatarLoading.visibility=View.VISIBLE
                }


                is NetworkRequest.Success->{
                    binding.avatarLoading.visibility=View.GONE

                    response.data?.let {

                        if (isNetworkAvailable) {
                            viewModel.callProfileApi()
                        }

                    }
                }


                is NetworkRequest.Error->{
                    binding.avatarLoading.visibility=View.GONE

                    Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()
                }




            }








        }




    }

}