package com.pluu.theme.sample.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import androidx.annotation.ColorInt

@ColorInt
fun Context.getColorFromTheme(colorAttributeId: Int): Int {
    val typedValue = TypedValue()
    val typedArray: TypedArray = this.obtainStyledAttributes(
        typedValue.data, intArrayOf(colorAttributeId)
    )
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}
