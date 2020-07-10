package com.cretix.recyclerviewhw

import java.io.Serializable

abstract class DataTypes(val type: Int)
data class Header(val name: String, val grade: String, val link: String) : DataTypes(1)
data class Info(val header: String, val text: String) : DataTypes(2)
data class SkillHeader(val filtered: Boolean) : DataTypes(3)
data class Skill(val name: String, val length: String, val realAge: Int) : DataTypes(4)
data class YearFilter(val ageMin: Int, val ageMax: Int, val displayAge: String, var checked: Boolean, val all: Boolean) : Serializable
