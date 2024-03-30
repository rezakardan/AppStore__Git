package com.example.appstore.ui.detail_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.databinding.FragmentDetailFeatureBinding
import com.example.appstore.ui.detail_comments.DetailCommentsAdapter
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFeatureFragment : BaseFragment() {

    lateinit var binding: FragmentDetailFeatureBinding

    @Inject
    lateinit var featuresAdapter: DetailFeatureAdapter

    private val viewModel by activityViewModels<DetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailFeatureBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.productIdLiveData.observe(viewLifecycleOwner) {


            if (isNetworkAvailable) {


                viewModel.detailFeatures(it)
            }


        }


        loadFeaturesApi()

    }


    private fun loadFeaturesApi() {

        viewModel.featuresLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                    binding.featuresLoading.visibility = View.VISIBLE
                    binding.featuresList.visibility = View.GONE
                }


                is NetworkRequest.Success -> {

                    response.data?.let { data ->

                        featuresAdapter.setData(data)

                        binding.featuresLoading.visibility = View.GONE

                        binding.featuresList.visibility = View.VISIBLE
                        initRecycler()

                        if (data.isNotEmpty()) {

                            binding.emptyLay.visibility = View.GONE

                            binding.featuresList.visibility = View.VISIBLE


                        } else {
                            binding.emptyLay.visibility = View.VISIBLE

                            binding.featuresList.visibility = View.GONE
                        }

                    }

                }


                is NetworkRequest.Error -> {
                    binding.featuresLoading.visibility = View.GONE
                    binding.featuresList.visibility = View.VISIBLE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }


        }


    }

    private fun initRecycler() {

        binding.featuresList.adapter = featuresAdapter
        binding.featuresList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

}