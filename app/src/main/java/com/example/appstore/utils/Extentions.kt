package com.example.appstore.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.text.DecimalFormat

fun Int.moneySeparating(): String {
    return "${DecimalFormat("#,###.##").format(this)} تومان"
}

fun Int.moneySeparatingToolTip(): String {
    return "${DecimalFormat("#,###.##").format(this)} "
}







@SuppressLint("Range")
fun getRealFileFromUri(context: Context, uri: Uri):String?{

   var result:String?=null


    val resolver=context.contentResolver.query(uri,null,null,null,null)


    if (resolver==null){

        result =uri.path
    }else{


        if (resolver.moveToFirst()){


            result=resolver.getString(resolver.getColumnIndex(MediaStore.Images.ImageColumns.DATA))


        }

        resolver.close()
    }

return result
}