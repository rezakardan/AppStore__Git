package com.example.appstore.ui.detail

import android.app.Dialog
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Icon
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.getChildMeasureSpec
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appstore.R
import com.example.appstore.data.model.cart.BodyAddToCart
import com.example.appstore.data.model.detail.ResponseDetail
import com.example.appstore.databinding.DialogImageBinding
import com.example.appstore.databinding.FragmentDetailBinding
import com.example.appstore.ui.detail.adapters.ImagesAdapter
import com.example.appstore.ui.detail.adapters.PagerAdapter
import com.example.appstore.utils.*
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.event.EventBus
import com.example.appstore.utils.event.Events
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.CartViewModel
import com.example.appstore.viewmodels.DetailsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.SimpleFormatter
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment() {
    lateinit var binding: FragmentDetailBinding

    private val viewModel by activityViewModels<DetailsViewModel>()

    private val cartViewModel by viewModels<CartViewModel>()

    private val args by navArgs<DetailFragmentArgs>()
    private var productId = 0
    private var isNeededToColor = false

    @Inject
    lateinit var imagesAdapter: ImagesAdapter


    private lateinit var countDownTimer: CountDownTimer


    @Inject
    lateinit var pagerAdapter: PagerAdapter


    @Inject
    lateinit var bodyAddToCart: BodyAddToCart

    private var isAddedToCart = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.let {

            productId = it.productid
            viewModel.sendProductId(productId)

        }
        if (productId > 0) {
            if (isNetworkAvailable) {
                viewModel.callDetailApi(productId)

            }
        }
        binding.detailHeaderLay.backImg.setOnClickListener { findNavController().popBackStack() }
        loadDetailApi()

        loadDetailFavoriteApi()

        loadAddToCartApi()
    }


    private fun loadDetailApi() {


        viewModel.detailLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is NetworkRequest.Loading -> {
                    binding.detailLoading.visibility = View.VISIBLE
                    binding.containerLay.visibility = View.GONE
                }

                is NetworkRequest.Success -> {
                    binding.detailLoading.visibility = View.GONE
                    binding.containerLay.visibility = View.VISIBLE

                    response.data?.let { data ->
                        initDetailViews(data)

                    }
                }


                is NetworkRequest.Error -> {
                    binding.detailLoading.visibility = View.GONE
                    binding.containerLay.visibility = View.VISIBLE

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }

    private fun initDetailViews(data: ResponseDetail) {

        initDetailHeaderViews(data)
        initDetailInfo(data)
        initDetailTimerViews(data)

        setUpPagerView()


        initBottomAddToCart(data)


    }


    private fun initDetailHeaderViews(data: ResponseDetail) {

        loadImage(data.image!!)


        binding.detailHeaderLay.apply {

            productTitle.text = data.title
            if (data.description.isNullOrEmpty().not()) {
                val info = HtmlCompat.fromHtml(
                    data.description.toString(),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
                productInfo.text = info
            } else {
                productInfo.visibility = View.GONE
            }



            if (data.colors!!.isNotEmpty()) {

                setUpColorsChip(data.colors.toMutableList())

                lifecycleScope.launch {
                    delay(100)
                    colorsScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT)

                }


                isNeededToColor = true
            } else {
                isNeededToColor = false

                line1.visibility = View.GONE
                colorsTitle.visibility = View.GONE
                colorsScroll.visibility = View.GONE


            }

            updateFavUi(data.isAddToFavorite!!.toInt())
            favImg.setOnClickListener {

                if (isNetworkAvailable) {

                    viewModel.callFavoriteApi(productId)

                }

            }

            if (data.images != null) {
                if (data.images.isNotEmpty()) {
                    data.images.add(0, data.image)
                    initImagesRecycler(data.images)
                }

            }
        }


    }


    private fun initDetailInfo(data: ResponseDetail) {

        binding.detailInfoLay.apply {

            if (data.brand != null) {
                brandTxt.text = data.brand.title?.fa

            } else {
                brandLay.visibility = View.GONE
                line1.visibility = View.GONE
            }



            categoryTxt.text = data.category?.title

            if (data.guarantee.isNullOrEmpty().not()) {
                guaranteeTxt.text = data.guarantee

            } else {
                guaranteeLay.visibility = View.GONE
                line3.visibility = View.GONE
            }

            quantityTxt.text = "${data.quantity} ${getString(R.string.item)}"

            commentsTxt.text = "${data.commentsCount} ${getString(R.string.comment)}"


            rateTxt.text = "${data.likesCount} ${getString(R.string.rate)}"

            if (data.status == SPECIAL) {

                specialTitle.visibility = View.VISIBLE


            } else {
                specialTitle.visibility = View.GONE

            }


        }


    }


    private fun loadImage(image: String) {
        binding.detailHeaderLay.apply {


            val urlImage = "${BASE_URL_IMAGE}${image}"

            Glide.with(requireContext())
                .load(urlImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(productImg)

            productImg.setOnClickListener {
                showImageInDialog(urlImage)

            }


        }
    }


    private fun showImageInDialog(urlImage: String) {


        val dialog = Dialog(requireContext())
        val dialogBinding = DialogImageBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        Glide.with(requireContext())
            .load(urlImage)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(dialogBinding.productImg)

        dialog.show()


    }


    private fun setUpColorsChip(data: MutableList<ResponseDetail.Color>) {

        data.forEach {

            val chip = Chip(requireContext())

            val drawable = ChipDrawable.createFromAttributes(
                requireContext(),
                null,
                0,
                R.style.DetailChipBackground
            )
            chip.setChipDrawable(drawable)

            val colorTitle = if (it.hexCode?.lowercase() == COLOR_WHITE) {

                COLOR_BLACK
            } else {
                it.hexCode
            }

            chip.setTextColor(Color.parseColor(colorTitle))
            chip.text = it.title

            chip.id = it.id!!

            // chip.setTextAppearanceResource(R.style.DetailChipText)

            binding.detailHeaderLay.colorsChipGroup.apply {

                addView(chip)
                chip.setOnCheckedChangeListener { _, isChecked ->


                    if (isChecked) {

                        bodyAddToCart.colorId = chip.id.toString()


                    }


                }


            }


        }


    }


    private fun updateFavUi(isAddedToFavorite: Int) {

        if (isAddedToFavorite == 1) {

            binding.detailHeaderLay.favImg.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.salmon
                )
            )


        } else {

            binding.detailHeaderLay.favImg.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )


        }


    }

    private fun initImagesRecycler(urlImage: List<String>) {

        imagesAdapter.setData(urlImage)
        binding.detailHeaderLay.productImagesList.adapter = imagesAdapter
        binding.detailHeaderLay.productImagesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        imagesAdapter.setOnImagesClickListener {


            loadImage(it)


        }

    }


    private fun initDetailTimerViews(data: ResponseDetail) {

        binding.detailTimerLay.apply {
            if (data.discountedPrice!!.toInt() > 0) {

                if (data.endTime.isNullOrEmpty().not()) {

                    priceDiscountLay.visibility = View.VISIBLE

                    val date = data.endTime!!.split("T")[0]

                    discountTimer(date)
                    countDownTimer.start()


                } else {

                    priceDiscountLay.visibility = View.GONE
                }

                binding.detailBottom.oldPriceTxt.apply {

                    text = data.productPrice.toString().toInt().moneySeparating()

                    paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


                }


            } else {

                priceDiscountLay.visibility = View.GONE
            }


        }


    }


    private fun discountTimer(fullData: String) {

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date: Date = formatter.parse(fullData) as Date
        val currentMillis = System.currentTimeMillis()

        val finalMillis = date.time - currentMillis


        countDownTimer = object : CountDownTimer(finalMillis, 1_000) {
            override fun onTick(p0: Long) {
                var timer = p0

                val day: Long = TimeUnit.MILLISECONDS.toDays(timer)
                timer -= TimeUnit.DAYS.toMillis(day)


                val hours: Long = TimeUnit.MILLISECONDS.toHours(timer)
                timer -= TimeUnit.HOURS.toMillis(hours)

                val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(timer)
                timer -= TimeUnit.MINUTES.toMillis(minutes)


                val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(timer)

                try {
                    binding.detailTimerLay.timerLay.apply {
                        if (day > 0) {
                            dayLay.isVisible = true
                            dayTxt.text = day.toString()
                        } else {
                            dayLay.isVisible = false
                        }
                        hourTxt.text = hours.toString()
                        minuteTxt.text = minutes.toString()
                        secondTxt.text = seconds.toString()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFinish() {
            }

        }


    }


    private fun setUpPagerView() {

        //  pagerAdapter= PagerAdapter(parentFragmentManager,lifecycle)

        binding.detailPagerLay.apply {

            detailTabLayout.addTab(detailTabLayout.newTab().setText(getText(R.string.comment)))

            detailTabLayout.addTab(detailTabLayout.newTab().setText(getString(R.string.features)))

            detailTabLayout.addTab(detailTabLayout.newTab().setText(getString(R.string.priceChart)))


            detailViewPager.adapter = pagerAdapter



            detailTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) detailViewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }


            })

            detailViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {


                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    detailTabLayout.selectTab(detailTabLayout.getTabAt(position))


                }


            })


        }


    }


    private fun initBottomAddToCart(data: ResponseDetail) {

        binding.detailBottom.apply {

            isAddedToCart = data.isAddToCart!!.toInt()

            if (data.isAddToCart == 1) {


                updateBottomAddToCartUi()


            }

            finalPriceTxt.text = data.finalPrice.toString().toInt().moneySeparating()



            addToCartBtn.setOnClickListener {


                if (isAddedToCart == 0) {
                    if (data.quantity!!.toInt() > 0) {
                        if (isNeededToColor) {
                            if (bodyAddToCart.colorId == null) {
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.selectTheOneOfColors),
                                    Snackbar.LENGTH_SHORT
                                ).show()

                            } else {
                                cartViewModel.callAddToCartApi(productId, bodyAddToCart)

                            }
                        } else {
                            cartViewModel.callAddToCartApi(productId, bodyAddToCart)
                        }
                    } else {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.shouldExistsProductInStore),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }


        }

    }


    private fun updateBottomAddToCartUi() {

        binding.detailBottom.addToCartBtn.apply {


            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.royalBlue))

            icon = ContextCompat.getDrawable(requireContext(), R.drawable.cart_circle_check)


            text = getString(R.string.existsInCart)


        }


    }


    private fun loadDetailFavoriteApi() {

        viewModel.favoriteLiveData.observe(viewLifecycleOwner) { response ->

            binding.detailHeaderLay.apply {

                when (response) {


                    is NetworkRequest.Loading -> {
                        favLoading.visibility = View.VISIBLE
                        favImg.visibility = View.GONE
                    }


                    is NetworkRequest.Success -> {
                        favLoading.visibility = View.GONE
                        favImg.visibility = View.VISIBLE

                        response.data?.let { data ->

                            updateFavUi(data.count!!)
                            binding.detailInfoLay.apply {
                                if (data.count == 1) {

                                    rateTxt.text = "${
                                        rateTxt.text.toString().dropLast(7).toInt() + 1
                                    } ${getString(R.string.rate)}"


                                } else {
                                    rateTxt.text = "${
                                        rateTxt.text.toString().dropLast(7).toInt() - 1
                                    } ${getString(R.string.rate)}"

                                }


                            }

                        }


                    }


                    is NetworkRequest.Error -> {
                        binding.detailHeaderLay.favLoading.visibility = View.GONE
                        binding.detailHeaderLay.favImg.visibility = View.VISIBLE

                        Snackbar.make(
                            binding.root,
                            response.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

            }

        }

    }


    private fun loadAddToCartApi() {


        cartViewModel.addToCartLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is NetworkRequest.Loading -> {

                }


                is NetworkRequest.Success -> {

                    response.data?.let { data ->

                        Snackbar.make(
                            binding.root,
                            response.data.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()

                        isAddedToCart = 1
                        updateBottomAddToCartUi()


                        lifecycleScope.launch {

                            EventBus.publish(Events.IsUpdateCart)



                        }

                    }
                }


                is NetworkRequest.Error -> {
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }


    override fun onDestroy() {
        super.onDestroy()

        if (this::countDownTimer.isInitialized) countDownTimer.cancel()


    }


}