package com.alexvoz.cvirus.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.data.country_data.CountryData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.NumberFormat
import java.text.SimpleDateFormat

class AppLineChart : LineChart {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    fun setData(countryData: List<CountryData>) {
        val confirmedDataValues: ArrayList<Entry> = ArrayList()
        for (i in countryData.indices) {
            confirmedDataValues.add(Entry(i.toFloat(), countryData[i].confirmed.toFloat()))
        }
        val confirmedDataSet = LineDataSet(confirmedDataValues, "confirmedDataSet")
        confirmedDataSet.setupFormat(R.color.bg_chart_gray, context)
        confirmedDataSet.color = ContextCompat.getColor(context, R.color.dark_gray)
        confirmedDataSet.fillFormatter = IFillFormatter { _, _ -> this.axisLeft.axisMinimum }

        val deathsDataValues: ArrayList<Entry> = ArrayList()
        for (i in countryData.indices) {
            deathsDataValues.add(Entry(i.toFloat(), countryData[i].deaths.toFloat()))
        }
        val deathsDataSet = LineDataSet(deathsDataValues, "deathsDataSet")
        deathsDataSet.setupFormat(R.color.colorSecondaryLight, context)
        deathsDataSet.color = ContextCompat.getColor(context, R.color.colorSecondary)
        deathsDataSet.fillFormatter = IFillFormatter { _, _ -> this.axisLeft.axisMinimum }

        val recoveredDataValues: ArrayList<Entry> = ArrayList()
        for (i in countryData.indices) {
            recoveredDataValues.add(Entry(i.toFloat(), countryData[i].recovered.toFloat()))
        }
        val recoveredDataSet = LineDataSet(recoveredDataValues, "recoveredDataSet")
        recoveredDataSet.setupFormat(R.color.color_green_recovered, context)
        recoveredDataSet.fillFormatter = IFillFormatter { _, _ -> this.axisLeft.axisMinimum }

        val resultData = LineData(confirmedDataSet,deathsDataSet,recoveredDataSet)
        resultData.setValueTextSize(9f)

        //Setups applying
        this.data = resultData
        this.description.isEnabled = false
        this.setDrawGridBackground(false)
        this.setScaleEnabled(false)
        this.setTouchEnabled(false)

        //X, Y axis setups


        val x: XAxis = this.xAxis
        x.setLabelCount(8, true)
        x.setDrawGridLines(false)
        x.textColor = Color.BLACK
        x.axisLineColor = Color.BLACK
        x.position = XAxis.XAxisPosition.BOTTOM
        x.textSize = 13f
        x.axisLineWidth = 0.6f
        x.valueFormatter = ChartValueFormatter(countryData)

        val y: YAxis = this.axisLeft
        y.setLabelCount(5, false)
        y.setDrawGridLines(false)
        y.textColor = Color.BLACK
        y.axisLineColor = Color.BLACK
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        y.textSize = 13f
        y.axisLineWidth = 0.6f
        y.valueFormatter = ChartValueFormatter()

        this.axisRight.isEnabled = false
        this.legend.isEnabled = false
        this.animateXY(1500, 1500)
        this.extraBottomOffset = 5f

        //Update chart
        this.invalidate()
    }

    private fun LineDataSet.setupFormat(colorSrc: Int, context: Context) {
        this.mode = LineDataSet.Mode.CUBIC_BEZIER
        this.cubicIntensity = 0.4f
        this.lineWidth = 2f
        this.fillAlpha = 100
        this.setDrawFilled(true)
        this.setDrawCircles(false)
        this.setDrawValues(false)
        this.setDrawHorizontalHighlightIndicator(false)

        this.setCircleColor(Color.WHITE)
        this.highLightColor = ContextCompat.getColor(context, colorSrc)
        this.color = ContextCompat.getColor(context, colorSrc)
        this.fillColor = ContextCompat.getColor(context, colorSrc)
    }
}

class ChartValueFormatter(private val countryData: List<CountryData>? = null) : ValueFormatter() {
    override fun getPointLabel(entry: Entry?): String {
        return entry?.y.toString()
    }

    override fun getBarLabel(barEntry: BarEntry?): String {
        return barEntry?.y.toString()
    }

    @SuppressLint("SimpleDateFormat")
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return if (countryData != null) {
            if (countryData.size > value) {
                val date = SimpleDateFormat("yyyy-MM-dd").parse(countryData[value.toInt()].date)
                if (date != null) SimpleDateFormat("MMM").format(date) else ""
            } else ""
        } else NumberFormat.getInstance().format(value)
    }
}
