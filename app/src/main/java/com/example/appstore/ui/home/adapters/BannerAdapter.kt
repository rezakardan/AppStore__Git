package com.example.appstore.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.appstore.R
import com.example.appstore.data.model.home.ResponseBanners
import com.example.appstore.databinding.ItemBannerBinding
import com.example.appstore.utils.BASE_URL_IMAGE_WITH_STORAGE
import com.example.appstore.utils.PRODUCT
import javax.inject.Inject

class BannerAdapter @Inject constructor() : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    lateinit var binding: ItemBannerBinding

    private var bannerList = emptyList<ResponseBanners.ResponseBannersItem>()

    inner class BannerViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(oneItem: ResponseBanners.ResponseBannersItem) {

            binding.itemTitle.text = oneItem.title


            val imageUrl = "$BASE_URL_IMAGE_WITH_STORAGE${oneItem.image}"
            binding.itemImg.load(imageUrl) {

                crossfade(true)
                crossfade(500)
                diskCachePolicy(CachePolicy.ENABLED)
                error(R.drawable.placeholder)
            }



            binding.root.setOnClickListener {

    val sendData=if (oneItem.link!! == PRODUCT){


        oneItem.linkId




    }else{


        oneItem.urlLink?.slug

    }


                bannersListener?.let {

                    it(sendData!!,oneItem.link)




                }

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {

        return holder.onBind(bannerList[position])

    }

    override fun getItemCount() = bannerList.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()


    private var bannersListener: ((String, String) -> Unit)? = null

    fun onBannersClickListener(listener: (String, String) -> Unit) {

        bannersListener = listener


    }


    fun diffUtilBanner(data: List<ResponseBanners.ResponseBannersItem>) {

        val diff = BannerDiffUtils(bannerList, data)

        val diffUtils = DiffUtil.calculateDiff(diff)

        bannerList = data

        diffUtils.dispatchUpdatesTo(this)


    }


    class BannerDiffUtils(
        private val oldItem: List<ResponseBanners.ResponseBannersItem>,
        private val newItem: List<ResponseBanners.ResponseBannersItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItem[newItemPosition] === oldItem[oldItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItem[newItemPosition] === oldItem[oldItemPosition]

        }


    }


}