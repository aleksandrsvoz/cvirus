package com.alexvoz.cvirus.util

import java.text.DecimalFormat

object UiUtils {

}

fun Float.getNumberWithSpaces(): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(this)
}

fun Int.getNumberWithSpaces(): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(this)
}