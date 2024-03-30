package com.example.appstore.ui.detail_comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.data.model.detail.BodyDetailAddComment
import com.example.appstore.data.model.detail.ResponseGetDetailComment
import com.example.appstore.databinding.FragmentDetailCommentBinding
import com.example.appstore.ui.detail.DetailFragmentDirections
import com.example.appstore.ui.profile_comment.CommentAdapter
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailCommentFragment : BaseFragment() {

    lateinit var binding: FragmentDetailCommentBinding

    private val viewModel by activityViewModels<DetailsViewModel>()

    @Inject
    lateinit var commentAdapter: DetailCommentsAdapter

    private var productId = 0




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.productIdLiveData.observe(viewLifecycleOwner) {
            productId = it
            if (isNetworkAvailable) {
                viewModel.detailComments(it)

            }


        }

        loadDetailComments()



        binding.addNewCommentTxt.setOnClickListener {

            val direction=DetailFragmentDirections.actionDetailFragmentToCommentAddFragment(productId)
            findNavController().navigate(direction)

        }

    }


    private fun loadDetailComments() {

        viewModel.detailCommentLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {


                is NetworkRequest.Loading -> {
                    binding.commentsLoading.visibility = View.VISIBLE
                    binding.commentsList.visibility = View.GONE
                }


                is NetworkRequest.Success -> {
                    binding.commentsLoading.visibility = View.GONE
                    binding.commentsList.visibility = View.VISIBLE

                    response.data?.let { data ->

                        if (data.data!!.isNotEmpty()) {

                            binding.emptyLay.visibility = View.GONE

                            commentAdapter.setData(data.data)

                            initRecycler()
                        } else {
                            binding.emptyLay.visibility = View.VISIBLE


                        }


                    }


                }


                is NetworkRequest.Error -> {
                    binding.commentsLoading.visibility = View.GONE
                    binding.commentsList.visibility = View.VISIBLE
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }

        }


    }


    private fun initRecycler() {

        binding.commentsList.adapter = commentAdapter

        binding.commentsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)


    }


}