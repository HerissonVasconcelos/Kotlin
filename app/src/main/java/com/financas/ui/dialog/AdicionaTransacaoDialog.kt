package com.financas.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.financas.R
import com.financas.model.Tipo

class AdicionaTransacaoDialog(viewGroup: ViewGroup, context: Context) : FormularioTransacaoDialog(viewGroup, context) {

    override val tituloBotao: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        val titulo = if (tipo == Tipo.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
        return titulo
    }


}