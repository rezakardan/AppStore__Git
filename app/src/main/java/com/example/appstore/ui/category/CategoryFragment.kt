package com.example.appstore.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentCategoryBinding
import com.example.appstore.ui.category.adapters.CategoryAdapter
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.CategoryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : BaseFragment() {


    lateinit var binding: FragmentCategoryBinding


    @Inject
lateinit var categoryAdapter:CategoryAdapter

    private val categoryViewModel:CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolbar.toolbarTitleTxt.text=getString(R.string.categories)

        binding.toolbar.toolbarBackImg.visibility=View.GONE

        binding.toolbar.toolbarOptionImg.visibility=View.GONE


        categoryViewModel.callCategoryApi()








        loadCategoryApi()

    }



    private fun loadCategoryApi(){



        categoryViewModel.categoryLiveData.observe(viewLifecycleOwner){response->


            when(response){


                is NetworkRequest.Loading->{

                    binding.categoriesList.showShimmer()




                }


                is NetworkRequest.Error->{

                    binding.categoriesList.hideShimmer()

                    Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()

                }



                is NetworkRequest.Success->{

                    binding.categoriesList.hideShimmer()

response.data?.let {

    categoryAdapter.setData(it)

    initCategoryRecycler()


}



                }



            }









        }




    }



    private fun initCategoryRecycler(){

        binding.categoriesList.adapter=categoryAdapter

        binding.categoriesList.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        categoryAdapter.onCategoryAdapterClicked { slug->


val direction=CategoryFragmentDirections.actionToCategory(slug)

            findNavController().navigate(direction)






        }






    }


}