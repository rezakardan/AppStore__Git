package com.example.appstore.ui.home.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.appstore.R
import com.example.appstore.data.model.home.ResponseDiscount
import com.example.appstore.databinding.ItemDiscountBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import javax.inject.Inject

class DiscountAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder>() {

    lateinit var binding: ItemDiscountBinding


    private val decimalFormat by lazy { DecimalFormat("#,###.##") }

   private var discount = emptyList<ResponseDiscount.ResponseDiscountItem>()

    inner class DiscountViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun onBind(oneItem: ResponseDiscount.ResponseDiscountItem) {

            binding.itemTitle.text = oneItem.title

binding.itemProgress.progress=oneItem.quantity.toString().toInt()
            binding.itemPrice.apply {

                text = decimalFormat.format(oneItem.discountedPrice.toString().toInt())+"تومان "
                paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                setTextColor(ContextCompat.getColor(context, R.color.salmon))

            }


            binding.itemPriceDiscount.text = decimalFormat.format(oneItem.finalPrice.toString().toInt())+" تومان "

            binding.itemImg.load("${BASE_URL_IMAGE}${oneItem.image}") {

                crossfade(true)
                crossfade(500)
                diskCachePolicy(CachePolicy.ENABLED)
                error(R.drawable.placeholder)
            }
binding.root.setOnClickListener {

    discountListener?.let {

       it(oneItem.id!!)


    }


}

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountViewHolder {

        binding = ItemDiscountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscountViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DiscountViewHolder, position: Int) {
        holder.onBind(discount[position])
    }

    override fun getItemCount() = discount.size


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position


    private var discountListener: ((Int) -> Unit)? = null

    fun setOnDiscountClickListener(listener: (Int) -> Unit) {

        discountListener = listener


    }

    fun setData(data: List<ResponseDiscount.ResponseDiscountItem>) {

        val diffUtil = DiffUtilDiscount(discount, data)

        val diff = DiffUtil.calculateDiff(diffUtil)

        discount = data

        diff.dispatchUpdatesTo(this)


    }


    class DiffUtilDiscount(
        private val oldItem: List<ResponseDiscount.ResponseDiscountItem>,
        private val newItem: List<ResponseDiscount.ResponseDiscountItem>
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