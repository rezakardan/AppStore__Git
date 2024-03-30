package com.example.appstore.ui.shipping.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appstore.data.model.shipping.ResponseShippingList
import com.example.appstore.databinding.ItemShippingProductBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ShippingAdapter @Inject constructor(@ApplicationContext private val context: Context) : RecyclerView.Adapter<ShippingAdapter.ImagesViewHolder>() {

    lateinit var binding: ItemShippingProductBinding
    private var imagesList = emptyList<ResponseShippingList.Order.OrderItem>()

    inner class ImagesViewHolder(item: View) : RecyclerView.ViewHolder(item) {
fun onBind(oneItem:ResponseShippingList.Order.OrderItem){

val imageUrl="${BASE_URL_IMAGE}${oneItem.productImage}"
    Glide.with(context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(binding.itemImg)


binding.itemTitle.text=oneItem.productTitle.toString()


}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        binding =
            ItemShippingProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
holder.onBind(imagesList[position])
    }

    override fun getItemCount()=imagesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position






    fun setData(data:List<ResponseShippingList.Order.OrderItem>){

        val diff= ImagesDiffUtils(imagesList,data)

        val diffU=DiffUtil.calculateDiff(diff)
        imagesList=data

        diffU.dispatchUpdatesTo(this)


    }



    class ImagesDiffUtils(private val oldItem: List<ResponseShippingList.Order.OrderItem>, private val newItem: List<ResponseShippingList.Order.OrderItem>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }


    }


}