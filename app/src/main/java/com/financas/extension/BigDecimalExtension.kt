package com.condo.finanask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formataMoeda(): String {
    var moedaBR = DecimalFormat.getCurrencyInstance(Locale("pt", "br")).format(this)
    moedaBR = if (this < BigDecimal.ZERO) {
        moedaBR.replace("(R$", "R$ -")
                .replace(")", "")
    } else {
        moedaBR.replace("R$", "R$ ")
    }
    return moedaBR
}