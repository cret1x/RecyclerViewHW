package com.cretix.recyclerviewhw

abstract class DataTypes(val type: Int)
data class Header(val name: String, val grade: String, val link: String) : DataTypes(1)
data class Info(val header: String, val text: String) : DataTypes(2)
data class SkillHeader(val filtered: Boolean) : DataTypes(3)
data class Skill(val name: String, val length: String) : DataTypes(4)
