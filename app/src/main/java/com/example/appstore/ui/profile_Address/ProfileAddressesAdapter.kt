package com.example.appstore.ui.profile_Address

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.R
import com.example.appstore.data.profile_address.ResponseProfileAddresses
import com.example.appstore.databinding.ItemAddressesBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProfileAddressesAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<ProfileAddressesAdapter.AddressViewHolder>() {

    lateinit var binding: ItemAddressesBinding

    private var address = emptyList<ResponseProfileAddresses.ResponseProfileAddressesItem>()

    inner class AddressViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(oneItem: ResponseProfileAddresses.ResponseProfileAddressesItem) {


            binding.nameTxt.text = "${oneItem.receiverFirstname} ${oneItem.receiverLastname}"


            binding.provinceTxt.text = oneItem.province?.title

            binding.cityTxt.text = oneItem.city?.title

            binding.locationTxt.text =
                "${oneItem.postalAddress} - ${context.getString(R.string.plateNumber)} ${oneItem.plateNumber}  ${
                    context.getString(R.string.floor)
                } ${oneItem.floor}"

            binding.postalTxt.text = oneItem.postalCode

            binding.phoneTxt.text = oneItem.receiverCellphone

            binding.root.setOnClickListener {

                onAddressItemListener?.let {

                    it(oneItem)


                }


            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        binding = ItemAddressesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.onBind(address[position])
    }

    override fun getItemCount() = address.size


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    private var onAddressItemListener: ((ResponseProfileAddresses.ResponseProfileAddressesItem) -> Unit)? = null

    fun setOnAddressClickListener(listener: (ResponseProfileAddresses.ResponseProfileAddressesItem) -> Unit) {

        onAddressItemListener = listener


    }


    fun setData(data: List<ResponseProfileAddresses.ResponseProfileAddressesItem>) {

        val diffUtilInput = AddressDiffUtils(address, data)

        val diff = DiffUtil.calculateDiff(diffUtilInput)

        address = data

        diff.dispatchUpdatesTo(this)


    }

    class AddressDiffUtils(
        private val oldItem: List<ResponseProfileAddresses.ResponseProfileAddressesItem>,
        private val newItem: List<ResponseProfileAddresses.ResponseProfileAddressesItem>
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