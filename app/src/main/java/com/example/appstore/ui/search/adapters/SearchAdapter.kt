package com.example.appstore.ui.search.adapters

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
import com.example.appstore.data.model.search.ResponseSearch
import com.example.appstore.databinding.ItemSearchBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import javax.inject.Inject

class SearchAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {


    lateinit var binding: ItemSearchBinding

    private var search = emptyList<ResponseSearch.Products.Data>()


    private val decimal by lazy { DecimalFormat("#,###.##") }

    inner class SearchViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(oneItem: ResponseSearch.Products.Data) {

            binding.titleTxt.text = oneItem.title

            val img = "${BASE_URL_IMAGE}${oneItem.image}"

            binding.itemImg.load(img)


            binding.itemQuantity.text = "${context.getString(R.string.quantity)} ${oneItem.quantity} " +
                    context.getString(R.string.item)


            if (oneItem.discountedPrice!! > 0) {

               binding. itemDiscount.apply {
                    isVisible = true
                    text = decimal.format(oneItem.discountedPrice.toString().toInt())+" تومان "
                }
                binding.itemPrice.apply {
                    text = decimal.format(oneItem.productPrice.toString().toInt())+" تومان "
                    paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(ContextCompat.getColor(context, R.color.salmon))
                }
                binding.itemPriceDiscount.apply {
                    isVisible = true
                    text =decimal.format( oneItem.finalPrice.toString().toInt())+" تومان "
                }
            } else {
                binding. itemDiscount.isVisible = false
                //itemPriceDiscount.isVisible = false
                binding. itemPriceDiscount.apply {
                    text = decimal.format(oneItem.productPrice.toString().toInt())+" تومان "
                    setTextColor(ContextCompat.getColor(context, R.color.darkTurquoise))
                }
            }


            oneItem.colors?.let {

                initColorsAdapter(it)


            }

            binding.root.setOnClickListener {




            }

        }


    }


    private fun initColorsAdapter(list:List<ResponseSearch.Products.Data.Color>){


        val colorAdapter=ColorsAdapter()

        colorAdapter.setData(list)
        binding.itemsColors.adapter=colorAdapter

        binding.itemsColors.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        holder.onBind(search[position])


    }

    override fun getItemCount() = search.size


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    private var searchListener: ((String) -> Unit)? = null

    fun onSearchItemClickListener(listener: (String) -> Unit) {

        searchListener = listener


    }


    fun setData(data: List<ResponseSearch.Products.Data>) {

        val diff = DiffUtils(search, data)

        val diffU = DiffUtil.calculateDiff(diff)

        search = data

        diffU.dispatchUpdatesTo(this)


    }

    class DiffUtils(
        private val oldItem: List<ResponseSearch.Products.Data>,
        private val newItem: List<ResponseSearch.Products.Data>
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