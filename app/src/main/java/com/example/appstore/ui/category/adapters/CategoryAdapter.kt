package com.example.appstore.ui.category.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.data.model.category.ResponseCategory
import com.example.appstore.databinding.ItemCategoriesBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CategoryAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    lateinit var binding: ItemCategoriesBinding

    private var categories = emptyList<ResponseCategory.ResponseCategoryItem>()

    inner class CategoryViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun onBind(oneItem: ResponseCategory.ResponseCategoryItem) {

            binding.itemTitle.text = oneItem.title

if (oneItem.subCategories!!.isNotEmpty()){

    binding.itemSubCatsList.visibility=View.VISIBLE

initSubCategoryAdapter(oneItem.subCategories)

}else{

    binding.itemSubCatsList.visibility=View.GONE


}
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.onBind(categories[position])

    }

    override fun getItemCount() = categories.size


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    private var categoryListener: ((String) -> Unit)? = null

    fun onCategoryAdapterClicked(listener: (String) -> Unit) {

        categoryListener = listener


    }


    private fun initSubCategoryAdapter(data: List<ResponseCategory.ResponseCategoryItem.SubCategory>) {

        val subAdapter = SubCategoryAdapter()

        subAdapter.setData(data)

        binding.itemSubCatsList.adapter = subAdapter

        binding.itemSubCatsList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)

subAdapter.getSubSlug { slug->


categoryListener?.let {

    it(slug)




}




}
    }


    fun setData(data: List<ResponseCategory.ResponseCategoryItem>) {

        val diff = AdapterDiffUtils(categories, data)

        val diffU = DiffUtil.calculateDiff(diff)

        categories = data

        diffU.dispatchUpdatesTo(this)


    }


    class AdapterDiffUtils(
        private val oldItem: List<ResponseCategory.ResponseCategoryItem>,
        private val newItem: List<ResponseCategory.ResponseCategoryItem>
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