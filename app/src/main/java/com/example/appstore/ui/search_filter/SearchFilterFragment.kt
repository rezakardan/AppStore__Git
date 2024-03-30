package com.example.appstore.ui.search_filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentSearchFilterBinding
import com.example.appstore.viewmodels.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFilterFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentSearchFilterBinding

    override fun getTheme() = R.style.RemoveBottomSheetBackground

    @Inject
    lateinit var searchFilterAdapter: SearchFilterAdapter

    private val searchFilterViewMode: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.closeImg.setOnClickListener { this@SearchFilterFragment.dismiss()}

        searchFilterViewMode.filterSearch()
        loadSearchFilter()

    }


    private fun loadSearchFilter() {


        searchFilterViewMode.filterSearchLiveData.observe(viewLifecycleOwner) {

            searchFilterAdapter.setData(it)

            initFilterSearchRecycler()


        }


    }


    private fun initFilterSearchRecycler(){

        binding.filtersList.adapter = searchFilterAdapter

        binding.filtersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        searchFilterAdapter.setOnSearchFilter {


            this@SearchFilterFragment.dismiss()

            searchFilterViewMode.sendSearchFilter(it)




        }
    }
}