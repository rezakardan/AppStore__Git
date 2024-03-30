package com.example.appstore.ui.profile_orders.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appstore.data.model.profile_order.ResponseProfileOrders
import com.example.appstore.databinding.ItemOrderProductBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import javax.inject.Inject

class ProductsAdapter @Inject constructor() : RecyclerView.Adapter<ProductsAdapter.ColorViewHolder>() {


    lateinit var binding: ItemOrderProductBinding

    private var colors= emptyList<ResponseProfileOrders.Data.OrderItem>()

    inner class ColorViewHolder(item: View) : RecyclerView.ViewHolder(item) {

fun onBind(oneItem:ResponseProfileOrders.Data.OrderItem){
    oneItem.extras?.let {



    binding.itemTitle.text=it.title

        val image="$BASE_URL_IMAGE${it.image}"
        binding.itemImg.load(image)

    }

}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        binding=ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ColorViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {

        holder.onBind(colors[position])

    }

    override fun getItemCount()=colors.size

    override fun getItemId(position: Int)=position.toLong()

    override fun getItemViewType(position: Int)=position


    fun setData(data:List<ResponseProfileOrders.Data.OrderItem>){

        val diff=DiffUtils(colors,data)

        val diffU=DiffUtil.calculateDiff(diff)

        colors=data

        diffU.dispatchUpdatesTo(this)




    }

    class DiffUtils(
        private val oldItem: List<ResponseProfileOrders.Data.OrderItem>,
        private val newItem: List<ResponseProfileOrders.Data.OrderItem>
    ) : DiffUtil.Callback() {
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