package com.cretix.recyclerviewhw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager = LinearLayoutManager(this)
        val items_ = listOf(
            Header("Никита Амирханов", "10 Класс", "https://github.com/cret1x"),
            Info("Идея проекта", "Здесь написана очень интересная идея проекта которая всем прям очень понравится дада конечно."),
            SkillHeader(false),
            Skill("PHP", ">2 лет"),
            Skill("Python", ">3 лет"),
            Skill("C++", ">3 лет"),
            Skill("C#", "<1 года")
        )
        recycler.adapter = Adapter().apply { items = items_ }
    }
}
