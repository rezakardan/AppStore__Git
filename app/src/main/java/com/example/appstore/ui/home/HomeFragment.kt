package com.example.appstore.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import coil.load
import coil.request.CachePolicy
import com.example.appstore.R
import com.example.appstore.data.model.home.ResponseDiscount
import com.example.appstore.databinding.FragmentHomeBinding
import com.example.appstore.ui.category.CategoryFragmentDirections
import com.example.appstore.ui.home.adapters.*
import com.example.appstore.utils.PRODUCT
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding


    private val homeViewModel: HomeViewModel by viewModels()


    private val snapHelper by lazy { PagerSnapHelper() }

    @Inject
    lateinit var discountAdapter: DiscountAdapter

    @Inject
    lateinit var bannerAdapter: BannerAdapter


    @Inject
    lateinit var mobileAdapter: ProductAdapter

    @Inject
    lateinit var shoesAdapter: ProductAdapter


    @Inject
    lateinit var lapTopAdapter: ProductAdapter

    @Inject
    lateinit var stationaryAdapter: ProductAdapter
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







        binding.circleImg.setOnClickListener {


            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)


        }


        binding.searchImg.setOnClickListener {


            findNavController().navigate(R.id.action_To_searchFragment)


        }


        homeViewModel.callImgProfileApi()

        homeViewModel.callBannersApi()

        homeViewModel.callDiscountApi()
        homeViewModel.callMobileApi()

        homeViewModel.callShoesApi()
        homeViewModel.callLapTopApi()
        homeViewModel.callStationaryApi()

        loadImgProfile()

        loadBannersData()
        loadDiscountApi()

        loadMobileApi()

        loadShoesApi()

        loadLapTopApi()
        loadStationaryApi()
    }


    private fun loadMobileApi() {

        homeViewModel.mobileLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.mobileRecycler.showShimmer()


                }

                is NetworkRequest.Success -> {


                    binding.mobileRecycler.hideShimmer()
                    response.data?.let { data ->

                        data.subCategory?.let { subCats ->


                            subCats.products?.let { products ->


                                products.data?.let { myData ->


                                    if (myData.isNotEmpty()) {


                                        mobileAdapter.setData(myData)
                                        initMobileRecycler()

                                    }


                                }


                            }


                        }


                    }
                }


                is NetworkRequest.Error -> {
                    binding.mobileRecycler.hideShimmer()

                    Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_SHORT).show()
                }


            }


        }


    }

    private fun initMobileRecycler() {


        binding.mobileRecycler.adapter = mobileAdapter
        binding.mobileRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mobileAdapter.onItemClickListener {

            val direction = HomeFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)
        }

    }


    private fun loadShoesApi() {

        homeViewModel.shoesLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.shoesRecycler.showShimmer()


                }

                is NetworkRequest.Success -> {


                    binding.shoesRecycler.hideShimmer()
                    response.data?.let { data ->

                        data.subCategory?.let { subCats ->


                            subCats.products?.let { products ->


                                products.data?.let { myData ->


                                    if (myData.isNotEmpty()) {


                                        shoesAdapter.setData(myData)
                                        initShoesRecycler()

                                    }


                                }


                            }


                        }


                    }
                }


                is NetworkRequest.Error -> {
                    binding.shoesRecycler.hideShimmer()

                    Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_SHORT).show()
                }


            }


        }


    }

    private fun initShoesRecycler() {


        binding.shoesRecycler.adapter = shoesAdapter
        binding.shoesRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        shoesAdapter.onItemClickListener {

            val direction = HomeFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)
        }
    }


    private fun loadLapTopApi() {

        homeViewModel.lapTopLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.laptopRecycler.showShimmer()


                }

                is NetworkRequest.Success -> {


                    binding.laptopRecycler.hideShimmer()
                    response.data?.let { data ->

                        data.subCategory?.let { subCats ->


                            subCats.products?.let { products ->


                                products.data?.let { myData ->


                                    if (myData.isNotEmpty()) {


                                        lapTopAdapter.setData(myData)
                                        initLapTopRecycler()

                                    }


                                }


                            }


                        }


                    }
                }


                is NetworkRequest.Error -> {
                    binding.laptopRecycler.hideShimmer()

                    Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_SHORT).show()
                }


            }


        }


    }

    private fun initLapTopRecycler() {


        binding.laptopRecycler.adapter = lapTopAdapter
        binding.laptopRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        lapTopAdapter.onItemClickListener {

            val direction = HomeFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)


        }
    }


    private fun loadStationaryApi() {

        homeViewModel.stationaryLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.stationeryRecycler.showShimmer()


                }

                is NetworkRequest.Success -> {


                    binding.stationeryRecycler.hideShimmer()
                    response.data?.let { data ->

                        data.subCategory?.let { subCats ->


                            subCats.products?.let { products ->


                                products.data?.let { myData ->


                                    if (myData.isNotEmpty()) {


                                        stationaryAdapter.setData(myData)
                                        initStationaryRecycler()

                                    }


                                }


                            }


                        }


                    }
                }


                is NetworkRequest.Error -> {
                    binding.stationeryRecycler.hideShimmer()

                    Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_SHORT).show()
                }


            }


        }


    }

    private fun initStationaryRecycler() {


        binding.stationeryRecycler.adapter = stationaryAdapter
        binding.stationeryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        stationaryAdapter.onItemClickListener {

            val direction = HomeFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)
        }

    }


    private fun loadDiscountApi() {

        homeViewModel.discountLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                    binding.takhfifRecycler.showShimmer()


                }
                is NetworkRequest.Success -> {
                    binding.takhfifRecycler.hideShimmer()


                    response.data?.let { data ->

                        if (data.isNotEmpty()) {
                            initDiscountRecycler(data)
                            data[0].endTime?.let {
                                val timeSplit = it.split("T")[0]

                                timerDiscount(timeSplit)
                                countDownTimer.start()

                            }


                        } else {

                            binding.takhfif.visibility = View.GONE

                        }


                    }
                }

                is NetworkRequest.Error -> {
                    binding.takhfifRecycler.hideShimmer()

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()

                }

            }


        }


    }


    private fun initDiscountRecycler(data: List<ResponseDiscount.ResponseDiscountItem>) {


        discountAdapter.setData(data)
        binding.takhfifRecycler.adapter = discountAdapter

        binding.takhfifRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)


        discountAdapter.setOnDiscountClickListener {
            val direction = HomeFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)
        }

    }


  private fun timerDiscount(fullTime: String) {


        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)


        val date: Date = formatter.parse(fullTime) as Date


        val currentMillis = System.currentTimeMillis()

        val finalMillis = date.time - currentMillis

        countDownTimer = object : CountDownTimer(finalMillis, 1_000) {
            override fun onTick(p0: Long) {
                var timer = p0

                val days: Long = TimeUnit.MILLISECONDS.toDays(timer)

                timer -= TimeUnit.DAYS.toMillis(days)

                val hour: Long = TimeUnit.MILLISECONDS.toHours(timer)
                timer -= TimeUnit.HOURS.toMillis(hour)

                val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(timer)

                timer -= TimeUnit.MINUTES.toMillis(minutes)


                val second: Long = TimeUnit.MILLISECONDS.toSeconds(timer)

                timer -= TimeUnit.SECONDS.toMillis(second)


                try {


                    if (days > 0) {

                        binding.timerLay.dayLay.visibility = View.VISIBLE
                        binding.timerLay.dayTxt.text = days.toString()

                    } else {

                        binding.timerLay.dayLay.visibility = View.GONE

                    }




                    binding.timerLay.hourTxt.text = hour.toString()



                    binding.timerLay.minuteTxt.text = minutes.toString()





                    binding.timerLay.secondTxt.text = second.toString()


                } catch (e: Exception) {

                    e.printStackTrace()
                }
            }

            override fun onFinish() {

            }


        }

    }


    private fun loadImgProfile() {


        homeViewModel.imgProfileLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {


                is NetworkRequest.Loading -> {

                    binding.progressBarImg.visibility = View.VISIBLE

                }


                is NetworkRequest.Error -> {

                    binding.progressBarImg.visibility = View.GONE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()

                }

                is NetworkRequest.Success -> {

                    binding.progressBarImg.visibility = View.GONE

                    response.data?.let {

                        if (it.avatar != null) {


                            binding.circleImg.load(it.avatar) {

                                crossfade(true)
                                crossfade(800)
                                diskCachePolicy(CachePolicy.ENABLED)

                            }
                            binding.badgeImg.visibility = View.GONE


                        } else {

                            binding.badgeImg.visibility = View.VISIBLE
                            binding.circleImg.load(R.drawable.placeholder_user)


                        }


                    }


                }


            }


        }


    }


    private fun loadBannersData() {


        homeViewModel.bannersHomeLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {

                    binding.bannerLoading.visibility = View.VISIBLE

                    binding.bannerList.visibility = View.GONE
                }

                is NetworkRequest.Success -> {
                    binding.bannerList.visibility = View.VISIBLE
                    binding.bannerLoading.visibility = View.GONE

                    response.data?.let {


                        if (it.isNotEmpty()) {
                            bannerAdapter.diffUtilBanner(response.data)
                            initBannerRecyclerView()

                        }

                    }


                }


                is NetworkRequest.Error -> {
                    binding.bannerList.visibility = View.VISIBLE

                    binding.bannerLoading.visibility = View.GONE

                    Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_SHORT).show()


                }


            }


        }


    }


    private fun initBannerRecyclerView() {

        binding.bannerList.apply {

            adapter = bannerAdapter

            set3DItem(true)
            setAlpha(true)
            setInfinite(false)


        }

        bannerAdapter.onBannersClickListener { sendData, type ->

        if (type== PRODUCT){


            val direction=HomeFragmentDirections.actionToDetail(sendData.toInt())

            findNavController().navigate(direction)

        }else{

            val direction=CategoryFragmentDirections.actionToCategoryDetail(sendData)
            findNavController().navigate(direction)
        }

        }



        snapHelper.attachToRecyclerView(binding.bannerList)
        binding.bannerLoadingIndicator.attachToRecyclerView(binding.bannerList, snapHelper)
    }


    override fun onDestroy() {
        super.onDestroy()

        if (this::countDownTimer.isInitialized) countDownTimer.cancel()

    }


}