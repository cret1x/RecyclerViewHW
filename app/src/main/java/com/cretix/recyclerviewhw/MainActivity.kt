package com.cretix.recyclerviewhw

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    companion object {
        private const val KEY_SEL = "selected"
        private const val REQUEST_CODE = 1337
        private var prefs: SharedPreferences? = null
        private var filters = listOf<YearFilter>()
    }


    private val filtered_skills = listOf(
        Skill("PHP", ">2 лет", 750),
        Skill("JS", ">2 лет", 750),
        Skill("Kotlin", ">2 лет", 750),
        Skill("Python", ">3 лет", 10000),
        Skill("C/C++", ">3 лет", 8000),
        Skill("C#", "<1 года", 100)
    )

    private fun matchFilter(skill: Skill): Boolean {
        if (filters.isEmpty()) return false
        if (filters[0].all) return true
        for (filter in filters) {
            if (skill.length == filter.displayAge) return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = getSharedPreferences("FILTER", Context.MODE_PRIVATE)
        filters = if (savedInstanceState?.getSerializable(KEY_SEL) != null) {
            savedInstanceState.getSerializable(KEY_SEL) as List<YearFilter>
        } else {
            listOf( YearFilter(-1, 999999999, "", prefs!!.getBoolean("0", true), true))
        }
    }

    override fun onStart() {
        super.onStart()
        recycler.layoutManager = LinearLayoutManager(this)
        val items_ = mutableListOf(
            Header("Никита Амирханов", "10 Класс", "https://github.com/cret1x"),
            Info("Идея проекта", "Приложение для синхронизации медиа документов между телефоном и компьютером. По типу Гугл Фото, но работающее в локальной сети. Есть возможность выбирать какие альбомы будут синхронизироваться"),
            SkillHeader(filters.isNotEmpty())
        )
        for (skill in filtered_skills)
            if (matchFilter(skill))
                items_.add(skill)
        recycler.adapter = Adapter().apply { items = items_ }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_SEL, filters as Serializable)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val a = data.getSerializableExtra(KEY_SEL)
            filters = a as List<YearFilter>
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
