package com.neppplus.finalproject_20210915.utils

import android.content.Context
import android.util.TypedValue

class SizeUtil {

    companion object {

        fun dpToBox(context: Context, dp: Float): Float{

            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, context.resources.displayMetrics)

        }
    }
}