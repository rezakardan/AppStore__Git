package com.example.appstore.ui.wallet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appstore.R
import com.example.appstore.data.model.wallet.BodyIncreaseWallet
import com.example.appstore.databinding.FragmentIncreaseWalletBinding
import com.example.appstore.utils.event.EventBus
import com.example.appstore.utils.event.Events
import com.example.appstore.utils.moneySeparating
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.WalletViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class IncreaseWalletFragment : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.RemoveBottomSheetBackground

    lateinit var binding: FragmentIncreaseWalletBinding


    @Inject
   lateinit var body:BodyIncreaseWallet


   private val viewModel by viewModels<WalletViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncreaseWalletBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeImg.setOnClickListener { this@IncreaseWalletFragment.dismiss() }




binding.amountEdt.addTextChangedListener {


    if (it.toString().isNotEmpty()) {


        binding.amountTxt.text = it.toString().trim().toInt().moneySeparating()


    }else{
        binding.amountTxt.text=""
    }
}

        binding.submitBtn.setOnClickListener {

            val amount = binding.amountEdt.text.toString()
if (amount.isNotEmpty()){

    body.amount=amount
    viewModel.callIncreaseWalletApi(body)

}





        }







        loadIncreaseWalletApi()
    }




    private fun loadIncreaseWalletApi(){

        viewModel.increaseWalletLiveData.observe(viewLifecycleOwner){response->



            when(response){



                is NetworkRequest.Loading->{

                }

                is NetworkRequest.Success->{
                    response.data?.let { data ->



                        val intent=Intent(Intent.ACTION_VIEW, Uri.parse(data.startPay))
                        startActivity(intent)

                        this@IncreaseWalletFragment.dismiss()

                    }
                }


                is NetworkRequest.Error->{
                    Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()
                }



            }









        }





    }



}