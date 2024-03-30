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
import com.example.appstore.databinding.ItemAddressesDialogBinding
import com.example.appstore.databinding.ItemShippingProductBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AddressesAdapter @Inject constructor(@ApplicationContext private val context: Context) : RecyclerView.Adapter<AddressesAdapter.ImagesViewHolder>() {

    lateinit var binding: ItemAddressesDialogBinding
    private var imagesList = emptyList<ResponseShippingList.Addresse>()

    inner class ImagesViewHolder(item: View) : RecyclerView.ViewHolder(item) {
fun onBind(oneItem:ResponseShippingList.Addresse){



binding.itemNameTxt.text="${oneItem.receiverFirstname} ${oneItem.receiverLastname}"

    binding.itemAddressTxt.text=oneItem.postalAddress


    binding.root.setOnClickListener {

        addressesListener?.let {

            it(oneItem)


        }




    }

}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        binding =
           ItemAddressesDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
holder.onBind(imagesList[position])
    }

    override fun getItemCount()=imagesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position



    private var addressesListener:((ResponseShippingList.Addresse)->Unit)?=null

fun onAddressesClickListener(listener:(ResponseShippingList.Addresse)->Unit){


    addressesListener=listener



}

    fun setData(data:List<ResponseShippingList.Addresse>){

        val diff= ImagesDiffUtils(imagesList,data)

        val diffU=DiffUtil.calculateDiff(diff)
        imagesList=data

        diffU.dispatchUpdatesTo(this)


    }



    class ImagesDiffUtils(private val oldItem: List<ResponseShippingList.Addresse>, private val newItem: List<ResponseShippingList.Addresse>) :
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