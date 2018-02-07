package com.condo.finanask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int) : String{

    if(this.length > caracteres){
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter,caracteres)}..."
    }
    return this
}

fun String.ConvertToCalendar(): Calendar {
    val dataFormatada = SimpleDateFormat("dd/MM/yyyy").parse(this)
    val data = Calendar.getInstance()
    data.time = dataFormatada
    return data
}