package com.example.appstore.ui.profile_orders.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.R
import com.example.appstore.data.model.profile_order.ResponseProfileOrders
import com.example.appstore.databinding.ItemOrdersBinding
import com.example.appstore.utils.extention.convertDateToPersian
import com.example.appstore.utils.moneySeparating
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OrdersAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<OrdersAdapter.SearchViewHolder>() {


    lateinit var binding: ItemOrdersBinding

    private var search = emptyList<ResponseProfileOrders.Data>()




    inner class SearchViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        @SuppressLint("SetTextI18n")
        fun onBind(oneItem: ResponseProfileOrders.Data) {


            binding.itemPrice.text=oneItem.finalPrice.toString().toInt().moneySeparating()



            val dateSplit=oneItem.updatedAt!!.split("T")
            val date=dateSplit[0].convertDateToPersian()

            val hour="${context.getString(R.string.hour)} ${

                dateSplit[1].split(".")[0].dropLast(3)

            }"



            binding.calendarTitle.text="$date | $hour"


          oneItem.orderItems?.let {products->
              initProductAdapter(products)
          }


        }


    }


    private fun initProductAdapter(list:List<ResponseProfileOrders.Data.OrderItem>){


        val adapter=ProductsAdapter()

        adapter.setData(list)
        binding.productsList.adapter=adapter

        binding.productsList.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        holder.onBind(search[position])


    }

    override fun getItemCount() = search.size


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position




    fun setData(data: List<ResponseProfileOrders.Data>) {

        val diff = DiffUtils(search, data)

        val diffU = DiffUtil.calculateDiff(diff)

        search = data

        diffU.dispatchUpdatesTo(this)


    }

    class DiffUtils(
        private val oldItem: List<ResponseProfileOrders.Data>,
        private val newItem: List<ResponseProfileOrders.Data>
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