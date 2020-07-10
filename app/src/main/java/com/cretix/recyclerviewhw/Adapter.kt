package com.cretix.recyclerviewhw

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_info.view.*
import kotlinx.android.synthetic.main.item_skills.view.*
import kotlinx.android.synthetic.main.item_skills_header.view.*

class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_HEADER = 1
    private val TYPE_INFO = 2
    private val TYPE_SKILLS_HEADER = 3
    private val TYPE_SKILLS = 4

    var items: List<DataTypes> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int =
        items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false))
        } else if (viewType == TYPE_INFO) {
            return InfoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false))
        } else if (viewType == TYPE_SKILLS_HEADER) {
            return HeaderSkillsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_skills_header, parent, false))
        } else if (viewType == TYPE_SKILLS) {
            return SkillsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_skills, parent, false))
        } else {
            return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = items[position].type
        if (type == TYPE_HEADER) {
            val h: Header = items[position] as Header
            (holder as HeaderViewHolder).onBind(h.name, h.grade, h.link)
        }
        if (type == TYPE_INFO) {
            val h: Info = items[position] as Info
            (holder as InfoViewHolder).onBind(h.header, h.text)
        }
        if (type == TYPE_SKILLS_HEADER) {
            val h: SkillHeader = items[position] as SkillHeader
            (holder as HeaderSkillsViewHolder).onBind(h.filtered)
        }
        if (type == TYPE_SKILLS) {
            val h: Skill = items[position] as Skill
            (holder as SkillsViewHolder).onBind(h.name, h.length)
        }
    }

    private class HeaderViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val textName = root.name
        private val textGrade = root.grade
        private val textLink = root.btn_github
        private val img = root.imageView
        private val v = root

        fun onBind(name: String, grade: String, link: String) {
            img.clipToOutline = true
            textName.text = name
            textGrade.text = grade
            textLink.setOnClickListener {
                val uri: Uri = Uri.parse(link)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                v.context.startActivity(intent)
            }
        }
    }

    private class InfoViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val textHeader = root.header_info
        private val textText = root.text_info
        fun onBind(header: String, text: String) {
            textHeader.text = header
            textText.text = text
        }
    }
    inner class HeaderSkillsViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val icon = root.filtered
        fun onBind(filtered: Boolean) {
            icon.setOnClickListener {
                (it.context as Activity).startActivityForResult(Intent(it.context, FilterActivity::class.java), 1337)
            }
            if (filtered) {
                icon.setImageResource(R.drawable.ic_filter_active)
            } else {
                icon.setImageResource(R.drawable.ic_filter)
            }
        }
    }
    private class SkillsViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val skillName = root.skill_name
        private val skillLength = root.duration
        private val v = root

        fun onBind(name: String, length: String) {
            skillName.text = name
            skillLength.text = length
        }
    }
}
