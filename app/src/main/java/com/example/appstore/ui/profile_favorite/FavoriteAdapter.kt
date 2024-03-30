package com.example.appstore.ui.profile_favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appstore.R
import com.example.appstore.data.model.favorite.ResponseGetFavorite
import com.example.appstore.databinding.ItemMyCommentsBinding
import com.example.appstore.databinding.ItemMyFavoritesBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import com.example.appstore.utils.moneySeparating
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FavoriteAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favorites = emptyList<ResponseGetFavorite.Data>()


    lateinit var binding: ItemMyFavoritesBinding

    inner class FavoriteViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun onBind(oneItem: ResponseGetFavorite.Data) {

            oneItem.likeable?.let {
                binding.itemTitle.text = it.title


                val imageUrl = "$BASE_URL_IMAGE${it.image}"
                binding.itemImg.load(imageUrl)




                binding.quantityTitle.text = "${it.quantity} ${context.getString(R.string.item)}"

                binding.priceTxt.text = it.finalPrice!!.toInt().moneySeparating()



            }




            binding.itemTrashImg.setOnClickListener {

                favoriteListener?.let {

                    it(oneItem.id!!)


                }


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        binding = ItemMyFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.onBind(favorites[position])
    }

    override fun getItemCount() = favorites.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    fun setData(data: List<ResponseGetFavorite.Data>) {

        val commentDiff = CommentDiffUtils(favorites, data)

        val diff = DiffUtil.calculateDiff(commentDiff)

        favorites = data

        diff.dispatchUpdatesTo(this)


    }


    private var favoriteListener: ((Int) -> Unit)? = null

    fun onFavoriteItemClickListener(listener: (Int) -> Unit) {


        favoriteListener = listener


    }


    class CommentDiffUtils(
        private val oldItem: List<ResponseGetFavorite.Data>,
        private val newItem: List<ResponseGetFavorite.Data>
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