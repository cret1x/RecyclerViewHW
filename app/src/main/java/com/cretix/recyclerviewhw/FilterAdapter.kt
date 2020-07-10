package com.cretix.recyclerviewhw

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var prefs: SharedPreferences = ctx.getSharedPreferences("FILTER", Context.MODE_PRIVATE)

    var items: List<YearFilter> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(items[position].displayAge, items[position].checked, items[position].all, position)
    }

    inner class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val box = root.checkBox

        fun onBind(name: String, chk: Boolean, all: Boolean, pos: Int) {

            if (all) {
                box.isChecked = chk
                box.setText(R.string.filter_all)
                box.setOnClickListener {
                    val isChecked = box.checkBox.isChecked

                    Log.d("ALL Box", "Chainging to $isChecked")
                    for (i in items.indices) {
                        items[i].checked = isChecked
                        prefs.edit().putBoolean((i).toString(), isChecked).apply()
                    }
                    notifyDataSetChanged()
                }
            } else {
                box.text = name
                box.isChecked = chk

                box.setOnClickListener {
                    items[pos].checked = box.checkBox.isChecked
                    prefs.edit().putBoolean((pos).toString(), box.checkBox.isChecked).apply()
                    var all_checked = true
                    for (item in items) {
                        if (!item.all && !item.checked) {
                            all_checked = false
                        }
                    }
                    items[0].checked = all_checked
                    prefs.edit().putBoolean(("0").toString(), all_checked).apply()
                    notifyDataSetChanged()
                }
            }
        }
    }
}
