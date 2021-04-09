package com.alexvoz.cvirus.presentation.covid_data

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.data.covid_data.network.Global
import com.alexvoz.cvirus.presentation.main.SharedViewModel
import com.alexvoz.cvirus.util.getNumberWithSpaces
import com.hookedonplay.decoviewlib.charts.DecoDrawEffect
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.charts.SeriesItem.SeriesItemListener
import com.hookedonplay.decoviewlib.events.DecoEvent
import kotlinx.android.synthetic.main.block_today_data.view.*
import kotlinx.android.synthetic.main.fragment_global_data.*
import kotlinx.coroutines.*


@InternalCoroutinesApi
class CovidGlobalDataFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var circleTotalDataBgIndex = 0
    private var casesCircleDataIndex = 0
    private var recoveredCircleDataIndex = 0
    private var deathsCircleDataIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_global_data, container, false)
    }

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        sharedViewModel.covidData.observe(viewLifecycleOwner, {
            it?.let { covidData -> setData(covidData.global) }
        })
    }

    private fun setData(global: Global) {
        iTodayDataBlock.tvNewCases.text = global.newConfirmed.getNumberWithSpaces()
        iTodayDataBlock.tvRecovered.text = global.newRecovered.getNumberWithSpaces()
        iTodayDataBlock.tvDeaths.text = global.newDeaths.getNumberWithSpaces()

        initCircleTotalData((global.totalConfirmed * 1.40).toFloat())

        startCircleTotalDataAnimation(
            global.totalConfirmed,
            global.totalDeaths,
            global.totalRecovered
        )

        mlGlobalData.transitionToEnd()
    }


    private fun initCircleTotalData(maxValue: Float) {

        dvGlobalDataCircleTotalData.deleteAll()
        dvGlobalDataCircleTotalData.configureAngles(360, 180)

        val circleTotalDataBg =
            SeriesItem.Builder(ContextCompat.getColor(requireContext(), R.color.bg_global_data_pie))
                .setRange(0f, maxValue, maxValue)
                .setInitialVisibility(false)
                .setLineWidth(150f)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .build()
        circleTotalDataBgIndex = dvGlobalDataCircleTotalData.addSeries(circleTotalDataBg)


        val casesCircleData =
            SeriesItem.Builder(ContextCompat.getColor(requireContext(), R.color.color_orange_cases))
                .setRange(0f, maxValue, 0f)
                .setInitialVisibility(false)
                .setLineWidth(70f)
                .setInset(PointF(-40f, -40f))
                .setSpinClockwise(true)
                .setCapRounded(true)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .build()
        casesCircleDataIndex = dvGlobalDataCircleTotalData.addSeries(casesCircleData)


        val recoveredCircleData =
            SeriesItem.Builder(
                ContextCompat.getColor(requireContext(), R.color.color_green_recovered)
            )
                .setRange(0f, maxValue, 0f)
                .setInitialVisibility(false)
                .setCapRounded(true)
                .setLineWidth(40f)
                .setInset(PointF(20f, 20f))
                .setCapRounded(true)
                .build()
        recoveredCircleDataIndex = dvGlobalDataCircleTotalData.addSeries(recoveredCircleData)


        val deathsCircleData =
            SeriesItem.Builder(ContextCompat.getColor(requireContext(), R.color.color_red_deaths))
                .setRange(0f, maxValue, 0f)
                .setInitialVisibility(false)
                .setCapRounded(true)
                .setLineWidth(30f)
                .setInset(PointF(60f, 60f))
                .setCapRounded(true)
                .build()
        deathsCircleDataIndex = dvGlobalDataCircleTotalData.addSeries(deathsCircleData)


        addProgressListener(casesCircleData, tvGlobalDataCases)
        addProgressListener(recoveredCircleData, tvGlobalDataRecovered)
        addProgressListener(deathsCircleData, tvGlobalDataDeaths)
    }

    private fun startCircleTotalDataAnimation(cases: Int, deaths: Int, recovered: Int) {

        val textViewsForProgressListener =
            arrayOf(tvGlobalDataCases, tvGlobalDataRecovered, tvGlobalDataDeaths)
        val fadeDuration = 2000L

        dvGlobalDataCircleTotalData.addEvent(
            DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setIndex(circleTotalDataBgIndex)
                .setDuration(fadeDuration)
                .build()
        )

        dvGlobalDataCircleTotalData.addEvent(
            DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(casesCircleDataIndex)
                .setFadeDuration(fadeDuration)
                .setDuration(1500)
                .setLinkedViews(textViewsForProgressListener)
                .setDelay(1000)
                .build()
        )

        dvGlobalDataCircleTotalData.addEvent(
            DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(recoveredCircleDataIndex)
                .setFadeDuration(fadeDuration)
                .setDuration(1500)
                .setLinkedViews(textViewsForProgressListener)
                .setDelay(1100)
                .build()
        )

        dvGlobalDataCircleTotalData.addEvent(
            DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(deathsCircleDataIndex)
                .setFadeDuration(fadeDuration)
                .setLinkedViews(textViewsForProgressListener)
                .setDuration(1500)
                .setDelay(1200)
                .build()
        )

        dvGlobalDataCircleTotalData.addEvent(
            DecoEvent.Builder(cases.toFloat()).setIndex(casesCircleDataIndex).setDelay(3000).build()
        )
        dvGlobalDataCircleTotalData.addEvent(
            DecoEvent.Builder(recovered.toFloat()).setIndex(recoveredCircleDataIndex).setDelay(6000)
                .build()
        )
        dvGlobalDataCircleTotalData.addEvent(
            DecoEvent.Builder(deaths.toFloat()).setIndex(deathsCircleDataIndex).setDelay(9000)
                .build()
        )
    }

    private fun addProgressListener(
        seriesItem: SeriesItem,
        view: TextView
    ) {
        seriesItem.addArcSeriesItemListener(object : SeriesItemListener {
            override fun onSeriesItemAnimationProgress(
                percentComplete: Float,
                currentPosition: Float
            ) {
                view.text = currentPosition.getNumberWithSpaces()
            }

            override fun onSeriesItemDisplayProgress(percentComplete: Float) {}
        })
    }
}