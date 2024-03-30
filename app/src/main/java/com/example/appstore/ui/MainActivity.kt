package com.example.appstore.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appstore.R
import com.example.appstore.data.stored.SessionManagerDataStore
import com.example.appstore.databinding.ActivityMainBinding
import com.example.appstore.utils.base.BaseActivity
import com.example.appstore.utils.event.EventBus
import com.example.appstore.utils.event.Events
import com.example.appstore.utils.network.NetworkChecker
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.otp.AppSignatureHelper
import com.example.appstore.viewmodels.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var navHost: NavHostFragment


    @Inject
    lateinit var sessionManager: SessionManagerDataStore


    @Inject
    lateinit var networkChecker: NetworkChecker

    private var isNetworkAvailable = true


    private val cartViewModel by viewModels<CartViewModel>()

    @Inject
    lateinit var appSignature: AppSignatureHelper

    var hashCode = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        binding.bottomNavi.setupWithNavController(navHost.navController)

        binding.bottomNavi.setOnNavigationItemReselectedListener { }

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.homeFragment || destination.id == R.id.cartFragment || destination.id == R.id.categoryFragment || destination.id == R.id.profileFragment) {

                binding.bottomNavi.visibility = View.VISIBLE


            } else {


                binding.bottomNavi.visibility = View.GONE


            }


        }






        appSignature.appSignatures.forEach {

            hashCode = it

            Log.e("hashCodeLog", "hashCode: $hashCode")


        }



        lifecycleScope.launch {

            EventBus.observe<Events.IsUpdateCart> {

                cartViewModel.getCartList()




            }



        }

        lifecycleScope.launch{
        networkChecker.network().collect{

isNetworkAvailable=it

        }

        }


        lifecycleScope.launch {
delay(200)
            sessionManager.getToken.collect{token->


                token?.let {


                    if (isNetworkAvailable){

                        cartViewModel.getCartList()
                    }


                }





            }


        }


        loadCartList()
    }


    private fun loadCartList(){

        cartViewModel.getCartListLiveData.observe(this@MainActivity){response->



            when(response){


                is NetworkRequest.Loading->{

                }

                is NetworkRequest.Success->{


response.data?.let { data->


    if (data.quantity!=null) {


        if (data.quantity.toString().toInt()>0){

            binding.bottomNavi.getOrCreateBadge(R.id.cartFragment).apply {

                number=data.quantity.toString().toInt()

                backgroundColor=ContextCompat.getColor(this@MainActivity,R.color.caribbeanGreen)

            }

        }else{
            binding.bottomNavi.removeBadge(R.id.cartFragment)


        }


    }else{

        binding.bottomNavi.removeBadge(R.id.cartFragment)
    }


}



                }


                is NetworkRequest.Error->{
                    Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()
                }




            }







        }





    }






    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }


}