package com.example.appstore.ui.category_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentCategoryProductBinding
import com.example.appstore.ui.category_product.adapters.CategoryProductAdapter
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.CategoryProductViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CategoryProductFragment : BaseFragment() {

    lateinit var binding: FragmentCategoryProductBinding

    private val args by navArgs<CategoryProductFragmentArgs>()
    private var slug = ""


    private val categoryProductViewModel by activityViewModels<CategoryProductViewModel>()


    @Inject
    lateinit var productCategoryAdapter: CategoryProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.toolbarOptionImg.setOnClickListener { findNavController().navigate(R.id.action_categoryProductFragment_to_categoryFilterFragment) }


        binding.toolbar.toolbarBackImg.setOnClickListener { findNavController().popBackStack() }




        args?.let {
            slug = it.slug
        }


        if (isNetworkAvailable) {

            categoryProductViewModel.categoryProduct(slug)
        }



        categoryProductViewModel.sendFilterLiveData.observe(viewLifecycleOwner) {

            if (isNetworkAvailable) {

                categoryProductViewModel.categoryProduct(
                    slug,
                    it.search,
                    it.sort,
                    it.minPrice,
                    it.maxPrice,
                    onlyAvailable = it.onlyAvailable
                )


            }


        }




        binding.toolbar.toolbarBackImg.setOnClickListener { findNavController().popBackStack() }


        binding.toolbar.toolbarOptionImg.setOnClickListener { findNavController().navigate(R.id.action_categoryProductFragment_to_categoryFilterFragment) }

        loadProductCategoryApi()
    }


    private fun loadProductCategoryApi() {

        categoryProductViewModel.productLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                    binding.productsList.showShimmer()


                }

                is NetworkRequest.Error -> {


                    binding.productsList.hideShimmer()

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()


                }

                is NetworkRequest.Success -> {

                    binding.productsList.hideShimmer()

                    response?.data?.let { data ->


                        binding.toolbar.toolbarTitleTxt.text = data.subCategory?.title


                        response.data?.subCategory?.products.let { product ->


                            if (product?.data!!.isNotEmpty()) {

                                binding.emptyLay.visibility = View.GONE

                                binding.productsList.visibility = View.VISIBLE

                                productCategoryAdapter.setData(product.data)
                                initProductRecycler()

                            } else {


                                binding.emptyLay.visibility = View.VISIBLE

                                binding.productsList.visibility = View.GONE


                            }


                        }

                    }


                }
            }

        }


    }


    private fun initProductRecycler() {

        binding.productsList.adapter = productCategoryAdapter

        binding.productsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


    }


}




