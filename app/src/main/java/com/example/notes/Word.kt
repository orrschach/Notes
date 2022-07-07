package com.example.notes

data class Word(
    val en: String?,
    val ch: String?,
    val w_class: String?,
) {
    companion object {
        val TABLE = "Word"
        val COL_ID = "id"
        val COL_en = "en"
        val COL_ch = "ch"
        val COL_w_class = "w_class"
    }
    var id: Int? = null
}