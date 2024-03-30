package com.example.appstore.ui.detail_feature

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.R
import com.example.appstore.data.model.detail.ResponseDetailFeatures
import com.example.appstore.data.model.detail.ResponseGetDetailComment
import com.example.appstore.databinding.ItemCommentBinding
import com.example.appstore.databinding.ItemFeaturesBinding
import com.example.appstore.utils.extention.convertDateToPersian
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DetailFeatureAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<DetailFeatureAdapter.ImagesViewHolder>() {

    lateinit var binding: ItemFeaturesBinding
    private var imagesList = emptyList<ResponseDetailFeatures.ResponseDetailFeaturesItem>()

    inner class ImagesViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun onBind(oneItem: ResponseDetailFeatures.ResponseDetailFeaturesItem) {


            binding.titleTxt.text=oneItem.featureTitle

            binding.infoTxt.text=oneItem.featureItemTitle





        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        binding =
            ItemFeaturesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.onBind(imagesList[position])
    }

    override fun getItemCount() = imagesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position





    fun setData(data: List<ResponseDetailFeatures.ResponseDetailFeaturesItem>) {

        val diff = ImagesDiffUtils(imagesList, data)

        val diffU = DiffUtil.calculateDiff(diff)
        imagesList = data

        diffU.dispatchUpdatesTo(this)


    }


    class ImagesDiffUtils(
        private val oldItem: List<ResponseDetailFeatures.ResponseDetailFeaturesItem>,
        private val newItem: List<ResponseDetailFeatures.ResponseDetailFeaturesItem>
    ) :
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