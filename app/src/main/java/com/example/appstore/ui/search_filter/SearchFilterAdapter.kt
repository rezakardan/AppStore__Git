package com.example.appstore.ui.search_filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.data.model.search.FilterModel
import com.example.appstore.databinding.ItemFilterBinding
import javax.inject.Inject

class SearchFilterAdapter @Inject constructor() :
    RecyclerView.Adapter<SearchFilterAdapter.SearchFilterViewHolder>() {

    private var filterList = emptyList<FilterModel>()

    lateinit var binding: ItemFilterBinding

    inner class SearchFilterViewHolder(item: View) : RecyclerView.ViewHolder(item) {

fun onBind(oneItem:FilterModel){

binding.itemTitle.text=oneItem.faName



    binding.root.setOnClickListener {


        searchFilterListener?.let {
            it(oneItem.enName)


        }




    }
}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilterViewHolder {
        binding= ItemFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchFilterViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchFilterViewHolder, position: Int) {
        holder.onBind(filterList[position])

    }

    override fun getItemCount()=filterList.size


private var searchFilterListener:((String)->Unit)?=null


    fun setOnSearchFilter(listener:(String)->Unit){

        searchFilterListener=listener



    }


    fun setData(data: List<FilterModel>) {
        val diff = DiffUtils(filterList, data)

        val diffU = DiffUtil.calculateDiff(diff)

        filterList = data

        diffU.dispatchUpdatesTo(this)


    }


    class DiffUtils(
        private val oldItem: List<FilterModel>,
        private val newItem: List<FilterModel>
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