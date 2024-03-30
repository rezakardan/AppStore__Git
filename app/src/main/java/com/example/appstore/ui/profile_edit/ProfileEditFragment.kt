package com.example.appstore.ui.profile_edit

import android.R.attr.typeface
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.appstore.R
import com.example.appstore.data.model.profile.BodyUpdateProfile
import com.example.appstore.databinding.FragmentProfileEditBinding
import com.example.appstore.utils.event.EventBus
import com.example.appstore.utils.event.Events
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileEditFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentProfileEditBinding


    override fun getTheme() = R.style.RemoveBottomSheetBackground

private val viewModel by viewModels<ProfileViewModel>()
@Inject
    lateinit var body:BodyUpdateProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

binding.closeImg.setOnClickListener { this@ProfileEditFragment.dismiss()}



        viewModel.callProfileApi()

binding.submitBtn.setOnClickListener {

    if (binding.nameEdt.text.isNullOrEmpty().not()) {
        body.firstName = binding.nameEdt.text.toString()
    }
    if (binding.familyEdt.text.isNullOrEmpty().not()) {
        body.lastName = binding.familyEdt.text.toString()
    }
    if (binding.idNumberEdt.text.isNullOrEmpty().not()) {
        body.idNumber = binding.idNumberEdt.text.toString()

    }


viewModel.updateProfile(body)


}

        binding.birthDateEdt.setOnTouchListener { _, motionEvent ->

           if (motionEvent.action==MotionEvent.ACTION_DOWN){


               openDatePicker()





           }


            false




        }



        loadProfileApi()
        loadingUpdateProfile()
    }




    private fun openDatePicker(){


        PersianDatePickerDialog(requireContext())

            .setTodayButtonVisible(true)
            .setMinYear(1300)
            .setMaxYear(1430)

            .setInitDate(1360, 3, 13)
            .setTitleType(PersianDatePickerDialog.DAY_MONTH_YEAR)
            .setShowInBottomSheet(true)
            .setListener(object : PersianPickerListener {
                override fun onDateSelected(persianPickerDate: PersianPickerDate) {

                    val birthDateMilady="${persianPickerDate.gregorianYear}-${persianPickerDate.gregorianMonth}-${persianPickerDate.gregorianDay}"

                    val birthDatePersian="${persianPickerDate.persianYear}-${persianPickerDate.persianMonth}-${persianPickerDate.persianDay}"

               body.gregorianDate=birthDateMilady

                    binding.birthDateEdt.setText(birthDatePersian)


                }

                override fun onDismissed() {}
            }).show()



    }



    private fun loadingUpdateProfile(){


        viewModel.updateProfileLiveData.observe(viewLifecycleOwner){response->

            when(response){


                is NetworkRequest.Loading->{
                    binding.loading.visibility=View.VISIBLE
                }

                is NetworkRequest.Success->{
                    binding.loading.visibility=View.GONE

                    response.data?.let { data->

                        lifecycleScope.launch {

EventBus.publish(Events.IsUpdateProfile)


                        }


                        this@ProfileEditFragment.dismiss()






                    }

                }

                is NetworkRequest.Error->{
                    binding.loading.visibility=View.GONE

                    Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()
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

response.data?.let { data->

    if (data.firstname!!.isNotEmpty()){


        binding.nameEdt.setText(data.firstname)



    }
    if (data.lastname.isNullOrEmpty().not()){

        binding.familyEdt.setText(data.lastname)



    }

    if (data.idNumber.isNullOrEmpty().not()){


        binding.idNumberEdt.setText(data.idNumber)
    }


    if (data.birthDate.isNullOrEmpty().not()){

val birthDateSplit=data.birthDate!!.split("T")[0].replace("-","/")
        binding.birthDateEdt.setText(birthDateSplit)
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


}