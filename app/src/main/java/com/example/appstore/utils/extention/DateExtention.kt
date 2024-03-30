package com.example.appstore.utils.extention

import com.example.appstore.utils.views.TimeUtils

fun String.convertDateToPersian(): String {


    val dateSplit = this.split("-")


    val timeUtils = TimeUtils(dateSplit[0].toInt(), dateSplit[1].toInt(), dateSplit[2].toInt())



    return timeUtils.iranianDate

}