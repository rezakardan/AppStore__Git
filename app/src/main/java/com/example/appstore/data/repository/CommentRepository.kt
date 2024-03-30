package com.example.appstore.data.repository

import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class CommentRepository @Inject constructor(private val api:ApiService){

suspend fun getComments()=api.getComment()

    suspend fun deleteComments(id:Int)=api.deleteComments(id)


}