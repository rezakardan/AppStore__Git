package com.example.appstore.ui.profile_comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appstore.data.model.comment.ResponseGetComment
import com.example.appstore.databinding.ItemCommentBinding
import com.example.appstore.databinding.ItemMyCommentsBinding
import com.example.appstore.utils.BASE_URL_IMAGE
import com.example.appstore.utils.BASE_URL_IMAGE_WITH_STORAGE
import javax.inject.Inject

class CommentAdapter @Inject constructor() :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var comments = emptyList<ResponseGetComment.Data>()


    lateinit var binding: ItemMyCommentsBinding

    inner class CommentViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun onBind(oneItem: ResponseGetComment.Data) {

            oneItem.product?.let {
                binding.itemTitle.text = it.title


                val imageUrl = "$BASE_URL_IMAGE${it.image}"
                binding.itemImg.load(imageUrl)
            }

            binding.itemRating.rating = oneItem.rate.toString().toFloat()

            binding.itemCommentTxt.text = oneItem.comment





            binding.itemTrashImg.setOnClickListener {

                commentListener?.let {

                    it(oneItem.id!!)


                }


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        binding = ItemMyCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.onBind(comments[position])
    }

    override fun getItemCount() = comments.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    fun setData(data: List<ResponseGetComment.Data>) {

        val commentDiff = CommentDiffUtils(comments, data)

        val diff = DiffUtil.calculateDiff(commentDiff)

        comments = data

        diff.dispatchUpdatesTo(this)


    }


    private var commentListener: ((Int) -> Unit)? = null

    fun onCommentItemClickListener(listener: (Int) -> Unit) {


        commentListener = listener


    }


    class CommentDiffUtils(
        private val oldItem: List<ResponseGetComment.Data>,
        private val newItem: List<ResponseGetComment.Data>
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