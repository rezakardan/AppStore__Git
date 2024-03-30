package com.example.appstore.ui.detail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appstore.databinding.ItemProductImagesBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImagesAdapter @Inject constructor(@ApplicationContext private val context: Context) : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    lateinit var binding: ItemProductImagesBinding
    private var imagesList = emptyList<String>()

    inner class ImagesViewHolder(item: View) : RecyclerView.ViewHolder(item) {
fun onBind(oneItem:String){

val imageUrl="${BASE_URL_IMAGE}${oneItem}"
    Glide.with(context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(binding.itemImg)




binding.root.setOnClickListener {

    onImagesClickListener?.let {

        it(oneItem)


    }



}
}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        binding =
            ItemProductImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
holder.onBind(imagesList[position])
    }

    override fun getItemCount()=imagesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position


    private var onImagesClickListener:((String)->Unit)?=null

    fun setOnImagesClickListener(listener:(String)->Unit){

        onImagesClickListener=listener
    }



    fun setData(data:List<String>){

        val diff= ImagesDiffUtils(imagesList,data)

        val diffU=DiffUtil.calculateDiff(diff)
        imagesList=data

        diffU.dispatchUpdatesTo(this)


    }



    class ImagesDiffUtils(private val oldItem: List<String>, private val newItem: List<String>) :
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