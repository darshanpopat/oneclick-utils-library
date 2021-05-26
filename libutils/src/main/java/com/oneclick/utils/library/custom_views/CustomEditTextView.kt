package com.oneclick.utils.library.custom_views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.oneclick.utils.library.R
import com.oneclick.utils.library.listeners.TypeFactory
import com.oneclick.utils.library.AppUtils

/**
 * Author : Dhruti Mistry
 * Date : 10 December 2020
 * Description : Custom class for handling edittext validation on edittext focus change.
 */
class CustomEditTextView(context: Context, attrs: AttributeSet?) :
    TextInputEditText(context, attrs) {
    private var typefaceType = 0
    private var typeEditText = 0
    private var mTypeFactory: TypeFactory? = null

    init {
        mTypeFactory = TypeFactory(context)

        val array: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditTextView,
            0, 0
        )

        typeEditText = try {
            array.getInteger(R.styleable.CustomEditTextView_view_type, 0)
        } finally {
            array.recycle()
        }

        if (!isInEditMode) {
            setTextIsSelectable(false)
            typeface = getTypeFace(typefaceType)
        }

        onFocusChangeListener = OnFocusChangeListener { _, b ->
            if (!hasFocus()) {
                when (typeEditText) {
                    0 -> {//normal validation
                        if (text!!.trim().toString().isEmpty()) {
                            showToast(this, resources.getString(R.string.text_empty_email))
                        }
                    }
                    1 -> {//email validation
                        if (text!!.trim().toString().isEmpty()) {
                            showToast(this, resources.getString(R.string.text_empty_email))
                        } else {
                            if (!AppUtils.isEmailValid(text!!.trim().toString())) {
                                showToast(
                                    this,
                                    resources.getString(R.string.text_invalid_email)
                                )
                            }
                        }
                    }
                    2 -> {//password validation
                        if (text!!.trim().toString().isEmpty()) {
                            showToast(this, resources.getString(R.string.text_empty_password))
                        }
                    }
                    3 -> {//fname validation
                        if (text!!.trim().toString().isEmpty()) {
                            showToast(this, resources.getString(R.string.text_empty_fname))
                        }
                    }
                    4 -> {//lname  validation
                        if (text!!.trim().toString().isEmpty()) {
                            showToast(this, resources.getString(R.string.text_empty_lname))
                        }
                    }
                    5 -> {//username validation
                        if (text!!.trim().toString().isEmpty()) {
                            showToast(this, resources.getString(R.string.text_empty_username))
                        }
                    }
                    6 -> {//confirm pass validation
                        if (text!!.trim().toString().isEmpty()) {
                            showToast(this, resources.getString(R.string.text_empty_confirm_pass))
                        }
                    }
                    7 -> {//mobile no validation
                        if (text!!.trim().toString().isEmpty()) {
                            showToast(this, resources.getString(R.string.text_empty_mobile))
                        }
                    }
                }
            }
        }
    }

    private fun showToast(view: View, error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
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