package com.example.appstore.utils.extention

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import com.example.appstore.R
import com.example.appstore.utils.views.CustomToolTip
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

fun LineChart.setUpMyChart(formatter:IndexAxisValueFormatter,entry:ArrayList<Entry>,count:Int,list:MutableList<String>){

with(this){

    legend.isEnabled=false
    setTouchEnabled(true)
    isDragEnabled=false
setScaleEnabled(false)
setPinchZoom(false)
    animateX(1000)
    description.isEnabled=false
    axisRight.isEnabled=false
    extraRightOffset=   context.resources.getDimension(`in`.nouri.dynamicsizeslib.R.dimen._8mdp)
    extraLeftOffset=context.resources.getDimension(`in`.nouri.dynamicsizeslib.R.dimen._8mdp)


    axisLeft.apply {

        setDrawLabels(false)
        setDrawGridLines(true)
        gridColor=ContextCompat.getColor(context, R.color.lavender)

        setDrawAxisLine(false)

        axisMinimum=0f
        axisMaximum=50000000f





    }

    xAxis.apply {

    labelCount=count - 1

        axisMaximum=0f
        axisMaximum=(count-1).toFloat()

        isGranularityEnabled=false
        granularity=1f
        position=XAxis.XAxisPosition.BOTTOM
        valueFormatter=formatter
        typeface= Typeface.createFromAsset(context.assets,"fonts/atlas_regular.ttf")

textSize=9.5f
        setDrawGridLines(false)
        setDrawAxisLine(true)
        axisLineColor=ContextCompat.getColor(context,R.color.lavender)


    }

val toolTip=CustomToolTip(context,R.layout.custom_chart_tooltip,list)
    marker=toolTip
    data= lineChartDataSet(entry,context)
    invalidate()

}





}



private fun lineChartDataSet(list:ArrayList<Entry>,context: Context):LineData{

val lineDataSet=LineDataSet(list,"Data").apply {


  lineWidth=2f
    valueTextSize=15f
    mode=LineDataSet.Mode.LINEAR
    color=ContextCompat.getColor(context,R.color.royalBlue)
    setDrawValues(false)
    valueTextColor=ContextCompat.getColor(context,R.color.eerieBlack)
    setDrawCircleHole(true)
    circleHoleRadius=4f

    circleRadius=5f
    setCircleColor(ContextCompat.getColor(context,R.color.royalBlue))
    fillAlpha=100
    setDrawHighlightIndicators(false)





}

val dataSet=ArrayList<ILineDataSet>()
    dataSet.add(lineDataSet)
return LineData(dataSet)


}