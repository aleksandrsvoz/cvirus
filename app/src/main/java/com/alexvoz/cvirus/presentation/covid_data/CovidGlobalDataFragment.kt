package com.alexvoz.cvirus.presentation.covid_data

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.data.covid_data.network.Global
import com.alexvoz.cvirus.util.SharedViewModel
import com.alexvoz.cvirus.util.getNumberWithSpaces
import com.hookedonplay.decoviewlib.charts.DecoDrawEffect
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.charts.SeriesItem.SeriesItemListener
import com.hookedonplay.decoviewlib.events.DecoEvent
import kotlinx.android.synthetic.main.fragment_country_data.*
import kotlinx.android.synthetic.main.fragment_global_data.*
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
class CovidGlobalDataFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_global_data, container, false)
    }

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.covidData.observe(viewLifecycleOwner, {
            it?.let { covidData -> setData(covidData.global) }
        })

//        val seriesItem1 = SeriesItem.Builder(requireContext().getColor(R.color.colorPrimary))
//            .setRange(0f, 100f, 0f)
//            .setInitialVisibility(false)
//            .setLineWidth(70f)
//            .setSeriesLabel(SeriesLabel.Builder("Percent %.0f%%").build())
//            .setInterpolator(OvershootInterpolator())
//            .setShowPointWhenEmpty(true)
//            .setCapRounded(true)
//            .setInset(PointF(32f, 32f))
//            .setDrawAsPoint(false)
//            .setInitialVisibility(true)
//            .setSpinClockwise(true)
//            .setSpinDuration(6000)
//            .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
//            .build()
//
//        val a = dynamicArcView.addSeries(seriesItem1)
//
//        dynamicArcView.addEvent(DecoEvent.Builder(70f).setIndex(a).setDelay(1000).build())


    }


    private fun setData(global: Global) {
        tvGlobalTodayCases.text = global.newConfirmed.getNumberWithSpaces()
        tvGlobalRecoveredToday.text = global.newRecovered.getNumberWithSpaces()
        tvGlobalDiedToday.text = global.newDeaths.getNumberWithSpaces()

        createTracks((global.totalConfirmed * 1.40).toFloat())
        setupEvents(global.totalConfirmed, global.totalDeaths, global.totalRecovered)
    }


    private var mBackIndex = 0
    private var mSeries1Index = 0
    private var mSeries2Index = 0
    private var mSeries3Index = 0
    private var mUpdateListeners = true

    @SuppressLint("NewApi")
    fun createTracks(max: Float) {


        val view = view
        if (dynamicArcView == null || view == null) {
            return
        }
        dynamicArcView.deleteAll()
        dynamicArcView.configureAngles(360, 180)


        val arcBackTrack = SeriesItem.Builder(requireContext().getColor(R.color.global_data_pie_bg))
            .setRange(0f, max, max)
            .setInitialVisibility(false)
            .setLineWidth(150f)
            .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
            .build()

        mBackIndex = dynamicArcView.addSeries(arcBackTrack)


        val seriesItem1 = SeriesItem.Builder(requireContext().getColor(R.color.orange))
            .setRange(0f, max, 0f)
            .setInitialVisibility(false)
            .setLineWidth(70f)
            .setInset(PointF(-40f, -40f))
            .setSpinClockwise(true)
            .setCapRounded(true)
            .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
            .build()

        mSeries1Index = dynamicArcView.addSeries(seriesItem1)

        val seriesItem2 = SeriesItem.Builder(requireContext().getColor(R.color.green))
            .setRange(0f, max, 0f)
            .setInitialVisibility(false)
            .setCapRounded(true)
            .setLineWidth(40f)
            .setInset(PointF(20f, 20f))
            .setCapRounded(true)
            .build()

        mSeries2Index = dynamicArcView.addSeries(seriesItem2)

        val seriesItem3 = SeriesItem.Builder(requireContext().getColor(R.color.red))
            .setRange(0f, max, 0f)
            .setInitialVisibility(false)
            .setCapRounded(true)
            .setLineWidth(30f)
            .setInset(PointF(60f, 60f))
            .setCapRounded(true)
            .build()

        mSeries3Index = dynamicArcView.addSeries(seriesItem3)



        addProgressListener(seriesItem1, tvGlobalDataCases)
        addProgressListener(seriesItem2, tvGlobalDataRecovered)
        addProgressListener(seriesItem3, tvGlobalDataDeaths)


    }

    fun setupEvents(cases: Int, deaths: Int, recovered: Int) {

        val view = view
        if (dynamicArcView == null || dynamicArcView.isEmpty || view == null) {
            return
        }

        mUpdateListeners = true


        val linkedViews = arrayOf(tvGlobalDataCases, tvGlobalDataRecovered, tvGlobalDataDeaths)
        val fadeDuration = 2000L


        dynamicArcView.addEvent(
            DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setIndex(mBackIndex)
                .setDuration(fadeDuration)
                .build()
        )

        dynamicArcView.addEvent(
            DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(mSeries1Index)
                .setFadeDuration(fadeDuration)
                .setDuration(1500)
                .setLinkedViews(linkedViews)
                .setDelay(1000)
                .build()
        )

        dynamicArcView.addEvent(
            DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(mSeries2Index)
                .setFadeDuration(fadeDuration)
                .setDuration(1500)
                .setLinkedViews(linkedViews)
                .setDelay(1100)
                .build()
        )

        dynamicArcView.addEvent(
            DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(mSeries3Index)
                .setFadeDuration(fadeDuration)
                .setLinkedViews(linkedViews)
                .setDuration(1500)
                .setDelay(1200)
                .build()
        )


//        dynamicArcView.addEvent(
//            DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
//                .setIndex(mSeries2Index)
//                .setLinkedViews(linkedViews)
//                .setDuration(1500)
//                .setDelay(1100)
//                .build()
//        )


//        dynamicArcView.addEvent(
//            DecoEvent.Builder(10f).setIndex(mSeries2Index).setDelay(3900).build()
//        )
//        dynamicArcView.addEvent(
//            DecoEvent.Builder(22f).setIndex(mSeries2Index).setDelay(7000).build()
//        )
//
//        dynamicArcView.addEvent(
//            DecoEvent.Builder(25f).setIndex(mSeries1Index).setDelay(3300).build()
//        )
//        dynamicArcView.addEvent(
//            DecoEvent.Builder(50f).setIndex(mSeries1Index).setDuration(1500).setDelay(9000).build()
//        )


        dynamicArcView.addEvent(
            DecoEvent.Builder(cases.toFloat()).setIndex(mSeries1Index).setDelay(3000).build()
        )
        dynamicArcView.addEvent(
            DecoEvent.Builder(recovered.toFloat()).setIndex(mSeries2Index).setDelay(6000).build()
        )
        dynamicArcView.addEvent(
            DecoEvent.Builder(deaths.toFloat()).setIndex(mSeries3Index).setDelay(9000).build()
        )

//        dynamicArcView.addEvent(
//            DecoEvent.Builder(0f).setIndex(mSeries1Index).setDuration(500).setDelay(10500)
//                .setListener(object : ExecuteEventListener {
//                    override fun onEventStart(event: DecoEvent) {
//                        mUpdateListeners = false
//                    }
//
//                    override fun onEventEnd(event: DecoEvent) {}
//                })
//                .setInterpolator(AccelerateInterpolator()).build()
//        )


    }

    fun addProgressRemainingListener(
        seriesItem: SeriesItem,
        view: TextView,
        format: String,
        maxValue: Float
    ) {
        require(format.length > 0) { "String formatter can not be empty" }
        seriesItem.addArcSeriesItemListener(object : SeriesItemListener {
            override fun onSeriesItemAnimationProgress(
                percentComplete: Float,
                currentPosition: Float
            ) {
                if (mUpdateListeners) {
                    if (format.contains("%%")) {
                        // We found a percentage so we insert a percentage
                        view.text = String.format(
                            format,
                            (1.0f - currentPosition / seriesItem.maxValue) * 100f
                        )
                    } else {
                        view.text = String.format(format, maxValue - currentPosition)
                    }
                }
            }

            override fun onSeriesItemDisplayProgress(percentComplete: Float) {}
        })
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