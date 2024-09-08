package com.tafakkur.blogweb.util

import kotlin.js.Date

fun String.toFormattedDateTime(): String {
    val jsDate = Date(this)
    val day = jsDate.getDate().toString().padStart(2,'0')
    val month = jsDate.toLocaleString("id-ID", js("{ month: 'long' }"))
    val year = jsDate.getFullYear()
    val hours = jsDate.getHours().toString().padStart(2,'0')
    val minutes = jsDate.getMinutes().toString().padStart(2,'0')

    return "$day $month $year $hours:$minutes"
}