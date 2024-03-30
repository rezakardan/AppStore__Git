package com.example.appstore.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentSearchBinding
import com.example.appstore.ui.search.adapters.SearchAdapter
import com.example.appstore.utils.NEW
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    lateinit var binding: FragmentSearchBinding

    private val searchViewModel: SearchViewModel by activityViewModels()


    @Inject
    lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.toolbar.toolbarTitleTxt.text = getString(R.string.searchInProducts)

        binding.toolbar.toolbarBackImg.setOnClickListener { findNavController().popBackStack() }

        binding.toolbar.toolbarOptionImg.visibility = View.GONE



        binding.searchEdt.addTextChangedListener {

            if (it.toString().length > 3) {


                if (isNetworkAvailable) {

                    searchViewModel.search(it.toString(), NEW)


                }


            }

            if (it.toString().isEmpty()) {


                binding.emptyLay.visibility = View.VISIBLE

                binding.searchList.visibility = View.GONE


            }


        }

searchViewModel.sendSearchFilterLiveData.observe(viewLifecycleOwner){

    if (binding.searchEdt.toString().length>3){

        if (isNetworkAvailable){


            searchViewModel.search(binding.searchEdt.text.toString(),it)


        }



    }




}


        binding.filterImg.setOnClickListener { findNavController().navigate(R.id.action_searchFragment_to_searchFilterFragment) }


        loadSearchApi()
    }


    private fun loadSearchApi() {


        searchViewModel.searchLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                    binding.searchList.showShimmer()


                }


                is NetworkRequest.Error -> {
                    binding.searchList.hideShimmer()
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


                is NetworkRequest.Success -> {

                    response.data?.let { data ->

                        data.products?.let { products ->


                            if (products.data?.isNotEmpty()!!) {


                                binding.emptyLay.visibility = View.GONE

                                binding.searchList.visibility = View.VISIBLE




                                searchAdapter.setData(products.data)

                                initSearchAdapter()
                            } else {

                                binding.searchList.visibility = View.GONE

                                binding.emptyLay.visibility = View.VISIBLE


                            }
                        }


                    }


                }


            }


        }


    }


    private fun initSearchAdapter() {

        binding.searchList.adapter = searchAdapter

        binding.searchList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


    }
}