package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.comment.ResponseDeleteComments
import com.example.appstore.data.model.comment.ResponseGetComment
import com.example.appstore.data.repository.CommentRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CommentsViewModel@Inject constructor(private val repository:CommentRepository):ViewModel() {

    val getCommentLiveData=MutableLiveData<NetworkRequest<ResponseGetComment>>()

    fun callGetCommentApi()=viewModelScope.launch {

        getCommentLiveData.value=NetworkRequest.Loading()

        val response=repository.getComments()

        getCommentLiveData.value=NetworkResponse(response).generateResponse()





    }



    val deleteCommentsLiveData=MutableLiveData<NetworkRequest<ResponseDeleteComments>>()

    fun callDeleteCommentApi(commentId:Int)=viewModelScope.launch {

        deleteCommentsLiveData.value=NetworkRequest.Loading()

        val response=repository.deleteComments(commentId)

        deleteCommentsLiveData.value=NetworkResponse(response).generateResponse()








    }


}