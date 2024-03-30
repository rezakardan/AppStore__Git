package com.example.appstore.utils.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import com.example.appstore.R
import com.example.appstore.utils.moneySeparatingToolTip
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

@SuppressLint("ViewConstructor")
class CustomToolTip(context: Context, layout: Int, private val dateToDisplay: MutableList<String>) :
    MarkerView(context, layout) {


    private var dateTxt: TextView? = null

    private var priceTxt: TextView? = null

    init {


        dateTxt = findViewById(R.id.dateTxt)

        priceTxt = findViewById(R.id.countTxt)


    }

    override fun refreshContent(e: Entry, highlight: Highlight) {


        try {

            val index = e.x.toInt()
            dateTxt?.text = dateToDisplay[index]

            dateTxt?.typeface = Typeface.createFromAsset(context.assets, "fonts/atlas_regular.ttf")

            priceTxt?.text = highlight.y.toInt().moneySeparatingToolTip()

            priceTxt?.typeface = Typeface.createFromAsset(context.assets, "fonts/atlas_regular.ttf")

        } catch (e: Exception) {


            e.printStackTrace()


        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -height.toFloat())
    }

}