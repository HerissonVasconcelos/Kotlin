package com.condo.finanask.extension

import com.financas.model.Tipo
import java.math.BigDecimal
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

fun String.ConvertToBigDecimal(): BigDecimal {
    return BigDecimal(this.replace("R$ ", "").replace(".", "").replace(",", "."))
}

fun String.ConvetToEnumTipo() : Tipo {
    return if(this == "RECEITA") Tipo.RECEITA else Tipo.DESPESA
}