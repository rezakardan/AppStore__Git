package com.example.appstore.ui.detail_chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.appstore.databinding.FragmentDetailChartBinding
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.extention.setUpMyChart
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.DetailsViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailChartFragment : BaseFragment() {

    lateinit var binding: FragmentDetailChartBinding


    private val viewModel by activityViewModels<DetailsViewModel>()

    private val daysList = arrayListOf<String>()
    private val pricesList = ArrayList<Entry>()

    private val daysListForToolTip = arrayListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.productIdLiveData.observe(viewLifecycleOwner) {

            if (isNetworkAvailable) {
                viewModel.detailPriceChart(it)
            }


        }

        loadDetailChart()

    }


    private fun loadDetailChart() {


        viewModel.detailChartLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.featuresLoading.visibility = View.VISIBLE
                    binding.pricesChart.visibility = View.GONE

                }


                is NetworkRequest.Success -> {

                    binding.featuresLoading.visibility = View.GONE

                    binding.pricesChart.visibility = View.VISIBLE

                    response.data?.let { data ->

                        if (data.isNotEmpty()) {


                            for (i in data.indices) {

                                daysListForToolTip.add(data[i].day!!)
                                daysList.add(data[i].day!!.drop(5))




                                    pricesList.add(Entry(i.toFloat(),data[i].price!!.toFloat()))


                            }




                                    binding.pricesChart.setUpMyChart(
                                        DaysFormatter(daysList),
                                        pricesList,
                                        daysList.size,
                                        daysListForToolTip
                                    )






                        }


                    }
                }


                is NetworkRequest.Error -> {
                    binding.pricesChart.visibility = View.VISIBLE

                    binding.featuresLoading.visibility = View.GONE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }


    inner class DaysFormatter(private val daysList: ArrayList<String>) : IndexAxisValueFormatter() {


        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {


            val index = value.toInt()

            return if (index < daysList.size) {
                daysList[index]

            } else {
                null
            }
        }


    }


}

