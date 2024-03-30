package com.example.appstore.ui.category_filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.appstore.R
import com.example.appstore.data.model.search.FilterModel
import com.example.appstore.databinding.FragmentCategoryFilterBinding
import com.example.appstore.utils.moneySeparating
import com.example.appstore.viewmodels.CategoryProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class CategoryFilterFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentCategoryFilterBinding


    override fun getTheme() = R.style.RemoveBottomSheetBackground

    private var minPrice: String? = null

    private var maxPrice: String? = null

    private var sort: String? = null

    private var search: String? = null

    private var onlyAvailable: Boolean? = false


    private val categoryFilterProductViewModel by activityViewModels<CategoryProductViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryFilterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeImg.setOnClickListener { this@CategoryFilterFragment.dismiss() }


        lifecycleScope.launch {

            delay(100)



binding.sortScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT)

        }

        binding.submitBtn.setOnClickListener {

            if (binding.searchEdt.text.isNotEmpty()) {

                search = binding.searchEdt.text.toString()

            }

            onlyAvailable = binding.availableCheck.isChecked

            categoryFilterProductViewModel.sendFilter(
                search,
                sort,
                minPrice,
                maxPrice,
                onlyAvailable
            )
            this@CategoryFilterFragment.dismiss()

        }



        initRangePrice()


        categoryFilterProductViewModel.getFilter()
        loadSortData()
    }


    private fun initRangePrice() {

        val formatter = LabelFormatter { value ->
            value.toInt().moneySeparating()
        }

        binding.priceRange.apply {


            setValues(7000000F, 21000000F)


            setLabelFormatter(formatter)

            addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: RangeSlider) {

                }

                override fun onStopTrackingTouch(slider: RangeSlider) {

                    val values = slider.values

                    minPrice = values[0].toInt().toString()

                    maxPrice = values[1].toInt().toString()


                }
            })


        }


    }


    private fun loadSortData() {

        categoryFilterProductViewModel.filterLiveData.observe(viewLifecycleOwner) {

            setUpChip(it)


        }


    }

    private fun setUpChip(list: MutableList<FilterModel>) {

        var tempList = mutableListOf<FilterModel>()

        tempList.clear()

        tempList = list

        tempList.forEach {

            val chip = Chip(requireContext())

            val drawable = ChipDrawable.createFromAttributes(
                requireContext(),
                null,
                0,
                R.style.FilterChipsBackground
            )

            chip.setChipDrawable(drawable)

            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            chip.text = it.faName
            chip.id = it.id

            binding.sortChipGroup.apply {


                addView(chip)
                setOnCheckedStateChangeListener { group, _ ->


                    sort = tempList[group.checkedChipId - 1].enName


                }
            }

        }


    }


}