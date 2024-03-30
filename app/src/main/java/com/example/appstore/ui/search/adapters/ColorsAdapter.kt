package com.example.appstore.ui.search.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.data.model.search.ResponseSearch
import com.example.appstore.databinding.ItemColorsListBinding
import javax.inject.Inject

class ColorsAdapter @Inject constructor() : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {


    lateinit var binding: ItemColorsListBinding

    private var colors= emptyList<ResponseSearch.Products.Data.Color>()

    inner class ColorViewHolder(item: View) : RecyclerView.ViewHolder(item) {

fun onBind(oneItem:ResponseSearch.Products.Data.Color){

    binding.itemsColor.setBackgroundColor(Color.parseColor(oneItem.hexCode))



}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        binding=ItemColorsListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ColorViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {

        holder.onBind(colors[position])

    }

    override fun getItemCount()=colors.size

    override fun getItemId(position: Int)=position.toLong()

    override fun getItemViewType(position: Int)=position


    fun setData(data:List<ResponseSearch.Products.Data.Color>){

        val diff=DiffUtils(colors,data)

        val diffU=DiffUtil.calculateDiff(diff)

        colors=data

        diffU.dispatchUpdatesTo(this)




    }

    class DiffUtils(
        private val oldItem: List<ResponseSearch.Products.Data.Color>,
        private val newItem: List<ResponseSearch.Products.Data.Color>
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