package com.android.mykotlinmvp

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by zhangguanjun on 2018/1/4.
 */
fun Fragment.showToast(content: String) {
    Toast.makeText(activity,content,Toast.LENGTH_SHORT).show()
}

fun Context.showToast(content: String) {
    Toast.makeText(this,content,Toast.LENGTH_SHORT).show()
}

