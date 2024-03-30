package com.example.appstore.ui.profile_comment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentProfileCommentBinding
import com.example.appstore.utils.base.BaseFragment
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.CommentsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileCommentFragment : BaseFragment() {


    lateinit var binding: FragmentProfileCommentBinding

    private val viewModel by viewModels<CommentsViewModel>()

    @Inject
    lateinit var commentAdapter: CommentAdapter


    private var recyclerViewState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileCommentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isNetworkAvailable) {
            viewModel.callGetCommentApi()
        }


        binding.toolbar.apply {

            toolbarBackImg.setOnClickListener { findNavController().popBackStack() }

            toolbarOptionImg.visibility = View.GONE

            toolbarTitleTxt.text = getString(R.string.yourComments)

        }
        loadCommentsApi()
        loadDeleteCommentApi()
    }


    private fun loadCommentsApi() {


        viewModel.getCommentLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.commentsList.showShimmer()
                }


                is NetworkRequest.Success -> {
                    binding.commentsList.hideShimmer()



                    response.data?.let { data ->

                        if (data.data.isNotEmpty()) {


                            commentAdapter.setData(data.data)
                            initCommentAdapter()
                        } else {


                            binding.emptyLay.visibility = View.VISIBLE

                            binding.commentsList.visibility = View.GONE
                        }


                    }
                }


                is NetworkRequest.Error -> {
                    binding.commentsList.hideShimmer()

                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }

    private fun initCommentAdapter() {

        binding.commentsList.adapter = commentAdapter
        binding.commentsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.commentsList.layoutManager?.onRestoreInstanceState(recyclerViewState)


        commentAdapter.onCommentItemClickListener {


            recyclerViewState=binding.commentsList.layoutManager?.onSaveInstanceState()


            if (isNetworkAvailable) {
                viewModel.callDeleteCommentApi(it)
            }
        }

    }


    private fun loadDeleteCommentApi() {

        viewModel.deleteCommentsLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {


                is NetworkRequest.Loading -> {

                }
                is NetworkRequest.Success -> {


                    response.data?.let {

                        if (isNetworkAvailable) {

                            viewModel.callGetCommentApi()
                        }


                    }
                }


                is NetworkRequest.Error -> {


                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }


        }


    }
}