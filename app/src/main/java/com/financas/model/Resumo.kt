package com.financas.model

import com.condo.finanask.extension.ConvertToBigDecimal
import com.financas.data.room.TransacaoEntity
import java.math.BigDecimal

class Resumo(val transacoes: List<TransacaoEntity>) {

    val totalReceita get() = somaPor(Tipo.RECEITA)

    val totalDespesa get() = somaPor(Tipo.DESPESA)

    val total get() = totalReceita.subtract(totalDespesa)

    private fun somaPor(tipo: Tipo): BigDecimal {
        val somaTransacoesPeloTipo = transacoes
                .filter {it.tipo == tipo.toString()}
                .sumByDouble { it.valor.ConvertToBigDecimal().toDouble() }
        return BigDecimal(somaTransacoesPeloTipo)
    }
}