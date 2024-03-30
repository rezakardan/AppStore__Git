package com.example.appstore.ui.home.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ActivityChooserView.InnerLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appstore.R
import com.example.appstore.data.model.home.ResponseProducts
import com.example.appstore.databinding.ItemProductsBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import javax.inject.Inject

class ProductAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.MobileViewHolder>() {


    lateinit var binding: ItemProductsBinding

    private var mobile = emptyList<ResponseProducts.SubCategory.Products.Data>()


    private val decimalFormat by lazy { DecimalFormat("#,###,##") }

    inner class MobileViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun onBind(oneItem: ResponseProducts.SubCategory.Products.Data) {


            binding.itemTitle.text = oneItem.title

            val imgUrl = "${BASE_URL_IMAGE}${oneItem.image}"
            binding.itemImg.load(imgUrl)


            binding.itemProgress.progress = oneItem.quantity.toString().toInt()


            if (oneItem.discountedPrice!! > 0) {

                binding.itemDiscount.visibility = View.VISIBLE

                binding.itemDiscount.text =
                    decimalFormat.format(oneItem.discountedPrice.toString().toInt()) + "تومان "



                binding.itemPriceDiscount.visibility = View.VISIBLE


                binding.itemPriceDiscount.text =
                    decimalFormat.format(oneItem.finalPrice.toString().toInt()) + " نومان "

                binding.itemPrice.apply {

                    text = decimalFormat.format(oneItem.productPrice.toString().toInt()) + " تومان "

                    paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                    setTextColor(ContextCompat.getColor(context, R.color.salmon))


                }

            } else {

                binding.itemDiscount.visibility = View.GONE

                binding.itemPriceDiscount.visibility = View.GONE
                binding.itemPrice.apply {

                    text = decimalFormat.format(oneItem.productPrice.toString().toInt()) + "تومان "

                    setTextColor(ContextCompat.getColor(context, R.color.darkTurquoise))

                }


            }

            binding.root.setOnClickListener {

                itemClickListener?.let {

                    it(oneItem.id!!)

                }


            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MobileViewHolder {
        binding = ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MobileViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MobileViewHolder, position: Int) {
        holder.onBind(mobile[position])
    }

    override fun getItemCount() = mobile.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    private var itemClickListener: ((Int) -> Unit)? = null

    fun onItemClickListener(listener: (Int) -> Unit) {

        itemClickListener = listener


    }

    fun setData(data: List<ResponseProducts.SubCategory.Products.Data>) {

        val diff = DiffUtils(mobile, data)

        val diffUt = DiffUtil.calculateDiff(diff)

        mobile = data


        diffUt.dispatchUpdatesTo(this)


    }

    class DiffUtils(
        private val oldItem: List<ResponseProducts.SubCategory.Products.Data>,
        private val newItem: List<ResponseProducts.SubCategory.Products.Data>
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