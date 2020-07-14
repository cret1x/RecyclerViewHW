package com.cretix.recyclerviewhw

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_filter.*
import java.io.Serializable

class FilterActivity : AppCompatActivity() {
    companion object {
        const val KEY_SEL = "selected"
        private var prefs: SharedPreferences ? = null
        const val REQUEST_CODE = 1337
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        prefs = getSharedPreferences("FILTER", Context.MODE_PRIVATE)
        recycler.layoutManager = LinearLayoutManager(this)
        val items_ = listOf(
            YearFilter(-1, 999999999, "", prefs!!.getBoolean("0", true), true),
            YearFilter(0, 365, "<1 года", prefs!!.getBoolean("1", true), false),
            YearFilter(720, 999999999, ">2 лет", prefs!!.getBoolean("2", true), false),
            YearFilter(1095, 999999999, ">3 лет", prefs!!.getBoolean("3", true), false)
        )

        val isChecked: (YearFilter) -> Boolean = { it.checked }

        recycler.adapter = FilterAdapter(applicationContext).apply { items = items_ }
        button.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().putExtra(KEY_SEL,  items_.filter { isChecked(it) } as Serializable))
            finish()
        }
    }
}
