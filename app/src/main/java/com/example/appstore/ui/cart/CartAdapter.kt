package com.example.appstore.ui.cart

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appstore.R
import com.example.appstore.data.model.cart.ResponseGetCartList
import com.example.appstore.data.model.search.ResponseSearch
import com.example.appstore.databinding.ItemCartBinding
import com.example.appstore.databinding.ItemSearchBinding
import com.example.appstore.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import javax.inject.Inject

class CartAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<CartAdapter.SearchViewHolder>() {


    lateinit var binding: ItemCartBinding

    private var search = emptyList<ResponseGetCartList.OrderItem>()


    private val decimal by lazy { DecimalFormat("#,###.##") }

    inner class SearchViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(oneItem: ResponseGetCartList.OrderItem) {

            binding.itemTitle.text = oneItem.productTitle

            binding.itemCountTxt.text = oneItem.quantity


            if (oneItem.colorTitle!=null){
                binding.colorTxt.text = oneItem.colorTitle


            }else{
                binding.colorLay.visibility=View.GONE
            }
            val img = "${BASE_URL_IMAGE}${oneItem.productImage}"

            binding.itemImg.load(img)

            if (oneItem.productGuarantee.isNullOrEmpty().not()) {
                binding.guaranteeLay.visibility = View.VISIBLE
                binding.guaranteeTxt.text = oneItem.productGuarantee


            } else {
                binding.guaranteeLay.visibility = View.GONE

            }


            //Discount
            if (oneItem.discountedPrice!!.toInt() > 0) {
                binding. itemPriceDiscount.isVisible = true
                binding. itemPrice.apply {
                    isVisible = true
                    text = oneItem.finalPrice.toString().toInt().moneySeparating()
                }
                binding.  itemPriceDiscount.apply {
                    text = (oneItem.quantity.toString().toInt() * oneItem.price.toString().toInt()).moneySeparating()
                    paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(ContextCompat.getColor(context, R.color.salmon))
                }
            } else {
               binding. itemPriceDiscount.isVisible = false
                binding. itemPrice.apply {
                    text = oneItem.price.toString().toInt().moneySeparating()
                    setTextColor(ContextCompat.getColor(context, R.color.darkTurquoise))
                }
            }


            if (oneItem.quantity.toString().toInt() == 1) {

                binding.itemMinusImg.visibility = View.INVISIBLE
                binding.itemTrashImg.visibility = View.VISIBLE


            } else {


                binding.itemMinusImg.visibility = View.VISIBLE
                binding.itemTrashImg.visibility = View.GONE
            }

            if (oneItem.quantity.toString().toInt() == oneItem.productQuantity.toString().toInt()) {


                binding.itemPlusImg.apply {
                    alpha = 0.2f
                    isEnabled = false
                }


            }


            binding.itemPlusImg.setOnClickListener {

                cartListener?.let {

                    it(oneItem.id!!, INCREMENT)

                }


            }

            binding.itemMinusImg.setOnClickListener {

                cartListener?.let {

                    it(oneItem.id!!, DECREMENT)

                }


            }

            binding.itemTrashImg.setOnClickListener {

                cartListener?.let {
                    it(oneItem.id!!, DELETECART)
                }

            }


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        holder.onBind(search[position])


    }

    override fun getItemCount() = search.size


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    private var cartListener: ((Int, String) -> Unit)? = null

    fun onItemClickListener(listener: (Int, String) -> Unit) {

        cartListener = listener


    }


    fun setData(data: List<ResponseGetCartList.OrderItem>) {

        val diff = DiffUtils(search, data)

        val diffU = DiffUtil.calculateDiff(diff)

        search = data

        diffU.dispatchUpdatesTo(this)


    }

    class DiffUtils(
        private val oldItem: List<ResponseGetCartList.OrderItem>,
        private val newItem: List<ResponseGetCartList.OrderItem>
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