package com.financas.model

import java.math.BigDecimal
import java.util.*

class Transacao(val valor: BigDecimal,
                val tipo: Tipo,
                val categoria : String = "Outra",
                val data : Calendar = Calendar.getInstance())