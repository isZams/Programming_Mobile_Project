package com.example.programming_mobile_project.beach_view

import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText
import java.lang.NumberFormatException

/**
 * Classe che implementa [InputFilter] per definire i valori massimi e minimi di ogni EditText
 */
class MinMaxFilter(val min: Int, val max: Int) : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dStart: Int,
        dEnd: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(min, max, input))
                return null
        } catch (e: NumberFormatException) {
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}