package com.example.appstore.ui.detail_comment_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appstore.R
import com.example.appstore.data.model.detail.BodyDetailAddComment
import com.example.appstore.databinding.FragmentCommentAddBinding
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.viewmodels.DetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommentAddFragment : BottomSheetDialogFragment() {


    override fun getTheme() = R.style.RemoveBottomSheetBackground


    lateinit var binding: FragmentCommentAddBinding

    private val args by navArgs<CommentAddFragmentArgs>()

    private var productId = 0


    private val viewModel by viewModels<DetailsViewModel>()

    @Inject
    lateinit var bodyAddComment: BodyDetailAddComment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentAddBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeImg.setOnClickListener { this@CommentAddFragment.dismiss() }
        args.let {

            productId = it.id


        }


        binding.submitBtn.setOnClickListener {

            if (binding.commentEdt.text.isNotEmpty()) {

                bodyAddComment.comment = binding.commentEdt.toString()
                bodyAddComment.rate = binding.rateSlider.value.toInt().toString()

                viewModel.postDetailAddComments(productId,bodyAddComment)

            } else {
                Snackbar.make(binding.root, getString(R.string.notEmptyComment),Snackbar.LENGTH_SHORT).show()
            }




        }





        loadAddDetailComment()

    }



    fun loadAddDetailComment(){


        viewModel.detailAddCommentLiveData.observe(viewLifecycleOwner){response->


            when(response){

                is NetworkRequest.Loading->{

                }

                is NetworkRequest.Success->{
                    response.data?.let { data->


                        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()

                        this@CommentAddFragment.dismiss()





                    }
                }


                is NetworkRequest.Error->{
                    Snackbar.make(binding.root,response.message.toString(),Snackbar.LENGTH_SHORT).show()
                }




            }







        }





    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.detailAddCommentLiveData.removeObservers(viewLifecycleOwner)
    }


}