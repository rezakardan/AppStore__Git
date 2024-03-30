package com.example.appstore.ui.detail_comments

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

class DetailCommentsAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<DetailCommentsAdapter.ImagesViewHolder>() {

    lateinit var binding: ItemCommentBinding
    private var imagesList = emptyList<ResponseGetDetailComment.Data>()

    inner class ImagesViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun onBind(oneItem: ResponseGetDetailComment.Data) {

            oneItem.user?.let { user ->

                if (user.firstname != null) {

                    binding.itemNameTxt.text = "${user.firstname} ${user.lastname}"

                } else {
                    binding.itemNameTxt.text = context.getString(R.string.withoutName)
                }


            }


            val dateSplit = oneItem.createdAt!!.split("T")[0].convertDateToPersian()

            val hourSplit =
                "${context.getString(R.string.hour)} ${oneItem.createdAt.split("T")[1].split(".").dropLast(3)}"


            binding.itemDateTxt.text = "${dateSplit} : ${hourSplit}"


            binding.itemRating.rating=oneItem.rate.toString().toFloat()

            binding.itemCommentTxt.text=oneItem.comment
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        binding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.onBind(imagesList[position])
    }

    override fun getItemCount() = imagesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position


    private var onImagesClickListener: ((String) -> Unit)? = null

    fun setOnImagesClickListener(listener: (String) -> Unit) {

        onImagesClickListener = listener
    }


    fun setData(data: List<ResponseGetDetailComment.Data>) {

        val diff = ImagesDiffUtils(imagesList, data)

        val diffU = DiffUtil.calculateDiff(diff)
        imagesList = data

        diffU.dispatchUpdatesTo(this)


    }


    class ImagesDiffUtils(
        private val oldItem: List<ResponseGetDetailComment.Data>,
        private val newItem: List<ResponseGetDetailComment.Data>
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