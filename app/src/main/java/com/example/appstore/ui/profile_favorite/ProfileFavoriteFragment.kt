package com.example.appstore.ui.profile_favorite

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentProfileFavoriteBinding
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFavoriteFragment : BaseFragment() {

    lateinit var binding: FragmentProfileFavoriteBinding


    private val viewModel by viewModels<FavoriteViewModel>()


    private var recyclerViewState:Parcelable?=null


    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isNetworkAvailable) {
            viewModel.getFavorites()
        }
        binding.toolbar.toolbarTitleTxt.text = getString(R.string.yourFavorites)

        binding.toolbar.toolbarOptionImg.visibility = View.GONE

        binding.toolbar.toolbarBackImg.setOnClickListener { findNavController().popBackStack() }

        loadFavoriteApi()
        loadDeleteFavoriteApi()
    }


    private fun loadFavoriteApi() {


        viewModel.getFavoriteLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.favoritesList.showShimmer()
                }

                is NetworkRequest.Success -> {
                    binding.favoritesList.hideShimmer()

                    response.data?.let { data ->


                        if (data.data.isNotEmpty()) {

                            favoriteAdapter.setData(data.data)
                            initFavoriteAdapter()

                        } else {


                            binding.emptyLay.visibility = View.VISIBLE
                            binding.favoritesList.visibility = View.GONE
                        }


                    }

                }


                is NetworkRequest.Error -> {
                    binding.favoritesList.hideShimmer()

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }


    private fun initFavoriteAdapter() {

        binding.favoritesList.adapter = favoriteAdapter

        binding.favoritesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

binding.favoritesList.layoutManager?.onRestoreInstanceState(recyclerViewState)


        favoriteAdapter.onFavoriteItemClickListener {



            recyclerViewState=binding.favoritesList.layoutManager?.onSaveInstanceState()

if (isNetworkAvailable){

    viewModel.callDeleteFavoriteApi(it)
}

        }



    }

    private fun loadDeleteFavoriteApi(){

        viewModel.deleteFavorite.observe(viewLifecycleOwner){response->



            when(response){

                is NetworkRequest.Loading->{

                }

                is NetworkRequest.Success->{


                    response.data?.let {

                        if (isNetworkAvailable){

                            viewModel.getFavorites()
                        }



                    }
                }

                is NetworkRequest.Error->{
                    Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()
                }






            }









        }









    }




}