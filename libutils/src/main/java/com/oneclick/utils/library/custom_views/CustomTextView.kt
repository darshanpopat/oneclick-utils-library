package com.oneclick.utils.library.custom_views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.oneclick.utils.library.R
import com.oneclick.utils.library.listeners.TypeFactory

class CustomTextView(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {
    private var typefaceType = 0
    private var mTypeFactory: TypeFactory? = null

    init {
        mTypeFactory = TypeFactory(context)

        val array: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            0, 0
        )

        typefaceType = try {
            array.getInteger(R.styleable.CustomTextView_font_name, 0)
        } finally {
            array.recycle()
        }

        if (!isInEditMode) {
            setTextIsSelectable(false)
            typeface = getTypeFace(typefaceType)
        }
    }

    private fun getTypeFace(type: Int): Typeface? {
        return when (type) {
            Constants.REGULAR -> mTypeFactory!!.regular
            Constants.MEDIUM -> mTypeFactory!!.medium
            Constants.SEMIBOLD -> mTypeFactory!!.semibold

            else -> mTypeFactory!!.regular
        }
    }

    interface Constants {
        companion object {
            const val REGULAR = 1
            const val MEDIUM = 2
            const val SEMIBOLD = 3
        }
    }

}
