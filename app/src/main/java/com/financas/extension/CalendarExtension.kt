package com.condo.finanask.extension

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formataData():String{
    return SimpleDateFormat("dd/MM/yyyy").format(this.time)
}