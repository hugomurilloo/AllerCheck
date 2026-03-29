package com.allercheck

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class activity_stats : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val viewModel = ViewModelProvider(this).get(StatsViewModel::class.java)

        val btnAtras = findViewById<ImageButton>(R.id.btnAtras)
        val barChart = findViewById<BarChart>(R.id.barChartFiltros)
        val pieChart = findViewById<PieChart>(R.id.pieChartReviews)
        val tvCo2 = findViewById<TextView>(R.id.tvCo2)
        val btnReset = findViewById<Button>(R.id.btnReset)

        btnAtras.setOnClickListener { finish() }

        btnReset.setOnClickListener {
            viewModel.resetStats()
            Toast.makeText(this, "Estadístiques reiniciades", Toast.LENGTH_SHORT).show()
        }

        // Observar cambios y actualizar gráficos
        viewModel.stats.observe(this) { stats: AppStats? ->
            if (stats == null) return@observe

            // BARRAS - Filtros
            val barEntries = ArrayList<BarEntry>()
            barEntries.add(BarEntry(0f, stats.gluten.toFloat()))
            barEntries.add(BarEntry(1f, stats.lactose.toFloat()))
            barEntries.add(BarEntry(2f, stats.fruitsSecs.toFloat()))
            barEntries.add(BarEntry(3f, stats.marisc.toFloat()))
            barEntries.add(BarEntry(4f, stats.vega.toFloat()))
            barEntries.add(BarEntry(5f, stats.halal.toFloat()))
            barEntries.add(BarEntry(6f, stats.kosher.toFloat()))
            barEntries.add(BarEntry(7f, stats.ou.toFloat()))

            val barDataSet = BarDataSet(barEntries, "Filtres")
            barDataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.GRAY, Color.BLACK)
            barDataSet.valueTextSize = 10f

            barChart.data = BarData(barDataSet)
            barChart.xAxis.valueFormatter = IndexAxisValueFormatter(listOf("Glu", "Lac", "Fru", "Mar", "Veg", "Hal", "Kos", "Ou"))
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.xAxis.granularity = 1f
            barChart.description.isEnabled = false
            barChart.animateY(800)
            barChart.invalidate() // Fuerza el redibujado

            // REDONDO - Reseñas
            val pieEntries = ArrayList<PieEntry>()
            if (stats.reviews > 0) {
                pieEntries.add(PieEntry(stats.reviews.toFloat(), "Ressenyes"))
            } else {
                pieEntries.add(PieEntry(1f, "Sense activitat"))
            }

            val pieDataSet = PieDataSet(pieEntries, "")
            pieDataSet.colors = listOf(Color.parseColor("#79C447"), Color.LTGRAY)
            pieDataSet.valueTextSize = 12f

            pieChart.data = PieData(pieDataSet)
            pieChart.description.isEnabled = false
            pieChart.centerText = "Activitat"
            pieChart.animateXY(800, 800)
            pieChart.invalidate()

            // CÁLCULO CO2 REAL
            val kgCo2 = (stats.secondsUsed / 3600.0) * 0.004
            tvCo2.text = "Petjada CO2: ${String.format("%.6f", kgCo2)} kg"
        }
    }
}