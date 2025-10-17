package com.uts.uts_map_kelompok_i

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.uts.uts_map_kelompok_i.data.Schedule
import java.util.*

class StatisticFragment : Fragment() {

    private var lineChart: LineChart? = null
    private var layoutAchievements: LinearLayout? = null
    private var layoutTips: LinearLayout? = null
    private var spinnerFilter: Spinner? = null

    private val scheduleViewModel: ScheduleViewModel by activityViewModels()
    private val handler = Handler(Looper.getMainLooper())
    private var currentMode = "Minggu"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_statistic_progress, container, false)

        lineChart = view.findViewById(R.id.lineChart)
        layoutAchievements = view.findViewById(R.id.layoutAchievements)
        layoutTips = view.findViewById(R.id.layoutTips)
        spinnerFilter = view.findViewById(R.id.spinnerFilter)

        setupSpinner()
        showAchievements()
        showRandomTips()
        startAutoUpdate()
        observeCompletedSchedules()

        return view
    }

    private fun setupSpinner() {
        val items = arrayOf("Minggu", "Bulan", "Tahun")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        spinnerFilter?.adapter = adapter

        spinnerFilter?.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentMode = items[position]
                observeCompletedSchedules()
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }
    }

    private fun observeCompletedSchedules() {
        scheduleViewModel.completedSchedules.observe(viewLifecycleOwner) { completed ->
            if (completed.isEmpty()) {
                lineChart?.clear()
                return@observe
            }

            setupChartFromHistory(completed)
        }
    }

    private fun setupChartFromHistory(completed: List<Schedule>) {
        val labels = ArrayList<String>()
        val entries = ArrayList<Entry>()

        when (currentMode) {
            "Minggu" -> {
                val days = listOf("Senin","Selasa","Rabu","Kamis","Jumat","Sabtu","Minggu")
                days.forEachIndexed { index, day ->
                    val count = completed.count { it.dayOfWeek == day }
                    labels.add(day)
                    entries.add(Entry(index.toFloat(), count.toFloat()))
                }
            }
            "Bulan" -> {
                val weeks = listOf("Minggu1","Minggu2","Minggu3","Minggu4")
                weeks.forEachIndexed { index, week ->
                    val count = completed.count {
                        val dayOfMonth = getDayOfMonth(it.date)
                        (dayOfMonth in ((index*7)+1)..((index+1)*7))
                    }
                    labels.add(week)
                    entries.add(Entry(index.toFloat(), count.toFloat()))
                }
            }
            "Tahun" -> {
                val months = listOf("Jan","Feb","Mar","Apr","Mei","Jun","Jul","Agu","Sep","Okt","Nov","Des")
                months.forEachIndexed { index, month ->
                    val count = completed.count { getMonth(it.date) == index }
                    labels.add(month)
                    entries.add(Entry(index.toFloat(), count.toFloat()))
                }
            }
        }

        val dataSet = LineDataSet(entries, "Jumlah Latihan ($currentMode)").apply {
            color = Color.parseColor("#FFD60A")
            setCircleColor(Color.BLACK)
            circleRadius = 4f
            lineWidth = 3f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(false)
        }

        lineChart?.data = LineData(dataSet)
        lineChart?.xAxis?.apply {
            valueFormatter = IndexAxisValueFormatter(labels)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            textColor = Color.BLACK
            granularity = 1f
        }

        lineChart?.axisLeft?.textColor = Color.BLACK
        lineChart?.axisRight?.isEnabled = false
        lineChart?.legend?.isEnabled = false
        lineChart?.description?.isEnabled = false
        lineChart?.setTouchEnabled(false)
        lineChart?.animateY(700)
        lineChart?.invalidate()
    }

    // helper untuk ambil hari/bulan
    private fun getDayOfMonth(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun getMonth(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(Calendar.MONTH) // 0=Jan
    }

    private fun showAchievements() {
        layoutAchievements?.removeAllViews()
        val achievements = listOf(
            "🏃‍♂️ Rajin berolahraga minggu ini!",
            "💪 Konsisten menjaga kebugaran!",
            "🔥 Terus tingkatkan jumlah latihan!"
        )
        achievements.forEach { text ->
            val tv = TextView(requireContext()).apply {
                this.text = text
                this.textSize = 16f
                this.setTextColor(Color.BLACK)
                this.setPadding(16, 12, 16, 12)
            }
            layoutAchievements?.addView(tv)
        }
    }

    private fun showRandomTips() {
        val tipsList = listOf(
            "💡 Lakukan pemanasan 5 menit sebelum latihan.",
            "💧 Jangan lupa minum air setelah berolahraga.",
            "🧘 Istirahat cukup agar otot bisa pulih optimal.",
            "🏋️‍♂️ Fokus pada teknik, bukan hanya durasi.",
            "🥗 Konsumsi makanan sehat tinggi protein setelah latihan."
        )

        if (tipsList.isEmpty()) return

        val randomTip = tipsList.random()
        layoutTips?.removeAllViews()

        val card = CardView(requireContext()).apply {
            setCardBackgroundColor(Color.parseColor("#FFF9C4"))
            radius = 24f
            cardElevation = 8f
            setContentPadding(24, 24, 24, 24)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
        }

        val tv = TextView(requireContext()).apply {
            text = randomTip
            textSize = 16f
            setTextColor(Color.DKGRAY)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        card.addView(tv)
        layoutTips?.addView(card)

        card.alpha = 0f
        card.translationY = 50f
        card.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun startAutoUpdate() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isAdded) {
                    observeCompletedSchedules()
                    showRandomTips()
                    handler.postDelayed(this, 10000)
                }
            }
        }, 5000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
