package br.com.knowledge.core.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import br.com.knowledge.R
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat
import java.util.*

var TextInputLayout.text: String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }

fun loadingImage(context: Context, url: String?, imageView: ImageView) {
    Glide
        .with(context)
        .load(url)
        .into(imageView)
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Double.formatCurrency(locale: Locale = Locale.getDefault()): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}