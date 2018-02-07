package com.financas.model

import java.math.BigDecimal

class Resumo(val transacoes: List<Transacao>) {

    val totalReceita get() = somaPor(Tipo.RECEITA)

    val totalDespesa get() = somaPor(Tipo.DESPESA)

    val total get() = totalReceita.subtract(totalDespesa)

    private fun somaPor(tipo: Tipo): BigDecimal {
        val somaTransacoesPeloTipo = transacoes
                .filter {it.tipo == tipo}
                .sumByDouble { it.valor.toDouble() }
        return BigDecimal(somaTransacoesPeloTipo)
    }
}