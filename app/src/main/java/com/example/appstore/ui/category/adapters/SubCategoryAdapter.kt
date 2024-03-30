package com.example.appstore.ui.category.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.data.model.category.ResponseCategory
import com.example.appstore.databinding.ItemCategoriesSubBinding
import javax.inject.Inject

class SubCategoryAdapter@Inject constructor():RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {


    lateinit var binding: ItemCategoriesSubBinding

    private var subList= emptyList<ResponseCategory.ResponseCategoryItem.SubCategory>()

    inner class SubCategoryViewHolder(item:View):RecyclerView.ViewHolder(item){


        fun onBind(oneItem:ResponseCategory.ResponseCategoryItem.SubCategory){

            binding.itemTitle.text=oneItem.title


            binding.root.setOnClickListener {

                sendSubSlugListener?.let {

                    it(oneItem.slug.toString())



                }




            }


        }





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        binding= ItemCategoriesSubBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SubCategoryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {

        holder.onBind(subList[position])
    }

    override fun getItemCount()=subList.size

    override fun getItemId(position: Int)=position.toLong()

    override fun getItemViewType(position: Int)=position





    private var sendSubSlugListener:((String)->Unit)?=null

    fun getSubSlug(listener:(String)->Unit){

        sendSubSlugListener=listener




    }




    fun setData(data:List<ResponseCategory.ResponseCategoryItem.SubCategory>){

        val diff= AdapterDiffUtils(subList,data)

        val diffU=DiffUtil.calculateDiff(diff)

        subList=data

        diffU.dispatchUpdatesTo(this)





    }

    class AdapterDiffUtils(
        private val oldItem: List<ResponseCategory.ResponseCategoryItem.SubCategory>,
        private val newItem: List<ResponseCategory.ResponseCategoryItem.SubCategory>
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